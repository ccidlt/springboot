package com.ds;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.ObjectId;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ds.config.async.ReentrantLockOperate;
import com.ds.config.webmvc.MyEvent;
import com.ds.controller.ElasticsearchController;
import com.ds.controller.setting.BoyController;
import com.ds.dao.BoyDao;
import com.ds.dao.GirlDao;
import com.ds.entity.*;
import com.ds.entity.dto.BoyDTO;
import com.ds.entity.dto.GirlDTO;
import com.ds.enums.ProcessLineEnum;
import com.ds.enums.ProcessNodeEnum;
import com.ds.service.*;
import com.ds.service.impl.BoyFeignServiceImpl;
import com.ds.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
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
    public void hutool() throws Throwable{
        //UUID
        //ObjectId（MongoDB）ObjectId是MongoDB数据库的一种唯一ID生成策略，是UUID version1的变种，Hutool针对此封装了cn.hutool.core.lang.ObjectId
        //Snowflake（Twitter）雪花算法
        //生成的UUID是带-的字符串，类似于：a5c8a5e8-df2b-4706-bea4-08d0939410e3
        String uuid = IdUtil.randomUUID();
        //生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();
        System.out.println("简化的UUID，去掉了横线:"+simpleUUID);
        System.out.println("获取随机UUID:"+uuid);

        //生成类似：5b9e306a4df4f8c54a39fb0c
        String objectId1 = ObjectId.next();
        //方法2：从Hutool-4.1.14开始提供
        String objectId2 = IdUtil.objectId();
        System.out.println(objectId1);
        System.out.println(objectId2);

        //参数1为终端ID
        //参数2为数据中心ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);//参数1为终端ID、参数2为数据中心ID
        long id = snowflake.nextId();
        System.out.println(id);

        //md5加密、随机数
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
        System.out.println(StrUtil.nullToDefault(null,""));
        System.out.println(StrUtil.subSuf("abc",1));//截取
        System.out.println(StrUtil.sub("abc",0,"abc".length()));//截取
        System.out.println(StrUtil.format("{}+{}=2","1","1"));//格式化
        System.out.println(StrUtil.replace("{}+{}=2","{}","1"));//替换
        System.out.println(StrUtil.join(",","a","b"));
        System.out.println(StrUtil.fillBefore("ab",'c',5));
        //ReUtil 正则工具
        System.out.println(ReUtil.replaceAll("ab0", "[0-9]", "c"));
        System.out.println(ReUtil.findAll("[0-9]", "ab0", 0));
        //数字工具
        System.out.println(NumberUtil.isNumber("1.2"));
        System.out.println(NumberUtil.add(1, 1.2, 1.3));
        System.out.println(NumberUtil.sub(5, 1.1, 1.2));
        System.out.println(NumberUtil.mul(10, 1.12));
        System.out.println(NumberUtil.div(1.12, 10, 2));
        System.out.println(NumberUtil.roundStr(1.125, 2));
        System.out.println(NumberUtil.decimalFormat(",###", 299792458));//299,792,458
        System.out.println(NumberUtil.decimalFormat("#.###", new BigDecimal("299792458.03456"), RoundingMode.HALF_UP));//299792458.035
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
        System.out.println(DateUtil.between(date1, date2, DateUnit.DAY));//3  计算日期时间之间的偏移量
        Date newDate = DateUtil.offset(date1, DateField.DAY_OF_MONTH, 2); //计算日期时间之间的偏移量
        Date beginOfDay = DateUtil.beginOfDay(date1);//获取某天的开始时间
        Date endOfDay = DateUtil.endOfDay(date1);//获取某天的结束时间
        Date beginOfMonth = DateUtil.beginOfMonth(date1);//获取某月的开始时间
        Date endOfMonth = DateUtil.endOfMonth(date1);//获取某月的结束时间
        //对象工具
        System.out.println(ObjectUtil.isNotNull(new ArrayList<String>()));
        System.out.println(ObjectUtil.isNotEmpty(new HashMap<String,Object>()));
        System.out.println(ObjectUtil.defaultIfNull(null,""));
        //对象转换工具 BeanUtil
        Boy boy = BeanUtil.copyProperties(new Boy(), Boy.class);
        System.out.println(boy);
        Map<String, Object> boyMap = BeanUtil.beanToMap(boy);
        System.out.println(boyMap);
        Boy boy2 = new Boy();
        BeanUtil.copyProperties(boy,boy2);
        System.out.println(boy);
        List<Boy> boys = BeanUtil.copyToList(new ArrayList<Boy>(), Boy.class);
        System.out.println(boys);
        //反射
        Method[] methods = ReflectUtil.getMethods(BoyController.class);
        Method method = ReflectUtil.getMethod(BoyController.class, "getBoys");
        System.out.println(method.getName());
        //获取定义在src/main/resources文件夹中的配置文件
        ClassPathResource resource = new ClassPathResource("application.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());
        System.out.println("/classPath:"+properties);
        //Http工具
        String get = HttpUtil.get("https://www.baidu.com");
        JSONObject requestJson = new JSONObject();
        requestJson.put("name", "zhang3");
        requestJson.put("age", 10);
        String requestParam = requestJson.toJSONString();
        String post = HttpUtil.post("https://www.baidu.com", requestParam, 10 * 1000);
        //获取指定类、方法、字段、构造器上的注解列表
        Annotation[] annotationList = AnnotationUtil.getAnnotations(BoyController.class, false);
        RequestMapping requestMapping = AnnotationUtil.getAnnotation(BoyController.class, RequestMapping.class);//获取指定类型注解
        Object annotationValue = AnnotationUtil.getAnnotationValue(BoyController.class, RequestMapping.class);//获取指定类型注解的值
        //MD5加密
        String str = "123456";
        String md5Str = SecureUtil.md5(str);
        System.out.println("secureUtil md5:"+md5Str);
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

        //练习
        //IdUtil
        String randomUUID = IdUtil.randomUUID();
        String objectId = IdUtil.objectId();
        Snowflake snowflakeObject = IdUtil.getSnowflake(0, 0);
        long snowflakeNextId = snowflakeObject.nextId();
        //ObjectUtil
        boolean notNull = ObjectUtil.isNull(null);
        boolean notEmpty = ObjectUtil.isNotEmpty(null);
        String defaultIfNull = ObjectUtil.defaultIfNull(null, "");
        boolean equal = ObjectUtil.equal("a", "b");
        boolean contains = ObjectUtil.contains("a", "b");
        //BeanUtil
        List<String> copyToList = BeanUtil.copyToList(new ArrayList<String>(), String.class);
        Boy boy11 = BeanUtil.copyProperties(new Boy(), Boy.class);
        Boy boy12 = new Boy();
        BeanUtil.copyProperties(new Boy(),boy12);
        Map<String, Object> beanToMap = BeanUtil.beanToMap(new Boy());
        Boy boy13 = BeanUtil.mapToBean(beanToMap, Boy.class, false, new CopyOptions());
        //ReflectUtil
        Boy boy21 = new Boy();
        Field[] fields = ReflectUtil.getFields(boy21.getClass());
        for (Field field : fields) {
            // 获取属性字段类型
            String canonicalName = field.getType().getCanonicalName();//java.lang.String
            // 获取字段值
            Object fieldValue = ReflectUtil.getFieldValue(boy21, field);
        }
        Method[] methodArr = ReflectUtil.getMethods(boy21.getClass());
        Method method_getId = ReflectUtil.getMethod(boy21.getClass(), "getId");
        Object invoke = ReflectUtil.invoke(boy21, method_getId);
        Class<? extends SpringbootApplicationTests> aClass = this.getClass();
        log.info("className：{}", aClass.getName());
        log.info("classSimpleName：{}", aClass.getSimpleName());
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            log.info("methodName：{}，methodAnnotations：{}", declaredMethod.getName(), StrUtil.join(",", declaredMethod.getDeclaredAnnotations()));
        }
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            log.info("fieldName：{}，fieldType：{}", declaredField.getName(), declaredField.getType());
        }
        //StrUtil
        boolean notEmpty21 = StrUtil.isNotEmpty(null);
        String nullToEmpty22 = StrUtil.nullToEmpty(null);
        boolean equals23 = StrUtil.equals("a", "b");
        boolean equalsAny24 = StrUtil.equalsAny("a", "b", "c");
        boolean contains25 = StrUtil.contains("ab", "a");
        boolean contains26 = StrUtil.containsAny("ab", "a", "b");
        boolean startWith27 = StrUtil.startWith("ab", "a");
        boolean endWith28 = StrUtil.endWith("ab", "b");
        String sub29 = StrUtil.sub("ab", 0, "ab".length()-1);
        String subSuf30 = StrUtil.subSuf("ab", 0);
        String format = StrUtil.format("{}{}", "a", "b");
        String replace = StrUtil.replace("abc", "c", "");
        String join = StrUtil.join(",", "a", "b", "c");
        String fillBefore = StrUtil.fillBefore("ab", 'c', 5);
        //ReUtil 正则工具
        String replaceAll = ReUtil.replaceAll("ab0", "[0-9]", "c");
        List<String> findAll = ReUtil.findAll("\\d", "3ab012", 0);
        //NumberUtil
        boolean isNumber31 = NumberUtil.isNumber("1");
        double add32 = NumberUtil.add(1, 2, 3).doubleValue();
        double sub33 = NumberUtil.sub(3, 2, 1).doubleValue();
        double mul34 = NumberUtil.mul(1, 2);
        double div35 = NumberUtil.div(1, 2, 2);
        String roundStr36 = NumberUtil.roundStr(div35, 2);
        String decimalFormat37 = NumberUtil.decimalFormat(",###", 299792458);//299,792,458
        String decimalFormat371 = NumberUtil.decimalFormat("#.###", 299792458.00);//299792458
        String decimalFormat372 = NumberUtil.decimalFormat("#.###",  new BigDecimal("299792458.0125"), RoundingMode.HALF_UP);//299792458.013
        String decimalFormat373 = NumberUtil.decimalFormat("###,###.###",  new BigDecimal("299792458.0125"), RoundingMode.HALF_UP);//299,792,458.013
        //DateUtil
        Date date = DateUtil.date();
        int year = DateUtil.year(date);
        int month = DateUtil.month(date)+1;
        int day = DateUtil.dayOfMonth(date);
        String format1 = DateUtil.format(date, "yyyy-MM-dd");
        Date parse = DateUtil.parse(format1, "yyyy-MM-dd");
        Date offset = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        long between = DateUtil.between(date, offset, DateUnit.DAY);
        Date beginOfMonth1 = DateUtil.beginOfMonth(date);
        Date endOfMonth1 = DateUtil.endOfMonth(date);
        //FileUtil 用spring的MultipartFile表单接收文件
        //ls 列出目录和文件
        File[] lsList = FileUtil.ls("F:/");
        for (File file : lsList) {
            System.out.println(file.getName());
        }
        //touch 创建文件，如果父目录不存在也自动创建
        File touch = FileUtil.touch("F:/XXX/XXX.txt");
        FileUtil.writeString("你我他", "F:/XXX/XXX.txt", CharsetUtil.UTF_8);
        System.out.println(touch.getPath());
        //mkdir 创建目录，会递归创建每层目录
        File mkdir = FileUtil.touch("F:/YYY/YYY.txt");
        System.out.println(mkdir.getPath());
        //del 删除文件或目录（递归删除，不判断是否为空），这个方法相当于Linux的delete命令
        FileUtil.del("F:/YYY");
        //copy 拷贝文件或目录
        byte[] bytes = FileUtil.readBytes("F:/XXX/XXX.txt");
        File file = FileUtil.writeBytes(bytes, "F:/YYY/YYY.txt");
        //ResourceUtil
        InputStream inputStream = ResourceUtil.getStream("docs/header");
        FileUtil.writeFromStream(inputStream, "F:/header.md");
        //HttpUtil
        /*String paramJson = "{\n" +
                "    \"leaveReason\":\"请假\",\n" +
                "    \"flowFormPersonDraftDTOList\":[\n" +
                "        {\n" +
                "            \"approvalPersonCode\":\"002\",\n" +
                "            \"approvalPersonName\":\"赵老师\",\n" +
                "            \"approvalRoleCode\":\"TEACHER\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"personId\":\"1\",\n" +
                "    \"personCode\":\"001\",\n" +
                "    \"personName\":\"小明\",\n" +
                "    \"roleCode\":\"STUDENT\"\n" +
                "}";
        String post1 = HttpUtil.post("http://localhost:8078/flow/saveDraft", paramJson, 60000);
        System.out.println(JSON.parseObject(post1, Result.class));*/
        /*HashMap<String, Object> paramMap = new HashMap<>();
        //文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
        paramMap.put("file", FileUtil.file("D:\\face.jpg"));
        String result= HttpUtil.post("https://www.baidu.com", paramMap);
        String fileUrl = "http://mirrors.sohu.com/centos/8.4.2105/isos/x86_64/CentOS-8.4.2105-x86_64-dvd1.iso";
        //将文件下载后保存在E盘，返回结果为下载文件大小
        long size = HttpUtil.downloadFile(fileUrl, FileUtil.file("e:/"));
        System.out.println("Download size: " + size);*/
        //TreeUtil
        TreeStructure ts1 = new TreeStructure(1L,0L,"1",1);
        TreeStructure ts11 = new TreeStructure(11L,1L,"11",11);
        TreeStructure ts111 = new TreeStructure(111L,11L,"111",111);
        TreeStructure ts112 = new TreeStructure(112L,11L,"112",112);
        TreeStructure ts12 = new TreeStructure(12L,1L,"12",12);
        TreeStructure ts121 = new TreeStructure(121L,12L,"121",121);
        TreeStructure ts2 = new TreeStructure(2L,0L,"2",2);
        List<TreeStructure> treeStructureList = ListUtil.of(ts1, ts11, ts111, ts112, ts12, ts121, ts2);
        List<TreeStructure> treeStructures = treeStructureList.stream().filter(ts -> ObjectUtil.equal(0L, ts.getPid())).collect(Collectors.toList());
        List<TreeStructure> tsList = JSON.parseArray(JSON.toJSONString(treeStructureList),TreeStructure.class);
        List<TreeStructure> rootList = JSON.parseArray(JSON.toJSONString(treeStructures),TreeStructure.class);
        rootList.forEach(root->{
            treeBuild(root, tsList);
        });
        log.info("构造树：{}", JSON.toJSONString(rootList));
        List<Long> treeChildList = new ArrayList<>();
        treeChildList.add(1L);
        treeChild(1L, treeStructureList, treeChildList);
        log.info("节点下属：{}", treeChildList);
        List<Long> treeParentList = new ArrayList<>();
        treeParentList.add(111L);
        treeParent(11L, treeStructureList, treeParentList);
        log.info("节点上级：{}", treeParentList);
        //转换
        String[] stringArr = new String[]{"1", "2", "3"};
        int[] intArr = Arrays.stream(stringArr).mapToInt(Integer::valueOf).toArray();
        Integer[] integerArr = Arrays.stream(intArr).boxed().toArray(Integer[]::new);
        int[] intArr2 = Arrays.stream(integerArr).mapToInt(Integer::intValue).toArray();
        String[] stringArr2 = Arrays.stream(integerArr).map(String::valueOf).toArray(String[]::new);
        List<String> stringList = Arrays.stream(stringArr2).collect(Collectors.toList());
        String[] stringArr3 = stringList.stream().toArray(String[]::new);
        //交集、差集、并集
        List<String> copyOnWriteArrayList1 = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList1.add("a");
        copyOnWriteArrayList1.add("b");
        copyOnWriteArrayList1.add("c");
        List<String> copyOnWriteArrayList2 = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList2.add("a");
        copyOnWriteArrayList2.add("b");
        List<String> copyOnWriteArrayList3 = copyOnWriteArrayList1.stream().filter(copyOnWriteArrayList2::contains).collect(Collectors.toList());
        List<String> copyOnWriteArrayList4 = copyOnWriteArrayList1.stream().filter(c->!copyOnWriteArrayList2.contains(c)).collect(Collectors.toList());
        List<String> copyOnWriteArrayList5 = BeanUtil.copyToList(copyOnWriteArrayList3, String.class);
        copyOnWriteArrayList5.addAll(copyOnWriteArrayList4);
        Boy boy31 = new Boy(1, "zhangsan");
        Boy boy32 = new Boy(2, "lisi");
        Boy boy33 = new Boy(3, "wangwu");
        List<Boy> copyOnWriteArrayList6 = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList6.add(boy31);
        copyOnWriteArrayList6.add(boy32);
        copyOnWriteArrayList6.add(boy33);
        copyOnWriteArrayList6.add(boy33);
        log.info("{}",copyOnWriteArrayList6);
        List<Boy> copyOnWriteArrayList7 = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList7.add(boy31);
        copyOnWriteArrayList7.add(boy32);
        log.info("{}",copyOnWriteArrayList6.stream().distinct().collect(Collectors.toList()));
        List<Boy> copyOnWriteArrayList8 = copyOnWriteArrayList6.stream().distinct().collect(Collectors.toList());
        List<Boy> copyOnWriteArrayList9 = copyOnWriteArrayList8.stream().filter(copyOnWriteArrayList7::contains).collect(Collectors.toList());
        log.info("{}",copyOnWriteArrayList9);
        List<Boy> copyOnWriteArrayList10 = copyOnWriteArrayList8.stream().filter(c->!copyOnWriteArrayList7.contains(c)).collect(Collectors.toList());
        log.info("{}",copyOnWriteArrayList10);
        List<Boy> copyOnWriteArrayList11 = BeanUtil.copyToList(copyOnWriteArrayList9, Boy.class);
        copyOnWriteArrayList11.addAll(copyOnWriteArrayList10);
        Map<String, Boy> linkedHashMap = copyOnWriteArrayList11.stream().collect(Collectors.toMap(c -> c.getId() + "-" + c.getName(), Function.identity(), (v1, v2) -> v1, LinkedHashMap::new));
        List<Boy> arrayList = new ArrayList<>(linkedHashMap.values());
        Map<String, Boy> boyGroupMap = copyOnWriteArrayList11.stream()
                .collect(Collectors.groupingBy(c->c.getId() + "-" + c.getName(), HashMap::new, Collectors.collectingAndThen(Collectors.toList(), c -> c.get(0))));
        arrayList = new ArrayList<>(boyGroupMap.values());
        arrayList = copyOnWriteArrayList11.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(c -> c.getId() + "-" + c.getName()))),
                        ArrayList::new));
    }
    public void treeBuild(TreeStructure root, List<TreeStructure> tsList){
        for (TreeStructure ts : tsList) {
            if(ObjectUtil.equal(ts.getPid(),root.getId())){
                root.getChildren().add(ts);
                treeBuild(ts, tsList);
            }
        }
    }
    public void treeChild(Long id, List<TreeStructure> tsList, List<Long> ids){
        for (TreeStructure ts : tsList) {
            if(ObjectUtil.equal(ts.getPid(),id)){
                ids.add(ts.getId());
                treeChild(ts.getId(), tsList, ids);
            }
        }
    }
    public void treeParent(Long id, List<TreeStructure> tsList, List<Long> ids){
        for (TreeStructure ts : tsList) {
            if(ObjectUtil.equal(ts.getId(),id)){
                ids.add(ts.getId());
                treeParent(ts.getPid(), tsList, ids);
            }
        }
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


    @Resource
    private BoyDao boyDao;
    //High Level Client
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    /**
     * 创建索引
     */
    @Test
    public void testCreateIndexByIK() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("boys");
        String json = "{\n" +
                "    \"mappings\":{\n" +
                "        \"properties\":{\n" +
                "            \"id\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "            },\n" +
                "            \"name\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\",\n" +
                "                \"copy_to\":\"all\"\n" +
                "            },\n" +
                "            \"all\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        //设置请求中的参数
        request.source(json, XContentType.JSON);
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        restHighLevelClient.close();
        log.info("{}",response.isAcknowledged());
    }
    /**
     * 删除索引
     */
    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("boys");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        log.info("{}",response.isAcknowledged());
    }
    /**
     * 添加文档
     */
    @Test
    public void testCreateDoc() throws IOException {
        Boy boy = boyDao.selectById(1);
        IndexRequest request = new IndexRequest("boys");
        request.id(String.valueOf(boy.getId()));
        String json = JSON.toJSONString(boy);
        request.source(json,XContentType.JSON);
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("{}",response.status().equals(RestStatus.OK));
        } catch (IOException e) {
            if(!StrUtil.contains(e.getMessage(),"200 OK")){
                throw e;
            }
            log.error(e.getMessage());
        } finally {
            restHighLevelClient.close();
        }
    }
    /**
     * 批量添加文档
     */
    @Test
    public void testCreateDocAll() throws IOException {
        List<Boy> boyList = boyDao.selectList(null);
        BulkRequest bulk = new BulkRequest();
        for (Boy boy : boyList) {
            IndexRequest request = new IndexRequest("boys");
            request.id(String.valueOf(boy.getId()));
            String json = JSON.toJSONString(boy);
            request.source(json,XContentType.JSON);
            bulk.add(request);
        }
        try {
            BulkResponse response = restHighLevelClient.bulk(bulk, RequestOptions.DEFAULT);
            log.info("{}",response.status().equals(RestStatus.OK));
        } catch (IOException e) {
            if(!StrUtil.contains(e.getMessage(),"200 OK")){
                throw e;
            }
            log.error(e.getMessage());
        } finally {
            restHighLevelClient.close();
        }
    }
    /**
     * 更新文档
     */
    public void testUpdateDoc() throws IOException {
        Boy boy = boyDao.selectById(1);
        UpdateRequest request = new UpdateRequest("boys", String.valueOf(boy.getId()));
        request.doc(JSON.toJSONString(boy), XContentType.JSON);
        try {
            UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            log.info("{}",response.status().equals(RestStatus.OK));
        } catch (IOException e) {
            if(!StrUtil.contains(e.getMessage(),"200 OK")){
                throw e;
            }
        } finally {
            restHighLevelClient.close();
        }
    }
    /**
     * 删除文档
     */
    @Test
    public void testDeleteDoc() throws IOException {
        //参数 1: 删除请求对象 参数 2: 请求配置对象
        DeleteRequest request = new DeleteRequest("boys");
        request.id("1");
        try {
            DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            log.info("{}",response.status().equals(RestStatus.OK));
        } catch (IOException e) {
            if(!StrUtil.contains(e.getMessage(),"200 OK")){
                throw e;
            }
            log.error(e.getMessage());
        } finally {
            restHighLevelClient.close();
        }
    }
    /**
     * 批量删除文档
     */
    @Test
    public void testDeleteDocAll() throws IOException {
        List<Boy> boyList = boyDao.selectList(null);
        BulkRequest bulk = new BulkRequest();
        for (Boy boy : boyList) {
            DeleteRequest request = new DeleteRequest("boys");
            request.id(String.valueOf(boy.getId()));
            bulk.add(request);
        }
        try {
            BulkResponse response = restHighLevelClient.bulk(bulk, RequestOptions.DEFAULT);
            log.info("{}",response.status().equals(RestStatus.OK));
        } catch (IOException e) {
            if(!StrUtil.contains(e.getMessage(),"200 OK")){
                throw e;
            }
            log.error(e.getMessage());
        } finally {
            restHighLevelClient.close();
        }
    }
    /**
     * 按id查询
     */
    @Test
    public void testGet() throws IOException {
        GetRequest request = new GetRequest("boys");
        request.id("1");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        if(response.isExists()){
            String json = response.getSourceAsString();
            log.info(json);
            log.info(JSON.parseObject(json, Boy.class).toString());
        }else{
            log.error("没有找到该id的文档");
        }
    }
    /**
     * 按条件查询
     */
    @Test
    public void testSearch() throws IOException {
        SearchRequest request = new SearchRequest("boys");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
//        builder.query(QueryBuilders.termQuery("name","郭靖"));
//        builder.query(QueryBuilders.matchQuery("name","郭"));
//        builder.query(QueryBuilders.wildcardQuery("all","*郭*"));
        request.source(builder);

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            Boy boy = JSON.parseObject(source, Boy.class);
            log.info(boy.toString());
        }
    }

    //Low Level Client
    /*
    spring:
      elasticsearch:
        rest:
          uris: http://localhost:9200
          username:
          password:
          # 连接超时时间(默认1s)
          connection-timeout: 1000
          # 读取超时时间(默认30s)
          read-timeout: 1000
     */
    @Autowired
    private ElasticsearchController elasticsearchController;
    @Test
    public void createIndexAndMapping() {
        Boolean aBoolean = elasticsearchController.createIndexAndMapping();
        System.err.println(aBoolean);
    }
    @Test
    public void deleteIndex() {
        Boolean aBoolean = elasticsearchController.deleteIndex();
        System.err.println(aBoolean);
    }
    @Test
    public void existsIndex() {
        Boolean aBoolean = elasticsearchController.existsIndex();
        System.err.println(aBoolean);
    }
    @Test
    public void save() {
        Elasticsearch elasticsearch = new Elasticsearch("1", "lt", "南京市江宁区", 20, new Date());
        System.err.println(elasticsearchController.save(elasticsearch));
    }
    @Test
    public void delete() {
        elasticsearchController.deleteById("1");
    }
    @Test
    public void findById() {
        Elasticsearch elasticsearch = elasticsearchController.findById("1");
        System.err.println(elasticsearch);
    }
    @Test
    public void search() {
        List<Elasticsearch> teacherList = elasticsearchController.search("address", "南京市");
        System.err.println(teacherList);
    }
    @Test
    public void searchByPage() {
        List<Elasticsearch> teacherList = elasticsearchController.searchByPage(0, 10, "name", "lt");
        System.err.println(teacherList);
    }


    /**
     * mongodb
     */
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void mongodb(){
        Mongodb mongodb = Mongodb.builder().name("狄云").build();
        Mongodb mongodb1 = mongoTemplate.save(mongodb);
        log.info("{}",mongodb1);
        List<Mongodb> all = mongoTemplate.findAll(Mongodb.class);
        log.info("{}",all);
    }

    @Resource
    private GirlService girlService;
    @Resource
    private GirlDao girlDao;
    @Test
    public void crud(){
        BoyDTO boyDTO = BoyDTO.builder().id(4).name("令狐冲").build();
        GirlDTO girlDTO1 = GirlDTO.builder().id(8).name("任盈盈").build();
        GirlDTO girlDTO3 = GirlDTO.builder().name("岳灵珊").build();
        List<GirlDTO> girlDTOList = ListUtil.of(girlDTO1, girlDTO3);
        boyDTO.setGirlList(girlDTOList);
        Boy boy = BeanUtil.copyProperties(boyDTO, Boy.class);
        int mainbool = boyDao.updateById(boy);
        if(mainbool > 0){
            List<Girl> girls = girlDao.selectList(new QueryWrapper<Girl>().lambda().eq(Girl::getBoyId, boyDTO.getId()));
            List<GirlDTO> addList = girlDTOList.stream().filter(girl -> 0 == girl.getId()).collect(Collectors.toList());
            List<Girl> girlAddList = BeanUtil.copyToList(addList, Girl.class);
            girlAddList.forEach(girl -> girl.setBoyId(boy.getId()));
            girlService.saveBatch(girlAddList);
            List<GirlDTO> updateList = girlDTOList.stream().filter(girl -> 0 != girl.getId()).collect(Collectors.toList());
            List<Girl> girlUpdateList = BeanUtil.copyToList(updateList, Girl.class);
            girlUpdateList.forEach(girl -> girl.setBoyId(boy.getId()));
            girlService.updateBatchById(girlUpdateList);
            List<Girl> girlDeleteList = girls.stream().filter(girl -> !girlUpdateList.stream().map(Girl::getId).collect(Collectors.toList()).contains(girl.getId())).collect(Collectors.toList());
            girlService.removeByIds(girlDeleteList.stream().map(Girl::getId).collect(Collectors.toList()));
        }
    }

    @Resource(name = "tptScheduler")
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Test
    public void optimisticLock() throws InterruptedException {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        List<String> childrenList = CollectionUtil.toList("a_1000", "b_3000", "c_2000", "d_1500", "e_5000");
        CountDownLatch maincdl = new CountDownLatch(childrenList.size());
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (String child : childrenList) {
            threadPoolTaskScheduler.submit(()->{
                try {
                    threadLocal.set(child);
                    log.info("thread：thread_{}", StrUtil.split(child, "_").get(0));
                    Thread.sleep(Integer.valueOf(StrUtil.split(child, "_").get(1)));
                    log.info("threadLocal：{}", threadLocal.get());
                    log.info("atomicInteger：{}", atomicInteger.incrementAndGet());
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }finally {
                    maincdl.countDown();
                }
            });
        }
        maincdl.await();
        log.info("atomicInteger：{}", atomicInteger.get());
        log.info("{}", "main前进！");
    }

    /**
     * 自定义starter
     */
    @Autowired
    private TestStarterService testStarterService;
    @Autowired
    RedisUtils redisUtils;
    @Test
    public void testStarter() throws NoSuchMethodException {
        log.info("{}", testStarterService.getName());
        redisUtils.set(this.getClass().getSimpleName()+":"+this.getClass().getDeclaredMethod("testStarter").getName(),new Boy(1,"张三"));
        Boy o = (Boy)redisUtils.get(this.getClass().getSimpleName()+":"+this.getClass().getDeclaredMethod("testStarter").getName());
        log.info("{}", o);
        log.info("{},{}", o.getId(), o.getName());
    }

    /**
     * 审批流程
     */
    @Resource
    private ProcessBusinessService processBusinessService;
    @Resource
    private ProcessRecordService processRecordService;
    @Resource
    private ProcessLineService processLineService;
    @Resource
    private ProcessApprovalService processApprovalService;
    @Test
    public void processTest(){
//        ProcessBusiness processBusiness = new ProcessBusiness();
//        processBusiness.setMoney(NumberUtil.toBigDecimal(500));
//        processBusiness.setUserId(1L);
//        apply(processBusiness);

        queryApplyList(1L);

//        queryApplyDetails(5L);

//        queryApprovalList(2L);

//        ProcessRecord processRecord = new ProcessRecord();
//        processRecord.setNodeCode(ProcessNodeEnum.BX_ZJL.getCode());
//        processRecord.setLineCode(ProcessLineEnum.BX_ZJLBH.getCode());
//        processRecord.setBusinessId(6L);
//        processRecord.setApprovalUser(3L);
//        processRecord.setApprovalResult("不同意");
//        processRecord.setApprovalTime(new Date());
//        processRecord.setApprovalState("3");
//        approval(processRecord);

    }
    //申请人列表
    public void queryApplyList(Long userId){
        List<ProcessBusiness> list = processBusinessService.list(
                new LambdaQueryWrapper<ProcessBusiness>()
                        .eq(ProcessBusiness::getUserId, userId)
        );
        list.forEach(processBusiness->{
            ProcessRecord processRecord = processRecordService.getOne(
                    new LambdaQueryWrapper<ProcessRecord>()
                            .eq(ProcessRecord::getBusinessId, processBusiness.getId())
                            .orderByDesc(ProcessRecord::getApprovalTime)
                            .last("limit 1")
            );
            processBusiness.setPreNodeCode(processRecord.getNodeCode());
            processBusiness.setLineCode(processRecord.getLineCode());
            ProcessLine processLine = processLineService.getOne(
                    new LambdaQueryWrapper<ProcessLine>()
                            .eq(ProcessLine::getCode, processBusiness.getLineCode())
            );
            processBusiness.setNextNodeCode(processLine.getNodeCodeNext());
            ProcessLineEnum processLineEnum = ProcessLineEnum.getProcessStateEnum(processBusiness.getLineCode());
            if(ObjectUtil.isNotNull(processLineEnum)){
                processBusiness.setState(processLineEnum.getStateBigDesc());
            }
        });
        log.info("申请人列表：{}", list);
    }
    //申请记录详情
    public void queryApplyDetails(Long businessId){
        List<ProcessRecord> list = processRecordService.list(
                new LambdaQueryWrapper<ProcessRecord>()
                        .eq(ProcessRecord::getBusinessId, businessId)
                        .orderByAsc(ProcessRecord::getApprovalTime)
        );
        log.info("申请记录详情：{}", list);
    }
    //待审批列表、已通过列表、已驳回列表
    public void queryApprovalList(Long userId){
        //待审批列表
        List<ProcessBusiness> dshList = new ArrayList<>();
        List<ProcessBusiness> processBusinessList = processBusinessService.list();
        for (ProcessBusiness processBusiness : processBusinessList) {
            ProcessRecord processRecordLast = processRecordService.getOne(
                    new LambdaQueryWrapper<ProcessRecord>()
                            .eq(ProcessRecord::getBusinessId, processBusiness.getId())
                            .orderByDesc(ProcessRecord::getApprovalTime)
                            .last("limit 1")
            );
            if(ObjectUtil.isNotNull(processRecordLast)){
                ProcessLine processLine = processLineService.getOne(
                        new LambdaQueryWrapper<ProcessLine>()
                                .eq(ProcessLine::getCode, processRecordLast.getLineCode())
                );
                List<ProcessApproval> processApprovalList = processApprovalService.list(
                        new LambdaQueryWrapper<ProcessApproval>()
                                .eq(ProcessApproval::getNodeCode, processLine.getNodeCodeNext())
                                .apply("(find_in_set('"+userId+"', user_id) or find_in_set('"+userId+"', user_group))")
                );
                if(ObjectUtil.isNotEmpty(processApprovalList)){
                    processBusiness.setPreNodeCode(processRecordLast.getNodeCode());
                    processBusiness.setLineCode(processRecordLast.getLineCode());
                    processBusiness.setNextNodeCode(processLine.getNodeCodeNext());
                    ProcessLineEnum processLineEnum = ProcessLineEnum.getProcessStateEnum(processBusiness.getLineCode());
                    if(ObjectUtil.isNotNull(processLineEnum)){
                        processBusiness.setState(processLineEnum.getStateSmallDesc());
                    }
                    dshList.add(processBusiness);
                }
            }

        }
        log.info("待审批列表：{}", dshList);
        //已通过列表
        List<ProcessBusiness> tgList = processBusinessService.list(
                new LambdaQueryWrapper<ProcessBusiness>()
                        .apply("(select process_record.approval_state from process_record where process_business.id=process_record.business_id and process_record.approval_user='"+userId+"' order by process_record.approval_time desc limit 1)='2'")
        );
        for (ProcessBusiness processBusiness : tgList) {
            ProcessRecord processRecordLast = processRecordService.getOne(
                    new LambdaQueryWrapper<ProcessRecord>()
                            .eq(ProcessRecord::getBusinessId, processBusiness.getId())
                            .orderByDesc(ProcessRecord::getApprovalTime)
                            .last("limit 1")
            );
            if(ObjectUtil.isNotNull(processRecordLast)){
                processBusiness.setPreNodeCode(processRecordLast.getNodeCode());
                processBusiness.setLineCode(processRecordLast.getLineCode());
                ProcessLine processLine = processLineService.getOne(
                        new LambdaQueryWrapper<ProcessLine>()
                                .eq(ProcessLine::getCode, processRecordLast.getLineCode())
                );
                processBusiness.setNextNodeCode(processLine.getNodeCodeNext());
                ProcessLineEnum processLineEnum = ProcessLineEnum.getProcessStateEnum(processBusiness.getLineCode());
                if(ObjectUtil.isNotNull(processLineEnum)){
                    processBusiness.setState(processLineEnum.getStateSmallDesc());
                }
            }
        }
        log.info("已通过列表：{}", tgList);
        //已驳回列表
        List<ProcessBusiness> bhList = processBusinessService.list(
                new LambdaQueryWrapper<ProcessBusiness>()
                        .apply("(select process_record.approval_state from process_record where process_business.id=process_record.business_id and process_record.approval_user='"+userId+"' order by process_record.approval_time desc limit 1)='3'")
        );
        for (ProcessBusiness processBusiness : bhList) {
            ProcessRecord processRecordLast = processRecordService.getOne(
                    new LambdaQueryWrapper<ProcessRecord>()
                            .eq(ProcessRecord::getBusinessId, processBusiness.getId())
                            .orderByDesc(ProcessRecord::getApprovalTime)
                            .last("limit 1")
            );
            if(ObjectUtil.isNotNull(processRecordLast)){
                processBusiness.setPreNodeCode(processRecordLast.getNodeCode());
                processBusiness.setLineCode(processRecordLast.getLineCode());
                ProcessLine processLine = processLineService.getOne(
                        new LambdaQueryWrapper<ProcessLine>()
                                .eq(ProcessLine::getCode, processRecordLast.getLineCode())
                );
                processBusiness.setNextNodeCode(processLine.getNodeCodeNext());
                ProcessLineEnum processLineEnum = ProcessLineEnum.getProcessStateEnum(processBusiness.getLineCode());
                if(ObjectUtil.isNotNull(processLineEnum)){
                    processBusiness.setState(processLineEnum.getStateSmallDesc());
                }
            }
        }
        log.info("已驳回列表：{}", bhList);
    }
    //发起申请
    public void apply(ProcessBusiness processBusiness){
        processBusinessService.save(processBusiness);
        ProcessRecord processRecord = new ProcessRecord();
        processRecord.setNodeCode(ProcessNodeEnum.BX_SQR.getCode());
        processRecord.setLineCode(ProcessLineEnum.BX_SQRTOXMJL.getCode());
        processRecord.setBusinessId(processBusiness.getId());
        processRecord.setApprovalUser(processBusiness.getUserId());
        processRecord.setApprovalResult("申请");
        processRecord.setApprovalTime(new Date());
        processRecord.setApprovalState("1");
        approval(processRecord);
    }
    //审批
    public void approval(ProcessRecord processRecord){
        processRecordService.save(processRecord);
    }

}
