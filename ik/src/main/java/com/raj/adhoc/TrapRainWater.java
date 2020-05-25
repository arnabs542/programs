package com.raj.adhoc;

import java.util.Arrays;

public class TrapRainWater {
    /**
     * Trapping Rain Water
     * https://www.geeksforgeeks.org/trapping-rain-water/
     * Given n non-negative integers representing an elevation map where the width of each bar is 1,
     * compute how much water it is able to trap after raining.
     *
     * Input: arr[]   = {3, 0, 2, 0, 4}
     * Output: 7
     *
     * Input: arr[] = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
     * Output: 6
     */
    public static void main(String[] args) {
        System.out.println(brute(new int[]{3,0,2,0,4}));
        System.out.println(brute(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));

        System.out.println(precomputeBounds(new int[]{3,0,2,0,4}));
        System.out.println(precomputeBounds(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    }

    /**
     *         |
     * |       |
     * |   |   |
     * | . | . |     => for bar 2, its contrib = min_bounds(3,4) - 2 = 1
     * 3 0 2 0 4
     *
     * # For each bar, find the left & right max heights (enclosures to be able to trap water)
     * # Water trapped for this bar is min(left,right) - A[i]
     * # Add them up
     *
     * Time = O(n^2), no aux space
     */
    static int brute(int[] A) {
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            // find max left bound
            int left = A[i];
            for (int j = 0; j < i; j++) {
                left = Math.max(left, A[j]);
            }
            // find max right bound
            int right = A[i];
            for (int j = i+1; j < A.length; j++) {
                right = Math.max(right, A[j]);
            }
            res += Math.min(left, right) - A[i];  // trapped water for this bar alone
        }
        return res;
    }

    /**
     * [Precompute bottleneck getting max bounds]
     * For faster runtime, we'll need to remove the bottleneck of finding left & right bounds in constant time.
     * # Pre-compute for each arr index, the max from left & max from right into an left & right arrays
     * # Iter once & get max left & right bounds and compute trapped water for this bar and add them up
     *
     * Time/Space = O(n)
     */
    static int precomputeBounds(int[] A) {
        int res = 0;
        int[] left = new int[A.length];
        int[] right = new int[A.length];

        // precompute left & right bounds
        int max = 0;
        for (int i = 0; i < A.length; i++) {
            max = Math.max(max, A[i]);
            left[i] = max;
        }
        System.out.println(Arrays.toString(left));
        max = 0;
        for (int i = A.length-1; i >= 0; i--) {
            max = Math.max(max, A[i]);
            right[i] = max;
        }
        System.out.println(Arrays.toString(right));

        // now compute trapped water
        for (int i = 0; i < A.length; i++) {
            res += Math.min(left[i], right[i]) - A[i];
        }
        return res;
    }

}
