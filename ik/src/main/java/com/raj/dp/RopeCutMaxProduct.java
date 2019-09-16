package com.raj.dp;

/**
 * @author rshekh1
 */
public class RopeCutMaxProduct {
    /**
     * Given a rope with length n, find the maximum value maxProduct, that can be achieved for product =
     * len[0] * len[1] * ... * len[m - 1], where len[] is the array of lengths obtained by cutting the given rope into m parts.
     *
     * Note that: there should be atleast one cut, i.e. m >= 2. All m parts obtained after cut should have non-zero integer valued lengths.
     */
    public static void main(String[] args) {
        System.out.println(max_product_from_cut_pieces(5));
        System.out.println(max_product_from_cut_pieces(6));
        System.out.println(max_product_from_cut_pieces(7));
        System.out.println(max_product_from_cut_pieces(79));
    }

    /**
     * 1D DP array is sufficient as only 2 constraints:
     *
     * rope length   0   1   2   3   4   5   6   7
     * max product   1   1   2   3   4   6   9
     *                                   |
     *                              max(1xf4,
     *                                  2xf3,
     *                                  3xf2,
     *                                  5) --> 5 not allowed for 5 length but we have to consider it for length > 5
     *
     *  dp[len] = max of i*dp[len-i], where i=1 to len-1
     */
    static long max_product_from_cut_pieces(int len) {
        // for length n, we don't have to consider nth length, but after 3 this edge case doesn't need to be handled as f(n-1) > n
        // example - max product for 3 is 2 as 3 isn't considered - we need to make at least 1 cut as per problem statement
        if (len <= 3) return len - 1;

        long[] dp = new long[len+1];
        dp[0] = 1; dp[1] = 1; dp[2] = 2; dp[3] = 3;

        // O(n^2)
        for (int i = 4; i <= len; i++) {
            long max = 0;
            for (int j = 1; j < i; j++) {
                max = Math.max(max, j * dp[i - j]);
            }
            dp[i] = max;
        }
        return dp[len];
    }

}
