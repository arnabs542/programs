package com.raj.trees;

/**
 * @author rshekh1
 */
public class DiameterTree {

    /**
     * https://www.geeksforgeeks.org/diameter-of-a-binary-tree/
     * https://www.youtube.com/watch?v=ey7DYc9OANo&t=990s
     *
     * The diameter of a tree (sometimes called the width) is the number of nodes on the longest path between two end nodes.
     * It can also be said as the distance between farther 2 leaves (assuming nodes stores distance from parent)
     * Value of the node represents the distance from its parent
     *                          0
     *                     1         1
     *                   5   7     6   9
     *
     * o/p = 7+1+1+9 = 18
     */
    public static void main(String[] args) {
        Node root = new Node(0);
        root.left = new Node(1); root.left.left = new Node(5); root.left.right = new Node(7);
        root.right = new Node(1); root.right.left = new Node(6); root.right.right = new Node(9);
        System.out.println("Diameter passing thru root = " + findDiameter(root));
        System.out.println("The diameter of given binary tree in O(n) : " + diameter(root));

        maxSum(root); System.out.println("maxSum : " + max);  // simplest
        System.out.println("maxDiameter : " + maxDiameter(root).maxAtNode);

        System.out.println("======================");

        // modify existing tree to make the right subtree's left & right child 10X far apart than earlier
        root.right.left = new Node(60); root.right.right = new Node(90);
        System.out.println("Diameter NOT passing thru root = " + findDiameter(root));
        System.out.println("The diameter of given binary tree in O(n) : " + diameter(root));

        System.out.println("maxDiameter : " + maxDiameter(root).maxAtNode);

        maxSum(root); System.out.println("maxSum : " + max);

        System.out.println("======================");

        root = new Node(-10); root.left = new Node(9); root.left.left = new Node(5);
        root.left.right = new Node(-6); root.right = new Node(-20);
        max = Integer.MIN_VALUE; maxSum(root); System.out.println("maxSum : " + max);

        System.out.println("maxDiameter : " + maxDiameter(root).maxAtNode);

        root.left = new Node(-9); root.left.left = new Node(5);
        max = Integer.MIN_VALUE; maxSum(root); System.out.println("maxSum : " + max);
        System.out.println("maxDiameter : " + maxDiameter(root).maxAtNode);

    }

    /**
     * Diameter can pass through root or just be contained in a subtree.
     * Hence we need to keep track of 2 distances, one passing thru root & another at this node itself (subtree)
     * Runtime = O(n^2) ... as apart from inorder traversal, at each node, we also try to compute dist separately
     */
    static int findDiameter(Node n) {
        if (n == null) return 0;

        // Is diameter passing through root? Get height of left & right subtrees
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

    /**
     * To optimize, we can try following approaches:
     * 1: Precompute heights of each node in hashMap and re-use it. O(n) extra space.
     * 2: Calculate the height in the same recursion rather than calling height() separately.
     *    - Idea is to keep another state info for keeping track of subtree heights as we go down or backtrack.
     *    - No extra space other than depth of recursion stack O(logn)
     * Runtime = O(n)
     */
    static class Height { int h; }
    static int diameter(Node root) {
        Height height = new Height();
        return diameter(root, height);
    }

    static int diameter(Node n, Height height) {    // height tracks subtree height
        if (n == null) return 0;   // base case

        // traverse left & right getting their diameters as well as keeping track of the left subtree & right subtree heights
        Height lh = new Height(), rh = new Height();    // define lh, lr for passing it to recursive call
        int leftDiameter = diameter(n.left, lh);        // lh will be updated for max height as we go down
        int rightDiameter = diameter(n.right, rh);

		// Update max possible subtree height at this node
        height.h = Math.max(lh.h, rh.h) + n.dist;

        // Diameter is max of length passing thru root or max of left/right subtree
        return Math.max(lh.h + rh.h + n.dist, Math.max(leftDiameter, rightDiameter));
    }

    static class Node {
        int dist;
        Node left, right;
        public Node(int d) {
            dist = d;
        }
        public String toString() { return dist + ""; }
    }

    /**
     * Coupang: "Given a tree, find the max path sum, which originates from leaf & which may or may not pass through the root."
     * Most elegant solution in O(n), using global ptr max (u can as well make it as a class variable and have the actual impl inside it)
     *          -10
     *        9     -20     (also try with -9)
     *     5   -6
     *
     * o/p = 5+9-6 = 8
     *
     * [subset pattern - incl/excl node]
     * Rec Seq:
     * - go left, node 5 returns 5
     * - node 9 has a choice to make:
     *   - pick max of (5,-6) = 5
     *   - try a path including this node 5,9,-6 = 8 => update in max as we have a full path here
     * - node 9 returns max of left&right + it's value viz. 5+9
     * - root node -10 has following choices:
     *   - left=14,right=-20, it will return 14+ -10 back (which has no effect as rec terminates)
     *   - path with left & right subtree including itself viz. 5,9,-10,-20 = -16 (updating max has no effect)
     * - print global max
     *
     * Time = O(n)
     */
    static int max = Integer.MIN_VALUE;

    static int maxSum(Node n) {
        if (n == null) return 0;

        int leftSum = maxSum(n.left);
        int rightSum = maxSum(n.right);

        // what's the max at this node (full path with this node)
        int max_incl_root = n.dist + leftSum + rightSum;

        // update global max
        max = Math.max(max, max_incl_root);

        // running max of left or right (not yet full path, but used later)
        int runningMaxLeftOrRight = n.dist + Math.max(leftSum, rightSum);

        return runningMaxLeftOrRight;    // return running max for summing up later
    }

    /**
     * Another approach could be, if each node returns tuple of running sum & max at the node & doesn't use global ptrs
     * @see com.raj.trees.MaxSumNonAdjNodes - similar subset pattern
     * # DFS until leaf nodes reached
     * # Each node returns max sum incl/excl this node
     *
     * Time/Space = O(n)
     */
    static Tuple maxDiameter(Node n) {
        if (n == null) return new Tuple(0,0);

        // dfs & get left & right answers
        Tuple leftTuple = maxDiameter(n.left);
        Tuple rightTuple = maxDiameter(n.right);

        // options with subtree - left/right subtree max or a new subtree with this node
        int subtreeMax = Math.max(Math.max(leftTuple.maxAtNode, rightTuple.maxAtNode), n.dist + leftTuple.runningMaxSum + rightTuple.runningMaxSum);

        // options for max left/right to build upwards
        int maxLeftOrRight = n.dist + Math.max(leftTuple.runningMaxSum, rightTuple.runningMaxSum);

        // build answer for this node
        return new Tuple(maxLeftOrRight, subtreeMax);
    }

    static class Tuple {
        int runningMaxSum; // to be used for max computations when given node is included
        int maxAtNode;     // choose from L subtree, R subtree, n.val + runningMaxSum of either L or R
        Tuple(int r, int m) {
            runningMaxSum = r;
            maxAtNode = m;
        }
    }

}
