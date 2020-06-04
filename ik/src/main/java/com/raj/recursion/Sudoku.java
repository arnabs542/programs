package com.raj.recursion;

import java.util.*;

/**
 */
public class Sudoku {

    /**
     * Solve a Sudoku board with partially filled numbers.
     * Each row can have exactly 1-9 numbers
     * Each col can have exactly 1-9 numbers
     * Each 3x3 matrix can have exactly 1-9 numbers
     */
    public static void main(String[] args) {
        int[][] A = new int[][] {
                {3,0,6,5,0,8,4,0,0},
                {5,2,0,0,0,0,0,0,0},
                {0,8,7,0,0,0,0,3,1},
                {0,0,3,0,1,0,0,8,0},
                {9,0,0,8,6,3,0,0,5},
                {0,5,0,0,9,0,6,0,0},
                {1,3,0,0,0,0,2,5,0},
                {0,0,0,0,0,0,0,7,4},
                {0,0,5,2,0,6,3,0,0}
        };
        // lets represent each cell as a a number. we can always reverse engineer the row/col
        solve(A, 0);
        System.out.println(Arrays.deepToString(A));
    }

    /**
     * Runtime:
     * O(n ^ m) where n is the number of possibilities for each square (ie, 9 in classic Sudoku) and m is the number of spaces that are blank.
     * This can be seen by working backwards from only a single blank. If there is only one blank, then you have n possibilities that you must work through in the worst case.
     * If there are two blanks, then you must work through n possibilities for the first blank and n possibilities for the second blank for each of the possibilities for the first blank.
     * If there are three blanks, then you must work through n possibilities for the first blank. Each of those possibilities will yield a puzzle with two blanks that has n^2 possibilities.
     */
    static boolean solve(int[][] A, int cell) {
        if (cell >= A.length * A[0].length) return true;    // all cells done

        // find the row & col offset from cell position
        int r = cell / A.length;
        int c = cell % A.length;

        if (A[r][c] != 0) return solve(A, cell + 1);     // if a value already set, move on to next one

        // if not already filled, try all valid options for this cell
        // optimized way than blindly validating same cell against each number
        List<Integer> options = getValidOptions(A, r, c);
        for (int n : options) {                  // try a number
            A[r][c] = n;                            // then assign
            if (solve(A,cell+1)) return true; // see if this choice holds fine for solving the board recursively
            A[r][c] = 0;                            // else unassign / backtrack, start from last pos
        }
        return false;
    }

    // go thru its 3x3 matrix, row & col and get the missing numbers for this cell
    static List<Integer> getValidOptions(int[][] A, int r, int c) {
        Set<Integer> options = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        // 3x3
        int boxRowStart = (r/3) * 3;
        int boxColStart = (c/3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                options.remove(A[boxRowStart + i][boxColStart + j]);
            }
        }
        // row
        for (int i = 0; i < A[0].length; i++) {
            options.remove(A[r][i]);
        }
        // col
        for (int i = 0; i < A.length; i++) {
            options.remove(A[i][c]);
        }
        return new ArrayList<>(options);
    }

}
