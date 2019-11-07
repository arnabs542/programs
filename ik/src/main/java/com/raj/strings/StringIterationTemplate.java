package com.raj.strings;

import com.raj.Util;

import java.util.Arrays;
import java.util.Stack;

public class StringIterationTemplate {

    public static void main(String[] args) {
        printAllSubstrings("abcd"); printAllSubstrings_Optimized("abcd"); System.out.println("---");

        printAllSubSets("abcd".toCharArray(), 0, new Stack<>()); System.out.println("---");

        permuteAllChars("abc".toCharArray(), 0); System.out.println("---");
    }

    /**
     * Print all substring: (contiguous chars)
     * abcd => a, ab, abc, abcd, b, bc, bcd, c, cd
     *
     * Note - we aren't skipping chars when going from left to right (as we do when we print subsets)
     *
     * Algo:
     * # 1st loop = Fix ith char, eg. b
     * # 2nd loop = j : Run from i until len, printing substrs(i...j)
     * Runtime = O(n^3)
     */
    static void printAllSubstrings(String str) {
        int cmp = 0;
        for (int i = 0; i < str.length(); i++) {    // O(n)
            System.out.println();
            for (int j = i; j < str.length(); j++) {    // O(n)
                cmp += j+1 - i;
                System.out.print(str.substring(i, j+1) + " ");  // substring is also O(n) as it has to run thru the length
            }
        }
        System.out.println("comparisons = " + cmp);
    }

    /**
     * Idea is to use a temp string and keep appending chars (use extra aux space)
     * Runtime = O(n^2)  ... n + n-1 + n-2 ... 1 = n(n+1)/2 = O(n^2)
     * Aux Space = O(n)
     */
    static void printAllSubstrings_Optimized(String str) {
        int cmp = 0;
        for (int i = 0; i < str.length(); i++) {        // O(n)
            System.out.println();
            StringBuilder tempStr = new StringBuilder();
            for (int j = i; j < str.length(); j++) {    // O(n)
                tempStr.append(str.charAt(j));          // append is O(1) with StringBuilder
                cmp++;
                System.out.print(tempStr + " ");
            }
        }
        System.out.println("comparisons = " + cmp);
    }

    /**
     * Print all subsets (Ordering doesn't matter)
     * abcd => (1 char) a,b,c,d, (2 char) ab,ac,ad,bc,bd, (3 char) abc,abd,acd,bcd, (4 char) abcd
     * Runtime = Num Arrangements Ideally for 1 char + 2 chars + 3 chars + 4 chars = C(4,1) + C(4,2)...
     * => O(2^n)
     *
     * Note - We are skipping chars in b/w when going from left to right
     *
     * Algo:
     * # Include / Exclude ith char & recurse
     */
    static void printAllSubSets(char[] A, int i, Stack<Character> slate) {
        if (i == A.length) {
            System.out.print(slate + " ");
            return;
        }

        printAllSubSets(A, i+1, slate);     // exclude ith char
        slate.add(A[i]); printAllSubSets(A, i+1, slate); slate.pop();   // include ith char by adding it to slate & revert later
    }

    /**
     * Print all Permutations of string
     * abc => abc, acb, bac, bca, cab, cba
     *
     * Algo:
     * # Iterate & Fix the ith char (using swap bring it to front for easier manipulation)
     * # Recurse on i+1, revert swap
     * # Print when i==len
     * # For dupes chars, we can do an optimization to prune the recursion beforehand if it's been done before (store rec seed in set)
     */
    static void permuteAllChars(char[] A, int i) {
        if (i == A.length) {
            System.out.print(Arrays.toString(A) + " ");
            return;
        }

        // iterate & fix each char from i...len
        // it's easier to bring the fixed char at front & revert it later after recursion
        for (int j = i; j < A.length; j++) {
            Util.swap(A, i, j);
            permuteAllChars(A, i+1);
            Util.swap(A, i, j);
        }
    }

}
