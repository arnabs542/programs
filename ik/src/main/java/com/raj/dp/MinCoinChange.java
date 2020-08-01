package com.raj.dp;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class MinCoinChange {

    /**
     * Given coins k, find the Minimum number coins required to give change for amount A
     * A = 8
     * k = {2,3,5}
     * => 2 i.e. 3+5
     *
     * Some pointers:
     * # Will GREEDY Approach work here?
     *   As we are trying to minimize number of coin changes, we start with max coin until we use up A:
     *   8-5=3, 3-3=0 => answer = 3,5
     *   But it will fail for - A=10, k={1,5,7} => we get 3=>{7,1,1} while optimal solution is 2=>{5,5}
     *   Hence, Greedy won't work. We should prove this & then go for Recursion => Identifying Overlapping Sub-problems => DP
     *
     * # First try to solve it as number of ways to give changes amounting to 8.
     * Then think about minimizing the number of coins used.
     *
     */
    public static void main(String[] args) {
        Res res = new Res();
        int numWays = rec(8, res, new int[]{2,3,5});
        System.out.println("using rec, numWays = " + numWays + ", Min Number of coins required = " + res.min);

        System.out.println("min coins rec = " + minCoinChange_rec(8, new int[]{2,3,5}));
        System.out.println("min coins DP = " + minCoinChange_dp(8, new int[]{2,3,5}));
    }

    /**
     * Min num coins to reach A is min to reach (A - value of this coin) + 1 for this coin
     *                  f(A)
     *               2/  3|  5\     ... using k coins (branching factor)
     *               /    |    \
     * min (1  +   f6    f5    f3  )
     *            /|\   /|\   /|\
     *
     * Depth of recursion = A/kmin coin which is ~A
     * Time  = (num coins) ^ A/minCoin ... O(k^A)  ie. branching_factor ^ depth_rec_tree
     * Space = depth of recursion ie. A/minCoin ... O(A)
     *
     * With memoization ? In this case it may be better than DP as it may end up solving lesser subproblems & memoizing
     * them than DP which solves everything from 0 to A
     * See rec() for an easier solution
     */
    static int minCoinChange_rec(int A, int[] k) {
        if (A == 0) return 0; // reached goal, hence a possible solution
        if (A < 0) return Integer.MAX_VALUE; // not possible to provide change, hence force max value for this branch to prune

        int min = Integer.MAX_VALUE;    // or set it to some high value like 9999 to avoid tackling edge case
        for (int coin : k) {
            min = Math.min(min, minCoinChange_rec(A-coin, k));
        }
        // now add 1 for this coin
        if (min != Integer.MAX_VALUE) {     // edge case: to avoid integer overflow when 1 is added to MAX, it becomes MIN & throws off comparison
            min = min + 1;                  // hence add 1 later after asserting, it's not already infinity. Can be avoided by init max_value-buffer
        }
        return min;
    }

    /**
     * Alternative easier to understand solution using a stack to track coin changes so far
     */
    static class Res {
        Stack<Integer> stack = new Stack<>();
        int min = Integer.MAX_VALUE;
    }
    /*static int[] memo = new int[9];  // uncomment to use dp top down memoization
    static {
        Arrays.fill(memo, -1);
    }*/
    static int rec(int A, Res res, int[] coins) {
        if (A==0) {
            System.out.println(res.stack);
            res.min = Math.min(res.min, res.stack.size());
            return 1;
        }
        if (A<0) return 0;
        /*if (memo[A] != -1) {   // uncomment to use dp top down memoization
            return memo[A];
        }*/
        int cnt = 0;
        for (int c : coins) {
            res.stack.push(c);   // which coin was used?
            cnt += rec(A-c, res, coins); // update count coins used
            res.stack.pop();
        }
        //memo[A] = cnt;    // uncomment to use dp top down memoization
        return cnt;
    }

    /**
     * DP Formula => dp[a] stores the min num of coins reqd for reaching amt a. We init dp[a] as max.
     * dp[a] = min(dp[a], dp[a-k]+1) for k = 2,3  ... using coin 2 is 1+dp[a-k]
     *
     * Time = O(A*numCoins), Space = O(Amount)
     * Note - Memoized recursive soln might be better in terms of space & time as DP solves all sub-problems from 0 to A
     */
    static int minCoinChange_dp(int Amount, int[] coins) {
        int[] dp = new int[Amount+1]; // +1 for 0th base case

        //init array with max value-100 (as adding+1 overflows into -ve int values), so that we can minimize it later
        Arrays.fill(dp, Integer.MAX_VALUE-100);
        dp[0] = 0;  // base case i.e. reaching amount 0 with 0 coins is 0
        for (int amt = 1; amt <= Amount; amt++) {
            for (int coin : coins) {
                /**
                 * Minimize to reach A using k coins
                 *            A
                 *    2/            \3
                 *  1+dp[A-2]     1+dp[A-3]   ... minimum of these
                 */
                if (amt >= coin) {
                    dp[amt] = Math.min(dp[amt], 1 + dp[amt - coin]); // use this coin, hence +1 and remaining get it from dp[a-c]
                }
            }
        }
        System.out.println("DP table = " + Arrays.toString(dp));

        // what coins were used for min change? backtrack from dp[A] for DP table = [0, 2147483547, 1, 1, 2, 1, 2, 2, 2]
        System.out.print("Coins used for min change = ");
        int a = Amount;
        while (a > 0) {
            for (int c : coins) {
                if (dp[a] == (dp[a-c] + 1)) { // we reached dp[A] using 1 more coin than dp[A-c], so find that
                    System.out.print(c + ",");
                    a -= c;
                    break;
                }
            }
        }
        System.out.println();
        return dp[Amount];
    }

}
