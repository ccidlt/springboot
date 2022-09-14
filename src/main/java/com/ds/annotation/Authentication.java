package com.ds.annotation;

import java.lang.annotation.*;

/**
 * 鉴权
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authentication {

    boolean required() default true;

}
