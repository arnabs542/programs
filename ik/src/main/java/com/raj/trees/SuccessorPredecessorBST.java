package com.raj.trees;

import static com.raj.trees.Util.Node;

public class SuccessorPredecessorBST {

    /**
     * Find the inorder successor and predecessor node of a given target value T.
     * In case the given T is not found in BST, then return the two values within which this T will lie.
     * https://www.geeksforgeeks.org/inorder-predecessor-successor-given-key-bst/
     */
    public static void main(String[] args) {
        Node root = new Node(12);
        root.left = new Node(10); root.left.left = new Node(9); root.left.right = new Node(11); root.left.right.left = new Node(10.7f);
        root.right = new Node(17); root.right.right = new Node(20); root.right.left = new Node(13);
        PredSucc predSucc = new PredSucc();
        findInorderSuccessorPredecessor(root, 11.9d, predSucc);
        System.out.println(predSucc);
    }

    /**
     *            12 (root)
     *        /              \
     *      10                17
     *    /    \            /    \
     *  9       11        13      20
     *         /
     *      10.7
     *
     * If T = 10.0, o/p = 9 & 11
     * If T = 11.9, o/p = 11 & 12
     *
     * Cases?
     * # If n == null, return
     * # If T == n,
     *   -> If its left subtree is not null,
     *      Then predecessor will be the right most child of immediate left child or left child itself (if no rightmost).
     *   -> If its right subtree is not null,
     *      Then successor will be the left most child of immediate right child or right child itself (if no leftmost).
     * # If T < n,
     *   -> Assign successor = n, then recursively search in left subtree
     * # If T > n,
     *   -> Assign predecessor = n, then recursively search in right subtree
     */
    public static void findInorderSuccessorPredecessor(Node n, double T, PredSucc predSucc) {
        if (n == null) return;
        if (T == n.val) {
            // find predecessor, if left child exists
            if (n.left != null) {
                Node tmp = n.left;
                while (tmp.right != null) tmp = tmp.right;
                predSucc.pred = tmp;
            }
            // find successor, if right child exists
            if (n.right != null) {
                Node tmp = n.right;
                while (tmp.left != null) tmp = tmp.left;
                predSucc.succ = tmp;
            }
        } else if (T < n.val) {  // go left if T is smaller than n, also assign succ
            predSucc.succ = n;
            findInorderSuccessorPredecessor(n.left, T, predSucc);
        } else {    // if (T > n.val), go right also assign pred
            predSucc.pred = n;
            findInorderSuccessorPredecessor(n.right, T, predSucc);
        }
    }



    static class PredSucc {
        Node pred,succ;
        public String toString() { return pred + ", " + succ; }
    }

}
