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

    public Node root;

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
        System.out.println();
    }

    // Breadth first traverse - using queue for FIFO
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
        System.out.println();
    }

    // Traverse (Left subtree), Root, (Right subtree) recursively
    static void printInorder(Node root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }

    static void printAllPaths(Node root) {
        if (root == null) return;
        printPaths(root, "");
    }

    // do DFS, pre-order, keep adding node val as we traverse down. Print once we reach leaf node
    static void printPaths(Node n, String soFar) {
        soFar += n.val + " ";
        if (n.left == null && n.right == null) {
            System.out.println(soFar);
            return;
        }
        printPaths(n.left, soFar);
        printPaths(n.right, soFar);
    }

    static void printPaths(Node n, Stack stack) {
        if (n.left == null && n.right == null) {
            for (Object o : stack) System.out.print(o + " ");
            System.out.println(n);
            return;
        }
        stack.push(n);
        printPaths(n.left, stack);
        printPaths(n.right, stack);
        stack.pop();
    }

    static void printPathSum(Node n, Stack<Integer> stack, int sum) {
        if (n == null) return;

        // process node
        stack.add(n.val);  // add state
        sum -= n.val;
        if (sum == 0) {   // is the target sum reached?
            for (int i : stack) {
                System.out.print(i + " ");
            }
        }

        // recurse
        printPathSum(n.left, stack, sum);
        printPathSum(n.right, stack, sum);
        stack.pop();  // revert state
    }

    static class Result {
        int val;
        int count;
    }

    static int kth_smallest_element(Tree.Node root, int k) {
        if (k < 1 || root == null) return 0;
        // do inorder, decr k while processing node, when k == 0 return that value
        Result res = new Result();
        inorderKthElement(root, k, res);
        return res.val;
    }

    static void inorderKthElement(Tree.Node n, int k, Result res) {
        if (n == null) return ;

        inorderKthElement(n.left, k, res);
        res.count++;
        //System.out.println("count = " + count + " , " + n.val);
        if (res.count == k) {
            res.val = n.val;
            return;
        }
        inorderKthElement(n.right, k, res);
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
        System.out.println("\nPrint all paths : ");
        printPaths(tree.root, new Stack<>());
        System.out.println("Print Path Sum=8 :");
        printPathSum(tree.root, new Stack<>(), 8);

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

        System.out.println("\nkth smallest node = " + kth_smallest_element(bst.root, 6    ));
    }

}
