package com.raj.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CriticalConnections_Bridges {
    /**
     * https://www.geeksforgeeks.org/bridge-in-a-graph/
     * Similar to Articulation Points: https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
     *
     * There are n servers numbered connected by undirected server-to-server connections forming a network
     * where connections[i] = [a, b] represents a connection between servers a and b.
     * Any server can reach any other server directly or indirectly through the network.
     * Find critical connections that, if removed, will make some x server unable to reach some other y
     * server which were initially reachable.
     *
     * num_servers = 4, connections = [[0, 1], [1, 2], [2, 0], [1, 3]]
     *
     *      0 ----- 1 ---- 3
     *       \     /
     *        \   /
     *          2
     * result=[[1, 3]]
     */
    public static void main(String[] args) {
        System.out.println(findCriticalConnections(4, Arrays.asList(
                Arrays.asList(0,1),
                Arrays.asList(1,2),
                Arrays.asList(2,0),
                Arrays.asList(1,3)
        )));
    }

    private static List<Integer> adj[];
    private static int time = 0;

    /**
     * A simple approach is to one by one remove all edges and see if removal of an edge causes disconnected graph.
     * For every edge (u, v), do following:
     * # Remove (u, v) from graph
     * # See if the graph remains connected (We can either use BFS or DFS)
     * # Add (u, v) back to the graph
     * Time complexity of the above method is O(E*(V+E)) for a graph represented using adjacency list.
     *
     * Can we do better? O(V+E) algorithm :
     * The idea is to use DFS (Depth First Search). In DFS, we follow vertices in tree form called DFS tree. In DFS tree,
     * a vertex u is parent of another vertex v, if v is discovered by u (obviously v is an adjacent of u in graph).
     * In DFS tree, a vertex u is articulation point if one of the following two conditions is true:
     * 1) u is root of DFS tree and it has at least two children.
     * 2) u is not root of DFS tree and it has a child v such that no vertex in subtree rooted with v has a back edge to
     * one of the ancestors (in DFS tree) of u.
     *
     * We do DFS traversal of given graph with additional code to find out Articulation Points (APs). In DFS traversal,
     * we maintain a parent[] array where parent[u] stores parent of vertex u. Among the above mentioned two cases,
     * the first case is simple to detect. For every vertex, count children. If currently visited vertex u is
     * root (parent[u] is NIL) and has more than two children, print it.
     *
     * How to handle second case? The second case is trickier. We maintain an array disc[] to store discovery time of
     * vertices. For every node u, we need to find out the earliest visited vertex (the vertex with minimum discovery
     * time) that can be reached from subtree rooted with u. So we maintain an additional array low[] which is defined
     * as follows:
     * low[u] = min(disc[u], disc[w]), where w is an ancestor of u and there is a back edge from some descendant of u to w.
     *
     * https://oj.interviewkickstart.com/view_editorial/5370/217/
     */
    public static List<List<Integer>> findCriticalConnections(int noOfServers, List<List<Integer>> connections){

        List<List<Integer>> result = new ArrayList<>();

        // visited[] --> keeps track of visited vertices
        boolean visited[] = new boolean[noOfServers];

        // disc[] --> Stores discovery times of visited vertices
        int discovery_time[] = new int[noOfServers];
        int lowest_time[] = new int[noOfServers];

        // parent[] --> Stores parent vertices in DFS tree
        int parent[] = new int[noOfServers];
        Arrays.fill(parent, -1);

        // Create Adjacency matrix out of given connections (edges of undirected graph)
        createAdjMatrix(noOfServers, connections);

        time = 0;

        // Call the recursive helper function to find Bridges in DFS tree rooted with vertex 'i'
        for (int i=0; i<noOfServers; i++) {
            if (!visited[i]) {
                dfs(i, visited, discovery_time, lowest_time, parent, connections, result);
            }
        }
        if (result.size() == 0) {
            addInResult(-1, -1, result);
        }
        return result;
    }

    // A recursive function that finds and prints bridges using DFS traversal
    // u --> The vertex to be visited next
    private static void dfs(int u,
                            boolean visited[],
                            int discovery_time[], int lowest_time[], int parent[],
                            List<List<Integer>> connections, List<List<Integer>> result) {

        // Mark the current node as visited
        visited[u] = true;

        // Initialize discovery time and low value
        discovery_time[u] = lowest_time[u] = time++;

        // Go through all vertices adjacent to this
        for(int v: adj[u]) {
            // If v is not visited yet, then make it a child of u in DFS tree and recur for it.
            if (!visited[v]) {
                parent[v] = u;
                dfs(v, visited, discovery_time, lowest_time, parent, connections, result);

                // Check if the subtree rooted with v has a connection to one of the ancestors of u
                lowest_time[u] = Math.min(lowest_time[u], lowest_time[v]);

                // If the lowest vertex reachable from subtree
                // under v is below u in DFS tree, then u-v is
                // a bridge
                if (lowest_time[v] > discovery_time[u]) {
                    addInResult(u, v, result);
                }
            }
            // Update low value of u for parent function calls.
            else if (v != parent[u]) {
                lowest_time[u] = Math.min(lowest_time[u], discovery_time[v]);
            }
        }
    }

    private static void addInResult(int u, int v, List<List<Integer>> result) {
        List<Integer> temp = new ArrayList<>();
        temp.add(u);
        temp.add(v);
        result.add(temp);
    }

    private static void createAdjMatrix(int noOfServers, List<List<Integer>> connections) {
        adj = new ArrayList[noOfServers];

        for (int i=0;i<noOfServers;i++) adj[i] = new ArrayList<>();

        for (List<Integer> connection: connections) {
            int u = connection.get(0); int v = connection.get(1);
            adj[u].add(v);
            adj[v].add(u);
        }
    }

}
