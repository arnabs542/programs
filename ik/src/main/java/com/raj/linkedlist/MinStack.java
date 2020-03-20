package com.raj.linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MinStack {

    /**
     * Implement A Min Stack
     * You have to build a min stack. Min stack should support push, pop methods (as usual stack) as well as one method that returns the minimum element in the entire stack.
     * You are given an integer array named operations of size n, containing values >= -1.
     * operations[i] = -1 means you have to perform a pop operation. The pop operation does not return the removed/popped element.
     * operations[i] = 0 means you need to find the minimum element in the entire stack and add it at the end of the array to be returned.
     * operations[i] >= 1 means you need to push operations[i] on the stack.
     *
     * Example-
     * Input: [10, 5, 0, -1, 0, -1, 0]
     * Output: [5, 10, -1]
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(min_stack(new int[] {10,5,0,-1,0,-1,0})));
        System.out.println(Arrays.toString(min_stack(new int[] {10,5,5,5,0,-1,0,-1,0,-1,0}))); // handle dupes
    }

    static int[] min_stack(int[] ops) {
        List<Integer> res = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        Stack<Min> min = new Stack<>();
        for (int n : ops) {
            //System.out.println(n + " ?");
            //System.out.println("stack = " + stack + ", min = " + min);
            if (n == 0) res.add(min.isEmpty() ? -1 : min.peek().val);
            else if (n == -1) {
                if (!min.isEmpty() && stack.peek() == min.peek().val) {
                    Min m = min.pop();
                    m.cnt--;
                    if (m.cnt > 0) min.add(m);
                }
                if (!stack.isEmpty()) stack.pop();
            } else {
                if (min.isEmpty() || n < min.peek().val) {
                    min.add(new Min(n));
                } else if (n == min.peek().val) {
                    Min m = min.pop();
                    m.cnt++;
                    min.add(m);
                }
                stack.add(n);
            }
        }
        return res.stream().mapToInt(i->i).toArray();
    }

    static class Min {
        int val;
        int cnt;    // tracks dupes
        Min(int _val) {
            val = _val;
            cnt = 1;
        }
    }

}
