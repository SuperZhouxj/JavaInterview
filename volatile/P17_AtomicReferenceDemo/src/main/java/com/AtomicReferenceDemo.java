package com;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@ToString
@AllArgsConstructor
class User{
    String name;
    Integer age;
}

public class AtomicReferenceDemo {


    public static void main(String args[]){
        AtomicReference<User> atomicReference = new AtomicReference<User>();
        User zh3 =new User("zhangsan",10);
        User li4 = new User("Lisi",11);
        atomicReference.set(zh3);
        System.out.println(atomicReference.compareAndSet(zh3,li4) +"\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(zh3,li4) +"\t" + atomicReference.get().toString());
    }

}
