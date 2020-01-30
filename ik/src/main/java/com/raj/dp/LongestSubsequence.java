package com.raj.dp;

/**
 * @author rshekh1
 */
public class LongestSubsequence {

    public static void main(String[] args) {
        int[] A = new int[]{30,22,9,33,21,50,41,60,15};
        int[] dp = LIS(A);
        int max = 0;
        System.out.print("DP Table: ");
        for (int n : dp) {
            if (n > max) max = n;
            System.out.print(n + " ");
        }
        System.out.println("Increasing sequence is = ");
        for (int i = 1; i < A.length; i++) {
            //if (dp[i] > dp[i-1]) System.out.print(A[i-1] + " ");
        }
        System.out.println("\nLength of increasing max subsequence = " + max);

        System.out.println("Length of decreasing max subsequence = " + LBS(new int[]{1, 11, 2, 10, 4, 5, 2, 1}));
    }

    /**
     * LONGEST INCREASING SUB-SEQUENCE (non-contiguous)
     * https://www.geeksforgeeks.org/longest-increasing-subsequence-dp-3/  (watch the embedded video for explanation)
     * Input : arr[] = {30,22,9,33,21,50,41,60,15}
     * Output : Length of LIS = 4
     * The longest increasing sub-sequence is {9,21,41,60}
     *
     * A[]      30  22  9   33  21  50  41  60  15
     * LIS      1   1   1   2   2   3   3   4   2
     *
     * Time = O(n^2)
     */
    static int[] LIS(int[] A) {
        int[] dp = new int[A.length];

        // fill dp table for length 1
        for (int i = 0; i < A.length; i++) {
            dp[i] = 1;
        }

        for (int i = 1; i < A.length; i++) {    // keep moving j b/w 0 -> i to find the max subseq so far
            for (int j = 0; j < i; j++) {
                // if this i is greater, meaning it's increasing sequence
                // also maintain only the max sub-sequence length in dp table
                if (A[i] > A[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp;
    }

    // Longest decreasing sub-sequence
    static int[] LDS(int[] A) {
        int[] dp = new int[A.length];

        // fill dp table for length 1
        for (int i = 0; i < A.length; i++) {
            dp[i] = 1;
        }

        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < i; j++) {
                // if this i is lesser, meaning it's decreasing sequence
                // also maintain only the max sub-sequence length in dp table
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
     * Longest inc then dec sub-sequence is [1 2 10 4 2 1]
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

    /**
     * Optimal Solution in O(nlogn):
     * https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
     */

}
