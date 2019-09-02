package com.raj.graphs;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class DetectCyclesDirected {

    public static void main(String[] args) {
        /**
         *      0 ----> 1 ---> 4
         *      |       ^    /
         *      v        \ v
         *      2         3
         */
        DetectCyclesDirected.Graph graph = new DetectCyclesDirected.Graph(5, false);
        graph.addEdge(0,2);
        graph.addEdge(0,1);
        graph.addEdge(1,4);
        graph.addEdge(4,3);
        graph.addEdge(3,2); // Put graph.addEdge(3,2) for no cycles & graph.addEdge(3,1) for cycles
        boolean hasCycles = false;
        for (int i = 0; i < graph.V; i++) {
            System.out.println("Launching dfs from vertex " + i + ", isVisited ? " + graph.isVisited[i]);
            if (!graph.isVisited[i] && graph.dfsFindCycles(i)) hasCycles = true;
            //if (!graph.isVisited[i] && graph.dfsFindCyclesWithStack(i)) hasCycles = true;
        }
        System.out.println("hasCycle ? " + hasCycles);
        if (!hasCycles) System.out.println("Path => " + graph.outS);

        DetectCyclesDirected.Graph DAG = new DetectCyclesDirected.Graph(5, false);
        DAG.addEdge(0,2);
        DAG.addEdge(0,1);
        DAG.addEdge(1,4);
        DAG.addEdge(4,3);
        DAG.addEdge(3,1);
        hasCycles = false;
        for (int i = 0; i < DAG.V; i++) {
            System.out.println("Launching dfs from vertex " + i + ", isVisited ? " + graph.isVisited[i]);
            //if (!graph.isVisited[i] && graph.dfsFindCycles(i)) hasCycles = true;
            if (!DAG.isVisited[i] && DAG.dfsFindCyclesWithStack(i)) hasCycles = true;
        }
        System.out.println("hasCycle ? " + hasCycles);
        if (!hasCycles) System.out.println("Path => " + DAG.out);
    }

    static class Graph {
        List<Integer>[] adjList;    // array of Lists [0] -> 2,5,7, [1] -> 3,4 ...
        int V;
        boolean isUndirected;

        // using departure times method
        boolean[] isVisited;
        int[] departureTimes;
        Stack<Integer> outS = new Stack<>();

        // using ordered hashSet, also prints the path in order of visit
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        LinkedHashSet<Integer> out = new LinkedHashSet<>(); // this is only for printing a valid topo sort

        public Graph(int v, boolean isUndir) {
            V = v;
            adjList = new List[v];
            for (int i = 0; i < adjList.length; i++) adjList[i] = new ArrayList<>();
            isUndirected = isUndir;
            isVisited = new boolean[V];
            departureTimes = new int[V];
        }

        public void addEdge(int startV, int endV) {
            adjList[startV].add(endV);
            if (isUndirected) adjList[endV].add(startV);
        }

        int time = 1;

        public boolean dfsFindCycles(int v) {
            isVisited[v] = true;    // mark vertex visited

            for (int adjV : adjList[v]) {
                if (!isVisited[adjV]) {
                    boolean hasCycle = dfsFindCycles(adjV); // go deep to find answer
                    if (hasCycle) return true;
                } else {    // already visited & departure time not set, means it is part of this dfs stack (back edge)
                    if (departureTimes[adjV] == 0) return true;
                }
            }
            outS.add(v);
            departureTimes[v] = time ++;
            return false;
        }

        public boolean dfsFindCyclesWithStack(int v) {
            isVisited[v] = true;    // mark vertex visited
            out.add(v);
            set.add(v);     // add to the ordered set to track recursion
            for (int adjV : adjList[v]) {
                if (!set.contains(adjV)) {
                    return dfsFindCyclesWithStack(adjV);
                } else {    // this vertex is already visited
                    System.out.println("Cycle detected => " + set + ", " + adjV);
                    return true;
                }
            }
            set.remove(v);
            return false;
        }

    }

}
