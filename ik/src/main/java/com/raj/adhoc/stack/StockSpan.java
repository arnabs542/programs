package com.raj.adhoc.stack;

import java.util.Arrays;
import java.util.Stack;

public class StockSpan {
    /**
     * The stock span problem is a financial problem where we have a series of n daily price quotes for a stock and we
     * need to calculate span of stock’s price for all n days.
     * The span Si of the stock’s price on a given day i is defined as the maximum number of consecutive days just
     * before the given day, for which the price of the stock on the current day is less than or equal to its price on
     * the given day.
     * Input =  {100, 80, 60, 70, 60, 75, 85}
     * Output = {1,   1,  1,  2,  1,  4,  6 }
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(stockSpan_brute(new int[]{100, 80, 60, 70, 60, 75, 85})));
        System.out.println(Arrays.toString(stockSpan(new int[]{100, 80, 60, 70, 60, 75, 85})));
    }

    /**
     * Brute: O(n^2)
     * Fix a day, for each preceeding day compute how many are smaller
     */
    static int[] stockSpan_brute(int[] A) {
        int[] span = new int[A.length];
        span[0] = 1;
        for (int i = 1; i < A.length; i++) {
            span[i] = 1;
            for (int j = i-1; j >= 0; j--) {
                if (A[j] > A[i]) break;
                span[i]++;
            }
        }
        return span;
    }

    /**
     * https://www.geeksforgeeks.org/the-stock-span-problem/
     * [Stack Based Pattern - keep only what's reqd]
     * To optimize, we need to get the num days which are lesser than current in constant time.
     * To achieve that we need to keep trimming the contents of our DS.
     * Since we need to look back starting from top, we use stack.
     * How do we trim? If we just store index, we can cull prices which we know won't affect the computation of results for further days.
     * 100   80   60   70
     *  1    1    1    2
     *  (decreasing)   x -> Increase from prev, we can remove all elem which are less than 70 as further elems have to be more than this to make for count increase
     *   keep them
     *   as later prices
     *   can be higher than any of them
     *
     *  0  1  3  4  5
     * 100 80 70 60 75
     *               x -> remove all elem lesser than 75
     * 100 80 75 => 5-top = 5-1 = 4
     *
     * Time/Space = O(n)
     */
    static int[] stockSpan(int[] A) {
        int[] span = new int[A.length];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        span[0] = 1;

        for (int i = 1; i < A.length; i++) {
            while (!stack.isEmpty() && A[i] > A[stack.peek()]) {  // cull elems lesser than curr Ai
                stack.pop();
            }
            span[i] = stack.isEmpty() ? i + 1 : i - stack.peek(); // compute span which is i-top. edge case - stack empty? it means i is greater than all
            stack.push(i);    // push i after computing span
        }
        return span;
    }

}
