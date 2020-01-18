package com.raj.dp;

import java.util.Arrays;
import java.util.Stack;

public class LongestCommonSubsequence {

    /**
     * https://www.geeksforgeeks.org/longest-common-subsequence-dp-using-memoization/
     * https://www.geeksforgeeks.org/longest-common-subsequence-dp-4/
     * Examples:
     * LCS for input Sequences "abracadabra" and "bradcard" is "bracar" of length 6.
     * LCS for input Sequences "ABCDGH" and "AEDFHR" is "ADH" of length 3.
     */
    public static void main(String[] args) {
        System.out.println(rec("abracadabra".toCharArray(), 0, "bradcard".toCharArray(), 0));
        System.out.println(dp("abracadabra".toCharArray(), "bradcard".toCharArray()));
    }

    /**
     * Iteratively, you won't be able to formulate loop, hence think of it as a "subset pattern" problem.
     *
     * Brute Force:
     * Generate all possible subsets of string A & store it in map.
     * Generate all possible subsets of string B and check if it exists in map, if yes we found a match.
     * Update max length.
     *
     * Time = O(2^n + 2^m)
     * Space = O(2^n)
     */

    /**
     * SubOptimal: Using recursion, we optimize on space & reduce it to O(depth of rec stack) = O(log n)
     *
     * abracadabra
     * bradcard
     *           i=0,j=0
     *         /        \           ... 1st char mismatch
     *      i=1,j=0    i=0,j=1      ... try both options - exclude i & search in B, include i & search in B(j+1...len) as we don't know where the match may lie
     *       |           ..         ... chars match
     *      i=2,j=1                 ... incr both ptrs and recurse
     *
     * Time = O(2^n + 2^m) exponential
     */
    static int rec(char[] A, int i, char[] B, int j) {
        // base case
        if (i >= A.length || j >= B.length) return 0;

        // character match
        if (A[i] == B[j]) return 1 + rec(A, i+1, B, j+1);
        else {  // character mismatch
            return Math.max(rec(A, i+1, B, j),      // exclude ith char and search in B
                    rec(A, i, B, j+1));     // include ith char and search in B(j+1 ... len)
        }
    }

    /**
     *             # a b r a c ...
     *          #  0 0 0 0 0 0 0
     *          b  0 0 1 1 1
     *          r  0 0 1 2 ---> chars match incr + 1
     *          a  0 1 1 2 ---> chars mismatch carry forward max of above col or prev col
     *          d
     *          .
     *
     * DP Formula:
     * dp[i][j] = 1 + dp[i-1][j-1] , if chars match
     *            max(dp[i-1][j], dp[i][j-1]) , if chars mismatch
     *
     * Time & Space = O(n * m)
     */
    static int dp(char[] A, char[] B) {
        int[][] dp = new int[A.length+1][B.length+1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i-1] == B[j-1]) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        System.out.println(Arrays.deepToString(dp));

        // print the lcs string
        Stack<Character> st = new Stack();
        int i=A.length; int j=B.length;
        while(i>0 && j>0) {
            if (dp[i][j] == dp[i-1][j-1]) {i--;j--;}
            else if (dp[i][j] == dp[i-1][j]) i--;
            else if (dp[i][j] == dp[i][j-1]) j--;
            else if (dp[i][j] == 1+dp[i-1][j-1]) {st.add(A[i-1]);i--;j--;}
            else if (dp[i][j] == 1+dp[i-1][j]) {st.add(A[i-1]);i--;}
            else if (dp[i][j] == 1+dp[i][j-1]) {st.add(A[i-1]);j--;}
        }

        String res = "";
        while(!st.isEmpty()) res += st.pop()+"";
        System.out.println("Longest Common Sub-sequence => " + res);

        return dp[A.length][B.length];
    }

    // alternative to print string using dfs guided by dp table results
    private static void dfs_lcs(int[][] dp, String a, String b, int i, int j, StringBuilder sb){
        if(i >= a.length() || j >= b.length()) return;
        if (a.charAt(i) == b.charAt(j)) {
            sb.append(a.charAt(i));
            dfs_lcs(dp, a, b, i+1, j+1, sb);
        } else if(dp[i][j+1] > dp[i+1][j]) {
            dfs_lcs(dp, a, b, i, j+1, sb);
        } else {
            dfs_lcs(dp, a, b, i+1, j, sb);
        }
    }

}
