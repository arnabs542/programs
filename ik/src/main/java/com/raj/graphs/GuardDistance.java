package com.raj.graphs;

import java.util.*;

public class GuardDistance {

    /**
     * You are given a 2D char grid of size n * m. Each element of the grid is either a GUARD, an OPEN space or a WALL.
     * Every GUARD can move up, down, left and right in the open space. They cannot move on the wall.
     * Find, for every cell, the distance from the nearest guard cell. Consider -1 as this distance for WALL cells and unreachable cells.
     *
     * Input:
     * O O O O G
     * O W W O O
     * O O O W O
     * G W W W O
     * O O O O G
     *
     * Sample Output 1:
     *
     * 3  3  2  1  0
     * 2 -1 -1  2  1
     * 1  2  3 -1  2
     * 0 -1 -1 -1  1
     * 1  2  2  1  0
     */
    public static void main(String[] args) {
        System.out.println(find_shortest_distance_from_a_guard(Arrays.asList(
                Arrays.asList('O','O','O','O','G'),
                Arrays.asList('O','W','W','O','O'),
                Arrays.asList('O','O','O','W','O'),
                Arrays.asList('G','W','W','W','O'),
                Arrays.asList('O','O','O','O','G')
        )));
    }

    /**
     * Shortest Dist within 2D Grid? Use BFS for traversal for sure as it will give shortest path as we expand.
     * Create a 2D distArr for storing dist from Guard. Init cells as -1.
     * Identify each Guard cell, push them into Q. Corresponding distArr cell is set 0.
     * BFS:
     * # For every de-queued cell, get all 4 neighbors, see if they are OPEN & unvisited, add current dist+1 to distArr neighbor indices
     * # Also add the neighbor cells to Q.
     *
     * Runtime = O(N*M), Aux space = O(N*M) for 2D distArr & result
     */
    public static List<List<Integer>> find_shortest_distance_from_a_guard(List<List<Character>> grid) {
        int M = grid.size(); int N = grid.get(0).size();
        List<List<Integer>> distGrid = new ArrayList<>();  // res dist arr
        for (int i = 0; i < M; i++) {
            distGrid.add(new ArrayList<>());
            for (int j = 0; j < N; j++) {
                distGrid.get(i).add(-1);
            }
        }
        Queue<Cell> q = new LinkedList<>();
        // find guards and seed them into Q
        for (int i=0;i<M;i++) {
            for (int j=0;j<N;j++) {
                if (grid.get(i).get(j) == 'G') {
                    q.add(new Cell(i,j));
                    distGrid.get(i).set(j,0);
                }
            }
        }
        while(!q.isEmpty()) {  // BFS from each guards found & update distances
            Cell cell = q.remove();
            // for each neighbor which isn't wall, +1 dist & add to Q
            if (cell.j-1 >= 0) {
                addToQueueIfUnvisitedOpen(grid, distGrid, q, cell.i, cell.j, cell.i, cell.j-1);  // use row,col offset
            }
            if (cell.j+1 < N) {
                addToQueueIfUnvisitedOpen(grid, distGrid, q, cell.i, cell.j, cell.i, cell.j+1);
            }
            if (cell.i-1 >= 0) {
                addToQueueIfUnvisitedOpen(grid, distGrid, q, cell.i, cell.j, cell.i-1, cell.j);
            }
            if (cell.i+1 < M) {
                addToQueueIfUnvisitedOpen(grid, distGrid, q, cell.i, cell.j, cell.i+1, cell.j);
            }
        }
        return distGrid;
    }

    static void addToQueueIfUnvisitedOpen(List<List<Character>> grid, List<List<Integer>> distGrid, Queue<Cell> q, int i, int j, int to_i, int to_j) {
        char c = grid.get(to_i).get(to_j);
        int curr_dist = distGrid.get(i).get(j);
        if (c == 'O' &&  distGrid.get(to_i).get(to_j) == -1) {      // open & unvisited cell
            distGrid.get(to_i).set(to_j, curr_dist + 1);
            q.add(new Cell(to_i,to_j));
        }
    }

    static class Cell {
        int i,j;
        Cell(int ii, int jj) { i=ii; j=jj; }
    }

}
