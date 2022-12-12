package com.ds.annotation;

import java.lang.annotation.*;

/**
 * 跳过token认证
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassToken {

    boolean required() default true;

}
