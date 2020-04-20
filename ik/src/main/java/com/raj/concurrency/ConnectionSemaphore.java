package com.raj.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ConnectionSemaphore {
    /**
     * https://www.caveofprogramming.com/java-multithreading/java-multithreading-semaphores-part-12.html
     * Semaphores are mainly used to limit the number of simultaneous threads that can access a resources.
     */
    private static ConnectionSemaphore instance = new ConnectionSemaphore();

    // Semaphore with 10 permits with fair fifo scheduling
    private Semaphore sem = new Semaphore(10, true);
    private int connections = 0;

    private ConnectionSemaphore() {}

    public static ConnectionSemaphore getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();     // try acquiring a permit or wait
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        try {
            doConnect();       // get connection
        } finally {
            sem.release();    // release conn finally
        }
    }

    public void doConnect() {
        synchronized (this) {       // critical section
            connections++;
            System.out.println("Current connections: " + connections);
        }

        try {
            Thread.sleep((long) (Math.random() * 2000));   // do sumthing with acquired connection
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        for(int i=0; i < 200; i++) {   // if no bounded semaphore is provided, all connections will exhaust & cause DB to overwhelm
            Thread.sleep(100);
            executor.submit(() -> ConnectionSemaphore.getInstance().connect());
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);    //wait to complete
    }

}
