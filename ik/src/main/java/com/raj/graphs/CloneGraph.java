package com.raj.graphs;

import java.util.*;

public class CloneGraph {

    /**
     * Clone an undirected graph
     */
    public static void main(String[] args) {
        Graph graph = new Graph(new int[][]{
                {1,2},{1,3},{2,3},{2,4},{3,4},{5,6}
        });
        graph.print();
        Graph clonedGraph = graph.cloneGraph();
        clonedGraph.print();
    }

    static class Graph {
        Map<Integer,Node> graphNodes = new HashMap<>();

        Graph() {
        }

        Graph(int[][] inp) {
            for (int[] edge : inp) {
                // build undirected graph, so both ways linking
                // create u,v nodes & add to graph
                int u = edge[0];
                int v = edge[1];
                // u->v
                addChild(u,v);
                // v->u
                addChild(v,u);
            }
        }

        Node addNode(int v) {
            if (!graphNodes.containsKey(v)) graphNodes.put(v, new Node(v));
            return graphNodes.get(v);
        }

        void addChild(int u, int v) {
            Node _u = addNode(u);
            Node _v = addNode(v);
            _u.childs.add(_v);
        }

        /**
         * Traverse Graph and create a clone node
         * Mark visited
         */
        Set<Integer> visited;
        Graph cloneGraph() {
            visited = new HashSet<>();
            Graph clonedGraph = new Graph();
            graphNodes.keySet().forEach(x -> {
                if (!visited.contains(x)) bfs(x, clonedGraph);
            });
            return clonedGraph;
        }

        void bfs(int x, Graph cloneGraph) {
            Queue<Integer> queue = new LinkedList<>();
            visited.add(x);
            queue.add(x);   // add start node

            while (!queue.isEmpty()) {
                int v = queue.poll();
                cloneGraph.addNode(v);          // clone v

                for (Node child : graphNodes.get(v).childs) {   // clone v's child
                    cloneGraph.addChild(v, child.id);
                    if (!visited.contains(child.id)) {
                        visited.add(child.id);       // mark visited
                        queue.add(child.id);  // add unexplored vertex
                    }
                }
            }
        }

        void print() {
            for (int v = 0; v <= graphNodes.size(); v++) {
                if (graphNodes.get(v) != null)
                    System.out.println(graphNodes.get(v).id + " -> " + graphNodes.get(v).childs);
            }
            System.out.println();
        }

    }

    static class Node {
        int id;
        List<Node> childs = new ArrayList<>();
        Node(int _v) {
            id = _v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return id == node.id &&
                    Objects.equals(childs, node.childs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, childs);
        }

        @Override
        public String toString() {
            return id + "";
        }
    }

}
