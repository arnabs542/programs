package com.raj.dp;

import java.util.Arrays;

public class DiceThrowSum {
    /**
     * Given K dices, find the number of ways to get a given sum T. T is the sum of vals on each face when all the dice are thrown.
     *
     * Follow-up: Generalize it for m faces instead of standard 6 faces.
     *
     * Input: dices = 2, sum = 4
     * Output : 3
     * Ways to reach sum equal to 4 in 2 dices can be { (1, 3), (2, 2), (3, 1) }
     */
    public static void main(String[] args) {
        System.out.println(rec_memo(4,2));
        System.out.println(rec_memo(8,2));

        System.out.println(dp(4,2));
        System.out.println(dp(8,2));
    }

    /**
     * Other eg.
     * T=1,K=1 => (1) => 1
     * T=2,K=1 => (2) => 1
     *
     * -- base cond --
     * T=0,K=0  => 1 (reached answer / is empty set)
     * negative scenarios when it's not possible:
     * T=7,K=1  => 0
     * T=6,K=8  => 0
     * T=0,K=1  => 0
     *
     *         T=4,K=2
     *         / / \ \
     *       for each dice num 1...6, find ways rec(T-i, K-1) => add them up
     *       Memoize results
     *
     * Time/Space = O(target * dices)
     */
    static int[][] memo;
    static int rec_memo(int target, int dices) {
        memo = new int[target+1][dices+1];
        for (int i = 0; i < memo.length; i++) {
            Arrays.fill(memo[i], -1);
        }
        return rec_memo_helper(target, dices, memo);
    }

    static int rec_memo_helper(int target, int dices, int[][] memo) {
        // base cases
        if (target == 0 && dices == 0) return 1; // valid empty set or we reached a valid soln
        if (target < 0 || dices <= 0) return 0;  // no valid path if -ve target & dices exhaust

        // return memoized results if available
        if (memo[target][dices] != -1) return memo[target][dices];

        // recursive cases
        int num_ways = 0;
        for (int i = 1; i <= 6; i++) {  // just replace 6 by 'm' faces, for generalization
            num_ways += rec_memo_helper(target-i, dices-1, memo);
        }
        memo[target][dices] = num_ways;
        return num_ways;
    }

    /**
     * https://www.geeksforgeeks.org/dice-throw-dp-30/
     * Table:
     *          0 1 2 3 4 5 6 7  --> target
     *        0 1 0 0 0 0 0 0 0
     *        1 0 1 1 1 1 1 1 1
     *        2 0 0 1 2 3 4 5 6
     * Follows the above rec formula:
     * dp[dices][target] = sum of dp[dices-1][target-k] ... for all k=1 to 6 provided k<=target. Why? Roll 1 Dice u get 1...6 & find pre-computed answer dp[dices-1][target-k]
     *
     * Time/Space = O(target * dices)   ... we can ignore dices faces 1...6 as it is constant
     */
    static int dp(int target, int dices) {
        int[][] dp = new int[dices+1][target+1];   // NOTE: dices is row & target is column
        dp[0][0] = 1; //empty set

        for (int i = 1; i <= dices; i++) {
            for (int j = 1; j <= target; j++) {
                for (int k = 1; k <= 6 && k <= j; k++) {   // substitute 6 by 'm' for general soln using 'm' dice faces
                    dp[i][j] += dp[i-1][j-k];
                }
            }
        }
        System.out.println(Arrays.deepToString(dp));
        return dp[dices][target];
    }

}
