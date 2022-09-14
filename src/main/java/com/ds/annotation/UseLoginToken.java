package com.ds.annotation;

import java.lang.annotation.*;

/**
 * 需要token认证
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseLoginToken {

    boolean required() default true;

}
