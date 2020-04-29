package com.raj.strings;

import java.util.*;

public class RotationAnagram {
    /**
     * Given a list of strings, group rotations of a string together
     * Input: ["tokyo", "paris", "aaa", "kyoto", "aaa", "sipar", "", "yotok"]
     * Output:[[tokyo, kyoto, yotok], [paris], [aaa], [sipar]]
     *
     * Note: "sipar" isn't rotation of "paris", hence they are grouped separately
     *
     * n = num of words, m = max length of a string
     *
     * Brute: O(n.n.m)
     * for each word, check every other word in list, is rotation of it?
     *
     * Optimal: O(n.mlogm + n.m)
     * # we need to optimize the check somehow => use a O(1) lookup DS
     * # what to we put as key? can we use anagram(sorted chars) of the word?
     * # how do we know if it is really a rotation, like "sipar" is anagram of "paris" but not a rotation
     *   - do a rotation check once we find a anagram key in map
     *   - "sipar" still needs to be added in output, we can't just add it separately in map as there might be a "sipar" rotation as well
     *     - we can do Map(sortedAnagram -> Map(word -> wordRotations))  ... now we can add multiple rotated groups of same anagram
     *     - eg. aiprs -> { {paris -> paris,...}, {sipar -> sipar,...} }
     * # how do we optimize check rotation?
     *   - concat string 2 times & check other is a substring eg. tokyotokyo, kyoto is substr hence rotation
     *
     * Another approach: O(n.2m)
     * # build 2 times appended strings eg. tokyotokyo in a suffix trie ... O(n.m) using Ukonnen's algo
     * # check if the kyoto substr is present in suffix trie  ... O(m)
     */
    static Collection<Collection<String>> groupAnagrams(List<String> words) {
        Map<String, List<Set<String>>> map = new HashMap<>();
        for (String word : words) {
            if (word == null || word.isEmpty()) continue;
            // sort chars
            char[] wordChars = word.toCharArray();
            Arrays.sort(wordChars);
            String sortedStr = new String(wordChars);
            if (!map.containsKey(sortedStr)) {
                map.put(sortedStr, new ArrayList<>());
                Set<String> set = new HashSet<>();
                set.add(word);
                map.get(sortedStr).add(set);  // kooty -> {tokyo}
            } else {
                // get current groups for this anagram
                List<Set<String>> groups = map.get(sortedStr);
                // prep a new group if the word isn't rotation of existing groups isn't found, this is to avoid concurrent mod exception
                Set<String> newGroup = new HashSet<>();

                // check which rotation group this word belongs
                for (Set<String> group : groups) {
                    if (isRotation(word, group.iterator().next())) group.add(word);
                    else {
                        newGroup.add(word);
                    }
                }
                // add to groups, if new group was populated
                if (!newGroup.isEmpty()) groups.add(newGroup);
            }
        }
        Collection<Collection<String>> res = new ArrayList<>();
        map.values().forEach(x -> res.addAll(x));
        return res;
    }

    static boolean isRotation(String word, String other) {
        return (word + word).contains(other);
    }

    public static void main(String[] args) {
        System.out.println(groupAnagrams(Arrays.asList("tokyo", "paris", "aaa", "kyoto", "aaa", "sipar", "", "yotok")));
    }

}
