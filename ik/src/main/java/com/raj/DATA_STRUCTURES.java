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
         * Arrays
         * Stack
         * LinkedList / Queue
         * HashMap
         * HashSet
         * LinkedHashMap
         * Heaps - PriorityQueue
         * Trees - BinaryTree, BST
         * Graphs
         * Trie
         * Suffix Tree
         * Segment Trees
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
         * Segment Trees
         * https://www.geeksforgeeks.org/overview-of-data-structures-set-3-graph-trie-segment-tree-and-suffix-tree/#code11
         * https://youtu.be/0l3xN3BpxHg
         *
         */
    }

}
