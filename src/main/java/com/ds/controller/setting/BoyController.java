package com.ds.controller.setting;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ds.dao.BoyDao;
import com.ds.entity.Boy;
import com.ds.entity.Result;
import com.ds.service.BoyService;
import io.seata.core.context.RootContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/")
@Api(tags = "接口")
@Slf4j
public class BoyController {

    private Logger logger = LoggerFactory.getLogger(BoyController.class);

    @Autowired
    @Qualifier("boyService")
    private BoyService boyService;

    @RequestMapping(value = "/getBoys", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@ApiOperation(value="获取Boy表所有数据", response = Boy.class)
    @ApiOperation(value = "获取Boy表所有数据")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功", response = Boy.class),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500, message = "后端程序报错")
    })
    public List<Boy> getBoys() {
        List<Boy> boys = boyService.getBoys();
        logger.info("获取所有的表数据条数={}", boys != null ? boys.size() : 0);
        log.info("获取所有的表数据条数={}", boys != null ? boys.size() : 0);
        return boys;
    }

    @RequestMapping(value = "/getBoysById", method = RequestMethod.GET)
    @ApiOperation("获取Boy表数据(通过id)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType = "Integer")
    })
    public List<Boy> getBoysByParam(@RequestParam("id") Integer id) {
        Boy boy = new Boy();
        boy.setId(id);
        return boyService.queryBoy(boy);
    }

    @RequestMapping(value = "/getBoysByParam", method = RequestMethod.GET)
    @ApiOperation("获取Boy表数据")
    public List<Boy> getBoysByParam() {
        Boy boy = new Boy();
        boy.setName("杨过");
        return boyService.queryBoy(boy);
    }

    @RequestMapping(value = "/addBoy", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增/修改")
    public Boy addBoy(
            @ApiParam(name = "boy", value = "Boy", required = true)
            @Validated
            @RequestBody(required = true) Boy boy) {
        //boy中有id则为修改，没有为新增
        boyService.saveOrUpdate(boy);
        return boyService.getById(boy.getId());
    }

    @RequestMapping(value = "/saveBoy", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增/修改")
    @SentinelResource(value = "saveBoy") //热点参数限流：value对应资源名
    public Boy saveBoy(
            @ApiParam(name = "boy", value = "Boy", required = true)
            @Validated
            @RequestBody(required = true) Boy boy) {
        return boyService.saveBoy(boy);
    }

    @Value("${filePath:F:/file/}")
    String filePath;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("文件上传")
    @ApiImplicitParam(name = "file", value = "文件", required = true, paramType = "query", dataType = "file")
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
                boy1.setGirlId(SysDictOne.getId());
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
        String result = "";
        CountDownLatch rollBackLatch = new CountDownLatch(1);
        CountDownLatch mainThreadLatch = new CountDownLatch(2);
        AtomicBoolean rollbackFlag = new AtomicBoolean(false);
        List<Future<String>> list = new ArrayList<Future<String>>();
        // 线程有返回值
        Future<String> future = executor1(rollBackLatch, mainThreadLatch, rollbackFlag);
        list.add(future);
        // 线程无返回值
        executor2(rollBackLatch, mainThreadLatch, rollbackFlag);
        // 主线程业务执行完毕 如果其他线程也执行完毕 且没有报异常 正在阻塞状态中 唤醒其他线程 提交所有的事务
        // 如果其他线程或者主线程报错 则不会进入if 会触发回滚
        if (!rollbackFlag.get()) {
            try {
                mainThreadLatch.await();
                rollBackLatch.countDown();
                for (Future<String> f : list)
                    if (!"success".equals(f.get()))
                        result = f.get() + "。";
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public Future<String> executor1(CountDownLatch rollBackLatch, CountDownLatch mainThreadLatch,AtomicBoolean rollbackFlag) {
        Future<String> result = tptExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (rollbackFlag.get())
                    return "error"; // 如果其他线程已经报错 就停止线程
                // 设置一个事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
                try {
                    // 业务处理开始
                    // ..............
                    // 业务处理结束
                    mainThreadLatch.countDown();
                    rollBackLatch.await();// 线程等待
                    if (rollbackFlag.get()) {
                        transactionManager.rollback(status);
                    } else {
                        transactionManager.commit(status);
                    }
                    return "success";
                } catch (Exception e) {
                    e.printStackTrace();
                    // 如果出错了 就放开锁 让别的线程进入提交/回滚 本线程进行回滚
                    rollbackFlag.set(true);
                    rollBackLatch.countDown();
                    mainThreadLatch.countDown();
                    transactionManager.rollback(status);
                    return "操作失败：" + e.getMessage();
                }
            }

        });
        // result.get()阻塞线程
        return result;
    }
    public void executor2(CountDownLatch rollBackLatch, CountDownLatch mainThreadLatch, AtomicBoolean rollbackFlag) {
        tptExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (rollbackFlag.get())
                    return; // 如果其他线程已经报错 就停止线程
                // 设置一个事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
                try {
                    // 业务处理开始
                    // .....
                    // 业务处理结束
                    mainThreadLatch.countDown();
                    rollBackLatch.await();// 线程等待
                    if (rollbackFlag.get()) {
                        transactionManager.rollback(status);
                    } else {
                        transactionManager.commit(status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // 如果出错了 就放开锁 让别的线程进入提交/回滚 本线程进行回滚
                    rollbackFlag.set(true);
                    rollBackLatch.countDown();
                    mainThreadLatch.countDown();
                    transactionManager.rollback(status);
                }
            }
        });
    }

}
