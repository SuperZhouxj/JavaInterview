package com;

public class HeapSizeDemo {
    public static void main(String args[]){
        long totalMemory = Runtime.getRuntime().totalMemory();//返回java虚拟机中的内存总量，一般为物理内存的1/64
        long maxMemory = Runtime.getRuntime().maxMemory();//返回java虚拟机试图使用的最大内存量，一般为物理内存的1/4
        System.out.println("Total_Memory(-Xms):"+totalMemory+"字节，"+totalMemory/(double)1024/1024+"MB");
        System.out.println("Total_Memory(-Xmx):"+maxMemory+"字节，"+maxMemory/(double)1024/1024+"MB");
    }
}
