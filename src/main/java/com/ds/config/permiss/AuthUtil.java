package com.ds.config.permiss;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Token 权限验证工具类
 * 
 * @author miki
 */
@Component
public class AuthUtil
{
    /**
     * 底层的 AuthLogic 对象
     */
    @Resource
    private AuthLogic authLogic;

    /**
     * 检验当前会话是否已经登录，如未登录，则抛出异常
     */
    public void checkLogin()
    {
        authLogic.checkLogin();
    }

    /**
     * 当前账号是否含有指定角色标识, 返回true或false
     * 
     * @param role 角色标识
     * @return 是否含有指定角色标识
     */
    public boolean hasRole(String role)
    {
        return authLogic.hasRole(role);
    }

    /**
     * 当前账号是否含有指定角色标识, 如果验证未通过，则抛出异常: NotRoleException
     * 
     * @param role 角色标识
     */
    public void checkRole(String role)
    {
        authLogic.checkRole(role);
    }

    /**
     * 根据注解传入参数鉴权, 如果验证未通过，则抛出异常: NotRoleException
     * 
     * @param requiresRoles 角色权限注解
     */
    public void checkRole(RequiresRoles requiresRoles)
    {
        authLogic.checkRole(requiresRoles);
    }

    /**
     * 当前账号是否含有指定角色标识 [指定多个，必须全部验证通过]
     * 
     * @param roles 角色标识数组
     */
    public void checkRoleAnd(String... roles)
    {
        authLogic.checkRoleAnd(roles);
    }

    /**
     * 当前账号是否含有指定角色标识 [指定多个，只要其一验证通过即可]
     * 
     * @param roles 角色标识数组
     */
    public void checkRoleOr(String... roles)
    {
        authLogic.checkRoleOr(roles);
    }

    /**
     * 当前账号是否含有指定权限, 返回true或false
     * 
     * @param permission 权限码
     * @return 是否含有指定权限
     */
    public boolean hasPermi(String permission)
    {
        return authLogic.hasPermi(permission);
    }

    /**
     * 当前账号是否含有指定权限, 如果验证未通过，则抛出异常: NotPermissionException
     * 
     * @param permission 权限码
     */
    public void checkPermi(String permission)
    {
        authLogic.checkPermi(permission);
    }

    /**
     * 根据注解传入参数鉴权, 如果验证未通过，则抛出异常: NotPermissionException
     * 
     * @param requiresPermissions 权限注解
     */
    public void checkPermi(RequiresPermissions requiresPermissions)
    {
        authLogic.checkPermi(requiresPermissions);
    }

    /**
     * 当前账号是否含有指定权限 [指定多个，必须全部验证通过]
     * 
     * @param permissions 权限码数组
     */
    public void checkPermiAnd(String... permissions)
    {
        authLogic.checkPermiAnd(permissions);
    }

    /**
     * 当前账号是否含有指定权限 [指定多个，只要其一验证通过即可]
     * 
     * @param permissions 权限码数组
     */
    public void checkPermiOr(String... permissions)
    {
        authLogic.checkPermiOr(permissions);
    }
}
