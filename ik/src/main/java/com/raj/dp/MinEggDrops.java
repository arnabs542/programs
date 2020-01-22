package com.raj.dp;

import java.util.Arrays;

public class MinEggDrops {
    /**
     * We have K number of eggs & a building with N floors. What is the min number of attempts that is guaranteed
     * to find the highest floor from which the egg won't break on dropping?
     * https://www.geeksforgeeks.org/egg-dropping-puzzle-dp-11/
     */
    public static void main(String[] args) {
        System.out.println(rec(20, 3));
        System.out.println(dp(10, 3));
    }

    /**
     * # DP / Recursion - we should always start with simplest base cases:
     * If we had just one egg, and N floors, we'll have to start with 1st, then 2nd....N floors yielding worst case of
     * N attempts.
     * But here we got K eggs, definitely we could do better.
     *
     * # Can we apply Greedy Strategy - Like use Binary Search?
     * Suppose, we proceed using binary search, and let k=100, we take 50th floor first and test it. What if the egg
     * breaks? we have to test for the remaining 1 to 49 floors with only 1 egg remaining, that requires 49 more drops
     * in the worst case where the required floor is 49th floor.
     * Binary search is ruled out as it cannot give us critical floor with optimal number of drops using only 2 eggs.
     * If we had infinite supply of eggs then binary search is the best method.
     *
     * # We are left with trying all floors & computing what's the minimum attempts recursively.
     *                f(n,k)
     *              /        \
     *          break       no break
     *       f(n-1,k-1)     f(n-1,k)  ... as all eggs are intact & one less floor to cover
     *      /        \
     *  f(n-2,k-2)  f(n-2,k-1)  ....
     *
     * for each floor n <- 1 to N,
     *   num attempts floor n = 1 + max[f(n-1, k-1), f(n-1, k)]  ... include/exclude egg break & take the MAX of attempts as we'll have to ensure full coverage
     * return min (all floor attempts)
     *
     * Recurrence =>  min(min, max(f(n-1, k-1), f(n-1, k))) for floor 1...N
     *
     * Time = O(bf ^ height of tree) = O(2^n)
     */
    static int rec(int N, int K) {
        // base cases
        if (N <= 1) return N;   // for 1 floor, we just need min 1 attempt irrespective of # of eggs
        if (K == 1) return N;   // for 1 egg, we need n trials

        // recursive case
        int minAttempts = Integer.MAX_VALUE;
        for (int i = 1; i <= N; i++) {        // we are dropping an egg from ith floor
            int attempts = 1 + Math.max(      // add 1 for this attempt
                    rec(i-1, K-1),      // exclude this egg - egg breaks, so try i-1 lower floors
                    rec(N-i, K));          // include this egg - egg doesn't break, so try higher n-i floors
            minAttempts = Math.min(minAttempts, attempts);
        }
        return minAttempts;
    }

    /**
     * Time = O(k.n^2), Space = O(n.k)
     */
    static int dp(int N, int K) {
        int[][] dp = new int[K+1][N+1];     // k+1 eggs rows, n+1 floors cols

        // for 1 floor, we need min 1 attempt
        for (int i = 0; i <= K; i++) dp[i][1] = 1;

        // for 1 egg, we need j trials for j floors
        for (int j = 0; j <= N; j++) dp[1][j] = j;

        for (int k = 2; k <= K; k++) {
            for (int n = 2; n <= N; n++) {
                // to compute dp(k,n) we need to try each floor & compute minAttempts as we did in rec
                dp[k][n] = Integer.MAX_VALUE;   // we will minimize, so init with max integer
                for (int i = 1; i <= n; i++) {
                    // attempts for dropping from ith floor
                    int attempts = 1 + Math.max(
                            dp[k-1][i-1],
                            dp[k][n-i]);
                    dp[k][n] = Math.min(dp[k][n], attempts);
                }
            }
        }
        System.out.println(Arrays.deepToString(dp));
        return dp[K][N];
    }

}
