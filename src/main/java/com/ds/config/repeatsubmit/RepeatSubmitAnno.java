package com.ds.config.repeatsubmit;

import java.lang.annotation.*;

/**
 * @Description: TODO 重复提交注解
 * @Author lt
 * @Date 2023/4/10 11:51
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmitAnno {

    /**
     * 默认失效时间两秒
     * @return
     */
    long seconds() default 2;

}
