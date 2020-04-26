package com.raj.strings;

public class LongestPalinSubstr {

    /**
     * Find the Longest Palindromic Substring (contiguous chars)
     */
    public static void main(String[] args) {
        System.out.println(bruteForce("abcbabcbcdddd"));  //o/p = bcbabcb
        System.out.println("Longest Palin = " + quadratic("abcbabcbcdddd"));
    }

    /**
     * BF Algo:
     * Enumerate all Substrings of the String ... O(n^2)
     * Check if each of these substr is a Palindrome ... O(n)
     * Runtime = O(n^3) polynomial
     * Note - Recursive solve cud be exponential & may have higher memory footprint, hence this is better.
     */
    static String bruteForce(String s) {
        int max_i = -1, max_j = -1;

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                System.out.println("substr = " + s.substring(i, j+1));
                if (isPalin(s.substring(i, j+1)) && (j+1-i) > (max_j+1 - max_i)) {  // is a palin & has greater length
                    max_i = i; max_j = j;
                    break; // no need to go further in this loop as next iter wud be of smaller length
                }
            }
        }
        if (max_i == -1) return ""; // no palins found
        return s.substring(max_i, max_j+1);
    }

    static boolean isPalin(String s) {
        for (int i = 0, j = s.length()-1; i < s.length() && j > 0; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }

    /**
     * Algo: "Slide & Expand around center"
     * For an O(n^2) algo which is better than BF, we'll need to pick each char and loop on rest
     * Picking left & right won't do the trick, DP might be complicated
     * # Fix each char from left and expand simultaneously from 'chosen' mid until not palin
     * # Check if left & right ptrs match, keep updating max
     * # Above wud wrk for odd lengths, for even length just fix left & right adjacent
     * # Keep 2 loops for each odd & even lengths for simplicity
     * Runtime = O(n^2)
     */
    static String quadratic(String s) {
        if (s.isEmpty() || s.length() == 1) return s;

        int max_i = -1, max_j = -1;

        for (int i = 1; i < s.length(); i++) {
            // find odd length max palin
            int left = i, right = i;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                System.out.println("Odd palin = " + s.substring(left, right+1));
                if ((right-left) > (max_j-max_i)) {
                    max_i = left; max_j = right;
                }
                left--; right++;
            }
            // find odd length max palin
            left = i-1; right = i;
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                if ((right-left) > (max_j-max_i)) {
                    max_i = left; max_j = right;
                }
                System.out.println("Evn palin = " + s.substring(left, right+1));
                left--; right++;
            }
        }
        if (max_i == -1) return s.substring(0, 1);
        return s.substring(max_i, max_j+1);
    }

    /**
     * Another Linear solve ? Manacher's Algo : https://www.youtube.com/watch?v=V-sEwsca1ak
     * https://en.wikipedia.org/wiki/Longest_palindromic_substring
     * Not required for interview
     */

}
