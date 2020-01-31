package test;

import com.Singleton2;

public class Singleton2Test {
    public static void main(String args[]){
        Singleton2 singleton2 = Singleton2.INSTANCE;
        System.out.println(singleton2);
    }
}
