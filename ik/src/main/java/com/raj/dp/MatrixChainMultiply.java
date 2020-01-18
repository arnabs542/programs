package com.raj.dp;

import java.util.Arrays;

public class MatrixChainMultiply {
    /**
     * Given a sequence of matrices, find the most efficient way to multiply these matrices together. The problem is merely to decide in which order to perform the multiplications.
     * For example, suppose A is a 10 × 30 matrix, B is a 30 × 5 matrix, and C is a 5 × 60 matrix. Then,
     * input = [10,30,5,60]
     * (AB)C = (10×30×5) + (10×5×60) = 1500 + 3000 = 4500 operations
     * A(BC) = (30×5×60) + (10×30×60) = 9000 + 18000 = 27000 operations.
     * Clearly, the first one requires less number of operations.
     *
     * Understanding # operation in matrix multiply:
     *
     * |...|   |.....|   |....x -> 3rd row of A * 3rd col of B
     * |...| x |.....| = |.....|  ie. each col in C requires 3 operations (which is A's col or B's row)
     * |...|   |.....|   |.....|
     * |...|             |.....|
     *  4x3      3x5   =   4x5  => num elements in C = 20
     *   A   x    B    =    C   => Total operations to compute C = 20*3 = 4*3*5
     */
    public static void main(String[] args) {
        int[] A = new int[]{10,30,5,60};
        int[][] memo = new int[A.length+1][A.length+1];
        Arrays.stream(memo).forEach(x -> Arrays.fill(x, Integer.MAX_VALUE));
        System.out.println(rec_memo(A, 0, A.length-1, memo));

        System.out.println(dp(A));
    }

    /**
     * [Rod cutting pattern + minimization]
     * We'll have to try all combinations and check costs.
     * Similar to rod cut, we need to make cuts at each index, & compute the cost recursively.
     *                 (10,30,5,60)
     *               /      |       \
     *             10|..  13,30|..  ..|60
     *
     *  i    k    j
     * 10 30 | 5 60
     *      cut or parenthesis bound
     * cost = A[i] * A[k] * A[j] --> cost of this cut
     *       + cost(i...k-1)
     *       + cost(k+1...j)
     * Overlapping subproblems - use memoization
     *
     * Space Complexity: O(n^2)
     * Time Complexity: O(n^3)
     */
    static int rec_memo(int[] A, int i, int j, int[][] memo) {

        if (j == i+1) return 0;  // cost of multiplying a single matrix
        if (memo[i][j] != Integer.MAX_VALUE) return memo[i][j];
        /**
         * i .... cut at k .... j
         */
        int minCost = Integer.MAX_VALUE;
        for (int k = i+1; k < j; k++) {             // n
            int cost = A[i] * A[k] * A[j]
                    + rec_memo(A, i, k, memo)       // n^2
                    + rec_memo(A, k, j, memo);
            minCost = Math.min(minCost, cost);
        }
        memo[i][j] = minCost;
        //System.out.println(Arrays.deepToString(memo));
        return memo[i][j];
    }

    // for each length of 2 to n(incl.), compute the min cost - O(n^3) as 3 loops
    static int dp(int[] A) {
        int dp[][] = new int[A.length][A.length];
        for (int L=2; L<A.length; L++) {  // try varying lengths
            for (int i=0; i<A.length-L; i++) {  // i goes until totalLen-L
                int j = i + L;  // fix j as start+L
                dp[i][j] = 1000000;
                for (int k=i+1; k<j; k++) {   // now try parenthesis at diff points
                    int cost = dp[i][k] + dp[k][j] + A[i]*A[k]*A[j];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        return dp[0][A.length-1];
    }

}
