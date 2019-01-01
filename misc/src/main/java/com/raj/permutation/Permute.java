package com.raj.permutation;

/**
 * https://www.youtube.com/watch?v=uFJhEPrbycQ
 */
public class Permute {

    /**
     * abcd => abcd, acbd, adbc, dabc, bdac...      (all 4 letters permutation as abcd is different than acbd)
     *             "", abcd
     *              /    \        \
     *          a,bcd    b,acd     c,abd ...
     *         /   \         \      \
     *     ab,cd  ac,bd ...  ba,cd  bc,ad ..
     */
    public static void main(String[] args) {
        permuteStr("", "abcd");
    }

    public static void permuteStr(String soFar, String remanining) {
        // base case or exit criteria
        if (remanining.isEmpty()) System.out.println(soFar);

        // recursive case
        for (int i = 0; i < remanining.length(); i++) {
            permuteStr(soFar+remanining.charAt(i), remanining.substring(0,i) + remanining.substring(i+1));
        }
    }

}
