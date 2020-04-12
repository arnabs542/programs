package com.raj.recursion.combinations;

import java.util.Stack;

public class SubsetChangeCase {

    /**
     * Permute a string by changing case.
     * Input : ABC
     * Output : abc Abc aBc ABc abC AbC aBC ABC
     */
    public static void main(String[] args) {
        subset_by_case("ABC".toCharArray(), 0, new Stack<>());
    }

    /**
     * O(2^n) runtime
     * O(n) space for stack
     */
    static void subset_by_case(char[] A, int i, Stack<Character> stack) {
        if (i == A.length) {
            StringBuilder sb = new StringBuilder();
            for (char c:stack) sb.append(c);
            System.out.println(sb.toString());
            return;
        }

        // exclude i - replace it with a lower case
        stack.push(Character.toLowerCase(A[i]));
        subset_by_case(A, i+1, stack);
        stack.pop();

        // include i - keep the upper char
        stack.push(A[i]);
        subset_by_case(A, i+1, stack);
        stack.pop();
    }

    /**
     * https://www.geeksforgeeks.org/permute-string-changing-case/
     * Bitwise ops - Uses no extra space - slightly complex
     */

}
