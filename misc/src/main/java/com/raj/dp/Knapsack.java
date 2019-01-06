package com.raj.dp;

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
     */
    public int dp_W_n0(int n) {
        int i, w;
        int K[][] = new int[n+1][W_total+1];

        // Build table K[][] in bottom up manner
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W_total; w++) {
                if (i==0 || w==0)  K[i][w] = 0;
                else if (W[i-1] <= w) K[i][w] = Math.max(V[i-1] + K[i-1][w-W[i-1]], K[i-1][w]);
                else K[i][w] = K[i-1][w];
                System.out.print(K[i][w] + " ");
            }
            System.out.println();
        }

        return K[n][W_total];
    }

    // Returns the maximum value that can be put in a knapsack of capacity W
    static int knapSack(int W, int wt[], int val[], int n)
    {
        int i, w;
        int K[][] = new int[n+1][W+1];

        // Build table K[][] in bottom up manner
        for (i = 0; i <= n; i++)
        {
            for (w = 0; w <= W; w++)
            {   comparisons ++;
                if (i==0 || w==0)
                    K[i][w] = 0;
                else if (wt[i-1] <= w)
                    K[i][w] = Math.max(val[i-1] + K[i-1][w-wt[i-1]],  K[i-1][w]);
                else
                    K[i][w] = K[i-1][w];
                System.out.print(K[i][w] + " ");
            }
            System.out.println();
        }

        return K[n][W];
    }

    public static void main(String[] args) {
        Knapsack ks = new Knapsack();
        comparisons = 0;
        System.out.println(ks.recursive_2_power_n(0, 0) + " , Comparisons = " + comparisons);

        comparisons = 0;
        System.out.println(ks.dp_W_n(W.length-1, W_total) + " , Comparisons = " + comparisons);

        int val[] = new int[]{60, 100, 120};
        int wt[] = new int[]{10, 20, 30};
        int  W = 50;
        int n = val.length;
        System.out.println(knapSack(W, wt, val, n) + " , Comparisons = " + comparisons);
        System.out.println(ks.dp_W_n0(n));
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
