package com;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁Demo  尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁
 */
public class SpinLockDemo {
    //原子引用线程，原本为空
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"\t come in");
        //自旋锁的核心，A线程睡眠5s时候，B线程不断循环，尝试获取锁，当A是atomicRefenrence置空，B获取锁
        while (!atomicReference.compareAndSet(null,thread)){

        }
    }

    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"\t come out");
    }

    public static void main(String args[]){
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);}catch (Exception e){e.printStackTrace();}
            spinLockDemo.myUnlock();
        },"A").start();

        try {TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}

        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
            spinLockDemo.myUnlock();
        },"B").start();

    }
}
