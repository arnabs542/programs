package com.raj.concurrency;

import java.util.LinkedList;
import java.util.List;

/**
 * https://www.baeldung.com/java-concurrency-interview-questions
 * The following BlockingQueue implementation shows how multiple threads work together via the wait-notify pattern.
 * If we push an element into an empty queue, all threads that were waiting in the pop method wake up and try to receive
 * the value. If we push an element into a full queue, the push method waits for the call to the poll method.
 * The poll method removes an element and notifies the threads waiting in the push method that the queue has an empty
 * place for a new item.
 *
 * Design a Blocking Queue for multithreaded env, where we provide access to PUSH & POP selectively on some condition.
 */
public class BlockingQueue<T> {

    private List<T> queue = new LinkedList<>();
    private Object lock = new Object();
    private int limit = 5;

    /**
     * PUSH()
     * Since, we are making the method synchronized, we need to think about on what CONDITION do we wait & notify:
     * wait - when Q is full, can't PUSH so give up monitor temporarily so that threads waiting in POP() can fill the Q.
     * notifyAll - if Q was empty, POP() can now take the value we'll push into Q.
     */
    public void push(T item) {
        synchronized (lock) {
            while (queue.size() == limit) {   // Q is full, just temporarily relieve the monitor for poll threads to poll values
                try {
                    System.out.println("Q is Full, will wait to push");
                    lock.wait();
                } catch (InterruptedException e) {
                }
            }
            // note commented code is more of an optimization

            //boolean wasQueueEmpty = queue.isEmpty();  // get current state of queue to notify later
            queue.add(item);   // in critical section, just add
            //if (wasQueueEmpty) {  // signal waiting threads in poll to continue as now we pushed an element to Q
            lock.notifyAll();
            //}
        }
    }

    /**
     * POLL()
     * wait - if Q is empty, can't poll, just wait for threads in push to fulfill not-empty condition
     * notifyAll - if the Q was full, lets signal waiting threads in Push to add values as we'll free up 1 space
     */
    public T poll() {
        T val;
        synchronized (lock) {
            while (queue.isEmpty()) {
                try {
                    System.out.println("Q is Empty, will wait to poll...");
                    lock.wait();
                } catch (InterruptedException e) {
                }
            }

            // note commented code is more of an optimization

            //boolean wasQueueFull = (queue.size() == limit);
            val = queue.remove(0);
            //if (wasQueueFull) {
            lock.notifyAll();
            //}
        }
        return val;
    }

    public static void main(String[] args) {
        /**
         * Or use high level DS:
         * ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(5);
         * blockingQueue.put();
         * blockingQueue.take();
         */
        BlockingQueue<Integer> Q = new BlockingQueue<>();
        int[] cnt = new int[1];
        Thread producer = new Thread(() -> {
            while (true) {
                ++cnt[0];
                System.out.println("Pushing " + cnt[0]);
                Q.push(cnt[0]);
                System.out.println("Pushed " + cnt[0]);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();

        Thread consumer = new Thread(() -> {
            while (true) {
                System.out.println("Polling ");
                int val = Q.poll();
                System.out.println("Polled " + val);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer.start();
    }

}
