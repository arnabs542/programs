package com.raj.hashing;

import java.util.HashMap;
import java.util.Map;

public class AnagramsMake {

    // Complete the makingAnagrams function below.
    /**
     * We consider two strings to be anagrams of each other if the first string's letters can be rearranged to form the second string. In other words, both strings must contain the same exact letters in the same exact frequency. For example, bacdc and dcbac are anagrams, but bacdc and dcbad are not.
     *
     * Alice is taking a cryptography class and finding anagrams to be very useful. She decides on an encryption scheme involving two large strings where encryption is dependent on the minimum number of character deletions required to make the two strings anagrams. Can you help her find this number?
     *
     * Given two strings,  and , that may not be of the same length, determine the minimum number of character deletions required to make  and  anagrams. Any characters can be deleted from either of the strings.
     *
     * For example,  and . The only characters that match are the 's so we have to remove  from  and  from  for a total of  deletions.
     *
     ab, ab => 0
     abc, ade => 4
     abc, amnop => 6
     aaa, aaa => 0
     aaa, bbb => 6
     aabbcc, aaaabbbbcccc => 6
     => a->2,b->2,c->2 (-) a->4,b->4,c->4 => 6

     Algo:
     - add first str to hashMap with counts of chars
     - for each c in 2nd str, if it exists in map, decrement count, else add it
     - aaabc, aabbcc => 3

     */
    static int makingAnagrams(String s1, String s2) {
        if (s1 == null && s2 == null) return 0;
        Map<Character,Integer> map1 = new HashMap<>();
        Map<Character,Integer> map2 = new HashMap<>();
        for (Character c : s1.toCharArray()) addToMap(map1, c);
        for (Character c : s2.toCharArray()) addToMap(map2, c);

        int count = 0;
        for (Character c : map1.keySet()) { // process map1 & (map1 intersect map2) elems
            if (map2.containsKey(c)) {
                count += Math.abs(map1.get(c) - map2.get(c));
                map2.remove(c);
            }
            else count += map1.get(c);
        }
        for (int i : map2.values()) count += i;  // process remaining map2
        return count;
    }

    static void addToMap(Map<Character,Integer> map, Character c) {
        if (!map.containsKey(c)) map.put(c, 1);
        else map.put(c, map.get(c) + 1);
    }

    public static void main(String[] args) {
        System.out.println(AnagramsMake.makingAnagrams("aabbcc", "aaaabbbbcccc"));
    }

}
