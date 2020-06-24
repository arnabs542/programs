package com.raj.trees;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaxWidthBTree {
    /**
     * https://leetcode.com/problems/maximum-width-of-binary-tree/
     * Given a binary tree, write a function to get the maximum width of the given tree. The width of a tree is the maximum width among all levels.
     * The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes in the level, where the null nodes between the end-nodes are also counted into the length calculation.
     *
     * Example 1:
     *
     * Input:
     *
     *            1
     *          /   \
     *         3     2
     *        / \     \
     *       5   3     9
     *
     * Output: 4
     * Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).
     * Example 2:
     *
     * Input:
     *
     *           1
     *          /
     *         3
     *        / \
     *       5   3
     *
     * Output: 2
     * Explanation: The maximum width existing in the third level with the length 2 (5,3).
     * Example 3:
     *
     * Input:
     *
     *           1
     *          / \
     *         3   2
     *        /
     *       5
     *
     * Output: 2
     * Explanation: The maximum width existing in the second level with the length 2 (3,2).
     * Example 4:
     *
     * Input:
     *
     *           1
     *          / \
     *         3   2
     *        /     \
     *       5       9
     *      /         \
     *     6           7
     * Output: 8
     * Explanation:The maximum width existing in the fourth level with the length 8 (6,null,null,null,null,null,null,7).
     */
    public static void main(String[] args) {

    }

    /**
     * Level order traverse using Q
     * Instead of storing null nodes, we can save space by -
     * Define a node's idx as Heap Idx, ie. i,2i,2i+1
     * At each new level, mark the left node's idx & find max width as node's idx - leftmost idx
     */
    static int maxWidth(Node node) {
        if (node == null) return 0;
        int maxWidth = 1;
        Deque<Node> deque = new ArrayDeque<>();
        node.val = 1;  // set val as heap idx
        deque.add(node);
        while (!deque.isEmpty()) {
            // update max width as leftmost & rightmost idx diff + 1
            maxWidth = Math.max(maxWidth, deque.getLast().val - deque.getFirst().val + 1);
            // level wise process
            for (int i = 0; i < deque.size(); i++) {
                Node cur = deque.removeFirst();
                // add left & right w/ updated idx
                if (cur.left != null) {
                    cur.left.val = cur.val * 2;
                    deque.addLast(cur.left);
                }
                if (cur.right != null) {
                    cur.right.val = (cur.val * 2) + 1;
                    deque.addLast(cur.right);
                }
            }
        }
        return maxWidth;
    }

    static class Node {
        int val;
        Node left,right;
    }
}
