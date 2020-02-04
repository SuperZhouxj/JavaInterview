package com;

/**
 * 强引用
 */
public class StrongReferenceDemo {
    public static void main(String args[]){
        Object object1 = new Object(); //这样定义的默认就是强引用,把一个对象赋值给一个引用变量，这个引用变量就是个强引用
        Object object2 = object1;//object2引用赋值
        object1 = null;
        System.gc();
        System.out.println(object1);
        System.out.println(object2);
    }
}
