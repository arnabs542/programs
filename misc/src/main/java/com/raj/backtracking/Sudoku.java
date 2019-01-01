package com.raj.backtracking;

/**
 * @author rshekh1
 */
public class Sudoku {

    public static void main(String[] args) {
        int[][] a = new int[9][9];
        solve(a, 0, 0);
    }

    static boolean solve(int[][] a, int r, int c) {
        if (r >= 8) return true;    // all rows done
        if (c > 8) {                // reset r,c if it goes over bounds as we are only incrementing c for next try
            r += 1;
            c = 0;
        }
        for (int n = 1; n <= 9; n++) {                  // try a number
            if (isValid(a, r, c, n)) {                  // is it ok?
                a[r][c] = n;                            // then assign
                if (solve(a, r, c + 1)) return true; // see if this choice holds fine for solving the board recursively
                a[r][c] = 0;                            // else unassign / backtrack, start from last pos
            }
        }
        return false;
    }

    static boolean isValid(int[][] a, int r, int c, int n) {    // validate, each 3x3 matrix will have 1-9
        // TODO
        return true;
    }

}
