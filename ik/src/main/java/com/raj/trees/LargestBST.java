package com.raj.trees;

public class LargestBST {

    /**
     * Given a binary tree, find the largest subtree which is also a binary search tree(BST). Here largest subtree means
     * subtree with maximum number of nodes. A binary search(BST) is a rooted binary tree whose left child values are
     * less than or equal to parent value and right child values are greater than or equal to parent value.
     */
    public static void main(String[] args) {

    }

    /**
     * Brute Force - Traverse each node starting with root and going down. For each node, find the largest subtree which is BST.
     * Since, we are going top down, any BST found naturally returns the largest BST under it.
     * Runtime = O(n^2) as we compute isBST() for subtree rooted at each node while traversing
     */
    static int brute(Node n) {
        if (n == null) return 0;
        if (isBST(n, Integer.MIN_VALUE, Integer.MAX_VALUE)) return size(n);  // O(n+n)
        return Math.max(brute(n.left), brute(n.right)); // O(n)
    }

    static boolean isBST(Node n, int min, int max) {
        if (n == null) return true;
        // n is out of min/max bounds
        if (n.data < min || n.data > max) return false;
        // update bounds as we go left or right
        return isBST(n.left, min, n.data -1) && isBST(n.right, n.data +1, max);
    }

    static int size(Node n) {
        if (n == null) return 0;
        return 1 + size(n.left) + size(n.right);
    }

    static int linear(Node n) {
        return findLargestBST(n).size;
    }

    /**
     * In method 1, we traverse the tree in top down manner and do BST test for every node. If we traverse the tree in
     * bottom up manner, then we can pass information about subtrees to the parent. The passed information can be used
     * by the parent to do BST test (for parent node) only in constant time (or O(1) time). A left subtree need to tell
     * the parent whether it is BST or not and also need to pass maximum value in it. So that we can compare the maximum
     * value with the parent’s data to check the BST property. Similarly, the right subtree need to pass the minimum
     * value up the tree. The subtrees need to pass the following information up the tree for the finding the largest BST.
     * 1) Whether the subtree itself is BST or not (In the following code, is_bst_ref is used for this purpose)
     * 2) If the subtree is left subtree of its parent, then maximum value in it. And if it is right subtree then minimum value in it.
     * 3) Size of this subtree if this subtree is BST
     */
    static Result findLargestBST(Node n) {

        // provide max value to 'min' so that when left subtree if null, n.data is picked as min & vice-versa
        if (n == null) return new Result(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);

        Result result = new Result();
        Result leftRes = findLargestBST(n.left);
        Result rightRes = findLargestBST(n.right);

        if (leftRes.isBST && rightRes.isBST && n.data >= leftRes.max && n.data <= rightRes.min) {
            int min = Math.min(leftRes.min, n.data); // when left subtree if null, n.data is picked as min (which we want) as non-bst & null subtrees will return min as Int.max_value
            int max = Math.max(rightRes.max, n.data);
            return new Result(true, 1 + leftRes.size + rightRes.size, min, max);
        }

        return new Result(false, Math.max(leftRes.size, rightRes.size), Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    static Result _findLargestBST(Node n) {

        if (n == null) return new Result();

        Result result = new Result();
        Result leftRes = _findLargestBST(n.left);
        Result rightRes = _findLargestBST(n.right);

        if (!leftRes.isBST || !rightRes.isBST || n.data < leftRes.max || n.data > rightRes.min) {   // or u could negate this and write it
            result.isBST = false;
            result.size = Math.max(leftRes.size, rightRes.size);
            return result;
        }

        // both left & right subtrees are BST

        // update counts
        result.size = 1 + leftRes.size + rightRes.size;

        // update min / max (we can avoid some verbosity, refer to findLargestBST where these explicit conditions are handled via cleverly supplying result defaults)
        result.min = (n.left != null) ? leftRes.min : n.data;  // min of left subtree
        result.max = (n.right != null) ? rightRes.max : n.data; // max of right subtree

        return result;
    }

    private static class Result {
        boolean isBST;
        int size;
        int min,max;

        public Result() {
        }

        public Result(boolean isBST, int size, int min, int max) {
            this.isBST = isBST;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }

    private static class Node {
        int data;
        Node left, right;
    }
}
