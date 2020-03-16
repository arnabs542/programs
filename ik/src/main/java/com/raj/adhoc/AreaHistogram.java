package com.raj.adhoc;

import java.util.Stack;

/**
 * @author rshekh1
 */
public class AreaHistogram {

    public static void main(String[] args) {
        System.out.println(findMaxPossibleArea_BruteForce(new long[]{6,2,5,4,5,1,6}, 0 ,6));
        System.out.println(findMaxPossibleArea(new long[]{6,2,5,4,5,1,6}, 0 ,6));
    }

    /**
     * *********************** PROBLEM DESCRIPTION ***************************
     * Find the largest rectangular area possible in a given histogram, where
     * the largest rectangle can be made of a number of contiguous bars.
     * For simplicity, assume that all bars have same width and the width is 1 unit.
     * You will be given an array arr of height of bars of size n.
     */

    /**
     * Brute Force - O(n^2) - idea is to compute area using bars where the max area wud be the min height of bar * num_bars for each i=0 to n bars
     *
     * iter from bar i = 0 to n
     *   maxPossibleHeight = 0
     *   for each bar i, iter j = i to n
     *     maxPossibleHeight = min of bar height so far
     *     compute area so far using maxPossibleHeight
     *     update maxArea
     */
    static long findMaxPossibleArea_BruteForce(long[] A, int l, int r) {
        long maxArea = 0;
        for (int i = 0; i < A.length; i++) {  // what's our start bar
            long maxPossibleHeight = Integer.MAX_VALUE;
            for (int j = i; j < A.length; j++) {  // start bar to nth bar
                maxPossibleHeight = Math.min(A[j], maxPossibleHeight); // update bar height, which is min seen so far
                long area = maxPossibleHeight * (j - i + 1);
                maxArea = Math.max(area, maxArea);
            }
        }
        return maxArea;
    }

    /**
     * Time complexity: O(n)
     * Space complexity: O(n)
     * https://www.youtube.com/watch?v=MhQPpAoZbMc
     *
     * Idea is to find the left & right boundary for a bar in constant time so that we can do O(n)
     * Keep adding bars in increasing order to stack until we hit a bar of lower height (signal to start computing some areas)
     *   - if a bar is lesser height, we'll need to cull stack contents so that we achieve the increasing order
     *   - so start popping until we hit a bar which is of lesser height
     *   - compute area after each pop = popped bar height * width (i being our right bound * left bound being new top elem)
     *   - edge cases :
     *     - if stack is empty, just compute area with i
     *     - if stack has content after we iterate through all bars, just pop as above logic
     */
    static long findMaxPossibleArea(long[] A, int l, int r) {
        Stack<Integer> stack = new Stack<>();  // just maintain array indices for convenience
        long maxArea = 0; int i = l;

        while (i <= r) {
            System.out.println("---> ArrIdx Stack = " + stack);
            // push if stack empty or bar has more height
            if (stack.isEmpty() || A[i] >= A[stack.peek()]) {
                System.out.println("Pushed bar " + i);
                stack.push(i++);
            } else {
                // ith bar is of lower height, (it becomes our right boundary) pop it!
                System.out.println("Bar " + i + " is smaller than top " + stack.peek() + " popping top...");
                int top = stack.pop();

                // now the current top represents our left bound, compute area = top bar height * (right - left bounds)
                long area_with_top = computeArea(A, stack, top, l, i);
                maxArea = Math.max(maxArea, area_with_top);
            }
        }

        // leftover elems, just apply the popping logic as above
        while (!stack.isEmpty()) {
            System.out.println("---> ArrIdx Stack = " + stack);
            System.out.println("Popping remaining stack bar " + stack.peek());
            int top = stack.pop();
            long area_with_top = computeArea(A, stack, top, l, i);
            maxArea = Math.max(maxArea, area_with_top);
        }

        return maxArea;
    }

    private static long computeArea(long[] A, Stack<Integer> stack, int top, int l, int i) {
        // now the current top represents our left bound
        long area_with_top;
        if (stack.isEmpty()) {  // left bound is l when stack is empty
            area_with_top = A[top] * 1l * (i-l);
            System.out.println("  area_with_top = " + A[top] + "*(" + i + "-" + l + ") = " + area_with_top);
        } else {    // left bound is new stack top, deduct -1 for i being one bar forward for right bound
            area_with_top = A[top] * 1l * ((i-1) - stack.peek());
            System.out.println("  area_with_top = " + A[top] + "*(" + i + "-1-" + stack.peek() + ") = " + area_with_top);
        }
        System.out.println("  area_with_top = " + area_with_top);
        return area_with_top;
    }

}


