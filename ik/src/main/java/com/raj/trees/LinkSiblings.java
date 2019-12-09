package com.raj.trees;

import java.util.LinkedList;
import java.util.Queue;

public class LinkSiblings {

    /**
     * Given a binary tree, populate next_right pointers in each node and return the root of the tree.
     *              1
     *        2 -------> 3
     *     4 ---> 5  6 -----> 7
     */
    public static void main(String[] args) {

        
    }

    static Node root;

    private static class Pair {
        int level; Node n;
        Pair(int l, Node _n) { level = l; n = _n; }
    }

    static Node populateSiblingPointers(Node root) {
        if (root == null) return null;
        
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(0, root));
        
        int curr_level = 0;
        Node prev = null;

        while(!q.isEmpty()) {
            Pair p = q.remove();
            if (p.level != curr_level) {
                prev = null;
                curr_level = p.level;
            } else {    // same level, hence link siblings
                if (prev != null) prev.next = p.n;
            }
            if (p.n.left != null) q.add(new Pair(curr_level+1, p.n.left));
            if (p.n.right != null) q.add(new Pair(curr_level+1, p.n.right));
            prev = p.n;
        }
        return root;
    }

    private static class Node {
        int val;
        Node left, right, next;
    }

}
