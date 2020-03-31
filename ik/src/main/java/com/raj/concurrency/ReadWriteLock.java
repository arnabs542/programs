package com.raj.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {
    /**
     * # Reentrant ReadWrite Lock: https://www.codejava.net/java-core/concurrency/java-readwritelock-and-reentrantreadwritelock-example
     * - Multiple threads can read the data at the same time, as long as there’s no thread is updating the data.
     * - Only one thread can update the data at a time, causing other threads (both readers and writers) block until the write lock is released.
     * - If a thread attempts to update the data while other threads are reading, the write thread also blocks until the read lock is released.
     *
     * Reader's Writers Problem:
     * https://www.geeksforgeeks.org/readers-writers-problem-set-1-introduction-and-readers-preference-solution/
     */


    /**
     * Synchronize Threads to access methods sequentially.
     * One class has three methods: method1, method2, method3.
     * One instance of this class will be passed to 3 different threads.
     * The first thread continues to call method1. 2nd will call method2, 3rd will call method3.
     * method1, 2, 3 should be called sequentially
     * eg. 1,2,3,1,2,3,1,2,3,1,2,3....
     */
    public static void main(String args[] ) throws Exception {
        Shared1 shared = new Shared1();
        final AtomicInteger cnt = new AtomicInteger(25);

        // 2 threads accessing the same method
        Thread T1_a = new Thread(() -> {
            while (cnt.decrementAndGet() > 0) shared.method1();
        }, "T1_a");
        Thread T1_b = new Thread(() -> {
            while (cnt.decrementAndGet() > 0) shared.method1();
        }, "T1_b");

        Thread T2_a = new Thread(() -> {
            while (cnt.decrementAndGet() > 0) shared.method2();
        }, "T2_a");
        Thread T2_b = new Thread(() -> {
            while (cnt.decrementAndGet() > 0) shared.method2();
        }, "T2_b");

        Thread T3_a = new Thread(() -> {
            while (cnt.decrementAndGet() > 0) shared.method3();
        }, "T3_a");
        Thread T3_b = new Thread(() -> {
            while (cnt.decrementAndGet() > 0) shared.method3();
        }, "T3_b");

        // start thread in random order
        T2_a.start(); T2_b.start();
        T1_a.start(); T1_b.start();
        T3_a.start(); T3_b.start();

        T3_a.join(); T3_b.join();
        T1_a.join(); T1_b.join();
        T2_a.join(); T2_b.join();

        System.out.println("=== DONE ===");
        System.exit(0);
    }

    /**
     * Approach 1: Uses 1 write lock, whoever has it can print. Use STATE variable to say which method can go ahead and lock.
     * Note: This approach fails for multiple threads accessing the same method
     */
    static class Shared {

        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock lock = readWriteLock.writeLock();
        int STATE = 1;

        public Shared() {}

        public void method1() {
            while (true) {
                if (STATE == 1) {
                    lock.lock();
                    System.out.print("1 ");
                    STATE = 2;
                    lock.unlock();
                }
            }
        }

        public void method2() {
            while (true) {
                if (STATE == 2) {
                    lock.lock();
                    System.out.print("2 ");
                    STATE = 3;
                    lock.unlock();
                }
            }
        }

        public void method3() {
            while (true) {
                if (STATE == 3) {
                    lock.lock();
                    System.out.print("3 ");
                    STATE = 1;
                    lock.unlock();
                }
            }
        }
    }

    /**
     * Approach 2: Works even with multiple thread accessing the same method.
     * Obtain lock before entering the critical section of method, which guarantees only 1 thread is accessing
     * If condition not met, the thread waits until woken up. It again checks if cond is met to avoid spurious wakes.
     * Then only it does the job & notifies others.
     */
    static class Shared1 {
        final Object lock = new Object();
        int STATE = 1;

        public void method1() {
            synchronized (lock) {       // u can only use wait(), notify() once we lock an object thru synchronized
                while (STATE != 1) {    // if spurious wake, re-verify condition (notifyAll wakes all waiting threads on lock)
                    try {
                        lock.wait();   // wait until woken up
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(STATE + " ");   // now we are in STATE=1 for sure
                STATE = 2;      // change state
                lock.notifyAll();
            }
        }

        public void method2() {
            synchronized (lock) {
                while (STATE != 2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(STATE + " ");
                STATE = 3;
                lock.notifyAll();
            }
        }

        public void method3() {
            synchronized (lock) {
                while (STATE != 3) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(STATE + " ");
                STATE = 1;
                lock.notifyAll();
            }
        }

    }

}
