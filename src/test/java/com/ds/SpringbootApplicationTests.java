package com.ds;

import com.ds.entity.Boy;
import com.ds.entity.FatherAndSon;
import com.ds.entity.Girl;
import com.ds.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
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
        set = new TreeSet<>(cpt2);
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

    @Resource
    RestTemplate restTemplate;

    @Test
    public void getDataBaseData(){
        //TestRestTemplate testRestTemplate = new TestRestTemplate();
        List<Boy> boyList = restTemplate.postForEntity("http://127.0.0.1:8081/getBoys", null, List.class).getBody();
        log.info(boyList.toString());
    }

    @Test
    public void lamboda(){
        User liubei = new User("liubei", 40);
        User guanyu = new User("guanyu", 38);
        User zhangfei = new User("zhangfei", 35);
        User zhangfei1 = new User("zhangfei", 36);
        List<User> users = new ArrayList<>();
        if(users instanceof List){
            users.add(0,liubei);
            users.add(1,guanyu);
            users.add(zhangfei);
            users.add(zhangfei1);
        }
        List<User> newUsers = new ArrayList<>();
        users.stream().collect(Collectors.groupingBy(a -> a.getName())).forEach((b,c) -> {
            System.out.println(c);
            c.stream().reduce((d,e) -> new User(d.getName(),d.getAge()+e.getAge())).ifPresent(newUsers::add);
        });
        //System.out.println(newUsers.stream().sorted((x,y) -> x.getAge()-y.getAge()).collect(Collectors.toList()));
        //Map<String,List<User>> treeMap = users.stream().collect(Collectors.groupingBy(a -> a.getName()));
        Map<String,List<User>> treeMap = new Hashtable<>();
        users.forEach((User user) -> {
            List<User> users1 = treeMap.get(user.getName());
            if(users1 == null){
                users1 = new Vector<User>();
            }
            users1.add(user);
            treeMap.put(user.getName(),users1);
        });

        User u1 = new User("xiao1", 21);
        User u2 = new User("xiao2", 22);
        User u3 = new User("xiao3", 23);
        User u4 = new User("xiao4", 24);
        User u5 = new User("xiao1", 21);
        User u6 = new User("xiao2", 22);
        User u7 = new User("xiao5", 25);
        User[] userarr = {u1,u2,u3,u4};
        Set<User> set1 = new HashSet<>(Arrays.asList(userarr));
        Set<User> set2 = new HashSet<>();
        set2.add(u5);
        set2.add(u6);
        set2.add(u7);
        //交集
        Map<String, User> setmap1 = set1.stream().collect(Collectors.toMap(u -> u.getName() + "_$$_" + u.getAge(), Function.identity()));
        List<User> setlist1 = set2.stream().filter(u -> {
            if (setmap1.containsKey(u.getName() + "_$$_" + u.getAge())) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));

        Map<String,Object> map = new LinkedHashMap<>();
        for(User user : users){
            if(map.containsKey(user.getName())){
                User newUser = new User();
                BeanUtils.copyProperties(user,newUser);
                newUser.setAge(((User)map.get(newUser.getName())).getAge()+newUser.getAge());
                map.put(newUser.getName(),newUser);
            }else{
                map.put(user.getName(),user);
            }
        }
        List<User> newUsers2 = new ArrayList<>();
        map.forEach((k,v) -> newUsers2.add((User)v));
        System.out.println(newUsers2.toString());

        List<User> newUsers3 = users.stream().filter(x->!"zhangfei".equals(x.getName())).map(x->new User(x.getName(),x.getAge()+5)).distinct().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(newUsers3);

        User ur = users.stream().filter(x->!"zhangfei".equals(x.getName())).findFirst().orElse(null);
        User ur2 =new User();
        users.stream().filter(x->!"zhangfei".equals(x.getName())).findAny().ifPresent(x -> BeanUtils.copyProperties(x,ur2));
        System.out.println(ur);
        System.out.println(ur2);

        int[] arr = {1,2,3,4,5};
        System.out.println(Arrays.stream(arr).reduce(0,Integer::sum));

        //递归
        FatherAndSon fas0 = new FatherAndSon(0,"xingming",-1);
        FatherAndSon fas1 = new FatherAndSon(1,"zhangsan1",0);
        FatherAndSon fas11 = new FatherAndSon(11,"zhangsan11",1);
        FatherAndSon fas111 = new FatherAndSon(111,"zhangsan111",11);
        FatherAndSon fas12 = new FatherAndSon(12,"zhangsan12",1);
        FatherAndSon fas2 = new FatherAndSon(2,"lisi2",0);
        FatherAndSon fas21 = new FatherAndSon(21,"lisi21",2);
        FatherAndSon fas3 = new FatherAndSon(3,"wangwu3",0);
        FatherAndSon[] fasarr = new FatherAndSon[]{fas0,fas1,fas11,fas111,fas12,fas2,fas21,fas3};
        List<FatherAndSon> faslist = Arrays.asList(fasarr);
        faslist.sort((x,y) -> Integer.compare(x.getId(),y.getId()));
        Map<Integer, List<FatherAndSon>> collect = faslist.stream().collect(Collectors.groupingBy(x -> x.getPid()));
        List<FatherAndSon> fatherAndSons = collect.get(-1);
        //1、建立树形结构
        //2、递归，建立子树形结构
        List<FatherAndSon> newlist = new ArrayList<>();
        fatherAndSons.stream().forEach(fas -> {
            //fas = getDiGuiData(fas,faslist);
            fas = getDiGuiData(fas,collect);
            newlist.add(fas);
        });
        System.out.println(newlist);

        FatherAndSon aa = faslist.stream().reduce((x, y) -> new FatherAndSon(x.getId() + y.getId(),x.getName()+","+y.getName(),x.getPid()+y.getPid())).get();
        Integer bb = faslist.stream().map(x -> x.getId()).reduce(0,(a,b) -> a+b);
        System.out.println(aa);
        System.out.println(bb);

        MyFunctionInterface myFunctionInterface = new MyFunctionInterfaceImpl();
        myFunctionInterface.method1();
        myFunctionInterface.method2("default方法");
    }
    private FatherAndSon getDiGuiData(FatherAndSon fas,Map<Integer, List<FatherAndSon>> collect) {
        List<FatherAndSon> newlist = new ArrayList<>();
        if(collect.get(fas.getId()) != null && !collect.get(fas.getId()).isEmpty()){
            collect.get(fas.getId()).forEach((FatherAndSon fatherAndSon) -> {
                newlist.add(getDiGuiData(fatherAndSon,collect));
            });
        }
        fas.setFass(newlist);
        return fas;
    }
    private FatherAndSon getDiGuiData(FatherAndSon fas,List<FatherAndSon> faslist) {
        List<FatherAndSon> newlist = new ArrayList<>();
        faslist.stream().forEach((FatherAndSon fatherAndSon) -> {
            if(fas.getId() == fatherAndSon.getPid()){
                newlist.add(getDiGuiData(fatherAndSon,faslist));
            }
        });
        fas.setFass(newlist);
        return fas;
    }

    @FunctionalInterface//函数式编程
     interface MyFunctionInterface<T>{

        public static final String a = "常量";

        void method1();

        default void method2(T t){
            System.out.println(t);
        }

        static void method3(){
            System.out.println("method3");
        }
    }
    class MyFunctionInterfaceImpl implements MyFunctionInterface{
        @Override
        public void method1() {
            System.out.println("method1方法");
        }
    }
    abstract class MyAbstractClass{
        private String b = "成员变量";

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        abstract void method3();
    }

    @Test
    public void thread(){
        class MyThread extends Thread{
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"\t一边看电视");
            }
        }
        MyThread myThread = new MyThread();
        myThread.start();//线程就绪，等CPU调用
        class MyRunable implements Runnable{
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"\t一边玩游戏");
            }
        }
        Thread thread = new Thread(new MyRunable());
        thread.start();//线程就绪，等CPU调用
        System.out.println(Thread.currentThread().getName()+"\t一边看书");
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t一边听音乐");
        }).start();
        new Thread(new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Thread.currentThread().getName()+"\t一边打羽毛球";
            }
        })).start();
    }


    @Test
    public void LocalDateTimeDemo(){
        System.out.println(System.currentTimeMillis());
        //localDateTime localDate localTime
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        System.out.println(localDateTime);
        System.out.println(localDate);
        System.out.println(localTime);
        LocalDateTime localDateTime2 = LocalDateTime.of(localDate,localTime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime3 = LocalDateTime.parse("2021-08-24 23:01:58",dateTimeFormatter);
        System.out.println(localDateTime3);
        String localDateTime3str = localDateTime3.format(dateTimeFormatter);
        System.out.println(localDateTime3str);
        LocalDateTime localDateTime4 = localDateTime.plusMonths(2);
        System.out.println(localDateTime4);
        LocalDateTime localDateTime5 = localDateTime.plusMonths(-2);
        System.out.println(localDateTime5);
        System.out.println(localDateTime5.getYear());
        System.out.println(localDateTime5.getMonthValue());
        System.out.println(localDateTime5.getDayOfMonth());

        //date to localDateTime
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime6 = LocalDateTime.ofInstant(instant, zoneId);
        System.out.println(localDateTime6);

        //localDateTime to date
        LocalDateTime localDateTime7 = LocalDateTime.now();
        ZoneId zoneId2 = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime7.atZone(zoneId2);
        Instant instant2 = zonedDateTime.toInstant();
        Date date2 = Date.from(instant2);
        System.out.println(date2);
    }


    /**
     * 字节流
     */
    @Test
    public void readWrite1(){
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream("F:\\input\\1.txt");
            fileOutputStream = new FileOutputStream("F:\\output\\2.txt");
            byte[] bytes = new byte[1024];
            int len;
            while((len = fileInputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes);
            }
            fileOutputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 字符流
     */
    @Test
    public void readWrite2(){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileReader = new FileReader("F:\\input\\11.txt");
            bufferedReader = new BufferedReader(fileReader);
            fileWriter = new FileWriter("F:\\output\\22.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            //第一种方式读取
            /*char[] chars = new char[1024];
            int len;
            while((len = fileReader.read(chars)) != -1){
                fileWriter.write(chars);
            }*/
            //第二种方式读取
            String line;
            while((line=bufferedReader.readLine()) != null){
                bufferedWriter.write(line);
                //读取一行后换行输出
                bufferedWriter.newLine();
            }
            bufferedReader.close();
            fileReader.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    @Test
    public void asyncThread(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Map<String,Object>> list = new ArrayList<>();
        List<Future> futures = new ArrayList<>();
        for(int i=0;i<10;i++){
            int ii = i;
            Map<String, Object> hashMap = new HashMap<>();
            Future future = executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + LocalDateTime.now());
                hashMap.put("key" + ii, Integer.valueOf(ii));
                list.add(hashMap);
            });
            futures.add(future);
        }
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        list.forEach(map -> System.out.println(Thread.currentThread().getName()+":"+map));
    }

    @Test
    public void collectionTest(){
        Boy boy1 = new Boy(1,"张无忌","1,2");
        Boy boy2 = new Boy(2,"令狐冲","3,4");
        Boy boy3 = new Boy(3,"杨过","5");
        Girl girl = new Girl(1,"赵敏","1");
        Girl gir2 = new Girl(2,"周芷若","1");
        Girl gir3 = new Girl(3,"任盈盈","2");
        Girl gir4 = new Girl(4,"岳灵珊","2");
        Girl gir5 = new Girl(5,"小龙女","3");
        //测试并集-1
        List<Boy> boys1 = new ArrayList<Boy>() {
            {
                add(boy1);
                add(boy2);
                add(boy3);
            }
        };
        List<Boy> boys2 = new ArrayList<>();
        boys2.add(boy1);
        boys2.add(boy3);
        Map<String,Boy> map = new LinkedHashMap<>();
        for(Boy boy : boys1){
            String key = boy.getId()+"_$$_"+boy.getName()+"_$$_"+boy.getGirls();
            map.put(key,boy);
        }
        for(Boy boy : boys2){
            String key = boy.getId()+"_$$_"+boy.getName()+"_$$_"+boy.getGirls();
            if(Objects.isNull(map.get(key))){
                map.put(key,boy);
            }
        }
        List<Boy> boys3 = new ArrayList<>();
        for(Map.Entry<String,Boy> entry : map.entrySet()){
            boys3.add(entry.getValue());
        }
        System.out.println(boys3);
        //测试并集-2
        List<Boy> boys5 = new ArrayList<>();
        List<Boy> boys4 = new ArrayList<>();
        boys4.addAll(boys1);
        boys4.addAll(boys2);
        Set<String> set = new HashSet<>();
        for(Boy boy : boys4){
            String key = boy.getId()+"_$$_"+boy.getName()+"_$$_"+boy.getGirls();
            if(!set.contains(key)){
                set.add(key);
                boys5.add(boy);
            }
        }
        System.out.println(boys5);
        //测试并集-3
        List<Boy> boys7 = new ArrayList<>();
        List<Boy> boys6 = new ArrayList<>();
        boys6.addAll(boys1);
        boys6.addAll(boys2);
        Map<String, Boy> collect = boys6.stream().collect(Collectors.toMap(boy -> boy.getId() + "_$$_" + boy.getName() + "_$$_" + boy.getGirls(), Function.identity(), (v1, v2) -> v1, LinkedHashMap::new));
        collect.forEach((k,v) -> boys7.add(v));
        System.out.println(boys7);
        //测试并集-4
        List<Boy> boys9 = new ArrayList<>();
        List<Boy> boys8 = new ArrayList<>();
        boys8.addAll(boys1);
        boys8.addAll(boys2);
        Map<String, List<Boy>> collect1 = boys8.parallelStream().collect(Collectors.groupingBy(boy -> boy.getId() + "_$$_" + boy.getName() + "_$$_" + boy.getGirls(), LinkedHashMap::new, Collectors.toList()));
        collect1.forEach((k,v) -> boys9.add(v.get(0)));
        System.out.println(boys9);

        //测试交集
        List<Boy> boys10 = new ArrayList<>();
        boys10.addAll(boys1);
        boys10.addAll(boys2);
        List<Boy> collect2 = boys10.stream().collect(Collectors.groupingBy(boy -> boy.getId() + "_$$_" + boy.getName() + "_$$_" + boy.getGirls(), LinkedHashMap::new, Collectors.toList())).entrySet().stream().filter(entry -> entry.getValue().size() > 1).map(entry -> entry.getValue().get(0)).collect(Collectors.toList());
        System.out.println(collect2);

        //获取毫秒数
        long time1 = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long time2 = new Date().getTime();
        long time3 = System.currentTimeMillis();
        System.out.println(time1);
        System.out.println(time2);
        System.out.println(time3);
    }

}
