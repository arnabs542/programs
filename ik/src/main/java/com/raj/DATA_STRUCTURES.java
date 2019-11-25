package com.raj;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DATA_STRUCTURES {

    /**
     * Data Structures in JAVA
     */
    public static void main(String[] args) {

        /**
         * https://www.geeksforgeeks.org/overview-of-data-structures-set-3-graph-trie-segment-tree-and-suffix-tree/#code11
         * List - Arrays, LinkedList(Queue), Stack
         * Map - HashMap, LinkedHashMap, TreeMap
         * Set - HashSet, LinkedHashSet, TreeSet
         * Heaps - PriorityQueue
         * Trees - BinaryTree, BST - PreOrder, InOrder, PostOrder
         * Graphs - BFS, DFS, Visited, Cycle detect
         * Trie - Compact Trie, Suffix Tree
         * Segment Trees - RMQ
         *
         * Ordered Sets :
         * TreeSet maintains sorted collection
         * LinkedHashSet maintains insertion order
         */

        // Build an LRU cache using linkedhashmap
        int MAX_CACHE_SIZE = 10;
        LinkedHashMap<String,String> res = new LinkedHashMap<String,String>() {
            @Override   // Override protected method & define policy to evict entry when size exceeds
            protected boolean removeEldestEntry(Map.Entry<String,String> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };

        // Return a single valued list
        Collections.singleton(-1);

        /**
         * Segment Trees -
         * Use when many operations of range queries(min/max/sum/product) on array and array updates are many as well.
         * For example, finding the sum of all the elements in an array from indices L to R,
         * or finding the minimum (famously known as Range Minimum Query problem) of all the elements in an array from indices L to R.
         * These problems can be easily solved in O(log n) vs Naive solve will incur O(n) range query & O(1) update
         * https://www.geeksforgeeks.org/overview-of-data-structures-set-3-graph-trie-segment-tree-and-suffix-tree/#code11
         * https://youtu.be/0l3xN3BpxHg
         * @see com.raj.trees.SegmentTree
         */
    }

}
