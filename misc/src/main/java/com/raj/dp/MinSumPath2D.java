package com.raj.dp;

/**
 * @author rshekh1
 */
public class MinSumPath2D {

    public static void main(String[] args) {
        int[][] A = {
                {1,3,2},
                {4,3,1},
                {5,6,1}
        };
        System.out.println(minSumDP(A, 3, 3));
        //System.out.println(minSum(A, 2, 2));
    }

    /**
     * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right
     * which minimizes the sum of all numbers along its path.
     * Note: You can only move either down or right at any point in time.
     * Input :
     *      [ 1 3 2
     *        4 3 1
     *        5 6 1 ]
     * Output : 8
     *      1 -> 3 -> 2 -> 1 -> 1
     */
    // O(mn)
    static int minSumDP(int[][] A, int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                else if (i == 0) A[i][j] += A[i][j-1];
                else if (j == 0) A[i][j] += A[i-1][j];
                else A[i][j] += Math.min(A[i-1][j], A[i][j-1]);
            }
        }
        return A[m-1][n-1];
    }

    // Exponential O(2^mn) ?
    static int minSum(int A[][], int m, int n) {
        if (n < 0 || m < 0)
            return Integer.MAX_VALUE;
        else if (m == 0 && n == 0) return A[m][n];
        else return A[m][n] + Math.min(minSum(A, m-1, n), minSum(A, m, n-1));
    }

}
