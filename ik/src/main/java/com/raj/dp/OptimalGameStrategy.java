package com.raj.dp;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class OptimalGameStrategy {

    /**
     * Optimal Strategy for a Game
     *
     * Consider a row of n coins of values v1, . . ., vn. We play a game against an opponent by alternating turns.
     * In each turn, a player selects either the first or last coin from the row, removes it from the row permanently,
     * and receives the value of the coin. Determine the maximum possible amount of money we can definitely win if we move first.
     * Assume full competency by both players.
     *
     * v = [8, 15, 3, 7]
     * o/p => Best=22 (7+15) , other alternative = 15 (8+7) (if he was greedy)
     * 23 (15+8), if opponent was dumb which isn't the case here - we assume opponent always maximizes his chance
     */
    public static void main(String[] args) {
        System.out.println(rec_dumb(new int[]{8,15,3,7}));
        System.out.println(rec(new int[]{8,15,3,7}, 0, 3));
        System.out.println(rec_memo(new int[]{8,15,3,7}, new int[4][4],0, 3));
        System.out.println(dp(new int[]{8,15,3,7}));
    }

    static int rec_dumb(int[] A) {
        return rec_dumb(A, 0, A.length-1, 0);
    }

    // WRONG ! Just gives the max of all possible moves. Doesn't take into account other player's competency
    static int rec_dumb(int[] A, int i, int j, int value) {
        if (i >= j) return value;
        return max(new int[]{
                rec_dumb(A, i+2, j, value+A[i]),
                rec_dumb(A, i+1, j-1, value+A[i]),
                rec_dumb(A, i, j-2, value+A[j]),
                rec_dumb(A, i+1, j-1, value+A[j])
        });
    }

    static int max(int[] vals) {
        int max = vals[0];
        for (int n : vals) max = Math.max(max, n);
        return max;
    }

    /**
     * https://www.youtube.com/watch?v=3hNuefaICxw
     * "The best chance we have, to maximize our money - with the only guarantee we can issue is that we can achieve min
     * of these 2 values, as we don't have control on what choice the opponent makes & if opponent is smart, then he'll
     * choose such that it minimizes the value we can receive.
     * V  =            8,15,3,7
     *           (7)8,15,3    (8)15,3,7
     *         (7)15,3(8)      (8)3,7(15)
     *        (22)3(8)          (15)3(15)
     *       (22)(11)-> win      (15)(18) -> loss
     *
     *               A picks Vi                     A picks Vj
     *  Vi,j = max { Vi + min{V(i+2,j), V(i+1,j-1)}, Vj + min{V(i,j-2), V(i+1,j-1)} }
     *  ==> opponent will pick such that we get minimum, but we'll try to maximize with what we are left with
     */
    static int rec(int[] V, int i, int j) {
        if (i == j) return V[i];
        if (j == i+1) return Math.max(V[i], V[j]);

        return Math.max(
                V[i] + Math.min(rec(V, i+2, j), rec(V, i+1, j-1)), // A picks Vi then B has (i+2,j) & (i+1,j-1) options
                V[j] + Math.min(rec(V, i, j-2), rec(V, i+1, j-1))  // A picks Vj then B has (i,j-2) & (i+1,j-1) options
        );
    }

    /**
     * Memoized sub-problems - top-down
     * Space Complexity: O(n^2)
     * Time Complexity: O(n^2)
     */
    static int rec_memo(int[] V, int[][] memo, int i, int j) {
        // Base cases
        if (i == j) return V[i];    // we reached end, return A's amount
        if (j == i+1) return Math.max(V[i], V[j]);  // when we have 2 remaining, A pick's max
        if (memo[i][j] != 0) return memo[i][j]; // already computed, return it

        memo[i][j] = Math.max(
                V[i] + Math.min(rec_memo(V, memo, i+2, j), rec_memo(V, memo, i+1, j-1)),  // A picks Vi
                V[j] + Math.min(rec_memo(V, memo, i, j-2), rec_memo(V, memo, i+1, j-1))  // A picks Vj
        );
        System.out.println(Arrays.deepToString(memo));
        return memo[i][j];
    }

    // DP bottom-up. Time & space is same as above
    static int dp(int[] v) {
        int n = v.length;
        int[][] table = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= n - 1; j++) {
                if (i > j) {
                    continue;
                } else if (i == j) {
                    table[i][j] = v[i];
                } else if (j == i+1) {
                    table[i][j] = Math.max(v[i], v[j]);
                } else {
                    // if the opponent is incompetent then Player 1 will always get the max in each step
                    // table[i][j] = Math.max(v[i] + Math.max(table[i + 1][j - 1], table[i + 2][j]),
                    //                 v[j] + Math.max(table[i + 1][j - 1], j >= 2 ? table[i][j - 2] : 0));
                    // But the opponent is also competent. At each step each player will select a coin which maximizes his collection
                    // So, if Player 1 selects i th coin, the next coin he will select will be MIN of the next
                    // two choices as player 2 will make a selection which maximizes his collection
                    table[i][j] = Math.max(v[i] + Math.min(table[i + 1][j - 1], table[i + 2][j]),
                                           v[j] + Math.min(table[i + 1][j - 1], table[i][j - 1]));
                }
            }
        }
        System.out.println(Arrays.deepToString(table));
        return table[0][n - 1];
    }

}
