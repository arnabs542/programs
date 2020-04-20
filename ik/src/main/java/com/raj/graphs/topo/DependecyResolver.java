package com.raj.graphs.topo;

import java.util.*;

public class DependecyResolver {
    /**
     * Given a lib dependency u -> v, print how we can optimize by building in parallel
     *
     * Test case: {a=[c, b], b=[], c=[]}
     * Example output: [[b, c], [a]]
     *
     * Test case: {b=[], c=[], d=[], e=[d], a=[c, b]}
     * Example output: [[b, c, d], [a, e]]
     *
     * Test case: {b=[c], c=[], d=[], e=[d], a=[c, b]}
     * Example output: [[c, d], [b, e], [a]]

     === Approach 1 ===
     {a=[c, b], b=[c], c=[], d=[], e=[d]}

     2    0
     a -> c
     |
     v
     b  ret 1 (0+1 in backtracking)
     |
     v
     c  ret 0 (no neighbors)

     1    0
     e -> d


     build adjList Map<String,Set>
     maintain Map visitedLvl(String,Integer) to track visited nodes and their levels
     for each v unvisited node
        dfs(v)
     reduce map into List by lvl -> [0] = leaf nodes, [1] = ...


     dfs(v)
     if already visited, return its lvl
     if no childs add to visited map as v,0 return
     for each child of v:
        lvl = dfs(w)
        add to visitedmap(w,lvl)

     a
     0 -> c b
     1 -> a
     e
     0 -> d
     1 -> e
     parse return nodes by levels

     Time = O(v)
     Space = O(v + max level)
     */
    public static void main(String[] args) {
        // Approach 1 : DFS
        System.out.println(optimizeDependencyDFS(new String[][] {
                {"a","b"}, {"a","c"},
                {"b",""}, {"c",""}
        }));
        System.out.println(optimizeDependencyDFS(new String[][] {
                {"a","b"}, {"a","c"},
                {"b",""}, {"c",""}, {"d",""},
                {"e","d"}
        }));
        System.out.println(optimizeDependencyDFS(new String[][] {
                {"a","b"}, {"a","c"},
                {"b","c"}, {"c",""}, {"d",""},
                {"e","d"}
        }));

        // Approach 2 : BFS Kahns
        System.out.println("==== Using Kahn's Indegree ====");
        System.out.println(optimizeDependencyBFS_Kahns(new String[][] {
                {"a","b"}, {"a","c"},
                {"b",""}, {"c",""}
        }));
        System.out.println(optimizeDependencyBFS_Kahns(new String[][] {
                {"a","b"}, {"a","c"},
                {"b",""}, {"c",""}, {"d",""},
                {"e","d"}
        }));
        System.out.println(optimizeDependencyBFS_Kahns(new String[][] {
                {"a","b"}, {"a","c"},
                {"b","c"}, {"c",""}, {"d",""},
                {"e","d"}
        }));
    }

    static int maxLvl = 0;
    static Map<String,Set<String>> adjMap;
    static Map<String,Integer> visitedByLvl;

    static List<Collection<String>> optimizeDependencyDFS(String[][] edges) {
        adjMap = new HashMap<>();
        visitedByLvl = new HashMap<>();
        for (String[] edge : edges) {
            String u = edge[0];
            String v = edge[1];
            if (!adjMap.containsKey(u)) adjMap.put(u, new HashSet<>());
            if (!v.isEmpty()) adjMap.get(u).add(v);
        }

        for (String v : adjMap.keySet()) {
            dfs(v);
        }

        System.out.println("Adjacency Map => " + adjMap + ", Visited by lvl map => " + visitedByLvl);

        List<Collection<String>> res = new ArrayList<>();
        while (maxLvl-- >= 0) res.add(new HashSet<>());

        for (String v : visitedByLvl.keySet()) {
            res.get(visitedByLvl.get(v)).add(v);
        }
        return res;
    }

    static void dfs(String v) {
        // Base cases
        if (visitedByLvl.containsKey(v)) return;  // visited then return
        if (adjMap.get(v) == null || adjMap.get(v).isEmpty()) {   // if leaf node, then set lvl = 0 & return
            visitedByLvl.put(v,0);
            return;
        }

        int lvl = 0;  // compute max lvl
        for (String w : adjMap.get(v)) {   // explore childs
            dfs(w);
            lvl = Math.max(visitedByLvl.get(w), lvl);  // a->b->c  a->b  , we take max lvl
        }
        visitedByLvl.put(v, lvl+1);   // set lvl for v
        maxLvl = Math.max(maxLvl, lvl+1);
    }

    /**
     * Approach 2: Kahn's algo which is simpler given the problem.
     * BFS Layer-wise peeling & updating lvl,{vertices} tuple
     *
     * Build adjMap
     * Compute in-degrees in map<v,int> in reverse order why?
     *      {a=[b, c], b=[c], c=[], d=[], e=[d]}
     *      b ----> a <---- c     d ---> e
     * We need to unlock c & d first so their in-degrees better be 0, else it won't work
     *
     * Add leaf tuple(v,0) nodes to Q.
     * iter Q:
     *   pop v. Add to nodesByLvl(0,{v}).
     *   reduce in-degree of childs
     *   if they become 0, add to Q
     */
    static List<Collection<String>> optimizeDependencyBFS_Kahns(String[][] edges) {
        adjMap = new HashMap<>();
        Map<String,Integer> inDegree = new HashMap<>();
        for (String[] edge : edges) {
            String u = edge[0];
            String v = edge[1];
            if (!adjMap.containsKey(v)) adjMap.put(v, new HashSet<>());
            if (!v.isEmpty()) {
                adjMap.get(v).add(u);
                inDegree.put(v, inDegree.getOrDefault(v, 0));
                inDegree.put(u, inDegree.getOrDefault(u, 0) + 1);
            }
        }
        System.out.println("Reverse Adjacency Map => " + adjMap + ", Indegree Map => " + inDegree);

        Queue<Pair> Q = new LinkedList<>();
        // add to Q start nodes (indegree = 0)
        for (String v : inDegree.keySet()) {
            if (inDegree.get(v) == 0) Q.add(new Pair(v,0));
        }
        System.out.println(Q);
        // compute node lvl map
        Map<Integer,Set<String>> mapByLvl = new HashMap<>();
        int maxLvl = 0;
        while (!Q.isEmpty()) {
            Pair pr = Q.poll();

            // add to lvl map this node
            if (!mapByLvl.containsKey(pr.lvl)) mapByLvl.put(pr.lvl, new HashSet<>());
            mapByLvl.get(pr.lvl).add(pr.v);
            maxLvl = Math.max(maxLvl, pr.lvl);

            // update indegrees of neighbors & add newly found 0 degreed nodes
            if (adjMap.get(pr.v) == null) continue;
            for (String w : adjMap.get(pr.v)) {
                int deg = inDegree.get(w);
                deg--;
                inDegree.put(w, deg);
                if (deg == 0) Q.add(new Pair(w, pr.lvl+1));
            }
        }

        System.out.println(mapByLvl);
        // parse map to desired output
        List<Collection<String>> res = new ArrayList<>();
        int lvl = 0;
        while (mapByLvl.containsKey(lvl)) {
            res.add(mapByLvl.get(lvl++));
        }
        return res;
    }

    static class Pair {
        String v;
        int lvl;
        Pair(String _v, int _lvl) {
            v = _v;
            lvl = _lvl;
        }

        @Override
        public String toString() {
            return v +  " " + lvl;
        }
    }

}
