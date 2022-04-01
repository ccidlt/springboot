package com.ds.config;

import com.ds.service.MyAspectInterface;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Order(value = 2)
public class MyAspect {

//    @Pointcut("execution(public * com.ds.controller.*.*(..))")
    @Pointcut("@annotation(com.ds.service.MyAspectInterface)")
    public void cut(){

    }

    @SneakyThrows
    @Around("cut()")
    public Object around(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getName();//类名 com.ds.controller.******
        String methodName = signature.getName();//方法名
        Object[] paramValues = joinPoint.getArgs();//参数值
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();//参数名称
        Method method = signature.getMethod();
        MyAspectInterface annotation = method.getAnnotation(MyAspectInterface.class);
        String annotationValue = annotation.value();
        String annotationDescribe = annotation.describe();
        return joinPoint.proceed();
    }

}
