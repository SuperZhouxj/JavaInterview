package test;

import com.Singleton5;

import java.util.concurrent.*;

public class Singleton5Test {
    public static void main(String args[]) throws ExecutionException, InterruptedException {

        Callable<Singleton5> callable = new Callable<Singleton5>() {
            @Override
            public Singleton5 call() throws Exception {
                return Singleton5.getINSTANCE();
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Singleton5> f1 = service.submit(callable);
        Future<Singleton5> f2 = service.submit(callable);

        Singleton5 s1 = f1.get();
        Singleton5 s2 = f2.get();
        System.out.println(s1 == s2);

        service.shutdown();
    }
}
