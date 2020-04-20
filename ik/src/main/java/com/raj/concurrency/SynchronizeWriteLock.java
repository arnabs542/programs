package com.raj.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizeWriteLock {

    public static void main(String[] args) throws InterruptedException {
        SynchronizeWriteLock synchronizeWriteLock = new SynchronizeWriteLock();
        long start = System.currentTimeMillis();

        Thread t1 = new Thread(() -> {
            try {
                synchronizeWriteLock.process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                synchronizeWriteLock.process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Time taken = " + (System.currentTimeMillis() - start));  // ideally ~2 secs
        System.out.println("list sizes = " + synchronizeWriteLock.list1.size()    // shud be 2000 plus 2000
                + " " + synchronizeWriteLock.list2.size());
    }

    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    Random random = new Random();

    Object lock1 = new Object();
    Object lock2 = new Object();

    /**
     * Using synchronized on the method acquires lock on method instance monitor which is just 1, hence it takes more than 4 secs!!
     * Use 2 locks to allow 2 concurrent access on stage1 & stage 2, now the time is ~2secs.
     */
    void stage1() throws InterruptedException {
        synchronized (lock1) {
            Thread.sleep(1);
            list1.add(random.nextInt());
        }
    }

    void stage2() throws InterruptedException {
        synchronized (lock2) {
            Thread.sleep(1);
            list2.add(random.nextInt());
        }
    }

    void process() throws InterruptedException {   // write 1000 numbers to 2 lists one after other by 2 threads
        for (int i = 0; i < 1000; i++) {
            stage1();
            stage2();
        }
    }
}
