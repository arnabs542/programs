package com.raj.graph;

import java.util.*;

/**
 * @author rshekh1
 */
public class CheapestFlightKStops {

    public static void main(String[] args) {
        System.out.println(findCheapestPrice(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0 , 2, 1)); // connecting flights
        System.out.println(findCheapestPrice(3, new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0 , 2, 0)); // direct flight
    }

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
    private static int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
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
