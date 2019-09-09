package com.raj.dp;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class CountWays {

    /**
     * How many ways to score N in American football
     * Scores possible = 2,3,6
     * Total score = 8
     *
     *
     * Total score => 0 1 2 3 4 5 6 7 8
     * # of ways   => 1 0 1 1 1 2 3 3 f(8) = f(2)+f(6) + f(3)+f(5) + f(6)+f(2) 
     *                       /  |  \            = 4 + 3 + 4 = 10
     *                  2,2   2,3
     *                        3,2
     *                        f(2)+f(3)
     *
     * Given, N = total score & k[] = scores possible
     * dp[N] = sum of dp[N-k[i]], if N>k & i = 0...k.length
     *         dp[0] = 1 .... 1 way to score 0
     *         dp[1] = 0 .... not possible to score
     *         dp[8] = u cud reach 8 by scoring 2,3 or 6 from prev sub-problems (refer to com.raj.dp.CountWays to understand why)
     *               = num ways to score (8 - 2,3,6) respectively
     *               = dp[8-2]+dp[8-3]+dp[8-6] = dp[6]+dp[5]+dp[2]
     */
    public static void main(String[] args) {
        System.out.println(waysToScore(8, new int[]{2,3,6}));
    }
    
    static int waysToScore(int N, int[] K) {
        int[] dp = new int[N+1];
        dp[0] = 1; dp[1] = 0;   // base cases

        for (int i = 2; i <= N; i++) {
            // we are using prev solves to reach 8 by getting 1 more goal (ie. score of 2,3 or 6), the summation of which gives the num ways for ith
            for (int k : K) {
                if (i >= k) dp[i] += dp[i-k];
            }
        }
        System.out.println("DP Table => " + Arrays.toString(dp));
        return dp[N];
    }

}
