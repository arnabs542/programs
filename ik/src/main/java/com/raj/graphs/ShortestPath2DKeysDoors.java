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
     * 'a' = Key of type 'a'. All lowercase letters are keys.
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
     * BFS, for each neighbor mark visited, and also set the min distSoFar, plus collate keys & add cell to path
     * Old cell ------------------> New cell
     *           can unlock door?
     *           if Yes, add to Queue with following -
     *          + cell path
     *          + update minDist
     *          + add keys to doors
     */
    public static void main(String[] args) {
        Maze maze = new Maze(new String[] {
                "...B",
                ".b#.",
                "@#+."
        });
        System.out.println(Arrays.toString(maze.find_shortest_path()));
    }

    static class Maze {
        String[] grid;
        boolean[][] visited;

        public Maze(String[] g) {
            grid = g;
        }

        public int[][] find_shortest_path() {
            visited = new boolean[grid.length][grid[0].length()];
            Coords start = findStartCell();
            Cell end = bfs(start);
            String[] arr = end.path.split(",");
            int[][] path = new int[arr.length][2];
            for (int i = 0; i < path.length; i++) {
                if (arr[i].isEmpty()) continue;
                path[i][0] = Integer.valueOf(arr[i].split(" ")[0]);
                path[i][1] = Integer.valueOf(arr[i].split(" ")[1]);
            }
            return path;
        }

        public Cell bfs(Coords start) {
            Queue<Cell> queue = new LinkedList<>();
            // add start cell info
            Cell startCell = new Cell(start.i, start.j);
            startCell.path += "," + start.i + " " + start.j;
            queue.add(startCell);
            visited[start.i][start.j] = true;
            while (!queue.isEmpty()) {
                Cell cell = queue.poll();
                for (Coords w : getNeighbors(cell.coords.i, cell.coords.j)) {
                    if (!visited[w.i][w.j]) {
                        visited[w.i][w.j] = true;   // mark visited
                        char c = grid[w.i].charAt(w.j);
                        if (Character.isAlphabetic(c)) {    // is this a door or key?
                            if (Character.isLowerCase(c)) cell.keysToDoor += Character.toUpperCase(c); // key found
                            if (Character.isUpperCase(c) && !cell.keysToDoor.contains(c+"")) continue; // door found
                        }
                        Cell wCell = new Cell(w.i, w.j);
                        // add discovered key / path
                        wCell.keysToDoor = cell.keysToDoor;
                        wCell.path = cell.path + "," + w.i + " " + w.j;
                        if (grid[w.i].charAt(w.j) == '+') return cell; // reached goal state, return
                        queue.add(wCell);
                    }
                }
            }
            return null;
        }

        public Coords findStartCell() {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length(); j++) {
                    if (grid[i].charAt(j) == '@') return new Coords(i, j);
                }
            }
            return null;
        }

        public List<Coords> getNeighbors(int i, int j) {
            List<Coords> neighbors = new ArrayList<>();
            if (i - 1 >= 0 && grid[i - 1].charAt(j) != '#') neighbors.add(new Coords(i - 1, j));
            if (i + 1 < grid.length && grid[i + 1].charAt(j) != '#') neighbors.add(new Coords(i + 1, j));
            if (j - 1 >= 0 && grid[i].charAt(j - 1) != '#') neighbors.add(new Coords(i, j - 1));
            if (j + 1 < grid[i].length() && grid[i].charAt(j + 1) != '#') neighbors.add(new Coords(i, j + 1));
            return neighbors;
        }
    }

    static class Cell {
        Coords coords;
        int minDist;
        String keysToDoor = "";
        String path = "";

        public Cell(int i, int j) {
            coords = new Coords(i, j);
        }
    }

    static class Coords {
        int i, j;
        Coords(int x, int y) { i=x; j=y; }
    }

}
