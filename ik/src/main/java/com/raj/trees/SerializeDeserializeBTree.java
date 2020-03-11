package com.raj.trees;

import java.util.LinkedList;
import java.util.Queue;

public class SerializeDeserializeBTree {

    /**
     * Serialize a Binary Tree to store in a file so that it can be later restored. The structure of tree must be
     * maintained. Deserialize to read tree back from file.
     * https://www.geeksforgeeks.org/serialize-deserialize-binary-tree/
     */
    public static void main(String[] args) {
        Node root = new Node(20);
        root.left = new Node(8); root.left.left = new Node(4);
        root.left.right = new Node(12); root.left.right.left = new Node(10);
        root.left.right.right = new Node(14);
        root.right = new Node(24); root.right.right = new Node(30);
        root.right.right.left = new Node(28);

        preOrderPrint(root); System.out.println();

        Queue<Integer> queue = new LinkedList<>();
        serialize(root, queue);
        System.out.println(queue);

        Node deserRoot = deserialize(queue);
        preOrderPrint(deserRoot);
    }

    /**
     * BST ? Either preorder or inorder in enough as we can make use of the BST property at construction to guide recursion.
     * Complete Tree ? Level order is enough
     * Full Tree ? Every internal node has 0 or 2 childs, so just use preorder plus a bit to indicate whether 0 child or 2.
     * General BTree?
     *
     * Approach 1:
     * Do PreOrder + InOrder Traversals, store in 2 arrays. Reconstruct it back going left to right in PreOrder sequence
     * & recursively building left & right after looking up it's left/right nodes in InOrder sequence.
     * Uses O(2*n) space
     *
     * Approach 2: (save space by doing just preorder & -1 for leaf's null childs)
     * # Iterate tree in PreOrder fashion, indicating -1 for leaf's null ptrs. Store it in Q.
     * # Deserialize: Pop element, if -1 then return null base case & non -1s as new root nodes,
     *   recursively building left/right subtrees & returning new root node.
     *
     * Input:
     *          20
     *        /   \
     *       8     24
     *      / \      \
     *     4  12      30
     *       /  \    /
     *      10  14  28
     *
     * Output: 20 8 4 -1 -1 12 10 -1 -1 14 -1 -1 24 -1 30 28 -1
     * Time/Space = O(n)
     */
    private static void serialize(Node n, Queue<Integer> queue) {
        if (n == null) {
            queue.add(-1);  // add -1 for null nodes
            return;
        }
        queue.add(n.val);   // add the nodes val & recurse left & right
        serialize(n.left, queue);
        serialize(n.right, queue);
    }

    private static Node deserialize(Queue<Integer> queue) {
        //if (queue.isEmpty()) return null;  // not required
        int val = queue.poll();
        if (val == -1) return null;
        Node root = new Node(val);
        root.left = deserialize(queue);
        root.right = deserialize(queue);
        return root;
    }

    private static void preOrderPrint(Node n) {
        if (n == null) return;
        System.out.print(n.val + " "); // print root
        preOrderPrint(n.left);
        preOrderPrint(n.right);
    }

    private static class Node {
        Node left, right;
        int val;
        Node(int _val) {
            val = _val;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }

}
