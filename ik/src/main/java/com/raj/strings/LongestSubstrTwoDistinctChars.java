package com.raj.strings;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstrTwoDistinctChars {

    public static void main(String[] args) {
        /*System.out.println(getLongestSubstringLengthExactly2DistinctChars("baabcbab"));
        System.out.println(getLongestSubstringLengthExactly2DistinctChars("aaa"));
        System.out.println(getLongestSubstringLengthExactly2DistinctChars("ab"));
        System.out.println(getLongestSubstringLengthExactly2DistinctChars("aaaabbbccccc"));*/

        System.out.println(getLongestSubstringLengthExactlyKDistinctChars("ab", 2));
        System.out.println(getLongestSubstringLengthExactlyKDistinctChars("aaa", 2));
        System.out.println(getLongestSubstringLengthExactlyKDistinctChars("abcdef", 2));
        System.out.println(getLongestSubstringLengthExactlyKDistinctChars("eceba", 2));
        System.out.println(getLongestSubstringLengthExactlyKDistinctChars("baabcbab", 2));
        System.out.println(getLongestSubstringLengthExactlyKDistinctChars("aaaabbbccccc", 2));
    }

    /**
     * WRONG SOLUTION
     * Using Map frequency to track length of str & 1 ptr
     * # iter i -> 0 to len
     * - is3rdChar(map, s[i]) { reset map by keeping only prev char }
     * - updateFreq(map, s[i])
     * - maxLen = getMaxLen(map)
     *
     * WON'T WRK for Test case like 'baabcbab' as b's state is carried over and will mess up stae
     * If we forcefully set b's count as 1 after 3 distinct chars, then it fails for 'aaaabbbbcccc' as b's count is more
     */
    static void getLongestSubstringLengthExactly2DistinctChars(String s) {
        /*if (s == null || s.length() < 2) return 0;

        int maxLen = 0;
        Map<Character, Integer> countMap = new HashMap<>();  // tracks char & it's freq, thereby giving the length when size exceeds 2

        for (int i = 0; i < s.length(); i++) {

            // do we see a 3rd distinct char? reset counts & remove unwanted chars
            if (countMap.size() == 2 && !countMap.containsKey(s.charAt(i))) {
                final int _i = i;
                countMap.entrySet().removeIf(x -> x.getKey() != s.charAt(_i - 1));   // remove everything but prev in map
                countMap.put(s.charAt(i-1), 1);
            }

            // add to map & their counts
            if (countMap.containsKey(s.charAt(i))) countMap.put(s.charAt(i), countMap.get(s.charAt(i)) + 1);
            else countMap.put(s.charAt(i), 1);

            // update length at last
            if (countMap.size() == 2) {
                int len = countMap.values().stream().reduce(0, Integer::sum);
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;*/
    }

    /**
     * Using Sliding Window 2 pointer expand & contract & map of char frequencies
     * # Init 2 ptrs - left & right at start
     * # Expand window - incr right until we see a 3rd distinct char. Keep updating freq & maxLen
     * # Contract window - decr left until map has 2 chars, keep updating freq, when 0, remove from map
     *
     * !! Got Stuck big time due to ptrs being ahead after loop (fails when ptr reaches boundary or when has 2 distinct chars etc)!!
     * VERY IMPORTANT - ALWAYS INCR WHILE LOOP PTRS BEFORE USING IT, OTHERWISE YOU'LL GET INTO EXTREMELY COMPLEX DEBUGGING MESS
     *
     * Runtime = O(n+n) for expand & contract respectively w/ O(k) aux space for k distinct chars
     * Better than prev anyways as we at max do 2 iters in sliding window algos
     */
    static int getLongestSubstringLengthExactlyKDistinctChars(String s, int k) {
        if (s == null || s.length() < 2) return 0;
        int left = -1, right = -1;
        int maxLen = 0;
        Map<Character, Integer> countMap = new HashMap<>();  // tracks char & it's freq, thereby giving the length when size exceeds 2
        while (right < s.length()) {

            // expand till map has 3 distinct chars
            while (countMap.size() <= k && ++right < s.length()) {  // always incr ptr before using it
                if (countMap.containsKey(s.charAt(right))) countMap.put(s.charAt(right), countMap.get(s.charAt(right))+1);
                else countMap.put(s.charAt(right), 1);
            }
            if (countMap.size() >= 2) maxLen = Math.max(maxLen, right-left-1);

            // contract till map becomes 2 chars
            while (countMap.size() > k) {
                if (countMap.containsKey(s.charAt(++left))) {
                    countMap.put(s.charAt(left), countMap.get(s.charAt(left))-1);
                    if (countMap.get(s.charAt(left)) == 0) countMap.remove(s.charAt(left));
                }
            }
        }
        return maxLen;
    }

}
