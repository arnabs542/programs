package com.raj.adhoc;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class MergeOverlappingIntervals {

    /**
     * For input n = 4, inputArray = [[1, 3], [5, 7], [2, 4], [6, 8]], output will be:
     *
     * 1 4
     * 5 8
     */
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(getMergedIntervals(new int[][]{
                {1, 3},
                {5, 7},
                {2, 4},
                {6, 8}
        })));
    }

    //Time = O(n), Aux Space = O(1)
    static int[][] getMergedIntervals(int[][] A) {
        if (A == null || A.length <= 1) return A;

        // sort by start idx
        Arrays.sort(A, (a, b) -> Integer.compare(a[0],b[0]));

        int i = 0; // use the array itself for result, i represents bounds of the res array
        for (int j=1; j<A.length; j++) {
            if (A[i][1] < A[j][0]) A[++i] = A[j];
            else A[i][1] = Math.max(A[i][1],A[j][1]);
        }
        return Arrays.copyOfRange(A, 0, i+1);
    }
}
