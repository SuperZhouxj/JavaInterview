package com;

import java.util.concurrent.*;

/**
 * 自写线程池
 */
public class MyWriterTheadPoolDemo {

    public static void main(String args[]) {
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5个处理线程
        // ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池一个处理线程
        //ExecutorService threadPool = Executors.newCachedThreadPool();//一池N个处理线程
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy()
                //new ThreadPoolExecutor.CallerRunsPolicy()
                //new ThreadPoolExecutor.DiscardOldestPolicy()
                new ThreadPoolExecutor.DiscardPolicy()
        );
        try {
            for (int i = 1; i <= 10; i++) {
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
