package com.ds;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.*;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.ds.config.async.ReentrantLockOperate;
import com.ds.config.webmvc.MyEvent;
import com.ds.controller.setting.BoyController;
import com.ds.entity.Boy;
import com.ds.entity.FatherAndSon;
import com.ds.entity.Person;
import com.ds.entity.User;
import com.ds.service.PersonFactory;
import com.ds.service.impl.BoyFeignServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
    public void lambda() {
        //forEach
        List<Integer> list = new ArrayList<Integer>() {{
            add(5);
            add(2);
            add(3);
            add(4);
            add(1);
        }};
        list.forEach(System.out::println);
        HashMap<String, Integer> map = new HashMap<String, Integer>() {{
            put("a", 5);
            put("b", 2);
            put("c", 3);
            put("d", 4);
            put("e", 1);
        }};
        map.forEach((k, v) -> System.out.println(k + "=" + v));
        User user1 = new User(1, "root", "root", "超级用户");
        User user2 = new User(2, "user", "user", "普通用户");
        User user3 = new User(3, "vip", "vip", "VIP用户");
        List<User> userlist = new ArrayList<>();
        userlist.add(user1);
        userlist.add(user2);
        userlist.add(user3);
        List<User> userlist2 = new ArrayList<>();
        userlist2.add(user1);
        userlist2.add(user2);
        userlist.forEach(user -> System.out.println("account=" + user.getAccount() + "，age=" + user.getPassword()));
        //sort
        list.sort((v1, v2) -> v1.compareTo(v2));
        System.out.println("sort1" + list);
        list.sort((v1, v2) -> v1 - v2);
        System.out.println("sort2:" + list);
        userlist.sort(Comparator.comparing(User::getAccount, Comparator.reverseOrder()).thenComparing(User::getUsername));
        System.out.println("sort3:" + list);
        //filter
        List<Integer> filter1 = list.stream().filter(v -> v >= 3).collect(Collectors.toList());
        System.out.println("filter:" + filter1);
        //allMatch、anyMatch、nonMatch
        boolean b = list.stream().anyMatch(v -> v >= 3);
        System.out.println("anyMatch:" + b);
        //map
        List<Integer> map1 = list.stream().map(v -> v + 10).collect(Collectors.toList());
        System.out.println("map1:" + list);
        List<String> map2 = userlist.stream().map(User::getAccount).collect(Collectors.toList());
        System.out.println("map2:" + userlist);
        List<User> map3 = userlist.stream().map(user -> {
            user.setId(user.getId() + 10);
            return user;
        }).collect(Collectors.toList());
        System.out.println("map3:" + userlist);
        //mapToInt
        int mapToInt = userlist.stream().mapToInt(User::getId).sum();
        System.out.println("mapToInt:" + mapToInt);
        //group
        Map<String, List<User>> group1 =
                userlist.stream().collect(Collectors.groupingBy(User::getAccount, LinkedHashMap::new, Collectors.toList()));
        Map<String, IntSummaryStatistics> group2 =
                userlist.stream().collect(Collectors.groupingBy(User::getAccount, LinkedHashMap::new, Collectors.summarizingInt(User::getId)));
        Map<String, User> group3 =
                userlist.stream().collect(Collectors.groupingBy(User::getAccount, LinkedHashMap::new, Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0))));
        //toMap
        Map<String, User> toMap =
                userlist.stream().collect(Collectors.toMap(User::getAccount, Function.identity(), (v1, v2) -> v1, LinkedHashMap::new));
        System.out.println("toMap:" + toMap);
        //reduce
        Optional<User> option = userlist.stream().reduce((v1, v2) -> new User(v1.getId() + v2.getId(), "", "", ""));
        System.out.println("reduce:" + option.orElse(null));
        String userNames = userlist.stream().map(User::getUsername).reduce("", (v1, v2) -> v1 + "," + v2);
        //optional
        List<User> users = Optional.ofNullable(userlist).orElse(null);
        String username = Optional.ofNullable(user1).map(User::getAccount).orElse(null);
        //distinct
        List<String> distinct1 = userlist.stream().map(User::getUsername).distinct().collect(Collectors.toList());
        //去重
        List<User> distinct2 = userlist.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparingInt(User::getId))
                ), ArrayList::new));
        //join
        String join = userlist.stream().map(User::getUsername).collect(Collectors.joining(","));
        //max、min
        User max1 = userlist.stream().collect(Collectors.maxBy(Comparator.comparing(User::getId))).orElse(null);
        User max2 = userlist.stream().max(Comparator.comparing(User::getId)).orElse(null);
        //sum
        long sum1 = userlist.stream().mapToInt(User::getId).sum();
        long sum2 = userlist.stream().collect(Collectors.summingInt(User::getId));
        //count
        long count1 = userlist.stream().count();
        long count2 = userlist.stream().collect(Collectors.counting());
        //两集合交集
        List<User> userChaList = userlist.stream()
                .filter(a -> userlist2.stream().map(aa -> aa.getAccount() + "," + aa.getUsername())
                        .collect(Collectors.toList()).contains(a.getAccount() + "," + a.getUsername()))
                .collect(Collectors.toList());

    }

    /**
     * 交集并集差集
     */
    @Test
    public void union() {
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

        User user1 = new User(1, "root", "root", "超级用户");
        User user2 = new User(2, "user", "user", "普通用户");
        User user3 = new User(3, "vip", "vip", "VIP用户");
        List<User> list1 = new ArrayList<User>() {{
            add(user1);
            add(user2);
        }};
        List<User> list2 = new ArrayList<User>() {{
            add(user1);
            add(user3);
        }};
        //对象集合交集
        List<User> list3 = list1.stream().filter(item -> list2.stream()
                .map(e -> e.getAccount() + "_$$_" + e.getUsername()).collect(Collectors.toList()).contains(item.getAccount() + "_$$_" + item.getUsername()))
                .collect(Collectors.toList());
        System.out.println("对象集合交集是 " + list3);
        //对象集合差集
        List<User> list4 = list1.stream().filter(item -> !list2.stream()
                .map(e -> e.getAccount() + "_$$_" + e.getUsername()).collect(Collectors.toList()).contains(item.getAccount() + "_$$_" + item.getUsername()))
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
        System.out.println("相差天数:" + days);
        //计算10天前日期
        Calendar instance = Calendar.getInstance();
        instance.setTime(date2);
        instance.add(Calendar.DAY_OF_YEAR, -10);
        System.out.println("10天前:" + sdf.format(instance.getTime()));
    }

    /**
     * 数字操作
     */
    @Test
    public void number() {
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
        if (matches) {
            String group = m.group();
        }
    }

    /**
     * 递归
     */
    @Test
    public void recursion() {
        FatherAndSon fas1 = new FatherAndSon(1, "张三", 0);
        FatherAndSon fas2 = new FatherAndSon(2, "李四", 0);
        FatherAndSon fas3 = new FatherAndSon(3, "王五", 0);
        FatherAndSon fas4 = new FatherAndSon(4, "赵六", 2);
        FatherAndSon fas5 = new FatherAndSon(5, "钱七", 2);
        FatherAndSon fas6 = new FatherAndSon(6, "武汉", 3);
        FatherAndSon fas7 = new FatherAndSon(7, "南京", 5);
        List<FatherAndSon> fass = new ArrayList<>();
        fass.add(fas1);
        fass.add(fas2);
        fass.add(fas3);
        fass.add(fas4);
        fass.add(fas5);
        fass.add(fas6);
        fass.add(fas7);
        List<FatherAndSon> first = fass.stream().filter(item -> item.getPid() == 0).collect(Collectors.toList());
        for (FatherAndSon fas : first) {
            fas.setFass(recursionDown(fass, fas.getId()));
        }
        System.out.println(first);

        List<Integer> ids = new ArrayList<>();
        recursionDown2(fass, 2, ids);
        System.out.println(ids);

        List<Integer> ids2 = new ArrayList<>();
        ids2.add(7);
        recursionUp(fass, 5, ids2);
        System.out.println(StringUtils.join(ids2.toArray(), ","));
    }

    public List<FatherAndSon> recursionDown(List<FatherAndSon> fass, int id) {
        List<FatherAndSon> newfass = new ArrayList<>();
        for (FatherAndSon fas : fass) {
            if (fas.getPid() == id) {
                fas.setFass(recursionDown(fass, fas.getId()));
                newfass.add(fas);
            }
        }
        return newfass;
    }

    public void recursionDown2(List<FatherAndSon> fass, int id, List<Integer> ids) {
        for (FatherAndSon fas : fass) {
            if (fas.getPid() == id) {
                ids.add(fas.getId());
                recursionDown2(fass, fas.getId(), ids);
            }
        }
    }

    public void recursionUp(List<FatherAndSon> fass, int pid, List<Integer> ids) {
        for (FatherAndSon fas : fass) {
            if (fas.getId() == pid) {
                ids.add(fas.getId());
                recursionUp(fass, fas.getPid(), ids);
            }
        }
    }

    @Autowired
    ReentrantLockOperate reentrantLockOperate;

    @Test
    public void reentrantLockTest() throws Exception {
        final ArrayList<Future<Boolean>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //阻塞调用返回值
            final Future<Boolean> hel = reentrantLockOperate.reentrantLockTest();
            list.add(hel);
            if (list.size() == 5) {
                while (true) {
                    for (int j = list.size() - 1; j >= 0; j--) {
                        final Future<Boolean> aBoolean = list.get(j);
                        if (aBoolean.isDone()) {
                            list.remove(j);
                        }
                    }
                    if (list.size() == 0) {
                        break;
                    }
                }
            }
        }
    }

    @Test
    public void synchronizedText1() throws Exception {
        for (int i = 0; i < 10; i++) {
            reentrantLockOperate.synchronizedText();
        }
        Thread.sleep(60 * 1000);
    }

    @Test
    public void hutool(){
        //简单的uuid、雪花算法id、md5加密、随机数
        System.out.println(IdUtil.fastSimpleUUID());
        System.out.println(IdUtil.randomUUID());
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println(id);
        long snowflakeNextId = IdUtil.getSnowflakeNextId();
        System.out.println(snowflakeNextId);
        System.out.println(SecureUtil.md5("123456"));
        System.out.println(RandomUtil.randomNumbers(5));
        //类型转换工具类-Convert
        int a = 1;
        String aStr = Convert.toStr(a);
        String[] b = { "1", "2", "3", "4" };
        Integer[] intArray = Convert.toIntArray(b);
        String[] strArr = {"a", "b", "c", "d"};
        List<String> strList = Convert.toList(String.class, strArr);
        Object[] aArr = {"a", "你", "好", "", 1};
        List<?> list = Convert.convert(List.class, aArr);
        list = Convert.toList(aArr);
        int[] nums = {1,2,3,4,5,6};
        Integer[] integers = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        int[] ints = Arrays.stream(integers).mapToInt(Integer::intValue).toArray();
        //字符串工具
        System.out.println(StrUtil.isNotEmpty("abc"));
        System.out.println(StrUtil.equals("123","abc"));
        System.out.println(StrUtil.contains("abc","ab"));
        System.out.println(StrUtil.nullToEmpty(null));
        System.out.println(StrUtil.subSuf("abc",1));//截取
        System.out.println(StrUtil.sub("abc",0,"abc".length()));//截取
        System.out.println(StrUtil.format("{}+{}=2","1","1"));//格式化
        System.out.println(StrUtil.replace("{}+{}=2","{}","1"));//替换
        //对象工具
        System.out.println(ObjectUtil.isNotNull(new ArrayList<String>()));
        System.out.println(ObjectUtil.isNotEmpty(new HashMap<String,Object>()));
        System.out.println(ObjectUtil.defaultIfNull("abc",""));
        //对象转换工具 BeanUtil
        Boy boy = BeanUtil.copyProperties(new Boy(), Boy.class);
        System.out.println(boy);
        Map<String, Object> boyMap = BeanUtil.beanToMap(boy);
        System.out.println(boyMap);
        Map<String, Object> boyMap2 = BeanUtil.beanToMap(boyMap);
        System.out.println(boyMap2);
        List<Boy> boys = BeanUtil.copyToList(new ArrayList<Boy>(), Boy.class);
        System.out.println(boys);
        //反射
        Method[] methods = ReflectUtil.getMethods(BoyController.class);
        Method method = ReflectUtil.getMethod(BoyController.class, "getBoys");
        System.out.println(method.getName());
        //数字工具
        System.out.println(NumberUtil.isNumber("1.2"));
        System.out.println(NumberUtil.add(1, 1.2, 1.3));
        System.out.println(NumberUtil.sub(5, 1.1, 1.2));
        System.out.println(NumberUtil.mul(10, 1.12));
        System.out.println(NumberUtil.div(1.12, 10, 2));
        System.out.println(NumberUtil.roundStr(1.125, 2));
        System.out.println(NumberUtil.decimalFormat(",###", 299792458));//299,792,458
        int[] array = NumberUtil.generateRandomNumber(0, 10, 8);// 生成随机数,用int类型数组承载
        System.out.println(StrUtil.join(",",array));
        //时间工具
        System.out.println(DateUtil.date());
        System.out.println(DateUtil.parse("2019-09-17", "yyyy-MM-dd"));
        System.out.println(DateUtil.format(DateUtil.date(), "yyyy-MM-dd"));
        System.out.println(DateUtil.year(DateUtil.date()));
        System.out.println(DateUtil.month(DateUtil.date())+1);
        System.out.println(DateUtil.dayOfMonth(DateUtil.date()));
        Date date1 = DateUtil.parse("2019-09-20 17:35:35");
        Date date2 = DateUtil.parse("2019-09-17 14:35:35");
        System.out.println(DateUtil.between(date1, date2, DateUnit.DAY));//3
        //Http工具
        String get = HttpUtil.get("https://www.baidu.com");
        JSONObject requestJson = new JSONObject();
        requestJson.put("name", "zhang3");
        requestJson.put("age", 10);
        String requestParam = requestJson.toJSONString();
        String post = HttpUtil.post("https://www.baidu.com", requestParam, 10 * 1000);
        //ServletUtil
        //Map<String, String> map = ServletUtil.getParamMap(request);
        //文件工具FileUtil
        //树工具TreeUtil
        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "11", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");
        System.out.println("treeList:"+treeList);

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("rid");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getWeight());
                    tree.setName(treeNode.getName());
                    // 扩展属性 ...
                    tree.putExtra("extraField", 666);
                    tree.putExtra("other", new Object());
                });

        //JWT
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("uid", IdUtil.randomUUID());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };
        String token = JWTUtil.createToken(map, "1234".getBytes());
        System.out.println(token);
        String rightToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEyMywiZXhwaXJlX3RpbWUiOjE2NzUyMzU1Njg3MDN9.QGCl5dXpi0bunuNBrZY2DCddvA2oZVieu3QF3KlklYM";
        final JWT jwt = JWTUtil.parseToken(rightToken);
        jwt.getHeader(JWTHeader.TYPE);
        jwt.getPayload("sub");
        System.out.println(jwt.getHeader(JWTHeader.TYPE));
        System.out.println(jwt.getPayload("uid"));
        System.out.println(jwt.getPayload("expire_time"));
        boolean verify = JWTUtil.verify(rightToken, "1234".getBytes());
        System.out.println(verify);
    }

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    private static final String PREFIXTAG = "test:";
    @Test
    public void redisTest(){
        //字符串
        redisTemplate.opsForValue().set(PREFIXTAG + "value:next", "a", 1L, TimeUnit.MINUTES);//text/value/test:value:next=abc
        System.out.println(redisTemplate.opsForValue().get(PREFIXTAG + "value:next"));
        redisTemplate.opsForValue().set(PREFIXTAG + "value", "b", 1L, TimeUnit.MINUTES);//text/test:value=abc
        System.out.println(redisTemplate.opsForValue().get(PREFIXTAG + "value"));
        redisTemplate.opsForValue().set("value", "c", 1L, TimeUnit.MINUTES);//value=abc
        System.out.println(redisTemplate.opsForValue().get("value"));
        System.out.println("=====================================");
        //哈希
        redisTemplate.opsForHash().put(PREFIXTAG + "hash", "hashkey1", "a");
        redisTemplate.opsForHash().put(PREFIXTAG + "hash", "hashkey2", "b");
        if(redisTemplate.getExpire(PREFIXTAG + "hash") != null){
            redisTemplate.expire(PREFIXTAG + "hash", 1L, TimeUnit.MINUTES);
        }
        System.out.println(redisTemplate.opsForHash().get(PREFIXTAG + "hash", "hashkey1"));
        System.out.println(redisTemplate.opsForHash().get(PREFIXTAG + "hash", "hashkey2"));
        System.out.println(redisTemplate.opsForHash().keys(PREFIXTAG + "hash"));
        System.out.println("=====================================");
        //列表
        redisTemplate.opsForList().leftPush(PREFIXTAG + "list", "a");
        redisTemplate.opsForList().leftPush(PREFIXTAG + "list", "b");
        redisTemplate.opsForList().leftPush(PREFIXTAG + "list", "c");
        if(redisTemplate.getExpire(PREFIXTAG + "list") != null){
            redisTemplate.expire(PREFIXTAG + "list", 1L, TimeUnit.MINUTES);
        }
        Long size = redisTemplate.opsForList().size(PREFIXTAG + "list");
        System.out.println(size);
        System.out.println(redisTemplate.opsForList().range(PREFIXTAG + "list",0,size-1));
        for(int i=0;i<size;i++){
            System.out.println(redisTemplate.opsForList().rightPop(PREFIXTAG + "list"));
        }
        System.out.println("=====================================");
        //集合
        redisTemplate.opsForSet().add(PREFIXTAG + "set", "a", "b", "c");
        if(redisTemplate.getExpire(PREFIXTAG + "set") != null){
            redisTemplate.expire(PREFIXTAG + "set", 1L, TimeUnit.MINUTES);
        }
        Set<String> members = redisTemplate.opsForSet().members(PREFIXTAG + "set");
        System.out.println(members);
        Iterator<String> iterator = members.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println("=====================================");
        //有序集合
        redisTemplate.opsForZSet().add(PREFIXTAG + "zset", "c", 0.3);
        redisTemplate.opsForZSet().add(PREFIXTAG + "zset", "a", 0.1);
        redisTemplate.opsForZSet().add(PREFIXTAG + "zset", "b", 0.2);
        if(redisTemplate.getExpire(PREFIXTAG + "zset") != null){
            redisTemplate.expire(PREFIXTAG + "zset", 1L, TimeUnit.MINUTES);
        }
        Long size2 = redisTemplate.opsForZSet().size(PREFIXTAG + "zset");
        System.out.println(size2);
        System.out.println(redisTemplate.opsForZSet().range(PREFIXTAG + "zset",0,size2-1));
        System.out.println(redisTemplate.opsForZSet().rangeByScore(PREFIXTAG + "zset", 0, 0.5));
        System.out.println("=====================================");
        //setIfAbsent
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(PREFIXTAG + "absent", "a", 1L, TimeUnit.MINUTES);
        System.out.println(ifAbsent+":::"+redisTemplate.opsForValue().get(PREFIXTAG + "absent"));
        Boolean ifAbsent2 = redisTemplate.opsForValue().setIfAbsent(PREFIXTAG + "absent", "b", 1L, TimeUnit.MINUTES);
        System.out.println(ifAbsent2+":::"+redisTemplate.opsForValue().get(PREFIXTAG + "absent"));
        redisTemplate.opsForValue().set(PREFIXTAG + "absent","c", 1L, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get(PREFIXTAG + "absent"));
    }

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;
    @Value("${kafka_topics}")
    private String kafkaTopics;
    @Test
    public void kafkaTest(){
        kafkaTemplate.send(kafkaTopics.split(",")[0], "abc");
    }

    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void eventListen(){
        applicationContext.publishEvent(new MyEvent("abc"));
    }

    @Resource(name = "tptExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Test
    public void atomicCountDown() {
        AtomicInteger mainab = new AtomicInteger(0);
        CountDownLatch maincdl = new CountDownLatch(1);
        CountDownLatch subcdl = new CountDownLatch(5);
        for (int i=1;i<=5;i++){
            int ii = i;
            threadPoolTaskExecutor.submit(()->{
                try {
                    maincdl.await();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
                log.info("sub{}========{}",ii,Thread.currentThread().getName());
                subcdl.countDown();
                mainab.incrementAndGet();
            });
        }
        log.info("main{}====ato{}===={}","1",mainab.get(),Thread.currentThread().getName());
        maincdl.countDown();
        try {
            subcdl.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        log.info("main{}====ato{}===={}","2",mainab.get(),Thread.currentThread().getName());
    }

    @Autowired
    private BoyFeignServiceImpl boyFeignServiceImpl;
    /**
     * 调用主服务支持全局事务提交、回滚，
     * 单独调用ds服务各自支持事务
     */
    @Test
    public void dsTransactional() {
        Boy boy1 = new Boy();
        boy1.setName("乔峰");
        Boy boy2 = new Boy();
        boy2.setName("段誉");
        Integer result = boyFeignServiceImpl.dsTransactional(boy1, boy2);
        log.info("result======{}",result);
    }

    @Test
    public void functionInterface() {
//        Function<String,Integer> function = new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) {
//                return Integer.valueOf(s);
//            }
//        };
//        Function<String,Integer> function = a->Integer.valueOf(a);
        Function<String,Integer> function = Integer::valueOf;
        System.out.println(function.apply("123"));
//        Function<String,Boy> function = Boy::new;
//        System.out.println(function.apply("张三"));
        PersonFactory<Person> personFactory = Person::new;
        System.out.println(personFactory.create("李四","13800000000"));
    }

}
