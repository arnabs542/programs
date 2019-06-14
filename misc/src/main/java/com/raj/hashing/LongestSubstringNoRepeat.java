package com.raj.hashing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author <a href="mailto:rshekhar@walmartlabs.com">Shekhar Raj</a>
 */
public class LongestSubstringNoRepeat {

    // O(n^2)
    public int lengthOfLongestSubstring(String a) {
        int max = 0;
        for (int i=0; i<a.length(); i++) {
            HashSet<Character> set = new HashSet<>();
            for (int j=i; j<a.length(); j++) {
                char ch = a.charAt(j);
                if (set.contains(new Character(ch))) {
                    break;
                }
                set.add(new Character(ch));
            }
            max = (set.size() > max) ? set.size() : max;   // update max pointer
        }
        return max;
    }

    // O(n) with O(n) space
    public int lengthOfLongestSubstring_On(String s) {
        Map<Character,Integer> map = new HashMap<>();
        int max = 0;
        // extend/contract sliding window (i,j) as we traverse
        for (int i = 0, j = 0; j < s.length(); j++) {
            if (map.containsKey(s.charAt(j))) { // need to adjust i
                i = Math.max(i, map.get(s.charAt(j))+1);
            }
            max = Math.max(max, j-i+1); // keep updating max length
            map.put(s.charAt(j), j);    // add this char and it's index
        }
        return max;
    }

    public static void main(String[] args) {
        LongestSubstringNoRepeat l = new LongestSubstringNoRepeat();
        System.out.println(l.lengthOfLongestSubstring_On("dadbc"));
        System.out.println(l.lengthOfLongestSubstring_On("dvdf"));
        System.out.println(l.lengthOfLongestSubstring_On("advdfg"));
    }
}
