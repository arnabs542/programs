package com.raj.dp;

public class PaintHouse {
    /**
     * Given a list of adjacent houses and the cost to paint with a certain color, find the min cost of painting all
     * houses, with constraint that no 2 adjacent houses can have the same color.
     * Input: { {17,2,17},
     *          {16,16,5},
     *          {14,3,19}
     *        }
     * MinCost = 2+5+3 = 10
     */
    public static void main(String[] args) {
        System.out.println(minCost(new int[][]{ {17,2,17},
                {16,16,5},
                {14,3,19}
        }));
    }

    /**
     * Brute: for each starting option on house 0 color, find recursively the cost by applying the constraint.
     * DP (complexity is same a s Brute):
     * dp[0] = init with cost for house A[0]
     * for i = 1 to n:
     * dp[i][0] = A[i][0] + min ( dp[i-1][1], dp[i-1][2] )
     * dp[i][1] = A[i][1] + min ( dp[i-1][0], dp[i-1][2] )
     * dp[i][2] = A[i][2] + min ( dp[i-1][0], dp[i-1][1] )
     * }
     * return min (dp[n-1][0..2])
     *
     * O(n*3)
     * Space Optimization: Use the input array as DP table.
     */
    static int minCost(int[][] A) {
        int n = A.length;
        for (int i=1; i<n; i++) {
            A[i][0] += Math.min ( A[i-1][1], A[i-1][2] );
            A[i][1] += Math.min ( A[i-1][0], A[i-1][2] );
            A[i][2] += Math.min ( A[i-1][0], A[i-1][1] );
        }
        return Math.min(Math.min(A[n-1][0], A[n-1][1]), A[n-1][2]);
    }
}
