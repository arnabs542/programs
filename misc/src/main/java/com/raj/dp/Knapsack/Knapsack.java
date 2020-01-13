package com.raj.dp.Knapsack;

/**
 * 0-1 Knapsack problem : Maximize value of items added to a Knapsack that can only carry upto a maximum weight
 * https://www.geeksforgeeks.org/knapsack-problem/
 *
 * W [10, 20, 30]       // weight
 * V [60, 100, 120]     // value
 * Allowed Weight = 50
 */
public class Knapsack {

    static int[] W = {10,20,30,40,50};
    static int[] V = {60,100,120,150,180};
    static int W_total = 50;
    int N = W.length;
    static int comparisons = 0;

    /**
     * O(2^n) exponential complexity as each option has 2 paths
     * => each level adds twice the number of steps => 2 + 4 + 8 + 16 ... => 2^n
     *
     * Constant space complexity
     *
     *              f(i, w)
     *              /      \
     *     f(i+1,w+W[i])   f(i+1,w)    ----> each level 2 child for each option
     *        /     \           /   \
     *f(i+2,w'+W[i]) f(i+2,w')  ..  ....
     *
     * @param i
     * @param weightSoFar
     * @return
     */
    public int recursive_2_power_n(int i, int weightSoFar) {
        comparisons ++;
        // Base case
        if (i == N || weightSoFar == W_total) return 0; // exhausted all options or no more weight left

        // try ith option, if the weight is within limits or don't try it - evaluate their values & find max
        if (weightSoFar + W[i] <= W_total) {
            return Math.max(V[i] + recursive_2_power_n(i+1, weightSoFar + W[i]),  // try i
                    recursive_2_power_n(i+1, weightSoFar));  // don't try i
        } else return recursive_2_power_n(i+1, weightSoFar);
    }


    private int[][] dpTable = new int[W.length][W_total+1];

    /**
     * Fill DP table right to left for overlapping sub problem results
     * @param n
     * @param weightLeft
     * @return
     */
    public int dp_W_n(int n, int weightLeft) {
        comparisons ++;
        // Base case
        if (n <= 0 || weightLeft <= 0) return 0; // exhausted all options or no more weight left

        if (dpTable[n][weightLeft] > 0) return dpTable [n][weightLeft];  // already pre-computed

        // try ith option, if the weight is within limits or don't try it - evaluate their values & find max
        if (W[n] <= W_total) {
            dpTable[n][weightLeft] = Math.max(V[n] + dp_W_n(n-1, weightLeft - W[n]),  // try i
                    dp_W_n(n-1, weightLeft));  // don't try i
        } else dpTable[n][weightLeft] = dp_W_n(n-1, weightLeft);

        //print(dpTable);
        return dpTable[n][weightLeft];
    }

    /**
     * Trying to find the optimal substructure: https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
     * Returns the maximum value that can be put in a knapsack of capacity W
     */
    static int knapSack(int W, int wt[], int val[], int n) {
        int i, w;
        int K[][] = new int[n+1][W+1];

        // Build table K[][] in bottom up manner
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {   comparisons ++;
                if (i==0 || w==0)
                    K[i][w] = 0;
                else if (wt[i-1] <= w)
                    K[i][w] = Math.max(val[i-1] + K[i-1][w-wt[i-1]],  K[i-1][w]);
                else
                    K[i][w] = K[i-1][w];
            }
        }
        return K[n][W];
    }


    static int tot_wt;
    static int num_items;
    static int[][] knapsackTable;

    static int knapsack_dp(int[] W, int[] V) {
        knapsackTable = new int[num_items+1][tot_wt+1];
        // start from 1,1 onwards as 0th col, rows will be 0
        for (int n = 1; n <= num_items; n++) {      // take or not take item 'n'
            for (int w = 1; w <= tot_wt; w++) {     // allowed weight
                int takeItem = 0;           comparisons ++;
                int notTakeItem = knapsackTable[n-1][w];    // take whatever we computed for the last n-1th & w
                if (W[n] <= w) {     // if weight of this item is less than allowed weight
                    takeItem = V[n] + knapsackTable[n-1][w-W[n]];
                }
                knapsackTable[n][w] = Math.max(takeItem, notTakeItem);
            }
        }
        showResult(W, V);
        return knapsackTable[num_items][tot_wt];
    }

    // shows which items were included in knapsack traversing back from DP table
    static void showResult(int[] W, int[] V) {
        System.out.println("\nMax value of knapsack = " + knapsackTable[num_items][tot_wt] + " lbs");
        for (int n = num_items, w = tot_wt; n > 0; n--) {
            if (knapsackTable[n][w] != 0 && knapsackTable[n][w] != knapsackTable[n-1][w]) {
                System.out.println("Selected item " + n + " with weight, value = " + W[n] + " lb, $" + V[n]);
                w -= W[n];
            }
        }
    }

    public static void main(String[] args) {
        Knapsack ks = new Knapsack();
        comparisons = 0;
        System.out.println(ks.recursive_2_power_n(0, 0) + " , Comparisons = " + comparisons);
        comparisons = 0;
        System.out.println(knapSack(5, new int[]{4,2,3}, new int[]{10,4,7}, 3) + " , Comparisons = " + comparisons);

        tot_wt = 5;
        num_items = 3;
        int V[] = new int[]{0, 10, 4, 7};  // pad 0 (no item, no weight selected) for keeping index 1 for actual values
        int W[] = new int[]{0, 4, 2, 3};
        knapsack_dp(W, V);
    }

    static void print(int[][] dpTable) {
        for (int i = 0; i < dpTable.length; i++) {
            for (int j = 0; j <= W_total; j++) {
                System.out.print(dpTable[i][j] + " ");
            }
            System.out.println();
        }
    }

}
