package com;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable{
    private String lockA;
    private String LockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.LockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 自己持有："+lockA+"\t 尝试获取："+LockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (LockB){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有："+LockB+"\t 尝试获取："+lockA);
            }
        }
    }
}

public class DemoLockDemo {
    public static void main(String args[]){
          new Thread(new HoldLockThread ("lockA","lockB"),"ThreadAAA").start();
          new Thread(new HoldLockThread ("lockB","lockA"),"ThreadBBB").start();
    }
}
