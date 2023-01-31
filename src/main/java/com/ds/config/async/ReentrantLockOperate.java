package com.ds.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class ReentrantLockOperate {

    /**
     * 1、作为多个线程同时使用的原子计数器。
     * addAndGet()- 以原子方式将给定值添加到当前值，并在添加后返回新值。
     * getAndAdd() - 以原子方式将给定值添加到当前值并返回旧值。
     * incrementAndGet()- 以原子方式将当前值递增1并在递增后返回新值。它相当于i ++操作。
     * getAndIncrement() - 以原子方式递增当前值并返回旧值。它相当于++ i操作。
     * decrementAndGet()- 原子地将当前值减1并在减量后返回新值。它等同于i-操作。
     * getAndDecrement() - 以原子方式递减当前值并返回旧值。它相当于-i操作。
     * <p>
     * 2、在比较和交换操作中实现非阻塞算法。
     * 比较和交换操作将内存位置的内容与给定值进行比较，并且只有它们相同时，才将该内存位置的内容修改为给定的新值。这是作为单个原子操作完成的
     * boolean compareAndSet(int expect, int update)
     */
    AtomicInteger count = new AtomicInteger(30);

    /**
     * 减法计数器,用于线程阻塞
     * countDownLatch.countDown();计数器减一
     * countDownLatch.await();等待计数器归零主线程才会继续执行
     */
    CountDownLatch countDownLatch = new CountDownLatch(100);

    /**
     * 线程安全类：
     * 1、通过synchronized实现线程安全：Vector、HashTable、StringBuffer
     * 2、原子类AtomicLong、AtomicInteger等
     * 3、ConcurrentHashMap
     * 4、ThreadPoolExecutor
     */
    Map<String, AtomicInteger> concurrentHashMap = new ConcurrentHashMap<>();


    //锁ReentrantLock，同一对象，所有线程同步
    ReentrantLock lock = new ReentrantLock();

    @Async("tptExecutor")
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

    @Async("tptExecutor")
    public void synchronizedText() throws Exception {
//        //锁对象，因为对象为单例，同一对象，所有线程同步
//        synchronized (this){
//            Thread.sleep(1000);
//            log.info(Thread.currentThread().getName()+": "+ LocalDateTime.now());
//        }
        //锁对象，不同对象，单个线程同步
        byte[] byteArr = new byte[2];
        synchronized (byteArr) {
            Thread.sleep(1000);
            log.info(Thread.currentThread().getName() + ": " + LocalDateTime.now());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        //java中的辅助类，可以保证
        final CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            int ii = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    count.getAndIncrement();
                    System.out.println(Thread.currentThread().getName()+"-"+ii+":"+count);
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        System.out.println(count.get());
    }

}
