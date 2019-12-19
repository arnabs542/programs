package com.raj.trees;

import java.util.Stack;

/**
 * Implement an iterator over a binary tree. Your iterator will be initialized with the root node of a binary tree.
 * 1. Calling next() will return the next number in in-ordered traversed list of the binary tree.
 * 2. Calling hasNext() should return whether the next element exists.
 * Both functions should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
 */
public class TreeIterator {

    /**
     *          6        ---> root
     *       2       8
     *   1     4    7 9
     *       3   5
     *
     * Algo:
     * Use a stack to keep track of recursive state
     * Go left recursively & keep adding to stack
     * next() -> remove and go right
     *
     * Runtime = O(n), Aux space = O(height of tree)
     */
    public static void main(String[] args) {
        Node root = new Node(6);
        root.left = new Node(2); root.left.left = new Node(1);
        root.left.right = new Node(4); root.left.right.left = new Node(3); root.left.right.right = new Node(5);
        root.right = new Node(8);
        root.right.right = new Node(9);
        root.right.left = new Node(7);
        TreeIterator treeIterator = new TreeIterator(root);
        while (treeIterator.hasNext()) {
            System.out.println(treeIterator.next());
        }
    }

    Stack<Node> stack = new Stack<>();

    public TreeIterator(Node root) {
        stack.push(root);
        traverse(root.left);
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public int next() {
        Node n = stack.pop();
        traverse(n.right);
        return n.val;
    }

    public void traverse(Node n) {
        if (n == null) return;
        stack.push(n);
        traverse(n.left);
    }

    private static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
        }
    }

}
