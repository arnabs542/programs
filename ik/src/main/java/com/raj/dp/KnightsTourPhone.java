package com.raj.dp;

import java.util.Arrays;
import java.util.List;

/**
 * @author rshekh1
 */
public class KnightsTourPhone {

    /**
     * Knight's tour!
     * Given a phone keypad as shown below:
     * 1 2 3
     * 4 5 6
     * 7 8 9
     * - 0 -
     *
     * How many different phone numbers of given length can be formed starting from the given digit?
     * The constraint is that the movement from one digit to the next is similar to the movement of the Knight in a chess game.
     *
     * For eg. if we are at 1 then the next digit can be either 6 or 8 if we are at 6 then the next digit can be 1, 7 or 0.
     * Repetition of digits are allowed - 1616161616 is a valid number.
     *
     * Sample Input 1:
     * startdigit = 1, phonenumberlength = 2
     * Output = 2
     * Two possible numbers of length 2: 16, 18
     *
     * Sample Input 2:
     * startdigit = 1, phonenumberlength = 3
     * Output 2 = 5
     * Possible numbers of length 3: 160, 161, 167, 181, 183
     */
    static int[][] keypad = {
            {1,2,3},
            {4,5,6},
            {7,8,9},
            {-1,0,-1}
    };

    static List<Integer> getNeighbors(int n) {
        if (n == 1) return Arrays.asList(6,8);
        if (n == 2) return Arrays.asList(7,9);
        if (n == 3) return Arrays.asList(4,8);
        if (n == 4) return Arrays.asList(3,9,0);
        if (n == 5) return Arrays.asList();
        if (n == 6) return Arrays.asList(7,1,0);
        if (n == 7) return Arrays.asList(2,6);
        if (n == 8) return Arrays.asList(1,3);
        if (n == 9) return Arrays.asList(4,2);
        if (n == 0) return Arrays.asList(4,6);
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Count = " + rec(1,2, "1"));
        System.out.println("Count = " + rec(1,3, "1"));
        System.out.println("Count = " + rec(1,4, "1"));

        System.out.println("Count dp = " + dp(1,2));
        System.out.println("Count dp = " + dp(1,3));
        System.out.println("Count dp = " + dp(1,4));
    }

    /**
     *              f(1,3)
     *              /   \
     *          6,2      8,2        ... branching factor 2 or 3 worst case
     *         / | \     / \
     *      7,1 1,1 0,1 .....
     *
     * Recurrence => f(s,dig) = f(w1,dig-1) + f(w2,dig-1) ...
     * Time = O(2^digits)
     */
    static int rec(int start, int digits, String soFar) {
        // base cases
        if (digits == 1) {
            System.out.print(soFar + " ");
            return 1;  // ==1 to compensate for rec(1,2) -> rec(1,1) --> X
        }

        int count = 0;
        for (int n : getNeighbors(start)) {
            count += rec(n, digits - 1, soFar + n);
        }
        return count;
    }

    /**
     * From recursive solution above, Recurrence => f(s,dig) = f(w1,dig-1) + f(w2,dig-1) ...
     * digits =>     0  1  2  3  4
     *           0   0  1  2  6  .
     * start |   1   0  1  2  sum of dp(neighbors,dig-1) = dp[6][dig-1] + dp[8][dig-1] = 5
     *       v   2   0  1  2  .
     *           3   0  1  2  .
     *           4   0  1  3
     *           5   0  1  0
     *           6   0  1  3
     *           7   0  1  2
     *           8   0  1  2
     *           9   0  1  2
     *
     * Time / Space = O(10*num digits) = O(num digits) as the keypad num is constant
     */
    static int dp(int start, int digits) {
        int[][] dp = new int[10][digits+1];  // 10 rows for keys & say 3 rows for digits

        // base cases - with 0 digit dp is 0, with 1 digits dp is 1
        for (int key = 0; key <= 9; key++) {
            dp[key][1] = 1;
        }

        // NOTE - we need to first compute digit i, then i+1 as i+1th depends on ith, hence outer loop (even though dp row is key)
        for (int dig = 2; dig <= digits; dig++) { // digits
            for (int key = 0; key <= 9; key++) {  // keypad num
                // dp(i,j) is sum of neighbors for digit-1
                for (int w : getNeighbors(key)) {
                    dp[key][dig] += dp[w][dig-1];
                }
            }
        }
        return dp[start][digits];
    }

}
