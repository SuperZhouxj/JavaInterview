package com;

import java.time.Instant;

/**
 * 懒汉式：
 *  延时创建这个实例对象
 *
 *  （1）构造器私有化
 *  （2）用一个静态变量保存这个唯一的实例
 *  （3）提供一个静态方法，获取这个实例对象
 *
 *  针对Singleton4线程不安全修改，加锁，适合多线程
 */
public class Singleton5 {
    private static Singleton5 INSTANCE;
    public Singleton5(){

    }
    public static Singleton5 getINSTANCE(){
        if (INSTANCE == null){
            synchronized (Singleton5.class){
                if (INSTANCE==null){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new Singleton5();
                }
            }
        }
        return INSTANCE;
    }
}
