package com.raj.graph;

/**
 * @author rshekh1
 */
public class DFS {

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
        System.out.println("DFS => ");
        dfs(graph.allVertices.get("B"));
        Graph.visited.clear();
    }

    public static void dfs(Graph.Vertex v) {
        // base case
        if (Graph.visited.contains(v)) return;
        // process
        System.out.print(v.value + ", ");
        // add to visited
        Graph.visited.add(v);
        // now visit it's adjacent vertices
        v.adjVertices.forEach(adjV -> dfs(adjV));
    }

}
