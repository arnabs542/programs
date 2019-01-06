package com.raj.tree.binarytree;

/**
 * @author rshekh1
 */
public class CheckMirrorTree {

    /* Given two trees, return true if they are
       mirror of each other */
    boolean areMirror(Node a, Node b)
    {
        // Both empty
        if (a == null && b == null) return true;

        // If only one is empty
        if (a == null || b == null) return false;

        // data doesn't match
        //if (a.data != b.data) return false;

        // recursively check if a's left equals b's right & a'right equals b's left
        return a.data == b.data && areMirror(a.left, b.right) && areMirror(a.right, b.left);
    }

    // Driver code to test above methods
    public static void main(String[] args) {
        CheckMirrorTree tree = new CheckMirrorTree();
        Node a = new Node(1);
        Node b = new Node(1);
        a.left = new Node(2);
        a.right = new Node(3);
        a.left.left = new Node(4);
        a.left.right = new Node(5);

        b.left = new Node(3);
        b.right = new Node(2);
        b.right.left = new Node(5);
        b.right.right = new Node(4);

        if (tree.areMirror(a, b)) System.out.println("Yes");
        else System.out.println("No");
    }

    static class Node {
        int data; Node left, right;
        public Node(int data) { this.data = data; left = right = null; }
    }

}
