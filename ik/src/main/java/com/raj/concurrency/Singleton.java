package com.raj.concurrency;

public class Singleton {
    /**
     * Implement Singleton class for Multithreaded env
     */
    private static volatile Singleton instance;
    private static Singleton res;
    private static final Object mutex = new Object();

    private Singleton() {
    }

    /**
     * https://www.journaldev.com/171/thread-safety-in-java-singleton-classes-with-example-code
     * Even in a Single core, single cpu will do context switches b/w threads to give a chance for them to proceed.
     * Hence multiple instance can be created by 2 competing threads.
     * # Why do we need res ref?
     * In cases where the instance is already initialized (most of the time), the volatile field is only accessed once (due to “return res;” instead of “return instance;”).
     * This can improve the method’s overall performance by as much as 25 percent.
     */
    public static Singleton getInstance() {
        Singleton res = instance;       //In cases where the instance is already initialized (most of the time), the volatile field is only accessed once (due to “return result;” instead of “return instance;”). This can improve the method’s overall performance by as much as 25 percent.
        if (res == null) {  // 2 threads can halt here
            synchronized (mutex) {  // synch on method level will lock other threads even for trivial reads !!
                res = instance;
                if (res == null) {  // check again for null as the other halted thread may come with null again
                    instance = new Singleton();
                }
            }
        }
        return res;
    }

}
