package com.raj.trees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
     *
     * Another simpler variant:
     * https://leetcode.com/problems/maximum-binary-tree/ (use reverse idx of num -> idx) for linear soln.
     */
    public static void main(String[] args) {
        char I[] = { 'D', 'B', 'E', 'A', 'F', 'C' };    // inorder
        char P[] = { 'A', 'B', 'D', 'E', 'C', 'F' };    // preorder
        Node root = buildBTree_in_pre(I, 0, I.length-1, P);
        printLevelOrder(root); p=0;

        int[] pre;
        root = buildBTree_pre_post(pre = new int[]{1,2,3}, 0, pre.length-1, new int[]{2,3,1});
        printLevelOrder(root); i=0;

        root = buildBTree_pre_post(pre = new int[]{1, 2, 4, 8, 9, 5, 3, 6, 7}, 0, pre.length-1, new int[]{8, 9, 4, 5, 2, 6, 7, 3, 1});
        printLevelOrder(root); i=0;
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
    static Node buildBTree_in_pre(char[] I, int s, int e, char[] P) {
        if (s > e) return null;

        // pick a node from preorder list as root
        Node root = new Node(P[p]);

        // search this root in inorder list b/w s & e. Left subarray is left subtree & right subarray is right subtree.
        int rootIdx = search(I, s, e, P[p]);
        p++;    // move ptr as we have used this node from preorder list as root

        root.left = buildBTree_in_pre(I, s, rootIdx-1, P);     // recursively build left tree
        root.right = buildBTree_in_pre(I, rootIdx+1, e, P);    // recursively build right tree

        return root;
    }

    static Map<Character,Integer> map = null;
    static int search(char[] I, int s, int e, char val) {   // O(n) ... can be made O(1) via a hashmap(char val -> idx) lookup
        if (map == null) {
            map = new HashMap<>();
            for (int i = 0; i < I.length; i++) {
                map.put(I[i], i);  // works only if elems are unique
            }
        }
        return map.get(val);
    }

    /**
     * Build tree from PreOrder & PostOrder
     * https://www.geeksforgeeks.org/full-and-complete-binary-tree-from-given-preorder-and-postorder-traversals/
     * It is not possible to construct a general Binary Tree from preorder and postorder traversals
     * But if know that the Binary Tree is Full, we can construct the tree without ambiguity.
     * pre[]  = {1, 2, 4, 8, 9, 5, 3, 6, 7}
     * post[] = {8, 9, 4, 5, 2, 6, 7, 3, 1}
     *                1
     *         89452      673
     * The value next to 1 in pre[], must be left child of root. So we know 1 is root and 2 is left child.
     * How to find the all nodes in left subtree? We know 2 is root of all nodes in left subtree.
     * All nodes before 2 in post[] must be in left subtree. Now we know 1 is root, elements {8, 9, 4, 5, 2} are in left subtree
     * & the elements {6, 7, 3} are in right subtree.
     *
     * pre[]  = {1, 2, 4, 8, 9, 5, 3, 6, 7}
     *           r  L
     * post[] = {8, 9, 4, 5, 2, 6, 7, 3,  1}
     *            ..... L ....| ... R ...|r
     */
    static int i = 0; // tracks preorder index, need it as global as it needs to be increment b/w recursive calls & backtracking will cause incr val to be lost
    static Node buildBTree_pre_post(int[] pre, int s, int e, int[] post) {
        // Base case
        if (i >= pre.length || s > e) return null;

        // build root
        Node root = new Node(pre[i++]);

        /**
         * Edge case to stop recur if 1 elem left in partition
         * else it'll use up all nodes from pre without right subtree being formed
         */
        if (i >= pre.length || s >= e) {
            return root;
        }

        // make use of post arr to find the left & right partitions
        int bound = findBound(post, pre[i], s, e);

        // recursively build left & right subtree
        root.left = buildBTree_pre_post(pre, s, bound, post);
        root.right = buildBTree_pre_post(pre, bound+1, e-1, post);

        return root;
    }

    static int findBound(int[] post, int n, int s, int e) { // Store Node -> Idx in map to optimize lookups to O(1)
        for (int j = s; j <= e; j++) {
            if (post[j] == n) return j;
        }
        return s;
    }

    static void printLevelOrder(Node root) {
        System.out.println("\nLevel Order print");
        Queue<Node> q = new LinkedList();
        q.add(root);
        while (!q.isEmpty()) {
            Node n = q.remove();
            System.out.print((n.v == 0 ? n.val+"": n.v) + " ");
            if (n.left != null) q.add(n.left);
            if (n.right != null) q.add(n.right);
        }
    }

    static class Node {
        char val;
        int v;
        Node left, right;
        Node(char v) {
            val = v;
        }
        Node(int _v) {
            v = _v;
        }
        public String toString() {
            return v == 0 ? val+"" : v + " " + left + " " + right + " ";
        }
    }

}
