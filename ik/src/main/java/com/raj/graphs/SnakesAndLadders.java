package com.raj.graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SnakesAndLadders {

    /**
     * Find the minimum number of die rolls necessary to reach the final cell of the given Snakes and Ladders board game.
     * Player starts from cell one, rolls a die and advances 1-6 (random number of) steps at a time. Should they land on
     * a cell where a ladder starts, player immediately climbs up that ladder. Similarly, having landed on a cell with a
     * snake’s mouth, player goes down to the tail of that snake. Game ends when the player arrives at the last cell.
     *
     * Sample Input 1:
     * n = 20, moves = [-1, 18, -1, -1, -1, -1, -1, -1, 2, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1, -1]
     * Output = 2
     * Having started from cell 1, player rolls 1 and moves to cell 2. The stair takes them from cell 2 to cell 19.
     * They then roll 1 to arrive to the last, 20th cell. No fewer die rolls can bring a player to the last cell.
     *
     * Sample Input 2:
     * n = 8, moves = [-1, -1, -1, -1, -1, -1, -1, -1]
     * Output = 2
     * There are several ways to reach the last cell in two rolls: 6+1, 5+2, 4+3, 3+4, 2+5, 1+6.
     */
    public static void main(String[] args) {
        System.out.println(minNumberOfRolls(8, Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1)));
        System.out.println(minNumberOfRolls(20, Arrays.asList(-1, 18, -1, -1, -1, -1, -1, -1, 2, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1, -1)));
        System.out.println(minNumberOfRolls(18, Arrays.asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,0,1,2,3,4,5,-1,-1,-1)));  // not feasible
    }

    /**
     * Graph problem - All cells are nodes and there is an edge from every cell to the next 6 cells on the board.
     * But no need to construct the graph upfront, we'll dynamically add cells as we try dice rolls from a given cell.
     * Use BFS : The first arrival to a node using BFS is guaranteed to use the fewest possible number of edges
     * Handle Edge cases -
     * 1> u need x to win but roll of dice is greater than x?
     * 2> from a cell, any roll of dice takes you to snake and u go backward (there is no way to move forward)?
     *
     * Runtime = O(n)
     * Aux Space = O(n) for visited arr & 6*n for max Q size(which is O(n) as well). But in practice, processing each
     * node adds at most 6 new nodes to the Q, and no one node is processed more than once so it's size is constant.
     */
    static int minNumberOfRolls(int n, List<Integer> moves) {
        Integer[] A = moves.toArray(new Integer[moves.size()]);
        boolean[] visited = new boolean[n];                 // tracks a visited cell before

        Queue<Cell> q = new LinkedList<>();
        q.add(new Cell(0, 0));

        while (!q.isEmpty()) {
            Cell cell = q.remove();
            /**
             * If we visited this node before we must have reached it with fewer dice rolls than we have done now.
             * No need to consider it again. It also, handles edge case 2> where there are 6 snakes ahead all taking us before this cell
             */
            if (visited[cell.idx]) continue;

            visited[cell.idx] = true;    // mark visited

            if (cell.idx == A.length-1) return cell.numMoves;   // reached end, BFS gives shortest path, hence return

            // try dice rolls
            for (int i : new int[]{1,2,3,4,5,6}) {
                int newPos = cell.idx + i;
                if (newPos >= A.length) break;              // dice roll is greater than what's needed to finish
                if (A[newPos] != -1) newPos = A[newPos];    // if it was a snake or ladder, goto the col it says
                q.add(new Cell(newPos, cell.numMoves+1));
            }
        }
        return -1;
    }

    static class Cell {
        int idx;
        int numMoves;
        Cell(int i, int m) {
            idx = i; numMoves = m;
        }
    }

}
