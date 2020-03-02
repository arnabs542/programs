package com.raj.graphs;

import java.util.*;

public class LongestPathWeightedDAG {

    /**
     * Given a weighted DAG (directed acyclic graph), where weight of an edge denotes the length of that edge, a node
     * from_node and a node to_node, you have to find longest path from from_node to to_node.
     */
    public static void main(String[] args) {
        //System.out.println("Max Path = " + Arrays.toString(find_longest_path_brute(1, null, null,null, 1,1)));
        /*System.out.println("Max Path = " + Arrays.toString(find_longest_path_brute(4,
                new int[]{1, 1, 1, 3},
                new int[]{2, 3, 4, 4},
                new int[]{2, 2, 4, 3},
                1, 4)));
        System.out.println("Max Path = " + Arrays.toString(find_longest_path(4,
                new int[]{1, 1, 1, 3},
                new int[]{2, 3, 4, 4},
                new int[]{2, 2, 4, 3},
                1, 4)));*/
        System.out.println("Max Path = " + Arrays.toString(find_longest_path(7,
                new int[]{1, 1, 1, 1, 2, 3, 4, 5, 6},
                new int[]{2, 3, 4, 5, 5, 5, 5, 6, 7},
                new int[]{1, 2, 3, 1, 1, 1, 1, 1, 1},
                1, 7)));
    }

    /**
     * Brute Force:
     * Build a adjacencyList first.
     * Launch DFS from source(don't use visited as we need to traverse each path & compute dist), & visit all v -> w
     * For each neighbor w, add up dist & dfs (keep saving path in stack)
     * If reached endVertex, terminate recursion & update maxPath
     * <p>
     * Runtime = O(V^2)  ... as we might visit same vertex many times without visited flag
     * Consider:
     *                      A --------
     *                   /  |  \      |
     *                  v   v   v     |
     *                 a1   a2  a3    |
     *                  \   |   /     |
     *                   v  v  v      |
     *                      B <-------
     *                      |
     *                      v
     *                      C
     *                      |
     *                      v
     *                      D
     *                      ..... --> Z
     * A -> a1 (wt=1), a2 (wt=2), a3 (wt=3) -> B -> C -> D .... Z
     * The tail B...Z is being visited for each neighbor of source vertex
     */
    static int[] find_longest_path_brute(int dag_nodes, int[] dag_from, int[] dag_to, int[] dag_weight, int from_node, int to_node) {
        if (dag_nodes <= 1) return new int[]{1};

        // build adjacency list
        Graph G = new Graph(dag_nodes, from_node, to_node, dag_from, dag_to, dag_weight);
        System.out.println(G.adjMap);

        // dfs each & every path (some v will be visited multiple times) & update maxPath
        Stack<Integer> path = new Stack<>();
        path.push(from_node);
        G.dfs_brute(from_node, 0, path);

        int[] res = new int[G.maxPath.size()];
        int i = 0;
        while (i < G.maxPath.size()) {
            res[i] = G.maxPath.get(i++);
        }
        return res;
    }

    static class Graph {
        int num_vertices;
        int startV, endV;
        int maxWt; List<Integer> maxPath = new ArrayList<>();       // max path
        Map<Integer, List<Node>> adjMap = new HashMap<>();     // adj map

        Graph(int dag_nodes, int from_node, int to_node, int[] dag_from, int[] dag_to, int[] dag_weight) {
            startV = from_node; endV = to_node;
            num_vertices = dag_nodes;
            for (int i = 0; i < dag_from.length; i++) {
                if (!adjMap.containsKey(dag_from[i])) adjMap.put(dag_from[i], new ArrayList<>());
                adjMap.get(dag_from[i]).add(new Node(dag_to[i], dag_weight[i]));
            }
        }

        void dfs_brute(int v, int wt, Stack<Integer> path) {
            if (v == endV) {    // base case
                System.out.println(wt + " -> " + path);
                if (wt > maxWt) {
                    maxWt = wt;
                    maxPath = new ArrayList<>(path);
                }
                return;
            }
            // dfs neighbors
            if (adjMap.get(v) == null) return;
            for (Node w : adjMap.get(v)) {
                path.push(w.id);
                dfs_brute(w.id, wt + w.wt, path);
                path.pop(); // backtrack
            }
        }

