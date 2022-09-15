package com.ds.config.authority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据范围注解
 * @DataScope(authKey = AuthKeyConstant.ILLEGAL_BUILD_DAYLIY_INSPECT_VIEW, comTableName = "u")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataScope {
    /**
     * 数据权限
     */
    int auth() default 1;
    /**
     * 需要加数据权限范围的表别名,单位权限
     */
    String comTableName() default "com";

    String comFieldName() default "com_id";
}
