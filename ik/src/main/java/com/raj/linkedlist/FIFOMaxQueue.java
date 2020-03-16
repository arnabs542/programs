package com.raj.linkedlist;

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
        /*LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addLast(1);  // same as add
        linkedList.addLast(2);
        linkedList.addLast(3);
        linkedList.addLast(4);
        System.out.println(linkedList.getFirst());
        System.out.println(linkedList.getLast());
        Iterator<Integer> it = linkedList.descendingIterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }*/

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
    static LinkedList<Integer> queue = new LinkedList<>();
    static LinkedList<Integer> maxTracker = new LinkedList<>();

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

}
