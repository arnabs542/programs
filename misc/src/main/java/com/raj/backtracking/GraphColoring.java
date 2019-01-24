package com.raj.backtracking;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class GraphColoring {

    /**
     * Graph:
     *   a ---- b
     *    \   |  \
     *     \  |  c
     *      \| /
     *      d ---- e
     *
     *  where 0,1,2,3,4 represents a,b,c,d,e vertices respectively
     */
    static int[][] adjMatrix = new int[][] {
            {0,1,0,1,0},
            {1,0,1,1,0},
            {0,1,0,1,0},
            {1,1,1,0,1},
            {0,0,0,1,0}
    };
    static int numVertices = adjMatrix.length;
    static int[] colors = new int[numVertices];  // we have to assign colors for each vertex, 0 means no colors assigned
    static int numColors;

    public static void main(String[] args) {
        numColors = 3;
        colors[0] = 1;  // lets assign color 1 for vertex 0 & keep moving forward
        if (colorGraph(1)) Arrays.stream(colors).forEach(x -> System.out.print(x + ","));
        else System.out.println("Not possible...");
    }

    static boolean colorGraph(int vertex) {
        if (vertex == numVertices) return true;

        for (int color = 1; color <= numColors; color++) {
            if (isFeasible(vertex, color)) {
                colors[vertex] = color;
                if (colorGraph(vertex + 1)) return true;
            }
        }

        return false;
    }

    static boolean isFeasible(int vertex, int color) {
        // color is ok if none of the neighbors have this color
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[vertex][i] == 1 && colors[i] == color) return false;
        }
        return true;
    }

}
