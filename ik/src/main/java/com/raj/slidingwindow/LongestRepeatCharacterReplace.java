package com.raj.slidingwindow;

public class LongestRepeatCharacterReplace {
    /**
     * Given a string s that consists of only uppercase English letters, you can perform at most k operations on that string.
     * In one operation, you can choose any character of the string and change it to any other uppercase English character.
     * Find the length of the longest sub-string containing all repeating letters you can get after performing the above operations.
     *
     * Example 1:
     * Input:
     * s = "ABAB", k = 2
     * Output: 4
     * Explanation: Replace the two 'A's with two 'B's or vice versa.
     *
     * Example 2:
     * Input:
     * s = "AABABBA", k = 1
     * Output: 4
     * Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA". The substring "BBBB" has the longest repeating letters, which is 4.
     */
    public static void main(String[] args) {
        System.out.println(characterReplacement("AABABBA", 1));
    }

    /**
     * We will need to know how many letters in our substring that we need to replace.
     * Expand until lettersToReplace < k
     * To find out the lettersToReplace = (end - start + 1) - mostFreqLetter
     * Contract until lettersToReplace > k as window is invalid and we decrease the window size from the left.
     * O(n)
     */
    static int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int mostFreqLetter = 0;
        int left = 0;
        int max = 0;

        for(int right = 0; right < s.length(); right++) {
            freq[s.charAt(right) - 'A']++;
            mostFreqLetter = Math.max(mostFreqLetter, freq[s.charAt(right) - 'A']);
            int lettersToChange = (right - left + 1) - mostFreqLetter;
            if (lettersToChange > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
                /**
                 * NOTE:
                 * there is no need to decr mostFreqLetter as we don't know if the mostFreqLetter was due to left char that we deleted
                 * mostFreqLetter isn't actually the most frequent in window, but for the whole length.
                 * It may be invalid at some points, but this doesn't matter, because it was valid earlier in the string,
                 * and all that matters is finding the max window that occurred anywhere in the string.
                 * Additionally, it will expand if and only if enough repeating characters appear in the window to make
                 * it expand. So whenever it expands, it's a valid expansion.
                 */
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}
