package com.raj;

/**
 * @author rshekh1
 */
public class Util {

    public static void swap(int[] A, int i, int j) {
        if (i == j) return;
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

}
