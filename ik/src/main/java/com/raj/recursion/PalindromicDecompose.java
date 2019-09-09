package com.raj.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author rshekh1
 */
public class PalindromicDecompose {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(generate_palindromic_decompositions("aab")));
        System.out.println(Arrays.toString(generate_palindromic_decompositions("abracadabra")));
    }

    /**
     * For input s = “abracadabra”, output will be:
     *
     * a|b|r|a|c|ada|b|r|a
     * a|b|r|aca|d|a|b|r|a
     * a|b|r|a|c|a|d|a|b|r|a
     *
     * ============
     *               aab
     *       a,ab     aa,b   aab
     *       a|ab     aa|b    X
     *   a|a,b  a|ab
     *  a|a|b   X
     */
    static List<String> res;

    static String[] generate_palindromic_decompositions(String s) {
        res = new ArrayList<>();
        palinDecompose("", s);
        return res.toArray(new String[res.size()]);
    }

    static void palinDecompose(String soFar, String rest) {
        if (rest.isEmpty()) {
            String _soFar = soFar.substring(0, soFar.length()-1);
            res.add(_soFar);
            return;
        }

        String tmp = "";
        for (int i = 0; i < rest.length(); i++) {
            tmp += rest.charAt(i);  // generates a, aa, aab
            if (isPalin(tmp)) {     // if the generated string is a palindrome
                // add it to soFar which is our decomposed result & recurse on remaining
                palinDecompose(soFar + tmp + "|", rest.substring(i+1));
            }
        }
    }

    static boolean isPalin(String s) {
        if (s == null || s.isEmpty()) return false;
        if (s.length() == 1) return true;
        for (int i = 0, j=s.length()-1; i < j; i++,j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }
}
