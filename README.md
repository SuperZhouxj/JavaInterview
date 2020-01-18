## P1 几大公司面试必问

-JVM/GC

-多线程与高并发（JUC）

-Java集合类

## P2 Volitile

`volatile`:Java虚拟机提供的轻量级的同步机制。

-保证可见性

-不保证原子性

-禁止指令重排

并发包：`java.util.concurrent` `java.util.concurrent.atomic` `java.util.concurrent.locks`

**并发和并行的区别**
并发：多个线程同时访问一个资源（秒杀）
并行：多个线程（事情）同时去做

## P3 JMM(Java内存模型)之可见性
### JMM Java Memory Model
本身是抽象的概念，它描述的是一组规则或规范，通过者个规范定义了程序中各个变量
（实例字段，静态字段和构成数组对象的元素）的访问方式。

![Java内存模型](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P3_JMM.PNG)

每个线程都有自己的工作内存（私有工作区域），JMM规定所有变量存在主内存中，
如果线程对变量操作，要将主内存中变量拷贝到自己的内存操作，完成再写回主内存，
线程间的通信（传值）必须通过主内存完成。
### JMM可见性
一个线程修改主内存值后，及时通知其他线程的机制

## P4 可见性的代码验证说明
### JMM特性
-原子性

-可见性

-有效性

**原子性**
不可分割完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，
需要整体完整，要么同时成功，要么同时失败

## P7 Volatile不保证原子性问题解决
1.方法加上`synchronized`
2.使用JUC下的`AtomicIntefer`

JUC:`java.util.concurrent`包，并发编程常用工具包

## P8 Volatile禁止指令重排
PC执行程序时，为提高性能，编译器与处理器常对指令进行重排，一般分三种：

源代码->编译器优化重排->指令并行重排->内存系统重排->最终执行指令

多线程环境中线程交替执行，由于编译器优化重排存在，两个线程中使用变量
能否保证一致性无法确定，结果无法预测

## P9 工作内存和主内存延迟现象导致可见性问题
可以使用synchronized或者volatile关键字解决，它们都可以使用一个线程修改后的变量立即
对其他线程可见

## P10 单例模式在多线程环境下可能存在安全问题
**单例模式DCL**
```java
public static SingletonDemo getInstance(){
        //DCL(Double Check Lock )双端检锁机制
        if (instance == null){//加锁前判断
            synchronized (SingletonDemo.class){
                if (instance == null)//加锁后判断
                    instance = new SingletonDemo();
            }
        }
        return instance;
    }
```

## P11 单例模式volatile分析
DLC机制不一定线程安全，原因是有指令重排的存在，加入volatile可以禁止指令重排

## P12 CAS是什么
### CAS（Compare And Swap 比较并交换）
如果线程的期望值和主物理内存的真实值不一样，则修改更新值，操作为true，如果不一样，则需要重新获取物理内存真实值，返回false，修改失败

## P13 CAS底层原理：自旋锁 unsafe类
### unsafe类
CAS核心类，由于Java方法无法直接访问底层系统，需要通过本地（native）方法来访问，unsafe类相对于后面，可以直接操作
特定内存的数据，该类存在于`rt.jar`的`sun.misc`包中，其内部方法操作可以像C语言的指针一样直接操作内存，因此Java中CAS操作
依赖unsafe类的方法

注：unsafe类中的所有方法都是native修饰的，也就是说unsafe类中的方法都直接调用操作系统底层资源执行相应任务

## P14 CAS总结
比较当前工作内存中的值与主内存中的值，如果相同则执行规定操作，否则继续比较直到主内存和工作内存中的值一致为止

## P15 CAS缺点
**synchronized** 一致性保证，并发性下降，加锁，一个一个程序执行

**CAS**保证一致性，并发性，但需要比较多次（不加锁）

**CAS缺点**
1.循环时间长，开销很大的，`do while` 实现，如果不成功，将一直循环
2.只能够保证一个共享变量的原子操作
3.引来ABA问题

## P16 ABA问题
### 面试问题
CAS->unsafe->CAS底层思想（AtomicIngeger）->ABA->原子引用更新->如何规避ABA问题

