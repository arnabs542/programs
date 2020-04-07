package com.raj.trees;

public class MaxSum {
    /**
     * Given a binary tree, find the node which has the maximum sum of nodes including itself.
     * Print the max sum & the corresponding node.
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
