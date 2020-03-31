package com.raj.concurrency;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentMap {

    static class ConcurrentMapDS {
        private final Map<String, Data> m = new TreeMap<>();
        private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock r = rwl.readLock();
        private final Lock w = rwl.writeLock();

        public Data get(String key) {
            r.lock();
            try { return m.get(key); }
            finally { r.unlock(); }
        }

        public Set<String> allKeys() {
            r.lock();
            try { return m.keySet(); }
            finally { r.unlock(); }
        }

        public Data put(String key, Data value) {
            w.lock();
            try { return m.put(key, value); }
            finally { w.unlock(); }
        }

        public void clear() {
            w.lock();
            try { m.clear(); }
            finally { w.unlock(); }
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

}
