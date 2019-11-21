package com.raj.strings;

import java.util.*;

public class WordIndexInStr {

    public static void main(String[] args) {
        System.out.println(findIndex("you are a very very smart a", Arrays.asList("you", "are", "very", "handsome", "a")));
    }

    /**
     * text = “you are very very smart”
     * words = [“you”, “are”, “very”, “handsome”]
     *
     * Sample Output 1:
     * [ [0], [4], [8, 13], [-1] ]
     *
     * Runtime: O((n+w)*l) - It takes O(I) time to calculate hashcode or to compare two strings up to l characters long. Thus populating the hashmap with n words will take O(n*l), making w searches in that hashmap will take O(w*l). Total time is the sum of those: O(n*l) + O(w*l) = O((n+w)*l)
     */
    static ArrayList<ArrayList<Integer>> findIndex(String text, List<String> words) {

        String[] wordsInText = text.split(" ");

        // {word -> [index1, index2]}
        HashMap<String, ArrayList<Integer>> textIndex = new HashMap<>();
        int currentIndex = 0;
        for (String word : wordsInText) {
            ArrayList<Integer> indexes = textIndex.get(word);
            if (indexes == null) {
                indexes = new ArrayList<>();
            }
            indexes.add(currentIndex);
            textIndex.put(word, indexes);
            currentIndex += word.length() + 1;
        }

        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        for (String word : words) {
            ArrayList<Integer> indexes = textIndex.get(word);
            if (indexes == null) {
                indexes = new ArrayList<>(Collections.singleton(-1));
            }
            answer.add(indexes);
        }
        return answer;
    }

    /**
     * Trie based solution:
     * In this solution we use trie or prefix tree, see https://en.wikipedia.org/wiki/Trie.
     * First we insert all words from the text into the trie. Then we look up every word from words in the trie.
     * Although this solution has the same worst case time and space complexity as the hashmap based solution,
     * it will utilize less space when many words share common prefixes.
     * In the actual interview many interviewers will prefer to hear the trie based solution to the hashmap based one.
     *
     * For more hints on hashmap vs. trie based algorithms, see:
     * https://stackoverflow.com/questions/245878/how-do-i-choose-between-a-hash-table-and-a-trie-prefix-tree
     *
     * Advantages of tries:
     * The basics:
     * Predictable O(k) lookup time where k is the size of the key
     * Lookup can take less than k time if it's not there
     * Supports ordered traversal
     * No need for a hash function
     * Deletion is straightforward
     * New operations:
     * You can quickly look up prefixes of keys, enumerate all entries with a given prefix, etc.
     * Advantages of linked structure:
     * If there are many common prefixes, the space they require is shared.
     * Immutable tries can share structure. Instead of updating a trie in place, you can build a new one that's different only along one branch, elsewhere pointing into the old trie. This can be useful for concurrency, multiple simultaneous versions of a table, etc.
     * An immutable trie is compressible. That is, it can share structure on the suffixes as well, by hash-consing.
     */

}
