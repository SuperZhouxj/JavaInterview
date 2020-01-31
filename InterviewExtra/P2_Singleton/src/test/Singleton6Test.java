package test;

import com.Singleton6;

import java.util.concurrent.*;

public class Singleton6Test {
    public static void main(String args[]) throws ExecutionException, InterruptedException {

        Callable<Singleton6> callable = new Callable<Singleton6>() {
            @Override
            public Singleton6 call() throws Exception {
                return Singleton6.getINSTANCE();
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Singleton6> f1 = service.submit(callable);
        Future<Singleton6> f2 = service.submit(callable);

        Singleton6 s1 = f1.get();
        Singleton6 s2 = f2.get();
        System.out.println(s1 == s2);

        service.shutdown();
    }
}
