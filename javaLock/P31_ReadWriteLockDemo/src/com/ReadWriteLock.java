package com;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyLock{
    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void set(String key,Object value){
        reentrantReadWriteLock.writeLock().lock();
       try{
           System.out.println(Thread.currentThread().getName()+"\t写方法进入");
           map.put(key,value);
           try {
               TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
           System.out.println(Thread.currentThread().getName()+"\t写方法完成");
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           reentrantReadWriteLock.writeLock().unlock();
       }
    }
    public void get(String key){
        reentrantReadWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t读方法进入");
            map.get(key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
            System.out.println(Thread.currentThread().getName()+"\t读方法完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }

    }
    /*手写一个redis，包括读，写，清空
    public void clear(){
        map.clear();
    }
    */
}

public class ReadWriteLock {
    public static void main(String args[]) {
        MyLock myLock = new MyLock();
        for (int i = 1; i <= 5; i++) {
            Integer tmp = i;
            new Thread(() -> {
                myLock.set(tmp + "", tmp + "");
            }, String.valueOf(tmp)).start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
        for (int i = 1; i <= 5; i++) {
            Integer tmp = i;
            new Thread(() -> {
                myLock.get(tmp + "");
            }, String.valueOf(tmp)).start();
        }
    }
}
