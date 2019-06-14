package com.raj.scheduling;

import com.raj.graph.DetectCyclesDirected;
import com.raj.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author rshekh1
 */
public class TopologicalSort {

    /**
     * Given a directed acyclic graph, do a topological sort on this graph.
     *
     * Do DFS by keeping visited. Put the vertex which are completely explored into a recursionStack.
     * Pop from recursionStack to get sorted order.
     *
     * Space and time complexity is O(n).
     * https://www.geeksforgeeks.org/topological-sorting/
     *
     * "Topological Sorting vs Depth First Traversal (DFS):
     * In DFS, we print a vertex and then recursively call DFS for its adjacent vertices.
     * In topological sorting, we need to print a vertex before its adjacent vertices."
     *
     */
    public static void main(String[] args) {
        // build graph
        Graph graph = new Graph();
        graph.addVertexWithAdj("A", "C");
        graph.addVertexWithAdj("B", "C", "D");
        graph.addVertexWithAdj("C", "E");
        graph.addVertexWithAdj("D", "F");
        graph.addVertexWithAdj("E", "H", "F");
        graph.addVertexWithAdj("F", "G");
        System.out.println(graph);

        // do simple dfs
        //System.out.println("DFS => ");
        //dfs(graph.allVertices.get("B"));
        //graph.allVertices.forEach((k,v) -> dfs(v));
        //System.out.println(Graph.visited);
        //Graph.visited.clear();

        // check for cycles
        DetectCyclesDirected.detectCycle(graph);
        // do topo sort now
        System.out.println("\nTOPO Sort (for a directed graph u->v, u will always show up before v) => ");

        graph.allVertices.forEach((k,v) -> doDFS(v));
        while (!Graph.recursionStack.isEmpty()) System.out.print(Graph.recursionStack.pop() + ",");
    }

    public static void dfs(Graph.Vertex v) {
        // base case
        if (Graph.visited.contains(v)) return;
        // process
        // System.out.print(v.value + ", ");

        // add to visited
        Graph.visited.add(v);
        // now visit it's adjacent vertices
        v.adjVertices.forEach(adjV -> dfs(adjV));
    }

    /*public static void topSort(Graph graph) {
        graph.allVertices.forEach((k,v) -> {
            if (!Graph.visited.contains(k)) doDFS(v);
        });
        *//*doDFS(graph.allVertices.get("E"));
        doDFS(graph.allVertices.get("B"));
        doDFS(graph.allVertices.get("A"));*//*
    }*/

    public static void doDFS(Graph.Vertex v) {
        if (Graph.visited.contains(v)) return;
        Graph.visited.add(v);
        v.adjVertices.forEach(adjV -> {
            if (!Graph.visited.contains(adjV)) doDFS(adjV);
        });
        Graph.recursionStack.push(v);
    }

    private static class Node {
        String val;
        List<Node> outgoing = new ArrayList<>();
        Node(String v) {val = v;}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(val, node.val);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

}
