package com;

import java.lang.ref.WeakReference;

public class WeakReferenceDemo {

    public static void main(String args[]) {
        Object object = new Object();
        WeakReference<Object> softReference = new WeakReference(object);
        System.out.println(object);
        System.out.println(softReference.get());
        object = null;
        System.gc();

        System.out.println(object);
        System.out.println(softReference.get());
    }
}
