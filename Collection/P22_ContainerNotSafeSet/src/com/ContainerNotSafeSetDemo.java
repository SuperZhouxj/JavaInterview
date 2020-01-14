package com;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 原理类似于P20_ContainerNotSafeDemo
 * HashSet底层实现是HashMap
 */

public class ContainerNotSafeSetDemo {
    public static void main(String args[]) {
        Set<String> set = new CopyOnWriteArraySet<>();
        //Set<String> set = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}

