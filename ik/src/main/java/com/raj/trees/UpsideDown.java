package com.raj.trees;

/**
 * @author rshekh1
 */
public class UpsideDown {

    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.root = new Tree.Node(1);
        tree.root.left = new Tree.Node(2); tree.root.right = new Tree.Node(3);
        tree.root.left.left = new Tree.Node(4); tree.root.left.right = new Tree.Node(5);
        tree.root.left.left.left = new Tree.Node(6); tree.root.left.left.right = new Tree.Node(7);
        Tree.printBreadthFirst(tree.root);
        Tree.printBreadthFirst(flipUpsideDown(tree.root));
    }

    static Tree.Node flipUpsideDown(Tree.Node root) {
        if (root == null) return null;

        if (root.left == null && root.right == null) return root;
        Tree.Node newRoot = flipUpsideDown(root.left);

        root.left.left = root.right;
        root.left.right = root;
        root.left = null; root.right = null;

        return newRoot;
    }
}
