package com.raj.concurrency;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * https://www.caveofprogramming.com/java-multithreading/java-multithreading-deadlock-video-tutorial-part-11.html
 * Concurrency issues:
 * 1. No locks/synchronization, then inconsistent account state where total balance != 20000 (which shouldn't be as we did transfers b/w them)
 * 2. Locks acquired in different orders?
 *    - Deadlock can occur (Deadlock avoid Principle1: to avoid deadlocks, try acquiring locks in same order)
 * 3. In a Prod env, it's not possible to guarantee lock acquire order, then do
 *    - while(!both locks) lock(s).tryLock()  then do transfers & release locks
 *    - Deadlock avoid Principle2: try acquiring locks, if not possible, give up all locks & retry acquiring locks after some time
 *    - This way deadlock cycle is broken
 */
public class AccountTransfer {

    private Account acc1 = new Account();
    private Account acc2 = new Account();

    private Lock lock1 = new ReentrantLock();  // for each account, create locks
    private Lock lock2 = new ReentrantLock();

    private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {

        // try acquiring locks
        while(true) {
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;
            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock) return;
                if (gotFirstLock) firstLock.unlock();
                if (gotSecondLock) secondLock.unlock();
            }

            // Locks not acquired, keep retrying
            Thread.sleep(1);
        }
    }

    /**
     * firstThread() does random transfers from acc1 -> acc2
     */
    public void firstThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            acquireLocks(lock1, lock2);
            try {
                Account.transfer(acc1, acc2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    /**
     * secondThread() does random transfers from acc2 -> acc1
     */
    public void secondThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            acquireLocks(lock2, lock1);
            try {
                Account.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: " + acc1.getBalance());
        System.out.println("Account 2 balance: " + acc2.getBalance());
        System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));  // should always be 20000
    }

    static class Account {
        private int balance = 10000;   // start balance

        public void deposit(int amount) {
            balance += amount;
        }

        public void withdraw(int amount) {
            balance -= amount;
        }

        public int getBalance() {
            return balance;
        }

        public static void transfer(Account acc1, Account acc2, int amount) {
            acc1.withdraw(amount);
            acc2.deposit(amount);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AccountTransfer accountTransfer = new AccountTransfer();
        Thread t1 = new Thread(() -> {
            try {
                accountTransfer.firstThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                accountTransfer.secondThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        accountTransfer.finished();
    }

}
