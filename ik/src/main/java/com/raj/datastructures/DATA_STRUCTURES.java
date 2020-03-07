package com.raj.datastructures;

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
         * Heaps - PriorityQueue, Fibonacci Heaps (Constant time Insert + DecreaseKey)
         * Trees - BinaryTree, BST - PreOrder, InOrder, PostOrder
         * Graphs - BFS, DFS, Visited, Cycle detect
         * Trie - Compact Trie, Suffix Tree
         * Segment Trees - RMQ
         * Skip List - https://www.geeksforgeeks.org/skip-list/ - express lane hierarachy index over LL - O(logn) - used in highly scalable concurrent priority queues with less lock contention
         *
         * Ordered Sets :
         * TreeSet maintains sorted collection
         * LinkedHashSet maintains insertion order
         *
         * Big-O Cheat Sheet - Know Thy Complexities:
         * https://www.bigocheatsheet.com/
         *
         * === Binary Heap Complexities ===
         * Heapify a single node takes O(log n) time complexity where n is the total number of Nodes.
         * Therefore, building the entire Heap will take N heapify operations. Hence, Total time complexity => O(N*logN)
         *
         * ----> BUT in reality, building a heap takes O(n) time. How?
         * # The basic idea behind why the time is linear is due to the fact that the time complexity of heapify depends
         * on where it is within the heap.
         * # It takes O(1) time when the node is a leaf node (which makes up 50% of the nodes) & O(logn) time when it’s at the root.
         * # Almost 90% of the nodes of a complete binary tree reside in the 3 lowest levels. Thus the lesson to be
         * learned is that when designing algorithms that operate on trees, it is important to be most efficient on the
         * bottommost levels of the tree (as BuildHeap is) since that is where most of the weight of the tree resides.
         *
         * https://www.geeksforgeeks.org/building-heap-from-array/ (MAX HEAP)
         * Optimized Approach: The above approach can be optimized by observing the fact that the leaf nodes need not to
         * be heapified as they already follow the heap property. Also, the array representation of the complete binary
         * tree contains the level order traversal of the tree.
         *
         * So the idea is to find the position of the last non-leaf node and perform the heapify operation of each
         * non-leaf node in reverse level order.
         *
         * Any other operation on Heap takes O(log n)
         */

        // Build an LRU cache using linkedhashmap
        int MAX_CACHE_SIZE = 10;
        LinkedHashMap<String,String> res = new LinkedHashMap<String,String>() {
            @Override   // Override protected method & define policy to evict entry when size exceeds
            protected boolean removeEldestEntry(Map.Entry<String,String> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };

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