**ABA问题**CAS算法实现一个重要前提需要取出内存中某时刻数据，下时刻比较并替换，那么这个时间差就会数据有可能变化

eg.两个线程one,two从内存位置V中取出A，线程two操作将值变成B，然后two又将位置V数据变成A，这是线程one进行CAS操作发现内存仍然为A，然后操作并成功，
尽管one的CAS操作成功，并不代表这个过程没有问题。

## P17 AtomicReference原子引用
多线程并发中有ABA问题

## P19 AtomicStampedReference版本号原子引用
解决CAS中ABA问题

# Collection
## P20 集合类不安全之并发修改异常
`ArrayList`HashSet``HashMap`多线程下并发不安全

**解决**：`CopyOnWriteArrayList``CoyOnWriteArraySet``ConCurrentHashMap`

## P22 结合不安全Set
HashSet底层就是HashMap实现

HashSet add是一个值，HashMap是K，V对，实际HashSet的add也是用HashMap的add，只是只添加key,value是Object类型的常量，只关系K,不关心V

# JavaLock
## Java锁之公平和非公平锁
**公平锁**多个线程安装申请锁的顺序来获取锁，类似排队打饭，先来后到

**非公平锁**多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁，在高并发的情况下，有可能会造成优先级反转或者饥饿现象

并发包的ReentranLock的创建可以指定构造函数的Boolean类型来得到公平锁和非公平锁，默认的是非公平锁。非公平锁的优点在于吞吐量比公平锁大。对于
Synchronized而言，也是一种非公平锁。

## P26 可重入锁（又名递归锁）
**可重入锁**：同一个线程外层函数获取锁后，内层递归函数仍然能获取得到该锁的代码，同一个线程在外层方法获取锁的时候，在进入内层的方法会自动获取锁，
也就是说，线程可以进入任何一个它已经拥有的锁所同步着的代码块。

*`ReentrantLock``Synchronized`*是可重入锁，可重入锁最大的作用是避免死锁。

## P28 自旋锁（SpinLock）
**自旋锁**：尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。

## P30 Java读写锁（ReentrantReadWriteLock）
**独占锁**：该锁一次只能被一个线程所持有

*`ReentrantLock``Synchronized`*都是独占锁

**共享锁**：该锁可被多个线程所持有

对ReentrantReadWriteLock其读锁是共享锁，写锁是独占锁，读锁可保证并发读是高效的，读写/写读/写写过程互斥，不共存。

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

## P33 CyclicBarrier
CyclicBarrier字面的意思可循环使用的屏障，它可以做的事情是，让一组线程到达屏障（也可以叫同步点）时被阻塞，
直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程继续干活。线程进入屏障通过CyclicBarrier的await()方法。

```java
public static void main(String args[]){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{System.out.println("*****召唤神龙");});
        System.out.println("");
        for(int i =1;i<=7;i++){
            final int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t 收集第"+ temp +"龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
```

## P34 Samephore
信号量主要用于两个目的：一个时用于多个共享资源的互斥使用，一个用于并发线程数的控制，类似与争车位。

## P36  阻塞队列（BlockingQueue）
![阻塞队列模型](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P36_blockQueue.PNG)

试图从空的阻塞队列中获取元素的线程将会被阻塞，直到其它的线程往空的队列插入新的元素。
同样，试图往已满的阻塞队列中添加新元素的线程同样也会被阻塞，直到其它的线程从列中移除
一个或者多个元素或者完全清空队列后使队列重新变得空闲起来并后续增加。

在多线程领域：所谓阻塞，在某些情况下会挂起线程（即阻塞），一旦添加满足，被挂起的先后又会被自动
唤醒。

**为什么需要BlockingQueue?**

好处是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，英文这一切Blockingqueue
都给你一手包办了

**BlockingQueue种类**

![阻塞队列种类](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P36_BlockingQueueClassPNG.PNG)

## P40 阻塞队列之同步SynchronousQueue队列
SynchronousQueue没有容量。与其他BlockingQueue不同，SynchronousQueue是一个不存储元素的BlockingQueue
每个put操作必须等待一个take操作，否则不能继续添加元素，反之亦然。

## P42 Synchronized和Lock的区别
![阻塞队列种类](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P42_SynchronizedandLockDifference.PNG)
