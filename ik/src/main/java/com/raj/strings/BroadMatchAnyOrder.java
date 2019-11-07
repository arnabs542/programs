package com.raj.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BroadMatchAnyOrder {

    /**
     * Write a function that takes an input string and a character set and returns the minimum-length substring which
     * contains every letter of the character set at least once, in any order.
     * If you don't find a match, return an empty string
     *
     * Input => helloworld , {l,r,w}
     * o/p   =>      worl
     *
     * Input => "ababbcca", {a,b,c}
     * o/p   =>   "abbc"  .... contains all chars in any order
     *
     * Input => "cccbbaba", {a,b,c}
     * o/p   =>   "cbba"
     *
     * Input => "accbbababac", {a,b,c}
     * o/p   =>         "bac"
     */
    public static void main(String[] args) {
        System.out.println(broadMatch_brute("helloworld", new char[]{'l','r','w'}));
        System.out.println(broadMatch_index_track("helloworld", new char[]{'l','r','w'}));
        System.out.println(broadMatch_expand_contract("helloworld", new char[]{'l','r','w'}));

        System.out.println(broadMatch_expand_contract("ababbcca", new char[]{'a','b','c'}));
        System.out.println(broadMatch_expand_contract("cccbbaba", new char[]{'a','b','c'}));
        System.out.println(broadMatch_expand_contract("accbbababac", new char[]{'a','b','c'}));
    }

    /**
     * Algo: Simple Sliding Window
     * for substring i=0,1...n-1 until length, look for charset in any order using a hashset
     * Runtime = O(n^2 * m), where n = length of string, m = length of substring (m can be ignored as it can be max 26)
     */
    static String broadMatch_brute(String s, char[] charSet) {
        Set<Character> set = new HashSet<>();
        String minSubstr = "";
        int cmp = 0;
        for (int i = 0; i < s.length(); i++) {  // O(n)
            set.clear(); for (char c : charSet) set.add(c);
            for (int j = i; j < s.length(); j++) {  // O(n)
                if (set.contains(s.charAt(j))) set.remove(s.charAt(j));

                // debug substrs
                System.out.print(s.substring(i, j+1) + " "); cmp += j+1 - i; // worst case

                if (set.isEmpty()) {
                    String newSubstr = s.substring(i, j+1); // O(n)
                    if (minSubstr.isEmpty()) minSubstr = newSubstr;
                    else if (newSubstr.length() < minSubstr.length()) minSubstr = newSubstr;
                    break;
                }
            }
        }
        System.out.println("Num comparision = " + cmp);
        return minSubstr;
    }

    /**
     * Algo: Sliding Window - Index Tracking Method
     * Idea is to loop through chars once. Can we save some state as we progress? And keep updating min length?
     *
     * 0 1 2 3 4 5 6
     * b b a a c b a
     *
     * char set state info after each iteration
     * a   = x 2 3
     * b   = x 0 1
     * c   = x 4
     * len = (4-1)+1 = 4 ... min so far when we traverse until b b a a c
     *
     * a   = x 2 3 6
     * b   = x 0 1 5
     * c   = x 4
     * len = (6-4)+1 = 3  ... min & our final answer
     *
     * Runtime = O(nm) => O(n), m can be max 26 hence constant
     * How abt using a bst or treemap for this? O(n mlogm)
     */
    static String broadMatch_index_track(String s, char[] charSet) {
        // map to track last seen indices
        Map<Character,Integer> map = new HashMap<>();
        for (char c : charSet) map.put(c, null); // init as null

        String minStr = ""; int minLen = Integer.MAX_VALUE; int countNulls = map.size();

        for (int i = 0; i < s.length(); i++) {

            if (map.containsKey(s.charAt(i))) {

                if (map.get(s.charAt(i)) == null) { // need to track when all index were set
                    countNulls--;
                }
                map.put(s.charAt(i), i);  // update last seen for this char

                // update ptrs
                if (countNulls == 0) {  // all indices were set at least once, now we can start computing min length
                    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
                    for (int idx : map.values()) { // min-max finding can at max be 26, hence can be considered O(1)
                        min = Math.min(min, idx);
                        max = Math.max(max, idx);
                    }
                    int newLen = max - min + 1;
                    if (newLen < minLen) {  // is the new length smaller?
                        minLen = newLen;
                        minStr = s.substring(min, max+1);
                    }
                }
            }

        }
        return minStr;
    }

    /**
     * Algo: Sliding Window - Expand & then Contract
     *
     * Input =>  h e l l o w o r l d , set = {l,r,w}
     * Expand    . . l . . w . r  -> until all zeros exhausted, which means this is a probable substring, but may have leading chars which can be trimmed
     * Zeros=3       2     1   0     (decrement ptr when 0 becomes non-zero)
     * Map (l=2,r=1,w=1)
     *
     *           h e l l o w o r l d
     * Contract  . . . l
     * Zeros=0         1 (l becomes 2->0 in this contraction, hence stop)
     * Substring =>    l o w o r
     *
     * Zeros = 1 now
     * Expand again until Zeros=0 which is l....l
     * Contract again until Zeros>0 which is .w..l  (this will be our shortest substring)
     *
     * Runtime = O(n) expansion + O(n) contraction
     */
    static String broadMatch_expand_contract(String s, char[] charSet) {
        Map<Character,Integer> map = new HashMap<>();
        for (char c : charSet) map.put(c, 0);
        int zerosCount = charSet.length;

        int l = -1, r = -1, minLen = Integer.MAX_VALUE, minL = -1, minR = -1;

        while(r < s.length()) {

            // expand until zerosCount = 0
            while (zerosCount > 0 && ++r < s.length()) {
                if (map.containsKey(s.charAt(r))) {
                    if (map.get(s.charAt(r)) == 0) zerosCount--;
                    map.put(s.charAt(r), map.get(s.charAt(r)) + 1);
                }
            }

            // contract until zerosCount > 0
            while (zerosCount == 0 && ++l < s.length()) {
                if (map.containsKey(s.charAt(l))) {
                    if (map.get(s.charAt(l)) == 1) zerosCount++;
                    map.put(s.charAt(l), map.get(s.charAt(l)) - 1);
                }
            }
            int newLen = r-l;
            if (newLen < minLen) {
                minL = l; minR = r;
                minLen = newLen;
            }
        }

        return s.substring(minL, minR+1);
    }

}