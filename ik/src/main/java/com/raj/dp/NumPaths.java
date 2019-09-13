package com.raj.dp;

/**
 * @author rshekh1
 */
public class NumPaths {

    /**
     * Number Of Paths In A Matrix
     * Consider a maze mapped to a matrix with an upper left corner at coordinates (row, column) = (0, 0).
     * You can only move either towards right or down from a cell. You must determine the number of distinct paths
     * through the maze. You will always start at a position (0, 0), the top left, and end up at (n-1, m-1),
     * the bottom right.
     * As an example, consider the following diagram where '1' indicates an open cell and '0' indicates blocked.
     */
    public static void main(String[] args) {
        System.out.println(dp(new int[][]{
                {1},{0},{1},{1}
        })); // 0 - no path
        System.out.println(dp(new int[][]{
                {1,1,1,1},
                {1,1,1,1},
                {1,1,1,1}
        })); // 10
        System.out.println(dp(new int[][]{
                {1,1,0},
                {1,1,1},
                {0,1,1}
        })); // 4
    }

    // Time / Space = O(r*c)
    static int dp(int[][] A) {
        int r = A.length;
        int c = A[0].length;
        long[][] dp = new long[r+1][c+1];

        // pre-fill dp table with matrix
        for (int i=1; i<=r; i++)
            for (int j=1; j<=c; j++)
                dp[i][j] = A[i-1][j-1];

        // base case 1: no solution exists as start itself is blocked
        if (dp[1][1] == 0) return 0;

        // now count paths
        for (int i=1; i<=r; i++) {
            for (int j=1; j<=c; j++) {
                // base case 2: skip this else it will add upto 0
                if (i == 1 && j == 1) continue;
                if (dp[i][j] == 0) continue;  // blocked

                dp[i][j] = dp[i][j-1] + dp[i-1][j];
                dp[i][j] %= 1000000007; // to avoid overflows with large values
            }
        }
        return (int) dp[r][c];
    }

}
