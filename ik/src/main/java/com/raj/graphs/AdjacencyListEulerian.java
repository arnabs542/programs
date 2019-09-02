package com.raj.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rshekh1
 */
public class AdjacencyListEulerian {

    public static void main(String[] args) {
        /**
         *      0 ---- 1
         *       \    /
         *        \ /
         *         2 ---- 3
         *         |      |
         *         -------
         *         Eulerian cycle - all even degree vertices & we can start at 2 & go through all edges exactly once and be back at 2
         */
        Graph graph = new Graph(4, true);
        graph.addEdge(0,1);graph.addEdge(0,2);
        graph.addEdge(1,2);
        graph.addEdge(2,3);
        graph.addEdge(2,3); // comment this and it will be eulerian path only, not cycle
        System.out.println(graph.hasEulerianCycle() +  "," + graph.hasEulerianPath());
    }

    static class Graph {
        List<Integer>[] adjList;    // array of Lists [0] -> 2,5,7, [1] -> 3,4 ...
        int V;
        boolean isUndirected;

        public Graph(int v, boolean isUndir) {
            V = v;
            adjList = new List[v];
            for (int i = 0; i < adjList.length; i++) adjList[i] = new ArrayList<>();
            isUndirected = isUndir;
        }

        public void addEdge(int startV, int endV) {
            adjList[startV].add(endV);
            if (isUndirected) adjList[endV].add(startV);
        }

        public boolean hasEulerianCycle() {
            return getOddDegreeVertices() == 0;
        }

        public boolean hasEulerianPath() {
            int odd = getOddDegreeVertices();
            return odd == 0 || odd == 2;
        }

        public int getOddDegreeVertices() {
            return Arrays.stream(adjList).filter(x -> (x.size() % 2) != 0).collect(Collectors.toList()).size();
        }

    }

}
