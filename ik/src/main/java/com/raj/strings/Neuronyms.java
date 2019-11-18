package com.raj.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Neuronyms {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(neuronyms("nailed")));
    }

    static String[] neuronyms(String word) {
        /**
         * Omit first & last char
         * For remaining str, gen all substr.
         * Fix i. For each i, j goes until n-2 : compute len
         * Print s.substr(0,i) + len + s.substr(j+1)
         * O(n^2) substrs * O(n) length print = O(n^3)
         */
        if (word == null || word.length() < 4) return new String[]{};
        List<String> res = new ArrayList<>();
        for (int i=1; i<word.length()-1; i++) {
            for (int j=i+1; j<word.length()-1; j++) {
                res.add(word.substring(0,i) + (j-i+1) + word.substring(j+1));
            }
        }
        return res.toArray(new String[res.size()]);
    }

}
