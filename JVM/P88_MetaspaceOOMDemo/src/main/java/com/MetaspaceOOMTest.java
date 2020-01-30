package com;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=80m
 * 异常：java.lang.OutOfMemoryError: Metaspace
 */
public class MetaspaceOOMTest {
    static class OOMTest{}
    public static void main(final String[] args){
         int i =1;
         try{
             while (true){
                 i++;
                 Enhancer enhancer = new Enhancer();
                 enhancer.setSuperclass(OOMTest.class);
                 enhancer.setUseCache(false);
                 enhancer.setCallback(new MethodInterceptor() {
                     public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                         return methodProxy.invokeSuper(o,args);
                     }
                 });
                 enhancer.create();
             }
         }catch (Throwable e){
             System.out.println("多少次后发生了异常： "+i);
             e.printStackTrace();

         }

    }

}
