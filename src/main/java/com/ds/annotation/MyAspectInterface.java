package com.ds.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAspectInterface {

    @AliasFor("value")
    String name() default "";

    @AliasFor("name")
    String value() default "";

    String describe() default "";
}
