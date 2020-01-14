package com;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁（又名递归锁）
 * 线程可以进入任何一个它已经拥有的锁所同步者的代码块
 */
class Phone implements Runnable{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS");
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t ###### invoked sendEmail");
    }

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    private void get() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked get");
            set();
        }finally {
            lock.unlock();
            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked set");

        }finally {
            lock.unlock();
        }
    }
}

public class ReenterDemo {
    public static void main(String args[]){
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        Thread t3 = new Thread(phone,"t3");
        t3.start();
        Thread t4 = new Thread(phone,"t4");
        t4.start();
    }
}
