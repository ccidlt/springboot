package com.ds.annotation;

import java.lang.annotation.*;

//@Target注解用于定义注解的使用位置，如果没有该项，表示注解可以用于任何地方 TYPE-类
@Target({ElementType.METHOD, ElementType.TYPE})
//@Retention注解用于指明修饰的注解的生存周期，即会保留到哪个阶段 RUNTIME-运行级别保留
@Retention(RetentionPolicy.RUNTIME)
//指明修饰的注解，可以被例如javadoc此类的工具文档化，只负责标记，没有成员取值
@Documented
//@Inherited注解用于标注一个父类的注解是否可以被子类继承，如果一个注解需要被其子类所继承，则在声明时直接使用@Inherited注解即可。如果没有写此注解，则无法被子类继承。
//@Inherited
public @interface AspectService {
    /** 要执行的操作类型比如：add操作 **/
    String operationType() default "";

    /** 要执行的具体操作人比如：添加用户 **/
    String operationName() default "";
}
