package com.raj.arrays;

import java.util.HashMap;
import java.util.Map;

public class MiscStringFB {

    /**
     * Matching Pairs
     * Given two strings s and t of length N, find the maximum number of possible matching pairs in strings s and t
     * after swapping exactly two characters within s.
     *
     * Example 1
     * s = "abcd"
     * t = "adcb"
     * output = 4
     * Explanation:
     * Using 0-based indexing, and with i = 1 and j = 3, s[1] and s[3] can be swapped, making it  "adcb".
     * Therefore, the number of matching pairs of s and t will be 4.
     *
     * Example 2
     * s = "mno"
     * t = "mno"
     * output = 1
     * Explanation:
     * Two indices have to be swapped, regardless of which two it is, only one letter will remain the same. If i = 0 and j=1, s[0] and s[1] are swapped, making s = "nmo", which shares only "o" with t.
     */
    public static void main(String[] args) {
        System.out.println(matchingPairs("abcd", "adcb"));
        System.out.println(matchingPairs("mno", "mno"));
        System.out.println(matchingPairs("abde", "abfg"));

        System.out.println(minLengthSubstring("dcbefebce", "fd"));
        System.out.println(minLengthSubstring("bfbeadbcbcbfeaaeefcddcccbbbfaaafdbebedddf", "cbccfafebccdccebdd"));
    }

    static int matchingPairs(String s, String t) {
        // Write your code here
        // O(n) assuming no dupes
        if (s == null) return 0;
        if (s.equals(t)) return s.length()-2;

        Map<Character,Integer> map = new HashMap<>();
        int res = 0;
        for (int i=0; i<s.length(); i++) {   // O(n)
            if (s.charAt(i) == t.charAt(i)) res++;  // initial chars match
            else map.put(s.charAt(i), i);
        }

        if (map.size() == 1) return res - 1;
        int cnt = 0;
        for (int i=0; i<t.length(); i++) {  // O(n)
            char c = t.charAt(i);
            if (map.containsKey(c)) cnt++;
            // initial chars match
            if (map.containsKey(c) && s.charAt(i) == t.charAt(map.get(c))) cnt++;

            if (cnt  == 2) break; // done we can max do 2 extra matches by swapping
        }
        return res+cnt;
    }

    /**
     * Minimum Length Substrings
     * You are given two strings s and t. You can select any substring of string s and rearrange the characters of the
     * selected substring. Determine the minimum length of the substring of s such that string t is a substring of the
     * selected substring.
     *
     * Example:
     * s = "dcbefebce"
     * t = "fd"'
     * output = 5
     * Explanation:
     * Substring "dcbef" can be rearranged to "cfdeb", "cefdb", and so on. String t is a substring of "cfdeb".
     * Thus, the minimum length required is 5.
     */
    static int minLengthSubstring(String s, String t) {
        // Write your code here

        // create a set of chars of t
        int[] freq = new int[256];
        int cnt = 0;
        for (char c:t.toCharArray()) {
            freq[(int)c]++;
            cnt++;
        }

        // iter s until all chars in set are found
        int i=0;
        for (; i<s.length(); i++) {
            char c = s.charAt(i);
            if (freq[(int)c] > 0) {
                freq[(int)c]--;
                cnt--;
            }
            if (cnt == 0) break;
        }
        // return -1 if iter exhausted string
        return i==s.length() ? -1 : i+1;
    }

}
