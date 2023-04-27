package com.ds.config.redis;

import com.ds.SpringbootApplication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NumberGenServiceImpl implements NumberGenService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final int LENGTH = 6;

    private static final String MONTH_FORMAT = "yyyyMM";

    private static final String DAY_FORMAT = "yyyyMMdd";

    public String generateNumber(String code) {
        return getNumber(code, "");
    }

    public String generateNumberByMonth(String code) {
        return getNumber(code, new SimpleDateFormat(MONTH_FORMAT).format(new Date()));
    }

    public String generateNumberByDay(String code) {
        return getNumber(code, new SimpleDateFormat(DAY_FORMAT).format(new Date()));
    }

    private String getNumber(String code, String month) {
        code += month;
        Long number = stringRedisTemplate.opsForValue().increment("abc" + ":" + code);
        return code + StringUtils.leftPad(number.toString(), LENGTH, '0');
    }

    private static String REDIS_MESSAGE = "redisMessage";
    @Autowired
    public NumberGenServiceImpl(RedisTemplate redisTemplate) {
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        redisConnection.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                // 收到消息的处理逻辑
                System.out.println("Receive message : " + message);
            }
        }, REDIS_MESSAGE.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootApplication.class, args);
        RedisTemplate redisTemplate = (RedisTemplate)context.getBean("redisTemplate");
        redisTemplate.convertAndSend(REDIS_MESSAGE, "hello!");
    }

}
