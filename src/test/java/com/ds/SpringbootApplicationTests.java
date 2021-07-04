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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    @Test
    public void comparator (){
        Comparator<Integer> cpt = new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        TreeSet<Integer> set = new TreeSet<>(cpt);
        set.add(1);
        set.add(3);
        set.add(2);
        System.out.println(set.toString());

        Comparator<Integer> cpt2 = (x,y) -> Integer.compare(x,y);
        set = new TreeSet<>(cpt);
        set.add(6);
        set.add(4);
        set.add(5);
        System.out.println(set.toString());

        System.out.println(show1(() -> "hello supplier"));

        show2("consumer",(String name) -> {
            System.out.println("hello "+name);
        });

        System.out.println(show3("predicate",(String name) -> {
            return name.length() == 9;
        }));

        System.out.println(show4("function",(String name) -> {
            return "hello "+name;
        }));

        set.stream().forEach(System.out::println);
        System.out.println("========================================");
        set.stream().filter((x) -> x <= 6).map((x) -> x+1).sorted((x,y) -> y.compareTo(x)).forEach(System.out::println);
        System.out.println("========================================");
        set.stream().filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer <= 6;
            }
        }).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer+1;
            }
        }).sorted(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        }).limit(2).forEach(System.out::println);

        System.out.println("========================================");
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("liubei",40));
        list.add(new User("zhangfei",30));
        list.add(new User("guanyu",35));
        list.stream().map(n->n.getName())
                .forEach(System.out::println);
        System.out.println(list.stream().map(n->n.getName()).collect(Collectors.toList()).toString());

        System.out.println("========================================");
        ArrayList<User> list1 = new ArrayList<>();
        list1.add(new User("liubei",40));
        list1.add(new User("zhangfei",30));
        list1.add(new User("guanyu",35));

        ArrayList<User> list2 = new ArrayList<>();
        list2.add(new User("liubei",40));
        list2.add(new User("lisi",30));
        list2.add(new User("zhangsan",35));

        list1.addAll(list2);

        Map<String,Object> map1 = new HashMap<>();
        list1.forEach((User user) -> {
            if(map1.containsKey(user.getName())){
                user.setAge(user.getAge()+((User)map1.get(user.getName())).getAge());
                map1.put(user.getName(),user);
            }else{
                map1.put(user.getName(),user);
            }
        });
        System.out.println(map1.entrySet().toString());
        List<User> list3 = new ArrayList<User>();
        map1.forEach((k,y) -> list3.add((User)y));
        System.out.println(list3);
    }

    public String show1(Supplier<String> supplier){
        return supplier.get();
    }

    public void show2(String name,Consumer<String> consumer){
        consumer.accept(name);
    }

    public boolean show3(String name,Predicate<String> predicate){
        return predicate.test(name);
    }

    public String show4(String name, Function<String,String> function){
        return function.apply(name);
    }

}
