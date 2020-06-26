package com.raj.adhoc;

import java.util.Arrays;

public class RangeSumQuery {
    /**
     * Range Sum Query
     * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.
     * Example:
     * Given nums = [-2, 0, 3, -5, 2, -1]
     *
     * sumRange(0, 2) -> 1
     * sumRange(2, 5) -> -1
     * sumRange(0, 5) -> -3
     * Note:
     * You may assume that the array does not change.
     * There are many calls to sumRange function.
     */
    public static void main(String[] args) {
        init(new int[]{0,1,2,3,4,5,6,7,8});
        System.out.println(sumRange(0,2));
        System.out.println(sumRange(2,5));
        System.out.println(sumRange(5,7));
    }

    static int[] sums;

    static void init(int[] nums) {
        sums = new int[nums.length];
        sums[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sums[i] = sums[i-1] + nums[i];
        }
        System.out.println(Arrays.toString(sums));
    }

    static int sumRange(int i, int j) {
        if (i-1 < 0) return sums[j];
        return sums[j] - sums[i-1];
    }

}
