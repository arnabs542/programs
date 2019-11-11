package com.raj.strings;

import java.util.*;

public class RegExPatternMatch {

    /**
     * Find all words that match a pattern
     */
    public static void main(String[] args) {
        List<String> dict = Arrays.asList(new String[] {"cat","cats","car","cop","cart","dog"});

        List<String> res = new ArrayList<>();
        matchPattern(dict, "c*t", res);
        System.out.println(res);

        System.out.println(isMatch_recur("cats", "c*t*", 0,0));
        System.out.println(isMatch_brute(0,0,"cats".toCharArray(), "c*t*".toCharArray()));

        System.out.println(isMatch_Trie(dict, "cop*"));
        System.out.println(isMatch_Trie(dict, "c*t"));
        System.out.println(isMatch_Trie(dict, "c*t*"));
    }

    /**
     * c*t matches cat, cart
     * Iter of each text in dict,
     * Thinking recursively, match a char & recurse on others
     * Base case - when pattern is exhausted return true if text also finished else false
     * Recur on -
     *  if not *, match a char from text & pattern and recur
     *  if *, either don't move text ptr fwd or move it by one (* matches 0 or more chars)
     *
     * Runtime = there is branching factor of 2 for *, so worst case can be O(2^n) exponential
     */
    static void matchPattern(List<String> dict, String pattern, List<String> res) {
        // for each word in dict, check if it matches pattern
        for (String text : dict) {
            if (isMatch_recur(text, pattern, 0, 0)) res.add(text);
        }
    }

    // elegant
    private static boolean isMatch_brute(int sIdx, int pIdx, char[] s, char[] p) {
        // if pattern has reached end then text should also end for a match to happen
        if (pIdx == p.length) return sIdx == s.length;
        // * means you either match 0 or more chars
        if (p[pIdx] == '*') return isMatch_brute(sIdx, pIdx+1, s, p) || isMatch_brute(sIdx+1, pIdx, s, p);
        // ? means you exactly match 1 char
        if (p[pIdx] == '?') return isMatch_brute(sIdx+1, pIdx+1, s, p);
        // else if it's not special chars, we need exact match
        if (s[sIdx] != p[pIdx]) return false;
        // finally try the next char with next regex
        return isMatch_brute(sIdx+1, pIdx+1, s, p);
    }

    // almost same as above
    static boolean isMatch_recur(String text, String pattern, int i, int j) {
        // pattern is finished, return final answer as true only if text also finished, else false (as some text still remains)
        if (j == pattern.length()) return i == text.length();

        // text finished already
        if (i >= text.length()) return j == pattern.length()-1 && pattern.charAt(j) == '*';

        char t = text.charAt(i); char p = pattern.charAt(j);

        // match 0 or more chars from text
        if (p == '*') return isMatch_recur(text, pattern, i+1, j) || isMatch_recur(text, pattern, i, j+1);
        if (t == p) return isMatch_recur(text, pattern, i+1, j+1);
        else return false; // char didn't match
    }

    /**
     *
     * Runtime = O(m), where m is the length of substring w/ O(n) cost of building Prefix Trie
     */
    static List<String> isMatch_Trie(List<String> dict, String pattern) {
        Trie trie = buildTrie(dict);
        List<String> res = new ArrayList<>();
        searchTrie(trie.root, new Stack<>(), pattern.toCharArray(), 0, res);
        return res;
    }

    /**
     * c*t matches cat,cart
     * - dfs trie matching prefixes
     * - when hit with a * either skip node or move pattern ptr
     * - when pattern is exhausted add textSoFar to result
     */
    static void searchTrie(TNode node, Stack<Character> textSoFar, char[] pattern, int p, List<String> res) {
        if (p == pattern.length) {  // reached end of pattern, return
            String s = "";
            for (char c : textSoFar) s += c;
            if (node.childs.containsKey('$')) res.add(s); // add text, if reached end
            return;
        }

        if (node.childs.isEmpty()) return;  // no path forward in text

        if (pattern[p] == '*') {
            node.childs.forEach((k,v) -> {      // either match a char in text (dfs all childs)
                textSoFar.add(k);
                searchTrie(v, textSoFar, pattern, p, res);
                textSoFar.pop();    // revert stack
            });
            searchTrie(node, textSoFar, pattern, p+1, res); // or match none in text
        } else if (!node.childs.containsKey(pattern[p])) {  // chars don't match in text & pattern
            return;
        } else {
            textSoFar.add(pattern[p]);
            searchTrie(node.childs.get(pattern[p]), textSoFar, pattern, p+1, res);  // move both ptrs by one
            textSoFar.pop();
        }
    }

    static Trie buildTrie(List<String> dict) {
        Trie trie = new Trie();
        dict.forEach(text -> {
            TNode node = trie.root;
            for (char c: text.toCharArray()) {
                if (!node.childs.containsKey(c)) {  // create if char doesn't exist
                    TNode newNode = new TNode(c);
                    node.childs.put(c, newNode);
                }
                node = node.childs.get(c);  // keep moving thru chars
            }
            node.childs.put('$', new TNode('$'));   // end of word
        });
        return trie;
    }

    // O(nm) runtime. n = num words, m = max length of word
    public static class Trie {
        TNode root = new TNode('#'); // root node
    }

    public static class TNode {
        public char ch;
        public Map<Character,TNode> childs = new HashMap<>();

        TNode(char c) {
            ch = c;
        }
    }

}
