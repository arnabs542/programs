package com.raj.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author rshekh1
 */
public class ArrayToBST {

    public static void main(String[] args) {
        Tree.Node root = buildBST(new int[] {2,4,3,1,5});
        Tree.printBreadthFirst(root);
        mergeTwoBSTs(new Tree.Node(2), new Tree.Node(1));
    }

    /**
     * Build a BST from an array of integers
     * [1,2,3,4,5]
     *     mid
     *    /    \
     * f(1,2)  f(4,5)
     */
    static Tree.Node buildBST(int[] A) {
        Arrays.sort(A);
        return buildBST(A, 0, A.length - 1);
    }

    static Tree.Node buildBST(int[] A, int s, int e) {
        if (s > e) return null; // return null nodes for leafs
        int mid = (s + e) / 2;
        Tree.Node n = new Tree.Node(A[mid]); // mid index of array is the new node
        n.left = buildBST(A, s, mid - 1); // recursively build on left sub array, attach returned node
        n.right = buildBST(A, mid + 1, e); // recursively build on right sub array, attach returned node
        return n;   // return parent, as the stack unfolds from building left & right subtrees
    }

    public static Tree.Node mergeTwoBSTs(Tree.Node root1, Tree.Node root2) {

        // traverse both trees, inorder, create a resultant sorted array,
        // do binary search way BST creation
        List<Integer> l1 = new ArrayList<>(); List<Integer> l2 = new ArrayList<>();
        List<Integer> l3 = new ArrayList<>();
        inorder(root1, l1);
        inorder(root2, l2);
        int i = 0; int j = 0;
        while (i < l1.size() && j < l2.size()) {
            if (l1.get(i) < l2.get(j)) l3.add(l1.get(i++));
            else l3.add(l2.get(j++));
        }
        while (i < l1.size()) l3.add(l1.get(i++));
        while (j < l2.size()) l3.add(l2.get(j++));

        return buildBST(l3.stream().mapToInt(x->x).toArray(), 0, l3.size()-1);
    }

    static void inorder(Tree.Node n, List<Integer> list) {
        if (n == null) return;
        inorder(n.left, list);
        list.add(n.val);
        inorder(n.right, list);
    }

}
