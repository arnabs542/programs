package com.raj.strings;

import com.raj.Util;

import java.util.*;
import java.util.stream.Collectors;

import static com.raj.Util.reverseStr;
import static com.raj.strings.RegExPatternMatch.*;

public class PalindromePairs {

    /**
     * Given words = {dog, cat, racee, ma, dam, car, god} find palindrome pairs
     * Result = {doggod, raceecar, madam, goddog}
     */
    public static void main(String[] args) {
        System.out.println(findPalinPairs(Arrays.asList("dog", "cat", "racee", "ma", "dam", "car", "god")));
    }

    /**
     * BruteForce: O(n^2) * L, where L = max word length.
     *  => 2 loops. Fix a word, combine it with other string in list, check each combined string is palindrome or not
     *
     * Trie?
     *               --- root ---
     *            d     c   r  m  g
     *           o a    a   a  a   o
     *          g   m  r t  c       d
     *                      e
     *                      e
     * Algo -
     * # Build a prefix Trie
     * # Reverse each string & check in trie
     *   => for 'dog', 'god' exists, hence the string is 'goddog'. Similarly, 'doggod'
     *   => for 'dam', reverse is 'mad' but smaller string 'ma' exists.
     *      -> Special case - Also check if remaining chars after prefix match is also a palin. Result is 'ma'+'dam'
     *   => for 'car', reverse is 'rac' but bigger string 'racee' exists
     *      -> Applying logic of checking palin after prefix can become exponential as many strs could exist with this prefix.
     *      -> To avoid this, build a reverse Trie. And check in regular order in reverse trie.
     *
     *              --- root ---
     *           g  t e a   d  r
     *          o  a  e m  a o  a
     *         d  c   c   m   g  c  --> 'rac' from 'racee' matches, now just check if 'ee' is also palin. Hence, "raceecar" is found
     *                a
     *                r
     *      -> Some dupes like "doggod" & "goddog" will be found. Use Set to avoid dupes.
     * Complexity - Runtime is O(n * L) at the the cost O(n * L) of building Trie, where n = number of words
     */
    static List<String> findPalinPairs(List<String> words) {
        Set<String> res = new HashSet<>();
        List<String> reverseWords = words.stream().map(x -> reverseStr(x)).collect(Collectors.toList());

        // search reverse word in regular Trie
        Trie regularTrie = buildTrie(words);
        searchTrie(reverseWords, regularTrie, res);

        // search regular word in reverse Trie
        Trie reverseTrie = buildTrie(reverseWords);
        searchTrie(words, reverseTrie, res);

        return new ArrayList<>(res);
    }

    static void searchTrie(List<String> words, Trie trie, Set<String> res) {
        for (String w : words) {
            int matchedIdx = isPrefixWordInTrie(trie.root, w);
            if (matchedIdx > 0) {  // partial or full match
                if (Util.isPalin(w.substring(matchedIdx))) {
                    res.add(w + reverseStr(w.substring(0, matchedIdx)));
                }
            }
        }
    }

    // returns the index until it matches in trie
    static int isPrefixWordInTrie(TNode node, String word) {
        int i;
        for (i = 0; i < word.length(); i++) {
            if (!node.childs.containsKey(word.charAt(i))) {
                if (node.childs.containsKey('$')) return i; // only return if prefix is word in trie
                else return 0;
            }
            node = node.childs.get(word.charAt(i));
        }
        return node.childs.containsKey('$') ? i : 0;
    }

}
