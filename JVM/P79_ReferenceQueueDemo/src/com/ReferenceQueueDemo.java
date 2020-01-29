package com;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class ReferenceQueueDemo {
    public static void main(String args[]) throws InterruptedException {
        Object object = new Object();
        //回收前引用队列保存下
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(object,referenceQueue);

        System.out.println(object);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("***************");
        object = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(object);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());
    }
}
