package com.ds.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class LockTest {

    Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();

    AtomicInteger count = new AtomicInteger(30);

    //锁ReentrantLock，同一对象，所有线程同步
    ReentrantLock lock = new ReentrantLock();
    @Async("asyncExecutor")
    public Future<Boolean> reentrantLockTest() throws Exception {
        //锁ReentrantLock，不同对象，单个线程同步
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        int temp = 0;
        for (; ; ) {
            Thread.sleep(2000);
            int j = count.get();
            temp = j;
            if (temp == 0) {
                log.info("票已卖光");
                break;
            }
            final boolean b = count.compareAndSet(j, --j);
            if (b) {
                log.info("{}:卖出第{}张票，还剩{}张票", Thread.currentThread().getName(), ++j, temp = --temp);
            }
        }
        lock.unlock();
        return new AsyncResult<>(true);
    }

    @Async("asyncExecutor")
    public void synchronizedText() throws Exception {
//        //锁对象，因为对象为单例，同一对象，所有线程同步
//        synchronized (this){
//            Thread.sleep(1000);
//            log.info(Thread.currentThread().getName()+": "+ LocalDateTime.now());
//        }
        //锁对象，不同对象，单个线程同步
        byte[] byteArr = new byte[2];
        synchronized (byteArr){
            Thread.sleep(1000);
            log.info(Thread.currentThread().getName()+": "+ LocalDateTime.now());
        }
    }

}
