package com.raj.graphs;

import java.util.*;

/**
 * @author rshekh1
 */
public class FindOrderAlienDict {

    /**
     * Find Order Of Characters From Alien Dictionary:
     * Given a sorted dictionary of an alien language, you have to find the order of characters in that language.
     *
     * words = ["baa", "abcd", "abca", "cab", "cad"], output will be:
     * => "bdac"  (b comes before d in words above ...)
     *
     * words = ["caa", "aaa", "aab"]
     * => "cab"
     *
     * Time complexity:
     * In the solution one word will be compared maximum two times. With 1) previous word and 2) next word.
     * So comparing words and finding edges will take O(2 * total number of characters) = O(total number of characters).
     * Also an edge is added when a mismatch is found. Maximum number of mismatch will be <= number of words.
     * So in our directed graph |V| is O(number of different characters) and |E| is O(number of words).
     * We know that topological sort takes O(V + E) time, so that is O(number of different characters + number of words).
     * So our overall time complexity will be O(total number of characters + number of different characters + number of words) = O(total number of characters).
     *
     * Space complexity:
     * Input is O(total number of characters) and graph we will build will be O(number of different characters + number of words).
     * So space complexity is also O(total number of characters).
     */
    public static void main(String[] args) {
        System.out.println(find_order(new String[] {"aaa"})); //a
        System.out.println(find_order(new String[] {"a", "a", "a"})); //a
        System.out.println(find_order(new String[] {"caa", "aaa", "aab"})); //cab
        System.out.println(find_order(new String[] {"baa", "abcd", "abca", "cab", "cad"})); //bdac
    }

    static String find_order(String[] words) {
        if (words == null || words.length == 0) return "";

        // iterate over words char, for each diff, determine pairwise relations,
        // add them to adj list v -> w, create a DAG, then finally do a topo sort
        Graph g = new Graph();
        for (int i = 0; i < words.length - 1; i++) {
            // compare i with i+1 th word to determine pairwise relationship
            String a = words[i], b = words[i+1];
            if (a.equals(b)) continue;
            int j = -1, k = -1;
            while (++j < a.length() && ++k < b.length()) {
                if (a.charAt(j) == b.charAt(k)) continue;
                else {
                    if (a.charAt(j) < b.charAt(k)) g.addEdge(a.charAt(j), b.charAt(k));
                    else g.addEdge(a.charAt(k), b.charAt(j));
                    break;
                }
            }
        }

        // dfs
        for (char v : g.adjList.keySet()) {
            if (g.visited.get(v) == 0) g.dfs(v);
        }

        // handle edge case where the for loop for comparison won't execute for anything less than 2 words or when all words are same alphabets
        if (words.length == 1 || g.order.isEmpty()) return words[0].charAt(0) + "";

        Collections.reverse(g.order);
        String s = "";
        for (char c : g.order) s += c;
        return s;
    }

    static class Graph {
        // v -> w* relations as adjacency list
        Map<Character, List<Character>> adjList = new HashMap<>();
        Map<Character, Integer> visited = new HashMap<>();
        List<Character> order = new ArrayList<>();

        void addEdge(char v, char w) {
            if (!adjList.containsKey(v)) adjList.put(v, new ArrayList<>());
            adjList.get(v).add(w);
            // setting initial states for vertices to avoid NPE
            visited.put(v,0);
            visited.put(w,0);
        }

        void dfs(char v) {
            visited.put(v,1); // visited
            if (adjList.get(v) != null) {
                for (char w : adjList.get(v)) {
                    if (visited.get(w) == 0) dfs(w);
                    if (visited.get(w) == 1) throw new IllegalStateException("Cycle detected !!");
                }
            }
            visited.put(v,2);  // explored completely
            order.add(v);
        }
    }

}
