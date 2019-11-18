package com.raj.strings;

import com.raj.Util;

public class LongestRepeatedSubstr {

    public static void main(String[] args) {
        System.out.println(brute("dababac"));
        System.out.println(brute("dabcab"));

        System.out.println(dp("abab"));
        System.out.println(dp("ababa"));
    }

    /**
     * Brute: Enumerate all substr & check with control str
     * - Iter from l to r, fix a char
     * - for each char do a 2nd loop & move to right one char at a time
     * - form incremental control str & check if it is a substr in remaining str. Update longest.
     * o/p : dababac => aba
     *
     * Runtime : There are O(n^2) total substrings and checking them against the remaining string will take O(n^2) or O(n) time.
     * w/ naive matching algo - O(n^4)
     * w/ KMP or RabinKarp matching algo - O(n^3)
     */
    static String brute(String s) {
        String longest = "";
        for (int i = 0; i < s.length(); i++) {  // O(n)
            String controlStr = "";
            for (int j = i; j < s.length(); j++) {  // O(n)
                controlStr += s.charAt(j);
                // naive text match is O(n^2)
                if (s.substring(i+1).contains(controlStr) && controlStr.length() > longest.length()) longest = controlStr;
            }
        }
        return longest;
    }

    /**
     * DP - O(n^2) time & space
     * The basic idea is to find the longest repeating suffix for all prefixes in the string str.
     * https://www.geeksforgeeks.org/longest-repeating-and-non-overlapping-substring/
     * Length of longest non-repeating substring can be recursively defined as below.
     *
     * LCSRe(i, j) stores length of the matching substrings ending
     *             with i'th and j'th characters.
     *
     * If str[i-1] == str[j-1]                    (for non-overlap add cond && (j-i) > LCSRe(i-1, j-1))
     *      LCSRe(i, j) = LCSRe(i-1, j-1) + 1,
     * Else
     *      LCSRe(i, j) = 0
     *
     * Where i varies from 1 to n and
     *       j varies from i+1 to n
     * Also, keep track of max as we go forward
     */
    static String dp(String str) {
        int n = str.length();
        int LCSRe[][] = new int[n + 1][n + 1];

        String res = ""; // To store result
        int res_length = 0; // To store length of result

        // building table in bottom-up manner
        int i, max_idx = 0;
        for (i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                // Add (j-i) > LCSRe[i-1][j-1] to remove overlapping chars
                if (str.charAt(i - 1) == str.charAt(j - 1)) {   // we update table when when chars match
                    //System.out.println((i - 1) + " " + (j - 1));
                    LCSRe[i][j] = LCSRe[i - 1][j - 1] + 1;

                    // updating maximum length of the substring and the finishing index of the suffix
                    if (LCSRe[i][j] > res_length) {
                        res_length = LCSRe[i][j];
                        max_idx = Math.max(i, max_idx);
                    }
                } else {
                    LCSRe[i][j] = 0;
                }
            }
        }

        Util.print2DArray(LCSRe);

        // If we have non-empty result, then build the final repeating substr from max_idx & res_length
        if (res_length > 0) {
            res = str.substring(max_idx - res_length, max_idx);
        }

        return res;
    }

    /**
     * Suffix Tree - Optimal Solution
     * Build a suffix tree using Ukkonen's Algo - O(n)
     * Do dfs - Refer to Suffix Trie.longestRepeatedSubstr_dfs - O(n^2)
     */


}
