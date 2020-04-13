package com.raj.graphs;

import java.util.*;

public class MinimumHeightTree {
    /**
     * === Minimum Height Trees ===
     * "A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree."
     * "The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf."
     * For a undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a
     * rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs).
     * Given such a graph, write a function to find all the MHTs and return a list of their root labels.
     *
     * Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]
     *
     *         0
     *         |
     *         1
     *        / \
     *       2   3
     *
     * Output: [1]
     */
    public static void main(String[] args) {
        System.out.println(findMinHeightTrees_dfs_topo(4, new int[][]{{1,0}, {1,2}, {1,3}}));
        System.out.println(findMinHeightTrees_dfs_topo(6, new int[][]{{0,3}, {1,3}, {2,3}, {4,3}, {5,4}}));

        System.out.println(findMinHeightTrees_kahns(4, new int[][]{{1,0}, {1,2}, {1,3}}));
        System.out.println(findMinHeightTrees_kahns(6, new int[][]{{0,3}, {1,3}, {2,3}, {4,3}, {5,4}}));
    }

    /**
     * Pattern ? Since it's a graph with no cycles - we'll need to do topo sort with some modifications.
     *
     * Take trivial case: 1-2-3-4-5  => Node 3 is the answer, as it's the mid point and will have min height of 2.
     *
     * Approach 1: [DFS topo for max path & find mid]
     * # Expand on the trivial case solution above - we need to find the mid of the largest distance edge path.
     * # Pick any node, dfs & find the farthest node x.
     * # Launch dfs again from x, and find the farthest node y from it. Save the nodes in this path. (Topo sort)
     * # Now just find the mid of x---y path which can be 2 if size is even or 1 if odd.
     * Time = O(n+n), Space = O(n)
     */
    public static List<Integer> findMinHeightTrees_dfs_topo(int n, int[][] edges) {
        // build adjlist
        Graph graph = new Graph(n, edges);

        // pick any node and dfs to find farthest node x
        dfs(0, 0, new int[n], new Stack<>(), graph);

        // dfs again to find farthest node y from x. Top of stack is farthest node 'x'
        dfs(graph.farthestPath.peek(), 0, new int[n], new Stack<>(), graph);

        // finally find mid from computed max path
        List<Integer> path = graph.farthestPath;
        int size = graph.farthestPath.size();
        if (size % 2 == 0) {
            return Arrays.asList(path.get(size/2 - 1), path.get(size/2));
        } else {
            return Arrays.asList(path.get(size/2));
        }
    }

    static void dfs(int v, int dist, int[] visited, Stack<Integer> stack, Graph graph) {
        visited[v] = 1;  // visiting
        stack.push(v);   // add a node to path

        // update max path
        if (stack.size() > graph.farthestPath.size()) {
            graph.farthestPath.clear();
            graph.farthestPath.addAll(stack);
        }

        for (int w : graph.adjList[v]) {
            if (visited[w] == 0) {      // unvisited ?
                dfs(w, dist + 1, visited, stack, graph);
            }
        }

        visited[v] = 2; // visited
        stack.pop();    // backtrack
    }

    /**
     * Approach 2: [Layer wise leaf node removal until mid remains]
     * We can use modified Kahn's algo:
     * # Find degrees of nodes. Start with leaf(1-degree) nodes & add it to Queue.
     * # Pop & Decr degrees of adj nodes. If it becomes 1, add it to Queue.
     * # Return once size of Queue is 1 or 2 & they have 1 degree only.
     * O(n)
     */
    public static List<Integer> findMinHeightTrees_kahns(int n, int[][] edges) {
        Graph graph = new Graph(n, edges);
        List<Integer> leafs = new ArrayList<>();

        // add leaf aka 1-degree nodes
        for (int i = 0; i < n; i++) {
            if (graph.adjList[i].size() == 1) leafs.add(i);
        }

        while (n > 2) {  // nodes<=2, it means we have an answer
            List<Integer> newLeafs = new ArrayList<>();
            for (int v : leafs) {
                n--;
                for (int w : graph.adjList[v]) {   // for its neighbors, reduce degree by removing leaf node
                    Set<Integer> neighbors = graph.adjList[w];
                    neighbors.remove(v);
                    if (neighbors.size() == 1) newLeafs.add(w);  // add to new leaf if they become leaf
                }
            }
            leafs = newLeafs;
        }
        return leafs;
    }

    static class Graph {
        int V;
        Set<Integer>[] adjList;
        Stack<Integer> farthestPath = new Stack<>();

        Graph(int n, int[][] edges) {
            V = n;
            adjList = new HashSet[V];
            for (int i = 0; i < n; i++) adjList[i] = new HashSet<>();

            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                adjList[u].add(v);
                adjList[v].add(u);
            }
        }
    }

}
