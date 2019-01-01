package com.raj.combination;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author rshekh1
 */
public class Subsets {

    public static void main(String[] args) {
        subset("", "abc");

        subset_interviewBit(new ArrayList<>(), Lists.newArrayList(1,2,3));
        System.out.println(sortedSet);

        kLetterCombinations("", "12345", 3);
    }

    /**
     * Problem 1: Print all subsets where abc is same as acb/bac/cab etc [also called Combinations with any number of choices]
     * "abcd" => a,ab,abc,abcd,b,bc,bcd,bd,cd,c,d...
     */
    public static void subset(String soFar, String remaining) {
        if (remaining.isEmpty()) {
            System.out.println(soFar);
            return;
        }
        // include 'a' --> the first element of the set under consideration
        subset(soFar + remaining.charAt(0), remaining.substring(1));
        // don't include 'a'
        subset(soFar, remaining.substring(1));
    }


    /**
     * Problem 2: Almost same as above, except for lex sorting
     * [1,2,3] => [[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]] lexicographically sorted
     */
    public static void subset_interviewBit(List<Integer> soFar, List<Integer> remaining) {
        if (remaining.isEmpty()) {
            sortedSet.add(soFar);
            return;
        }
        List<Integer> tmpList = Lists.newArrayList(soFar);
        tmpList.add(remaining.get(0));
        subset_interviewBit(tmpList, remaining.subList(1,remaining.size()));
        subset_interviewBit(soFar, remaining.subList(1,remaining.size()));
    }

    // Lexicographically sorted data structure
    private static Set<List<Integer>> sortedSet = new TreeSet<>((o1, o2) -> {
        final String[] oArr = new String[2]; oArr[0] = ""; oArr[1] = "";
        o1.stream().forEach(x -> oArr[0] += x);
        o2.stream().forEach(x -> oArr[1] += x);
        int cmp = oArr[0].compareTo(oArr[1]);
        return cmp;
    });


    /**
     * Given two integers n and k, return all possible combinations of k numbers out of 1 2 3 ... n.
     * Make sure the combinations are sorted.
     *
     * https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
     *
     *                "", 1234
     *                /   |
     *             1,234  "",234
     *           /    |      |
     *         12,34  1,34  2,34
     *       /    |    |     |
     *    123,4  124  134   234
     */
    public static void kLetterCombinations(String soFar, String remain, int len) {
        // base case
        if (soFar.length() == len) {
            System.out.println(soFar);
            return;
        }

        if (remain.isEmpty()) return;

        // recursive case - include / exclude '1'
        kLetterCombinations(soFar+remain.charAt(0), remain.substring(1), len);
        kLetterCombinations(soFar, remain.substring(1), len);
    }

}
