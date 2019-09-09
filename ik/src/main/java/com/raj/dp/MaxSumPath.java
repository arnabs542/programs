package com.raj.dp;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class MaxSumPath {

    /**
     * Given a 2D grid(n x m), where u can move either right or down, find the max sum path
     *
     * Recurrence function:
     *
     *           0,  if r>n-1 OR c>m-1
     * f(r,c) =  grid[r][c],  if r=n-1 AND c=m-1 (final solution)
     *           grid[r][c] + max(f(r+1,c), f(r,c+1))
     *
     *              f(0,0)
     *      f(1,0)           f(0,1)     ... x2 branching factor
     *   f(2,0)   f(1,1)   f(1,1) f(0,2)
     * f(3,0) f(2,1) f(1,2) f(2,1) f(1,2) f(1,2) .... Overlapping subproblems
     *
     * => O(2^(n+m)) time
     */
    public static void main(String[] args) {
        int[][] grid = new int[][] {
            {5,6,4,2},
            {1,2,1,8},
            {1,8,3,2},
            {2,5,6,3}
        };
        System.out.println(maxSumPath_recursive(grid, 0, 0));
        System.out.println(maxSumPath_dp(grid));

        // dp recurrence fails for negative values (recursive solve still works)
        grid = new int[][] {
                {-2,4},
                {+0,6}
        };
        System.out.println(maxSumPath_recursive(grid, 0, 0));
        System.out.println(maxSumPath_dp(grid));
    }

    // Does repetitive solutions to sub-problems -
    // It can be memoized though but still DP is preferable since DP will likely be faster by a constant factor (no recursion overhead and no hash_map overhead)
    // Time = O(2^(m+n)), where is the tree branching factor & m+n is the max depth of recursion stack
    // Space = O(m+n)
    static int maxSumPath_recursive(int[][] grid, int r, int c) {
        if (r >= grid.length || c >= grid[0].length) return 0;
        return grid[r][c] + Math.max(maxSumPath_recursive(grid, r+1,c), maxSumPath_recursive(grid, r, c+1));
    }

    // DP solution with bottom up table computation => O(n+m) time & space
    static int maxSumPath_dp(int[][] grid) {
        // pad 0th row & 0th col so that we don't have to handle for edge cases like x-1, y-1 causing ArrayIndexOutofBounds
        int [][] dpTable = new int[grid.length+1][grid[0].length+1];
        for (int i = 1; i <= grid.length; i++) {
            for (int j = 1; j <= grid[0].length; j++) {
                // dpTable i,j is computing for grid i-1,j-1 actually due to padding
                dpTable[i][j] = grid[i-1][j-1] + Math.max(dpTable[i-1][j], dpTable[i][j-1]);
            }
            System.out.println("DP table state: " + Arrays.deepToString(dpTable));
        }

        // print path
        System.out.print("\nPath => ");
        int i = dpTable.length-1, j = dpTable[0].length-1;
        while (i>0 && j>0) {
            System.out.print(grid[i-1][j-1] + " ");
            if ((dpTable[i][j] - grid[i-1][j-1]) == dpTable[i][j-1]) {
                j = j-1;
            } else {
                i = i-1;
            }
        }
        System.out.println();

        return dpTable[grid.length][grid[0].length];    // dp table has all the values, just return
    }

}
