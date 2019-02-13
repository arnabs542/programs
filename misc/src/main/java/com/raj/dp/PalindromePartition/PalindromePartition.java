package com.raj.dp.PalindromePartition;

import java.util.ArrayList;

/**
 * @author rshekh1
 */
public class PalindromePartition {

    static int comparisons = 0;
    public static void main(String[] args) {
        String s = "kkk";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i+1; j <= s.length(); j++) {
                System.out.println("[" + i + "," + j + "] -> " + s.substring(i,j));
            }
        }
        printPalindromes_brute("dbabb");
        System.out.println("Comparisons=" + comparisons);

        partition("nitin");
        System.out.println(res);

        System.out.println(minCut("abcbcm"));   // a|bcb|m
        System.out.println(minCut("BANANA"));   // B|ANANA
    }

    /**
     * Print all palindromes within a String
     * dbabb -> d,b,a,b,b,bab,bb
     * O(n^2) solve
     */
    public static void printPalindromes_brute(String s) {
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                comparisons ++;
                if (isPalindrome(s.substring(i,j))) System.out.println(s.substring(i,j));
            }
        }
    }

    public static boolean isPalindrome(String s) {
        if (s.isEmpty()) return false;
        for (int i = 0,j = s.length()-1; i < j; i++, j--) {
            comparisons ++;
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }


    /**
     * Given a string s, partition s such that every string of the partition is a palindrome.
     * Return all possible palindrome partitioning of s.
     *
     * "aab" => [ ["a","a","b"], ["aa","b"] ]
     */
    static ArrayList<ArrayList<String>> res = new ArrayList<>();

    private static void partition(String a, int start, ArrayList<String> curPartitions) {
        if (start == a.length()) {
            res.add(new ArrayList<>(curPartitions));
            return;
        }
        for (int i = start + 1; i <= a.length(); i++) {
            String substr = a.substring(start, i);
            if (isPalindrome(substr)) {     // find a palindrome from starting left, partition at that char
                curPartitions.add(substr);           // add palindrome
                partition(a, i, curPartitions);      // and recurse on remaining
                curPartitions.remove(curPartitions.size() - 1);
            }
        }
    }

    public static ArrayList<ArrayList<String>> partition(String a) {
        res = new ArrayList<>();
        if (a.length() == 0) return res;
        partition(a, 0, new ArrayList<>());
        return res;
    }

    /**
     * https://www.youtube.com/watch?v=lDYIvtBVmgo
     * https://www.geeksforgeeks.org/palindrome-partitioning-dp-17/
     *
     * Given a string s, partition s such that every substring of the partition is a palindrome.
     * Return the minimum cuts needed for a palindrome partitioning of s.
     *
     * aab => 1 [aa | b]
     * abcbm => 2 [a | bcb | m]
     *
     * Algo:
     * Basically we need to compute costs of cutting at all possible partitions and take the min of it
     * Use DP to save sub results
     * Cost of a substring which is palindrome is 0, else it is 1 + cost of either sides
     *
     * dp[i][j] = 0, if substring(i,j) is a palindrome  ==> has O(n^2) complexity
     *            else 1 + min { dp[i][k] + dp[k+1][j], for k = 1 to j-1 }  ==> has O(n^2) complexity
     *
     *  === is Palindrome dp table  ==== (no need to fill bottom left half as the inner iteration happens for i to n)
     *          a   b   c   b   m
     *          0   1   2   3   4  (array idx)
     *   a  0   T   F   F   F   F
     *   b  1       T   F   T   F
     *   c  2           T   F   F
     *   b  3               T   F
     *   m  4                   T
     *
     *   === Min palindrome partition cost dp table ===
     *          a   b   c   b   m
     *          0   1   2   3   4  (array idx)          eg. bcbm = min of b|cbm, bc|bm, bcb|m
     *   a  0   0   1   2   1   2  dp[i][j] = cost
     *   b  1       0   1   0   1
     *   c  2           0   1   2
     *   b  3               0   1
     *   m  4                   0
     *
     *   min cost DP can as well be computed using a 1D array as described in below link:
     *   https://www.youtube.com/watch?v=WPr1jDh3bUQ
     *
     *  B   A   N   A   N   A       (i goes for length 0 to n & inner j goes for 0 to i)
     *  0   1   2   1   2   1
     *
     */
    private static int minCut(String s) {
        int n = s.length();

        // Fill palindrome dp table first as we'll use it compute palindrome partition cost later
        boolean[][] isPalin = new boolean[n][n];
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {     // +1 for offsetting length starting as 1
                int j = i + len - 1;                    // end index for substring
                if (len == 1) isPalin[i][j] = true;     // 1 char length substring is already a palindrome
                else if (len == 2) isPalin[i][j] = s.charAt(i) == s.charAt(j);  // 2 char lengths palindromes
                else isPalin[i][j] = s.charAt(i) == s.charAt(j) && isPalin[i+1][j-1];
            }
        }

        // Now compute the min cost for palindrome partitioning we can do it using a 1D array maintaining min cost as we move though the string from 0 to n
        int[] minCuts = new int[n];
        for (int i = 0; i < n; i++) {
            int minCutsSoFar = Integer.MAX_VALUE;
            if (isPalin[0][i]) minCuts[i] = 0;
            else {
                for (int j = 0; j < i; j++) {   // in each iteration we start again from 0 and compute min cost for 1 char to the right by trying a cut at j
                    if (isPalin[j+1][i])    // if the substring towards right of cut is already a palindrome, we incur +1 cost only
                        minCutsSoFar = Math.min(minCutsSoFar, minCuts[j] + 1); // keep updating min cuts
                }
                minCuts[i] = minCutsSoFar;  // assign the min computed cuts for this length
            }
        }
        return minCuts[n-1];
    }

}
