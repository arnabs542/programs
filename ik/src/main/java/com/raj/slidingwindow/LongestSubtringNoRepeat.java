package com.raj.slidingwindow;

import java.util.HashMap;
import java.util.Map;

public class LongestSubtringNoRepeat {
    /**
     * Given a string, find the length of the longest substring without repeating characters.
     *
     * Example 1:
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     *
     * Example 2:
     * Input: "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     *
     * Example 3:
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     */
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pwwkew"));
        System.out.println(lengthOfLongestSubstring_optimal("pwwkew"));
    }

    /**
     * [Expand/Contract Sliding Window pattern]
     * Expand until no dupes
     * Contract until the duped char
     * Keep updating max length
     * Time/Space = O(n + n) for expand+contract phases
     *
     * Note: always assert always proactively assert i,j < length before using it to avoid OOB
     */
    static int lengthOfLongestSubstring(String s) {
        //dry run: pwwkew => wke
        int max = 0;
        boolean[] visited = new boolean[256];
        int i=-1, j=-1;
        char[] A = s.toCharArray();
        while (j<A.length) {   //0

            // expand until no dupes
            while (++j<A.length && !visited[A[j]]) { // j=2, w exists!!
                visited[A[j]] = true; //v=pw
            }
            max = Math.max(max, j-1-i);  // 2-1+1 = 2

            // contract until dupe char boundary - always proactively assert i,j < length to avoid OOB
            while (j<A.length && ++i<A.length && A[i] != A[j]) {   // i=1  , A[j]=w
                visited[A[i]] = false; // mark unvisited
            }
        }
        max = Math.max(max, j-1-i);
        return max;
    }

    /**
     * Optimization: The contract phase will scan n additional, can we update ith ptr as soon as we see a dupe?
     * ..(seen before)...cur_ptr
     * Keep a hashmap which stores the characters in string as keys and their positions as values, and keep two pointers
     * which define the max substring. move the right pointer to scan through the string, and meanwhile update the hashmap.
     * If the character is already in the hashmap, then move the left pointer to the right of the same character last found.
     * Note that the two pointers can only move forward.
     * Time/Space = O(n)
     */
    static int lengthOfLongestSubstring_optimal(String s) {
        if (s.length() == 0) return 0;
        Map<Character,Integer> map = new HashMap<>();
        int max = 0;
        for (int i=0, j=0; j<s.length(); ++j) { // ...i...j
            if (map.containsKey(s.charAt(j))) { // found dupe, reset i bounds to prev dupe seen char idx + 1
                i = Math.max(j, map.get(s.charAt(j)) + 1);
            }
            map.put(s.charAt(j), j); // add to seen w/ latest idx
            max = Math.max(max, j-i+1);
        }
        return max;
    }

}
