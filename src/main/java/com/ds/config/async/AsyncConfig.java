package com.ds.config.async;

import com.ds.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
public class AsyncConfig {

    //获取当前机器的核数
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    @Bean("tptScheduler")
    @Primary
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(cpuNum * 2);
        return executor;
    }

    @Resource
    private RedisUtils redisUtils;

    @Bean("tptExecutor")
    @Primary
    public ThreadPoolTaskExecutor tptExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数：线程池创建时候初始化的线程数
        executor.setCorePoolSize(cpuNum * 2);
        // 最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(cpuNum * 2);
        // 缓冲队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        // 允许线程的空闲时间60秒：当超过了核心线程之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("thread-");
        // 缓冲队列满了之后的拒绝策略：由调用线程处理（一般是主线程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

    //@Scheduled(cron = "0 0 1 * * ?")
    @Async("tptExecutor")
    public void a() {
        if (redisUtils.acquireLock("bfsjk", 10)) {
            try {
                log.info("执行定时任务》》》" + new Date());
                backupDataSource();
                log.info("备份数据库成功!!!");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                redisUtils.releaseLock("bfsjk");
            }
        }
    }

    //备份数据库
    public void backupDataSource() {
        try {
            //String filePath=this.getClass().getResource("/sql").getPath();
            String filePath = this.getClass().getClassLoader().getResource("sql").getPath();
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            String ip = "127.0.0.1";
            String port = "3306";
            String dbName = "test";//备份的数据库名
            String username = "root";//用户名
            String password = "123456";//密码
            File uploadDir = new File(filePath);
            if (!uploadDir.exists())
                uploadDir.mkdirs();
            String cmd = "mysqldump -h" + ip + " -P" + port + " -u" + username + " -p" + password + " " + dbName + " > " + filePath + "/" + dbName + new Date().getTime() + ".sql";
            String osName = System.getProperty("os.name").toLowerCase();
            String[] command = new String[0];
            if (osName.startsWith("windows")) {
                command = new String[]{"cmd", "/c", cmd};
            } else if (osName.startsWith("linux")) {
                command = new String[]{"/bin/sh", "-c", cmd};
            }
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    //还原数据库
    public void restoreDataSource() {
        try {
            //String filePath=this.getClass().getResource("/sql").getPath();
            String filePath = this.getClass().getClassLoader().getResource("sql").getPath();
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            String dbName = "test";//备份的数据库名
            String username = "root";//用户名
            String password = "123456";//密码
            File uploadDir = new File(filePath);
            if (!uploadDir.exists())
                uploadDir.mkdirs();
            String cmd = "mysqldump -u" + username + " -p" + password + " " + dbName + " < " + filePath + "/" + dbName + new Date().getTime() + ".sql";
            String osName = System.getProperty("os.name").toLowerCase();
            String[] command = new String[0];
            if (osName.startsWith("windows")) {
                command = new String[]{"cmd", "/c", cmd};
            } else if (osName.startsWith("linux")) {
                command = new String[]{"/bin/sh", "-c", cmd};
            }
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Async("tptExecutor")
    public Future<String> b() {
        return new AsyncResult<>("返回值：" + Thread.currentThread().getName());
    }

}
