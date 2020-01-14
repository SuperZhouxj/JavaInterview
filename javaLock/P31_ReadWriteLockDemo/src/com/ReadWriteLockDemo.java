package com;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁实例，读是共享锁，写是独占锁
 * 读时为保证并发量，多线程可同时读，写时其他线程不可对其再读或者写
 * 读写能共存
 * 读写不能共存
 * 写写不能共存
 */
public class ReadWriteLockDemo {
    private volatile Map<String,Object> map = new HashMap<>();
    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void set(String key,Object value){
        reentrantReadWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t开始写入"+key);
            map.put(key,value);
            try {
                TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
            System.out.println(Thread.currentThread().getName()+"\t写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }
    public void get(String key){
        reentrantReadWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t开始读入");
            Object result = map.get(key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);}catch (Exception e){e.printStackTrace();}
            System.out.println(Thread.currentThread().getName()+"\t读入完成"+result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public static void main(String args[]){
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        for (int i =1;i<=5;i++){
            Integer temp = i;
            new Thread(()->{
                readWriteLockDemo.set(temp+"",temp+"");
            },String.valueOf(temp)).start();
        }
        for (int i =1;i<=5;i++){
            Integer temp = i;
            new Thread(()->{
                readWriteLockDemo.get(temp+"");
            },String.valueOf(temp)).start();
        }
    }
}
