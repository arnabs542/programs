package com.raj.arrays;

public class SortedArrayIntersect {
    /**
     * Given 2 sorted arrays, find the elements that are common.
     * [1,12,15,19,20,21]
     * [2,15,17,19,21,25,27]
     * o/p => 15,19,21
     *
     * Approach 1:
     * For each elem in first array, BSearch in 2nd array => O(mlogn)
     *
     * Approach 2:
     * Use set and dump all elems => O(n+m), linear extra space
     *
     * Approach 3:
     * Use 2 ptrs & use merge subroutine logic to find elems that are common => O(m+n)
     *
     */
    static void sortedArrayIntersect(int[] A, int[] B) {
        int i = 0, j = 0;
        while (i < A.length && j < B.length) {
            if (A[i] == B[j]) {
                System.out.println(A[i]);
                i++;
                j++;
            } else if (A[i] < B[j]) i++;
            else j++;
        }
    }

    public static void main(String[] args) {
        sortedArrayIntersect(new int[]{1,12,15,19,20,21}, new int[]{2,15,17,19,21,25,27});
    }

}
