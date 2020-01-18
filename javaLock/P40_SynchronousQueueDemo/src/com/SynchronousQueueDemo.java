package com;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {
    public static void main(String args[]) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"\t put a");;
                blockingQueue.put("a");

                System.out.println(Thread.currentThread().getName()+"\t put b");;
                blockingQueue.put("b");

                System.out.println(Thread.currentThread().getName()+"\t put c");;
                blockingQueue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\ttake "+blockingQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\ttake "+blockingQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\ttake "+blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
