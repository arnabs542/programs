package com.raj.backtracking;

/**
 * @author rshekh1
 */
public class SudokuSolver {

    static int[][] board = {
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

    static int BOARD_SIZE = 9;
    static int BOX_SIZE = 3;

    public static void main(String[] args) {
        if (solve(0, 0)) printBoard();
        else System.out.println("No solution...");
        /*if (solve0(0)) printBoard();
        else System.out.println("No solution...");*/
    }

    static void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (i % BOX_SIZE == 0) System.out.println(" ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (j % BOX_SIZE == 0) System.out.print(" ");
                System.out.print(board[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    static boolean solve(int x, int y) {
        if (x == BOARD_SIZE - 1 && y == BOARD_SIZE) return true;  // goal reached when recursing for solve(8,9)
        if (y == BOARD_SIZE) {x ++; y = 0;}       // reset x,y if y reaches upper bounds to process next row
        if (board[x][y] != 0) return solve(x, y+1);     // if a value already set, move on to next one
        for (int n = 1; n <= 9; n++) {
            if (isFeasible(x, y, n)) {
                board[x][y] = n;
                if (solve(x, y+1)) return true;     // solve the next col
                else board[x][y] = 0;
            }
        }
        return false;
    }

    static boolean solve0(int cell) {
        if (cell == 81) return true;  // goal reached when we are at 81th cell
        int r = cell / 9;       // compute row
        int c = cell % 9;       // compute col
        if (board[r][c] != 0) return solve0(cell + 1);     // if a value already set, move on to next one
        for (int n = 1; n <= 9; n++) {
            if (isFeasible(r, c, n)) {
                board[r][c] = n;
                if (solve0(cell + 1)) return true;     // solve the next col
                else board[r][c] = 0;
            }
        }
        return false;
    }

    static boolean isFeasible(int x, int y, int n) {
        // if n already exists in this row or column, then not feasible
        for (int t = 0; t < BOARD_SIZE; t++) {
            if (board[x][t] == n || board[t][y] == n)  return false;
        }
        // if n already exists in the 3x3 box
        int boxRowStart = (x/3) * BOX_SIZE;
        int boxColStart = (y/3) * BOX_SIZE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRowStart + i][boxColStart + j] == n) return false;
            }
        }
        return true;
    }

}
