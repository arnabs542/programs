package com.raj.dp;

/**
 * @author rshekh1
 */
public class LargestSumSubArray {

    public static void main(String[] args) {
        System.out.println(getLargestSum(new int[] {1,-2,3,4,-5,8}));
    }

    /**
     * Kadane's Algo DP:
     *
     *                  0   1   2   3   4   5  => A's elements
     *  currentMax      1   -1  3   7   2   10
     *  globalMax       1   1   3   7   7   10
     */
    private static int getLargestSum(int[] A) {
        // init pointers
        int currentMax = A[0];
        int globalMax = A[0];

        // iterate and update local/global max using previous computed values
        for (int n : A) {
            /**
             * No point considering this n, if n itself is greater than current sum, otherwise take it as it increases our sum
             */
            currentMax = Math.max(n, n + currentMax);
            globalMax = Math.max(globalMax, currentMax);
        }
        return globalMax;
    }

}
