package com.raj.dp;

/**
 * @author rshekh1
 */
public class MaxRobbery {

    /**
     * There are n houses built in a line, each of which contains some value in it. A thief is going to steal the
     * maximal value in these houses, but he cannot steal in two adjacent houses because the owner of a stolen house
     * will tell his two neighbors on the left and right side. What is the maximal stolen value?
     *
     * Input 1:
     * For example, if there are four houses with values [6, 1, 2, 7], the maximal stolen value is 13,
     * when the first and fourth houses are stolen.
     *
     * # Basically, we have an option at each house, either steal this house (only if he hasn't the prev) or skip this.
     * # [subset pattern] max(incl option, excl option)
     *
     *  values =>   0  6  1  2  7
     * maxProfit=>  0  6  6  8  13
     *
     * Input 2:
     * values = [1, 2, 4, 5, 1] => 7  (2+5)
     *
     *  values =>   0  1  2  4  5  1
     * maxProfit=>  0  1  2  5  7  7
     *
     * DP formula:
     * dp[i] = Math.max(dp[i-2] + values[i-1], dp[i-1])
     */
    public static void main(String[] args) {
        System.out.println(maxStolenValue(new int[] {6,1,2,7}));
        System.out.println(maxStolenValue(new int[] {1,2,4,5,1}));
    }

    /**
     * Space/Time = O(n)
     */
    static int maxStolenValue(int[] values) {
        int[] dp = new int[values.length+1];
        dp[0] = 0;
        dp[1] = values[0];

        for (int i=2; i<=values.length; i++) {
            // incl = steal this house then take max loot from 2 house before
            // excl = don't steal, then take max loot from prev house
            dp[i] = Math.max(
                    dp[i-2] + values[i-1],   // incl
                    dp[i-1]                  // excl
            );
        }
        return dp[values.length];
    }

}
