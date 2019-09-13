package com.raj.dp;

/**
 * @author rshekh1
 */
public class CoinPlayMax {

    /**
     * Consider a row of n coins of values v1, . . ., vn. We play a game against an opponent by alternating turns.
     * In each turn, a player selects either the first or last coin from the row, removes it from the row permanently,
     * and receives the value of the coin. Determine the maximum possible amount of money we can definitely win if we move first.
     * Assume full competency by both players.
     *
     * v = [8, 15, 3, 7]
     * o/p => 22 (7+15) , other alternative = 8+7 (if he was greedy)
     */
    public static void main(String[] args) {
        System.out.println(rec(new int[]{8,15,3,7}));
    }

    static int rec(int[] A) {
        return rec(A, 0, A.length-1, 0);
    }

    static int rec(int[] A, int i, int j, int value) {
        if (i >= j) return value;
        return max(new int[]{
                rec(A, i+2, j, value+A[i]),
                rec(A, i+1, j-1, value+A[i]),
                rec(A, i, j-2, value+A[j]),
                rec(A, i+1, j-1, value+A[j])
        });
    }

    static int max(int[] vals) {
        int max = vals[0];
        for (int n : vals) max = Math.max(max, n);
        return max;
    }

    static int dp() {
        return 0;
    }

}
