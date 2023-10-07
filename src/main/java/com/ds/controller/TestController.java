package com.ds.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ds.config.repeatsubmit.RepeatSubmitAnno;
import com.ds.config.snowflake.Snowflake;
import com.ds.config.snowflake.SnowflakeProperties;
import com.ds.config.swagger.SwaggerProperties;
import com.ds.dao.BoyDao;
import com.ds.entity.Boy;
import com.ds.entity.Result;
import com.ds.service.BoyService;
import com.github.benmanes.caffeine.cache.Cache;
import io.seata.core.context.RootContext;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试接口
 * @author lt
 * @date 2023/8/2 10:17
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private BoyService boyService;


    @Value("${filePath:F:/file/}")
    String filePath;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("文件上传")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "form", dataType = "__file")
    public Result upload(MultipartFile file, HttpServletRequest request) {
        /*ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");*/
        Result result = Result.ok();
        String originalFilename = file.getOriginalFilename();
        String hz = originalFilename.substring(originalFilename.lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        String filedir = filePath + format;
        File file1 = new File(filedir);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String filename = UUID.randomUUID().toString().replace("-", "") + hz;
        try {
            file.transferTo(new File(filedir, filename));
            result = Result.ok(format + "/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
            result = Result.fail();
        }
        return result;
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @ApiOperation("文件下载")
    @ApiImplicitParam(name = "path", value = "文件路径", required = true, paramType = "query", dataType = "String")
    public HttpServletResponse download(@RequestParam("path") String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(filePath + path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.setHeader("Access-Control-Allow-Origin", "*");//跨域
            response.setHeader("Access-Control-Expose-Headers", "content-disposition");//暴露出header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");//二进制流文件
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/globalTransactional", method = RequestMethod.POST)
    @Transactional
    public Result globalTransactional(@RequestBody(required = false) Boy b){
        System.out.println("globalTransactional AService XID: "+ RootContext.getXID());
        Boy boy = new Boy();
        boy.setName("令狐冲");
        return Result.ok(boyService.save(boy));
    }

    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public Result async(){
        boyService.async();
        return Result.ok();
    }

    @RequestMapping(value = "/atomic", method = RequestMethod.GET)
    public Result atomic(){
        boyService.atomic();
        return Result.ok();
    }


    /**
     * 编程式事务
     */
    @Resource(name = "tptExecutor")
    private ThreadPoolTaskExecutor tptExecutor;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @ApiOperation(value = "编程式事务测试", notes = "编程式事务测试")
    @RequestMapping(value = "/sqlSession", method = RequestMethod.GET)
    public String transactionTest() throws Exception {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(false);
        Connection connection = sqlSession.getConnection();
        connection.setAutoCommit(false);
        try {
            Boy boy = new Boy();
            boy.setName("ABC");
            BoyDao mapper = sqlSession.getMapper(BoyDao.class);
            mapper.insert(boy);
            List<Future<String>> futureList = new ArrayList<>();
            Future<String> thread1 = tptExecutor.submit(() -> {
                Boy SysDictOne = mapper.selectOne(new LambdaQueryWrapper<Boy>().eq(Boy::getId, boy.getId()).last("limit 1"));
                Boy boy1 = new Boy();
                boy1.setName("DEF");
                boy1.setGirlId(SysDictOne.getGirlId());
                mapper.insert(boy1);
                int i = 1/0;
                return "ok";
            });
            futureList.add(thread1);
            for (Future<String> future : futureList) {
                System.out.println(future.get());
            }
            connection.commit();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            connection.rollback();
        } finally {
            connection.close();
        }
        return "ok";
    }

    @Autowired
    private PlatformTransactionManager transactionManager;
    @ApiOperation(value = "编程式事务测试", notes = "编程式事务测试")
    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String transactionTest2() throws Exception {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus mainStatus = transactionManager.getTransaction(def);
        try {
            Boy boy = new Boy();
            boy.setName("ABC");
            boyService.save(boy);
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    tptExecutor.execute(() -> {
                        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                        TransactionStatus childStatus = transactionManager.getTransaction(def);
                        try {
                            Boy SysDictOne = boyService.getOne(new LambdaQueryWrapper<Boy>().eq(Boy::getId, boy.getId()).last("limit 1"));
                            Boy boy1 = new Boy();
                            boy1.setName("DEF");
                            boy1.setGirlId(SysDictOne.getGirlId());
                            boyService.save(boy1);
                            transactionManager.commit(childStatus);
                        } catch (Exception e) {
                            log.error(e.getMessage(),e);
                            transactionManager.rollback(childStatus);
                        }
                    });
                }
            });
            int i = 1/0;
            transactionManager.commit(mainStatus);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            transactionManager.rollback(mainStatus);
        }
        return "ok";
    }


    @Resource
    private Snowflake snowflake;
    @Resource
    private SnowflakeProperties snowflakeProperties;
    @Resource
    private SwaggerProperties swaggerProperties;
    @RepeatSubmitAnno
    @RequestMapping("/getSnowflake")
    public long getSnowflake() {
        log.info("dataCenterId:{},machineId:{},swagger.enable:{}",
                snowflakeProperties.getDataCenterId(),snowflakeProperties.getMachineId(),
                swaggerProperties.getEnable());
        return snowflake.nextId();
    }

    @RequestMapping("/logstash")
    public String logstash() throws Exception {
//        logger.info("logback 访问hello");
//        logger.error("logback 访问hello");
        log.info(JSON.toJSONString(new Boy(99,"张三丰"), SerializerFeature.WriteMapNullValue));
        return "logback 成功";
    }

    /**
     * 本地缓存：ConcurrentHashMap和Caffeine，Caffeine可过期，Caffeine读写性能比ConcurrentHashMap好
     * 分布式缓存：redis
     */
    private Map<String,Object> cacheMap = new ConcurrentHashMap<>();
    @Resource
    private Cache<String, Object> caffeine;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private static Lock lock = new ReentrantLock();
    @GetMapping("/cache")
    public void cache() throws InterruptedException {
//        log.info("{}",caffeine.asMap());
//        CountDownLatch countDownLatch = new CountDownLatch(5);
//        for (int i=0;i<5;i++){
//            int ii = i;
//            tptExecutor.execute(()->{
//                caffeine.put("cacheMap_"+ ii, ii);
//                countDownLatch.countDown();
//            });
//        }
//        countDownLatch.await();
//        log.info("{}",caffeine.asMap());

        //tryLock等待时间，没释放锁，其他线程直接放弃执行下面程序
        for (int i=0;i<5;i++){
            int ii = i;
            tptExecutor.execute(()->{
                try {
                    if(lock.tryLock(3, TimeUnit.SECONDS)){
                        try {
                            log.info(String.valueOf(ii));
//                            Thread.sleep(1*1000);
                            Thread.sleep(5*1000);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        } finally {
                            lock.unlock();
                        }
                    }
                    log.info(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            });
        }

    }

}
