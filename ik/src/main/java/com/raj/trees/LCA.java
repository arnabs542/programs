package com.raj.trees;

public class LCA {

    /**
     * You are given root of a binary tree T of n nodes. You are also given references of two nodes a & b.
     * You need to find the lowest common ancestor of a and b. Least or lowest common ancestor (LCA) of two nodes
     * is defined as the lowest node in T that has the two nodes as descendants.
     * For this problem, we allow a node to be an ancestor/descendant of itself.
     */
    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2); root.left.left = new Node(4); root.left.right = new Node(5);
        root.left.right.left = new Node(8); root.left.right.right = new Node(9);
        root.right = new Node(3); root.right.left = new Node(6); root.right.right = new Node(7);
        System.out.println(lca(root, new Node(4), new Node(9)));
    }

    /**
     * O(n) runtime & O(log n) space which tree height for recursive call stack
     */
    static Node lca(Node root, Node a, Node b) {
        if (root == null) return null;
        if (root.data == a.data || root.data == b.data) return root;

        // Is a or b in left subtree ?
        Node left = lca(root.left, a, b);

        // Is a or b in right subtree ?
        Node right = lca(root.right, a, b);

        // a & b are in left & right subtree or vice verse
        if (left != null && right != null) return root;

        // if either left or right isn't null it means, that subtree has both a,b
        return left != null ? left : right;
    }

    private static class Node {
        int data;
        Node left;
        Node right;
        Node(int d) {
            data = d;
        }

        @Override
        public String toString() {
            return data + "";
        }
    }

    /**
     * Reduction to Range Minimum Query (RMQ) problem. An advanced approach promising the efficient O(log(N))
     * or O(1) LCA responses after one-time O(N) pre-processing of the given graph.
     * https://oj.interviewkickstart.com/view_editorial/5072/65/
     *
     */


}
