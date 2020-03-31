package com.raj.concurrency;

public class NOTES {

    /**
     * https://www.geeksforgeeks.org/classical-problems-of-synchronization-with-semaphore-solution/
     *
     * https://www.udemy.com/course/multithreading-and-parallel-computing-in-java/?LSNPUBID=JVFxdTr9V80&ranEAID=JVFxdTr9V80&ranMID=39197&ranSiteID=JVFxdTr9V80-Bx9_FVORH8RWiDjbzSPqEQ
     * https://www.udemy.com/course/java-multithreading-concurrency-performance-optimization/
     *
     * # In a multithreaded environment, multiple threads might try to modify the same resource. If threads aren't managed
     *   properly, this will, of course, lead to consistency issues.
     *
     * # Guarded Blocks: https://www.baeldung.com/java-wait-notify
     *   wait(): this forces the current thread to wait until some other thread invokes notify() or notifyAll() on the same object.
     *   wait(long timeout): specify a timeout after which thread will be woken up automatically. A thread can be woken up before reaching the timeout using notify() or notifyAll().
     *   notify(): method is used for waking up any arbitrary thread that are waiting for an access to this object's monitor
     *   notifyAll(): wakes all threads that are waiting on this object's monitor
     *
     * # Thread.interrupt(): An interrupt is an indication to a thread that it should stop what it is doing and do something
     *   else. It's up to the programmer to decide exactly how a thread responds to an interrupt, but it is very common for
     *   the thread to terminate.
     *
     * # Most basic way to handle thread synchronization in a multithreaded environment:
     * - start threads:
     *   Thread t1...n = new Thread(() -> method())
     *   t1...n.start()
     *   t1...n.join()
     *
     * - Create a simple shared object lock
     *   Object lock = new Object();
     *
     * - method()
     *   synchronized(lock) {
     *       while(condition not met) {
     *           lock.wait()
     *       }
     *       // critical section - condition is met
     *       // update shared resource
     *       lock.notifyAll()
     *   }
     *
     */
    public class Data {
        private String packet;

        // True if receiver should wait
        // False if sender should wait
        private boolean transfer = true;

        public synchronized void send(String packet) {
            while (!transfer) {
                try {
                    wait();
                } catch (InterruptedException e)  {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            transfer = false;
            this.packet = packet;
            notifyAll();
        }

        public synchronized String receive() {
            while (transfer) {
                try {
                    wait();
                } catch (InterruptedException e)  {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            transfer = true;
            notifyAll();
            return packet;
        }
    }

}
