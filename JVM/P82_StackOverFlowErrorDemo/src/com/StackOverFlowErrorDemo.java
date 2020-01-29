package com;

/**
 * 栈溢出错误，这是错误，并非异样
 * 栈空间512K-1024K
 * 当栈深度超过虚拟机分配给线程的栈大小时就会出现此error。
 * 常发生于方法的无限递归调用（即方法递归没有带上结束递归的条件）
 */
public class StackOverFlowErrorDemo {
    public static void main(String args[]){
        stackOverFlowError();
    }

    private static void stackOverFlowError() {
        stackOverFlowError();//Exception in thread "main" java.lang.StackOverflowError
    }
}
