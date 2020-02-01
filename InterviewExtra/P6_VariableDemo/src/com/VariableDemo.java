package com;

public class VariableDemo {
    static int s;//成员变量，类变量
    int i;//成员变量，实例对象
    int j;//成员变量，实例对象
    {
        int i = 1;//非静态代码块中的局部变量
        i++;
        j++;
        s++;
    }
    public void test(int j){//形参，局部变量，j
        i++;
        j++;
        s++;
    }
    public static void main(String[] args){//形参，局部变量，args
        VariableDemo variableDemo1= new VariableDemo();//局部变量，variableDemo1
        VariableDemo variableDemo2 = new VariableDemo();//局部变量，variableDemo2
        variableDemo1.test(10);
        variableDemo1.test(20);
        variableDemo2.test(30);
        System.out.println(variableDemo1.i + "," + variableDemo1.j + "," + variableDemo1.s);
        System.out.println(variableDemo2.i + "," + variableDemo2.j + "," + variableDemo2.s);
    }
}
