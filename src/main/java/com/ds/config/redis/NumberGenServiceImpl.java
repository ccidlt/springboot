package com.ds.config.redis;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ds.SpringbootApplication;
import com.ds.utils.RedissonUtils;
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

import javax.annotation.PostConstruct;
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
                System.out.println("收到消息：" + message);
            }
        }, REDIS_MESSAGE.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootApplication.class, args);
        //redisTemplate发布
        RedisTemplate redisTemplate = (RedisTemplate)context.getBean("redisTemplate");
        redisTemplate.convertAndSend(REDIS_MESSAGE, "hello!redisTemplate!");
        //redisson发布
        RedissonUtils.publish("redisson:message", "hello!redisson!");
    }


    /**
     * redisson生成序列
     * @param prefix 前缀
     * @return 生成的序列
     */
    public String redissonIncr(String prefix){
        String code = prefix + DateUtil.format(new Date(),"yyyyMMdd");
        String key = "redisson:" + code;
        long l = RedissonUtils.incrAtomicValue(key);
        return code += StrUtil.fillBefore(Convert.toStr(l), '0', 3);
    }


    /**
     * 初始化, 订阅消息
     */
    @PostConstruct
    public void init(){
        RedissonUtils.subscribe("redisson:message", String.class, msg -> {
            System.out.println("收到消息：" + msg);
        });
    }


}
