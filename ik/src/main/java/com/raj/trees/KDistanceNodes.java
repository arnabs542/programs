package com.raj.trees;

public class KDistanceNodes {
    /**
     * https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
     * All Nodes Distance K in Binary Tree
     * We are given a binary tree (with root node root), a target node, and an integer value K.
     * Return a list of the values of all nodes that have a distance K from the target node.
     */
    public static void main(String[] args) {

    }

    /**
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
     * Output: [7,4,1]
     *                  3
     *            5          1
     *         6    2      0   8
     *            7   4
     *
     * Approach 1: [Pre-compute node -> parent map + BFS K dist nodes]
     * # Pre-compute & build a map of node -> parent. Now this becomes a graph problem.
     * # Keep a visited node set to not go in cycles
     * # Add target node T into Q with dist=0
     * # Pop Q:
     *   - if dist of node = K, print it
     *   - else find it's neighbors ie. L,R & parent +1 dist & add to Q.
     * Time/Space = O(n)
     */

    /**
     * Approach 2: [No aux space, 2 scan of Tree]
     * # Case 1: From target node, BFS & find K dist nodes following it's left & right.
     * # Case 2: Now nodes which are K dist ancestors, remains.
     * # From root, find target node in Left/Right subtree. Compute the dist of root to target node.
     * # Say, T lies in left & dist of root from T is d.
     *   - Go  left, if d > K, decr dist & print the node when d = K
     *   - Go right, if d < K, incr dist & print the node when d = K
     * O(n) Time
     */
    static void printNodesKDistApart(Node root, Node T, int K) {
        printNodesKDistFromNode(T, K);
        boolean isTInLeft = true;
        int d = findT(root.left, T, 1);
        if (d == -1) { // didn't find in left, try right
            findT(root.right, T, 1);
            isTInLeft = false;
        }
        if (d == K) {   // root is already K dist away
            System.out.println(root.val);
        } else if (d > K) {  // root is more than K dist away go nearer to T
            printNodesKDistFromNode(isTInLeft ? root.left : root.right, d-1);
        } else if (d < K) {  // root is less than K, move away from target in other subtree
            printNodesKDistFromNode(isTInLeft ? root.right : root.left, d+1);
        }
    }

    static void printNodesKDistFromNode(Node T, int K) { // dfs from T
        if (T == null) return;
        if (K == 0) {    // found K dist away node from T
            System.out.println(T.val);
            return;
        }
        printNodesKDistFromNode(T.left, K-1);
        printNodesKDistFromNode(T.right, K-1);
    }

    static int findT(Node n, Node T, int dist) {
        if (n == null) return -1;
        if (n.val == T.val) return dist;
        int left = findT(n.left, T, dist+1);
        int right = findT(n.right, T, dist+1);
        return left != -1 ? left : right;
    }

    static class Node {
        Node left,right;
        int val;
    }
}
