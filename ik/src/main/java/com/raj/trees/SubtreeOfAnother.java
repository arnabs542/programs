package com.raj.trees;

public class SubtreeOfAnother {
    /**
     * Subtree of Another Tree
     * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The tree s could also be considered as a subtree of itself.
     *
     * Example 1:
     * Given tree s:
     *
     *      3
     *     / \
     *    4   5
     *   / \
     *  1   2
     * Given tree t:
     *    4
     *   / \
     *  1   2
     * Return true, because t has the same structure and node values with a subtree of s.
     *
     *
     * Example 2:
     * Given tree s:
     *
     *      3
     *     / \
     *    4   5
     *   / \
     *  1   2
     *     /
     *    0
     * Given tree t:
     *    4
     *   / \
     *  1   2
     * Return false.
     */
    public static void main(String[] args) {
        Node n = new Node(3);
        n.left = new Node(4); n.left.left = new Node(1);
        n.left.right = new Node(2);
        n.right = new Node(5);

        Node t = new Node(4); t.left = new Node(1); t.right = new Node(2);
        System.out.println(isSubtree(n,t));

        // 2nd example
        n.left.right.left = new Node(0);
        System.out.println(isSubtree(n,t));
    }

    static StringBuilder spre = null;
    static StringBuilder tpre = null;

    /**
     * One approach will be to traverse both trees simultaneously once a node match:
     * f(A,B):
     *   if A == null ret B == null
     *   if A.val == B.val && isMatch(A,B) ret true;
     *   ret f(A.left,B.left) || f(A.right,B.right)
     *
     * isMatch(A,B):
     *   if A == null ret B == null
     *   if A.val != B.val ret false
     *   ret isMatch(A.left,B.left) && isMatch(A.right,B.right)
     * Time = O(n.m)
     *
     * To reduce to linear, we can think of doing the comparison just once?
     * # preorder traverse both trees and build a string to compare
     * # use KMP / Boyer-Moore linear algo to do substr comparison
     * Time/Space = O(n + m + n) = O(m+n)
     */
    static boolean isSubtree(Node s, Node t) {
        spre = new StringBuilder();
        tpre = new StringBuilder();
        preOrder(s, spre.append(","));
        preOrder(t, tpre.append(","));
        System.out.println(spre);
        System.out.println(tpre);
        return spre.toString().contains(tpre.toString());
    }

    static void preOrder(Node root, StringBuilder str) {  // creates a string of preorder traversal
        if (root == null) {
            str.append("#,");
            return;
        }
        str.append(root.val).append(",");
        preOrder(root.left, str);
        preOrder(root.right, str);
    }

    static class Node {
        Node left,right;
        int val;

        public Node(int val) {
            this.val = val;
        }
    }
}
