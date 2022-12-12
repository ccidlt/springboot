package com.ds.config.redis;

import com.ds.SpringbootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Random;

@Configuration
public class RedisMQConfig {

    @Resource
    private RedisMQEntity redisMQEntity;
    @Resource(name = "tptExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 注入消息监听容器
     *
     * @param connectionFactory 连接工厂
     * @param listenerAdapter   监听处理器1
     * @param listenerAdapter2  监听处理器2
     *                          (参数名称需和监听处理器的方法名称一致，因为@Bean注解默认注入的id就是方法名称)
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter,
                                            MessageListenerAdapter listenerAdapter2,
                                            MessageListenerAdapter listenerAdapter3) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setTaskExecutor(threadPoolTaskExecutor);
        //订阅一个叫epc_01 的信道
        container.addMessageListener(listenerAdapter, new PatternTopic(redisMQEntity.getMq1()));
        //订阅一个叫epc_02 的信道
        container.addMessageListener(listenerAdapter2, new PatternTopic(redisMQEntity.getMq2()));
        //订阅一个叫epc_03 的信道
        container.addMessageListener(listenerAdapter3, new PatternTopic(redisMQEntity.getMq3()));
        //这个container 可以添加多个 messageListener
        return container;
    }

    /**
     * 消息监听处理器1
     *
     * @param receiver 处理器类
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(RedisReceiver receiver) {
        //给messageListenerAdapter 传入一个消息接收的处理器，利用反射的方法调用“receiveMessage”
        return new MessageListenerAdapter(receiver, "receiveMessage"); //receiveMessage：接收消息的方法名称
    }

    /**
     * 消息监听处理器2
     *
     * @param receiver 处理器类
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter2(RedisReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage2");
    }

    /**
     * 消息监听处理器3
     *
     * @param receiver 处理器类
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter3(RedisReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage3");
    }

    @Bean
    RedisReceiver redisReceiver() {
        return new RedisReceiver();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringbootApplication.class, args);
        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
        Random random = new Random(2);
        int i = random.nextInt(2) + 1;
        template.convertAndSend("epc_0" + i, "Hello From Redis" + i + "!");
    }

}
