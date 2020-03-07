package com.raj.trees;

public class ClosestNode {

    /**
     Given a binary search tree and a target value T. The task is to find the node with minimum absolute
     difference with given target value T.

                  9
            4          17
         3     6            22
             5   7       20

     Input  :  T = 4
     Output :  4

     Input  :  T = 18
     Output :  17

     Input  :  T = 12
     Output :  9

     BruteForce:
     # Traverse inorder, store in Array. O(n)
     => Sorted array
     # Modified BSearch for the nearest target K. O(log n)
     Space = O(n), Time=O(n)

     # Optimal:
     Inorder traversal : 3 4 5 6 7 9 17 20 22, T = 18
     - Search K in BST
     - Track diff & minimize diff as we search until leaf node.

     Runtime = log(height of tree)
     NOTE : For Trees, runtime should be expressed in terms of height of tree as logn wud only hold true if the tree is balanced.
     */
    public static void main(String[] args) {
        Node root = new Node(9);
        root.left = new Node(4); root.left.left = new Node(3);
        root.left.right = new Node(6);
        root.right = new Node(17); root.right.right = new Node(22);

        System.out.println(searchNearestNodeK(root, 4));
        System.out.println(searchNearestNodeK(root, 18));
        System.out.println(searchNearestNodeK(root, 12));
        System.out.println(searchNearestNodeK(root, 100));
    }

    private static Node searchNearestNodeK(Node root, int T) {
        Result res = new Result(root);
        searchNearestNodeK(root, T, res);
        return res.node;
    }

    private static void searchNearestNodeK(Node n, int T, Result res) {  // Node res doesn't work, always create a result class
        if (n == null) return;  // search ends when we hit a leaf node

        // keep updating the diff
        int newDiff = Math.abs(n.val-T);
        if (newDiff < res.diff) {
            res.diff = newDiff;
            res.node = n;
            //System.out.println("Updating min node => " + res.node.val);
        }

        if (n.val == T) return;

        if (T < n.val) {
            searchNearestNodeK(n.left, T, res);
        } else {
            searchNearestNodeK(n.right, T, res);
        }
    }

    static class Node {
        int val;
        Node left;
        Node right;

        Node(int v) {
            val = v;
        }

        public String toString() {
            return val + "";
        }
    }

    static class Result {
        int diff = Integer.MAX_VALUE;
        Node node;

        Result(Node n) {
            node = n;
        }
    }
}
