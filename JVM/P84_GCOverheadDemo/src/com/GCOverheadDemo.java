package com;

import java.util.ArrayList;
import java.util.List;
/**
 * 在JVM中如果98%的时间是用于GC(Garbage Collection)且回收了不到2%的堆内存
 * java.lang.OutOfMemoryError: GC overhead limit exceeded
 * 运行参数：-Xms10m -Xmx10m -XX:+PrintGCDetails
 */
public class GCOverheadDemo {
    public static void main(String args[]){
        Integer i = 1;
        List<String> list = new ArrayList<>();

            try {
                while (true) {
                    list.add(String.valueOf(i++).intern());
                }

            }catch (Throwable e){
                System.out.println("**************i:"+i);
                e.printStackTrace();
                throw e;
            }

    }
}
