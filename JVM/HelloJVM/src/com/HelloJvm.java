package com;

import java.util.concurrent.TimeUnit;

/**
 * jps -l 查看java的进程pid
 * jinfo -flags pid  查看JVM的XX所有参数，包括根据硬件系统的默认设置（Non-default）和自定义设置（Command line）
 */
public class HelloJvm {
    public static void main(String args[]) throws InterruptedException {
        System.out.println("********JVMTEST");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
