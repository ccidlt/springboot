package com.ds;

import com.ds.entity.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;

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


    @Test
    public void compare() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User("Kreas", 18));
        userList.add(new User("Rose", 1));
        userList.add(new User("Jack", 4));
        userList.add(new User("Jimi", 23));
        // 这里进行排序
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        // 输出排序结果
        System.out.println("按名称升序排序结果：");
        for (User user : userList) {
            System.out.println(user.toString());
        }

        Map<Integer,Object> oriMap = new HashMap<Integer,Object>();
        oriMap.put(18, new User("Kreas", 18));
        oriMap.put(1, new User("Rose", 1));
        oriMap.put(4, new User("Jack", 4));
        oriMap.put(23, new User("Jimi", 23));
        Map<Integer,Object> sortMap = new TreeMap<Integer,Object>(new Comparator<Integer>() {
            public int compare(Integer key1, Integer key2) {
                return key1-key2;
            }
        });
        sortMap.putAll(oriMap);
        for(Map.Entry<Integer,Object> entry : sortMap.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue().toString());
        }
    }

}
