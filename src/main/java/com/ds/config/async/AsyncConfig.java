package com.ds.config.async;

import com.ds.utils.RedisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(50);
        return executor;
    }

    @Resource
    private RedisUtils redisUtils;

    @Bean("asyncExecutor")
    public ThreadPoolTaskExecutor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数：线程池创建时候初始化的线程数
        executor.setCorePoolSize(10);
        // 最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20);
        // 缓冲队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        // 允许线程的空闲时间60秒：当超过了核心线程之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("thread-");
        // 缓冲队列满了之后的拒绝策略：由调用线程处理（一般是主线程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    @Async("asyncExecutor")
    public void a(){
        if(redisUtils.acquireLock("a",10)){
            try {
                System.out.println("每2分钟执行一次的任务："+Thread.currentThread().getName() + ":" + LocalDateTime.now());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                redisUtils.releaseLock("a");
            }
        }
    }

    @Async("asyncExecutor")
    public Future<String> b(){
        return new AsyncResult<>("返回值："+Thread.currentThread().getName());
    }

}
