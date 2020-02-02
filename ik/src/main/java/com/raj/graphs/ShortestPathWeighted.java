package com.raj.graphs;

import java.util.*;

public class ShortestPathWeighted {
    /**
     * Given a DAG with weighted edges, find the shortest weighted path from a source vertex S to destination vertex D.
     */
    public static void main(String[] args) {
        System.out.println(dijkstra(4, 0, 3, new int[][] {
                new int[] {0, 1, 10},
                new int[] {1, 3, 15},
                new int[] {0, 2, 20},
                new int[] {1, 2, 05},
                new int[] {2, 3, 06}
        }));
    }

    /**
     *  u, v, wt
     * [0, 1, 10]
     * [1, 0, 15]
     * [0, 2, 20]
     * [1, 2, 05]
     * [2, 3, 06]
     *
     *            1
     *       10/  |  \15
     *        /   |   \
     *       0   5|    3
     *        \   |   /
     *       20\  |  /6
     *            2
     * o/p : Path = 0->1->2->3, MinCost = 21
     *
     * # Can we apply simple BFS as it's shortest?
     *   No. Since there is weight for edges, a longer path can have a smaller cost.
     * # Ok let's tweak BFS. Can we use Q to store vertices?
     *   No. Pushing vertices based on hops won't work. We'll have to use a Priority Based Queue where we assign weights & push.
     *   The top of the Q now has the min weight.
     * # Optimal Substructure?
     *   To solve, we need to solve all smaller sub-problems which is finding shortest path for each vertex.
     *   This only works if the weights are +ve. Negative weights defeats this property.
     *
     * DIJKSTRA ALGO:
     * # Basically a tweaked BFS using PQ - hence greedy as it capitalizes on the min weight top element.
     * # Also called Single Source Shortest Path algo, as it only computes for ONE pair of vertices.
     * # For finding all pairs shortest path, use DP on Graphs called Floyd-Warshall Algo:
     *   - https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
     * # Only works for positive weights. For negative weights, use Bellman-Ford Algo w/ O(VE) which is more than Dijkstra's O(ElogV):
     *   - https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/
     * # Algo:
     *   - Init a MinPQ with all V & INF dist
     *   - Push source V & 0 dist
     *   - loop until !PQ.empty
     *     - v = PQ.pop, mark visited
     *     - for neighbors w in v:
     *       - if dist[v]+dist(v,w) < dist[w]:
     *         - dist[w] = dist[v]+dist(v,w)
     *         - PQ.decreaseKey(w,dist[w])  //assign new priority as dist was minimized
     *         - parent[w] = v
     * # Time = O(BFS complexity * decreaseKey) = O((V+E)logV)
     *   -> Assuming we pre-built the heap in O(V) amortized cost & we are using Adjacency List (using matrix rep will increase to V^2)
     *   -> Can be further optimized to O(E+VlogV) using Fibonacci Heap as amortized cost for decrease key goes to E instead of ElogV
     * # Space = O(BFS) = O(V+E)
     */
    static int dijkstra(int V, int src, int dest, int[][] edges) {
        Graph G = new Graph(V, edges);
        return G.findShortestPath(src, dest);
    }

    static class Graph {
        int V;
        List<E>[] adjList;  // 0->1,wt=10  1->2,wt=5
        int[] parent;
        int[] dist;

        Graph(int _V, int[][] edges) {
            V = _V;
            dist = new int[V];
            Arrays.fill(dist, Integer.MAX_VALUE);
            parent = new int[V];
            Arrays.fill(parent, -1);

            adjList = new List[V];
            for (int i = 0; i < V; i++) adjList[i] = new ArrayList<>();

            for (int[] e : edges) {
                int v = e[0]; int w = e[1]; int wt = e[2];
                adjList[v].add(new E(w, wt));
            }
            System.out.println("Adj List : " + Arrays.toString(adjList));
        }

        public int findShortestPath(int src, int dest) {
            // init minPQ with all V & INF weight so that we can minimize it later
            PriorityQueue<E> pq = new PriorityQueue<>((a,b)-> a.wt-b.wt);
            for (int i = 1; i < V; i++) {
                pq.add(new E(i, Integer.MAX_VALUE));
            }
            pq.add(new E(0,0)); // add src vertex
            dist[0] = 0;

            // do bfs, minimize dist at v->w
            while (!pq.isEmpty()) {
                E V = pq.remove();
                int v = V.w;

                /**
                 * No decreaseKey present in Java. When we find a smaller distance for the node v. Then you simply don't
                 * update the distance for the pair (dist_val, v) in the priority queue, but instead insert a new pair
                 * (new_dist_val, v). Your priority queue now contains multiple pairs for v, but this is not a problem.
                 * Since new_dist_val < dist_val, the pair (new_dist_val, v) will be popped earlier from the priority queue
                 * than the pair (dist_val, v). Later, when you pop the pair (dist_val, v) you can recognize it as being
                 * old by comparing dist_val to the current distance of v, i.e. if dist_val != dist_array[v],
                 * you ignore the pair (dist_val, v).
                 */
                if (V.wt != dist[v]) continue;

                for (E W : adjList[v]) {  // for each neighbor w
                    int w = W.w;
                    int newDist = dist[v] + W.wt;
                    if (newDist < dist[w]) {  // shorter dist found
                        dist[w] = newDist;
                        parent[w] = v;  // update parent
                        // just insert a dupe w as decreaseKey operation lacking in java & remove incurs O(n) cost. Read comment above.
                        pq.add(new E(w, newDist));
                    }
                }
            }
            System.out.println("Shortest Dist Arr : " + Arrays.toString(dist));

            // print shortest weighted path
            List<Integer> shortestPath = new ArrayList<>();
            int i = dest;
            shortestPath.add(dest);
            while (i > 0) shortestPath.add(parent[i--]);
            Collections.reverse(shortestPath);
            System.out.println("Shortest Path : " + shortestPath);
            return dist[dest];
        }

    }

    static class E {
        int w, wt;
        E(int _w, int _wt) { w = _w; wt = _wt; }

        @Override
        public String toString() {
            return w + "," + wt;
        }
    }
}
