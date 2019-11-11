package com.raj.strings;

import java.util.Stack;

import static com.raj.strings.RegExPatternMatch.TNode;

public class SuffixTrie {

    public static void main(String[] args) {
        longestRepeatedSubstr_dfs(null, new Stack<>(), new Result());
        mostRepeatedSubstr_dfs(null, new Stack<>(), 0, new Result());
    }

    /**
     * Suffix Trie aka "Suffix Tree" -  https://www.geeksforgeeks.org/pattern-searching-using-trie-suffixes/
     * Very widely used in substring processing problems.
     *
     * # How is Suffix Trie different from Trie?
     * A suffix tree can be viewed as a data structure built on top of a trie where, instead of just adding the string
     * itself into the trie, you would also add every possible suffix of that string.
     * Once that's done you can search for any n-gram and see if it is present in your indexed string. In other words,
     * the n-gram search is a prefix search of all possible suffixes of your string.
     *
     * # When to USE Suffix Trees?
     * Prefix Trie can only help when search is with start of string.
     * Suffix Tree being Trie of all Suffixes can help with doing lookups of substrings
     * Clue is "finding substring" or when you are looking for something in middle - Suffix Tree may be used.
     *
     * # Intuition
     * Remember every substring is a string is a Prefix of a Suffix:
     * Say, suffixes of ABC => ABC, BC, C ...
     * -> If we build a Trie of all the above suffixes, we can find all substrings:
     *   - A & AB (which is a prefix of suffix ABC)
     *   - B & BC (which is a prefix of BC)
     *   - C itself
     *
     * # Building a Trie of Suffixes:
     * 1) Generate all suffixes of given text.
     * 2) Consider all suffixes as individual words and build a trie.
     * Let us consider an example text “banana$”. Following are all suffixes of “banana”
     * banana$
     * anana$
     * nana$
     * ana$
     * na$
     * a$
     * $
     * Note: Pre-processing of text may become costly if the text changes frequently. It is good for fixed text or less frequently changing text though.
     * 3) Now Search the pattern in above suffix tree - Runtime O(m) w/ building tree cost as O(n^2) & O(n^2) aux space
     * 4) Optimize Suffix Tree by building "Compact Trie" of Suffixes:
     *                  root  - $
     *             /     |     \
     *       banana$     a-$   na-$
     *                   |      |
     *                   na-$   na$
     *                   |
     *                   na$
     *    -> DS is Map<String,TNode> instead of just Character key. While inserting just break the node into 2, if substr match happens.
     *    -> The runtime & space reduces(store indices) to O(n) using Ukkonen's Algo: https://stackoverflow.com/questions/7451942/time-complexity-of-building-a-suffix-tree
     *
     * 5) Optimization - SUFFIX ARRAY is a sorted array of all suffixes of a string. It is a data structure used, among others, in full text indices, data compression algorithms and within the field of bibliometrics.
     * 6) For internet scale, build it in parallel that can reduce complexity to less than O(n)
     *
     * https://www.geeksforgeeks.org/pattern-searching-using-trie-suffixes/
     * https://en.wikipedia.org/wiki/Suffix_tree
     *
     * ==== Suffix Tree applications (VERY IMPORTANT) ====
     *
     * -> SubString search
     *   - O(m) complexity, where m is the length of the sub-string (but with initial O(n) time required to build the suffix tree for the string)
     *   - Remember it's more powerful than Prefix Tree as it allows you to search any substr while Prefix allows only with starting char
     *   - Even Prefixes can be searched as well.
     *
     * -> Finding the longest repeated substring
     *   - Find the deepest internal node in the tree, where depth is measured by the number of characters traversed from the root.
     *   - The string spelled by the edges from the root to such a node is a longest repeated substring.
     *   - Simple DFS w/ some modification to track size of strSoFar length when number of childs is greater than 1 (which implies repetition)
     *   - https://en.wikipedia.org/wiki/Longest_repeated_substring_problem
     *
     * -> Finding the most repeated substring
     *   - A variant of above, where we just track the DFS call that returns str w/ at least length 2 & has maximum number of '$'
     *   - Another variant could be to return most repeated substring with length greater than k. Then we just modify length criteria in above.
     *
     * -> Finding the longest common substring
     *   - Same as longest repeated substring, just that u got 2 strings as argument
     *   - Build a generalized Suffix Tree w/ all given strings
     *   - Find the deepest internal nodes which have leaf nodes from all the strings in the subtree below it.
     *   - A node that gets both '$' & '#' from dfs & has longest length is the answer
     *   - Can be used for plagiarism detection - https://en.wikipedia.org/wiki/Longest_common_substring_problem
     *
     * -> Finding the longest palindrome in a string
     *   - Similar to above, build a Suffix Tree using the string & it's reverse. Now we need to just find LCSubstring.
     *   - Apply the same logic to find the deepest internal node that has both strings leaves ending in '$' & '#'.
     *   - Edge case - what if the reverse str also exists in the string. Make sure the LCS is at the same index.
     *   - https://stackoverflow.com/questions/7043778/longest-palindrome-in-a-string-using-suffix-tree
     *   - Another solve is "expand around center" - com.raj.strings.LongestPalinSubstr
     *
     *
     * # Conclusion - Trie vs Suffix Tree vs Suffix Array - https://stackoverflow.com/questions/2487576/trie-vs-suffix-tree-vs-suffix-array
     * The trie was the first data structure of this kind discovered.
     * The suffix tree is an improvement over the trie (it has suffix links which allow linear error search, the suffix tree trims unnecessary branches of the trie therefore it does not require as much space).
     * The suffix array is a stripped down data structure based on the suffix tree (no suffix links (slow error matches), yet pattern matching is very fast).
     * The trie is not for real world use because it consumes too much space.
     * The suffix tree is lighter and faster than the trie and is used to index DNA or optimize some large web search engines.
     * The suffix array is slower in some pattern searches than the suffix tree but uses less space, and is more widely used than the Suffix tree.
     */

    /**
     * Runtime = O(n^2), as n suffixes exists with varying lengths (n + n-1 + n-2 ...) so total of n^2 nodes exists.
     * And we traverse all nodes during dfs.
     * Aux Space = O(n^2) to build Suffix Tree
     */
    static void longestRepeatedSubstr_dfs(TNode n, Stack<Character> soFar, Result res) {
        if (n.ch == '$') return; // hit end

        // Is the new substr of interest? Is it repeated & longest so far?
        if (n.childs.size() > 1 && soFar.size() > res.len) {
            res.len = soFar.size();     // update res
            res.str = soFar;
        }

        // now do dfs
        for (TNode w : n.childs.values()) {
            soFar.add(w.ch);
            longestRepeatedSubstr_dfs(w, soFar, res);
            soFar.pop();
        }

    }

    static void mostRepeatedSubstr_dfs(TNode n, Stack<Character> soFar, int count$, Result res) {
        if (n.ch == '$') { // alternatively u cud return 1 for each $ found and let the backtracking take care of adding the counts & finding max
            if (count$ > res.count) {   // Is soFar the most repeated ?
                res.count = count$;
                res.str = soFar;
            }
            return;
        }

        // now do dfs
        for (TNode w : n.childs.values()) {
            soFar.add(w.ch);
            mostRepeatedSubstr_dfs(w, soFar, count$ + 1, res);
            soFar.pop();
        }

    }

    private static class Result {
        Stack<Character> str = new Stack<>();
        int len = 0;
        int count = 0;
    }

}
