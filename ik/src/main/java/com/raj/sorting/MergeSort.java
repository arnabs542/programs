package com.raj.sorting;

public class MergeSort {

    /**
     *
     */
    public static void main(String[] args) {
        int[] inputA = {2, 3, 1, 5, 4, 6};
        System.out.println("Input A: ");
        for (int i = 0; i < inputA.length; i++) {
            System.out.print(" " + inputA[i]);
        }
        mergeSort(inputA, 0, inputA.length - 1);
        System.out.println("\nSorted A: ");
        for (int i = 0; i < inputA.length; i++) {
            System.out.print(" " + inputA[i]);
        }
    }

    /**
     * Merge Sort routine
     * Runtime = O(nlogn)
     * Aux Space = O(n) for merge routine
     */
    public static void mergeSort(int[] A, int start, int end) {

        if (A == null || A.length == 0 || start >= end) {
            return;
        }

        // Divide A
        int mid = (start + end) / 2;

        // Create sub-problems & recurse to solve
        mergeSort(A, start, mid);
        mergeSort(A, mid + 1, end);

        // Conquer by merging sub-solutions
        merge(A, start, mid, end);

    }

    /**
     * Merges left & right partition marked by indices in linear time using extra space O(n)
     */
    public static void merge(int[] A, int start, int mid, int end) {

        int iStart = start;
        int jStart = mid + 1;

        // create a temp A for copying elems from left & right sub-arrays of A
        int k = 0;       // start of merged A
        int[] tmpArr = new int[end - start + 1];

        // Copy smallest from either partition
        while (iStart <= mid && jStart <= end) {
            if (A[iStart] < A[jStart]) {
                tmpArr[k++] = A[iStart++];
            } else {
                tmpArr[k++] = A[jStart++];
            }
        }

        // Copy over leftovers from either partitions
        while (iStart <= mid) {
            tmpArr[k++] = A[iStart++];
        }

        while (jStart <= end) {
            tmpArr[k++] = A[jStart++];
        }

        // Now update the original A with merged & sorted A
        k = 0;
        while (start <= end) {
            A[start++] = tmpArr[k++];
        }

    }

}