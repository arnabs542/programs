package com.raj.concurrency;

public class BASICS {
/**
 * https://www.baeldung.com/java-concurrency-interview-questions
 * https://www.youtube.com/watch?v=lotAYC3hLVo&list=PLBB24CFB073F1048E&index=3
 *
 * # Process vs Thread:
 * Memory - Threads share memory(jvm) while Processes don't.
 * Isolation - Process is an independent/isolated piece of software that uses it's own memory - OS construct.
 * Communication - Processes use inter-process communication defined by OS to co-operate b/w processes.
 *
 * "Thread is a part of an application that shares a common memory with other threads of the same application. It allows
 * to shave off lots of overhead, design the threads to cooperate and exchange data between them much faster."
 *
 * # Monitor: A monitor is mechanism to control concurrent access to an object. Also, called mutex or intrinsic object lock.
 * wait and notify methods use object's monitor to communication among different threads.
 *
 * # 2 ways to create Thread - extend Thread class or implements Runnable & specify run() method
 *
 * # Thread Life cycle:
 * NEW — a new Thread instance that was not yet started via Thread.start()
 * RUNNABLE — a running thread. It is called runnable because at any given time it could be either running or waiting for the next quantum of time from the thread scheduler. A NEW thread enters the RUNNABLE state when you call Thread.start() on it
 * BLOCKED — a running thread becomes blocked if it needs to enter a synchronized section but cannot do that due to another thread holding the monitor of this section
 * WAITING — a thread enters this state if it waits for another thread to perform a particular action. For instance, a thread enters this state upon calling the Object.wait() method on a monitor it holds, or the Thread.join() method on another thread
 * TIMED_WAITING — same as the above, but a thread enters this state after calling timed versions of Thread.sleep(), Object.wait(), Thread.join() and some other methods
 * TERMINATED — a thread has completed the execution of its Runnable.run() method and terminated
 *
 * # Thread Interfaces:
 * Runnable has void run() - no return value, no checked ex, used in java.util.concurrent.Executor
 * Callable has T call() - return value, can throw ex, used in ExecutorService instances to start an async task and then call the returned Future instance to get its value.
 *
 * # Daemon threads: When all non-daemon threads are terminated, the JVM simply abandons all remaining daemon threads. Use them for supportive/service tasks.
 *
 * # ExecutorService interface has three standard implementations:
 * ThreadPoolExecutor — for executing tasks using a pool of threads. Once a thread is finished executing the task, it goes back into the pool. If all threads in the pool are busy, then the task has to wait for its turn.
 * ScheduledThreadPoolExecutor allows to schedule task execution instead of running it immediately when a thread is available. It can also schedule tasks with fixed rate or fixed delay.
 * ForkJoinPool is a special ExecutorService for dealing with recursive algorithms tasks. If you use a regular ThreadPoolExecutor for a recursive algorithm, you will quickly find all your threads are busy waiting for the lower levels of recursion to finish. The ForkJoinPool implements the so-called work-stealing algorithm that allows it to use available threads more efficiently.
 *
 * # Volatile keyword: Access to volatile int in Java will be thread-safe. Volatile makes sure that write to variable is
 * flushed to main memory and read to it also happens from main memory and hence making sure that thread see at right
 * copy of variable. Access to the volatile is automatically synchronized. If you have a field that is accessed from multiple threads, with at least one thread writing to it, then you should consider making it volatile, or else there is a little guarantee to what a certain thread would read from this field.
 * Writing to non-volatile 32-bit is atomic & volatile long 64-bit is atomic. Increment happens in multi-step, so use AtomicInteger in multithreaded env.
 *
 * # Synchronized:
 * 1. The synchronized keyword before a block means that any thread entering this block has to acquire the monitor (the object in brackets). If the monitor is already acquired by another thread, the former thread will enter the BLOCKED state and wait until the monitor is released.
 * synchronized(object) {
 *     // ...
 * }
 *
 * 2. A synchronized instance method has the same semantics, but the instance itself acts as a monitor.
 * synchronized void instanceMethod() {
 *     // ...
 * }
 *
 * 3. For a static synchronized method, the monitor is the Class object representing the declaring class.
 * static synchronized void staticMethod() {
 *     // ...
 * }
 *
 * Note: If the method is an instance method, then the instance acts as a monitor for the method. Two threads calling
 * the method on different instances acquire different monitors, so none of them gets blocked. If the method is static,
 * then the monitor is the Class object. For both threads, the monitor is the same, so one of them will probably block
 * and wait for another to exit the synchronized method.
 *
 * # Object.wait() aka "Wait for a certain condition to fulfill":
 * A thread that owns the object's monitor(for instance, a thread that has entered a synchronized section guarded by
 * the object) may call object.wait() to temporarily release the monitor and give other threads a chance to acquire the
 * monitor. This may be done, for instance, to wait for a certain condition.
 *
 * # Object.notify()/all() aka "Condition fulfilled, notify waiting threads":
 * When another thread that acquired the monitor fulfills the condition, it may call object.notify()/notifyAll() and
 * release the monitor. The notify method awakes a single thread in the waiting state, and the notifyAll method awakes
 * all threads that wait for this monitor, and they all compete for re-acquiring the lock.
 *
 * @see BlockingQueue for example of wait & notify
 *
 * # Thread unwanted states -
 * Deadlock is a condition within a group of threads that cannot make progress because every thread in the group has to
 * acquire some resource that is already acquired by another thread in the group. The most simple case is when two
 * threads need to lock both of two resources to progress, the first resource is already locked by one thread, and the
 * second by another. These threads will never acquire a lock to both resources and thus will never progress.
 * Example:
 * Thread T1 acquires resource A & needs B to progress.
 * Thread T2 acquires resource B & needs A to progress.
 *
 * Livelock is a case of multiple threads reacting to conditions, or events, generated by themselves. An event occurs in
 * one thread and has to be processed by another thread. During this processing, a new event occurs which has to be
 * processed in the first thread, and so on. Such threads are alive and not blocked, but still, do not make any progress
 * because they overwhelm each other with useless work.
 *
 * Starvation is a case of a thread unable to acquire resource because other thread (or threads) occupy it for too long
 * or have higher priority. A thread cannot make progress and thus is unable to fulfill useful work.
 *
 * # Use-Cases of the Fork/Join Framework.
 * The fork/join framework allows parallelizing recursive algorithms. The main problem with parallelizing recursion
 * using something like ThreadPoolExecutor is that you may quickly run out of threads because each recursive step would
 * require its own thread, while the threads up the stack would be idle and waiting.
 * The fork/join framework entry point is the ForkJoinPool class which is an implementation of ExecutorService. It
 * implements the work-stealing algorithm, where idle threads try to “steal” work from busy threads. This allows to
 * spread the calculations between different threads and make progress while using fewer threads than it would require
 * with a usual thread pool.
 */
}
