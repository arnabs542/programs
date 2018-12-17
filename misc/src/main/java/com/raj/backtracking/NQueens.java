package com.raj.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rshekh1
 */
public class NQueens {

    /**
     * Given an integer n, return all distinct solutions to the n-queens puzzle.
     * Consider nxn chess board & solve all combinations
     */

    private static class Board {
        public boolean[][] b;
        public Board(int n) {
            b = new boolean[n][n];
        }

        @Override
        public String toString() {
            List<String> res = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String r = "";
                for (int j = 0; j < n; j++) {
                    r += b[i][j] ? "Q " : ". ";
                }
                res.add(r);
            }
            return res.toString();
        }
    }

    static int n = 4;

    public static void main(String[] args) {

        for (int startAtCol = 0; startAtCol < n; startAtCol++) {  // Tries a Queen in first row at col 0 to n. This is to keep finding all solves for the board

            Board board = new Board(n);

            board.b[0][startAtCol] = true;  // set queen at 0, startAtCol
            solve(board, 1);
        }
    }

    /**
     * For current Board (with initial Queen pos set), find the solution
     */
    static boolean solve(Board B, int row) {

        if (row >= n) return isSolved(B);   // reached end of rows to try, return final answer

        for (int c = 0; c < n; c++) {   // for given row, try a col
            if (isValid(B, row, c)) {   // check if it's safe to place
                B.b[row][c] = true;       // place queen
                if (solve(B, row + 1)) return true;     // march forward from here and try next row
                else B.b[row][c] = false; // backtrack if not solvable with this choice of queen placement
            }
        }

        return false;   // exhausted all choices (rows+cols), no solve possible now
    }

    static boolean isValid(Board B, int row, int col) {
        for (int c = 0; c < n; c++) if (B.b[row][c]) return false; // horizontal scan
        for (int r = 0; r < n; r++) if (B.b[r][col]) return false; // vertical scan
        for (int d = 0; d < n; d++) {
            if (row+d < n && col+d < n && B.b[row+d][col+d]) return false; // upper diagonal scan
            if (row-d > -1 && col-d > -1 && B.b[row-d][col-d]) return false; // lower diagonal scan
        }
        return true;
    }

    // finds & prints a solved board
    static boolean isSolved(Board B) {
        int countQ = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (B.b[i][j]) countQ ++;
            }
        }
        if (countQ == n) {
            printBoard(B);
            return true;
        }
        return false;
    }

    static void printBoard(Board B) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%s %s", B.b[i][j] ? "Q" : ".", " ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------");
    }

}
