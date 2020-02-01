package com;

/**
 * 优点：代码简洁易懂，缺点：耗时，递归层数多了容易导致堆内存溢出
 */
public class Recursion {
    public static void main(String args[]){
        long start = System.currentTimeMillis();
        System.out.println(recursion(40));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    //求上i步台阶的算法，一次只能走一步或者两步
    private static int recursion(int i){
        if (i<=0){
            throw new IllegalArgumentException("n之不能小于1");
        }
        if (i ==1 || i ==2){
            return i;
        }else {
            return recursion(i-1)+recursion(i-2);
        }
    }
}
