package com.raj.hashing;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rshekh1
 */
public class WindowString {

    /**
     * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in linear time complexity.
     * Note that when the count of a character C in T is N, then the count of C in minimum window in S should be at least N.
     *
     * Example :
     * S = "ADOBECODEBANC"
     * T = "ABC"
     * Minimum window is "BANC"
     */
    public static void main(String[] args) {
        System.out.println(findMinWindow("ADOBECODEBANC", "ABC"));
        System.out.println(findMinWindow("AAAAAAAAAAA", "AAA"));
    }

    static String findMinWindow(String S, String T) {
        String minWindow = "";
        for (int i = 0; i < S.length(); i++) {
            // reset for comparison
            Map<String,Integer> map = new HashMap<>();
            for (char c : T.toCharArray()) {
                if (!map.containsKey(c+"")) map.put(c+"", 0);
                map.put(c+"", map.get(c+"")+1);
            }
            String thisWindow = "";
            for (int j = i; j < S.length(); j++) {
                String ch = S.charAt(j) + "";
                thisWindow += ch;
                if (map.containsKey(ch)) {
                    map.put(ch, map.get(ch)-1);
                    if (map.get(ch) == 0) map.remove(ch);
                }
                if (map.isEmpty() && (minWindow == "" || thisWindow.length() < minWindow.length()))
                    minWindow = thisWindow;
            }
        }
        return minWindow;
    }

}
