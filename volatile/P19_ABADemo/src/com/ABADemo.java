package com;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题解决   AtomicStampedReference
 */
public class ABADemo {
    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String args[]){
        System.out.println("=============以下是ABA问题的产生============");
        new Thread(()->{
            atomicInteger.compareAndSet(100,101);
            atomicInteger.compareAndSet(101,100);
        },"t1").start();
        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){e.printStackTrace();}
            System.out.println(atomicInteger.compareAndSet(100,2019)+"\t"+atomicInteger.get());

        },"t2").start();
        try {TimeUnit.SECONDS.sleep(2);}catch (Exception e){e.printStackTrace();}

        System.out.println("=============以下是ABA问题的解决============");
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本号："+stamp);
            try {TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
            atomicStampedReference.compareAndSet(100,101,stamp,++stamp);
            System.out.println(Thread.currentThread().getName()+"\t第二次版本号："+stamp);
            atomicStampedReference.compareAndSet(101,100,stamp,++stamp);
            System.out.println(Thread.currentThread().getName()+"\t第三次版本号："+stamp);
        },"t3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本号："+stamp);
            try {TimeUnit.SECONDS.sleep(3);}catch (Exception e){e.printStackTrace();}
            boolean result = atomicStampedReference.compareAndSet(100,101,stamp,++stamp);
            System.out.println(Thread.currentThread().getName()+"\t修改成功否："+result+"\t当前最新实际版本号："+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t当前实际最新值："+atomicStampedReference.getReference());
        },"t4").start();
    }
}
