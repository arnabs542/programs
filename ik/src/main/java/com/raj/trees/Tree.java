package com.raj.trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class Tree {

    public static class Node {
        public int val;
        public Node left, right;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val + " ";
        }
    }

    Node root;

    // Depth first traverse - root, left, right
    // Also called Pre-Order traversal
    static void printDepthFirst(Node root) {
        /*if (root == null) return;
        System.out.print(root.val + " ");
        printDepthFirst(root.left);
        printDepthFirst(root.right);*/

        // Alternative solution - using Stack
        Stack<Node> stack = new Stack();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.print(cur.val + " ");
            // push right & then left, as we want to go keep left on top for it to process first
            if (cur.right != null) stack.push(cur.right);
            if (cur.left != null) stack.push(cur.left);
        }
    }

    // Breadth first traverse - using queue to for FIFO
    // Also called Level-Order traversal
    static void printBreadthFirst(Node root) {
        if (root == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.print(cur.val + " ");
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
        }
    }

    // Traverse (Left subtree), Root, (Right subtree) recursively
    static void printInorder(Node root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }

    public static void main(String[] args) {
        /**
         * A binary tree :
         *          1
         *      2       3
         *    4   5   6   7
         */
        Tree tree = new Tree();
        tree.root = new Node(1);
        tree.root.left = new Node(2); tree.root.right = new Node(3);
        tree.root.left.left = new Node(4); tree.root.left.right = new Node(5);
        tree.root.right.left = new Node(6); tree.root.right.right = new Node(7);
        System.out.print("Depth First Traverse => "); printDepthFirst(tree.root);
        System.out.print("\nBreadth First Traverse => "); printBreadthFirst(tree.root);

        /**
         * A BST :  Left subtree <= Root < Right subtree
         *               10
         *          7          20
         *      5      8    15      25
         */
        Tree bst = new Tree();
        bst.root = new Node(10);
        bst.root.left = new Node(7); bst.root.right = new Node(20);
        bst.root.left.left = new Node(5); bst.root.left.right = new Node(8);
        bst.root.right.left = new Node(15); bst.root.right.right = new Node(25);
        System.out.print("\nBST Inorder Traverse => "); printInorder(bst.root);
    }

}
