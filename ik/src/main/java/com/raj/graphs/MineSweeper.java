package com.raj.graphs;

import java.util.Arrays;
import java.util.Random;

public class MineSweeper {
    /**
     * board = createMineSweeper(int width, int height, int numberOfMines)
     * solve(board)
     * play(board, int x, int y) => print if game over else return the bomb count
     */
    public static void main(String[] args) {
        int[][] board = createMineSweeper(4, 3, 4);
        System.out.println("Board  => " + Arrays.deepToString(board));

        solve(board);
        System.out.println("Solved => " + Arrays.deepToString(board));

        System.out.println(play(board, 0,2));
        System.out.println(play(board, 1,2));
        System.out.println(play(board, 2,3));
    }

    /**
     * --width--
     * B 3 3 B
     * . B B .
     * . . B .
     * B . B 2
     *
     * - lay out the board w/ random bombs given height & width
     *   - optimize random to be linear. How?
     * - once board is filled, iterate board
     *   - if non bomb cell, get adj cells &
     *   - find the num bombs
     *   - Set it at this cell
     *
     * Time = O(w * h) + O(numMines ^ 2)  => ... each cell 8 worst cell lookups
     * Space = O(width X heigth)
     */
    static int[][] createMineSweeper(int w, int h, int numMines) {
        int[][] board = new int[h][w]; // h = rows, w = cols
        placeMines(board, numMines);  // place mines randomly
        return board;
    }

    /**
     * # create a temp 1D arr with all cells
     * # place all bombs from start of arr
     * # shuffle array while reducing the random range as i + next_rand(i+1...n)
     */
    static void placeMines(int[][] board, int numMines) {
        int h = board.length;
        int w = board[0].length;
        int cells = h*w;
        int[] arr = new int[cells];

        // place all mines from start
        for (int i=0; i<numMines; i++) {
            arr[i] = -1;
        }

        // shuffle array
        Random rand = new Random();
        for (int i=0; i<cells; i++) {
            int r = i + rand.nextInt(cells-i);  // bound is exclusive
            // swap
            int t = arr[i];
            arr[i] = arr[r];
            arr[r] = t;
        }

        // copy cells array in board
        for (int cell=0; cell<cells; cell++) {
            board[cell/w][cell%w] = arr[cell];
        }
    }

    static int play(int[][] board, int x, int y) {
        if (board[x][y] == -1) System.out.print("Game Over! ");
        return board[x][y];
    }

    static void solve(int[][] board) {
        int h = board.length;
        int w = board[0].length;
        // 8 adjoining cells of i,j => i-1,j-1  i-1,j  i-1,j+1  i,j-1  i,j+1  i+1,j-1  i+1,j  i+1,j+1
        int[] row_offset = new int[]{-1,-1,-1, 0, 0, 1, 1, 1};
        int[] col_offset = new int[]{-1, 0, 1,-1, 1,-1, 0, 1};

        for (int i=0; i<h; i++) {
            for (int j=0; j<w; j++) {
                if (board[i][j] == -1) continue;  // if bomb, nothing to do
                // get adj cells
                int numBombs = 0;
                for (int k=0; k<8; k++) {
                    int x = i+row_offset[k];
                    int y = j+col_offset[k];
                    // adj cell within bounds & is a bomb
                    if ((x >= 0 && x < h) && (y >= 0 && y < w) && board[x][y] == -1) {
                        numBombs ++;
                    }
                }
                board[i][j] = numBombs;
            }
        }
    }

}
