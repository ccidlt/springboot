package com.ds.aop;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzAnno {

    @AliasFor("value")
    String name() default "";

    @AliasFor("name")
    String value() default "";

}
