package com.raj.dp;

/**
 * @author rshekh1
 */
public class LongestSubsequence {

    public static void main(String[] args) {
        int[] dp = LIS(new int[]{50, 3, 10, 7, 40, 80});
        int max = 0;    for (int n : dp) if (n > max) max = n;
        System.out.println(max);

        System.out.println(LBS(new int[]{1, 11, 2, 10, 4, 5, 2, 1}));
    }

    /**
     * LONGEST INCREASING SUBSEQUENCE (non-contiguous)
     * https://www.geeksforgeeks.org/longest-increasing-subsequence-dp-3/
     * Input : arr[] = {50, 3, 10, 7, 40, 80}
     * Output : Length of LIS = 4
     * The longest increasing subsequence is {3, 7, 40, 80}
     */
    static int[] LIS(int[] A) {
        int[] dp = new int[A.length];

        // fill dp table for length 1
        for (int i = 0; i < A.length; i++) {
            dp[i] = 1;
        }

        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                // if this i is greater, meaning it's increasing sequence
                // also maintain only the max subsequence length in dp table
                if (A[i] > A[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                }
            }
        }
        return dp;
    }

    // Longest decreasing subsequence
    static int[] LDS(int[] A) {
        int[] dp = new int[A.length];

        // fill dp table for length 1
        for (int i = 0; i < A.length; i++) {
            dp[i] = 1;
        }

        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                // if this i is lesser, meaning it's decreasing sequence
                // also maintain only the max subsequence length in dp table
                if (A[i] < A[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                }
            }
        }
        return dp;
    }

    /**
     * LONGEST BITONIC (increasing then decreasing) SUBSEQUENCE (non-contiguous)
     * https://www.geeksforgeeks.org/longest-bitonic-subsequence-dp-15/
     *
     * For the given array [1 11 2 10 4 5 2 1]
     * Longest inc then dec subsequence is [1 2 10 4 2 1]
     * Return value 6
     *
     * Algo:
     * Find longest increasing lis[] & longest decreasing lds[] subsequences
     * For each i, find max of 0->i inc & i->n dec lengths
     */
    static int LBS(int[] A) {
        int[] dp_lis = LIS(A);
        int[] dp_lds = LDS(A);
        int max = 0;
        for (int i = 0; i < A.length; i++) {
            max = Math.max(dp_lis[i] + dp_lds[i] - 1, max);  // -1 to compensate for the double counting of ith element
        }
        return max;
    }

}
