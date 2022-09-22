package com.ds.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

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

    @Async("asyncExecutor")
    public Future<Boolean> sayHello() throws Exception {
        final ReentrantLock lock = new ReentrantLock();
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

}
