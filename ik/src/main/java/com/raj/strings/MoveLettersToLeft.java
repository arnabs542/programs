package com.raj.strings;

public class MoveLettersToLeft {

    /**
     * Move letters to left w/ minimum writes.
     *
     * "0a193zbr" => "azbr3zbr"
     * In the given string letters are a, z, b and r. We can move all four letters to left side with minimum 4 writes
     * and get string "azbr3zbr". For any other string it will take more than four write operations so "azbr3zbr" is the ans.
     */
    public static void main(String[] args) {
        System.out.println(move_letters_to_left_side_with_minimizing_memory_writes("0a193zbr"));
    }

    static String move_letters_to_left_side_with_minimizing_memory_writes(String s) {
        if (s == null || s.length() < 2) return s;
        /**
         * Algo - 2 ptrs Lomuto's partitioning linear solve:
         * Init 2 ptrs i, j as -1. Move j left to right.
         * Once j finds a char, incr i and write s[j]
         */
        char[] A = s.toCharArray();
        int i = -1;
        for (int j=0; j<s.length(); j++) {
            if (Character.isDigit(A[j])) continue;
            A[++i] = A[j]; // write to j into ith index
        }
        return new String(A);
    }

}