        Stack<Integer> topo_sort() {
            Stack<Integer> stack = new Stack<>();   // stores ordered vertices
            boolean[] visited = new boolean[num_vertices+1];
            for (int v : adjMap.keySet()) {
                if (!visited[v]) dfs_topo(v, visited, stack);
            }
            return stack;
        }
        
        void dfs_topo(int v, boolean[] visited, Stack<Integer> stack) {
            visited[v] = true;
            if (adjMap.get(v) != null) {
                for (Node w : adjMap.get(v)) {  // dfs unvisited neighbors
                    if (!visited[w.id]) dfs_topo(w.id, visited, stack);
                }
            }
            stack.push(v);  // add to stack done vertex
        }

        int[] findMaxPath(Stack<Integer> stack) {
            long[] dist = new long[num_vertices+1];
            Arrays.fill(dist, Integer.MIN_VALUE);
            dist[stack.peek()] = 0;     // source vertex is 0 mile
            int[] parent = new int[num_vertices+1];     // to track vertex's parent to construct path later

            while (!stack.isEmpty()) {
                int v = stack.pop();
                if (adjMap.get(v) == null) continue;
                for (Node w : adjMap.get(v)) {
                    if (dist[w.id] == Integer.MIN_VALUE) {
                        dist[w.id] = w.wt;
                        parent[w.id] = v;
                    } else {
                        long newDist = dist[v] + w.wt;
                        if (newDist > dist[w.id]) {
                            dist[w.id] = newDist;
                            parent[w.id] = v;
                        }
                    }
                }
            }

            // parent arr contains each vertex's parent, reconstruct path in reverse order
            int v = endV;
            maxPath.add(v); // add endV first
            while (v != startV) {
                v = parent[v];  // find it's parent
                maxPath.add(v); // add it to path
            }
            System.out.println(maxPath);
            Collections.reverse(maxPath);
            return maxPath.stream().mapToInt(x -> x).toArray();
        }

    }

    static class Node {
        int id, wt;

        Node(int _id, int _wt) {
            id = _id;
            wt = _wt;
        }

        public String toString() {
            return id + "," + wt;
        }
    }

    /**
     * Optimal Solution: https://www.geeksforgeeks.org/shortest-path-for-directed-acyclic-graphs/
     * <p>
     * For a general weighted graph, we can calculate single source shortest distances in O(VE) time using Bellman–Ford
     * Algorithm. For a graph with no negative weights, we can do better and calculate single source shortest distances
     * in O(E + VLogV) time using Dijkstra’s algorithm. Can we do even better for Directed Acyclic Graph (DAG)? We can
     * calculate single source shortest distances in O(V+E) time for DAGs. The idea is to use Topological Sorting.
     * <p>
     * Idea is to determine a definite order in which to visit vertices so that we can do level-wise dfs visiting each
     * vertex only once & update max distances.
     * <p>
     * Following is complete algorithm for finding shortest distances.
     * 1) Initialize dist[] = {INF, INF, ….} and dist[s] = 0 where s is the source vertex.
     * 2) Create a topological order of all vertices.
     * 3) Do following for every vertex u in topological order.
     * …………Do following for every adjacent vertex v of u
     * ………………if (dist[v] > dist[u] + weight(u, v))
     * ………………………dist[v] = dist[u] + weight(u, v)
     */
    static int[] find_longest_path(int dag_nodes, int[] dag_from, int[] dag_to, int[] dag_weight, int from_node, int to_node) {
        if (dag_nodes == 1) return new int[]{1};  // only one node present

        // build adjacency map
        Graph G = new Graph(dag_nodes, from_node, to_node, dag_from, dag_to, dag_weight);
        System.out.println("Adj Map => " + G.adjMap);

        // topo sort vertices for visiting later level-wise
        Stack<Integer> stack = G.topo_sort();
        System.out.println("Topo Sorted V => " + stack);

        // now visit ordered set of vertices to find distances
        return G.findMaxPath(stack);
    }

}
