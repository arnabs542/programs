package com.raj.graphs;

import java.util.*;

/**
 * @author rshekh1
 */
public class ShortestPath2DKeysDoors {

    /**
     * Shortest Path In 2D Grid With Keys And Doors
     *
     * Given a 2D grid of size n * m, that represents a maze-like area, a start cell and a goal cell, you have to find
     * the shortest path from start to the goal. You can go up, down, left or right from a cell, but not diagonally.
     * Each cell in the grid can be either land or water or door or key to some doors. You can only travel on land cells,
     * key cells and door cells, and not on water cells.
     *
     * Cells in the grid can be described as:
     * '#' = Water.
     * '.' = Land.
     * 'a' = Key of type 'a'. All lowercase letters are keys. a-j only
     * 'A' = Door that opens with key 'a'. All uppercase letters are doors.
     * '@' = Starting cell.
     * '+' = Ending cell (goal).
     *
     *  . . . B
     *  . b # .
     *  @ # + .
     *
     * => 9 cells will be visited in shortest path.
     * Actual path is: 2 0 -> 1 0 -> 1 1 -> 0 1 -> 0 2 -> 0 3 -> 1 3 -> 2 3 -> 2 2
     *
     * Approach:
     * Visiting a cell(x,y) can happen in multiple ways - no keys, with 1 or more keys, each should be considered as a
     * different visited (If we just mark visited first time only, later if it led to a door, the path wud be blocked).
     * Hence something like visited[x][y][keys] wud work. keys can be a bit representation - 101 representing c,a keys.
     * So, [x][y][5] wud mean we came to x,y with c,a key this time which can be marked visited.
     * To track parent, we need another array similar to above to just set which cell led us to it.
     */
    public static void main(String[] args) {
        System.out.println("Path -> " + Arrays.deepToString(find_shortest_path(new String[] {
                "...B",
                ".b##",
                "@#+."
        })));
        System.out.println("Path -> " + Arrays.deepToString(find_shortest_path(new String[] {
                "...B",
                ".b#.",
                "@#+."
        })));
        System.out.println("Path -> " + Arrays.deepToString(find_shortest_path(new String[] {
                "c..B.",
                ".#b#.",
                "..#..",
                "@#+C."
        })));
    }

    static int[][] find_shortest_path(String[] grid) {
        Maze maze = new Maze(grid);
        // tells if a cell is visited for x,y,keys combination.
        // The third dimension is keys accumulated so far, 1111111111 keys combination - wud mean all a-j keys are gotten
        maze.visited = new boolean[grid.length][grid[0].length()][1024];
        Cell start = maze.findStartCell();
        return maze.bfs(start);
    }

    static class Maze {
        String[] grid;
        boolean[][][] visited;
        static int validMoves = 4;
        static int[] addRows = {-1,0,1,0};
        static int[] addCols = {0,1,0,-1};

        public Maze(String[] g) {
            grid = g;
        }

        int[][] bfs(Cell start) {

            Queue<Cell> q = new LinkedList<>();
            q.add(start);
            visited[start.i][start.j][start.keys] = true;

            while (!q.isEmpty()) {
                Cell v = q.poll();

                // reached goal ? build path and exit
                if (grid[v.i].charAt(v.j) == '+') return buildPath(v);

                // v -> w neighbors traversal
                for (int k = 0; k < validMoves; k++) {
                    // build neighbor(x,y) from predefined moves array
                    int wRow = v.i + addRows[k];
                    int wCol = v.j + addCols[k];
                    Cell w = new Cell(wRow, wCol, v.keys, v.dist + 1, v);   // link parent for this neighbor, for build path later

                    if (!isValid(w.i, w.j)) continue; // validate neighbor

                    char ch = grid[w.i].charAt(w.j); // is neighbor land, water, key or door ?

                    // water ?
                    if (ch == '#') continue;

                    // door ? can it be unlocked with accumulated keys ? can only be unlocked if that bit key is 1
                    if (ch >= 'A' && ch <= 'J' && (v.keys & 1 << (ch - 'A')) == 0) continue;

                    // key ? add it to w keys, eg. we had key f,g & now we found c => 1100000 + 100 = 1100100
                    if (ch >= 'a' && ch <= 'j') w.keys = v.keys | 1 << (ch - 'a');

                    // land ? or was it a door / key before ? we go ahead & add it to queue, if not visited for x,y,keys combo)
                    if (!visited[w.i][w.j][w.keys]) {
                        q.add(w);
                        visited[w.i][w.j][w.keys] = true;   // mark it as visited (if not, it will double count, see graph notes)
                    }
                }
            }
            System.out.print("No solution exists !! ");
            return null;    // no solution
        }

        Cell findStartCell() {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length(); j++) {
                    if (grid[i].charAt(j) == '@') return new Cell(i, j, 0, 0, null);
                }
            }
            return null;
        }

        int[][] buildPath(Cell cell) {
            List<int[]> path = new ArrayList<>();
            System.out.print("Steps = " + cell.dist + ", ");
            while (cell != null) {
                path.add(new int[]{cell.i, cell.j});
                cell = cell.parent;
            }
            if (path.size() == 0) path.add(new int[]{0,0});
            Collections.reverse(path);
            return path.toArray(new int[path.size()][2]);
        }

        boolean isValid(int row, int col) { // check bounds
            return (0 <= row) && (row < grid.length) && (0 <= col) && (col < grid[0].length());
        }

    }

    static class Cell {
        int i, j, keys, dist;
        Cell parent;
        Cell(int x, int y, int k, int d, Cell p) {
            i = x; j = y; keys = k; dist = d; parent = p;
        }
    }

}
