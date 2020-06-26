package com.raj.graphs.topo;

import java.util.*;

/**
 * @author rshekh1
 */
public class CourseSchedule {

    /**
     * You need to take n courses. Few of these courses have prerequisites.
     * You are given the prerequisites as a list of pairs where each pair is of form : [x, y]
     * where to take course 'x', you need to complete course 'y' before it.
     * Given these pairs and also the count of total courses n, you need to return the ordering in which
     * the courses should be taken.
     *
     * https://oj.interviewkickstart.com/view_editorial/5370/132/
     *
     * This is a typical topological sorting problem which can be solved using DFS.
     * TOPOLOGICAL SORT: https://www.geeksforgeeks.org/topological-sorting/  (ordering of graph's vertices)
     * Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices such that for every
     * directed edge uv, vertex u comes before v in the ordering. Topological Sorting for a graph is not possible
     * if the graph is not a DAG.
     *
     * A directed graph is formed using each prerequisite as an edge. Note that a possible ordering only exists if there
     * is no cycle in the directed acyclic graph formed. To find cycle in a directed acyclic graph, we keep three
     * visited states (0 for a node which has not been visited yet, 1 for those who are in current DFS stack
     * and 2 for those whose all children are completely visited.
     *
     * Example:
     * n=4, e=4 and prerequisites=[ [1, 0], [2, 0], [3, 1], [3, 2] ]
     *
     * Here we have 4 nodes and 4 edges. We create a graph with directed edges using each prerequisite.
     * The graph for this particular example can be represented like -
     *   _______
     *  |      |
     *  V      |
     *  0     1 <---- 3 -----> 2
     *  ^                      |
     *  |______________________|
     *
     * Here node '0' has no outward edge, which means course '0' requires no other course to be completed before it
     * to be taken. Hence course '0' can be taken as the first course. After course '0' is taken, we are eligible to
     * take course '1' and course '2' as they have no other prerequisite other than course '0'. We can choose any
     * course, let us take course '2' as our second course. Now we can only take course '1' as course '3' can't be taken
     * before course '1'. So we take course '1' as our next course and course '3' as our last course.
     * One possible answer turns out to be [0 2 1 3]. Also, note that [0 1 2 3] is also a correct ordering of courses.
     *
     * To find this ordering, we do DFS on this graph. As we reach the deepest node in the graph, we backtrack.
     * Here the deepest node irrespective of from where we start our DFS is node '0' (as it has no outgoing edges).
     * At the moment, when we are done traversing all the children and subtrees of them, we add this node id as the
     * next course to be taken in the answer array.
     *
     * bool dfs (int current_node) {
     *
     * put current_node in dfs stack. (visited turns to 1 from 0 for current_node)
     *
     * for ( all the child nodes of current node) {
     *  if (child_node has state 1) {
     *      cycle found
     *  }
     *
     *  if (child_node has state 0) {
     *      call dfs(child_node)
     *  }
     * }
     *
     * all child nodes are done. Add current_node to answer array.
     * Mark current_node as visited (state = 2)
     * }
     *
     * To find for cycles (no possible ordering), we keep three states of each node. If at any time we encounter
     * the visited state of child node is 1, that is there is a back edge in current stack, hence forming a cycle and
     * no possible ordering of courses.
     *
     * Time Complexity:
     *
     * O(n+e) where n denotes number of courses and e denotes number of prerequisites dependencies that is size of
     * 2d array prerequisites.
     * DFS takes O(n+e) to iterate over graph having n nodes and e edges.
     *
     * Auxiliary Space Used:
     * O(n+e) where n denotes number of courses and e denotes number of prerequisites dependencies that is size of 2d
     * array prerequisites. With n nodes and e edges, we can have maximum n nodes in function stack during DFS.
     * To create graph form given information takes O(n+e) space. Hence total auxiliary space used is
     * O(n) + O(n+e) --> O(n+e).
     *
     * Space Complexity:
     * O(n+e) where n denotes number of courses and e denotes number of prerequisites dependencies that is size of
     * 2d array prerequisites.
     *
     * To store e edges it will takes O(e) and auxiliary space used is O(n+e). So, total space complexity will be
     * O(e) + O(n+e) --> O(n+e).
     */
    public static void main(String[] args) {
        List<List<Integer>> prerequisites = new ArrayList<>();
        /**
         * 1  0
         * 2  0
         * 3  1
         * 3  2
         * o/p => 0,1,2,3 or 0,2,1,3
         */
        prerequisites.add(new ArrayList<>(Arrays.asList(1,0)));
        prerequisites.add(new ArrayList<>(Arrays.asList(2,0)));  // try 2,3 for creating a cycle
        prerequisites.add(new ArrayList<>(Arrays.asList(3,1)));
        prerequisites.add(new ArrayList<>(Arrays.asList(3,2)));

        // Topo sort can only happen for a DAG, so make sure we break out if there is a loop

        // Method 1 - DFS
        System.out.println("Topo sort using DFS (Course schedule order) => " + course_schedule(4, prerequisites));

        // Method 2 - Kahn's algo for Topo sort using in-degrees of vertices
        System.out.println("Topo sort using Kahn's in-degree method => " + course_schedule_kahns(4, prerequisites));
    }

