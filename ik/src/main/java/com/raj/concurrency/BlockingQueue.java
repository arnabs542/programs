package com.raj.concurrency;

import java.util.LinkedList;
import java.util.List;

/**
 * https://www.baeldung.com/java-concurrency-interview-questions
 * The following BlockingQueue implementation shows how multiple threads work together via the wait-notify pattern.
 */
public class BlockingQueue<T> {

    private List<T> queue = new LinkedList<T>();

    private int limit = 10;

    public synchronized void put(T item) {
        while (queue.size() == limit) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        if (queue.isEmpty()) {
            notifyAll();
        }
        queue.add(item);
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        if (queue.size() == limit) {
            notifyAll();
        }
        return queue.remove(0);
    }

}
