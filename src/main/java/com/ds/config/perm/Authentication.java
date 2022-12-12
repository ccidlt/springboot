package com.ds.config.perm;

import java.lang.annotation.*;

/**
 * 鉴权
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authentication {

    boolean required() default true;

    /**
     * 类型 1-role 2-menu 3-permission
     *
     * @return
     */
    PermissionType permissionType() default PermissionType.ROLE;

    /**
     * 需要校验的权限码
     */
    String[] value() default {};

    /**
     * 验证模式：AND | OR，默认AND
     *
     * @return
     */
    Logical logical() default Logical.AND;

}
