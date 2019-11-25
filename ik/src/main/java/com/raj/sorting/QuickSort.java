package com.raj.sorting;

import com.raj.Util;

import java.util.Arrays;
import java.util.Random;

/**
 * @author rshekh1
 */
public class QuickSort {

    static Random random = new Random();

    public static void main(String[] args) {
        int[] A = {4,2,8,7,1,3,5,6};
        Arrays.stream(A).forEach(x -> System.out.print(x + " "));
        QSort(A, 0, A.length - 1);
        System.out.print("\nSorted Array => " + Arrays.toString(A));
    }

    // Refer to images for more visual explanation
    // O(n^2) worst case, O(nlogn) avg case
    private static void QSort(int[] A, int startIdx, int endIdx) {

        // base case with 0 or 1 elem array - nothing to do, just return
        if (startIdx >= endIdx) return;

        // pick pivot & put it at start
        int pivotIdx = startIdx + random.nextInt(endIdx - startIdx + 1);
        Util.swap(A, startIdx, pivotIdx);   // move pivot at start
        System.out.println("Pivot = " + A[pivotIdx]);

        // Lomuto's partitioning - init smaller, bigger at pivot, keep moving bigger
        // and swap anything that it sees as smaller than pivot, thus creating a contiguous block of smaller elems | pivot | bigger elems
        int smaller = startIdx, bigger = startIdx + 1, pivot = A[startIdx]; // our pivot is now at start
        for (; bigger <= endIdx; bigger++) {
            if (A[bigger] < pivot) {    // don't use A[pivotIdx] as it's swapped elem
                Util.swap(A, ++smaller, bigger);
            }
        }
        // swap pivot to correct place, which is at the boundary of smaller
        Util.swap(A, startIdx, smaller);
        Arrays.stream(A).forEach(x -> System.out.print(x + " "));

        // recurse on rest, excluding pivot which is already at it's correct place
        QSort(A, startIdx, smaller - 1);
        QSort(A, smaller + 1, endIdx);
    }

}
