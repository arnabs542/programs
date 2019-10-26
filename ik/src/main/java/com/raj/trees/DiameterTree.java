package com.raj.trees;

/**
 * @author rshekh1
 */
public class DiameterTree {

    // TODO https://www.geeksforgeeks.org/diameter-of-a-binary-tree/
    // https://www.youtube.com/watch?v=ey7DYc9OANo&t=990s

    /**
     * The diameter of a tree (sometimes called the width) is the number of nodes on the longest path between two end nodes.
     * It can also be said as the distance between farther 2 leaves (assuming nodes stores distance from parent)
     */
    public static void main(String[] args) {
        Node root = new Node(0);
        root.left = new Node(1); root.left.left = new Node(5); root.left.right = new Node(7);
        root.right = new Node(1); root.right.left = new Node(6); root.right.right = new Node(9);
        System.out.println(findDiameter(root));
    }

    static int findDiameter(Node n) {
        if (n == null) return 0;

        // Is diameter passing through root? Get height of left & right subtrees plus this node
        int lHeight = getHeight(n.left);
        int rHeight = getHeight(n.right);

        // is diameter contained in left or right subtree?
        int lDiameter = findDiameter(n.left);
        int rDiameter = findDiameter(n.right);

        // diameter is max of above two
        return Math.max((lHeight + rHeight + n.dist), Math.max(lDiameter, rDiameter));
    }

    static int getHeight(Node n) {
        if (n == null) return 0;
        return n.dist + Math.max(getHeight(n.left), getHeight(n.right));
    }

    static class Node {
        int dist;
        Node left, right;
        public Node(int d) {
            dist = d;
        }
    }

}
