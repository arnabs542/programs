package com.raj.dp;

/**
 * @author rshekh1
 */
public class RodCutMaxProfit {

    public static void main(String[] args) {
        int arr[] = new int[] {1, 5, 8, 9, 10, 17, 17, 20};
        int size = arr.length;
        System.out.println("Maximum Obtainable Value is "+ cutRod(arr, size));
    }

    /**
     * https://www.geeksforgeeks.org/cutting-a-rod-dp-13/
     * Given a rod of length n inches and an array of prices that contains prices of all pieces of size smaller than n.
     * Determine the maximum value obtainable by cutting up the rod and selling the pieces.
     * For example, if length of the rod is 8 and the values of different pieces are given as following,
     * then the maximum obtainable value is 22 (by cutting in two pieces of lengths 2 and 6)
     *
     * length   | 1   2   3   4   5   6   7   8
     * --------------------------------------------
     * price    | 1   5   8   9  10  17  17  20
     *
     * And if the prices are as following, then the maximum obtainable value is 24 (by cutting in eight pieces
     * of length 1)
     * length   | 1   2   3   4   5   6   7   8
     * --------------------------------------------
     * price    | 3   5   8   9  10  17  17  20
     */

    static int cutRod(int[] price, int n) {
        if (n <= 0) return 0;
        int maxVal = Integer.MIN_VALUE;

        // Recursively cut the rod in different pieces and compare different configurations
        for (int i = 0; i < n; i++) {
            maxVal = Math.max(maxVal, price[i] + cutRod(price, n-i-1));
        }
        return maxVal;
    }

}
