package com.raj.trees;

import java.util.HashMap;
import java.util.Map;

public class BuildBTreeFromTraversals {

    /**
     * https://www.geeksforgeeks.org/if-you-are-given-two-traversal-sequences-can-you-construct-the-binary-tree/
     * The following combination can uniquely identify a tree (Inorder has to be there):
     * Inorder and Preorder.
     * Inorder and Postorder.
     * Inorder and Level-order.
     * Even if three of them (Pre, Post and Level) are given, the tree can not be constructed.
     *
     * https://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/
     */
    public static void main(String[] args) {
        char I[] = { 'D', 'B', 'E', 'A', 'F', 'C' };    // inorder
        char P[] = { 'A', 'B', 'D', 'E', 'C', 'F' };    // preorder
        Node root = buildBTree(I, 0, I.length-1, P);
        System.out.println(root);
    }

    /**
     * Inorder sequence: D B E A F C
     * Preorder sequence: A B D E C F
     * In a Preorder sequence, leftmost element is the root of the tree. So we know ‘A’ is root for given sequences.
     * By searching ‘A’ in Inorder sequence, we can find out all elements on left side of ‘A’ are in left subtree and
     * elements on right are in right subtree. So we know below structure now:
     *           A
     *         /   \
     *      (DBE)  (FC)
     *
     * Recursively follow above steps:
     *            A
     *        B       C
     *      D   E   F
     *
     * Runtime = O(n^2). Worst case occurs when tree is left skewed.
     * Example Preorder and Inorder traversals for worst case are {A, B, C, D} and {D, C, B, A}.
     *
     * Search for root in inorder list can be optimized via HashMap of val -> arr idx
     * Optimized runtime = O(n)
     */
    static int p = 0; // tracks preorder index, need it as global as it needs to be increment b/w recursive calls & backtracking will cause incr val to be lost
    static Node buildBTree(char[] I, int s, int e, char[] P) {
        if (s > e) return null;

        // pick a node from preorder list as root
        Node root = new Node(P[p]);

        // search this root in inorder list b/w s & e. Left subarray is left subtree & right subarray is right subtree.
        int rootIdx = search(I, s, e, P[p]);
        p++;    // move ptr as we have used this node from preorder list as root

        root.left = buildBTree(I, s, rootIdx-1, P);     // recursively build left tree
        root.right = buildBTree(I, rootIdx+1, e, P);    // recursively build right tree

        return root;
    }

    static Map<Character,Integer> map = null;
    static int search(char[] I, int s, int e, char val) {   // O(n) ... can be made O(1) via a hashmap(char val -> idx) lookup
        if (map == null) {
            map = new HashMap<>();
            for (int i = 0; i < I.length; i++) {
                map.put(I[i], i);
            }
        }
        return map.get(val);

        /*for (int i = s; i <= e; i++) {
            if (I[i] == val) return i;
        }
        return -1;*/
    }

    static class Node {
        char val;
        Node left, right;
        Node(char v) {
            val = v;
        }
        public String toString() {
            return " " + val + " " + left + " " + right;
        }
    }

}
