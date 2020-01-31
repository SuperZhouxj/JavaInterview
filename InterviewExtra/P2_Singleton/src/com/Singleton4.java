package com;

/**
 * 懒汉式：
 *  延时创建这个实例对象
 *
 *  （1）构造器私有化
 *  （2）用一个静态变量保存这个唯一的实例
 *  （3）提供一个静态方法，获取这个实例对象
 *
 *  线程不安全，适合单线程，具体多线程案例见测试类
 */
public class Singleton4 {
    private static Singleton4 INSTANCE;
    public Singleton4(){

    }
    public static Singleton4 getINSTANCE(){
        if (INSTANCE==null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new Singleton4();
        }
        return INSTANCE;
    }
}
