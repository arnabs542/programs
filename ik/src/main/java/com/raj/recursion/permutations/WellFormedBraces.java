package com.raj.recursion.permutations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rshekh1
 */
public class WellFormedBraces {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(find_all_well_formed_brackets(3)));
    }

    static String[] find_all_well_formed_brackets(int n) {
        p(0, 0, "", n);
        return res.toArray(new String[res.size()]);
    }

    static Set<String> res = new HashSet<>();

    static void p(int open, int close, String soFar, int n) {
        if (open == n && close == n) {  // all used, and well formed
            res.add(soFar);
            return;
        }

        if (open > n || close > n) return;  // exhausted one of the allowed open or close braces

        // try '('
        p(open + 1, close, soFar + "(", n);

        // try ')'  only if there is an open bracket already
        if (open > close) p(open, close + 1, soFar + ")", n);
    }

}
