package com.raj.arrays;

public class MaxSumSubArray {
    /**
     * Given an array find the max contiguous sum.
     *
     * arr = [-2,1,-3,4,-1,2,1,-5,4]
     * max sum = [4,-1,2,1] => 6
     *
     * Follow up: How wud u solve if the array is circular?
     * [5,-3,5] => max sum is 7, but max circular sum is 10
     */
    public static void main(String[] args) {
        System.out.println(maxSumArr(new int[] {-2,1,-3,4,-1,2,1,-5,4}));
        System.out.println(maxSumArr(new int[] {-2,-3,-1,-5}));  // works for -ve values
        System.out.println(findUnsortedSubarray(new int[]{2,3,3,2,4}));
    }

    /**
     * Brute: For each ith elem, find the max sum => O(n^2)
     *
     * DP:  iter once, have a running sum, try to maximize locally when we see a new elem, which means
     *      if the new elem is greater than sum(happen for -ve running sum), then pick just the elem
     *             -2  1 -3  4 -1  2  1 -5  4
     * DP sum =     0  1  0  4  3  5  6  1  5
     * maxsum =     0  1  0  4  3  5  6  6  6
     */
    static int maxSumArr(int[] A) {
        int max = Integer.MIN_VALUE;
        int curr_sum = 0;
        for (int i : A) {
            curr_sum += i;
            if (curr_sum <= i) {
                curr_sum = i;
            }
            max = Math.max(max, curr_sum);
        }
        return max;
    }

    /**
     * If the arr is circular, observe:
     * 2,-3,1,-3,1,5
     * tot sum = 3
     * max sum = 6
     * min sum = -5
     * cir max sum = 8 => tot sum - min sum
     *
     * edge case when all -ve:
     * -2,-3,-1
     * tot = -6
     * max = -1
     * min = -6
     * cir max sum = max sum = -1
     *
     * Formula = Math.max(max_sum, tot_sum - min_sum)
     */
    static int findUnsortedSubarray(int[] A) {
        if (A.length <= 1) return 0;
        int L = -1, min = 0, max = 0, R = -1;
        for (int i=1; i<A.length; i++) {
            if (A[i] == A[min]) min = i;
            if (A[i] >= A[max]) max = i;
            else {
                if (L == -1) {
                    if (A[min] == A[i]) {
                        L = min+1;
                        min = i;
                    }
                    else L = i-1;
                    System.out.println("init L to " + L);
                }
                R = i;
            }
        }
        System.out.println(L + "," + R);
        if (L == -1) return 0;
        return R-L+1;
    }

}
