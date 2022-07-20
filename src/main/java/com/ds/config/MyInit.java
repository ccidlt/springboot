package com.ds.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MyInit implements CommandLineRunner {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        scheduledTask();
    }

    private void scheduledTask() {
        try {
            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date nowDate = new Date();
            Date scheduleDate = sdfTime.parse(sdfDate.format(nowDate) + " 01:00:00");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            Date nextScheduleDate = calendar.getTime();
            long delay = 0;
            long period = 24 * 60 * 60 * 1000;
            long cz = scheduleDate.getTime() - nowDate.getTime();
            delay = cz >= 0 ? (scheduleDate.getTime() - nowDate.getTime()) : (nextScheduleDate.getTime() - nowDate.getTime());
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                if(redisTemplate.opsForValue().setIfAbsent("scheduledTask",true,5, TimeUnit.MINUTES)){
                    System.out.println("执行时间："+System.currentTimeMillis());
                    redisTemplate.delete("scheduledTask");
                }
            }, delay, period, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
