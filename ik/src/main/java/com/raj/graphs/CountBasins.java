package com.raj.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CountBasins {

    /**
     * You are given a matrix where each number represents altitude of that cell, such that, water flows towards
     * neighboring 4 cells with lower altitudes.
     * Cells that drain into the same sink – directly or indirectly – are said to be part of the same basin.
     * Your challenge is to partition the map into basins. Your code should output the sizes of the basins, in increasing order.
     *
     * Input 1:
     * 0 2 1 3
     * 2 1 0 4
     * 3 3 3 3
     * 5 5 2 1
     *
     * The basins, labeled with 0,1,2 are:
     * 0 0 1 1
     * 0 1 1 1
     * 0 1 1 2
     * 0 2 2 2
     *
     * o/p = 4,5,7
     *
     * Input 2:
     * 1 5 2            0 0 1
     * 2 4 7      =>    0 0 1
     * 3 6 9            0 0 0
     * o/p = 2,7
     */
    public static void main(String[] args) {
        System.out.println(find_basins(Arrays.asList(Arrays.asList(10))));

        System.out.println(find_basins(Arrays.asList(
                Arrays.asList(1,5,2),
                Arrays.asList(2,4,7),
                Arrays.asList(3,6,9)
        )));

        System.out.println(find_basins(Arrays.asList(
                Arrays.asList(0,2,1,3),
                Arrays.asList(2,1,0,4),
                Arrays.asList(3,3,3,3),
                Arrays.asList(5,5,2,1)
        )));

        System.out.println(find_basins(Arrays.asList(
                Arrays.asList(1,0,2,5,8),
                Arrays.asList(2,3,4,7,9),
                Arrays.asList(3,5,7,8,9),
                Arrays.asList(1,2,5,4,3),
                Arrays.asList(3,3,5,2,1)
        )));

    }

    /**
     * Algo:
     * Iter through each cell i,j. Find the lowest altitude neighbor (if this cell is lowest, move to next one).
     * Create an directed edge from cell -> sink
     * Then find num connected components & their sizes.
     *
     * Time / Space = O(n*m)
     *
     * Approach 2: Similar to graph coloring, identify local minima cells, assign colors(incrementing), then BFS from there
     */
    public static List<Integer> find_basins(List<List<Integer>> matrix) {
        Graph G = new Graph(matrix);
        for (int i = 0; i < G.rows; i++) {      // iter thru each cell
            for (int j = 0; j < G.cols; j++) {
                if (G.basins[i][j] == -1) G.dfs(new Cell(i,j));
            }
        }
        System.out.println("Colored Grid => " + Arrays.deepToString(G.basins));

        // O(n) count sort
        return G.countSort();
    }

    static class Graph {
        int rows,cols;
        int[][] grid;
        int[][] basins;
        int colorNum = -1;
        int[] offset_row = new int[]{-1, 0, 1, 0};
        int[] offset_col = new int[]{ 0, 1, 0,-1};

        //Map<Cell,Cell> edges = new HashMap<>();   // cell -> sink edge, not reqd, incurs slightly extra space & runtime

        Graph(List<List<Integer>> matrix) {
            rows = matrix.size(); cols = matrix.get(0).size();
            grid = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = matrix.get(i).get(j);
                }
            }
            // init basins to store results
            basins = new int[rows][cols];
            Arrays.stream(basins).forEach(row -> Arrays.fill(row, -1));
        }

        /*void addEdge(Cell cell, Cell sink) {
            edges.put(cell, sink);
        }*/

        Cell getMinNeighbor(Cell cell) {
            Cell minCell = cell;
            for (int offset = 0; offset < offset_row.length; offset++) {
                int r = cell.i + offset_row[offset];
                int c = cell.j + offset_col[offset];
                if (r < 0 || r >= rows || c < 0 || c >= cols) continue;
                if (grid[r][c] < grid[minCell.i][minCell.j]) minCell = new Cell(r,c);
            }
            return minCell;
        }

        int dfs(Cell v) {
            Cell w = getMinNeighbor(v);
            if (basins[w.i][w.j] == -1) {     // unvisited w, 2 cases follow
                // Case 1: w is same as v, means this is a new unvisited sink that we found
                if (w == v) {
                    int color = ++colorNum;   // get a new color
                    basins[v.i][v.j] = color; // color this sink
                    return color;
                }
                // Case 2: dfs & find a sink whose color we can assign to this v
                int color = dfs(w);
                basins[v.i][v.j] = color;
                return color;
            } else { // already visited w i.e. a color was already assigned to w, color v & return it
                int color = basins[w.i][w.j];
                basins[v.i][v.j] = color;
                return color;
            }
        }

        List<Integer> countSort() {
            int[] basinCounts = new int[rows*cols+1];
            for (int i = 0; i < rows; i++) {   // get counts for each basin first
                for (int j = 0; j < cols; j++) {
                    basinCounts[basins[i][j]]++;
                }
            }
            int[] counts = new int[rows*cols + 1];
            for (int i = 0; i < basinCounts.length; i++) {   // then get counts only array
                int cnt = basinCounts[i];
                if (cnt != 0) {
                    counts[basinCounts[i]]++;   // remember there may be dupe counts
                }
            }
            System.out.println(Arrays.toString(counts));
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < counts.length; i++) {
                while (counts[i]-- > 0) res.add(i);     // get non-zero counts which are sorted already
            }
            return res;
        }
    }

    static class Cell {
        int i, j;
        Cell(int _i, int _j) { i=_i; j=_j; }

        @Override
        public String toString() {
            return i + "," + j;
        }

        @Override
        public boolean equals(Object o) {
            Cell c = (Cell) o;
            return (c.i == i && c.j == j);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(i + "," + j);
        }
    }

}