    // DFS (modified) topo sort - idea is to first visit all neighboring nodes then process this node
    static List<Integer> course_schedule(int n, List<List<Integer>> prerequisites) {
        Graph g = new Graph(n);
        for (List<Integer> p: prerequisites) {
            g.addEdge(p.get(0), p.get(1));
        }

        // run thru each unvisited vertex 0...n-1
        for (int i = 0; i < n; i++) {
            try {
                if (g.isVisited[i] == 0) g.scheduleCourse(i);
            } catch (IllegalStateException e) {
                System.out.println("Cycle detected, can't do Topo sort !!");
                return new ArrayList<>(Arrays.asList(-1));
            }
        }
        return g.schedule;
    }

    // Kahn's topo sort - idea is a DAG will always have at least one zero-indegree vertex where we start from and reduce indegrees of neighbors. We keep adding zero indegrees to queue.
    static List<Integer> course_schedule_kahns(int n, List<List<Integer>> prerequisites) {
        Graph g = new Graph(n);
        for (List<Integer> p: prerequisites) {
            g.addEdge(p.get(0), p.get(1));
        }

        // compute in-degree of each vertex, v->w => inDegree[w]++
        int[] inDegree = new int[n];
        Arrays.stream(g.adjList).forEach(v -> v.forEach(w -> {
            inDegree[w]++;
        }));

        // Initialize count of visited vertices
        int cnt = 0;

        // find zero in-degree vertices (which is our starting points) add it to queue
        Queue<Integer> queue = new LinkedList<>();
        for (int v = 0; v < inDegree.length; v++) if (inDegree[v] == 0) queue.add(v);

        // for each v->w pair, reduce in-degrees & add any new zero in-degree vertex to queue. Also, add it to output in reverse order
        while (!queue.isEmpty()) {
            int v = queue.poll();
            g.schedule.add(v); // add to schedule but output will be reverse order
            for (int w : g.adjList[v]) {
                inDegree[w]--;
                if (inDegree[w] == 0) {
                    queue.add(w);
                }
            }
            cnt ++;  // process exactly n vertices, else cycle
        }
        // check for cycles as topo sort can only happen for a DAG
        if (cnt != n) {
            System.out.println("Cycle detected, can't do Topo sort !!");
            return new ArrayList<>(Arrays.asList(-1));
        }
        Collections.reverse(g.schedule);
        return g.schedule;
    }

    static class Graph {
        List<Integer>[] adjList;
        int V;
        int[] isVisited;
        List<Integer> schedule = new ArrayList<>();

        Graph(int n) {
            V = n;
            adjList = new List[n];
            for (int i = 0; i < n; i++) adjList[i] = new ArrayList<>();
            // init each array vertex
            isVisited = new int[n];     // 0 -> unvisited, 1 -> visited & in current DFS stack, 2 -> all children visited
        }

        void addEdge(int v, int w) {
            adjList[v].add(w);
        }

        // uses arrival / departure time template
        void scheduleCourse(int v) {
            isVisited[v] = 1;   // mark visited only - in current dfs stack, will be useful for cycle detection
            for (int w : adjList[v]) {
                if (isVisited[w] == 0) scheduleCourse(w);   // do dfs, if unvisited
                else if (isVisited[w] == 1) throw new IllegalStateException("Cycle / Back Edge detected, can't schedule courses !!");
            }
            isVisited[v] = 2;   // mark all children explored & done
            schedule.add(v);    // which wud mean that this course can now be scheduled as all dependencies are met
        }

    }

}
