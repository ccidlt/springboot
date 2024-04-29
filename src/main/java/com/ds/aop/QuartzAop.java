package com.ds.aop;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.client.RedisException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

// 核心 AOP 实现
@Aspect
@Component
@Slf4j
public class QuartzAop {

    @Resource
    private RedisTemplate redisTemplate;

    public boolean checkStatus(String key){
        try {
            // 这个接口必然是并发的，所以加分布式锁
            while (true) {
                // 一秒的超时时间
                boolean lock = checkLock(key,1);
                if (lock) {
                    // 获取到锁，才能跳出
                    break;
                }
            }
            String ip = InetAddress.getLocalHost().getHostAddress();
            // 获取服务器上的工作ip
            String currentIp = StrUtil.toStringOrNull(redisTemplate.opsForValue().get(key));
            // 如果为空的时候，设置进去
            if(currentIp == null){
                redisTemplate.opsForValue().set(key, ip, 10, TimeUnit.SECONDS);
                return true;
            }
            // 就是当前机器，则返回true
            if(currentIp.equals(ip)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            redisTemplate.delete(key);
        }
    }

    @Around("@annotation(com.ds.aop.QuartzAnno)")
    public void around(ProceedingJoinPoint jp) throws Throwable{
        MethodSignature methodSignature = (MethodSignature)jp.getSignature();
        Method method = methodSignature.getMethod();
        QuartzAnno annotation = method.getAnnotation(QuartzAnno.class);
        String name = annotation.name();
        if(checkStatus(name)){
            String ip = InetAddress.getLocalHost().getHostAddress();
            log.info("现在正在执行"+jp.getSignature()+":"+ip);
            jp.proceed();
        }
    }

    // 锁代码
    public boolean checkLock(String key,int second) {
        String lockKey = "lock:" + key;
        try {
            //第一次设置成功返回true；如果有值，返回false
            if (redisTemplate.opsForValue().setIfAbsent(lockKey, "lock")) {
                // 设置有限期
                redisTemplate.expire(lockKey, second, TimeUnit.SECONDS);
                return true;
            } else {
                // 50毫秒的延迟，避免过多请求
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
                return false;
            }
        } catch (RedisException e) {
            log.error(e.getMessage());
            return true;
        }
    }

}
