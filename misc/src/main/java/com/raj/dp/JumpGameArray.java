package com.raj.dp;

import java.util.Stack;

/**
 * @author rshekh1
 */
public class JumpGameArray {

    public static void main(String[] args) {
        System.out.println(canJump(new int[]{2,3,1,1,4}));
        System.out.println(canJump(new int[]{3,2,1,0,4}));

        System.out.println("\nMin Jumps Required = " + minJumps(new int[]{2,3,1,1,2,4,2,0,1,1}));
    }

    /**
     * Given an array of non-negative integers, you are initially positioned at the first index of the array.
     * Each element in the array represents your maximum jump length at that position.
     * Determine if you are able to reach the last index.
     *
     * For example:
     * A = [2,3,1,1,4] -> true
     * A = [3,2,1,0,4] -> false
     *
     * A = [1] -> true, [2,0] -> true, [3,2,0] -> true ...
     *
     *              2   3       1       1   4
     * maxJump      2   1,3=3   1,2=2   1   0,4=4
     */
    private static boolean canJump(int[] A) {
        int maxJumpAllowed = 0;
        for (int i = 0; i <= maxJumpAllowed; i++) {    // keep iterating until maxJumpAllowed
            if (maxJumpAllowed >= A.length-1) return true; // if maxJump already makes u reach dest, return true
            maxJumpAllowed = Math.max(maxJumpAllowed, i+A[i]);    // keep updating maxJumpAllowed, looking at this element
        }
        return false;
    }

    /**
     * Min Jumps Array
     * Given an array of non-negative integers, you are initially positioned at the first index of the array.
     * Each element in the array represents your maximum jump length at that position.
     * Your goal is to reach the last index in the minimum number of jumps.
     *
     * Example :
     * Given array A = [2,3,1,1,4]
     *
     * The minimum number of jumps to reach the last index is 2.
     * (Jump 1 step from index 0 to 1, then 3 steps to the last index)
     *
     * Algo DP:
     * Incrementally find solutions to jump 1 step at a time starting from 0th index:
     *
     *      https://www.youtube.com/watch?v=cETfFsSTGJI
     *      0   1   2   3   4   5   6   7   8   9   => array index
     *      2   3   1   1   2   4   2   0   1   1   => array elements
     *      0   1   1   2   2   3   3   4   4   4   => MinJump reqd to reach from start
     *      0   0   0   1   1   4   4   5   5   5   => Actual jump from index
     *                                                 9 came from 5 which came from 4, which came from 1 which came from 0
     *                                                 0,1,4,5,9
     * for i -> 1 to n
     *  min = 9999
     *  for j -> 1 to i
     *      if A[j] >= (i-j) min = Math.min(min, dp[j]+1)
     *  dp[i] = min
     *
     */
    private static int minJumps(int[] A) {
        int[] minJumps = new int[A.length];
        int[] actualJumps = new int[A.length];
        for (int i = 1; i < A.length; i++) {    // to block
            minJumps[i] = Integer.MAX_VALUE;    // init max
            for (int j = 0; j <= i; j++) {      // from block
                if (A[j] >= (i-j)) {
                    if (minJumps[j]+1 < minJumps[i]) actualJumps[i] = j;    // save the jump from block
                    minJumps[i] = Math.min(minJumps[i], minJumps[j]+1);     // update dp table for min jumps
                }
            }
        }

        // print jumps
        int i = actualJumps.length - 1;
        Stack<Integer> stack = new Stack<>();
        stack.push(i); // last block
        while (i > 0) {
            stack.push(actualJumps[i]);  // we jumped from here
            i = actualJumps[i];
        }
        System.out.println("Jump sequence: ");
        while (!stack.isEmpty()) System.out.print(stack.pop() + ",");

        return minJumps[A.length - 1];
    }

}
