package com.raj.backtracking;

import java.util.ArrayList;

/**
 * @author rshekh1
 */
public class PalindromePartition {

    static int comparisons = 0;
    public static void main(String[] args) {
        printPalindromes_brute("dbabb");
        System.out.println("Comparisons=" + comparisons);

        partition("nitin");
        System.out.println(res);
    }

    /**
     * Print all palindromes within a String
     * dbabb -> d,b,a,b,b,bab,bb
     * O(n^2) solve
     */
    public static void printPalindromes_brute(String s) {
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                comparisons ++;
                if (isPalindrome(s.substring(i,j))) System.out.println(s.substring(i,j));
            }
        }
    }

    public static boolean isPalindrome(String s) {
        if (s.isEmpty()) return false;
        for (int i = 0,j = s.length()-1; i < j; i++, j--) {
            comparisons ++;
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }


    /**
     * Given a string s, partition s such that every string of the partition is a palindrome.
     * Return all possible palindrome partitioning of s.
     *
     * "aab" => [ ["a","a","b"], ["aa","b"] ]
     */
    static ArrayList<ArrayList<String>> res = new ArrayList<>();

    private static void partition(String a, int start, ArrayList<String> curPartitions) {
        if (start == a.length()) {
            res.add(new ArrayList<>(curPartitions));
            return;
        }
        for (int i = start + 1; i <= a.length(); i++) {
            String substr = a.substring(start, i);
            if (isPalindrome(substr)) {     // find a palindrome from starting left, partition at that char
                curPartitions.add(substr);           // add palindrome
                partition(a, i, curPartitions);      // and recurse on remaining
                curPartitions.remove(curPartitions.size() - 1);
            }
        }
    }

    public static ArrayList<ArrayList<String>> partition(String a) {
        res = new ArrayList<>();
        if (a.length() == 0) return res;
        partition(a, 0, new ArrayList<>());
        return res;
    }

}
