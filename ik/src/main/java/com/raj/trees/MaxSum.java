package com.raj.trees;

public class MaxSum {
    /**
     * Given a binary tree, find the node & sum which has the maximum sum of nodes under it including itself.
     * Path may not include root. Print the max sum & the corresponding node.
     *
     *       -10
     *      /  \
     *     5    20
     *    / \   /
     *   2  8  -15
     *
     *  o/p : Node 5 => 2+5+8 = 15
     */
    public static void main(String[] args) {
        Node root = new Node(-10);
        root.left = new Node(5);
        root.left.left = new Node(2); root.left.right = new Node(8);
        root.right = new Node(20); root.right.left = new Node(-15);
        Result res = new Result();
        maxSum(root, res);
        System.out.println(res);

        maxPathSum(root);
        System.out.println(max);
    }

    /**
     * [subset pattern] incl/excl node and update result bottom up
     * # dfs - each recursive call returns sum including all nodes under it as well as updates max sum,node as we go up
     */
    static int maxSum(Node node, Result res) {
        if (node == null) return 0;

        // dfs left/right subtree
        int left = maxSum(node.left, res);
        int right = maxSum(node.right, res);

        // include this node & compute sum
        int incl = node.val + left + right;

        // exclude this node - nothing to do

        // update max
        if (incl >= res.maxSum) {
            res.maxSum = incl;
            res.maxNode = node;
        }

        // return sum which includes this node (we can't just return max as nodes above need all sum, imagine a subtree with l=10, r=-16, r=20 - we can't return just 20)
        return incl;
    }

    /**
     * VARIANT of this problem: consider PARTIAL PATHS, o/p = 2+5+8-10+20 = 23
     * https://leetcode.com/problems/binary-tree-maximum-path-sum/
     * Given a non-empty binary tree, find the maximum path sum. For this problem, a path is defined as any sequence of
     * nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at
     * least one node and does not need to go through the root.
     *
     * Note: Partial paths are allowed, hence -ve starting nodes shudn't be taken (no need to start paths with -ve weights)
     */
    static int max = Integer.MIN_VALUE;
    static int maxPathSum(Node root) {
        f(root);
        return max;
    }

    static int f(Node root) {
        if (root == null) return 0;
        // include only if it adds to +ve value, else assume 0. Otherwise -ve leaf nodes will distort sum (partial paths are allowed)
        int leftSum = Math.max(0, f(root.left));
        int rightSum = Math.max(0, f(root.right));
        max = Math.max(max, root.val + leftSum + rightSum);
        return root.val + Math.max(leftSum, rightSum);
    }

    static class Node {
        int val;
        Node left, right;
        Node(int v) {
            val = v;
        }
        public String toString() {
            return val + "";
        }
    }

    static class Result {
        int maxSum;
        Node maxNode;
        public String toString() {
            return "max (node,sum) = " + maxNode + "," + maxSum;
        }
    }

}
