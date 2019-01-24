package com.raj.backtracking;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class HamiltonianCycle {

    /**
     * https://walmart.udemy.com/algorithmic-problems-in-java/learn/v4/t/lecture/3943436?start=0
     * a ------ b
     * | \   /  | \
     * |   c    |  e
     * | /   \  | /
     * d ------ f
     */
    static int[][] adjMatrix = {
        //   a  b  c  d  e  f
            {0, 1, 1, 1, 0, 0}, // a eg, a-> a,e,f is 0 (not connected) & a-> b,c,d is 1 (connected)
            {1, 0, 1, 0, 1, 0}, // b
            {1, 1, 1, 1, 0, 1}, // c
            {1, 0, 1, 0, 0, 1}, // d
            {0, 1, 0, 0, 0, 1}, // e
            {0, 1, 1, 1, 1, 1}  // f
    };

    static int[] hPath = new int[adjMatrix.length]; // visit each vertex once in any order and store path here

    public static void main(String[] args) {
        hPath[0] = 0; // start at 'a'
        if (isHCycle(1)) Arrays.stream(hPath).forEach(x -> System.out.print((char)(97+x) + " -> "));  // one vertex already visited, 5 to go
    }

    /**
     * Hamiltonian Path is a path which visits each vertex in a connected graph exactly once.
     * Hamiltonian cycle is the path which connects back to the origin vertex,  thus forming a cycle.
     * Find if that exists,  given a graph.
     *
     * Algo:
     * Define Graph as adjacency matrix for simplicity
     * Use backtracking to traverse and find id the cycle exists,  making a choice, 
     * seeing if that solves otherwise backtrack and try the next available choice
     */
    static boolean isHCycle(int nVertex) {
        // if all vertices visited and there is a connection back to the origin 'a', we have found a solution
        if (nVertex == adjMatrix.length) {
            int currVertex = hPath[nVertex - 1]; // current vertex (-1 as we haven't found solve for nVertex yet)
            int startVertex = hPath[0]; // we started from here
            if (adjMatrix[currVertex][startVertex] == 1) return true;
            else return false;
        }

        // otherwise find a connection and move forward looking to find the cycle, else backtrack
        for (int adjacentV = 1; adjacentV < adjMatrix.length; adjacentV++) {
            if (isFeasible(nVertex, adjacentV)) {
                hPath[nVertex] = adjacentV; // add to path if feasible
                if (isHCycle(nVertex + 1)) return true;
            }
            //else hPath[nVertex] = 0; // backtrack
        }
        return false;
    }

    static boolean isFeasible(int nVertex, int adjacentV) {
        // is this new vertex connected with last vertex in path & also not already visited
        if (adjMatrix[hPath[nVertex-1]][adjacentV] == 1) {
            for (int i = 0; i < nVertex; i++) {
                if (adjacentV == hPath[i]) return false;
            }
            return true;
        }
        return false;
    }

}
