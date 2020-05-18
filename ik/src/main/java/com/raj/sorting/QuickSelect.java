package com.raj.sorting;

import com.raj.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.raj.Util.swap;
import static com.raj.sorting.KNearestNeighbors.Point;

/**
 * Hoare's Algorithm tweaks the QuickSort algo to achieve a O(n) - avg case algo
 * for finding the kth smallest element
 */
public class QuickSelect {

    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println(findKthSmallest(new int[]{3,2,1,4,8,7,5,6}, 4-1)); // 4th smallest, adjust for array index starting at 0
        System.out.println(findKthLargest(new int[]{4,6,2,8,6,5}, 4-1));
        System.out.println("Median = " + findMedian(new int[]{3,2,1,4,8,7,5,6}));
        System.out.println("K nearest points to origin = " + findKNearestPoints(null, null, 3));
    }

    /**
     * Return k nearest points to origin
     * QuickSelect k smallest, return all elements before kth pivot
     * @see com.raj.sorting.KNearestNeighbors
     */
    static List<Util.Point> findKNearestPoints(Util.Point[] points, Point origin, int k) {
        // refer to com.raj.sorting.KNearestNeighbors
        return null;
    }

    /**
     * Find the median of an unsorted array
     * => O(n) avg case with QuickSelect
     */
    static float findMedian(int[] A) {
        if (A.length % 2 == 0) {    // even length, median is n/2 -1 + n/2 elements avg
            return (findKthSmallest(A, A.length/2 - 1) + findKthSmallest(A, A.length/2)) / 2f;
        } else {
            return findKthSmallest(A, A.length/2 + 1);
        }
    }

    // k is the exact array index where the pivot will lie, so 4th smallest means 3rd index
    static int findKthSmallest(int [] A, int k) {
        return quickSelect_ksmallest(A, k, 0, A.length-1);
    }

    /**
     * Find the kth smallest number, given an unsorted array
     * Hoare's QuickSelect Algo - k times quick sort with reducing array partition every iteration
     * => O(n) avg case
     *
     * Note:
     * - pivot is sawpped to front, so start with +1
     * - swap pivot with boundary elem after partitioning
     */
    static int quickSelect_ksmallest(int[] A, int k, int start, int end) {
        // base case - 0 or 1 elem array, nothing to do
        if (start >= end) {
            return A[k];
        }

        // pick pivot
        int pivotIdx = start + random.nextInt(end - start + 1);
        System.out.println("PivotIdx=" + pivotIdx + ",Pivot=" + A[pivotIdx] + " for sub array " + Arrays.toString(Arrays.copyOfRange(A, start, end+1)));
        swap(A, start, pivotIdx);

        // partition
        int smaller = start, pivot = A[start];
        for (int bigger = smaller+1; bigger <= end; bigger++) {
            if (A[bigger] < pivot) swap(A, ++smaller, bigger);  // don't use A[pivotIdx] as it's swapped elem
        }
        swap(A, smaller, start);  // smaller is at boundary of lesser elems than pivot, hence swap pivot to here (not smaller+1 as it will bring a bigger elem to lesser partition)
        // Array looks like ...lesser elems... Pivot elem ...greater elems...
        System.out.println("After partitioning array " + Arrays.toString(A));

        // if pivot is kth, we found the answer as it is at it's exact sorted place in array & elems to left are smaller (but not sorted)
        if (smaller == k) {
            return A[smaller];
        }
        // else recurse on the partition that is of interest (dropping the other half that's of no use)
        else if (k < smaller) return quickSelect_ksmallest(A, k, start, smaller-1);
        else return quickSelect_ksmallest(A, k, smaller+1, end);
    }

    static int findKthLargest(int [] A, int k) {
        return quickSelect_klargest(A, k, 0, A.length-1);
    }

    // instead of smallest we find kth largest element
    static int quickSelect_klargest(int[] A, int k, int start, int end) {
        if (start >= end) return A[k];  // base case
        int pivot = A[start];   // first elem as pivot, greater | pivot | lesser
        int bigger = start, smaller = start+1; // smaller travels right
        for (; smaller <= end; smaller++) {
            if (A[smaller] >= pivot) swap(A, smaller, ++bigger);
        }
        swap(A, start, bigger);
        if (bigger == k) return A[k];
        else if (k < bigger) return quickSelect_klargest(A, k, start, bigger-1);
        else return quickSelect_klargest(A, k, bigger+1, end);
    }

}
