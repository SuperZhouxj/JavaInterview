package com;

/**
 * -XX:+PrintGCDetails -XX:+UseSerialGC -Xms10m -Xmx10m -XX:SurvivorRatio=4
 * Heap
 *  def new generation   total 2880K, used 1728K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
 *   eden space 2368K,  72% used [0x00000000ff600000, 0x00000000ff7b0060, 0x00000000ff850000)
 *   from space 512K,   0% used [0x00000000ff850000, 0x00000000ff850000, 0x00000000ff8d0000)
 *   to   space 512K,   0% used [0x00000000ff8d0000, 0x00000000ff8d0000, 0x00000000ff950000)
 *  tenured generation   total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
 *    the space 6848K,   0% used [0x00000000ff950000, 0x00000000ff950000, 0x00000000ff950200, 0x0000000100000000)
 *  Metaspace       used 3165K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 345K, capacity 388K, committed 512K, reserved 1048576K
 */
public class JVMSurvivorRatioDemo {
    public static void main(String args[]){
        System.out.println("************JVMSurvivorRatioDemo**********");
    }
}
