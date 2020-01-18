package com;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;
    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void MyProd() throws Exception{
        String data = null;
        boolean retValue;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L, TimeUnit.SECONDS);
            if (retValue){
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t大老板叫停，生产动作结束");
    }

    public void MyConsumer() throws Exception{
        String result = null;
        while (FLAG){
            result = blockingQueue.poll(2L,TimeUnit.SECONDS);
            if (null==result||"".equals(result)){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t超过两秒没有取到，消费退出");
                System.out.println("");
                System.out.println("");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消费队列取出"+result+"成功");
        }
    }
    public void stop(){
        FLAG = false;
    }
}

public class ProdCustmer_BlockQueueDemo {
    public static void main(String args[]) throws Exception{
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t生产线程启动");
            try {
                myResource.MyProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Prod").start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t消费线程启动");
            System.out.println("");
            System.out.println("");
            try {
                myResource.MyConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Consumer").start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("");
        System.out.println("");
        System.out.println("5秒时间到，大老板main线程叫停，活到结束");
        myResource.stop();
    }
}
