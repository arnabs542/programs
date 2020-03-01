package com.raj.strings;

import com.raj.Util;

import java.util.*;
import java.util.stream.Collectors;

import static com.raj.Util.isPalin;
import static com.raj.Util.reverseStr;
import static com.raj.strings.RegExPatternMatch.*;

public class PalindromePairs {

    /**
     * Given words = {dog, cat, racee, ma, dam, car, god} find palindrome pairs
     * Result = {doggod, raceecar, madam, goddog}
     */
    public static void main(String[] args) {
        System.out.println(findPalinPairs_Trie(Arrays.asList("b", "dog", "cat", "racee", "ma", "dam", "car", "god")));
        System.out.println(Arrays.toString(findPalinPairs_hashmap(new String[]{"b", "dog", "cat", "racee", "ma", "dam", "car", "god"})));
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
    static List<String> findPalinPairs_Trie(List<String> words) {   // to do handle 'b' in input
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
            if (matchedIdx > 0) {  // matched index is until it matched a waord in trie
                if (Util.isPalin(w.substring(matchedIdx))) {    // check if remaining part of word is palin
                    res.add(w + reverseStr(w.substring(0, matchedIdx))); // add the palin pair
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


    private static boolean isPalindrome(String word) {
        int start = 0;
        int end = word.length() - 1;
        while (start < end) {
            if (word.charAt(start) != word.charAt(end)) return false;
            start++;
            end--;
        }
        System.out.println(word + " ispalin");
        return true;
    }

    /**
     * [HashMap approach]
     * We are doing full text matches in Trie above. We just need a constant time lookup which can also be done via hashmap
     * # "dog", "cat", "racee", "ma", "dam", "car", "god" --> put them in set
     * # Iterate thru each word in set, reverse it and go left to right one char at a time creating a prefix | suffix split for word
     * # Check if prefix exists in set & rest aka suffix is palin.
     * # Check if prefix is palin & rest aka suffix is in set.
     * # Both of the above yields a palin pair.
     * racee -> ee|car  (prefix ee is palin & car exists in set) => raceecar ie. racee (original str) + car (corresponding pair in set) is one pair
     * dog -> god| => doggod
     * dam -> ma|d => ma exists & d is palin => dam + ma
     * a, levela
     * levela -> a | level
     * Runtime = O(n * 3L)
     */
    static String[] findPalinPairs_hashmap(String[] words) {
        Set<String> res = new HashSet<>();
        Map<String,Integer> map = new HashMap<>();
        Map<String,Integer> freq = new HashMap<>();
        int i = 0;
        for (String w : words) {
            map.put(w, i++);
            if (!freq.containsKey(w)) freq.put(w, 0);   // maintain freq to handle cases like aa,aa in words
            freq.put(w, freq.get(w)+1);
        }

        for (String s : map.keySet()) {  // n cost
            String revStr = Util.reverseStr(s); // L cost
            String prefix = "", suffix;
            for (i = 0; i < revStr.length(); i++) {  // n * 3L cost ?
                prefix += revStr.charAt(i);
                suffix = revStr.substring(i+1);   // L cost

                if (isPalin(prefix) && map.containsKey(suffix)) {
                    // edge case: handle single 'b' in input
                    // edge case: 'b', 'b' occurs 2 times, then it's ok to print as palin pairs
                    if (s.equals(suffix) && freq.get(s) == 1) continue;
                    res.add(s + suffix);   // ee | car   ... L cost
                }
                if (map.containsKey(prefix) && isPalin(suffix)) {
                    if (s.equals(prefix) && freq.get(s) == 1) continue;
                    res.add(s + prefix);   // ma | d     ... L cost
                }
            }
        }
        return res.toArray(new String[res.size()]);
    }

    /**
     * Mock Interview - given unique words in dict find palin pairs indices in dict.
     * Similar to above except no need to track freq as its unique. Used a hashmap(word->index) to track indices & split word into prefix | suffix.
     * Each time checking if prefix is palin & suffix exists in map & vice versa. Add them to res.
     * Edge case - What if word itself is palin & no "" empty word is given, then we shudn't print it by adding:
     * word index i != map.get(suffix) & vice versa
     */

}
