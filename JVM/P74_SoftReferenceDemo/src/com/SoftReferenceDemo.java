package com;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {

    public static void main(String args[]){
        //softRef_Memory_Enough();

        softRef_Memory_NotEnough();
    }

    /**
     * 内存够用，回收软引用
     */
    private static void softRef_Memory_Enough() {
        Object object = new Object();
        SoftReference<Object> softReference = new SoftReference(object);
        System.out.println(object);
        System.out.println(softReference.get());
        object = null;
        System.gc();

        System.out.println(object);
        System.out.println(softReference.get());
    }

    /**
     * 设置运行参数-Xms10m -Xmx10m -XX:+PrintGCDetails，故意产生大对象并配置小内存
     * 内存不够用，回收软引用
     */
    private static void softRef_Memory_NotEnough() {
        Object object = new Object();
        SoftReference<Object> softReference = new SoftReference(object);
        System.out.println(object);
        System.out.println(softReference.get());
        object = null;

        try{
             Byte[] bytes = new Byte[20*1024*1024];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(object);
            System.out.println(softReference.get());
        }

    }
}
