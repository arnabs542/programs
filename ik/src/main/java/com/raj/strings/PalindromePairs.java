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
     * BruteForce: O(n^2). 2 loops. Fix a word, combine it with other string in list, check each combined string is palindrome or not
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
     * Complexity - Runtime is O(n) at the the cost O(n) of building Trie, where n = number of words
     */
    static List<String> findPalinPairs(List<String> words) {
        Set<String> res = new HashSet<>();
        List<String> reverseWords = words.stream().map(x -> reverseStr(x)).collect(Collectors.toList());

        // search reverse word in regular Trie
        Trie trie = buildTrie(words);
        for (String w : reverseWords) {
            int idx = isPrefixTrie(trie.root, w);
            if (idx > 0) {  // partial or full match
                if (idx == w.length()) {
                    res.add(w.substring(0, idx) + reverseStr(w));  // FIX ME, doesn't yield correct results
                }
                else {
                    if (Util.isPalin(w.substring(idx))) {
                        res.add(w.substring(0, idx) + reverseStr(w));
                    }
                }
            }
        }

        // search regular word in reverse Trie
        trie = buildTrie(reverseWords);
        for (String w : words) {
            int idx = isPrefixTrie(trie.root, w);
            if (idx > 0) {  // partial or full match
                if (idx == w.length()) res.add(w.substring(0, idx) + w);
                else {
                    if (Util.isPalin(w.substring(idx))) res.add(w.substring(0, idx) + w);
                }
            }
        }

        return new ArrayList<>(res);
    }

    // returns the index until it matches in trie
    static int isPrefixTrie(TNode node, String word) {
        int i = 0;
        for (i = 0; i < word.length(); i++) {
            if (!node.childs.containsKey(word.charAt(i))) return i;
            node = node.childs.get(word.charAt(i));
        }
        return i;
    }

}
