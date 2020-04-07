package com.raj.trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ZigZagTraverse {

    /**
     * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
     * Given a binary tree, return the zigzag level order traversal of its nodes' values.
     * (ie, from left to right, then right to left for the next level and alternate between).
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     * Output:
     * [
     *   [3],
     *   [20,9],
     *   [15,7]
     * ]
     */
    public static void main(String[] args) {

    }

    /**
     * # Level order traverse using Q. Track current level.
     * # One way could be store it in map of lvl,Node & then print it later. But this wud use extra space.
     * # To optimize, use logic - if curr level increases, it means we have all nodes in Q for this new level.
     * # Print it depending on direction, if 0,2,4 -> print in natural else reverse
     * O(n)
     */
    public List<List<Integer>> zigzagLevelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        LinkedList<Pair> q = new LinkedList<>();
        q.add(new Pair(root,0));
        int curr_lvl = -1;

        while(!q.isEmpty()) {
            Pair n = q.peek();
            System.out.println(n.node.val);
            if (n.lvl > curr_lvl) {
                // print nodes
                curr_lvl = n.lvl;
                Iterator<Pair> it;
                if (curr_lvl % 2 == 0) {  // natural order
                    it = q.iterator();
                } else {    // reverse order
                    it = q.descendingIterator();
                }
                List<Integer> lvlNodes = new ArrayList<>();
                while(it.hasNext()) {
                    Node tNode = it.next().node;
                    if (tNode != null) lvlNodes.add(tNode.val);
                }
                res.add(lvlNodes);
            }

            // add childs
            if (n.node.left != null) q.add(new Pair(n.node.left, n.lvl+1));
            if (n.node.right != null) q.add(new Pair(n.node.right, n.lvl+1));
            q.poll();
        }
        return res;
    }

    static class Node {
        int val;
        Node left,right;
        Node(int v) {
            val = v;
        }
    }

    static class Pair {
        Node node;
        int lvl;
        Pair(Node n, int l) {
            node = n;
            lvl = l;
        }
    }

}
