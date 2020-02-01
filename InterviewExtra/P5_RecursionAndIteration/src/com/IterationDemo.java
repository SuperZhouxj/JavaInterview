package com;

/**
 * 迭代
 * 优点：省时，运行效率高， 缺点：代码没有递归简洁易懂
 */
public class IterationDemo {
    public static void main(String args[]){
        long start = System.currentTimeMillis();
        System.out.println(Iteration(40));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    private static int Iteration(int i){
        if (i<=0){
            throw new IllegalArgumentException("n之不能小于1");
        }
        if (i ==1 || i ==2) {
            return i;
        }

        int one =2;//初始化为走到第二个台阶的走法
        int two = 1;//初始化为走到第一个台阶的走法
        int sum = 0;
        for (int j = 3;j<=i;j++){
            //最后跨两步和跨一步的走法
            sum = one + two;
            two = one;
            one = sum;
        }
        return sum;
    }
}
