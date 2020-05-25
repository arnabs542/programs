package com.raj.adhoc;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MaxProduct {
    /**
     * You're given a list of n integers arr[0..(n-1)]. You must compute a list output[0..(n-1)] such that, for each
     * index i (between 0 and n-1, inclusive), output[i] is equal to the product of the three largest elements out of
     * arr[0..i] (or equal to -1 if i < 2, as arr[0..i] then includes fewer than three elements).
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findMaxProduct(new int[]{3,2,4,2,5,6}))); // -1,-1,24,24,60,120
    }

    static int[] findMaxProduct(int[] arr) {
        // use a min heap to track top 3
        int[] max = new int[arr.length];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a,b) -> a-b);
        int prod = 1;
        for (int i=0;i<arr.length;i++) {
            if (i<3) {
                minHeap.add(arr[i]);
                prod *= arr[i];
                if (i<2) max[i] = -1;
                else max[i] = prod;
            } else {
                // remove if new elem greater & adjust product
                if (minHeap.peek() < arr[i]) {
                    prod /= minHeap.remove();
                    minHeap.add(arr[i]);
                    prod *= arr[i];
                }
                max[i] = prod;
            }
        }
        return max;
    }

}
