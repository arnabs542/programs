package com.raj.strings;

public class SuffixTrie {

    /**
     * Suffix Trie -  https://www.geeksforgeeks.org/pattern-searching-using-trie-suffixes/
     *
     * Building a Trie of Suffixes
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
     * 3) Now Search the pattern in above suffix tree - O(m) w/ building tree as O(n) cost & O(n^2) aux space
     * 4) Optimize Suffix Tree by building "Compressed Trie" of Suffixes:
     *                  root  - $
     *             /     |     \
     *       banana$     a-$   na-$
     *                   |      |
     *                   na-$   na$
     *                   |
     *                   na$
     * 5) For internet scale, build it in parallel that can reduce complexity to less than O(n)
     *
     * https://www.geeksforgeeks.org/pattern-searching-using-trie-suffixes/
     * https://en.wikipedia.org/wiki/Suffix_tree
     *
     * Suffix Tree applications:
     * - String search, in O(m) complexity, where m is the length of the sub-string (but with initial O(n) time required to build the suffix tree for the string)
     * - Finding the longest repeated substring
     * - Finding the longest common substring
     * - Finding the longest palindrome in a string
     *
     *
     */
}
