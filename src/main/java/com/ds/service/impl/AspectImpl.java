package com.ds.service.impl;

import com.ds.service.AspectService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
@SuppressWarnings({"unused"})
public class AspectImpl {

    //@Pointcut("execution(public * com.ds.controller.*.*(..))")
    @Pointcut("@annotation(com.ds.service.AspectService)")
    private void cut(){

    }

    //环绕增强，是在before前就会触发,目标方法执行前后分别执行一些代码
    @Around("cut()")
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
        try{
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            log.info("Request, url: {"+url+"}, method: {"+method+"}, uri: {"+uri+"}, params: {"+queryString+"}");

            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            Method methodObject = methodSignature.getMethod();
            String operationType = methodObject.getAnnotation(AspectService.class).operationType();
            String operationName = methodObject.getAnnotation(AspectService.class).operationName();
            log.info("Method Type:" + operationType);
            log.info("Method Desc:" + operationName);

            String methodName = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
            Object args[] = joinPoint.getArgs();
            log.info("请求方法：{}, 请求参数: {}", methodName, Arrays.toString(args));
        }catch(Exception e){
            log.error(e.getMessage());
        }

        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        log.info("请求返回值：{}",result);
        return result;
    }

    //进来切点世界，先经过的第一个站
    @Before("cut()")
    public void before(JoinPoint joinPoint){

    }

    //进来切点这，最后经过的一个站，也是方法正常运行结束后,不管是抛出异常或者正常退出都会执行
    @After("cut()")
    public void after(JoinPoint joinPoint){

    }

    //统一异常处理
    @AfterThrowing(pointcut="cut()",throwing="e")
    public void afterThrowable(JoinPoint joinPoint,Throwable e) {
        //方法名获取
        String methodName = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        log.error("请求异常：请求方法：{}, 异常信息：{}",methodName,e.getMessage());
    }
}
