package com.raj.dp;

/**
 * @author rshekh1
 */
public class WaysToDecode {

    public static void main(String[] args) {
        System.out.println(countDecoding("126".toCharArray(), 3));
        System.out.println(countDecodingDP("126".toCharArray(), 3));

        /**
         * Input: digits[] = "1234"
         * Output: 3
         * The possible decodings are "ABCD", "LCD", "AWD"
         */
        System.out.println(countDecodingDP("1234".toCharArray(), 4));
    }

    /**
     * A message containing letters from A-Z is being encoded to numbers using the following mapping:
     *
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * Given an encoded message containing digits, determine the total number of ways to decode it.
     *
     * Example :
     * Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
     * The number of ways decoding "12" is 2.
     *
     * Refer to PermuteStr.permute(), we solved like (memoization seems difficult here):
     *                   "",f126
     *                /    |    \
     *           [1],f26  [12],f6  [126],f""
     *            /    |         \
     *       [1,2],f6 [1,26],f""  [12,6]
     *         /
     *       [1,2,6]
     *
     * Using DP solve: https://www.geeksforgeeks.org/count-possible-decodings-given-digit-sequence/
     * Given a digit sequence of length n, return count of possible decodings by replacing
     * 1 with A, 2 with B, ... 26 with Z
     */

    // O(2^n) exponential
    static int countDecoding(char[] digits, int n) {   // n represents current position in digit array while recursion
        // base case
        if (n == 0 || n == 1)  return 1;

        // Initialize count
        int count = 0;

        // count 1 digit combinations, if this digit is valid
        if (digits[n - 1] > '0')  count = countDecoding(digits, n - 1);

        // count 2 digit combinations, if the 2 digits are valid(10 -> 26)
        if (digits[n - 2] == '1' || (digits[n - 2] == '2' && digits[n - 1] < '7'))
            count += countDecoding(digits, n - 2);

        // add them and return
        return count;
    }

    // O(n) dp
    static int countDecodingDP(char[] digits, int n) {   // n represents current position in digit array while recursion
        int[] dp = new int[n + 1];
        dp[0] = 1; dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            if (digits[i - 1] > '0') dp[i] = dp[i - 1];
            if (digits[i - 2] == '1' || (digits[i - 2] == '2' && digits[i - 1] < '7')) dp[i] += dp[i - 2];
        }
        return dp[n];
    }

}
