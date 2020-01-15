## P1 几大公司面试必问

-JVM/GC

-多线程与高并发（JUC）

-Java集合类

## P2 Volitile

`volatile`:Java虚拟机提供的轻量级的同步机制。

-保证可见性

-不保证原子性

-禁止指令重排

并发包：`java.util.concurrent` `java.util.concurrent.atomic` `java.until.concurrent.locks`

并发和并行的区别
并发：多个线程同时访问一个资源（秒杀）
并行：多个线程（事情）同时去做

## P32 CountDownLatch


`CountDownLatch` 主要有两个方法，当一个或者多个线程调用`await` 方法时，调用线程会被阻塞，其他线程调用`countdown`
方法会将计数器减1（调用线程不会阻塞），当计数器的值为0时，因调用`await`方法被阻塞的线程会被唤醒，继续执行。

```java
public static void main(String args[]) throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for(int i =1;i<=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 灭亡");
                countDownLatch.countDown();
            },CountryEnum.check(i).getMessage()).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"统一六国");
    }
```
