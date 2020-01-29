package com;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * 弱引用的应用WeakHashMAp，系统垃圾回收后被回收
 */
public class WeakHashMapDemo {
    public static void main(String args[]){
        //对比HashMap
        myHashMap();
        System.out.println("***************");
        myWeakHashMap();
    }

    private static void myHashMap() {
        HashMap<Integer,String> hashMap = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";
        hashMap.put(key,value);
        System.out.println(hashMap);
        //HashMap底层源码是Node<K,V>，这里的key置空，底层node的key没有变化,15行的key是指向Integer的引用。
        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap);
    }


    private static void myWeakHashMap() {
        WeakHashMap<Integer,String> hashMap = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "WeakHashMap";
        hashMap.put(key,value);
        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap);
    }

}
