package com.raj.graphs;

import java.util.*;

/**
 * @author rshekh1
 */
public class TransposeGraph {

    public static void main(String[] args) {
        /**
         * Reverse edges of a strongly connected graph. Also called Transpose Graph.
         * 1->2
         * 2->3
         * 3->1
         * =>  [(2 -> 1), (3 -> 2), (1 -> 3)]
         */
        _Graph g = new _Graph();
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(3,1);
        Node reversedNode = build_other_graph(g.nodes.get(1));
        System.out.println(reversedNode);
    }

    static Node build_other_graph(Node node) {
        Graph g = new Graph();
        g.dfs(node);
        return g.nodes.entrySet().iterator().next().getValue();
    }

    static class Graph {
        Map<Integer,Node> nodes = new HashMap<>();

        // we create nodes as first step, then link while stack is unwinding
        void dfs(Node v) {

            // create new nodes -  doubles up as visited marking, plus we create nodes for linking later
            Node _v = new Node(v.val);
            nodes.put(v.val, _v);

            for (Node w : v.neighbours) {

                if (!nodes.containsKey(w.val)) dfs(w);  // nodes map doubles up as visited

                // now link while recursion stack is unwinding w->v, for each v-> w pairs, it works naturally
                nodes.get(w.val).neighbours.add(nodes.get(v.val));
            }
        }
    }

    // alternate solve, just it differs in the sense that we first create the reverse link then dfs
    static Node _build_other_graph(Node node) {
        _Graph graphT = new _Graph();
        dfs_reverseNodes(node, graphT);
        return graphT.nodes.get(node.neighbours.get(0).val);
    }

    static void dfs_reverseNodes(Node v, _Graph graphT) {
        graphT.visited.add(v);
        for (Node w : v.neighbours) {
            // build reverse w->v for each v->w pairs before going deep
            graphT.addEdge(w.val, v.val);
            if (!graphT.visited.contains(w)) dfs_reverseNodes(w, graphT);
        }
    }

    static class _Graph {
        // map of vertices
        Map<Integer,Node> nodes = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        void addEdge(int v, int w) {
            // create vertices if not exist
            if (!nodes.containsKey(v)) nodes.put(v, new Node(v));
            if (!nodes.containsKey(w)) nodes.put(w, new Node(w));
            // link them v->w
            nodes.get(v).neighbours.add(nodes.get(w));
        }
    }

    static class Node {
        Integer val;
        Vector<Node> neighbours = new Vector<>(0);
        Node(Integer _val) {
            val = _val;
            neighbours.clear();
        }
        public String toString() {
            return val + "";
        }
    }
}
