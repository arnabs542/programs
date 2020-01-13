package com.raj.dp;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author rshekh1
 */
public class BuySellStockDP {

    public static void main(String[] args) {
        System.out.println("\nMax profit = $" + buySell(new int[] {4,2,6,8,1,2}));
        System.out.println("\nMax profit = $" + buySellKTransactions(new int[] {10, 20, 30, 40 ,50, 60}, 2));
        System.out.println("\nMax profit = $" + buySellKTransactions_optimal(new int[] {10, 20, 30, 40 ,50, 60}, 2));
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
     *
     * Let profit[t][i] represent maximum profit using at most t transactions up to day i (including day i)
     */
    private static int buySellKTransactions(int[] A, int K) {       // O(k.n^2) solution
        int[][] profit = new int[K+1][A.length];
        for (int i = 1; i <= K; i++) {
            System.out.println("======= K = " + i);
            for (int j = 1; j < A.length; j++) {
                int maxSoFar = 0;   // find the max profit with using    (k-1) transaction + kth transaction
                System.out.println("For A[j] => "+ A[j]);
                for (int m = 0; m < j; m++) {
                    System.out.println("m => " + m + ", profit[i-1][m]="+profit[i-1][m] + ", A[j]-A[m]="+(A[j]-A[m]));
                    maxSoFar = Math.max(maxSoFar, profit[i-1][m] + (A[j]-A[m]));
                }
                System.out.println("maxSoFar = " + maxSoFar + ",profit[i][j-1]="+profit[i][j-1]);
                profit[i][j] = Math.max(profit[i][j-1], maxSoFar);    // take the best which is to include/exclude this max profit
                System.out.println("Final profit[i][j] = " + profit[i][j]);
            }
        }
        System.out.println(Arrays.deepToString(profit));
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
    /**
     * This is faster method which does optimization on slower method above which was O(k*n^2)
     * Time complexity here is O(K * number of days)
     *
     * Formula is -
     * T[i][j] = max(T[i][j-1], prices[j] + maxDiff)
     * maxDiff = max(maxDiff, T[i-1][j] - prices[j])
     * How ???? Not clear
     */
    public static int buySellKTransactions_optimal(int prices[], int K) {
        if (K == 0 || prices.length == 0) {
            return 0;
        }
        int T[][] = new int[K+1][prices.length];

        for (int i = 1; i < T.length; i++) {
            int maxDiff = -prices[0];
            for (int j = 1; j < T[0].length; j++) {
                T[i][j] = Math.max(T[i][j-1], prices[j] + maxDiff);
                maxDiff = Math.max(maxDiff, T[i-1][j] - prices[j]);
            }
            System.out.println(maxDiff);
        }
        printActualSolution(T, prices);
        return T[K][prices.length-1];
    }

    public static void printActualSolution(int T[][], int prices[]) {
        int i = T.length - 1;
        int j = T[0].length - 1;

        Deque<Integer> stack = new LinkedList<>();
        while(true) {
            if(i == 0 || j == 0) {
                break;
            }
            if (T[i][j] == T[i][j-1]) {
                j = j - 1;
            } else {
                stack.addFirst(j);
                int maxDiff = T[i][j] - prices[j];
                for (int k = j-1; k >= 0; k--) {
                    if (T[i-1][k] - prices[k] == maxDiff) {
                        i = i - 1;
                        j = k;
                        stack.addFirst(j);
                        break;
                    }
                }
            }
        }

        while(!stack.isEmpty()) {
            System.out.println("Buy at price " + prices[stack.pollFirst()]);
            System.out.println("Sell at price " + prices[stack.pollFirst()]);
        }
    }

}
