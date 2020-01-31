package com;

/**
 * 懒汉式：
 *  延时创建这个实例对象
 *
 *  （1）构造器私有化
 *  （2）用一个静态变量保存这个唯一的实例
 *  （3）提供一个静态方法，获取这个实例对象
 *
 *  针对Singleton5加锁过于复杂修改，适合多线程
 *
 *  重点：
 *  在内部类被加载和初始化时，才创建INSTANCE实例对象
 *  静态内部类不会自动随着外部类的加载和初始化而初始化，它是要单独去加载和初始化
 *  因为是在内部类加载和初始化时，创建的，因此线程安全的
 */
public class Singleton6 {
    private Singleton6(){
    }
    private static class Inner{
        private static Singleton6 INSTANCE = new Singleton6();
    }
    public static Singleton6 getINSTANCE(){
        return Inner.INSTANCE;
    }
}
