package com.raj.backtracking;

/**
 * @author rshekh1
 */
public class MazeSolver {

    /**
     *
     */
    public static void main(String[] args) {
        System.out.println("Maze Solved ? " + solve(0,0));
        printPath();
    }

    static int[][] maze = {     // 1 = path, 0 = wall
            {1,1,1,1,1},
            {0,0,0,1,0},
            {1,1,0,1,1},
            {1,0,1,0,1},
            {0,1,1,1,1}
    };

    static int[][] path = new int[maze.length][maze[0].length];     // stores the found path

    static void printPath() {
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[0].length; j++) {
                System.out.print(path[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean solve(int x, int y) {
        if (x == maze.length - 1 && y == maze[0].length - 1) {
            path[x][y] = 1;
            return true;
        }

        if (isFeasible(x, y)) {
            path[x][y] = 1;
            if (solve(x, y + 1) || solve(x + 1, y)) return true;
            else path[x][y] = 0;
        }

        return false;
    }

    // check if path exists & not outside maze bounds
    static boolean isFeasible(int x, int y) {
        return (x < maze.length && y < maze[0].length && maze[x][y] == 1);
    }

}
