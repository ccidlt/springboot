package com.ds.config.shirotoken;

import com.ds.entity.Role;
import com.ds.entity.User;
import com.ds.service.PermissionService;
import com.ds.service.RoleService;
import com.ds.service.UserService;
import com.ds.utils.JWTUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    /**
     * 为了让realm支持jwt的凭证校验
     * 等同于配置类的指定token类型
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 权限校验
     * 只有当检测用户需要权限或者需要判定角色的时候才会走
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = principals.toString();
        String userId = JWTUtils.getClaim(token, "userId");
        User user = userService.findById(Integer.valueOf(userId));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(user.getUsername().equals("admin")){//该用户拥有所有权限
            info.addRole("*");
            info.addStringPermission("*:*:*");
        }else {
            List<Role> roleList = roleService.findRoleByUserId(user.getId());
            Set<String> roleSet = new HashSet<>();
            List<Integer> roleIds = new ArrayList<>();
            for (Role role : roleList) {
                roleSet.add(role.getRole());
                roleIds.add(role.getId());
            }
            // 放入角色信息
            info.setRoles(roleSet);
            // 放入权限信息
            List<String> permissionList = permissionService.findByRoleId(roleIds);
            info.setStringPermissions(new HashSet<>(permissionList));
        }
        return info;
    }

    /**
     * 登录认证校验
     * 默认使用此方法进行用户名正确与否验证, 如果没有权限注解的话就不会去走上面的方法只会走这个方法
     * SecurityUtils.getSubject().login(token)时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken)authenticationToken;
        String token = (String) jwtToken.getPrincipal();
        //token是否存在
        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException(" 请先登录! ");
        }
        String userId = null;
        try {
            userId= JWTUtils.getClaim(token, "userId");
        }catch (Exception e){
            throw new AuthenticationException("token非法，不是规范的token，可能被篡改了，或者过期了");
        }
        if (!JWTUtils.verify(token) || userId == null){
            throw new AuthenticationException("token认证失效，token错误或者过期，重新登陆");
        }
        User user = userService.findById(Integer.valueOf(userId));
        if (user==null){
            throw new AuthenticationException("该用户不存在");
        }
        DataContextSupport.setDataPermissions(user);
        return new SimpleAuthenticationInfo(token,token,getName());
    }
}
