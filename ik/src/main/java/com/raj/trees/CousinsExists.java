package com.raj.trees;

public class CousinsExists {
    /**
     * Given a Binary Tree, find if cousins X,Y exists.
     * 2 nodes are cousins if they have same level, but different parents.
     *        1
     *    2       3
     *  4           5
     *
     *  findCousins(4,5) => true
     */
    public static void main(String[] args) {
        Node n = new Node(1);
        n.left = new Node(2); n.left.left = new Node(4);
        n.right = new Node(3); n.right.right = new Node(5);
        System.out.println(findCousins(n,4,5));
        System.out.println(findCousins(n,41,5));
    }

    /**
     * Traverse left & right, return the level+parent pair if node found
     * Check if left & right answers is not null + levels are same + parent different
     * O(n)
     */
    static boolean findCousins(Node root, int x, int y) {
        Pair pairX = findCousinsUtil(root, 0, -1, x);
        Pair pairY = findCousinsUtil(root, 0, -1, y);
        return (pairX != null && pairY != null
                && pairX.level == pairY.level && pairX.parent != pairY.parent);
    }

    static Pair findCousinsUtil(Node root, int level, int parent, int n) {
        if (root == null) return null;
        if (root.val == n) {
            return new Pair(level, parent);
        }
        Pair leftPair = findCousinsUtil(root.left, level+1, root.val, n);
        Pair rightPair = findCousinsUtil(root.right, level+1, root.val, n);
        return leftPair != null ? leftPair : rightPair;
    }

    static class Pair {
        int level,parent;
        Pair(int l, int p) {
            level = l;
            parent = p;
        }
    }

    static class Node {
        int val;
        Node left,right;
        Node(int v) {
            val = v;
        }
    }

}
