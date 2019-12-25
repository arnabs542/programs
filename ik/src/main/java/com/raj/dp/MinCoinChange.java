package com.raj.dp;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class MinCoinChange {

    /**
     * Given coins k, find the Minimum number coins required to give change for amount A
     * A = 8
     * k = {2,3,5}
     * => 2 i.e. 3+5
     */
    public static void main(String[] args) {
        System.out.println("min coins rec = " + minCoinChange_rec(8, new int[]{2,3,5}));
        System.out.println("min coins DP = " + minCoinChange_dp(8, new int[]{2,3,5}));
    }

    /**
     * Min num coins to reach A is min to reach (A - value of this coin) + 1 for this coin
     *                  f(A)
     *               2/  3|  5\     ... using k coins (branching factor)
     *               /    |    \
     * min (1  +   f6    f5    f3  )
     *            /|\   /|\   /|\
     *
     * Depth of recursion = A/kmin coin which is ~A
     * Time complexity: O(k^A)
     * Space = O(A) recursion depth
     *
     * With memoization ? In this case it may be better than DP as it may end up solving lesser subproblems & memoizing
     * them than DP which solves everything from 0 to A
     */
    static int minCoinChange_rec(int A, int[] k) {
        if (A == 0) return 0; // reached goal, hence a possible solution
        if (A < 0) return Integer.MAX_VALUE; // not possible to provide change, hence force max value for this branch to prune

        int min = Integer.MAX_VALUE;    // or set it to some high value like 9999 to avoid tackling edge case
        for (int coin : k) {
            min = Math.min(min, minCoinChange_rec(A-coin, k));
        }
        // now add 1 for this coin
        if (min != Integer.MAX_VALUE) {     // edge case: to avoid integer overflow when 1 is added to MAX, it becomes MIN & throws off comparison
            min = min + 1;                  // hence add 1 later after asserting, it's not already infinity
        }
        return min;
    }

    // Time = O(Ak), Space = O(A)
    // Note - Memoized recursive soln might be better in terms of space & time as DP solves some subproblems that are unnecessary like everything b/w 0 to A
    static int minCoinChange_dp(int A, int[] K) {
        int[] dp = new int[A+1]; // +1 for 0th base case
        Arrays.fill(dp, Integer.MAX_VALUE);  // init array with max
        dp[0] = 0;  // base case i.e. reaching amount 0 with 0 coins is 0
        for (int a = 1; a <= A; a++) {
            for (int k : K) {
                if (a >= k) {
                    dp[a] = Math.min(dp[a], dp[a - k]);
                }
            }
            if (dp[a] != Integer.MAX_VALUE) {
                dp[a] = 1 + dp[a];
            }
        }
        System.out.println("DP table = " + Arrays.toString(dp));
        System.out.print("Changes = ");
        int a = A;
        while (a > 0) {
            for (int k : K) {
                if (dp[a] == (dp[a-k] + 1)) {     // num ways to get a-k + 1
                    System.out.print(k + " ");
                    a -= k;
                    break;
                }
            }
        }
        System.out.println();
        return dp[A];
    }

}
