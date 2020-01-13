package com.raj.dp;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class RodCutMaxProfit {

    public static void main(String[] args) {
        int prices[] = new int[] {1, 5, 8, 9, 10, 17, 17, 20};
        int L = prices.length;
        System.out.println("Rec Max Profit = " + rec(prices,L));
        System.out.println("DP Max Profit = "+ dp(prices,L));
    }

    /**
     * https://www.geeksforgeeks.org/cutting-a-rod-dp-13/
     * Given a rod of length L and an array of prices that contains prices of all pieces of size smaller than L.
     * Determine the maximum value obtainable by cutting up the rod and selling the pieces.
     *
     * For example, if length of the rod is 8 and the values of different pieces are given as following:
     * length   | 1   2   3   4   5   6   7   8
     * --------------------------------------------
     * price    | 1   5   8   9  10  17  17  20
     *
     * O/P = 22 (by cutting in two pieces of lengths 2 and 6)
     *
     * First we think recursively - what are the sub-problems & are there overlaps?
     *
     * Rec Tree:
     *                   8         -- original length L
     *              1/  2|   8\    -- try all cut length from 1 to 8 recursively
     *              /    |     \
     *             7     6  ... 0
     *         1/ 2| 7\
     *         /   |   \           -- Overlapping subproblems f(6),f(5)... & Optimal substructure exists as we can apply sub-problems solutions
     *       6     5 .. 0
     * Rec formula =>
     * for cutL in 1 to L:
     *      maxProfit = max(maxProfit, price[cutL] + rec(price, L-cutL))
     *
     * Time = O(bf ^ depth of tree), bf=L & rec depth=L => O(L^L)
     * More accurate is L! as each level we decrease the length by 1 => L * L-1 * L-2 ... 2 * 1 = L!
     * Space = O(L) which is the height of tree
     */
    // int[] cache = {-1,-1,-1...}              // if using top-down DP memoization
    static int rec(int[] prices, int L) {
        if (L == 0) return 0;
        //if (cache[L] != -1) return cache[L];  // if using top-down DP memoization
        int maxProfit = 0;
        for (int cutLen = 1; cutLen <= L; cutLen++) { // try all cuts recursively
            maxProfit = Math.max(maxProfit, prices[cutLen-1] + rec(prices, L-cutLen));
        }
        //cache[L] = maxProfit;                 // if using top-down   DP memoization
        return maxProfit;
    }

    /**
     * Do we need 2d or 1d array? 1d will suffice as length is our only changing dimension.
     * Also, looking at rec solution at each length L(1->N), we need to compute maxProfit for that L
     * using prices of different cut lengths + leftover length from dp table.
     *
     * L = 1 2 3 4 5 6 7 8
     * P = 1 5 . . x = using cutLen from 1 to 5, what's the maxProfit?
     *
     * for L <- 1 to L:   -- determine dp[L]
     *   for cutLen <- 1 to cutLen:  -- try all cuts possible
     *      dp[L] = max(dp[L], price[cutLen] + dp[L-cutLen])
     * Time = O(L^2), Space = O(L)
     */
    static int dp(int[] prices, int length) {
        int[] dp = new int[length+1];
        dp[0] = 0;
        for (int L = 1; L <= length; L++) {
            for (int cutLen = 1; cutLen <= L; cutLen++) {
                dp[L] = Math.max(dp[L], prices[cutLen-1] + dp[L-cutLen]);
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[length];
    }

}
