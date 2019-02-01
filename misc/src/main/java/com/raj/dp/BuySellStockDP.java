package com.raj.dp;

/**
 * @author rshekh1
 */
public class BuySellStockDP {

    public static void main(String[] args) {
        System.out.println("\nMax profit = $" + buySell(new int[] {4,2,6,8,1,2}));
        System.out.println("\nMax profit = $" + buySellKTransactions(new int[] {10, 22, 5, 75, 65, 80}, 2));
    }

    /**
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * Design an algorithm to find the maximum profit. You may complete at most two transactions.
     *
     * Note:
     * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     *
     * Example :
     *
     * Input : [1 2 1 2]
     * Output : 2
     *
     * Explanation :
     *   Day 1 : Buy
     *   Day 2 : Sell
     *   Day 3 : Buy
     *   Day 4 : Sell
     *
     *   Input: [4,2,6,8,1,2]
     *          4   2   6   8   1   2
     * Profit = 0   0   4   6   6   7
     * Buy?
     *
     */
    private static int buySell(int[] A) {
        int[] dp = new int[A.length];
        boolean isBought = false;

        for (int i = 1; i < A.length; i++) {
            if (A[i] > A[i-1]) {    // time to buy as price has increased
                if (!isBought) System.out.println("Buy  @ " + A[i-1]);
                isBought = true;
                //dp[i] = dp[i-1] + A[i] - A[i-1];
            } else {    // time to sell as price fell
                if (isBought) System.out.println("Sell @ " + A[i-1]);
                isBought = false;
                //dp[i] = dp[i-1];
            }
            // DP Formula : max (dp[i-1], dp[i-1] + (Ai - Ai-1))
            dp[i] = dp[i-1] + Math.max(A[i] - A[i-1], 0);
        }
        if (isBought) System.out.println("Sell @ " + A[A.length-1]);

        System.out.println("DP Table: ");
        for (int i : dp) System.out.print(i + " ");
        return dp[A.length-1];
    }

    /**
     * https://www.geeksforgeeks.org/maximum-profit-by-buying-and-selling-a-share-at-most-k-times/
     *
     * Maximum profit by buying and selling a share at most k times
     * Price = [10, 22, 5, 75, 65, 80], K = 2
     * Output:  87
     * Trader earns 87 as sum of 12 and 75
     * Buy at price 10, sell at 22, buy at 5, sell at 80
     */
    private static int buySellKTransactions(int[] A, int K) {       // O(k.n^2) solution
        int[][] profit = new int[K+1][A.length];
        for (int i = 1; i <= K; i++) {
            for (int j = 1; j < A.length; j++) {
                int maxSoFar = 0;   // find the max profit with using    (k-1) transaction + kth transaction
                for (int m = 0; m < j; m++)
                    maxSoFar = Math.max(maxSoFar, profit[i - 1][m] + (A[j] - A[m]));

                profit[i][j] = Math.max(profit[i][j - 1], maxSoFar);    // take the best which is to include/exclude this max profit
            }
        }

        return profit[K][A.length-1];
    }

    /**
     * https://www.youtube.com/watch?v=oDhu5uGq_ic
     * [See image BuySellStocksKTransactions for understanding maxDiff]
     *
     *                    {     profit[i][j-1],         // don't transact on day j
     * profit[i][j] = max       A[j] + maxDiff,         // transact on day j
     *                          where maxDiff = max(maxDiff, profit[i-1][j] - A[j])
     *                    }
     *
     *  O (k.n) soln
     */
}
