package com.raj.arrays;

public class FindUnsortedSubArray {
    /**
     * Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending
     * order, then the whole array will be sorted in ascending order, too.
     * Input: [2, 6, 4, 8, 10, 9, 15]
     * Output: 5
     * Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted.
     */
    public static void main(String[] args) {
        System.out.println(findUnsortedSubarray(new int[]{2,3,3,2,4,2,4,6}));
        System.out.println(findUnsortedSubarray(new int[]{2,6,4,8,10,9,15}));
    }

    /**
     * If the array didn't have contiguous dupe, one could easily write sumthing like:
     *         int L = -1, R = -1;
     *         for (int i=1; i<A.length; i++) {
     *             if (L == -1 && A[i] < A[i-1]) L = i-1;
     *             if (A[i] < A[i-1]) R = i;
     *         }
     *         if (L == -1) return 0;
     *         return R-L+1;
     *
     * But it will have issues with dupes eg. [2, 3,3,2,4,2 ,4,6]  => 5
     * [L to R & R to L iterate once]
     * # We are looking for a sorted pattern which means the min is first elem, & max is last elem.
     * # If we go L->R each elem shud be greater or equal than last seen max. Mark the elem that violates this criteria.
     * # Similarly, go R->L, updating the min & mark elem which is greater than min.
     */
    static int findUnsortedSubarray(int[] A) {
        int start = -1, end = -1;

        // L -> R, find end, each elem shud be greater than current max so far
        int max = A[0];
        for (int i = 1; i < A.length; i++) {
            if (A[i] < max) end = i;
            max = Math.max(max, A[i]);
        }

        // R -> L, find start, each elem shud be lesser than current min so far
        int min = A[A.length-1];
        for (int i = A.length-2; i >= 0; i--) {
            if (A[i] > min) start = i;
            min = Math.min(min, A[i]);
        }
        if (start == -1) return 0;
        return end - start + 1;
    }
}
