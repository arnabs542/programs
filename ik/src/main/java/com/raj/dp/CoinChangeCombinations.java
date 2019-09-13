package com.raj.dp;

import java.util.Stack;

/**
 * @author rshekh1
 */
public class CoinChangeCombinations {

    /**
     * You are given coins of different denominations and a total amount of money. Write a function to compute the
     * number of combinations that make up that amount. You may assume that you have infinite number of each kind of coin.
     * The order of coins doesn't matter.
     * Input: amount = 5, coins = [1, 2, 5]
     * Output: 4
     * Explanation: there are four ways to make up the amount:
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     */
    public static void main(String[] args) {
        // Wrong !! gives all possible ways of getting to amount (permutations - order matters - arrangements)
        System.out.println("Total count => " + rec_permute(new int[]{1,2,5},5, new Stack()));
        // Correct - we need combinations where order doesn't matter, hence apply inclusion/exclusion principle
        System.out.println("Total count => " + rec_combinations(new int[]{1,2,5},0, 5, new Stack()));
    }

    // Wrong !! gives all possible ways of getting to amount (permutations - order matters - arrangements)
    static int rec_permute(int[] coins, int amt, Stack soFar) {
        if (amt == 0) {
            System.out.println(soFar);
            return 1;
        }
        if (amt < 0) return 0;
        int count = 0;
        for (int c : coins) {
            if (amt >= c) {
                soFar.push(c);
                count += rec_permute(coins, amt-c, soFar);
                soFar.pop();
            }
        }
        return count;
    }

    // Correct - we need combinations where order doesn't matter, hence apply inclusion/exclusion principle
    static int rec_combinations(int[] coins, int i, int amt, Stack soFar) {
        if (amt == 0) {
            System.out.println(soFar);
            return 1;
        }
        if (amt < 0 || i >= coins.length) return 0;

        soFar.push(coins[i]);
        int include = rec_combinations(coins, i+1, amt-coins[i], soFar);    // consider ith coin
        soFar.pop();
        int exclude = rec_combinations(coins, i+1, amt, soFar);  // don't consider ith coin
        return include + exclude;
    }

}
