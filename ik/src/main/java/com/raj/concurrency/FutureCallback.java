package com.raj.concurrency;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCallback {

    /**
     * Use Callable to return a future with returned results that can be gotten later.
     * Future<T> future = executor.submit(() -> {return sumthing;})
     * future.get();
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Integer> future = executor.submit(() -> {
            Random random = new Random();
            int duration = random.nextInt(4000);
            if (duration > 3000) throw new IOException("Sleeping for too long.");
            System.out.println("Starting ...");
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished.");
            return duration;
        });

        executor.shutdown();

        try {
            System.out.println("Result is: " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
