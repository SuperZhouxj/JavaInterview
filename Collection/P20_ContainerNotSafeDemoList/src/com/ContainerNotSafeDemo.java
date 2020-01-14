package com;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContainerNotSafeDemo {
    public static void main(String args[]){
        //List<String> list = Arrays.asList("a","b","c");
        //list.forEach(System.out::println);

        //List<String> list = new ArrayList<>();
        //List<String> list = new Vector<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        for(int i =0;i<20;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
            //使用List<String> list = new ArrayList<>()会出现java.util.ConcurrentModificationException

            /**
             * 1.故障现象
             *  java.util.ConcurrentModificationException
             *  2.故障原因
             *   并发修改争抢导致。参考花名册签名情况：
             *   一个同学正在写入，另一个同学过来争抢，导致数据不一致异常。即并发修改异常
             *  3.解决方法
             *    3.1 new Vector<>()
             *    3.2 new Collections.synchronizedList(new ArrayList<>())
             *    3.3 new CopyAndWriteArrayList<>()
             *    写时复制的容器，往一个容器中添加数据时，不直接往当前容器Object中添加 ，
             *    而是将当前容器复制一个新容器，在新容器中添加数据，加完元素以后，再将元容器的引用指向新容器，
             *    这样做的好处是对Copy'A'n'dWirte容器进行并发的读，而不需要加锁，因为不在当前容器中添加任何元素，
             *    读写分离思想，读和写不同的容器
             *    public boolean add(E e) {
             *         final ReentrantLock lock = this.lock;
             *         lock.lock();
             *         try {
             *             Object[] elements = getArray();
             *             int len = elements.length;
             *             Object[] newElements = Arrays.copyOf(elements, len + 1);
             *             newElements[len] = e;
             *             setArray(newElements);
             *             return true;
             *         } finally {
             *             lock.unlock();
             *         }
             *     }
             *
             *  4.优化建议（同样的错误不犯第二次）
             */
        }
    }
}
