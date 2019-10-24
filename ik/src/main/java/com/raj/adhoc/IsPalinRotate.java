package com.raj.adhoc;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rshekh1
 */
public class IsPalinRotate {

    /**
     * Given a string s of length N containing only lower case letters (a - z), we have to check if it is a rotation of
     * some palindromic string or not. Return one integer res. Return 1 if given string s is a rotation of some palindromic string else return 0.
     * For input s = aab  =>  1
     */
    public static void main(String[] args) {
        System.out.println(check_if_rotated("aa"));  // 1
        System.out.println(check_if_rotated("aab")); // 1
        System.out.println(check_if_rotated("abcb")); // 0
        System.out.println(check_if_rotated("dadad")); // 1
        System.out.println(check_if_rotated("aaabbcdcbbaaa")); // 1
        System.out.println(check_if_rotated("aaabbb")); // 0
        System.out.println(check_if_rotated("abcbc")); // 0
    }

    /**
     * WRONG !! THIS IS CORRECT if we had to arrive at a palin through any arrangement, but we can only rotate here
     * Intuition - num chars is even & only one can be odd
     * aab => 2a, 1b => 1
     * abc => all are odd => 0
     * aabb => all are even => 1
     */
    static int _check_if_rotated(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (set.contains(c)) set.remove(c);
            else set.add(c);
        }
        return (set.isEmpty() || set.size() == 1) ? 1 : 0;
    }

    static int check_if_rotated(String s) {
        // abcbc => bcbca => cbcab => bcabc => cabcb => abcbc (left rotate by 1)
        // start with i = 0 to n-1 & form substr from that idx rotated & check if palin
        for (int i = 0; i < s.length(); i++) {  // O(n^2) ... n for iter & n for palin check
            String s1 = s.substring(i);     // substring operation is O(1)
            String s2 = i > 0 ? s.substring(0, i) : "";
            if (isPalin(s1 + s2)) return 1;
        }
        return 0;
    }

    static boolean isPalin(String str) {
        int s = 0, e = str.length() - 1;
        while (s < e) {
            if (str.charAt(s++) != str.charAt(e--)) return false;
        }
        return true;
    }

}
