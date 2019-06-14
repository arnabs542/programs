package com.raj.scheduling;

import com.google.common.collect.Lists;
import com.raj.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class TaskSchedulerDAG {
    /**
     * Say you are building an application to assemble a robot. A robot consists of list of pieces that you
     * need to assemble in order. Each piece has zero or more dependencies on another piece.
     * For example, if piece B has a dependency on piece A, then A must be assembled before B.
     * In this problem, you are given a list of pieces along with their list of dependencies,
     * and your goal should be to print a valid order to assemble the pieces.

     For example, given:
     A -> []
     B -> [A]
     C -> [B]
     => [A,B,C]

     A -> [B]
     B -> []
     C -> [A]
     D -> [B,C]
     => B,A,C,D

     */
    private static Map<String, List<String>> tasksMap = new HashMap<>();

    public static void main(String args[] ) {
        tasksMap.put("A", Lists.newArrayList("B"));
        tasksMap.put("B", Lists.newArrayList(""));
        tasksMap.put("C", Lists.newArrayList("A", "B"));
        tasksMap.put("D", Lists.newArrayList("B", "C"));
        System.out.print("\n" + tasksMap + " => "); assemble(tasksMap);

        /*tasksMap.clear(); //completed.clear();
        tasksMap.put("A", Lists.newArrayList("B"));
        tasksMap.put("B", Lists.newArrayList("C"));
        tasksMap.put("C", Lists.newArrayList("A", "B"));
        tasksMap.put("D", Lists.newArrayList("B", "C"));
        System.out.print("\n" + tasksMap + " => "); assemble(tasksMap);*/
    }

    /**
     * Best way to solve this is via DAG with a start.
     * If we are able to visit all nodes (can only visit a node if there is no other incoming edge whose vertex isn't visited)
     * Edge cases:
     * Cycle ? If start doesn't exist, it's a cycle. Also, if u reach the same node via traversal, it's cycle.
     * Graph is disconnected ? Try with different node to solve
     *
     *      A -> B
     *      B -> x
     *      C -> A,B
     *      D -> B,C
     *      E -> x
     *      ==============
     *      Graph representation:
     *      A <-- B <-- null        E <-- null
     *      |   / |
     *      v v   v
     *      C --> D
     *
     *      o/p : B,A,C,D,E
     *
     *
     */
    private static Graph graph = new Graph();

    public static List<String> assemble(Map<String, List<String>> tasksMap) {
        // build graph, possibly identifying good start nodes
        buildGraph(tasksMap);

        // detect cycles, throws error
        //detectCycle();

        // solve
        solve();
        Iterator it = visited.iterator();
        while (it.hasNext()) System.out.println(it.next());
        return Lists.newArrayList(visited);
    }

    private static void buildGraph(Map<String, List<String>> tasksMap) {
        tasksMap.forEach((k,v) -> {
            v.forEach(vertex -> graph.addVertexWithAdj(vertex, k));
        });
    }

    private static List<String> solve() {
        final List<String> res = new ArrayList<>();
        // DFS
        graph.allVertices.forEach((k,v) -> {
            doDFS(v);
        });
        return res;
    }

    private static LinkedHashSet<String> visited = new LinkedHashSet<>();

    private static void doDFS(Graph.Vertex v) {
        // base case
        if (visited.contains(v.value)) return;

        // if vertex predecessors are visited(dep are met) or have no dependency, we can process
        if (tasksMap.get(v.value) == null || tasksMap.get(v.value).isEmpty()
                || visited.containsAll(tasksMap.get(v.value).stream().collect(Collectors.toSet()))) {
            visited.add(v.value);
            // dfs adjacent vertices
            v.adjVertices.forEach(adjV -> doDFS(adjV));
        }
    }
    /*private static boolean dfs(Node n) {
        if (n.outgoing.isEmpty() && visited.size() != graphNodes.size()) return false;
        if (visited.contains(n)) throw new IllegalStateException("Cycle detected !");
        n.outgoing.forEach(x -> {
            visited.add(x); // try adding
            if (!dfs(x)) visited.remove(x);
        });

    }*/


}
