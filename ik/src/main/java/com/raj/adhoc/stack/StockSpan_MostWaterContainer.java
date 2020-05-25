package com.raj.adhoc.stack;

import java.util.Arrays;
import java.util.Stack;

public class StockSpan_MostWaterContainer {
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

        System.out.println(mostWaterContainer(new int[]{1,8,6,2,5,4,8,3,7}));
        System.out.println(mostWaterContainer_2ptr(new int[]{1,8,6,2,5,4,8,3,7}));
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

    /**
     * Another variation of this problem - Find the container w/ most water
     * https://www.geeksforgeeks.org/container-with-most-water/
     * Idea is same as above. Here we compute water each bar will hold & try to maximize it
     *
     *         0 1 2 3 4 5 6 7 8
     * Input: [1,8,6,2,5,4,8,3,7]
     * Output: 49
     *
     * 1
     * 8 : 8>1, remove 1 as it's of no more reqd
     * 6 : add it
     * 2 : add
     * 5 : 5>2, so remove 2 & compute area as it's height * diff of top of stack's index & itself after removing
     * 4 : add   stack = [8,6,5,4]
     * 8 : 8>4,5,6 hence remove them,  stack = [8,8]
     * 3 : add
     * 7 : add,  stack = [8(1),8(6),7(8)] => remove dupe 8 => [8(1),7(8)]
     * empty stack and update max area for each bar removed
     * edge case - as there are two 8's, just keep the earliest one as ot maximizes the area
     *
     * Time/Space = O(n)
     * A more elegant & space efficient solution is described below
     */
    static int mostWaterContainer(int[] A) {
        int max_area = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for (int i = 1; i < A.length; i++) {
            while (!stack.isEmpty() && A[i] > A[stack.peek()]) {  // smaller height is no more of interest, remove it
                stack.pop();
            }
            if (!stack.isEmpty()) {
                int new_area = A[i] * (i - stack.peek());
                max_area = Math.max(max_area, new_area);

            }
            // remove dupe eg. just keep the earlier 8
            if (stack.isEmpty() || A[stack.peek()] != A[i]) stack.push(i);
        }

        System.out.println(stack);
        // empty stack
        while (!stack.isEmpty()) {
            int i = stack.pop();
            if (!stack.isEmpty()) {
                int new_area = A[i] * (i - stack.peek());
                max_area = Math.max(max_area, new_area);
            }
        }
        return max_area;
    }

    /**
     * More elegant & space efficient solution - [Greedy]
     * Just keep left & right ptrs and compute area with min height of two.
     * Incr left if left is smaller of two bars, else decr right
     * Time = O(n), no aux space
     *
     * @see com.raj.adhoc.TrapRainWater - It differs from this in the sense the bars have width 1, thereby water for each bar may be different
     */
    static int mostWaterContainer_2ptr(int[] A) {
        int left = 0, right = A.length-1;
        int max = 0;
        while (left < right) {
            // compute area with left & right bars
            int new_area = Math.min(A[left], A[right]) * (right - left);
            max = Math.max(max, new_area);

            // greedily try to maximize next iteration by advancing away from a smaller height bar
            if (A[left] < A[right]) left++;
            else right--;
        }
        return max;
    }

}
