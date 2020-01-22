package com;


public class JVMPrintGCDetailsDemo {
    /**
     * 配置运行参数-XX:+PrintGCDetails -XX:+UseSerialGC -Xms10m -Xmx10m，打印：
     *  Heap
     *  def new generation   total 3072K, used 1935K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
     *   eden space 2752K,  70% used [0x00000000ff600000, 0x00000000ff7e3ec8, 0x00000000ff8b0000)
     *   from space 320K,   0% used [0x00000000ff8b0000, 0x00000000ff8b0000, 0x00000000ff900000)
     *   to   space 320K,   0% used [0x00000000ff900000, 0x00000000ff900000, 0x00000000ff950000)
     *  tenured generation   total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
     *    the space 6848K,   0% used [0x00000000ff950000, 0x00000000ff950000, 0x00000000ff950200, 0x0000000100000000)
     *  Metaspace       used 3425K, capacity 4496K, committed 4864K, reserved 1056768K
     *   class space    used 372K, capacity 388K, committed 512K, reserved 1048576K
     */
//    public static void main(String args[]){
//        System.out.println("*******HelloDemo");
//    }

    /**
     * -XX:+PrintGCDetails -XX:+UseSerialGC -Xms10m -Xmx10m
     * OOM
     * 申请最大堆内存10M，实际使用申请20M，OOM错误
     * [GC (Allocation Failure) [DefNew: 1880K->320K(3072K), 0.0128154 secs][Tenured: 307K->626K(6848K), 0.0140160 secs] 1880K->626K(9920K), [Metaspace: 3392K->3392K(1056768K)], 0.0677738 secs] [Times: user=0.00 sys=0.00, real=0.07 secs]
     * [Full GC (Allocation Failure) [Tenured: 626K->608K(6848K), 0.0019325 secs] 626K->608K(9920K), [Metaspace: 3392K->3392K(1056768K)], 0.0019659 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * 	at com.JVMPrintGCDetailsDemo.main(JVMPrintGCDetailsDemo.java:22)
     */
    public static void main(String args[]){
        Byte[] bytes = new Byte[20*1024*1024]; //20M
    }
}
