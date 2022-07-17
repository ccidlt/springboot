package com.ds.config.authority;

/**
 * 数据范围权限需要业务层实现的接口
 */
public interface UserAuthInterface {
    /**
     * 查询用户对应权限的限制范围值
     *
     * @param authKey 权限值key
     * @return 用户的权限值
     */
    UserAuth getUserAuth(String authKey);
}
