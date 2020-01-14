package com;


public class SingletonDemo {
    //加锁volatile禁止指令重排，DCL不一定线程安全，有指令重排的存在
    private static volatile SingletonDemo instance;
    public SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 我的构造方法SingletonDemo");
    }
    public static SingletonDemo getInstance(){
        //DCL(Double Check Lock )双端检锁机制
        if (instance == null){//加锁前判断
            synchronized (SingletonDemo.class){
                if (instance == null)//加锁后判断
                    instance = new SingletonDemo();
            }
        }
        return instance;
    }

    public static void main(String args[]){
        //单线程（main函数的操作动作。。。。）
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        //并发多线程下，情况发生了很大变化
        for(int i =0;i<10;i++){
            new Thread(()->{
               SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
