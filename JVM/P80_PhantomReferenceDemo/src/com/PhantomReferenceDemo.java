package com;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * java提供了四种引用类型，在垃圾回收的时候，都各自有各自的特点
 * ReferenceQueue是用来配合引用工作的，没有ReferenceQueue一样可以运行
 *
 * 创建引用的时候可以指定关联的队列，当GC释放对象内存的时候，会引起加入到引用队列，
 * 如果程序发现某个虚引用已经被加入到引用队列，那么就在所引用的对象的内存内回收之前采取必要的行动，
 * 这相当于是一种通知机制
 *
 * 当关联的引用队列中有数据的时候，意味着引用指向的堆内存中的额对象被回收，通过这种方式
 * JVM允许我们在对象被销毁之后，做一些我们自己想做的事情。
 */

public class PhantomReferenceDemo {
    public static void main(String args[]) throws InterruptedException {{
        Object object = new Object();
        //回收前引用队列保存下
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(object,referenceQueue);

        System.out.println(object);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("***************");
        object = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(object);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }

    }
}
