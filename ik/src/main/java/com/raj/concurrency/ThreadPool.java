package com.raj.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool tp = new ThreadPool();
        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 12; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                tp.process(taskId);    // process a task in parallel
            });
        }
        tp.countDownLatch.await();    // using countDownLatch avoids waiting for executorservice termination

        //executorService.shutdown();
        //executorService.awaitTermination(1000, TimeUnit.SECONDS);
        System.out.println("Time taken = " + (System.currentTimeMillis() - start));  // ideally ~6 secs
    }

    CountDownLatch countDownLatch = new CountDownLatch(12);

    void process(int task) {
        System.out.println("Starting task " + task);
        try {
            Thread.sleep(2000);         // task takes 2 secs
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished task " + task);
        countDownLatch.countDown();
    }
}
