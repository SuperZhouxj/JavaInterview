package com;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String args[]) throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for(int i =1;i<=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 灭亡");
                countDownLatch.countDown();
            },CountryEnum.check(i).getMessage()).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"统一六国");
    }
}

