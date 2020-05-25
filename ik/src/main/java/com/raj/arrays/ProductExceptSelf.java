package com.raj.arrays;

import java.util.Arrays;

public class ProductExceptSelf {
    /**
     * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the
     * product of all the elements of nums except nums[i].
     *
     * Example:
     * Input:  [1,2,3,4]
     * Output: [24,12,8,6]
     * Note: Solve it without division and in O(n)
     */
    public static void main(String[] args) {
        /**
         * If div were allowed, u wud just multiply all nums & then divide by ith elem to get its product.
         * Watch out for div by zeros.
         *
         * But without using div, we'll need to get prod from all left & its right elements & multiply it.
         * [Pre-compute Pattern, Left + Right iterate] Iterate once, pre-compute some & use it in second iteration.
         *
         * [...] i [...]
         *       x -> left side prod * right side prod
         *       # pre-compute left side 0...i-1
         *       # as we iter from right & compute i i+1...n, just use the prev & multiply
         */
        int[] A = new int[]{1,2,3,4};

        // go left to right, incrementally computing prod except itself and store
        int[] res = new int[A.length];
        int prod = 1;
        res[0] = prod;
        for (int i = 1; i < A.length; i++) {
            prod *= A[i-1];
            res[i] = prod; // 1,1,2,6
        }

        // go right to left, incrementally computing prod except itself & store res[i] * current product from right
        prod = 1;
        for (int i = A.length-2; i >= 0; i--) {
            prod *= A[i+1];
            res[i] *= prod;
        }

        System.out.println(Arrays.toString(res));
    }

}
