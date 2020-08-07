package com.raj.sorting;

import java.util.Arrays;

public class BalancedPartition {
    /**
     * Balanced Split
     * Given a set of integers (which may include repeated integers), determine if there's a way to split the set into
     * two subsets A and B such that the sum of the integers in both sets is the same, and all of the integers in A are
     * strictly smaller than all of the integers in B.
     *
     * arr = [1, 5, 7, 1]
     * output = true
     * We can split the set into A = {1, 1, 5} and B = {7}.
     *
     * Example 2
     * arr = [12, 7, 6, 7, 6]
     * output = false
     * We can't split the set into A = {6, 6, 7} and B = {7, 12} since this doesn't satisfy the requirement that all
     * integers in A are smaller than all integers in B.
     */
    public static void main(String[] args) {
        System.out.println(balancedSplitExists(new int[] {1, 5, 7, 1}));
        System.out.println(balancedSplitExists(new int[] {12, 7, 6, 7, 6}));
    }

    /**
     * [Sort, 2 ptrs, Expand on condition]
     * # Sort array
     * # Create 2 ptrs i & j tracking left & right boundaries of equal sum partitions - leftSum & rightSum
     * # Add to left if A[i] < A[j] & leftSum is less than rightSum
     * # Add to right if A[j] < A[i]
     * # Return true if i & j meet
     * O(nlogn)
     */
    static boolean balancedSplitExists(int[] arr) {
        // sort
        Arrays.sort(arr);

        // 2 ptrs iter, checking for cond arr[i] < arr[j]
        int i = 0, j = arr.length-1;
        int leftSum = arr[i], rightSum = arr[j];
        while (i<j) {
            if (leftSum < rightSum && arr[i] < arr[j]) leftSum += arr[++i];  // expand left - first set
            else if (arr[j] > arr[i]) rightSum += arr[--j];    // expand right - second set only if its smaller than right
            else return false;  //can't expand as condition didn't meet, return false
        }
        return true;
    }

}
