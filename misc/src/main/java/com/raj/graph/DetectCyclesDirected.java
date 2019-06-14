package com.raj.graph;

/**
 * @author rshekh1
 */
public class DetectCyclesDirected {

    public static void main(String[] args) {
        // build graph
        Graph graph = new Graph();
        graph.addVertexWithAdj("A", "B", "C");
        graph.addVertexWithAdj("B", "C", "D");
        graph.addVertexWithAdj("D", "A");   // cycle
        graph.addVertexWithAdj("C", "E");

        // another disconnected graph
        graph.addVertexWithAdj("F", "G");
        graph.addVertexWithAdj("G", "H");
        graph.addVertexWithAdj("H", "F");   // cycle

        System.out.println(graph);
        detectCycle(graph);
    }

    public static void detectCycle(Graph graph) {
        System.out.println("DFS & detect cycle, if any => ");
        graph.allVertices.forEach((k,v) -> detectCycleDFS(v));
        Graph.visited.clear();
        Graph.recursionStack.clear();
        System.out.println(" >>> No cycles present ...");
    }

    /**
     *      *      ==============
     *      *      Graph representation:
     *      *      A --> B <-- null        E <-- null
     *      *      |   / |
     *      *      v v   v
     *      *      C --> D -----> A (cycle)
     *      *
     */

    public static void detectCycleDFS(Graph.Vertex v) {
        // base case
        if (Graph.recursionStack.contains(v)) {      // cycle detect
            String msg = v.value;
            System.out.println();
            while (!Graph.recursionStack.isEmpty()) msg += " <- " + Graph.recursionStack.pop().value;
            throw new IllegalStateException("Cycle detected [" + msg + "], aborting !! ");
        }
        if (Graph.visited.contains(v)) return;  // already processed

        // process
        System.out.print(v.value + ", ");

        // add to visited & recursion recursionStack
        Graph.visited.add(v);
        Graph.recursionStack.push(v);

        // now visit it's adjacent vertices
        v.adjVertices.forEach(adjV -> detectCycleDFS(adjV));

        // done with this vertex, remove from rec recursionStack
        Graph.recursionStack.pop();
    }

}
