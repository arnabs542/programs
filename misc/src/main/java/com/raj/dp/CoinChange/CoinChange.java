package com.raj.dp.CoinChange;

/**
 * @author rshekh1
 */
public class CoinChange {

    static int totalAmount = 4;

    public static void main(String[] args) {
        System.out.println("Total ways = " + coinChange_rec("", 0, "123"));
        System.out.println("\nTotal ways DP = " + coinChangeDP(new int[]{1,2,3}));
    }

    /**
     * 123 -> {1,1,1,1}, {1,1,2}, {1,3}, {2,2}, {3,1}  note {1,3} is same as {3,1}
     * O(2^n) exponential
     */
    static int coinChange_rec(String soFar, int valSoFar, String remain) {
        if (valSoFar == totalAmount) {
            System.out.println(soFar.replaceFirst(",",""));
            return 1;
        }

        if (valSoFar > totalAmount || remain.isEmpty()) return 0;

        return coinChange_rec(soFar+","+remain.charAt(0),
                valSoFar+Integer.parseInt(remain.charAt(0)+""), remain) +  // include coin
                coinChange_rec(soFar, valSoFar, remain.substring(1));   // exclude coin
    }

    /**
     * Alternate way using 1D DP array: https://www.geeksforgeeks.org/understanding-the-coin-change-problem-with-dynamic-programming/
     *
     *                      0   1   2   3   4   => amount
     *       coins(down) ----------------------     pad 0th row & 0th col
     *              0  |    1   0   0   0   0
     *  coins[0] =  1  |    1   1   1   1   1
     *  coins[1] =  2  |    1   1   2   2   3
     *  coins[2] =  3  |    1   1   2   3   4
     *
     *  O(coins_length x total_amount)
     */
    static int coinChangeDP(int[] coins) {
        int[][] ways = new int[coins.length + 1][totalAmount + 1];

        // solve trivial cases
        // ways to obtain 0 amount using zero,one or more coins is 1 which is to not pick any coin at all
        for (int i = 0; i <= coins.length; i++) ways[i][0] = 1;

        // can't achieve amt greater than 0 using 0 coins
        for (int i = 1; i <= totalAmount; i++) ways[0][i] = 0;

        for (int coin = 1; coin <= coins.length; coin++) {
            for (int amt = 1; amt <= totalAmount; amt++) {
                // Remember we padded 0th coin for solving trivial cases, but the coin array starts from 0(for which the coin value is 1)
                int coinIdx = coin - 1;
                if (coins[coinIdx] <= amt)
                    ways[coin][amt] = ways[coinIdx][amt] + ways[coin][amt - coins[coinIdx]];
                else ways[coin][amt] = ways[coinIdx][amt];
            }
        }
        return ways[coins.length][totalAmount];
    }

}
