package com.raj.graphs;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author rshekh1
 */
public class FindIslands {

    public static void main(String[] args) {
        System.out.println(findIslands(new int[][] {
                {1,1,1,0,0},{1,1,0,0,1}
        }));
        System.out.println(findIslands(new int[][] {
                {1,1,1,1,0},{1,1,0,1,0},{1,1,0,0,0},{0,0,0,0,0}
        }));
        System.out.println(findIslands(new int[][] {
                {1,1,0,0,0},{1,1,0,0,0},{0,0,1,0,0},{0,0,0,1,1}
        }));
    }

    /**
     * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
     * You may assume all four edges of the grid are all surrounded by water.
     * Extensions of the problem - Find max area of an island
     *
     * Example 1:
     * 11110
     * 11010
     * 11000
     * 00000
     * Output: 1
     *
     * Example 2:
     * 11000
     * 11000
     * 00100
     * 00011
     * Output: 3
     *
     * # Is it a graph problem ? Yes, it talks about adjacent, connections
     * # How do we represent Graph ? 2D Grid, where finding neighbors is trivial as getting A(i+1,j) or A(i,j+1)
     * # What template do we apply ? Connected components + DFS/BFS
     */
    static Pair<Integer, Integer> findIslands(int[][] A) {   // returns num islands & max area tuple
        // for each unvisited vertex launch DFS, increment islands count, update max
        // instead of using aux ds to store visited flags, we can set array index as 0 when explored
        int maxArea = 0, islands = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] == 1) { // found an unexplored island, let's launch dfs
                    //maxArea = Math.max(maxArea, dfs(A, i, j));
                    maxArea = Math.max(maxArea, bfs(A, i, j));
                    islands ++;
                }
            }
        }
        return new Pair<>(islands, maxArea);
    }

    // look at dfs template in images
    static int dfs(int[][] A, int i, int j) {
        A[i][j] = 0;    // mark explored
        int area = 1;   // add area for the explored region
        for (Pair<Integer,Integer> pair : getNeighbors(A, i, j)) {
            if (A[pair.getKey()][pair.getValue()] == 1) {   // unexplored
                area += dfs(A, pair.getKey(), pair.getValue()); // add area for this new region & keep going deep, incr area
            }
        }
        return area;    // final computed area
    }

    // look at bfs template in images
    static int bfs(int[][] A, int i, int j) {
        LinkedList<Pair<Integer,Integer>> queue = new LinkedList<>();
        A[i][j] = 0;    // mark explored
        int area = 1;   // add area for the explored region
        queue.push(new Pair<>(i,j));
        while (!queue.isEmpty()) {
            Pair<Integer,Integer> v = queue.pop();
            // DO NOT ADD Processing logic here as it will doubly push nodes into the queue
            for (Pair<Integer, Integer> w : getNeighbors(A, v.getKey(), v.getValue())) {
                if (A[w.getKey()][w.getValue()] == 1) {   // unexplored
                    // Add neighbor processing logic here, to avoid double pushing into the queue
                    A[w.getKey()][w.getValue()] = 0; // mark explored
                    area ++;    // increment area
                    queue.push(w);
                }
            }
        }
        return area;
    }

    static List<Pair<Integer,Integer>> getNeighbors(int[][] A, int i, int j) {
        /**
         *       1
         *     1 1 1
         *       1
         *  Also, check Array bounds
         */
        List<Pair<Integer,Integer>> neighbors = new ArrayList<>();
        if (j-1 >=0 && A[i][j-1] == 1) neighbors.add(new Pair<>(i, j-1));
        if (j+1 < A[i].length && A[i][j+1] == 1) neighbors.add(new Pair<>(i, j+1));
        if (i-1 >=0 && A[i-1][j] == 1) neighbors.add(new Pair<>(i-1, j));
        if (i+1 < A.length && A[i+1][j] == 1) neighbors.add(new Pair<>(i+1, j));
        return neighbors;
    }

}
