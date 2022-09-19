package com.ds.config.shirotoken;

import com.ds.entity.Role;
import com.ds.entity.User;
import com.ds.service.PermissionService;
import com.ds.service.RoleService;
import com.ds.service.UserService;
import com.ds.utils.RedisUtils;
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
import java.util.*;
import java.util.concurrent.TimeUnit;

public class UserRealm extends AuthorizingRealm {

    @Resource
    RedisUtils redisUtils;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    /**
     * 等同于配置类的指定token类型
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();//获取到用户
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

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken)authenticationToken;
        String token = (String) jwtToken.getPrincipal();
        //token是否存在
        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationException(" 请先登录! ");
        }
        User user = (User)redisUtils.get(token);
        if(user==null) {
            throw new AuthenticationException(" 请重新登录！ ");
        }
        redisUtils.set(token, user, 30L, TimeUnit.MINUTES);//redis token再次刷新值
        DataContextSupport.setDataPermissions(user);
        return new SimpleAuthenticationInfo(user,jwtToken.getPrincipal(),getName());
    }
}
