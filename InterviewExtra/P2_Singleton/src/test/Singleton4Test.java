package test;

import com.Singleton4;

import java.util.concurrent.*;

public class Singleton4Test {
    public static void main(String args[]) throws ExecutionException, InterruptedException {
       /* Singleton4 s1 = Singleton4.getINSTANCE();
        Singleton4 s2 = Singleton4.getINSTANCE();
        System.out.println(s1==s2);
        */
        Callable<Singleton4> callable = new Callable<Singleton4>() {
            @Override
            public Singleton4 call() throws Exception {
                return Singleton4.getINSTANCE();
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Singleton4> f1 = service.submit(callable);
        Future<Singleton4> f2 = service.submit(callable);

        Singleton4 s1 = f1.get();
        Singleton4 s2 = f2.get();
        System.out.println(s1 == s2);

        service.shutdown();
    }
}
