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
DLC直接不一定线程安全，原因是有指令重排的存在，加入volatile可以禁止指令重排

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
