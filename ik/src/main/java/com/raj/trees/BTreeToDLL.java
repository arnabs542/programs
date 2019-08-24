package com.raj.trees;

import static com.raj.trees.Tree.printBreadthFirst;

/**
 * @author rshekh1
 */
public class BTreeToDLL {

    /**
     * Convert a binary tree to Doubly Linked List. The left becomes prev ptr & right becomes next ptr of DLL. Also, it's a Circular DLL.
     * Given binary tree :
     *                    1
     *                2       3
     *              4   5   6   7
     * O/P :
     *      ---------------------------------------------------
     *     |                                                  | left
     *     --> 4 <==> 2 <==> 5 <==> 1 <==> 3 <==> 6 <==> 7 <--
     *    |                                                  | right
     *    ---------------------------------------------------
     * # The order is a inorder traversal
     * # Do inorder traversal, track prev node, link curr left(DLL's prev_ptr) to prev node & prev right(DLL's next_ptr) to curr
     * # Lastly link the head & tail ptrs to form a C-DLL
     *
     * Time = O(n)
     * Space = O(height) max stack size while recursion
     */
    static Tree.Node convertTreeToDLL(Tree.Node root) {
        if (root == null) return null;

        _convertTreeToDLL(root);
        // now arrange head & tail ptrs
        if (head != null) head.left = prev;
        if (prev != null) prev.right = head;
        return head;
    }

    /**
     * This is to keep track of prev & head
     * Note - if prev is not static, the prev's value will be lost while recursion unfolds.
     * This is because Java doesn't pass method arguments by reference; it passes them by value : https://stackoverflow.com/questions/40480/is-java-pass-by-reference-or-pass-by-value
     */
    static Tree.Node head = null, prev = null;

    static void _convertTreeToDLL(Tree.Node curr) {
        if (curr == null) return;

        // go left
        _convertTreeToDLL(curr.left);

        // process current node
        if (head == null) head = curr; // leftmost node becomes head
        else {
            // curr node's left links to prev & prev's right to curr
            prev.right = curr;
            curr.left = prev;
        }
        prev = curr;

        // go right
        _convertTreeToDLL(curr.right);
    }

    public static void main(String[] args) {
        /**
         * A binary tree :
         *          1
         *      2       3
         *    4   5   6   7
         */
        Tree tree = new Tree();
        tree.root = new Tree.Node(1);
        tree.root.left = new Tree.Node(2);
        tree.root.right = new Tree.Node(3);
        tree.root.left.left = new Tree.Node(4);
        tree.root.left.right = new Tree.Node(5);
        tree.root.right.left = new Tree.Node(6);
        tree.root.right.right = new Tree.Node(7);
        System.out.print("\nBreadth First Traverse => ");
        printBreadthFirst(tree.root);
        printBreadthFirst(convertTreeToDLL(tree.root));
    }
}
