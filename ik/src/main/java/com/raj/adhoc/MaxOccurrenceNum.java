package com.raj.adhoc;

import java.util.Arrays;

public class MaxOccurrenceNum {

    /**
     * Find max number of occurrences of an integer in given array
     * {2, 3, 3, 5, 3, 4, 1, 7} => 3
     */
    public static void main(String[] args) {
        System.out.println("Ans = " + findMaxOccurrence(new int[]{2, 3, 3, 5, 3, 4, 1, 7}));
        System.out.println("Ans = " + findMaxOccurrence(new int[]{2,2,3,1}));
        System.out.println(findMaxOccurrence(new int[]{5,8,2,2}));  // doesn't wrk as 8 is bigger than arr len
    }

    /**
     * Algo:
     * # BruteForce : Just fix a int i from left, find tempCount & update maxCount ...O(n^2)
     * # TrackIndex : Use HashMap to store freq of each number ...O(n) + O(n) space
     * # Circular Modulo Technique :   O(n)
     *             0
     *           3   1
     *             2
     * => imagine the modulo as a circular wrap around struct as above.
     * For 4 length arr, you keep incr counts at its place. To preserve the original int, just add the
     *
     *  0  1  2  3  4  5  6  7  (idx)
     *  2, 3, 3, 5, 3, 4, 1, 7
     *  2  11 11 29 11 12 1  15  ... for every element arr[i], increment arr[arr[i]%k] by k
     *
     *  (k is a num where all array elements are smaller than it & also is the arr length) ... it only wrks when this condition is satisfied
     *
     *  Basically, we want A[element which is A[i]] as k + A[i] & to avoid overflows, we mod :
     *  A[ A[i] % k ] = A[i] + k   ... only works if array elem
     */
    static int findMaxOccurrence(int[] A) {
        int k = A.length;
        int max = 0, maxIdx = -1;
        for (int i = 0; i < A.length; i++) {
            //System.out.println(i);
            int setToIdx = A[i] % k;
            A[setToIdx] = A[i] + k;
            if (A[setToIdx] > max) {    // keep updating max value & corresponding index
                max = A[setToIdx];
                maxIdx = setToIdx;
            }
        }
        System.out.println("Modified in-place arr = " + Arrays.toString(A));
        return A[maxIdx] % k;
    }

}
