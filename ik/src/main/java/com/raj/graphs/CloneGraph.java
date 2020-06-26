package com.raj.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloneGraph {

    /**
     * Clone an undirected graph
     * Given a reference of a node in a connected undirected graph.
     * Return a deep copy (clone) of the graph.
     *
     * Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
     * Output: [[2,4],[1,3],[2,4],[1,3]]
     * Explanation: There are 4 nodes in the graph.
     * 1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
     * 2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
     * 3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
     * 4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
     */
    public static void main(String[] args) {
        Graph graph = new Graph(new int[][]{{2,4},{1,3},{2,4},{1,3}});
        graph.print();
        Node cloneGraph = graph.clone(graph.nodes.get(1));
    }

    /**
     * Clone grap nodes in DFS manner
     * Graph may have cycles, but we need to still attach childs even if they are part of cycle
     * Use map of cloned nodes to track already cloned nodes and return if we again look for it
     */
    static class Graph {
        Map<Integer,Node> nodes = new HashMap<>();

        Graph(int[][] adjList) {
            for (int i = 0; i < adjList.length; i++) {
                int v = i+1;
                if (!nodes.containsKey(v)) {
                    Node n = new Node(v);
                    nodes.put(v, n);
                }
                Node n = nodes.get(v);
                for (int w : adjList[i]) {
                    Node child = null;
                    if (!nodes.containsKey(w)) nodes.put(w,new Node(w));
                    n.childs.add(nodes.get(w));
                }
            }
        }

        Map<Integer,Node> clonedNodes = new HashMap<>();
        Node clone(Node n) {
            if (n == null) return null;
            if (clonedNodes.containsKey(n.val)) return clonedNodes.get(n.val);
            Node clone = new Node(n.val);
            clonedNodes.put(n.val,clone);
            for (Node child : n.childs) {
                clone.childs.add(clone(child));
            }
            return clone;
        }

        void print() {
            for (Integer v : nodes.keySet()) {
                System.out.println(nodes.get(v));
            }
        }

    }

    static class Node {
        int val;
        List<Node> childs = new ArrayList<>();
        Node(int _v) {
            val = _v;
        }

        @Override
        public String toString() {
            String childsStr = "";
            for (Node c : childs) childsStr += c.val + " ";
            return val + " => " + childsStr;
        }
    }

    /*public static void main(String[] args) {
        Graph graph = new Graph(new int[][]{
                {1,2},{1,3},{2,3},{2,4},{3,4},{5,6}
        });
        graph.print();
        Graph clonedGraph = graph.cloneGraph();
        clonedGraph.print();
    }

    static class GraphDFS {
        Map<Integer,Node> adjMap = new HashMap<>();
        GraphDFS(int[][] adjList) {
            for (int i = 0; i < adjList.length; i++) {
                int v = i+1;

            }
        }
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

        *//**
     * Traverse Graph and create a clone node
     * Mark visited
     *//*
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
    }*/

}
