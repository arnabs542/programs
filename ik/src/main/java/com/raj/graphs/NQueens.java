package com.raj.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author rshekh1
 */
public class NQueens {

    /**
     * Place N Queens such that no queens attack each other.
     * For input n = 4, output will be:
     *
     * [
     *  ["-q--",
     *   "---q",
     *   "q---",
     *   "--q-"],
     *
     *  ["--q-",
     *   "q---",
     *   "---q",
     *   "-q--"]
     * ]
     *
     * Approach:
     * # We have 4 Q to place, 1 per row. For each row there are 4 col options. We dfs after placing on a col for row+1 th
     * # We kick off recursion with placing Q at 0th row, recursive step will involve iterating thru cols & if valid dfs
     * # Once all rows are done, we have finished placing all Q
     * Recursion Tree:
     *                  f(row 0)
     *      f(0),col0   f(0),1       f(0),2      f(0),3
     *     f(1),0...3   f(1),0....3  f(1),0...3  f(1),0...3
     *    f(2),0...3 .....
     *
     */
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(find_all_arrangements(4)));
    }

    // Time complexity exponential ~O(n!)
    static String[][] find_all_arrangements(int n) {
        int[] A = new int[n];   // instead of using 2D grid of booleans, we can represent just the queen's position by using 1D array A[row]=col
        Arrays.fill(A, -1);
        List<String[]> res = new ArrayList<>();
        dfs(A, 0, res);
        return res.toArray(new String[res.size()][4]);
    }

    static void dfs(int[] A, int row, List<String[]> res) {
        if (row == A.length) {  // all queens are placed in rows
            res.add(printBoard(A));
            return;
        }

        for (int col = 0; col < A.length; col++) {  // for given row, let's try placing Queen in cols 0...n-1
            A[row] = col;
            if (isValid(A, row, col)) {
                A[row] = col;
                dfs(A, row+1, res);
            }
        }
    }

    /**
     * Not Valid scenarios:
     * for left diagonals i1-j1 = i2-j2,
     * for right diagonal i1+j1 = i2+j2,
     * if row or col same, they are in same row/col
     *
     * eg. current state is :
     * A idx => 0,1,2,3 (row)
     * A col = [1,3,0,_]
     * Queens are placed at -
     * -q--
     * ---q
     * q---
     * ????
     *
     * if row=3,col=? to place is-
     * row won't ever conflict as we dfs(row+1) as soon as we find a spot for Queen to place
     * 3,0 -> conflict with 2,0 => col 0 conflicts
     * 3,1 -> conflict with 0,1 & diag 2,0 => col 1 conflicts, diag conflicts 3-1 = 2-0
     * 3,2 -> ok
     * 3,3 -> conflict with 1,3 => col 3 conflicts
     *
     */
    static boolean isValid(int[] A, int row, int col) {
        for (int i = 0; i < row; i++) {  // only go until row's seen, as anything over will have garbage values from an earlier board state

            // col conflict
            if (A[i] == col) return false;

            // left diagonal conflict
            if ((i-A[i]) == (row-col)) return false;

            // right diagonal conflict
            if ((i+A[i]) == (row+col)) return false;
        }
        return true;
    }

    /**
     * eg. A Valid board state :
     * A idx => 0,1,2,3 (row)
     * A col = [1,3,0,2]
     *
     * o/p Queens are placed at -
     * -q--
     * ---q
     * q---
     * --q-
     */
    static String[] printBoard(int[] A) {
        String[] boardState = new String[A.length];
        for (int i = 0; i < A.length; i++) {
            StringBuilder rowState = new StringBuilder();
            int cnt = A[i];
            while (cnt-- > 0) rowState.append("-");
            rowState.append("q");
            while (rowState.length() < A.length) rowState.append("-");
            boardState[i] = rowState.toString();
        }
        return boardState;
    }

}
