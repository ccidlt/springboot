package com.ds.config.repeatsubmit;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO 重复提交aop
 * @Author lt
 * @Date 2023/4/10 11:52
 */
@Component
@Aspect
@Slf4j
public class RepeatSubmitAop {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@within(com.ds.config.repeatsubmit.RepeatSubmitAnno) || @annotation(com.ds.config.repeatsubmit.RepeatSubmitAnno)")
    public void cut(){
        log.info("com.ds.config.repeatsubmit.RepeatSubmitAnno");
    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RepeatSubmitAnno repeatSubmitByCls = joinPoint.getTarget().getClass().getAnnotation(RepeatSubmitAnno.class);
        if(repeatSubmitByCls != null){
            log.info("repeatSubmitByCls：{}", repeatSubmitByCls.seconds());
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method methodObject = methodSignature.getMethod();
        RepeatSubmitAnno repeatSubmitByMethod = methodObject.getAnnotation(RepeatSubmitAnno.class);
        if(repeatSubmitByMethod != null){
            log.info("repeatSubmitByMethod：{}", repeatSubmitByMethod.seconds());
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes == null){
            return joinPoint.proceed();
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String uri = request.getRequestURI();
        log.info("请求uri：{}", uri);
        Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(uri, "",
                Objects.nonNull(repeatSubmitByMethod) ? repeatSubmitByMethod.seconds() : repeatSubmitByCls.seconds(),
                TimeUnit.SECONDS);
        //如果存在，表示已经请求过了，直接抛出异常，由全局异常进行处理返回指定信息
        if (ifAbsent != null && !ifAbsent) {
            throw new RuntimeException("重复请求");
        }
        return joinPoint.proceed();
    }

}
