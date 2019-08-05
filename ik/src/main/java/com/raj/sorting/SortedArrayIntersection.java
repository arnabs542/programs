package com.raj.sorting;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class SortedArrayIntersection {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(
                findSortedArrayIntersection_NoDupes(
                        new int[] {2,3,5,5,7,8,9}, new int[] {1,2,5,5,5,6,8})));  // 2,5,8
    }

    /**
     * Given 2 sorted arrays with dupes, find the intersecting elems, with dupes removed
     *
     * Opt A - Use Set to add elems of first array, then iterate second arr, check if elem is present in set then print
     * => O(n) runtime/space
     *
     * Opt B - Since arrays are sorted, we can use a form of MERGE sub-routine where we merge by having a ptr for each array
     * - we compare and print if elems are equal else move the ptr
     * - to remove dupes, we compare if previous element doesn't match the current elems under comparison
     * => O(n) runtime, no extra space
     */
    private static int[] findSortedArrayIntersection_NoDupes(int[] A, int[] B) {
        int[] res = new int[A.length];
        int i = 0, j = 0, k = 0;
        while (i < A.length && j < B.length) {
            if (A[i] == B[j] && (k == 0 || res[k-1] != A[i])) { // remove duplicate by checking prev elem (note - removing this check prints duped elems)
                res[k] = A[i];
                i++; j++; k++;
            } else if (A[i] > B[j]) j++;    // otherwise increment pointer which has lesser elem
            else i++;
        }
        return Arrays.copyOfRange(res, 0, k);
    }
}
