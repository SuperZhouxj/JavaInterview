package com;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Mydate{
    //int number = 0;
    //添加volatile验证JMM的可见性：一个线程修改主内存值后，及时通知其他线程
    volatile  int number = 0;
    public void add(){
        this.number =60;
    }

    public void addPlusPlus(){
        number++;
    }

    /**
     * 如何解决volatile不支持原子性
     * 1.方法上加上synchronized；
     * 2.使用JUC的atomicInteger；
     */
    AtomicInteger atomicInteger = new AtomicInteger();
    public void atomicAdd(){
        atomicInteger.incrementAndGet();
    }
}

public class Visibility {
    public static void main(String args[]){
        seeOkByvolatile();
        //badAtomicforvolatile();
    }
    //volatile不能保证原子性
    private static void badAtomicforvolatile() {
        Mydate mydate = new Mydate();
        for(int i =0;i<20;i++){
            new Thread(()->{
                for (int j =0;j<1000;j++){
                    mydate.addPlusPlus();
                    mydate.atomicAdd();
                }
            },String.valueOf(i)).start();
        }
        //主程序中始终有两个线程，主线程和GC线程
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"finally number value:"+ mydate.number);
        System.out.println(Thread.currentThread().getName()+"AtomicInteger finally number value:"+ mydate.atomicInteger);
    }

    //volatile可以保证可见性，及时通知其他线程，主物理内存的值已经修改
    private static void seeOkByvolatile() {
        Mydate mydate = new Mydate();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                //暂停线程一会
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mydate.add();
            System.out.println(Thread.currentThread().getName() + "\t update number value:"+mydate.number);
        },"AAA").start();

        while (mydate.number ==0){

        }
        System.out.println(Thread.currentThread().getName() + "\t ");
    }

}

