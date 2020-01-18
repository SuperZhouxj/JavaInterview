package com;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Synchronized实现精确唤醒比较困难，ReentrantLock 可实现分组唤醒需要唤醒的线程们，可以精确唤醒，
 * 而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程。
 * 题目：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
 * A打印5次，B打印10次，C打印15次
 * 接着
 * A打印5次，B打印10次，C打印15次
 * 循环10轮
 */

class ResourceData{
    private Integer number =1 ;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try{
            //判断
            while (1!=number){
                c1.await();
            }
            //干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            number++;
            //通知唤醒
            c2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try{
            //判断
            while (2!=number){
                c2.await();
            }
            //干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            number++;
            //通知唤醒
            c3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try{
            //判断
            while (3!=number){
                c3.await();
            }
            //干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            number = 1;
            //通知唤醒
            c1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

public class ReentrantLockDemo {
    public static void main(String args[]){
        ResourceData resourceData = new ResourceData();
            //难理解，a线程执行一次，跳到B线程，执行一次跳到C线程，执行完再跳回A线程执行，以此循环10次
            new Thread(()->{
                for (int j = 0; j < 10; j++) {
                    resourceData.print5();
                }
            },"A").start();
            new Thread(()->{
                for (int j = 0; j < 10; j++) {
                    resourceData.print10();
                }
            },"B").start();
            new Thread(()->{
                for (int j = 0; j < 10; j++) {
                    resourceData.print15();
                }
            },"C").start();

    }
}
