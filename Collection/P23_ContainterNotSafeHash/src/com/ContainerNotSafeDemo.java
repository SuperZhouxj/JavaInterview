package com;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 原理类似于P20_ContainerNotSafeDemo
 * 只是这个是ConcurrentHashMap,不是CopyOnWrite
 */

public class ContainerNotSafeDemo {
    public static void main(String args[]){
        Map<String,String> map = new ConcurrentHashMap<>();
        for(int i =0;i<20;i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}

