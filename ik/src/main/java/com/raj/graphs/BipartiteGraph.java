package com.raj.graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author rshekh1
 */
public class BipartiteGraph {

    public static void main(String[] args) {
        /**
         *    Can we color adjacent edges with different alternate colors? Same as asking is the Graph Bipartite?
         *    Different variation of this problem, can we split a group of n people in 2 teams such that each group has
         *    folks who don't DISLIKE each other. Dislike is represented by edges b/w 2 people
         *
         *    Given Graph:
         *    0-----1-----2
         *    |
         *    |
         *    3             4----5
         *
         *    ==> Yes
         */
        int G[][] = {
             //  0  1  2  3  4  5 (Vertex)    G[i][j] represents an edge b/w Vertex i & j
                {0, 1, 0, 1, 0, 0}, // 0
                {1, 0, 1, 0, 0, 0}, // 1
                {0, 1, 0, 1, 0, 0}, // 2
                {1, 0, 1, 0, 0, 0}, // 3
                {0, 0, 0, 0, 0, 1}, // 4
                {0, 0, 0, 0, 1, 0}  // 5
        };
        int[] vertexColors = new int[G.length];     // 1D array of vertices and values as their colors
        for (int i = 0; i < vertexColors.length; i++) vertexColors[i] = -1; // init colors as -1 & lets color 0 & 1

        for (int i = 0; i < vertexColors.length; i++) { // takes care of disconnected graphs bipartiteness as well
            if (vertexColors[i] == -1) {    // vertex not colored, aka not visited
                System.out.println("Bipartite ? " + isBipartite(G, i, vertexColors));
                System.out.println("Colors=" + Arrays.toString(vertexColors));
            }
        }
    }

    /**
     * A graph is Bipartite, if we can split the vertices into 2 subsets such that edges go b/w 2 sets
     * Note - DFS can also be used : https://www.geeksforgeeks.org/check-if-a-given-graph-is-bipartite-using-dfs/
     */
    static boolean isBipartite(int[][] G, int startV, int[] vColor) {   // vertexColor array, doubles up as visited array
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startV);   // start at vertex 0
        vColor[startV] = 0;  // start with color 0
        while (!queue.isEmpty()) {
            int v = queue.poll();
            if (G[v][v] == 1) return false;  // self loop, odd length cycle, not bipartite
            for (int i = v + 1; i < G[v].length; i++) { // for all neighbors starting v+1 as for i=1, 1---0 edge wud have been processed before
                // if an edge exists with this vertex, try to color it, if uncolored (unvisited)
                if (G[v][i] == 1) {
                    if (vColor[i] == -1) vColor[i] = 1 - vColor[v]; // alternating color
                    else if (vColor[i] == vColor[v]) return false;  // neighbor's color conflicts, not bipartite !!
                    queue.add(i);
                }
            }
        }
        return true;
    }

}
