package com.ds;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    DataSource dataSource;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void contextLoads() {
        System.out.println("当前数据源: "+dataSource.getClass());
    }

    @Test
    public void showDefaultCacheConfiguration() {
        System.out.println("一级缓存范围: " + sqlSessionFactory.getConfiguration().getLocalCacheScope());
        System.out.println("二级缓存是否被启用: " + sqlSessionFactory.getConfiguration().isCacheEnabled());
    }




    /*@Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void send1 (){
        for (int i=1;i<=10;i++){
            rabbitTemplate.convertAndSend("myQueue","message..."+i);
        }
    }
    @Test
    public void send2 (){
        rabbitTemplate.convertAndSend("myExchange","computer","message...direct发送");
    }
    @Test
    public void send3 (){
        rabbitTemplate.convertAndSend("myExchange","","message...fanout发送");
    }
    @Test
    public void send4 (){
        rabbitTemplate.convertAndSend("myExchange","user.fruit","message...topic发送");
    }
    @Test
    public void send5 (){
        rabbitTemplate.convertAndSend("myExchange","user.computer","message...topic发送");
    }*/
}
