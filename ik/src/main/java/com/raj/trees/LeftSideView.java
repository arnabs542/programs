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

}
