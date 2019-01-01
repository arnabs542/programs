package com.raj.hashing;

import java.util.*;

/**
 * @author rshekh1
 */
public class SubstringConcat {
    /**
     * You are given a string, S, and a list of words, L, that are all of the same length.
     * Find all starting indices of substring(s) in S that is a concatenation of each word in L exactly once and without any intervening characters.
     *
     * S: "barfoothefoobarman"
     * L: ["foo", "bar"]
     * You should return the indices: [0,9]  (order does not matter).
     */
    public static void main(String[] args) {
        System.out.println(findSubstring("barfoothefoobarman", new String[]{"foo","bar"}));
    }

    /**
     * Let length of word in L be l.
     * Create Map of words -> visited in L for lookup
     * Get substring of length from left (i) and see if it exist in set. If yes, mark as visited.
     * If the next l chars don't match with a word in L, break. Reset Map to false.
     * If all are visited, add start i to the result array
     */
    private static int[] findSubstring(String S, String[] L) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < S.length(); i++) {
            int idx = isMatch(S, L, i);
            if (idx > i) {
                System.out.println(i);
                res.add(i);
            }
        }
        int[] resArr = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            resArr[i] = res.get(i);
        }
        return resArr;
    }

    private static int isMatch(String S, String[] L, int i) {
        Set<String> set = new HashSet<>();
        Arrays.stream(L).forEach(x -> set.add(x));
        int l = L[0].length();
        int j = 0;
        while (i+j+l < S.length()) {
            int startIdx = i+j;
            String sub = S.substring(startIdx, startIdx+l);
            if (set.contains(sub)) {
                set.remove(sub);
                j += l;
            } else return i;
            if (set.isEmpty()) {
                return startIdx+l;
            }
        }
        return i;
    }

}
