package com.ds.config.async;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 多线程
 */
public class MyThreadPoolExecutor {


    private MyThreadPoolExecutor() {
    }

    /**
     * 单例模式的创建
     *
     * 饿汉式：线程安全，内存空间浪费
     *
     * 1.创建一个类 然后对类里面的构造器进行私有化 目的：防止外面调用该类创建对象 无法实现单例
     * 2.一开始先创建一个静态对象 目的：这个对象就是单例
     * 3.创建一个pubilc静态方法来返回对象,供外面调用该对象
     */
//    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,30, TimeUnit.MINUTES,new ArrayBlockingQueue<>(64),new ThreadPoolExecutor.DiscardPolicy());
//    public static ThreadPoolExecutor getThreadPoolExecutor() {
//        return threadPoolExecutor;
//    }

    /**
     * 单例模式的创建
     *
     * 懒汉式：线程不安全，避免了内存空间浪费
     *
     * 1.创建一个类 并将构造器设置为私有的 目的：防止外面的对象去调用 无法弄成单例模式
     * 2.声明一个静态对象 但是没有初始化  等到要使用的时候再去初始化对象 避免占用内存空间
     * 3.提供一个public static 方法供外面调用
     */
//    private volatile static ScheduledExecutorService scheduledExecutorService = null;
//    public static ScheduledExecutorService getScheduledExecutorService() {
//        if(scheduledExecutorService == null){
//            synchronized (MyThreadPoolExecutor.class){
//                if(scheduledExecutorService == null){
//                    scheduledExecutorService = new ScheduledThreadPoolExecutor(5);
//                }
//            }
//        }
//        return scheduledExecutorService;
//    }

    /**
     * 静态内部类的方式：
     * <p>
     * 静态内部类结合了懒汉式和饿汉式的优点既保证了线程安全，又节省空间
     */
    private static class ThreadPoolExecutorHolder {
        /**
         * corePoolSize ：线程池中核心线程数的最大值
         * maximumPoolSize ：线程池中能拥有最多线程数
         * keepAliveTime ：表示空闲线程的存活时间
         * TimeUnit unit ：表示keepAliveTime的单位
         * workQueue：用于缓存任务的阻塞队列,它决定了缓存任务的排队策略.ThreadPoolExecutor线程池推荐了三种等待队列，它们是：SynchronousQueue 、LinkedBlockingQueue 和 ArrayBlockingQueue。
         * handler 拒绝策略：表示当 workQueue 已满，且池中的线程数达到 maximumPoolSize 时，线程池拒绝添加新任务时采取的策略。
         * hreadPoolExecutor.AbortPolicy()抛出RejectedExecutionException异常。默认策略
         * ThreadPoolExecutor.CallerRunsPolicy()由向线程池提交任务的线程来执行该任务
         * ThreadPoolExecutor.DiscardPolicy()抛弃当前的任务
         * ThreadPoolExecutor.DiscardOldestPolicy()抛弃最旧的任务（最先提交而没有得到执行的任务）
         */
        private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<>(64), new ThreadPoolExecutor.DiscardPolicy());
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return ThreadPoolExecutorHolder.threadPoolExecutor;
    }


    private static class ScheduledExecutorServiceHolder {
        private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        return ScheduledExecutorServiceHolder.scheduledExecutorService;
    }


    public static void submit(Runnable runnable) {
        MyThreadPoolExecutor.getThreadPoolExecutor().submit(runnable);
    }

    public static <T> T submit(Callable<T> callable) {
        Future<T> future = MyThreadPoolExecutor.getThreadPoolExecutor().submit(callable);
        T t = null;
        try {
            t = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static void scheduled(Runnable runnable, Long initialDelay, Long period, TimeUnit timeUnit) {
        MyThreadPoolExecutor.getScheduledExecutorService().scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }

    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable, Executors.newSingleThreadExecutor());
    }

    public static <T> T runAsync(Supplier<T> supplier) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(supplier, Executors.newSingleThreadExecutor());
        T t = null;
        try {
            t = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
