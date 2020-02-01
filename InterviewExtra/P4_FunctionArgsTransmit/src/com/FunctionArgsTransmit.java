package com;

import java.util.Arrays;

public class FunctionArgsTransmit {
    public static void main(String args[]){
        int a = 1;
        String str = "hello";
        Integer num = 1;
        int[] arr ={1,2,3,4,5};
        Mydate mydate = new Mydate();

        change(a, str, num, arr, mydate);
        System.out.println("a = "+a);
        System.out.println("str = "+str);
        System.out.println("num = "+num);
        System.out.println("arr = "+ Arrays.toString(arr));
        System.out.println("mydate = "+mydate.a);
    }

    private static void change(int j, String s, Integer n, int[] a, Mydate m) {
        j +=1;
        s += "world";
        n += 1;
        a[0] +=1;
        m.a += 1;
    }

}

class Mydate{
    int a = 1;
        }