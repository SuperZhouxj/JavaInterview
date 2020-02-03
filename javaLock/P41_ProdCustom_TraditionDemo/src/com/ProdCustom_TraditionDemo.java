package com;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产一个，消费一个
 */
class ShareDate{
    private Integer number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increase(){
        lock.lock();
        try {
            //判断
            while (0!=number){
                //等待，不生产
                condition.await();
            }
            //干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void decrease(){
        lock.lock();
        try {
            while (0==number){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class ProdCustom_TraditionDemo {
    public static void main(String args[]){
        ShareDate shareDate = new ShareDate();
        for(int i =1;i<=5;i++){
            new Thread(()->{
                shareDate.increase();
            },"AA").start();
        }

        for(int i =1;i<=5;i++){
            new Thread(()->{
                shareDate.decrease();
            },"BB").start();
        }
    }

}
