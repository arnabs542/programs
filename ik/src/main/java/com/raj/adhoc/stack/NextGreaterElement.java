package com.raj.adhoc.stack;

import java.util.Arrays;
import java.util.Stack;

public class NextGreaterElement {
    /**
     * Given an array, print the Next Greater Element (NGE) for every element. The Next greater Element for an element
     * x is the first greater element on the right side of x in array. Elements for which no greater element exist,
     * consider next greater element as -1.
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(NGE_brute(new int[]{9,5,8,10})));
        System.out.println(Arrays.toString(NGE(new int[]{9,5,8,10})));
        System.out.println(Arrays.toString(NGE(new int[]{1,2,3,4,5,3,1,6,5,4})));
    }

    /**
     * Brute:
     * Fix i, for it go right and figure the next greater elem
     * O(n^2)
     */
    static int[] NGE_brute(int[] A) {
        int[] res = new int[A.length];
        Arrays.fill(res,-1);
        for (int i = 0; i < A.length; i++) {
            for (int j = i+1; j < A.length; j++) {
                if (A[j] > A[i]) {
                    res[i] = A[j];
                    break;
                }
            }
        }
        return res;
    }

    /**
     * [Stack pattern - keep only elems of interest]
     * Bottleneck is determining nge for each elem
     * 5,3,2,8 -> NGE for all lesser elems before 8 is 8
     * Idea is to keep elems in stack until we find a next greater elem. Pop them and assign this as nge.
     * Otherwise keep pushing to stack.
     */
    static int[] NGE(int[] A) {
        int[] res = new int[A.length];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        for (int i = 1; i < A.length; i++) {
            // we have next greater elem for elems in stack less than Ai
            while (!stack.isEmpty() && A[i] > A[stack.peek()]) {
                int idx = stack.pop();  // pop them & assign nge
                res[idx] = A[i];
            }
            stack.push(i);  // push new elem
        }

        while (!stack.isEmpty()) {  // pop leftovers and assign -1
            res[stack.pop()] = -1;
        }
        return res;
    }

}
