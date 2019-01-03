package com.raj.tree.binarytree;

import com.raj.tree.Node;

/**
 * @author: sraj1
 * Date:    10/10/12
 */
public class BST {

    static int cnt = 0; // to count iterations
    int maxSum = 0;
    private Node root;

    public static void main(String[] args) {

        /*
               10
              /  \
             5    20
            / \   /
           2  8  15

         */

        BST bst1 = new BST();
        bst1.insert(10);
        bst1.insert(20);
        bst1.insert(5);
        bst1.insert(8);
        bst1.insert(15);
        bst1.insert(2);

        Node root = bst1.getRoot();
        System.out.println("Lookup 5? " + bst1.lookup(root, 5) + "\n");

        System.out.println("Inorder traversal: ");
        bst1.inorder(bst1.getRoot());
        System.out.println();

        System.out.println("Print paths: ");
        bst1.printPaths(root, 0, new int[10]);

        System.out.println("Print paths with sum: ");
        bst1.printPathSum(root, 23, new int[10], 0);

        System.out.println("\nPrint size: " + bst1.size(root));
        System.out.println("Print height: " + bst1.height(root));
        System.out.println("Print sum: " + bst1.sum(root));

        System.out.println("isBST? " + bst1.isBST(root, Integer.MIN_VALUE));

        /*System.out.println("Delete Node from BST");
        bst1.delete(5);
        System.out.println("Print paths: ");
        bst1.printPaths(root, 0, new int[10]);*/

        System.out.println("Mirror Tree: ");
        //bst1.mirrorTree(root);
        Node mirror = bst1.mirror(root);    // root will alter (same as mirror)
        bst1.printPaths(mirror, 0, new int[10]);

        System.out.println("Is Mirror Tree ? " + bst1.isMirrorTree(root, mirror));
        System.out.println("Is Same Tree ? " + bst1.isSameTree(root, mirror));

        /*
              -10
             /  \
            5    20
           / \   /
          2  8  -15

        */
        Node n1 = new Node(-10);
        Node root1 = n1;
        n1.left = new Node(5);
        n1.left.left = new Node(2);
        n1.left.right = new Node(8);
        n1.right = new Node(20);
        n1.right.left = new Node(-15);
        System.out.println("Print paths: ");
        bst1.printPaths(root1, 0, new int[10]);

        System.out.println("Print max sum: " + bst1.maxSum2(root1));

        BTreeMaxSum bTreeMaxSum = new BTreeMaxSum();
        bTreeMaxSum.setRoot(root1);
        System.out.println("Print max sum BTree: " + bTreeMaxSum.getMaxSum() + ", Max Sum node is " + bTreeMaxSum.getMaxSumNode());

        BTreeHelper bTreeHelper = new BTreeHelper();
        int[] arr = bTreeHelper.getMaxSumPathArr(root1);
        for (int i = 0; i <= bTreeHelper.getEndIdx(); i++)
            System.out.println(arr[i]);
        System.out.println(bTreeHelper.getMaxSum());
        System.out.println(bst1.isSameTree(root, root1));
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node insert(int data) {
        root = insert(root, data);
        return root;
    }

    public Node insert(Node n, int data) {
        if (n == null)
            return new Node(data);
        if (data < n.data)
            n.left = insert(n.left, data);
        else n.right = insert(n.right, data);

        return n;
    }

    public void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.data + " ");
        inorder(n.right);
    }

    public void preorder(Node n) {
        if (n == null) return;
        System.out.println(n.data);
        preorder(n.left);
        preorder(n.right);
    }

    public void postorder(Node n) {
        if (n == null) return;
        postorder(n.left);
        postorder(n.right);
        System.out.println(n.data);
    }

    /**
     * O(log n) complexity as it keeps excluding half subtree at each node
     */
    public boolean lookup(Node n, int data) {
        if (n == null) return false;
        if (n.data == data) return true;
        if (data < n.data)
            return lookup(n.left, data);
        else return lookup(n.right, data);
    }

    /**
     * O(n) as it searches all left and right subtrees
     */
    public boolean lookupAnyBTree(Node n, int data) {
        if (n == null) return false;
        if (n.data == data) return true;
        return (lookupAnyBTree(n.left, data) || lookupAnyBTree(n.right, data));
    }

    /**
     * Print all possible paths from root to leafs
     * Algo :
     * - While traversing tree, we need to keep track of nodes traversed. Use either a LL or array simply
     * - if(n==null) return
     * - arrVisitedNodes[depth + 1] = n.data;
     * - {print all arr till depth; return;}
     * - traverse left, right subtrees;
     */
    public void printPaths(Node n, int depth, int[] arr) {
        if (n == null) return;

        arr[depth] = n.data;                        // process node

        if (n.left == null && n.right == null) {    // print array if leaf node which is a node with both left & right as null
            for (int i = 0; i <= depth; i++) System.out.print(arr[i] + " ");
            System.out.println();
            return;
        }

        printPaths(n.left, depth + 1, arr);   // left subtree recurse
        printPaths(n.right, depth + 1, arr);  // right subtree recurse
    }

    /**
     * Algo:
     * - null check
     * - return this node size i.e. 1 + size of left subtree + size of right subtree
     */
    public int size(Node n) {
        if (n == null) return 0;
        return 1 + size(n.left) + size(n.right);
    }

    /**
     * Algo:
     * - null check
     * - return 1 + max of (height of left subtree, height of right subtree)
     */
    public int height(Node n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    /**
     * Print the sum of all nodes below a given node
     * <p/>
     * Algo:
     * - null check
     * - return node's data + sum(left subtree) + sum(right subtree)
     */
    public int sum(Node n) {
        if (n == null) return 0;    cnt++;
        return n.data + sum(n.left) + sum(n.right);
    }

    /**
     * Delete a node in BST
     * https://www.geeksforgeeks.org/binary-search-tree-set-2-delete/
     *
     * Algo:
     * ## Cases ##
     * The node to be deleted is:
     * - Leaf, find it and set it to null.
     * - Has just one child, track it's parent and it's left/right as node's left/right.
     * - Has both left & right, find the minValue from right subtree and swap it's value with this node and
     * set minValue node to null.
     * - Keep tracking parent
     */
    // This method mainly calls deleteRec()
    public void delete(int key) {
        root = deleteRec(root, key);
    }

    /* A recursive function to insert a new key in BST */
    Node deleteRec(Node node, int key)
    {
        /* Base Case: If the tree is empty */
        if (node == null) return node;

        /* Otherwise, recur down the tree */
        if (key < node.data) node.left = deleteRec(node.left, key);
        else if (key > node.data) node.right = deleteRec(node.right, key);
        else {      /* node to be deleted found */
            // case 1: node with only one child or no child
            if (node.left == null) return node.right;
            else if (node.right == null) return node.left;

            // case 2: node with two children - Get the inorder successor (smallest in the right subtree)
            node.data = minValue(node.right).data;
            node.right = deleteRec(node.right, node.data);  // Delete the inorder successor
        }
        return node;
    }

    public Node minValue(Node n) {
        if (n.left == null) return n;
        else return minValue(n.left);
    }

    /**
     * Find maxSum node, which is maximum sum of all left & right nodes, given a node:
     * - init maxSum global var which stores max sum
     * - for a node, find sum as this node's data + sum of left subtree + sum of right subtree
     * - maxSum = max(sum, maxSum)
     * O(n^2) soln
     */
    public int maxSum2(Node n) {
        maxSum3(n);
        return maxSum;
    }

    private int maxSum3(Node n) {
        if (n == null) return 0;
        int sum = n.data + maxSum3(n.left) + maxSum3(n.right);  // this is the sum for this node
        maxSum = Math.max(sum, maxSum);     // update maxSum before returning sum
        return sum;
    }

    /**
     * Print path which sums up to X.
     */

    public void printPathSum(Node n, int sum, int arr[], int depth) {
        if (n == null) return;
        arr[depth] = n.data;
        int subSum = sum - n.data;

        if (n.left == null && n.right == null && subSum == 0) {
            for (int i = 0; i <= depth; i++) {
                System.out.print(arr[i] + " ");
            }
        }
        printPathSum(n.left, subSum, arr, depth + 1);
        printPathSum(n.right, subSum, arr, depth + 1);
    }

    /**
     * Mirror a tree
     */
    public void mirrorTree(Node n) {
        if (n == null) return;
        Node t = n.left;    // swap left, right
        n.left = n.right;
        n.right = t;
        mirrorTree(n.left);
        mirrorTree(n.right);
    }

    /**
     * Mirror tree, cleaner & elegant
     */
    public Node mirror(Node node) {
        if (node == null) return node;

        Node t = node.left;     // save left as it would be modified
        node.left = mirror(node.right);
        node.right = mirror(t);

        return node;
    }

    /**
     * Check if two b-trees are same
     */
    public boolean isSameTree(Node n1, Node n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;
        if (n1.data != n2.data) return false;
        return isSameTree(n1.left, n2.left) && isSameTree(n1.right, n2.right);
    }

    /**
     * Check if two b-trees are mirror of each other
     */
    public boolean isMirrorTree(Node n1, Node n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;
        if (n1.data != n2.data) return false;
        return isMirrorTree(n1.left, n2.right) && isMirrorTree(n1.right, n2.left);
    }


    /**
     * Check if a BTree is a BST.
     * <p/>
     * Algo1A: A BST's inorder traversal should produce sorted list. Traverse, store in list, check if sorted. Uses O(n) extra time/space.
     * Algo1B: While traversing check if the previous node's value was lesser than current node's value. Best soln.
     * Algo2: While traversing, narrow min/max and a node's value should lie within this range.
     */
    public boolean isBST(Node n, int prevData) {    // algo 1B
        if (n == null) return true;
        // traverse left
        boolean leftAnswer = isBST(n.left, prevData);
        // process node
        if (prevData > n.data) return false;
        prevData = n.data;
        // traverse right
        return leftAnswer && isBST(n.right, prevData);
    }

    public boolean isBST2(Node n) {
        return _isBST2(n, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // https://www.geeksforgeeks.org/a-program-to-check-if-a-binary-tree-is-bst-or-not/
    public boolean _isBST2(Node n, int min, int max) {
        if (n == null) return true;
        if (n.data < min || n.data > max) return false;
        return _isBST2(n.left, min, n.data-1)   // going left means the max value allowed will be 1 lesser than current
                && _isBST2(n.right, n.data+1, max); // similarly going right means the min value will at least increase by 1
    }

    /**
     * https://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/
     * Method 1 (By Storing root to n1 and root to n2 paths):
     * Following is simple O(n) algorithm to find LCA of n1 and n2.
     * 1) Find path from root to n1 and store it in a vector or array.
     * 2) Find path from root to n2 and store it in another vector or array.
     * 3) Traverse both paths till the values in arrays are same. Return the common element just before the mismatch.
     *
     * Method 2
     * LCA of a BST : Easy as it is BST and we can leverage that info. Use comparison of nodes <,=,> and cover all cases
     */
    public Node LCABST(Node n, int a, int b) {
        // Pre-check : both node should exist in BST, else return null
        if (lookup(n, a) && lookup(n, b)) return LCABSTHelper(n, a, b);
        return null;
    }

    private Node LCABSTHelper(Node n, int a, int b) {
        if (n == null) return null;
        if (n.data == a || n.data == b)
            return n;    // one of the nodes is parent of other, but check if the other exists though
        if (a < n.data && b < n.data) return LCABSTHelper(n.left, a, b);  // both a & b are smaller, recurse left
        if (a > n.data && b > n.data) return LCABSTHelper(n.right, a, b); // both a & b are greater, recurse right
        else return n;  // i.e. if(a < n.data && b > n.data) this node is LCA
    }

    /**
     * LCA of a BT : Little tricky, but idea is the same.
     */
    public Node LCA(Node n, int a, int b) {
        // Pre-check : both node should exist in BST, else return null
        if (lookup(n, a) && lookup(n, b)) return LCAHelper(n, a, b);
        return null;
    }

    private Node LCAHelper(Node n, int a, int b) {
        if (n == null) return null;
        if (n.data == a || n.data == b)
            return n;    // one of the nodes is parent of other, but check if the other exists though
        Node left = LCAHelper(n.left, a, b);
        Node right = LCAHelper(n.right, a, b);
        if (left != null) return left;
        else return right;
    }

}
