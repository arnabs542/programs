package com.raj.recursion;

import java.util.Stack;

/**
 * @author rshekh1
 */
public class NQueens {

    public static void main(String[] args) {
        solve(4, 1, new Stack());   // 4 queens
    }

    /**
     * n is the no. of queens to place
     * i is the queen being placed
     * slate is the queens placed so far, a concise representation of queens on board by column
     *      => slate = [0,3,2,1] wud mean queens placed at (0,0), (1,3), (2,2), (3,1)
     */
    static void solve(int n, int i, Stack slate) {
        if (i == n) {
            System.out.println(slate);
            return;
        }

        // try placing queen in col
        for (int col = 0; col < n; col++) {
            // constraint to prune unnecessary branching
            if (isValid(slate, col)) {
                slate.push(col);
                solve(n, i+1, slate);
                slate.pop();
            }
        }
    }

    /**
     * 2 Queens conflict when they are in:
     * same col => check if the slate contains repeated col
     * diagonal (11, 22, 33 etc) => check if the rowDiff = colDiff
     *
     */
    static boolean isValid(Stack slate, int col) {
        // TODO
        return true;
    }

}
