package com.raj.dp;

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
        System.out.println(isStringInterleaved_recur("bcc", 0, "bbca", 0, "bcbcbca", 0));
        System.out.println(isStringInterleaved_dp("bcc".toCharArray(), "bbca".toCharArray(), "bcbcbca".toCharArray()));
    }

    // O(2^n runtime) as branching factor is 2 per recursion frame
    // Hence at leaf level we may have 2^n nodes in worst case (consider A="XXX", B="XXX", C="XXXXXX")
    static boolean isStringInterleaved_recur(String A, int i, String B, int j, String C, int k) {
        if (k == C.length() && i == A.length() && j == B.length()) return true;   // all strings exhausted

        // char may lie in both strings, hence recur on both & then return accordingly
        // also if C's char doesn't match A or B's, then return false
        boolean a = false, b = false;
        if (i < A.length() && C.charAt(k) == A.charAt(i)) a = isStringInterleaved_recur(A, i+1, B, j, C, k+1);
        if (j < B.length() && C.charAt(k) == B.charAt(j)) b = isStringInterleaved_recur(A, i, B, j+1, C, k+1);
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
     *                         => discard F tuple, for T see if the new char equals C's new char
     *                         => T
     */
    static boolean isStringInterleaved_dp(char[] A, char[] B, char[] C) {
        boolean[][] dp = new boolean[A.length+1][B.length+1];

        // base cases
        dp[0][0] = true;
        for (int i = 1; i <= A.length; i++) {
            dp[i][0] = dp[i-1][0] && (C[i-1] == A[i-1]);
        }
        for (int i = 1; i <= B.length; i++) {
            dp[0][i] = dp[0][i-1] && (C[i-1] == B[i-1]);
        }

        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                dp[i][j] = (dp[i-1][j] && A[i-1]==C[i+j-1]) || (dp[i][j-1] && B[j-1]==C[i+j-1]);
            }
        }
        return dp[A.length][B.length];
    }

}
