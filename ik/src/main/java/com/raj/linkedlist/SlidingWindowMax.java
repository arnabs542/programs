package com.raj.linkedlist;

import java.util.*;

public class SlidingWindowMax {
    /**
     * Maximum In Sliding Window
     * Given an array of integers arr (of size n) and an integer w, find maximum number in all subarrays of length w.
     * Imagine that n is very large and a sliding window of a smaller size w is moving through arr from left to right.
     * We need to find the maximum in every position of the sliding window.
     *
     * Input:
     * arr = [1, 3, -1, -3, 5, 3, 6, 7], w = 3
     * o/p = [3, 3, 5, 5, 6, 7]
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(max_in_sliding_window(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
        System.out.println(Arrays.toString(max_in_sliding_window(new int[]{5,-5}, 2)));
    }

    /**
     * === Approach 1 ===
     * # Use w-sized PQ to track max element ... log w
     * # Delete trailing element as window advances   ...log w
     * - Augment Heap DS to store a indices map(element -> index)
     * - Now when we need to remove an element, first find its index in Map, then remove(int index)  ...doesn't exist in Java, so custom impl of heap needs to be provided
     * Runtime = O(n.log w)
     * Aux space = O(w)
     *
     * === Approach 2 ===
     * # To get to linear soln, we need to somehow get max in constant time
     * # Intuition is that we need a DS to operate on window, but the add/delete will happen at head/tail.
     * - Use a Deque for head/tail ops.
     * # How do we track max?
     * - Keep the max of window at the head. Then next max, so that on removing head after window slides, the next one become max.
     * - Add new elem to tail, but if it's higher than its immediate left, just remove it as it has no chance becoming max after we see the new greater elem.
     * - Do this until it becomes head or is less than left.
     * - Since, we are doing expand/contract method, the max we'll do is 2n ops than n*w.
     * - Note: we need to store arr idx in Deque to do window sizing when we slide right, else we won't know if the head is out of window or not
     * Runtime = O(n)
     * Aux space = O(w)
     *
     * Dry run:
     * i   deq     res
     * 0   0
     * 1   1
     * 2   1,2     1
     * 3   1,2,3   1,1
     * 4   4       1,1,4
     * 5   4,5     1,1,4,4
     * 6
     * 7
     */
    static int[] max_in_sliding_window(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w <= 1) return arr;
        List<Integer> res = new ArrayList<>();

        // Double ended queue w/ size k
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < arr.length; i++) {
            // Resize Deque
            // case 1: window sizing - remove trailing elem which are no more part of window
            // window sizing will happen once we see window elems i>=w
            if (i >= w && deque.getFirst() < i+1-w) deque.removeFirst();

            // case 2: element sizing - if new elem greater than left, remove them, as they are of no importance
            // if equal, it means its a dupe, no need to remove it
            while (!deque.isEmpty() && arr[i] > arr[deque.getLast()]) deque.removeLast();

            // now we can add to last
            deque.addLast(i);

            // add to res the max, only if we have seen one full window elems
            if (i+1 >= w) res.add(arr[deque.peekFirst()]);
        }
        return res.stream().mapToInt(x -> x).toArray();
    }

}
