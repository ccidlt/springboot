package com.ds;

import com.ds.entity.FatherAndSon;
import com.ds.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringbootApplicationTests {

    /**
     * lambda表达式
     */
    @Test
    public void lambda(){
        //forEach
        List<Integer> list = new ArrayList<Integer>(){{
           add(5);add(2);add(3);add(4);add(1);
        }};
        list.forEach(System.out :: println);
        HashMap<String, Integer> map = new HashMap<String, Integer>(){{
            put("a",5);put("b",2);put("c",3);put("d",4);put("e",1);
        }};
        map.forEach((k,v) -> System.out.println(k+"="+v));
        User user1 = new User("1",20);
        User user2 = new User("2",21);
        User user3 = new User("3",22);
        User user4= new User("4",23);
        User user5 = new User("5",24);
        List<User> userlist = new ArrayList<>();
        userlist.add(user1);
        userlist.add(user2);
        userlist.add(user3);
        userlist.add(user4);
        userlist.add(user5);
        userlist.forEach(user -> System.out.println("name="+user.getName()+"，age="+user.getAge()));
        //sort
        list.sort((v1,v2) -> v1.compareTo(v2));
        System.out.println("sort1"+list);
        list.sort((v1,v2) -> v1-v2);
        System.out.println("sort2:"+list);
        userlist.sort(Comparator.comparing(User::getName,Comparator.reverseOrder()).thenComparing(User::getAge));
        System.out.println("sort3:"+list);
        //filter
        List<Integer> filter1 = list.stream().filter(v -> v >= 3).collect(Collectors.toList());
        System.out.println("filter:"+filter1);
        //map
        List<Integer> map1 = list.stream().map(v -> v+10).collect(Collectors.toList());
        System.out.println("map1:"+list);
        List<String> map2 = userlist.stream().map(User::getName).collect(Collectors.toList());
        System.out.println("map2:"+userlist);
        List<User> map3 = userlist.stream().map(user -> {
            user.setAge(user.getAge()+10);
            return user;
        }).collect(Collectors.toList());
        System.out.println("map3:"+userlist);
        //mapToInt
        int mapToInt = userlist.stream().mapToInt(user -> Integer.parseInt(user.getName())).sum();
        System.out.println("mapToInt:"+mapToInt);
        //group
        Map<String, List<User>> group1 =
                userlist.stream().collect(Collectors.groupingBy(User::getName, LinkedHashMap::new, Collectors.toList()));
        Map<String, IntSummaryStatistics> group2 =
                userlist.stream().collect(Collectors.groupingBy(User::getName, LinkedHashMap::new, Collectors.summarizingInt(User::getAge)));
        //toMap
        Map<String, User> toMap =
                userlist.stream().collect(Collectors.toMap(User::getName, Function.identity(), (v1, v2) -> v1, LinkedHashMap::new));
        System.out.println("toMap:"+toMap);
        //reduce
        Optional<User> option = userlist.stream().reduce((v1, v2) -> new User("姓名", v1.getAge() + v2.getAge()));
        System.out.println("reduce:"+option.orElse(null));
        //optional
        List<User> users = Optional.ofNullable(userlist).orElse(null);
        String username = Optional.ofNullable(user1).map(User::getName).orElse(null);
    }

    /**
     * 交集并集差集
     */
    @Test
    public void union(){
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");
        set2.add("c");
        set2.add("d");
        set2.add("e");
        //交集
        set1.retainAll(set2);
        System.out.println("交集是 " + set1);
        //并集
        set1.addAll(set2);
        System.out.println("并集是" + set1);

        //差集
        set1.removeAll(set2);
        System.out.println("差集是 " + set1);

        User user1 = new User("张三",20);
        User user2 = new User("李四",21);
        User user3 = new User("王五",22);
        List<User> list1 = new ArrayList<User>(){{
            add(user1);add(user2);
        }};
        List<User> list2 = new ArrayList<User>(){{
            add(user1);add(user3);
        }};
        //对象集合交集
        List<User> list3 = list1.stream().filter(item ->list2.stream()
                .map(e -> e.getName()+"_$$_"+e.getAge()).collect(Collectors.toList()).contains(item.getName()+"_$$_"+item.getAge()))
                .collect(Collectors.toList());
        System.out.println("对象集合交集是 " + list3);
        //对象集合差集
        List<User> list4 = list1.stream().filter(item ->!list2.stream()
                .map(e -> e.getName()+"_$$_"+e.getAge()).collect(Collectors.toList()).contains(item.getName()+"_$$_"+item.getAge()))
                .collect(Collectors.toList());
        System.out.println("对象集合差集是 " + list4);
        //对象集合并集
        list2.addAll(list4);
        System.out.println("对象集合并集是 " + list2);
    }

    /**
     * 日期操作
     */
    @Test
    public void date() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2022-03-01");
        Date date2 = sdf.parse("2022-03-10");
        //相差天数
        long days = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        System.out.println("相差天数:"+days);
        //计算10天前日期
        Calendar instance = Calendar.getInstance();
        instance.setTime(date2);
        instance.add(Calendar.DAY_OF_YEAR, -10);
        System.out.println("10天前:"+sdf.format(instance.getTime()));
    }

    /**
     * 数字操作
     */
    @Test
    public void number(){
        //保留2位小数
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        String format1 = numberFormat.format(19.656);
        System.out.println(format1);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String format2 = decimalFormat.format(19.656);
        System.out.println(format2);
        BigDecimal bigDecimal = new BigDecimal("19.656");
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        double format3 = bigDecimal.doubleValue();
        System.out.println(format3);
        //正则匹配
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("22bb23");
        boolean matches = m.matches();//返回false,因为bb不能被\d+匹配,导致整个字符串匹配未成功.
        if(matches){
            String group = m.group();
        }
    }

    /**
     * 递归
     */
    @Test
    public void recursion(){
        FatherAndSon fas1 = new FatherAndSon(1, "张三", 0);
        FatherAndSon fas2 = new FatherAndSon(2, "李四", 0);
        FatherAndSon fas3 = new FatherAndSon(3, "王五", 0);
        FatherAndSon fas4 = new FatherAndSon(4, "赵六", 2);
        FatherAndSon fas5 = new FatherAndSon(5, "钱七", 2);
        List<FatherAndSon> fass = new ArrayList<>();
        fass.add(fas1);
        fass.add(fas2);
        fass.add(fas3);
        fass.add(fas4);
        fass.add(fas5);
        List<FatherAndSon> first = fass.stream().filter(item -> item.getPid() == 0).collect(Collectors.toList());
        for(FatherAndSon fas : first){
            fas.setFass(recursion2(fass, fas.getId()));
        }
        System.out.println(first);
    }
    public List<FatherAndSon> recursion2(List<FatherAndSon> fass, int id) {
        List<FatherAndSon> newfass = new ArrayList<>();
        for(FatherAndSon fas : fass){
            if(fas.getPid() == id){
                fas.setFass(recursion2(fass, fas.getId()));
                newfass.add(fas);
            }
        }
        return newfass;
    }

}
