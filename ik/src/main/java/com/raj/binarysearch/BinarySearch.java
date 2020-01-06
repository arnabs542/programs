package com.raj.binarysearch;

public class BinarySearch {
    /**
     * Given a sorted array arr[] of n elements, write a function to search a given element T in arr[].
     */
    public static void main(String[] args) {
        int [] A = new int[]{1,3,5,6,6,7,8,9};
        System.out.println(bSearch(A, 0, A.length-1, 7)); //ans = 5
        System.out.println(bSearch(A, 0, A.length-1, 4)); //ans = -1
    }

    static int bSearch(int[] A, int s, int e, int T) {
        if (s>e) return -1;
        int m = s + (e-s)/2;  // avoid s+e overflows
        if (A[m] == T) return m;
        if (T < A[m]) return bSearch(A, s, m-1, T);
        else return bSearch(A, m+1, e, T);
    }
}
