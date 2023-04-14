package com.ds.config.repeatsubmit;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO 重复提交interceptor
 * @Author lt
 * @Date 2023/4/14 10:22
 */
@Slf4j
public class RepeatSubmitInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public RepeatSubmitInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmitAnno repeatSubmitByCls = method.getDeclaringClass().getDeclaredAnnotation(RepeatSubmitAnno.class);
            RepeatSubmitAnno repeatSubmitByMethod = method.getAnnotation(RepeatSubmitAnno.class);
            if(ObjectUtil.isNull(repeatSubmitByCls) && ObjectUtil.isNull(repeatSubmitByMethod)){
                return true;
            }
            String uri = request.getRequestURI();
            log.info("请求uri：{}", uri);
            Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(uri, "",
                    ObjectUtil.isNotNull(repeatSubmitByMethod) ? repeatSubmitByMethod.seconds() : repeatSubmitByCls.seconds(),
                    TimeUnit.SECONDS);
            //如果存在，表示已经请求过了，直接抛出异常，由全局异常进行处理返回指定信息
            if (ifAbsent != null && !ifAbsent) {
                throw new RuntimeException("重复请求");
            }
        }
        return true;
    }

}
