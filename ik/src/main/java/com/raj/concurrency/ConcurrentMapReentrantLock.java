package com.raj.concurrency;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentMapReentrantLock {

    /**
     * # Reentrant ReadWrite Lock: https://www.codejava.net/java-core/concurrency/java-readwritelock-and-reentrantreadwritelock-example
     * - Multiple threads can read the data at the same time, as long as there’s no thread is updating the data.
     * - Only one thread can update the data at a time, causing other threads (both readers and writers) block until the write lock is released.
     * - If a thread attempts to update the data while other threads are reading, the write thread also blocks until the read lock is released.
     */
    static class ConcurrentMapDS {
        private final Map<String, Data> map = new TreeMap<>();
        private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock readLock = rwl.readLock();
        private final Lock writeLock = rwl.writeLock();

        public Data get(String key) {
            /**
             * === How to use Condition Variables ===
             * What if you want to wait on some condition as in while(some cond) obj.wait()
             * & then do some update & finally obj.notify?
             *
             * // Class variables
             * ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
             * Lock writeLock = rwl.writeLock();
             * Condition condition = lock.newCondition();
             *
             * void method() {
             *   lock.lock();                               ... similar to start of synchronized block
             *   while(some condition) condition.await();   ... similar to lock.wait()
             *   try {
             *       // update state
             *   } finally {
             *       condition.signal();                    ... similar to lock.notify()
             *       lock.unlock();                         ... similar to end of synchronized block
             *   }
             * }
             *
             */
            readLock.lock();
            try {
                return map.get(key);
            } finally {
                readLock.unlock();
            }
        }

        public Set<String> allKeys() {
            readLock.lock();
            try {
                return map.keySet();
            } finally {
                readLock.unlock();
            }
        }

        public Data put(String key, Data value) {
            writeLock.lock();
            try {
                return map.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }

        public void clear() {
            writeLock.lock();
            try {
                map.clear();
            } finally {
                writeLock.unlock();
            }
        }
    }

    static class Data {
        int id;
        String val;
        public Data(int id, String val) {
            this.id = id;
            this.val = val;
        }
    }

    public static void main(String[] args) {
        // multiple threads reading but we'll see last write always
        // multiple threads writing but only can write at a time and no reads will be allowed at that time
    }

}
