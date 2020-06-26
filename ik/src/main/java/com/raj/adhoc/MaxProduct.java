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
        System.out.println(maxProduct(new int[]{-2,3,4,1,0,2}));
        System.out.println(maxProduct(new int[]{2,-5,-2,-4,3}));
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

    /**
     * Max Product SubArray
     * Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.
     * A = -2  3  4  1   0 2
     * l = -2 -6 -24 -24 0 2  (go left -> right & compute product)
     * r = -24 12 4  1   0 2  (go right -> left & compute product)
     * Now observe that the max prod subarr is nothing but the max of of both l & r
     * default to 1 if product becomes 0.
     * we keep -ve products as we may see another -ve making the product +ve
     */
    static int maxProduct(int[] A) {
       int n = A.length, res = A[0], l = 0, r = 0;
        System.out.println("l r max");
        for (int i = 0; i < n; i++) {
            l =  (l == 0 ? 1 : l) * A[i];  // default to 1 if product becomes 0
            r =  (r == 0 ? 1 : r) * A[n - 1 - i];
            res = Math.max(res, Math.max(l, r));
            System.out.println(l + " " + r + " " + res);
        }
        return res;
    }

    /**
     * Loop through the array, each time remember the max and min value for the previous product, the most important thing
     * is to update the max and min value: we have to compare among max * A[i], min * A[i] as well as A[i], since this is
     * product, a negative * negative could be positive.
     */
    static int _maxProduct(int[] A) {
        int max = A[0], min = A[0], result = A[0];
        for (int i = 1; i < A.length; i++) {
            int temp = max;
            max = Math.max(Math.max(max * A[i], min * A[i]), A[i]);  // choose max b/w A[i] prod w/ min, max & itself as -ve * -ve is +ve
            min = Math.min(Math.min(temp * A[i], min * A[i]), A[i]); // as -ve * -ve is +ve, we need to carry -ve prod
            if (max > result) {
                result = max;
            }
        }
        return result;
    }

}
