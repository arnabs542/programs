package com.raj.sorting;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.raj.Util.Point;
import static com.raj.Util.swap;

/**
 * Hoare's Algorithm tweaks the QuickSort algo to achieve a O(n) - avg case algo
 * for finding the kth smallest element
 */
public class QuickSelect {

    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println(findKthSmallest(new int[]{3,2,1,4,8,7,5,6}, 4-1)); // 4th smallest, adjust for array index starting at 0
        System.out.println("Median = " + findMedian(new int[]{3,2,1,4,8,7,5,6}));
        System.out.println("K nearest points to origin = " + findKNearestPoints(new Point[] {
                new Point(7,9), new Point(5,4), new Point(1,2), new Point(3,5),
                new Point(1,7), new Point(3,9), new Point(6,2), new Point(8,6)
        }, 3));
    }

    /**
     * Return k nearest points to origin
     * QuickSelect k smallest, return all elements before kth pivot
     */
    static List<Point> findKNearestPoints(Point[] points, int k) {
        // TODO
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
        return quickSelect(A, k, 0, A.length-1);
    }

    /**
     * Find the kth smallest number, given an unsorted array
     * Hoare's QuickSelect Algo - k times quick sort with reducing array partition every iteration
     * => O(n) avg case
     */
    static int quickSelect(int[] A, int k, int start, int end) {
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
            if (A[bigger] < pivot) swap(A, ++smaller, bigger);
        }
        swap(A, smaller, start);
        System.out.println("After partitioning array " + Arrays.toString(A));

        // if pivot is kth, we found the answer
        if (smaller == k) {
            return A[smaller];
        }
        // else recurse on the partition that is of interest (dropping the other half that's of no use)
        else if (k < smaller) return quickSelect(A, k, start, smaller-1);
        else return quickSelect(A, k, smaller+1, end);
    }

}
