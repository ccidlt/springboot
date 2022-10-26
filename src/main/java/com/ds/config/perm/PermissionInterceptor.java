package com.ds.config.perm;

import com.alibaba.fastjson.JSON;
import com.ds.entity.Menu;
import com.ds.entity.Role;
import com.ds.entity.User;
import com.ds.service.MenuService;
import com.ds.service.PermissionService;
import com.ds.service.RoleService;
import com.ds.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private MenuService menuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(Authentication.class)){
            Authentication annotation = method.getAnnotation(Authentication.class);
            if(annotation.required()){
                PermissionType permissionType = annotation.permissionType();
                String[] value = annotation.value();
                Logical logical = annotation.logical();
                if(value.length == 0){
                    return true;
                }
                List<String> valueList = Arrays.stream(value).collect(Collectors.toList());
                if(valueList.contains("admin")){
                    return true;
                }
                String token = request.getHeader("token");
                User user = userService.getUser(token);
                List<Role> roles = roleService.findRoleByUserId(user.getId());
                List<String> roleList = roles.stream().map(Role::getRole).collect(Collectors.toList());
                List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
                //是否包含某些角色
                switch (permissionType){
                    case ROLE:
                        //是否包含某些角色
                        if(logical == Logical.AND){
                            if(roleList.containsAll(valueList)){
                                return true;
                            }
                        }else if(logical == Logical.OR){
                            if(valueList.stream().anyMatch(roleList::contains)){
                                return true;
                            }
                        }
                        break;
                    case MENU:
                        //是否包含这次的请求路径的菜单
                        //String path = request.getRequestURI();
                        List<Menu> menuList = menuService.findByRoleId(roleIds);
                        Set<String> menuPermsSet = menuList.stream().map(Menu::getPerms).collect(Collectors.toSet());
                        if(CollectionUtils.isNotEmpty(menuPermsSet)){
                            if(logical == Logical.AND) {
                                if (menuPermsSet.containsAll(valueList)) {
                                    return true;
                                }
                            }else if(logical == Logical.OR){
                                if(valueList.stream().anyMatch(menuPermsSet::contains)){
                                    return true;
                                }
                            }
                        }
                        break;
                    case PERMISSION:
                        //是否包含权限
                        List<String> permissionList = permissionService.findByRoleId(roleIds);
                        Set<Object> permissionSet = new HashSet<>(permissionList);
                        if(CollectionUtils.isNotEmpty(permissionSet)) {
                            if (logical == Logical.AND) {
                                if (permissionSet.containsAll(valueList)) {
                                    return true;
                                }
                            } else if (logical == Logical.OR) {
                                if (valueList.stream().anyMatch(a -> permissionSet.contains(a))) {
                                    return true;
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
                Map<String,Object> rsp = new HashMap<>(2);
                rsp.put("code",403);
                rsp.put("msg","无权访问");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                final PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(rsp));
                return false;
            }
        }
        return true;
    }
}
