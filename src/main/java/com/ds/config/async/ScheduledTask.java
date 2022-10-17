package com.ds.config.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledTask {

    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    // 用来存入线程执行情况, 方便于停止定时任务时使用
    private ConcurrentHashMap<String, ScheduledFuture<?>> scheduleMap = new ConcurrentHashMap<>();

    private String cronStr = "0/3 * * * * ?";

    /**
     * 启动任务
     * 如果不想手动触发任务可以使用 @PostConstruct注解来启动
     */
    public void startTask(String name)  {
        try {
            ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(new MyTask(name), new CronTrigger(cronStr));
            if(Optional.ofNullable(schedule).isPresent()){
                scheduleMap.put(name, schedule);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 查询所有的任务
     */
    public void queryTask(){
        scheduleMap.forEach((k,v)->{
            System.out.println(k+"  "+v);
        });
    }

    /**
     * 停止单个任务
     * @param name
     */
    public void stopTask(String name){
        if (scheduleMap.isEmpty()) return;
        if(scheduleMap.containsKey(name)){//如果包含这个任务
            ScheduledFuture<?> scheduledFuture = scheduleMap.get(name);
            if(scheduledFuture!=null){
                scheduledFuture.cancel(true);
                boolean cancelled = scheduledFuture.isCancelled();
                while (!cancelled) {
                    scheduledFuture.cancel(true);
                }
                scheduleMap.remove(name);
            }
        }
    }

    /**
     * 停止所有任务
     */
    public void stopAll(){
        if (scheduleMap.isEmpty()) return;
        scheduleMap.values().forEach(scheduledFuture -> scheduledFuture.cancel(true) );
    }

    /**
     *修改定时任务
     * @param cronStr
     * @param name
     */
    public void changeTask(String cronStr, String name) {
        this.cronStr = cronStr;
        stopTask(name);
        startTask(name);
    }


    public class MyTask implements Runnable {

        MyTask(String name) {
        }

        @Override
        public void run() {

        }
    }

}
