package com.raj.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetectCyclesSimple {

    /**
     * Return true if edge list representing a directed graph has a cycle.
     * N = 5, M = 7, edges = [[0,1],[0,3],[1,3],[1,2],[2,3],[4,0],[2,4]]
     * Output: true
     *
     *     4 --> 0 ----> 1
     *     ^      \    /  \
     *     |       v  v    v
     *     |        3 <---- 2
     *     |                 |
     *      -----------------
     * Cycle => 0→1→2→4→0
     */
    public static void main(String[] args) {
        System.out.println(hasCycle(5,7, Arrays.asList(
                Arrays.asList(0,1),
                Arrays.asList(0,3),
                Arrays.asList(1,3),
                Arrays.asList(1,2),
                Arrays.asList(2,3),
                Arrays.asList(4,0),
                Arrays.asList(2,4)
        )));
    }

    /**
     * Define visited[] as:
     * 0 = unvisited,
     * 1 = being visited & in current recursion stack (means a cycle),
     * 2 = completely visited
     * DFS on unvisited vertex, track true return values and exit with true if found.
     * Return false if no cycles after visiting all vertices.
     *
     * Runtime = O(N+M), Aux Space = O(N+M) for adjList
     */
    public static boolean hasCycle(int N, int M, List<List<Integer>> edges) {
        initGraph(N, edges);
        for (int v=0; v<N; v++) {           // try dfs starting with each unvisited vertex
            if (visited[v] == 0) {
                if (dfs(v)) return true;    // there was a cycle
            }
        }
        return false;                       // no cycle found
    }

    static boolean dfs(int v) {
        visited[v] = 1;                     // mark visited
        for (int w : adjList[v]) {          // visit neighbors
            if (visited[w] == 0) {          // visit this neighbor, return true if dfs detects cycle
                if (dfs(w)) return true;
            } else if (visited[w] == 1) {   // vertex in current recursion stack, cycle detected !!
                return true;
            }
        }
        visited[v] = 2;                     // mark completely visited
        return false;                       // no cycle found for vertex v
    }

    static List<Integer>[] adjList;         // notice the arr with list datatype for adjacencyList
    static int[] visited;

    static void initGraph(int N, List<List<Integer>> edges) {
        adjList = new List[N];
        visited = new int[N];
        for (int i=0;i<N;i++) adjList[i] = new ArrayList<>();
        edges.forEach(e -> adjList[e.get(0)].add(e.get(1)));
    }

}
