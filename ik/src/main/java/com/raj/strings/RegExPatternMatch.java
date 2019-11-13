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

        // In this question, b* only matches sequences of b '', b, bb, bbb... and not ba or bcd etc
        System.out.println(pattern_matcher("abb", "ab*"));
        System.out.println(pattern_matcher("abbc", "ab*"));
        System.out.println(pattern_matcher("aaa", ".a*.."));
        System.out.println(pattern_matcher("aaa", ".*b"));

        System.out.println(dp("abb", "ab*"));
        /*System.out.println(dp("abbc", "ab*"));
        System.out.println(dp("aaa", ".a*.."));
        System.out.println(dp("aaa", ".*b"));
        System.out.println(dp("", "a*b*c*.*g*"));*/

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

        // match 0 or more chars from text
        if (pattern.charAt(j) == '*') return isMatch_recur(text, pattern, i+1, j) || isMatch_recur(text, pattern, i, j+1);
        if (text.charAt(i) == pattern.charAt(j)) return isMatch_recur(text, pattern, i+1, j+1);
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

    /**
     * IK Problem: (slightly different from above)
     * Given a text string containing characters only from lowercase alphabetic characters and a pattern string containing
     * characters only from lowercase alphabetic characters and two other special characters '.' and '*'.
     * Your task is to implement pattern matching algorithm that returns true if pattern is matched with text otherwise returns false.
     * The matching must be exact, not partial.
     * 1) '.'  Matches a single character from lowercase alphabetic characters.
     * 2) '*'  Matches zero or more preceding character. It is guaranteed that '*' will have one preceding character. '**' won't occur as well.
     *
     * '.' = "a", "b", "c", ... , "z".
     * a*  = "", "a","aa","aaa","aaaa",...
     * ab* = "a", "ab", "abb", "abbb", "abbbb",...
     *
     * Example:
     * 1> text = "abbbc" and pattern = "ab*c" => can match "ac", "abc", "abbc", "abbbc"
     * true
     *
     * 2> text = "abcdefg" and pattern = "a.c.*.*gg*"
     * true
     * ".*" in pattern can match  "", ".", "..", "...", "....", ... (special case)
     * "g*" in pattern can match "", "g", "gg", "ggg", "gggg", ...
     *
     * "abc" and pattern = ".ab*.." => false
     * Too many edge cases to handle....
     */
    static boolean pattern_matcher(String text, String pattern) {
        return rec(text.toCharArray(), pattern.toCharArray(), 0, 0);
    }

    // O(2^n) exponential complexity
    static boolean rec(char[] txt, char[] pat, int i, int p) {
        if (p == pat.length) return i == txt.length;
        if (i >= txt.length) return p+1 < pat.length && pat[p+1] == '*' && rec(txt, pat, i, p+2);

        // 0 or more char match. handle cases like a* -> '', a, aa, aaa & ".a*.." -> "aaa"
        /**
         * '*' match has many edge cases:
         * 1. a* -> ''
         * 2. a* -> a
         * 3. a* -> aa, aaa ... (skip txt char only if prev pattern char matches)
         * If look forward 1 char and determine its a '*' then we can handle these
         */
        char next = (p+1 < pat.length && pat[p+1] == '*') ? '*' : ' ';
        if (next == '*') {
            if (pat[p] == txt[i] || pat[p] == '.') return rec(txt, pat, i+1, p) || rec(txt, pat, i, p+2); // for more chars, the prev char has to match
            return rec(txt, pat, i, p+2);   // otherwise just match 0 chars
        }

        //match 1 char from pattern and text and go to next in both
        if(txt[i] == pat[p] || pat[p] == '.') {
            return rec(txt, pat, i+1, p+1);
        }

        return false;
    }

    static boolean dp(String text, String pattern) {
        int N = text.length();
        int M = pattern.length();
        boolean[][] dp = new boolean[M+1][N+1];
        dp[0][0] = true;
        for (int i = 1; i <= M; i++) {
            dp[i][0] = (pattern.charAt(i-1) == '*') && dp[i-2][0]; // handles ("", a*b*c*.*g*)
        }
        for (int i = 1; i <= M; i++) {  // row i is for pattern
            for (int j = 1; j <= N; j++) {  // col j is for text
                char t = text.charAt(j-1);
                char p = pattern.charAt(i-1);
                if (t == p || p == '.') {
                    dp[i][j] = dp[i-1][j-1];
                } else if (p == '*') {
                    dp[i][j] = dp[i-2][j] ||
                            (((pattern.charAt(i-2) == t) || (pattern.charAt(i-2) == '.')) && dp[i][j-1]);
                } else {
                    dp[i][j] = false;
                }
            }
        }
        System.out.println(Arrays.deepToString(dp));
        return dp[M][N];
    }

}
