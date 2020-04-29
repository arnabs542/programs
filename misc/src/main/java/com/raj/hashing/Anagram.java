package com.raj.hashing;

import java.util.*;

/**
 * @author rshekh1
 */
public class Anagram {

    /**
     * Given an array of strings, return all groups of strings that are anagrams. Represent a group by a list of integers representing the index in the original list. Look at the sample case for clarification.
     *
     *  Anagram : a word, phrase, or name formed by rearranging the letters of another, such as 'spar', formed from 'rasp'
     *  Note: All inputs will be in lower-case.
     * Example :
     *
     * Input : cat dog god tca
     * Output : [[1, 4], [2, 3]]
     */
    public static void main(String[] args) {
        System.out.println(findAnagrams(new String[] {"cat", "dog", "god", "tca"}));
    }

    /**
     * Create a map with sorted letters as key and values as word's index
     * For each word in list, sort the letters of the word i and add it's index as value to the map
     * Return values as array index
     */
    static int[][] findAnagrams(String[] strings) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strings) {
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            String sortedString = new String(arr);
            map.computeIfAbsent(sortedString, k -> new ArrayList<>()).add(s);
        }
        System.out.println(map);
        return null;
    }
}
