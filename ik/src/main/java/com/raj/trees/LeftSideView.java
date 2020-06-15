package com.raj.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author rshekh1
 */
public class LeftSideView {

    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.root = new Tree.Node(5);
        tree.root.left = new Tree.Node(2); tree.root.right = new Tree.Node(3);
        tree.root.left.left = new Tree.Node(6); tree.root.left.right = new Tree.Node(7); tree.root.right.right = new Tree.Node(10);
        tree.root.left.right.right = new Tree.Node(8);
        Tree.printBreadthFirst(tree.root);
        printLeftView(tree.root);
    }

    /**
     *            5
     *        2        3
     *     6   7         10
     *          8
     *
     *    => 5,2,6,8  (as viewed from left) Also, called as print first at each level.
     *
     *  As level info is required, Breadth-First traversal should be used.
     *  Approach 1 - Use a level var at node level, incr by 1 when adding to Queue.
     *  Have another var for levelSeen. As soon as we see a new level from node dequeue, just print it & incr levelSeen.
     *
     *  Approach 2 - While doing dequeue, exploit the property that the size of the Q is the num of nodes at that level.
     */
    static void printLeftView(Tree.Node n) {
        if (n == null) return;
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(n, 0));
        int curr_level = -1;
        while (!queue.isEmpty()) {
            Pair node_pair = queue.poll();
            if (node_pair.level > curr_level) {
                System.out.println(node_pair.node.val);
                curr_level = node_pair.level;
            }
            if (node_pair.node.left != null) queue.add(new Pair(node_pair.node.left, node_pair.level+1));
            if (node_pair.node.right != null) queue.add(new Pair(node_pair.node.right, node_pair.level+1));
        }
    }

    // Approach 2
    static void _printLeftView(Tree.Node n) {
        Queue<Tree.Node> queue = new LinkedList<>();
        queue.add(n);
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean isLeftPrinted = false;
            while (size-- > 0) {
                Tree.Node cur = queue.poll();
                if (!isLeftPrinted) {
                    System.out.println(cur);
                    isLeftPrinted = true;
                }
                if (cur.left != null) queue.add(cur.left);
                if (cur.right != null) queue.add(cur.right);
            }
        }
    }

    /**
     * Print right side view
     */
    public void rightSideView(Tree.Node root) {
        // reverse level traversal
        Queue<Tree.Node> queue = new LinkedList();
        if (root == null) return;
        queue.add(root);
        while (queue.size() != 0) {
            int size = queue.size();
            for (int i=0; i<size; i++) {
                Tree.Node cur = queue.poll();
                if (i == 0) System.out.println(cur.val);    // 1st element at start of each level is our rightmost child
                if (cur.right != null) queue.offer(cur.right);  // enqueue right node first
                if (cur.left != null) queue.offer(cur.left);
            }
        }
    }

    static class Pair {
        Tree.Node node;
        int level;
        Pair(Tree.Node n, int l) {
            node = n;
            level = l;
        }
    }

}
