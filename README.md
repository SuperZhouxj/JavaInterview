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

**ABA问题：**CAS算法实现一个重要前提需要取出内存中某时刻数据，下时刻比较并替换，那么这个时间差就会数据有可能变化

eg.两个线程one,two从内存位置V中取出A，线程two操作将值变成B，然后two又将位置V数据变成A，这是线程one进行CAS操作发现内存仍然为A，然后操作并成功，
尽管one的CAS操作成功，并不代表这个过程没有问题。

## P17 AtomicReference原子引用
多线程并发中有ABA问题

## P19 AtomicStampedReference版本号原子引用
解决CAS中ABA问题

# Collection
## P20 集合类不安全之并发修改异常










## P10 CountDownLatch
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
