package com.raj.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Very important template for many Graph DFS related problem solving
 */
public class DFSArrivalDeparture {

    public static void main(String[] args) {
        /**
         *      0 ----> 2 ---> 3
         *      |       ^    /
         *      v        . v
         *      1 <------ 4
         */
        DFSArrivalDeparture.Graph graph = new DFSArrivalDeparture.Graph(5, false);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(2,3);
        graph.addEdge(3,4);
        graph.addEdge(4,1);
        //graph.addEdge(4,2); // creates cycles, comment it for no cycles case
        boolean hasCycles = false;
        for (int i = 0; i < graph.V; i++) {
            System.out.println("Launching dfs from vertex " + i + ", isVisited ? " + graph.isVisited[i]);
            if (!graph.isVisited[i] && graph.dfs(i)) hasCycles = true;
        }
        System.out.println("hasCycle ? " + hasCycles);
        System.out.println("Vertices --------> 0, 1, 2, 3, 4");
        System.out.println("Arrival   times = " + Arrays.toString(graph.arrivalTimes));
        System.out.println("Departure times = " + Arrays.toString(graph.departureTimes));
        System.out.print("\nPath taken ===> " + graph.outS);
    }

    static class Graph {
        List<Integer>[] adjList;    // array of Lists [0] -> 2,5,7, [1] -> 3,4 ...
        int V;
        boolean isUndirected;

        boolean[] isVisited;
        int[] arrivalTimes;
        int[] departureTimes;
        List<Integer> outS = new ArrayList<>();

        public Graph(int v, boolean isUndir) {
            V = v;
            adjList = new List[v];
            for (int i = 0; i < adjList.length; i++) adjList[i] = new ArrayList<>();
            isUndirected = isUndir;
            isVisited = new boolean[V];
            arrivalTimes = new int[V];
            departureTimes = new int[V];
        }

        public void addEdge(int startV, int endV) {
            adjList[startV].add(endV);
            if (isUndirected) adjList[endV].add(startV);
        }

        int time = 0;

        public boolean dfs(int v) {
            isVisited[v] = true;    // mark vertex visited
            arrivalTimes[v] = time ++;  // time when we just landed on a vertex & ready to explore neighbors

            System.out.println("    Visiting  vertex " + v);

            for (int w : adjList[v]) {  // v -> w

                if (!isVisited[w]) {
                    System.out.println("            dfs neighbor (v->w) : " + v + " -> " + w);
                    boolean hasCycle = dfs(w);
                    if (hasCycle) return true;  // return true if a cycle is found, otherwise continue. Note - return dfs(adjV) will not complete traversing the graph
                } else {    // already visited & departure time not set, means it is part of this dfs stack (back edge)
                    if (departureTimes[w] == 0) return true;
                }
                // neighbor completed
                System.out.println("        Completed neighbor (v->w) : " + v + " -> " + w);
            }
            // vertex completed
            System.out.println("    Completed vertex " + v);

            outS.add(v);    // output order of departures
            departureTimes[v] = time ++;
            return false;   // no cycles
        }
    }

}
