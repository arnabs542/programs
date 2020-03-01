package com.raj.strings;

public class SubstringMatch {

    /**
     * Find if a given substring is present in text
     * abcdbcam, bca => 4
     *
     */
    public static void main(String[] args) {
        System.out.println(bruteForce("abcdbcam", "bca"));
        System.out.println(rabin_karp("abcdbcam", "bca"));
        KMP();
    }

    /**
     * O(nm), where n is the length of text & m is the len of substr
     */
    static int bruteForce(String text, String substr) {
        for (int i = 0; i < text.length(); i++) {
            int j = 0;
            while (j < substr.length() && text.charAt(i+j) == substr.charAt(j)) {
                j++;
            }
            if (j == substr.length()) return i;
        }
        return -1;
    }

    /**
     * Rolling Hash Sliding Window - Rabin Karp Algo
     * # Compute Hash for substr using prime num & digits place significance (i* prime^0 + i* prime^1 + i* prime^2 ...)
     * # Iterate & keep calculating hash for window of size substr
     * # Readjust hash as we go from left to right in text as follows -
     *   - deduct leading char from text
     *   - divide hash / prime
     *   - add the new char ascii num w/ prime^substr.length-1
     *   - why ? eg. say prime=3 & ascii char as (text:2345, substr:345)
     *          => substr = (3*3^0)+(4*3^1)+(5*3^2) = 60
     *          => text(0..2) = 2+3*3+4*9 = 47, text(1..3) = (47-2)/3 + 5*9 = 60 (which is a match, we double confirm by iterating substr w/ matched text portion)
     * Runtime = hash is O(1), avg case = O(n) + O(m) = O(n)
     *                         worst case = O(nm), if hash is poor & collisions cause each of text's substr to match given substr
     */
    static int rabin_karp(String text, String substr) {
        int prime = 101;

        // compute substr hash
        int hashSubstr = 0, hashText = 0;
        for (int i = 0; i < substr.length(); i++) {
            hashSubstr += ((int)substr.charAt(i)) * Math.pow(prime,i);
            hashText += ((int)text.charAt(i)) * Math.pow(prime,i);
        }
        if (hashSubstr == hashText && text.substring(0, substr.length()).equals(substr)) return 0;

        // compute sliding window hash and check if it matches
        for (int i = substr.length(); i < text.length()-substr.length()+1; i++) {
            hashText = (int) ((hashText - (int)text.charAt(i-1)) / prime
                    + (int)text.charAt(i+substr.length()-1) * Math.pow(prime,substr.length()-1));
            if (hashSubstr == hashText && text.substring(i, i+substr.length()).equals(substr)) return i;
        }
        return -1;

        /**
         * Other problems that can be solved using Rolling Hash Pattern:
         * -> Given two strings A and B, and a number X find if they have a common sequence of length X.
         *    eg. ABCD, XBCY, len = 2
         *    - compute X length hashes & store in map, iter second & check if X length hash matches => O(n)
         * -> Given two strings, find out if one is a rotation of the other.
         *    eg. ABCD, DABC (compute hash of 2 letters & slide ?)
         * -> Keep two files on disk in sync, by copying only the deltas.
         */

    }

    /**
     * Using Suffix Trie -  https://www.geeksforgeeks.org/pattern-searching-using-trie-suffixes/
     * Building a Trie of Suffixes
     * 1) Generate all suffixes of given text.
     * 2) Consider all suffixes as individual words and build a trie.
     *
     * Let us consider an example text “banana$”. Following are all suffixes of “banana”
     * banana$
     * anana$
     * nana$
     * ana$
     * na$
     * a$
     * $
     *
     * 3) Now Search the pattern in above suffix tree - O(m) w/ building tree as O(n) cost & space
     */


    /**
     * Other linear time algos: https://www.geeksforgeeks.org/pattern-searching-using-trie-suffixes/
     * 1) Preprocess Pattern: KMP Algorithm, Rabin Karp Algorithm, Finite Automata, Boyer Moore Algorithm.
     * 2) Preprocess Text: Suffix Trie
     *    => Optimization - Compact Suffix Tree
     *    => For more details refer to SuffixTrie.java
     * 3) If substring is a valid prefix of word, build a Prefix Trie instead
     *    => Compressed Trie also called "radix tree"
     *    => For faster retrieval, you can store all words starting with prefix at each node w/ memory cost.
     *    => Push all prefix -> list words into in-memory shards for O(1) retrieval
     *    => For more enriched auto-complete experience, search parallely in Prefix Maps + Suffix Trees (which can be TST w/ O(logn) as it's less common)
     *       -> eg. try searching "orld" in Google. It shows "world". It means it also searches suffixes.
     * 4) Search in a huge file size of gigs, break it into smaller chunks, push into shards memory, apply linear time algos in parallel
     * 5) Lucene search (Inverted Index)
     *    => for use in distributed scale (uses inverse map of pattern -> {list of docs}) ... O(1) lookup
     *    => Shard it by start chars a,b,c etc. Fire parallel & distributed queries.
     *    => "the hello world" -> remove stopwords, fire hello & world parallely to 'h' & 'w' shards
     *
     * 6) A ternary search tree is a special trie data structure where the child nodes of a standard trie are ordered
     * as a binary search tree, but with up to three children rather than the binary tree's limit of two.
     *    => https://www.geeksforgeeks.org/ternary-search-tree/
     *    => https://en.wikipedia.org/wiki/Ternary_search_tree
     *    => https://www.geeksforgeeks.org/ternary-search-tree/
     *    => Common applications for ternary search trees include spell-checking and auto-completion.
     *    => Ternary search trees are more space efficient compared to standard prefix trees, at the cost of speed -
     *       -> BST like complexity ie. O(logn) w/ base 3 & O(n) space cost to build.
     *    => Further, ternary search trees can be used any time a hashtable would be used to store strings.
     *      -> There are applications where you need to just query if the string exists in a dictionary or not. Example: You have been given a jumbled word and you need to find the correct word that can be formed out of it. Then you just need to find its each permutation and query if it's there in the dictionary.
     *    => https://www.cs.usfca.edu/~galles/visualization/TST.html
     *
     * # Then when should we use TST?
     * -> One of the advantages of using TST over HashTable is that it helps in prefix searching and "near neighbor lookups".
     * Say you want to search all words starting with "Man". It can be used to implement a dictionary w/ auto-complete feature.
     * -> TRIE too can be used here. The downside with TRIE is its huge space requirements. If the input data is densely
     * distributed, then TRIE should be preferred over TST as many word prefix will share same nodes.
     *
     * Applications of ternary search trees:
     * 1. Ternary search trees are efficient for queries like “Given a word, find the next word in dictionary(near-neighbor lookups)” or “Find all telephone numbers starting with 9342 or “typing few starting characters in a web browser displays all website names with this prefix”(Auto complete feature)”.
     * 2. Used in spell checks: Ternary search trees can be used as a dictionary to store all the words. Once the word is typed in an editor, the word can be parallely searched in the ternary search tree to check for correct spelling.
     *
     */

     static void KMP() {
         /**
          *          0 1 2 3 4
          * String = A A A A B
          * Substr = A A B
          * When the mismatch happens at 2, there is no reason to reset back the substr to 0 as we know prev chars had matched.
          * So the idea is to reuse what computation we did earlier. We reset back substr to 1 and continue matching.
          * We'll need to pre-process substr to know where to rest in case of a mismatch
          * Runtime = O(n+m)  .. as we only reset back substr by 1 instead of all the way back
          */

     }

}
