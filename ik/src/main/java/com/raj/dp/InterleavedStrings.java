package com.raj.dp;

import com.raj.Util;

/**
 * @author rshekh1
 */
public class InterleavedStrings {
    /**
     * === HARD Problem ===
     * Given three strings A, B and C. Write a function that checks whether C is an interleaving of A and B.
     * C is said to be interleaving A and B, if it contains all characters of A and B and order of all characters
     * in individual strings is preserved.
     *
     * Input 0:
     * A = "bcc"
     * B = "bbca"
     * C = "bbcbcac"
     * => true
     *
     * Input 1:
     *     A = "aabcc"
     *     B = "dbbca"
     *     C = "aadbbcbcac"
     * Output = true
     * Explanation 1: "aa" (from A) + "dbbc" (from B) + "bc" (from A) + "a" (from B) + "c" (from A)
     *
     * Input 2:
     *     A = "aabcc"
     *     B = "dbbca"
     *     C = "aadbbbaccc"
     * Output = false
     * Explanation 2: It is not possible to get C by interleaving A and B.
     */
    public static void main(String[] args) {
        System.out.println(rec("bcc".toCharArray(), 0, "bbca".toCharArray(), 0, "bcbcbca".toCharArray(), 0));
        System.out.println(dp("bcc".toCharArray(), "bbca".toCharArray(), "bcbcbca".toCharArray()));
        System.out.println(dp("AB".toCharArray(), "D".toCharArray(), "ADBECF".toCharArray()));
    }

    // O(2^n runtime) as branching factor is 2 per recursion frame
    // Hence at leaf level we may have 2^n nodes in worst case (consider A="XXX", B="XXX", C="XXXXXX")
    static boolean rec(char[] A, int i, char[] B, int j, char[] C, int k) {
        if (k == C.length && i == A.length && j == B.length) return true;   // all strings exhausted

        // char may lie in both strings, hence recur on both & then return accordingly
        // also if C's char doesn't match A or B's, then return false
        boolean a = false, b = false;
        if (i < A.length && C[k] == A[i]) a = rec(A, i+1, B, j, C, k+1);
        if (j < B.length && C[k] == B[j]) b = rec(A, i, B, j+1, C, k+1);
        return a || b;  // it will eval false if both A & B don't match. Else, any match evals to true.
    }

    /**
     * Overlapping sub-problem, we can reduce time to O(mn), where m & n are lengths of A & B respectively
     * dp[i][j] = true, if (dp[i-1][j] && A[i-1]==C[i+j]) || (dp[i][j-1] && B[j-1]==C[i+j])
     *
     * A = "bcc", B = "bbca", C = "bbcbcac" => true
     *  B ->   ""  b  b  c  a
     *  A| ""   T  T  T  T  F
     *   v b    T  T  F  T  F
     *     c    F  T  T  T  T      j-1                 i-1
     *     c    F  F  T  F  T  => (A=bcc, B=bbc, F) + (A=bc, B=bbca, T)
     *                         => A's new char match C's, hence, we check if "bc","bbca" is T ?
     *                         => T
     * Time = O(n^2)
     */
    static boolean dp(char[] A, char[] B, char[] C) {
        if (C.length != (A.length + B.length)) return false;

        boolean[][] dp = new boolean[A.length+1][B.length+1];
        // base cases
        dp[0][0] = true;
        for (int i = 1; i <= A.length; i++) {  // bcc, bbcbcac
            dp[i][0] = dp[i-1][0] && (C[i-1] == A[i-1]);
        }
        for (int j = 1; j <= B.length; j++) {  // bbca, bbcbcac
            dp[0][j] = dp[0][j-1] && (C[j-1] == B[j-1]);
        }

        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                dp[i][j] = (dp[i-1][j] && A[i-1]==C[i+j-1])         // is A's char matching? if yes, check dp[A-1][B]
                        || (dp[i][j-1] && B[j-1]==C[i+j-1]);        // is B's char matching? if yes, check dp[A][B-1]
            }
        }
        Util.print2DBooleanArray(dp);
        return dp[A.length][B.length];
    }

}
