package com.raj.adhoc;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class AltPositiveNegative {

    /**
     * Given an array named array of size n, that contains both positive and negative numbers. Rearrange the array
     * elements so that positive and negative numbers appear alternatively in the output. The order in which the
     * positive/negative elements appear should be maintained.
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(alternating_positives_and_negatives(new int[] {2,3,-4,-9,-1,-7,1,-5,-6})));
    }

    // Similar to merge function, maintain 2 ptrs for +ve & -ve, move them alternatively & assign it to res arr
    static int[] alternating_positives_and_negatives(int[] A) {
        int p = 0, n = 0;
        int len = A.length;
        int[] res = new int[len]; int i = 0;

        while(p < len || n < len) {
            while(p < len) {
                if (A[p] < 0) p++;
                else {
                    res[i++] = A[p++];
                    break;
                }
            }

            while (n < len) {
                if (A[n] >= 0) n++;
                else {
                    res[i++] = A[n++];
                    break;
                }
            }
        }
        return res;
    }

}
