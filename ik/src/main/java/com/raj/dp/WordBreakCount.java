package com.raj.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rshekh1
 */
public class WordBreakCount {

    /**
     * You have to split the string txt using ‘ ‘(single white space delimiter) in such a way that
     * every segment (word) exists in our dictionary.
     *
     * Example 1: dict = [cat, cats, and, sand, dog]
     * f(catsanddogs) => 2 (cat sand dog, cats and dog)
     *
     * Example 2: dict = [kick, start, kickstart, is, awe, some, awesome]
     * f(kickstartisawesome) => 4 (kick start is awe some, kick start is awesome, kickstart is awe some, kickstart is awesome)
     *
     */
    public static void main(String[] args) {
        System.out.println(rec(new HashSet<>(Arrays.asList("cat", "cats", "and", "sand", "dog", "dogs")),
                "catsanddogs", 0));

        System.out.println(rec(new HashSet<>(Arrays.asList("kick", "start", "kickstart", "is", "awe", "some", "awesome")),
                "kickstartisawesome", 0));

        System.out.println(dp(new HashSet<>(Arrays.asList("cat", "cats", "and", "sand", "dog", "dogs")),
                "catsanddogs"));

        System.out.println(dp(new HashSet<>(Arrays.asList("kick", "start", "kickstart", "is", "awe", "some", "awesome")),
                "kickstartisawesome"));
    }

    /**
     *                          f(catsanddog)
     *                      /        |
     *  c...ca.. cat,f(sanddog)    cats,f(anddog) catsa..catsan...
     *                   /                  |
     *  s..sa..san.. cat,sand,f(dog)  a..an..and.. cats,and,f(dog)    ---> overlapping sub-problems (can be memoized)
     *
     *  Recurrence => f(text) = 1(valid dict word text[i,j]) + f(remaining)
     *
     * Time complexity:
     * Exponential, to be precise O(2^(n-2)) : https://stackoverflow.com/questions/31370674/time-complexity-of-the-word-break-recursive-solution
     * In each call you are calling the recursive function with length 1,2....n-1(in worst case).
     * To do the work of length n you are recursively doing the work of all the strings of length n-1, n-2, ..... 1.
     * So T(n) is the time complexity of your current call, you are internally doing a work of sum of T(n-1),T(n-2)....T(1)
     */
    static int rec(Set<String> dict, String text, int i) {
        if (i == text.length()) return 1; // reached end, we found 1 combination

        String soFar = "";
        int count = 0;
        for (int j = i; j < text.length(); j++) {
            soFar += text.charAt(j);
            if (dict.contains(soFar)) {             // found a valid word
                count += rec(dict, text, j+1);    // recurse on remaining
            }
        }
        return count; // return count
    }

    static int dp(Set<String> dict, String text) {
        int[] dpTable = new int[text.length()+1];
        //Arrays.fill(dpTable, -1);
        return dp(dict, dpTable, text, 0);
    }

    /**
     * Exactly same solution as recursive above, except that we memoized the solutions to sub-problems top-down
     * Time complexity is O(n*s) where s is the length of the largest string in the dictionary and n is the length of the given string.
     * Space = O(n)
     * May not be correct as per IK, its O(n^3) as we require n for each substring lookup from dict (a substring's hashcode has to be prepared for hashset lookup which is n)
     * dp(n states) x num of substrings x n dict lookup = O(n^3)
     *
     * O(n^2) with Trie DP - https://oj.interviewkickstart.com/view_top_submission/5747/211/69735/ (it will not proceed if a char doesn't match, hence avoiding all substr matches)
     */
    static int dp(Set<String> dict, int[] dpTable, String text, int i) {
        if (i == text.length()) return 1; // reached end, we found 1 combination

        if (dpTable[i] != 0) return dpTable[i]; // sub-problem was memoized before, use it

        String soFar = "";
        int count = 0;
        for (int j = i; j < text.length(); j++) {
            soFar += text.charAt(j);
            if (dict.contains(soFar)) {             // found a valid word
                count += dp(dict, dpTable, text, j+1);    // recurse on remaining
            }
        }
        dpTable[i] = count;
        System.out.println(Arrays.toString(dpTable));

        return dpTable[i];
    }

}
