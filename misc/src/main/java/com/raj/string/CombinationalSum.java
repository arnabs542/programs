package com.raj.string;

/**
 * @author rshekh1
 */
public class CombinationalSum {


    public static void main(String[] args) {
        cSum("", "127615", 0 , 8);
    }

    /**
     * Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
     * Each number in C may only be used once in the combination.
     *
     * Note:
     * All numbers (including target) will be positive integers.
     * Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
     * The solution set must not contain duplicate combinations.
     *
     * Example :
     *
     * Given candidate set 1,2,7,6,1,5 and target 8,
     *
     * A solution set is:
     *
     * [1, 7]
     * [1, 2, 5]
     * [2, 6]
     * [1, 1, 6]
     */
    public static void cSum(String soFar, String remain, int total, int target) {

        if (total >= target || remain.isEmpty()) {
            if (total == target) System.out.println(soFar);
            return;
        }

        char current = remain.charAt(0);
        cSum(soFar + current, remain.substring(1), total + Integer.parseInt(current+""), target);
        cSum(soFar, remain.substring(1), total, target);
    }
}
