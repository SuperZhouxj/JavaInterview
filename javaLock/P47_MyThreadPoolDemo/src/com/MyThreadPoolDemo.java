package com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 */
public class MyThreadPoolDemo {
    public static void main(String args[]) {
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5个处理线程
        // ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池一个处理线程
        ExecutorService threadPool = Executors.newCachedThreadPool();//一池N个处理线程
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理成功");
                });
                // TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
