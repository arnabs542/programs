package com.raj.arrays;

public class MatchingSubsequence {
    /**
     * Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.
     *
     * Input:
     * S = "abcde"
     * words = ["a", "bb", "acd", "ace"]
     * Output: 3
     * Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".
     */
    public static void main(String[] args) {
        System.out.println(numMatchingSubsequence("abcde", new String[]{"a","bb","acd","ace"}));
    }

    /**
     * Optimal solution will involve going through the chars in S once and for each char in S compare chars in words.
     * If they match move forward. If it hits end of word, we have found a match.
     * How do we keep state of each word's ptr?
     * # Use a map or 1D array to store word list index -> char idx ptr
     *
     * Time = O(S_len * words_len * a_words_max_len)
     */
    static int numMatchingSubsequence(String S, String[] words) {
        // init map with ptr at 0 for each word
        int[] wordPtr = new int[words.length];

        int res = 0;
        // iter S and move ptr
        for (char c : S.toCharArray()) {
            for (int i = 0; i < wordPtr.length; i++) {
                if (wordPtr[i] >= words[i].length()) continue;
                char w = words[i].charAt(wordPtr[i]);
                if (c == w) wordPtr[i]++;  // word ptr changed, verify if goal state reached
                if (wordPtr[i] == words[i].length()) res++;
            }
        }
        return res;
    }
}
