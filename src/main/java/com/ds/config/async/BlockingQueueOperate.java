package com.ds.config.async;

import com.google.common.collect.Queues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BlockingQueueOperate implements InitializingBean, DisposableBean {
    /**
     * 我们实际开发过程中经常会处理各种大批量数据入库，
     * 这个时候我们就会到队列，将数据先写入队列中，
     * 然后开启多个消费线程慢慢消费入库。从队列中消费数据有两种方式：单条消费、批量消费
     */

    /**
     * int capacity
     * 该参数表示当前定义队列的大小，也就是能存放多少条数据
     * boolean fair
     * 该参数表示访问该队列的策略是否公平。true：按照 FIFO 顺序访问插入或移除时受阻塞线程的队列；false:访问顺序是不确定的
     * Collection<? extends E> c
     * 该参数是一个集合，表示将一个集合的数据存入该阻塞队列，相当于给该队列一个初始数据
     */
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1000000,true);

    /**
     * 向队列中存放数据
     * @param message
     */
    public void saveQueueData(String message){
        //存放数据
        blockingQueue.offer("test");
    }

    /**
     * 从队列中单条消费数据
     */
    public void consumerBySingle() {
        while (true) {
            try {
                String take = blockingQueue.take();
                log.info("消费到的数据是：{}", take);
            } catch (Exception e) {
                log.error("缓存队列单条消费异常：{}", e.getMessage());
            }
        }
    }

    /**
     * 从队列中批量消费数据
     *
     * Queues.drain(blockingQueue, list, 100, 1, TimeUnit.MINUTES);这个方法一共有5个参数
     * 第一个：传入你需要批量消费的队列
     * 第二个：传入一个用来接收批量消费到的数据
     * 第三个：批量消费数据的大小，这里我们给100，即意味着每次消费100条数据
     * 第四个：批量消费的等待的最大间隔，什么意思呢？比如说，我先在队列中只有10条数据，它不到100条，那按道理就不会消费，但是这样显然不合理，所以需要指定当超多多长时间，即使当前队列中数据低于我们设定的阈值也会消费
     * 第五个，这个就很好理解，就是指定第四个参数的单位，是秒是分钟还是小时等等
     */
    public void consumerByBatch() {
        while (true) {
            try {
                List<String> list = new ArrayList<>();
                Queues.drain(blockingQueue, list, 100, 1, TimeUnit.MINUTES);
                log.info("批量消费到的数据是：{}", list);
            } catch (Exception e) {
                log.error("缓存队列批量消费异常：{}", e.getMessage());
            }
        }
    }


    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /*for (int i = 0 ;i < 789; i++){
            //存放数据
            blockingQueue.offer(i + "test");
        }
        consumerByBatch();*/
    }

    public static void main(String[] args) {
        //创建队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1000000,true);
        for (int i = 0 ;i < 789; i++){
            //存放数据
            blockingQueue.offer(i + "test");
        }
        while (true) {
            try {
                List<String> list = new ArrayList<>();
                Queues.drain(blockingQueue, list, 100, 1, TimeUnit.SECONDS);
                log.info("批量消费到的数据量是：{}, 数据是: {}", list.size(), list);
            } catch (Exception e) {
                log.error("缓存队列批量消费异常：{}", e.getMessage());
            }
        }
    }

}
