package com.raj.dp;

/**
 * @author rshekh1
 */
public class CountWays {

    public static void main(String[] args) {
        System.out.println(climbStairs(2));
        System.out.println(climbStairs(4));
    }

    /**
     * You are climbing a stair case. It takes n steps to reach to the top.
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     *
     * Input: n = 1
     * Output: 1
     * There is only one way to climb 1 stair
     *
     * Input: n = 2
     * Output: 2
     * There are two ways: (1, 1) and (2)
     *
     * Input : n = 3
     * Output : 3
     * (1 1 1), (1 2), (2 1)
     *
     * Input: n = 4
     * Output: 5
     * (1, 1, 1, 1), (1, 1, 2), (2, 1, 1), (1, 2, 1), (2, 2)
     *
     * DP formula:
     * You can reach ith step by taking 1 step from i-1 & 2 step from i-2 respectively
     * You get num ways at ith step dp[i] by combining num ways from 1 & 2 step behind dp[i-1] + dp[i-2]
     * dp[i] = dp[i-1] + dp[i-2]
     */
    public static int climbStairs(int A) {      // O(n) --> only iterate once to fill the table
        int[] dp = new int[A+1];

        dp[0] = 0; dp[1] = 1; dp[2] = 2;    // base cases

        for (int i = 3; i <= A; i++) dp[i] = dp[i-1] + dp[i-2]; // reach ith step by 1 or 2 more steps from i-1 or i-2

        return dp[A];
    }

}
