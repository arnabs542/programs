package com.raj.trees;

import java.util.LinkedList;
import java.util.Queue;

public class ConnectNextPtr {
    /**
     * Populating Next Right Pointers in Each Node
     * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children.
     * The binary tree has Next ptr as well. Populate each next pointer to point to its next right node.
     *         1
     *        / \
     *       2 -> 3
     *      /\    /\
     *     4->5->6->7
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/discuss/37715/Python-solutions-(Recursively-BFS%2Bqueue-DFS%2Bstack)
     */
    public static void main(String[] args) {
        Node n = new Node(1);
        n.left = new Node(2); n.left.left = new Node(4); n.left.right = new Node(5);
        n.right = new Node(3); n.right.left = new Node(6); n.right.right = new Node(7);
        connectNextPtr(n);
        System.out.println(n.left.next);
        System.out.println(n.right.next);
        System.out.println(n.left.left.next);
        System.out.println(n.left.right.next);
    }

    // level wise -  will wrk even for partially filled binary trees
    static void connectNextPtr(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node prev = null;
            for (int i = 0; i < size; i++) {  // level wise traversal
                Node cur = queue.remove();
                if (prev != null) prev.next = cur;
                if (cur.left != null) queue.add(cur.left);
                if (cur.right != null) queue.add(cur.right);
                prev = cur;
            }
        }
    }

    /**
     * DFS / Recur
     * Converting this to stack based soln, is as simple as adding a stack and push right child followed by left child.
     * Now stack's top is the one we operate
     */
    static void connect_recur(Node n) {
        if (n == null) return;
        // connect left & right child first
        if (n.left != null) n.left.next = n.right;
        // connect node's right child's next to node's next left
        if (n.next != null && n.right != null) n.right.next = n.next.left != null ? n.next.left : n.next.right;
        connect_recur(n.left);
        connect_recur(n.right);
    }

    static class Node {
        int val;
        Node left,right,next;
        Node(int v) {
            val = v;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }
}
