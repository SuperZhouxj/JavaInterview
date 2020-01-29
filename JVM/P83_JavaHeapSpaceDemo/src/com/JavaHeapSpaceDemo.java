package com;

/**
  * java.lang.OutOfMemoryError: Java heap space
 */
public class JavaHeapSpaceDemo {
    public static void main(String args[]){
       Byte[] bytes = new Byte[80*1024*1024];
       //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    }
}
