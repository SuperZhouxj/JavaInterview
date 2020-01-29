package com;

/**
 * 在JVM中如果98%的时间是用于GC(Garbage Collection)且可用的 Heap size
 * 不足2%的时候将抛出异常信息，java.lang.OutOfMemoryError: Java heap space
 */
public class JavaHeapSpaceDemo {
    public static void main(String args[]){
       Byte[] bytes = new Byte[80*1024*1024];
       //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    }
}
