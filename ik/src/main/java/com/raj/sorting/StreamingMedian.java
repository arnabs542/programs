package com.raj.sorting;

import java.util.Arrays;
import java.util.PriorityQueue;

public class StreamingMedian {

    /**
     * Find the Median of a Number Stream.
     *
     * Use 2 heaps:
     *
     *   max    min heap
     * 5 3 7    9 13 11
     *     |    |
     *    top  top
     *      \  /
     *     median
     *
     * add() - add number by comparing new number to tops of both heaps
     * rebalance() - rebalance once size diff of heaps grows by 2
     * median() - depending on size return median using the tops
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findMedian(new int[]{5, 15, 1, 3})));
        System.out.println(Arrays.toString(findMedian(new int[]{2, 4, 7, 1, 5, 3})));
    }

    static int[] findMedian(int[] arr) {
        // Write your code here
        // .... maxH | minH ....
        //       avg of top if even,
        //       else either max(maxH.size>minH.size) or min is median
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b-a);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a,b) -> a-b);
        int[] medians = new int[arr.length];

        medians[0] = arr[0];
        medians[1] = (arr[0] + arr[1])/2;
        if (arr[0] < arr[1]) {
            maxHeap.add(arr[0]);
            minHeap.add(arr[1]);
        } else {
            maxHeap.add(arr[1]);
            minHeap.add(arr[0]);
        }

        for (int i=2; i<arr.length; i++) {
            int n = arr[i];
            // add to either heap
            if (n <= maxHeap.peek()) maxHeap.add(n);
            else minHeap.add(n);

            // rebalance
            if (Math.abs((minHeap.size() - maxHeap.size())) > 1) {
                if (minHeap.size() > maxHeap.size()) {
                    maxHeap.add(minHeap.remove());
                } else {
                    minHeap.add(maxHeap.remove());
                }
            }
            // compute median
            if ((i+1) % 2 == 0) medians[i] = (maxHeap.peek() + minHeap.peek()) / 2;
            else medians[i] = maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
        }

        return medians;
    }

}
