package com;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * 运行参数：-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 * 异常：Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
 * 直接内存溢出
 */
public class DirectBufferMemoryDemo {
    public static void main(String args[]){
        System.out.println("配置的maxDirectMemory:"+sun.misc.VM.maxDirectMemory()/(double)1024/1024+"MB");
        try {
            TimeUnit.SECONDS.sleep(1);}catch (Exception e){e.printStackTrace();}
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6*1024*1024);
    }
}
