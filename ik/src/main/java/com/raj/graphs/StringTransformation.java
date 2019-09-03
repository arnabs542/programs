package com.raj.graphs;

import java.util.*;

/**
 * @author rshekh1
 */
public class StringTransformation {

    public static void main(String[] args) {

        System.out.println(Arrays.toString(string_transformation(
                new String[]{"cccw"}, null, null)));

        System.out.println(Arrays.toString(string_transformation(
                new String[]{}, "zzzzz", "zzzzz")));

        System.out.println(Arrays.toString(string_transformation(
                new String[]{}, "bbb", "bbc")));

        System.out.println(Arrays.toString(string_transformation(
                new String[]{"aaa"}, "baa", "aab")));

        System.out.println(Arrays.toString(string_transformation(
                new String[]{"cat", "hat", "bad", "had"}, "bat", "had")));

        System.out.println(Arrays.toString(string_transformation(
                new String[]{
                        "ecadccb",
                        "caecbed",
                        "becccba",
                        "ebaabab",
                        "zxebaac",
                        "bcebaad",
                        "baebaad",
                        "baebacd",
                        "bacbacd",
                        "cacbacd",
                        "cacbacc",
                        "qtbbacc"
                }, "bcebaac", "cabbacc")));
    }

    /**
     * You need to transform string start to string stop in least number of steps using given dictionary words.
     * In each transformation, you can only change one character of the current string.
     * e.g. "abc" -> "abd" is a valid transformation, because only one character 'c' is changed to 'd',
     * "abc" -> "axy" is not a valid transformation, because two characters are changed.
     * Every string (except, possibly, first and last ones) are in the dictionary of words.
     * For input n = 4, words = ["cat", "hat", "bad", "had"], start = "bat" and stop = "had", output will be:
     * bat
     * hat
     * had
     *
     * For i/p words = [], start = bbb, stop = bbc, o/p = ["bbb", "bbc"]
     * For i/p words = [], start = "zzzzzz", stop = "zzzzzz", o/p = [-1]
     */
    static String[] string_transformation(String[] words, String start, String stop) {
        if (start == null || (start.equals(stop) && words.length == 0)) return new String[] {"-1"};
        Set<String> dict = new HashSet<>();
        Arrays.stream(words).forEach(x -> dict.add(x));
        dict.add(stop); // add the destination so as to handle edge case where no neighbors are discovered for aaa -> aab
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(new Vertex(start, null));

        while (!queue.isEmpty()) {  // bfs already does shortest path
            Vertex v = queue.poll();

            List<Vertex> neighbors;
            // if num transformations for this word (which is string len * 26) will be greater than dict words, then just iterate dict words & pick 1 char transformations
            if (dict.size() < v.word.length() * 26) {
                neighbors = getNeighbors_choose_directly_from_dict(v, dict);
            } else {    // otherwise do the 26 way char transforms at each index, every time checking dict to see if it's a valid word
                neighbors = getNeighbors_by_transform(v, dict);
            }

            for (Vertex w : neighbors) {

                // reached goal, return (this check cud be after queue.poll as well)
                if (w.word.equals(stop)) return buildWordPath(w);

                // check if the new word exists in dict & has not been seen before
                if (dict.contains(w.word)) {
                    dict.remove(w.word); // remove from dict is equivalent of marking it as visited
                    queue.add(w);
                }
            }


        }
        return new String[] {"-1"};
    }

    static List<Vertex> getNeighbors_choose_directly_from_dict(Vertex v, Set<String> dict) {
        // check dict for 1 char transformations from v & add it as neighbor
        List<Vertex> neigh = new ArrayList<>();
        for (String dictWord : dict) {
            int charDiffs = 0;
            for (int i = 0; i < dictWord.length(); i++) {
                if (dictWord.charAt(i) != v.word.charAt(i)) charDiffs ++;
            }
            if (charDiffs == 1) neigh.add(new Vertex(dictWord, v));
        }
        return neigh;
    }

    static List<Vertex> getNeighbors_by_transform(Vertex v, Set<String> dict) {
        List<Vertex> neigh = new ArrayList<>();

        // for each neighbor which are 1 char transform away, add to neighbor, if valid dict word
        for (int i = 0; i < v.word.length(); i++) {

            char[] arr = v.word.toCharArray();
            for (char j = 'a'; j <= 'z'; j++) { // try all alphabet at jth index
                if (v.word.charAt(i) == j) continue; // skip as char matches
                char tmp = arr[i];
                arr[i] = j; // substitute char
                String newWordStr = new String(arr); // NOTE : arr.toString() doesn't give String
                if (dict.contains(newWordStr)) neigh.add(new Vertex(newWordStr, v));
                arr[i] = tmp; // revert substitute char
            }
        }
        return neigh;
    }

    // builds string transforms using prev ptrs
    static String[] buildWordPath(Vertex v) {
        List<String> res = new ArrayList<>();
        while (v != null) {
            res.add(v.word);
            v = v.prev;
        }
        Collections.reverse(res);
        return res.toArray(new String[res.size()]);
    }

    static class Vertex {
        String word;
        // IMP: we can build start -> stop transformations using this link info without extra space
        // instead of using Aux Space complexity of 1+2+3...n = O(n^2), if maintaining a List<words transformations so far>
        Vertex prev;

        Vertex (String w, Vertex p) {
            word = w;
            prev = p;
        }

        public String toString() {
            return word;
        }
    }

}
