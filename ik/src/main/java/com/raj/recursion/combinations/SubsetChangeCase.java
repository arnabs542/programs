package com.raj.recursion.combinations;

public class SubsetChangeCase {

    /**
     * Permute a string by changing case.
     * Input : A1b2
     * Output : a1b2 A1b2 a1B2 A1B2
     */
    public static void main(String[] args) {
        subset_by_case("A1b2".toCharArray(), 0);
    }

    /**
     * O(2^n) runtime
     * O(log n) for recursion
     */
    static void subset_by_case(char[] A, int i) {  // stack isn't needed as long as we revert the state
        if (i == A.length) {
            System.out.println(new String(A));
            return;
        }

        // if digit just move forward
        if (Character.isDigit(A[i])) {
            subset_by_case(A, i+1);
            return;
        }

        // if char, then try both lower & upper case by mutating state and reverting after
        char tmp = A[i];
        A[i] = Character.toLowerCase(A[i]);
        subset_by_case(A, i + 1);
        A[i] = Character.toUpperCase(A[i]);
        subset_by_case(A, i + 1);

        // revert
        A[i] = tmp;
    }

    /**
     * https://www.geeksforgeeks.org/permute-string-changing-case/
     * Bitwise ops - Uses no extra space - slightly complex
     */

}
