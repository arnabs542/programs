package com.raj.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author rshekh1
 */
public class KnightsTour {

    /**
     * rows = 5
     * cols = 5
     * start_row = 0
     * start_col = 0
     * end_row = 4
     * end_col = 1
     *
     * Sample Output:
     * 3 moves to reach from (0, 0) to (4, 1):
     * (0, 0) -> (1, 2) -> (3, 3) -> (4, 1).
     */
    public static void main(String[] args) {
        //System.out.println(find_minimum_number_of_moves(5,5,0,0,4,1));
        System.out.println(find_minimum_number_of_moves(2,7,0,6,1,1));

    }

    static int find_minimum_number_of_moves(int rows, int cols, int start_row, int start_col, int end_row, int end_col) {
        Grid g = new Grid(rows, cols);
        return g.bfs(start_row, start_col, end_row, end_col);
    }

    static class Grid {
        int[][] grid;
        Grid(int rows, int cols) {
            grid = new int[rows][cols];
        }

        public int bfs(int sr, int sc, int er, int ec) {
            if (sr == er && sc == ec) return 0;
            Queue<Cell> q = new LinkedList<>();
            q.add(new Cell(sr, sc));
            grid[sr][sc] = 1; // visited
            while (!q.isEmpty()) {
                Cell v = q.poll();
                for (Cell w : getNeighbors(v)) {
                    if (w.i == er && w.j == ec) {   // reached goal
                        return grid[v.i][v.j];
                    }
                    if (grid[w.i][w.j] == 0) {
                        grid[w.i][w.j] = grid[v.i][v.j] + 1;// mark visited
                        q.add(w);
                    }
                }
            }
            return -1;
        }

        public List<Cell> getNeighbors(Cell v) {
            List<Cell> n = new ArrayList<>();
            int[] a = new int[] {-2,-1,+1,+2};
            for (int i=0;i<a.length;i++) {
                for (int j=0;j<a.length;j++) {
                    // don't use same numbers for adj co-ords like it can't be 1,1 or 2,2
                    if (Math.abs(a[i]) == Math.abs(a[j])) continue;
                    int x = v.i+a[i]; int y = v.j+a[j];
                    if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) continue;
                    n.add(new Cell(x,y));
                }
            }
            //System.out.println("Neighbors of v = " + n);
            return n;
        }

    }

    static class Cell {
        int i,j;
        Cell(int x, int y) { i = x; j = y; }
        public String toString() {
            return i + "," + j + " ";
        }
    }


}
