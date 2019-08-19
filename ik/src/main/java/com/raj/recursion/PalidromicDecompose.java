package com.raj.recursion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rshekh1
 */
public class PalidromicDecompose {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(generate_palindromic_decompositions("abracadabra")));
    }

    /**
     * For input s = “abracadabra”, output will be:
     *
     * a|b|r|a|c|ada|b|r|a
     * a|b|r|aca|d|a|b|r|a
     * a|b|r|a|c|a|d|a|b|r|a
     *
     *                      caba
     *            c,aba  a,ba  b,a  a,''
     *      [c,ca,cab,caba] [a,ab,aba] [b,ba] [a]
     */
    static String[] generate_palindromic_decompositions(String s) {
       genPalins(s, 0, "");
       return res.toArray(new String[res.size()]);
    }

    static Set<String> res = new HashSet<>();

    // "caba" => c|aba
    static void genPalins(String s, int pos, String soFar) {
        System.out.println(soFar);
        if (pos == s.length()) {
            res.add(soFar);
            return;
        }

        for (int i = pos; i < s.length(); i++) {
            if (isPalin(s.substring(pos, i))) {
                if (pos == 0) genPalins(s, i + 1, s.substring(pos, i - pos));
                else genPalins(s, i + 1, soFar + '|' + s.substring(i - pos));
            }
        }
    }

    // "caba" => [a, aba, b, c]
    static void _genPalins(String soFar, String rest) {
        if (isPalin(soFar)) res.add(soFar);

        for (int i = 0; i < rest.length(); i++) {
            _genPalins(rest.substring(0, i+1), rest.substring(i+1));
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
