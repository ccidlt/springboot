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
