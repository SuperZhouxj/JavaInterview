**视频网址：https://www.bilibili.com/video/av64429441**

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

## P27 可重入锁（又名递归锁）
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

## P46 线程池的使用和优势
![线程池的使用和优势](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P46_ThreadPool.PNG)

## P47 线程池的常用三种方式
![线程池newFixedThreadPool](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P47_newFixedThreadPool.PNG)

![线程池newSingleThreadExecutor](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P47_newSingleThreadExecutor.PNG)

![线程池newCachedThreadPool](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P47_newCachedThreadPool.PNG)

**获得和使用Java多线程的4中方法**
- 继承Thread类

- 实现Runable接口，Runable接口没有返回值，不抛异常

- 实现Callable接口，有返回值，会抛异常。

- 通过线程池

## P49 线程池7大参数
![线程池七大参数](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P49_ThreadPoolSeverAugs.PNG)

### corePoolSize
1 在创建了线程池后，当有请求任务来了之后，就会安排池中的线程去执行请求任务，近似理解为今日当值线程

2 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中。
### maximunPoolSize

## P50 线程池工作原理
![线程池工作原理](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P50_ThreadPoolTheory.PNG)


![线程池工作原理描述](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P50_ThreadPoolTheoryDesc.PNG)

## P51 线程池的拒绝策略
**是什么**

等待队列也已经排满了，再也塞不下新任务了，同时，线程池中的max线程也达到了，无法继续为新任务服务。
这个时候我们就需要拒绝策略机制合理处理这个问题

**JDK内置的拒绝策略**

![JDK内置的拒绝策略](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P51_handler.PNG)

补：从而降低新任务的流量

## P52 线程池实际中使用那个
eg.你在工作中单一的/固定数的/可变的三种创建线程池的方法，你用哪个？

答：一个都不用，我们生产上自能使用自定义的

![线程池使用哪个](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P52_ThreadPoolToUseWhich.PNG)

## P54 线程池配置合理线程数
**CPU密集型**

CPU密集型任务配置尽可能少的线程数量；

一般公式：CPU核数+1个线程的线程池

**IO密集型**

- 由于IO密集型任务线程并不是一直在执行任务，则应配置尽可能多的线程
如CPU核数*2

- ![IO密集](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P54_IOMuch.PNG)

## P55 死锁的编码和定位分析
![死锁的定义](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P55_WhatDeadLock.PNG)

jps -l   显示当前所有java线程的pid命令
jstack：打印出给定的java进程PID或core file或远程调试服务的Java**堆栈信息**

# JVM
## P57 JVM体系结构
![JVM体系结构](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P56_JVMStruct.PNG)

**类装载器**
分类：根类加载器，扩展类加载器，系统类加载器；

考点：双亲委派机制，沙箱安全机制 

参见https://blog.csdn.net/zhmi_1015/article/details/93966942

**运行时数据区**
灰色的线程私有，与GC无关；粉色的线程共有，这部分和GC相关

**JVM GC算法**
![引用计数](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P56_GClgorithm1.PNG)

![复制](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P56_GClgorithm2.PNG)

复制算法多用于新生代

![标记清除](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P56_GClgorithm3.PNG)

标记清除优点：没有进行大规模复制，节约了内存空间。缺点：会产生内存碎片

![标记整理](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P56_GClgorithm4.PNG)

标记整理算法优点：不浪费空间，也不产生内存碎片，缺点：耗时（要整理）

标记清除和标记整理算法多用于老生代

## P58
枚举根节点做可达性分析（根搜索路径）

![根搜索路径](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P58_GCRoot.PNG)

![可以作为GCRoot对象](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P58_GCRootObject.PNG)

## P59 JVM的标配参数和X参数
**JVM参数类型**
- 标配参数(了解)：-version/-help/-showversion
- X参数（了解）：-Xint(解释执行)/-Xcomp(第一次使用就编译成本地代码)/-Xmixed(混合模式)
**- XX参数（重点）**
1.Boolean类型：-XX:+或者-某个属性值（+表示开启；-表示关闭）
2.KV设值类型：-XX:属性key=属性值value

