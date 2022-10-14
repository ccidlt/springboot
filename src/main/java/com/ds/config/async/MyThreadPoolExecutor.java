package com.ds.config.async;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 多线程
 */
public class MyThreadPoolExecutor {

    //饿汉式单例（随着类加载实例化）
    //private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,30, TimeUnit.MINUTES,new ArrayBlockingQueue<>(64),new ThreadPoolExecutor.DiscardPolicy());

    private MyThreadPoolExecutor(){}
    private static class ThreadPoolExecutorHolder {
        /**
         * corePoolSize ：线程池中核心线程数的最大值
         * maximumPoolSize ：线程池中能拥有最多线程数
         * keepAliveTime ：表示空闲线程的存活时间
         * TimeUnit unit ：表示keepAliveTime的单位
         * workQueue：用于缓存任务的阻塞队列,它决定了缓存任务的排队策略.ThreadPoolExecutor线程池推荐了三种等待队列，它们是：SynchronousQueue 、LinkedBlockingQueue 和 ArrayBlockingQueue。
         * handler 拒绝策略：表示当 workQueue 已满，且池中的线程数达到 maximumPoolSize 时，线程池拒绝添加新任务时采取的策略。
         *      hreadPoolExecutor.AbortPolicy()抛出RejectedExecutionException异常。默认策略
         *      ThreadPoolExecutor.CallerRunsPolicy()由向线程池提交任务的线程来执行该任务
         *      ThreadPoolExecutor.DiscardPolicy()抛弃当前的任务
         *      ThreadPoolExecutor.DiscardOldestPolicy()抛弃最旧的任务（最先提交而没有得到执行的任务）

         */
        private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,30, TimeUnit.MINUTES,new ArrayBlockingQueue<>(64),new ThreadPoolExecutor.DiscardPolicy());
    }
    public static final ThreadPoolExecutor getThreadPoolExecutor() {
        return ThreadPoolExecutorHolder.threadPoolExecutor;
    }

    //private static final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);

    private static class ScheduledExecutorServiceHolder {
        private static final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);
    }
    public static final ScheduledExecutorService getScheduledExecutorService() {
        return ScheduledExecutorServiceHolder.scheduledExecutorService;
    }

    //懒汉式


    public static void submit(Runnable runnable){
        MyThreadPoolExecutor.getThreadPoolExecutor().submit(runnable);
    }

    public static<T> T submit(Callable<T> callable){
        Future<T> future = MyThreadPoolExecutor.getThreadPoolExecutor().submit(callable);
        T t = null;
        try {
            t = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static void scheduled(Runnable runnable,Long initialDelay,Long period,TimeUnit timeUnit){
        MyThreadPoolExecutor.getScheduledExecutorService().scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }

    public static void runAsync(Runnable runnable){
        CompletableFuture.runAsync(runnable, Executors.newSingleThreadExecutor());
    }

    public static <T> T runAsync(Supplier<T> supplier){
        CompletableFuture<T> future = CompletableFuture.supplyAsync(supplier,Executors.newSingleThreadExecutor());
        T t = null;
        try {
            t = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }
}
