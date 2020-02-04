package com.raj.graphs;

import java.util.*;

/**
 * @author rshekh1
 */
public class CheapestFlightKStops {

    /**
     * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
     * Now given all the cities and flights, together with starting city src and the destination dst, your task is to
     * find the cheapest price from src to dst with up to k stops.
     *
     *                              0
     *                        100 /  \  500
     *                          1 --- 2
     *                            100
     * Input:
     * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
     * src = 0, dst = 2, k = 1
     * Output: 200
     *
     * Input:
     * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
     * src = 0, dst = 2, k = 0
     * Output: 500
     */
    public static void main(String[] args) {
        /*System.out.println(shortestPath(3, 0, 2, 1, new int[][] {
                {0,1,100},
                {1,2,100},
                {0,2,500}
        })); // happy case - $200 (0->1->2)*/
        System.out.println(shortestPath(4, 0, 3, 2, new int[][] {
                {0,1,200},
                {0,2,100},
                {2,1,50},
                {1,3,100},
                {2,3,300}
        })); // slightly complex case where vertex 2 is considered 2 times - $250 (0->1->2->3)
        /*System.out.println(shortestPath(3, 0, 2, 3, new int[][] {
                {0,1,100},
                {1,0,100}
        })); // cycles hence INF cost
        System.out.println(shortestPath(3, 0, 2, 1, new int[][] {
                {0,1,100},
                {1,0,100},
                {1,2,100},
                {0,2,500}
        })); // has cycles, but a path exists - $200 (0->1->2)
        System.out.println(shortestPath(3, 0, 2, 0, new int[][] {
                {0,1,100},
                {1,2,100},
                {0,2,500}
        })); // no hops, so direct flight is $500
        System.out.println(shortestPath(3, 0, 2, 0, new int[][] {
                {0,1,100},
                {1,2,100}
        })); // no hops no path exists - INF cost*/
    }

    /**
     * Shortest Path BFS algo just won't work as edges are weighted. Hence, something like Dijkstra would have to be applied.
     * # Weighted Graph with cycles - 0 -> 1 -> 2 -> 0  use visited[] to keep track explored vertex
     * # Cheapest Path - BFS optimizing on cost - Dijkstra algo for weighted graphs
     * # Upto K stops - account for that
     */
    static int shortestPath(int numVertex, int src, int dest, int numHops, int[][] edges) {
        // form adjlist
        Graph G = new Graph(numVertex, edges);
        int K = numHops + 1; // max number of vertex we can explore excluding src & including dest

        int[] cost = new int[numVertex];  // track of cost at the vertex
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[src] = 0;

        PriorityQueue<Vertex> pq = new PriorityQueue<>((a,b) -> a.wt-b.wt);

        pq.add(new Vertex(src, 0, K));
        boolean[] visited = new boolean[numVertex]; // visited array

        while(!pq.isEmpty()) {
            Vertex V = pq.remove(); //pop element
            int v = V.vertex;
            visited[v] = true;

            // go thru neighbors
            for (Vertex W : G.adjList[v]) {
                int w = W.vertex;

                // base case - if neighbor is already visited OR numStops is exhausted
                if (visited[w] || V.K <= 0) continue;

                // otherwise compute new cost & optimize
                int altCost = V.wt + W.wt;
                if (altCost < cost[w]) {
                    cost[w] = altCost;
                    pq.add(new Vertex(W.vertex, cost[w], V.K-1));
                }
            }
        }
        System.out.println("Cost Array: " + Arrays.toString(cost));

        return cost[dest];
    }

    static class Graph {
        List<Vertex>[] adjList;

        Graph(int V, int[][] edges) {
            adjList = new List[V];
            for (int i=0; i<V; i++) adjList[i] = new ArrayList<>();

            for (int[] edge:edges) {
                adjList[edge[0]].add(new Vertex(edge[1], edge[2], 0));
            }
        }
    }

    static class Vertex {

        int vertex, wt, K;

        Vertex (int _v, int _wt, int _K) {
            vertex = _v; wt = _wt; K = _K;
        }

        public String toString() {
            return vertex + ", " + wt + ", " + K;
        }
    }
        private static int _findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        /**
         * Algo: Dijkstra's shortest path algo
         * Create GraphNode {int vertexId, int price}
         * Create a arc map(srcVertex, List<GraphNode> destVertex)  0 : [0->1 $100, 0->2 $500], 1 : [1->2 $100]
         * BFS for destNode with levels = k, each time check for dest reached and minPrice so far
         */
        Map<Integer, List<GraphNode>> arcs = new HashMap<>();
        for (int[] arr : flights) {
            if (!arcs.containsKey(arr[0])) {
                arcs.put(arr[0], new ArrayList<>());
            }
            arcs.get(arr[0]).add(new GraphNode(arr[0], arr[1], arr[2]));
        }

        if (!arcs.containsKey(src)) return -1;  // no route as src doesn't exist

        Queue<GraphNode> queue = new LinkedList<>();
        for (GraphNode node : arcs.get(src)) {
            node.stops++;
            node.totalPrice += node.price;
            queue.add(node);
        }
        int minPrice = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            GraphNode node = queue.poll();      // we are at airport = node's dest
            if (node.stops == K+1 && node.dest == dst) {  // reached destination (+1 for dest counted as a stop)
                minPrice = Math.min(minPrice, node.totalPrice); // compute minPrice so far
                continue;
            }

            if (node.stops > K || !arcs.containsKey(node.dest)) continue;   // too many stops already or no path ahead

            for (GraphNode outgoing : arcs.get(node.dest)) {    // outgoing connecting flights
                outgoing.stops = node.stops + 1;
                outgoing.totalPrice = node.totalPrice + outgoing.price;
                queue.add(outgoing);
            }
        }
        return minPrice;
    }

    private static class GraphNode {
        public int src;
        public int dest;
        public int price;
        public int stops;
        public int totalPrice;
        public GraphNode(int s, int d, int p) {
            src = s;
            dest = d;
            price = p;
        }
    }

}
