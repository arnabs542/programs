package com.raj.linkedlist;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class FIFOMaxQueue {
    /**
     * Design a FIFO Max Queue:
     * max()
     * add(n)
     * remove()
     */
    public static void main(String[] args) {

        // Demonstrates how linkedList in java works
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addLast(1);  // same as add
        linkedList.addLast(2);
        linkedList.addLast(3);
        linkedList.addLast(4);
        System.out.println(linkedList.getFirst());  // get first in line
        System.out.println(linkedList.getLast());   // get last in line
        Iterator<Integer> it = linkedList.descendingIterator();  // traverse from last to first
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println(linkedList.removeFirst());  // removes the first in line (FIFO)

        add(1); System.out.println(max());
        add(2); System.out.println(max());
        add(5); System.out.println(max());
        remove(); System.out.println(max());
        add(3); System.out.println(max());
        add(4); System.out.println(max());
        remove(); System.out.println(max());
        remove(); System.out.println(max());
        System.out.println("Q = " + queue + ", Max = " + maxTracker);
    }

    /**
     * Approach 1:
     * # Use a maxHeap to track max
     * # Use a linked list to add, remove
     * # When removing also remove it from heap using a reference to the element in heap
     * max - O(1)
     * O(logn) for other operations for heap
     *
     * Approach 2:
     * # We'll need another DS in place of heap. What will that be?
     * # If we look at examples, it's obvious that we may not need to store prev smaller max, if the new element added is greater.
     *   Q   = +1 | 1,+2 | 1,2,+5 | -1,2,5
     *   Max = 1  | 2    | 5      |  5    ... adding a higher elem means we can remove before smaller maxes as this
     *                                        will be the max even if we remove elements from Q (it came later, so will stay until other elems are gone from Q)
     * # If the new elem is smaller than current max, just add it
     *   Q   = 2,5,+3 | 2,5,3,+4 | -2,5,3,4 | -5,3,4
     *   Max = 5,3    | 5,4      | 5,4      | 4
     * # If the elem removed is the curr max, remove it from max queue as well.
     */
    static LinkedList<Integer> queue = new LinkedList<>();  // Always doubly in Java
    static Deque<Integer> maxTracker = new ArrayDeque<>();  // LL has extra ptrs overhead & no locality of reference

    static void add(int n) {
        queue.add(n);
        Iterator<Integer> it = maxTracker.descendingIterator();
        while (it.hasNext() && it.next() < n) {
            it.remove();
        }
        maxTracker.add(n);
    }

    static void remove() {
        int n = queue.removeFirst();
        if (n == max()) maxTracker.removeFirst();
    }

    static int max() {
        return maxTracker.getFirst();
    }

    /**
     * Custom Doubly LinkedList impl:
     *
     * # add(int val):
     *   Node newNode = new Node(val)
     *      if head == null:
     *         head = newNode
     *         tail = head
     *      else tail.next = newNode
     *           newNode.prev = tail
     *           tail = tail.next
     *
     * # Node removeFirst():
     *      Node first = head
     *      if (head == tail) tail = null
     *      head = head.next
     *      head.prev = null
     *      return first
     *
     * # Node removeLast():
     *      Node last = tail
     *      if (head == tail) head = null
     *      tail = tail.prev
     *      tail.next = null
     *      return last
     *
     * # Node reverse():
     *   if head == null return null
     *   if (head == tail) return head
     *   Node curr = head, prev = null
     *   while curr != null:
     *      curr.prev = curr.next
     *      curr.next = prev
     *      prev = curr
     *      curr = curr.prev
     *
     */
}
