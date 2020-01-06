package com.raj.tree.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author rshekh1
 */
public class BinaryTree {

    public static void main(String[] args) {
        BTree bTree = createBTree();

        System.out.println("---- Level Order Print ----");
        levelOrder(bTree.root);

        System.out.println("---- Level Order Connect ----");
        levelOrderConnect(bTree.root);

        System.out.println("---- In Order Successors ----");
        findInOrderSuccessor(bTree);

        System.out.println("---- Flatten Binary Tree ----");
        LinkedList<Integer> list = new LinkedList<>();
        flattenBTree(bTree.root, list);
        System.out.println(list);
    }

    /**
     *              20
     *             /  \
     *           8    22
     *          / \     \
     *        4   12    25
     *           /  \
     *         10   14
     */
    public static BTree createBTree() {
        BTree bTree = new BTree(new Node(20));
        bTree.root.left = new Node(8);
        bTree.root.left.left = new Node(4);
        bTree.root.right = new Node(22); bTree.root.right.right = new Node(25);
        bTree.root.left.right = new Node(12);
        bTree.root.left.right.left = new Node(10);
        bTree.root.left.right.right = new Node(14);
        return bTree;
    }

    /**
     * https://www.geeksforgeeks.org/level-order-tree-traversal/
     * Print nodes in Level order traversal
     * Algo1: Use a Sorted Map of depth and traverse inorder / preorder and keep adding node to this map as value. Print contents.
     * Algo2: Use a Breadth First Search for nothing and print, which naturally does a level order traversal
     */
    public static void levelOrder(Node n) {
        if (n == null) return;
        Queue<Node> queue = new LinkedList<>();
        queue.add(n);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.print(node.data + ",");
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        System.out.println();
    }

    /**
     * Level order connect : https://www.geeksforgeeks.org/connect-nodes-at-same-level/
     * Input Tree
     *        A
     *       / \
     *      B   C
     *     / \   \
     *    D   E   F
     *
     * Output Tree
     *        A--->NULL
     *       / \
     *      B-->C-->NULL
     *     / \   \
     *    D-->E-->F-->NULL
     * Do BFS, maintain depth of each node, connect nodes if same depth
     */
    public static void levelOrderConnect(Node n) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(n);
        n.depth = 0;    // maintain depth of each node
        Node prevNode = null;   // save previous node for connecting
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (prevNode != null && prevNode.depth == node.depth) prevNode.next = node;    // connect if depth matches
            if (node.left != null) { queue.add(node.left); node.left.depth = node.depth + 1; }  // increment depth and add nodes
            if (node.right != null) { queue.add(node.right); node.right.depth = node.depth + 1; }
            prevNode = node;    // update previous node
        }
        System.out.println(n + "\n" + n.left + "," + n.left.next + "\n"
                + n.left.left + "," + n.left.left.next + "," + n.left.left.next.next + "\n"
                + n.left.right.left + "," + n.left.right.left.next);
    }

    /**
     * Inorder succ for: 8 -> 10, 10 -> 12, 14 -> 20
     *
     * 1> If right subtree of node is not NULL, then succ lies in right subtree. Do following.
     * Go to right subtree and return the node with minimum key value in right subtree.
     * 2) If right subtree of node is NULL, then start from root and use search like technique. Do following.
     * Travel down the tree, if a node’s data is greater than root’s data then go right side, otherwise go to left side.
     * Time = O(height of tree)
     */
    public static void findInOrderSuccessor(BTree bTree) {
        System.out.println(bTree.root.left + " => " + inOrderSuccessor(bTree.root.left, bTree.root));
        System.out.println(bTree.root.left.right.left + " => " + inOrderSuccessor(bTree.root.left.right.left, bTree.root));
        System.out.println(bTree.root.left.right.right + " => " + inOrderSuccessor(bTree.root.left.right.right, bTree.root));
    }

    public static Node inOrderSuccessor(Node n, Node root) {
        if (n.right != null) return minValue(n.right);  // if right subtree exists, find min there

        // Right is null, then search for successor down the tree starting from root node
        Node succ = null;
        while (root != null) {          // keep traversing until exhausted
            if (n.data < root.data) {   // go left if node has a lesser value
                succ = root;            // but do save this candidate successor while we go left
                root = root.left;       // now go left
            } else if (n.data > root.data) {    // if node has a greater value than root, continue search in right subtree
                root = root.right;              // no candidate successor here as we are looking for one greater than n's value
            } else {
                break;                  // if (n.data == root.data) => node found, means we must have saved it's successor
            }
        }
        return succ;
    }

    public static Node minValue(Node n) {
        if (n.left == null) return n;
        else return minValue(n.left);
    }

    /**
     * https://www.geeksforgeeks.org/flatten-a-binary-tree-into-linked-list/
     * Input :
     *           1
     *         /   \
     *        2     5
     *       / \     \
     *      3   4     6
     *
     * Output :
     *     1 -> 2 -> 3 -> 4 -> 5 -> 6
     * Do Preorder traversal, keep adding nodes to the linked list
     */
    public static void flattenBTree(Node n, LinkedList<Integer> l) {
        if (n == null) return;
        l.add(n.data);
        flattenBTree(n.left, l);
        flattenBTree(n.right, l);
    }

    /**
     * https://www.geeksforgeeks.org/construct-bst-given-level-order-traversal/
     */



    /* --------------------- Binary Tree DS -------------- */
    static class BTree {
        public Node root;
        public BTree(Node r) {
            root = r;
        }
    }

    static class Node {
        public int data;
        public Node left, right;

        public int depth;
        public Node next;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data + "";
        }
    }

}