## P63 JVM的XX参数之Xms Xmx坑题
-Xms等价于-XX:InitialHeapSize  JVM堆内存初始值    默认为物理内存的1/64

-Xmx等价于-XX:MaxHeapSize   JVM堆内存最大分配值    默认为物理内存的1/4

## P64 JVM盘点家底查看初始默认值
### 查看JVM默认值
**命令**：java -XX:+PrintFlagsInitial -version   --主要查看初始默认

**命令**：java -XX:+PrintFlagsFinal -version  --主要查看修改更新

**命令**：java -XX:+PrintCommandLineFlags -version  --主要查看命令行参数（参数中有JVM默认的垃圾回收器）

## P66 堆内存初始大小
![堆内存基本知识](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P66_HeapSize.PNG)

```java
long totalMemory = Runtime.getRuntime().totalMemory();//返回java虚拟机中的内存总量，默认为物理内存的1/64
        long maxMemory = Runtime.getRuntime().maxMemory();//返回java虚拟机试图使用的最大内存量，默认为物理内存的1/4
        System.out.println("Total_Memory(-Xms):"+totalMemory+"字节，"+totalMemory/(double)1024/1024+"MB");
        System.out.println("Total_Memory(-Xmx):"+maxMemory+"字节，"+maxMemory/(double)1024/1024+"MB");
```

## P67 常用基础参数栈内存Xss
**栈管运行，堆管存储**

### JVM常用参数
![JVM常用参数](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P67_JVMCommonParams.PNG)

**-Xss**等价于—XX:ThreadStackSize   设置单个线程栈的大小，一般默认为512K~1024K

## P68 常用基础参数元空间MetaspaceSize
![JVM常用参数](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P68_MetaspaceSize.PNG)

ps:查看某个线程的JVM参数：jinfo -flag InitialHeapSize（MetaspaceSize） PID

## P69 常用基础参数PrintGCDetails回收前后对比
![PrintGCDetails回收前后对比](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P69_GCLog.PNG)

![PrintGCDetails回收前后对比](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P69_GCLog1.PNG)

## P70 常用基础参数SurvivorRatio
![常用基础参数SurvivorRatio理论](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P70_SurvivorRatioTheory.PNG)

![常用基础参数SurvivorRatio](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P70_SurvivorRatio.PNG)


## P71 常用基础参数NewRatio
![常用基础参数NewRatio](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P71_NewRatio.PNG)

## P72 常用基础参数MaxTenuringThreshold
设置垃圾最大年龄

## P73 强引用Reference
![强引用Reference](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P73_Reference.PNG)

```java
 Object object1 = new Object();//等号左边是引用，在栈里面，等号右边是对象，在堆里面
```

## P74 软引用SoftReference
![软引用SoftReference](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P74_SoftReference.PNG)

## P75 弱引用WeakReference
对于只是弱引用的对象来说，只要垃圾回收机制一运行，不管JVM的内存空间是否足够，都会回收该对象占用的内存。

## P76 软引用和弱引用适用场景
![软需引用的适用场景](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P76_SoftWeakReferenceApplication.PNG)

## P78 虚引用
![虚引用PhantomReference](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P78_PhantomReference.PNG)

## P82 OOM
![OOM](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P82_OOM.PNG)

![OOM常见两个错误结构](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P82_OOMErrorStruct.PNG)

StackOverFlowError和Java heap space 都属于错误，而非异常

## P85 OOM之Direct Buffer Memory
![Direct Buffer Memory](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P85_DirectBufferMemory.PNG)

## P85 OOM之unable to create new native thread
![unable to create new native thread](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P86_UnableCreateNewThread.PNG)

## P85 OOM之Metaspace
![Metaspace](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P87_Metaspace.PNG)

