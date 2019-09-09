package com.raj.dp;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author rshekh1
 */
public class MaxProduction {

    /**
     * Day-wise productions using machines A & B are below. Only one machine is Active at a time.
     * Goal is to maximize production at the end of day A.length
     * Changing machines incurs a 1 day downtime.
     *
     * Day => 1  2  3  4
     * A     10  3  4  4
     * B      5  3  9  3
     *
     * o/p => (10,A) + (0 downtime) + (9,B) + (3,B) = 22
     *
     * DP Formula:
     *
     * Let's compute dp table for each machine's total production so far(using switching from other machine as well)
     * Mi = Machine's ith day accumulated production
     * M[i] = max { alternate M[i-1], M[i-1] + A[i] }
     *
     * Day => 1   2   3   4
     * M_A    10  13  17  21
     * M_B    5   10  19  22
     *            |
     *          A->B switch
     */
    public static void main(String[] args) {
        System.out.println(maximizeProduction(new int[]{10,3,4,4}, new int[]{5,3,9,3}));
    }

    static int maximizeProduction(int[] A, int[] B) {
        // 2 rows of A.length cols
        int[][] M = new int[2][A.length+1];
        M[0][0] = M[1][0] = 0;  // base case

        int[] M_A = M[0], M_B = M[1];
        int i = 0;
        for (i = 1; i <= A.length; i++) {
            M_A[i] = Math.max(M_B[i-1], M_A[i-1] + A[i-1]); // A[i-1] denotes machine A's ith day production
            M_B[i] = Math.max(M_A[i-1], M_B[i-1] + B[i-1]);
        }
        System.out.println("DP Table => " + Arrays.deepToString(M));

        boolean isA = M_A[M_A.length-1] > M_B[M_B.length-1] ? true : false;
        List<Pair<String,Integer>> res = new ArrayList<>();
        while (--i > 0) {
            if (isA) {
                res.add(new Pair("A", M_A[i]));
                isA = (M_A[i-1] + A[i-1]) == M_A[i];
            } else {
                res.add(new Pair("B", M_B[i]));
                isA = (M_B[i-1] + B[i-1]) != M_B[i];
            }
        }
        Collections.reverse(res);
        System.out.println("Path => " + res);
        return Math.max(M_A[M_A.length-1], M_B[M_B.length-1]);
    }

}
