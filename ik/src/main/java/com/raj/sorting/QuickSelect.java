package com.raj.sorting;

import com.raj.Util;

import java.util.Arrays;
import java.util.Random;

/**
 * Hoare's Algorithm tweaks the QuickSort algo to achieve a O(n) - avg case algo
 * for finding the kth smallest element
 */
public class QuickSelect {

    public static void main(String[] args) {
        System.out.println(findKthSmallest(new int[]{3,2,1,4,8,7,5,6}, 4));
    }

    static int findKthSmallest(int [] A, int k) {
        return quickSelect(A, k-1, 0, A.length-1);
    }

    static Random random = new Random();

    /**
     * Find the kth smallest number, given an unsorted array
     * Hoare's QuickSelect Algo
     */
    static int quickSelect(int[] A, int k, int start, int end) {
        // base case - 0 or 1 elem array, nothing to do
        if (start >= end) {
            return A[k];
        }

        // pick pivot
        int pivotIdx = start + random.nextInt(end - start + 1);
        System.out.println("PivotIdx=" + pivotIdx + ",Pivot=" + A[pivotIdx] + " for sub array " + Arrays.toString(Arrays.copyOfRange(A, start, end+1)));
        Util.swap(A, start, pivotIdx);

        // partition
        int smaller = start, pivot = A[start];
        for (int bigger = smaller+1; bigger <= end; bigger++) {
            if (A[bigger] < pivot) Util.swap(A, ++smaller, bigger);
        }
        Util.swap(A, smaller, start);
        System.out.println("After partitioning array " + Arrays.toString(A));

        // if pivot is kth, we found the answer
        if (smaller == k) {
            return A[smaller];
        }
        // else recurse on the partition that is of interest
        else if (k < smaller) return quickSelect(A, k, start, smaller-1);
        else return quickSelect(A, k, smaller+1, end);
    }

    /**
     * Find the median of an unsorted array
     */
}