## P90 主要的4种垃圾回收方式
- 串行垃圾回收器（Serial）
- 并行垃圾回收器（Parallel）
- 并发垃圾回收器（CMS）
- G1垃圾回收器

**串行垃圾回收器**
它为单线程环境设计且只使用一个线程进行垃圾回收，会暂停所有的用户线程。所以不适合服务器环境

**并行垃圾回收器**
多个垃圾回收线程并行工作，此时用户线程是暂停的，适用于科学计算/大数据处理首台处理等弱交互场景

**并发垃圾回收器**
用户线程和垃圾收集线程同时执行（不一定是并行，可以交替执行），不需要停顿用户线程。互联网公司多
用它，适用对响应时间有要求的场景。

**G1垃圾回收器**
将堆内存分割成不同的区域然后并发的对其进行垃圾回收

## P91 如何查看虚拟机默认的垃圾回收器
命令：java -XX:+PrintCommandLineFlags -version

## P92 JVM默认的垃圾回收器
java的gc回收的类型主要有几种：
`UseSerialGc`,`UseParallelGC`,`UseConcMarkSweepGC`,`UseParNewGC`,`UseParallelOldGC`,`UseG1GC`

## P93 GC的七种垃圾回收器
以HotSpot中包含的收集器为例：

![GC的七种垃圾回收器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P93_SevenGC.PNG)

## P95 GC之Serial收集器 
![GC之Serial收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P95_SerialGCDetails.PNG)

![GC之Serial收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P95_SerialGCDetails1.PNG)

## P96 GC之ParNew收集器 
![GC之ParNew收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P96_ParNewDetails.PNG)

![GC之ParNew收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P96_ParNewDetails1.PNG)

## P97 GC之Parallel收集器 
![GC之parallel收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P97_ParallelDetails.PNG)

![GC之parallel收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P97_ParallelDetails1.PNG)

![GC之parallel收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P97_ParallelDetails2.PNG)

## P98 GC之ParallelOld收集器 
![GC之ParallelOld收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P98_ParallelOldDetails.PNG)

## P99 GC之CMS收集器 
![GC之CMS收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P99_CMSDetails.PNG)

![GC之CMS收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P99_CMSDetails1.PNG)

![GC之CMS收集器步骤和特点](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P99_CMSGCStepAndFeature.PNG)

PS:由于并发进行，CMS在收集与应用线程会同时会增加对堆内存的占用，也就是说，CMS必须要在老年代堆内存
用尽之前完成垃圾回收，否则CMS回收失败，将触发担保机制，串行老年代收集器以STW的方式进行一次
GC，从而造成较大停顿时间。

## P100 GC之SerialOld收集器 
![GC之SerialOld收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P100_SerialOldGCDetails.PNG)

## P101 GC之垃圾收集器选择
![GC之垃圾收集器选择](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P101_GCChoose.PNG)

## P102 GC之G1收集器 
![GC之G1收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P102_G1GCDetails.PNG)

![GC之G1收集器](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P102_G1GCDetails1.PNG)

**G1收集器特点**

![GC之G1收集器特点](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P102_G1GCFeature.PNG)

## P103 GC之G1收集器底层原理
![GC之G1收集器底层原理](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P103_G1GCTheory.PNG)

![GC之G1收集器底层原理](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P103_G1GCTheory1.PNG)

**G1收集器底层回收步骤**

![GC之G1收集器底层回收步骤](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P103_G1GCStep.PNG)
 
![GC之G1收集器底层4步过程](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P103_G1GCStep1.PNG)


## P104 GC之G1参数配置及和CMS的比较
![G1收集器参数配置](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P104_G1GCArgs.PNG)
 
![G1收集器CMS的比较](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P104_G1CompareToCMS.PNG)

## P106 Linux命令之top
![Linux命令之top](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P106_LinuxCommand_Top.PNG)

## P116 Github_in限制搜索
![Github_in限制搜索](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P116_Github_in.PNG)

