package com.raj.graph;

import java.util.*;

/**
 * @author rshekh1
 */
public class Graph {

    public Map<String,Vertex> allVertices = new HashMap<>();
    public List<Edge> allEdges = new ArrayList<>();

    // helper DS
    public static Set<Vertex> visited = new LinkedHashSet<>();  // preserves insertion ordering
    public static Stack<Vertex> recursionStack = new Stack<>();  // recursion recursionStack

    public Vertex addVertex(String val) {
        Vertex vertex = allVertices.get(val);
        if (vertex == null) {
            vertex = new Vertex(val);
            allVertices.put(val, vertex);
        }
        return vertex;
    }

    public Vertex addVertexWithAdj(String v, String... adjV) {
        Vertex vertex = allVertices.get(v);
        if (vertex == null) {
            vertex = new Vertex(v);
            allVertices.put(v, vertex);
        }
        vertex.addAdjacentVertices(adjV);
        return vertex;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder("GRAPH => ");
        allVertices.forEach((k,v) -> str.append(k + "->" + v.adjVertices + ", "));
        return str.toString();
    }

    public class Vertex {
        public String value;
        public List<Vertex> adjVertices = new ArrayList<>();
        public List<Edge> edges = new ArrayList<>();

        public Vertex(String value) {
            this.value = value;
        }

        public void addAdjacentVertices(String... vals) {
            Arrays.stream(vals).forEach(x -> {
                Vertex vertex = addVertex(x);
                adjVertices.add(vertex);
            });
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return Objects.equals(value, vertex.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public static class Edge {
        boolean isDirected;
        Vertex v1,v2;
        int weight;

        public Edge(Vertex v1, Vertex v2, int weight, boolean isDirected) {
            this.isDirected = isDirected;
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }
    }

    /*@Override
    public String toString() {
        return "Graph{" +
                "allVertices=" + allVertices +
                ", allEdges=" + allEdges +
                '}';
    }*/

}
