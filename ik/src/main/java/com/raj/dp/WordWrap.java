package com.raj.dp;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class WordWrap {

    /**
     * Given a sequence of words (strings), and a limit on the number of characters that can be put in one line (line width), put line breaks in the given sequence such that the lines are printed neatly.
     * Input : words = [omg, very, are, extreme], limit = 10
     * Following arrangement of words in lines will have least cost:
     * Line1: “omg very  ”
     * Line2: “are       ”
     * Line3: “extreme”
     *
     * Note that we need to ignore the extra white spaces at the end of last line.
     * So, in the last line there will be 0 extra white spaces.
     * Cost for this configuration = (10 - (3+1+4))^3 + (10 - 3)^3 + (0)^3 = 351
     *
     * Input 2:
     * words = [abc, cd, e, ijklm], limit = 6
     *
     * Line1: “abc cd”
     * Line2: “e     ”
     * Line3: “ijklm”
     */
    public static void main(String[] args) {
        List<String> words = Arrays.asList("abc");
        min = Integer.MAX_VALUE; rec(words, 0, new Stack<>(), 5); System.out.println(min);
        long[] dpTable = new long[words.size()+1]; Arrays.fill(dpTable, -1); System.out.println(dp(words, 0, 5, dpTable));

        words = Arrays.asList("abc", "cd", "e", "ijklm");
        comp = 0; min = Integer.MAX_VALUE; rec(words, 0, new Stack<>(), 6); System.out.println(min);
        System.out.println("comparisons = " + comp);
        comp = 0; dpTable = new long[words.size()+1]; Arrays.fill(dpTable, -1); System.out.println(dp(words, 0, 6, dpTable));
        System.out.println("comparisons = " + comp);

        //System.out.println(solveBalancedLineBreaks(Arrays.asList("omg", "very", "are", "extreme"), 10));
    }

    /**
     * Brute Force? Not sure
     * Recursion ? Yes, DP ? Probably (minimize cost w/ constraints)
     * Recursion Tree:
     *              abc cd e ijklm  (limit = 6)
     *              /     |     \
     *           abc   abc cd    x
     *          / |       |
     *      abc  abc     abc cd
     *      cd   cd e    e
     *      /            \
     *    abc          abc cd
     *    cd e         e
     *    ijklm        ijklm
     *
     *
     */
    static long min;
    static int comp;

    public static void rec(List<String> words, int i, Stack<String> soFar, int limit) {
        comp ++;

        if (i >= words.size()) {
            long cost = 0;
            for (int j = soFar.size()-1; j >= 0; j--) {
                int left = j == soFar.size()-1 ? 0 : limit - soFar.get(j).length();
                cost += Math.pow(left,3);
            }
            System.out.println("soFar : " + soFar + ", cost = " + cost);
            min = Math.min(min, cost);
            return;
        }

        String curr = "";
        for (int j = i; j < words.size(); j++) {
            curr += curr.isEmpty() ? words.get(j) : " " + words.get(j);
            if (curr.length() > limit) continue;
            soFar.add(curr);
            rec(words, j+1, soFar, limit);
            soFar.pop();
        }
    }

    public static long dp(List<String> words, int i, int limit, long[] dpTable) {
        if (i >= words.size()) return 0;    // base case when index goes out of bounds, return 0 cost
        if (dpTable[i] != -1) return dpTable[i];  // memoized results

        String curr = "";
        long minCost = Integer.MAX_VALUE;  // our minCost using i...n-1 word

        for (int j = i; j < words.size(); j++) {
            curr += curr.isEmpty() ? words.get(j) : " " + words.get(j);     // keep appending word
            if (curr.length() > limit) continue;    // can't fit in line

            comp ++;

            // compute costs
            long thisLineCost = (j == words.size() - 1) ? 0 : (long) Math.pow(limit - curr.length(), 3);  // last line cost is 0 as per question
            long otherLineCost = dp(words, j+1, limit, dpTable);   // recursively get other costs
            long totalCost = thisLineCost + otherLineCost;
            minCost = Math.min(minCost, totalCost);     // minimize costs
        }
        dpTable[i] = minCost;  // memoize sub results
        System.out.println("dpTable = " + Arrays.toString(dpTable));
        return dpTable[i];
    }

}