## P117 Github_star和fork范围搜索
![Github_star和fork范围搜索](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P117_Github_StarAndFork.PNG)

## P118 Github_awesome搜索
![Github_awesome搜索](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P118_Github_awesome.PNG)

## P119 Github_高亮显示代码（#L数字）
![Github_高亮显示代码（#L数字）](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P119_Github_Highlight.PNG)

## P121 Github_搜索区域活跃用户
![Github_搜索区域活跃用户](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/P121_Github_SearchUser.PNG)




**视频网址：https://www.bilibili.com/video/av64423441**

## P2 单例设计模式
单例设计模式：即某个类在整个系统中只能有一个实例对象可获取和使用的代码模式。

**要点**
- 某个类只能有一个实例；实现：构造器私有化
- 它必须自行创建这个实例；实现：含有一个该类的静态变量来保存这个唯一的实例
- 它必须自行向系统提供这个实例；实现：对外提供获取该实例对象的方式：1.直接暴露2.用静态变量的get方法获取

![单例模式常用几种形式](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P2_Singleton.PNG)

## P3 类的初始化和实例初始化
![类的初始化](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P3_ClassInit.PNG)

![实例对象初始化](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P3_ObjectInit.PNG)

![重写](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P3_Override.PNG)

## P4 方法的参数传递机制
![方法的参数传递机制](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P4_FunctionArgsTransmit.PNG)

![方法的参数传递机制代码分析](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P4_FunctionArgsTransmit1.PNG)

## P6 成员变量和局部变量
![成员变量和局部变量](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P6_Variable.PNG)

![成员变量和局部变量存储位置](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P6_Variable1.PNG)

![成员变量和局部变量区别](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P6_Variable2.PNG)

## P11 Mybatis中当实体类中的属性名和表中的字段不一致
**解决方案**

（1）写sql语句时起别名；

（2）在Mybatis的全局配置文件中开启驼峰命名规则

（3）在mapper映射文件中使用resultMap来自定义映射规则

## P13 Git分支和工作流
![Git分支](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P13_Git_Branch.PNG)

![Git工作量](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P13_Git_Flow.PNG)

## P14 redis持久化
**Redis提供了两种不同的持久化方式**
- RDB(Redis DataBase)

- AOF(Append Of File)

**RDB**
在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话的Snapshot快照，它
恢复时是将快照文件直接读到内存里

![RDB优缺点](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P14_Redis_RDB.PNG)

**AOF**
_以日志的形式来记录每个写操作_，将Redis执行过的所以写指令记录下来（读操作不记录），
只许追加文件不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis
重启的话就根据日志文件的内容将写指令从前到后执行一次已完成数据的恢复工作。

![AOF优缺点](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P14_Redis_AOF.PNG)

## P15 Mysql什么时候用上索引
![索引优缺点](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P15_Mysql_Index.PNG)

![索引适用不适用场合](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P15_Mysql_Index_Where.PNG)

ps:过滤性不好指加索引过滤的数据不多，比如：性别

## P16 JVM垃圾回收机制
**GC发生在JVM的堆内存中**

![GC种类和各自的算法](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P16_JVM_GC.PNG)

**复制算法**
- 优点：没有标记清除过程，效率高；没有内存碎片
- 缺点：需要双倍的内存空间

**标记清除**
- 优点：不需要额外空间
- 缺点：两个扫描，耗时严重；会产生内存碎片

**标记压缩（标记整理）**
- 优点：没有内存碎片
- 缺点：需要移动对象成本

## P18 Elasticsearch和Solr区别
![Elasticsearch和Solr区别](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P18_ESAndSolr.PNG)

## P19 单点登陆
**单点登陆**：一处登陆多处使用，一般多用于分布式系统中

![单点登陆](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P19_SingleLogin.PNG)

![单点登陆](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P19_SingleLogin1.PNG)

## P21 消息队列
![单点登陆](https://github.com/SuperZhouxj/JavaInterview/blob/master/images/Extra_P21_MegQueue.PNG)


