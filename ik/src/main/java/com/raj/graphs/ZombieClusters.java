package com.raj.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author rshekh1
 */
public class ZombieClusters {

    /**
     * We have a 2D array having n rows and n columns where each cell, zombies[A][B], denotes whether zombie A
     * knows zombie B. The diagram showing connectedness will be made up of a number of binary strings,
     * characters 0 or 1. Each of the characters in the string represents whether the zombie associated with a
     * row element is connected to the zombie at that character's index. For instance, a zombie 0 with a
     * connectedness string '110' is connected to zombies 0 (itself) and zombie 1, but not to zombie 2.
     * The complete matrix of zombie connectedness is:
     *
     * 110
     * 110
     * 001 ==> 2
     * Zombies 0 and 1 are connected. Zombie 2 is not.
     * Your task is to determine the number of connected groups of zombies, or clusters, in a given matrix.
     */
    public static void main(String[] args) {
        List<String> zombies = Arrays.asList(new String[] {
                "110",     // 2
                "110",
                "001"
        });
        System.out.println(zombieCluster(zombies));


        zombies = Arrays.asList(new String[] {
                "1100",     // 2
                "1110",
                "0110",
                "0001"
        });
        System.out.println(zombieCluster(zombies));
    }

    public static int zombieCluster(List<String> zombies) {
        if (zombies == null || zombies.isEmpty()) return 0;
        int vertices = zombies.size();
        int numClusters = 0;
        isVisited = new int[vertices];
        for (int i=0; i<vertices; i++) {
            if (isVisited[i] == 0) {
                dfs(i, zombies);
                numClusters ++;
            }
        }
        return numClusters;
    }

    static int[] isVisited;

    static List<Integer> getNeighbors(int v, List<String> z) {
        List<Integer> n = new ArrayList<>();
        for (int i=0; i<z.get(v).length(); i++) {
            if (z.get(v).charAt(i) == '1') n.add(i);
        }
        return n;
    }

    static void dfs(int v, List<String> zombies) {
        isVisited[v] = 1;
        for (int w : getNeighbors(v, zombies)) {
            if (isVisited[w] == 0) dfs(w, zombies);
        }
    }

}
