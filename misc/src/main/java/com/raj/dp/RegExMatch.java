package com.raj.dp;

/**
 * @author rshekh1
 */
public class RegExMatch {

    public static void main(String[] args) {
        System.out.println(isMatch("aa", "a"));
        System.out.println(isMatch("aa", "aa"));
        System.out.println(isMatch("aaa", "aa"));
        System.out.println(isMatch("aa", "a*"));
        System.out.println(isMatch("ab", "?*"));
        System.out.println(isMatch("aab", "c*a*b"));
        System.out.println(isMatch("ccdcdcaaaffb", "c*a*b"));
        System.out.println(isMatch("aaa", "*"));
    }

    /**
     * Implement wildcard pattern matching with support for '?' and '*'.
     * '?' : Matches any single character.
     * '*' : Matches any sequence of characters (including the empty sequence).
     * The matching should cover the entire input string (not partial).
     * Examples :
     * isMatch("aa","a") → 0
     * isMatch("aa","aa") → 1
     * isMatch("aaa","aa") → 0
     * isMatch("aa", "*") → 1
     * isMatch("aa", "a*") → 1
     * isMatch("ab", "?*") → 1
     * isMatch("aab", "c*a*b") → 0
     *
     */
    private static boolean isMatch(String s, String regEx) {
        return isMatch_brute(0,0,s.toCharArray(),regEx.toCharArray()) &&
                isMatch_dp(s, regEx);
        /*if ((s == null || s.isEmpty()) && (regEx == null || regEx.isEmpty() || regEx.equals("*"))) return true;
        if (s == null || s.isEmpty() || regEx == null || regEx.isEmpty()) return false;
        return isMatch(s, 0, regEx, 0);*/
    }

    /**
     * Almost linear solve but not elegant code
     */
    private static boolean isMatch(String s, int i, String regEx, int j) {
        if (i == s.length() && (j == regEx.length() || regEx.charAt(j) == '*')) return true;
        if (i >= s.length() || j >= regEx.length()) return false;
        if (regEx.charAt(j) == '*') {
            if (regEx.length() == j+1) return true; // ab*
            while (j+1 < regEx.length() && s.charAt(i) != regEx.charAt(j+1)) {   // ab*d
                i++;
            }
            j++;
        } else if (regEx.charAt(j) == '?') {    // ab?c
            return isMatch(s, i+1, regEx, j+1);
        }

        if (s.charAt(i) != regEx.charAt(j)) return false;

        return isMatch(s, i+1, regEx, j+1);
    }

    /**
     * Simple elegant solution, but exponential complexity as it recurses through all possible combinations like * matches 0 or many chars
     */
    private static boolean isMatch_brute(int sIdx, int pIdx, char[] s, char[] p) {
        // if pattern has reached end then text should also end for a match to happen
        if (pIdx == p.length) return sIdx == s.length;
        // * means you either match 0 or more chars
        if (p[pIdx] == '*') return isMatch_brute(sIdx, pIdx+1, s, p) || isMatch_brute(sIdx+1, pIdx, s, p);
        // ? means you exactly match 1 char
        if (p[pIdx] == '?') return isMatch_brute(sIdx+1, pIdx+1, s, p);
        // else if it's not special chars, we need exact match
        if (s[sIdx] != p[pIdx]) return false;
        // finally try the next char with next regex
        return isMatch_brute(sIdx+1, pIdx+1, s, p);
    }

    /**
     * https://www.youtube.com/watch?v=3ZDZ-N0EPV0
     *
     * DP quadratic soln, extending previous cases into memoization
     *
     *              0   a   g   m   b   => text
     *          0   T   F   F   F   F
     * [a]      a   F   T   F   F   F
     * [a*]     *   F   T   T   T   T
     * [a*b]    b   F   F   F   F   T       eg, matching a*b with agmb, we check last char matches in this iteration, plus if [a*, agm] = true
     * [Pattern]                                matching a* with agm, we check * with and without char => [a*,ag]
     *
     * Formula: dp[i][j] =  * => empty space or this char match plus earlier value => dp[i][j-1] || dp[i-1][j]
     *                      ? => this matches, check before value => dp[i-1][j-1]
     *                      char match, check previous dp vale => dp[i-1][j-1]
     *                      don't match, then false
     *
     */
    private static boolean isMatch_dp(String text, String pattern) {
        boolean match[][] = new boolean[pattern.length()+1][text.length()+1];   // +1 for empty cases either side

        match[0][0] = true;     // both test and pattern are null
        if (pattern.length() > 0 && pattern.charAt(0) == '*') match[1][0] = true;   // edge case

        for (int i = 1; i <= pattern.length(); i++) {
            for (int j = 1; j <= text.length(); j++) {

                // Two cases if we see a '*'
                // a) We ignore '*' character and move to next character in the pattern, i.e. '*' indicates an empty sequence.
                // b) '*' character matches with ith character in input
                if (pattern.charAt(i - 1) == '*') match[i][j] = match[i][j - 1] || match[i - 1][j];

                // Current characters are considered as matching in two cases
                // (a) current character of pattern is '?'
                else if (pattern.charAt(i - 1) == '?') match[i][j] = match[i - 1][j - 1];

                // (b) characters actually match
                else if (text.charAt(j - 1) == pattern.charAt(i - 1)) match[i][j] = match[i - 1][j - 1];

                // If characters don't match
                else match[i][j] = false;
            }
        }
        return match[pattern.length()][text.length()];
    }

}
