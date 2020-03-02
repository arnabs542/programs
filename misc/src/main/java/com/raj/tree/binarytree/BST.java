package com.raj.tree.binarytree;

import com.raj.tree.Node;

import java.util.LinkedList;
import java.util.Queue;

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

        System.out.println("InOrder traversal: ");
        bst1.inorder(bst1.getRoot());
        System.out.println();

        System.out.println("LevelOrder traversal: ");
        bst1.levelOrder(bst1.getRoot());
        System.out.println();

        System.out.println("Print paths: ");
        bst1.printPaths(root, 0, new int[10]);

        System.out.println("Print paths with sum: ");
        bst1.printPathSum(root, 23, new int[10], 0);

        System.out.println("\nPrint size: " + bst1.size(root));
        System.out.println("Print height: " + bst1.height(root));
        System.out.println("Print sum: " + bst1.sum(root));

        System.out.println("isBST? " + bst1.isBST(root, Integer.MIN_VALUE));

        System.out.println("inorder successor of 8 => " + bst1.inorderSuccessor(root, 8));
        System.out.println("inorder predecessor of 8 => " + bst1.inorderPredecessor(root, 8));

        /*System.out.println("Delete Node from BST");
        bst1.delete(5);
        System.out.println("Print paths: ");
        bst1.printPaths(root, 0, new int[10]);*/

        System.out.println("Original Tree: ");
        bst1.printPaths(root, 0, new int[10]);
        System.out.println("Mirror Tree: ");
        Node mirror = bst1.mirror(root);    // alters original tree as well
        bst1.printPaths(mirror, 0, new int[10]);

        System.out.println("Is Mirror Tree ? " + bst1.isMirrorTree(root, mirror));  // false as original tree was altered

        Node clone = bst1.cloneTree(root);
        System.out.println("Is Same Tree ? " + bst1.isSameTree(root, clone));

        bst1.mirror(root); // revert back to original tree
        System.out.println("LCABST of 2,8 -> " + bst1.LCABST(root, 2, 8));
        System.out.println("LCA of 2,8 -> " + bst1.LCAAnyBTree(root, 2, 8));

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

    // left -> node -> right
    public void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.data + " ");
        inorder(n.right);
    }

    // node -> left -> right
    public void preorder(Node n) {
        if (n == null) return;
        System.out.println(n.data);
        preorder(n.left);
        preorder(n.right);
    }

    // left -> right -> node
    public void postorder(Node n) {
        if (n == null) return;
        postorder(n.left);
        postorder(n.right);
        System.out.println(n.data);
    }

    // level order - BFS
    public void levelOrder(Node n) {
        // maintain a queue to print as we traverse in FIFO order
        Queue<Node> queue = new LinkedList<>();
        queue.add(n);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.print(cur.data + " ");
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
        }
        System.out.println();
    }

    /**
     * O(log n) complexity as it keeps excluding half subtree at each node, if balanced tree
     * O(height_of_tree) is a better way to express as even for degenerate trees(linked list type), it will hold true
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

    Node deleteRec(Node node, int key)
    {
        // Base Case: If the tree is empty
        if (node == null) return node;

        // Otherwise, recur down the tree trying to find the node to be deleted
        if (key < node.data) node.left = deleteRec(node.left, key);     // deletion is as simple as changing the pointer
        else if (key > node.data) node.right = deleteRec(node.right, key);
        // node to be deleted found
        else {
            // case 1: node with no child
            if (node.left == null && node.right == null) return null;

            // case 2: node with one child
            if (node.left == null) return node.right;
            else if (node.right == null) return node.left;

            // case 3: node with two children
            // Find the min in the right subtree or even max in left (either is fine)
            node.data = minValue(node.right).data;
            node.right = deleteRec(node.right, node.data);  // Delete the node recursively
        }
        return node;
    }

    // min value in BST is deepest left node
    public Node minValue(Node n) {
        if (n.left == null) return n;
        else return minValue(n.left);
    }

    // find the inorder successor of data, given root n
    public Node inorderSuccessor(Node n, int data) {

        Node current = search(n, data);     // node for which successor needs to be found

        if (n == null || current == null) return null;

        // Case 1: Node has right subtree
        if (current.right != null) return minValue(current.right);

        // Case 2: Node has NO right subtree - traverse from root again, for each left traversal re-assign successor as it's ancestor
            // while for each right traversal don't re-assign. Hence, we'll find successor whose left subtree has the searched node
        else {
            Node node = n; // start with root
            Node successor = null;

            while (node != current) {
                if (data < node.data) {
                    successor = node;  // save the successor for each left traversal
                    node = node.left;  // now move left, thus saving last known successor
                } else {
                    node = node.right;
                }
            }
            return successor;
        }
    }

    public Node inorderPredecessor(Node n, int data) {
        Node current = search(n, data);     // node for which successor needs to be found

        if (n == null || current == null) return null;

        // Case 1: Node has left subtree - then it's immediate left is the predecessor
        if (current.left != null) return current.left;

        // Case 2: Node has NO left subtree - then it's immediate parent is the predecessor
        else {
            Node node = n; // start with root
            Node predecessor = null;

            while (node != current) {
                if (data < node.data) {
                    node = node.left;
                } else {
                    predecessor = node;  // save the successor for each left traversal
                    node = node.right;
                }
            }
            return predecessor;
        }
    }

    public Node search(Node n, int data) {
        if (n == null) return null;
        if (n.data == data) return n;
        if (data < n.data) return search(n.left, data);
        else return search(n.right, data);
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
     * Clone a tree
     */
    public Node cloneTree(Node n1) {
        if (n1 == null) return null;
        Node n2 = new Node(); n2.data = n1.data;
        n2.left = cloneTree(n1.left);
        n2.right = cloneTree(n1.right);
        return n2;
    }

    /**
     * Mirror a tree
     */
    public Node mirrorTree(Node n) {
        if (n == null) return null;
        Node t = n.left;    // swap left, right
        n.left = n.right;
        n.right = t;
        mirrorTree(n.left);
        mirrorTree(n.right);
        return n;
    }

    /**
     * Mirror tree, cleaner & elegant
     */
    public Node mirror(Node node) {
        if (node == null) return null;
        Node left = mirror(node.left);
        Node right = mirror(node.right);
        node.left = right;
        node.right = left;
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

    // O(n^2) solve, not very efficient
    public boolean isBST(Node n) {
        if (n == null) return true;
        return isLeftSubTreeLesser(n) && isRightSubTreeGreater(n)
        && isBST(n.left) && isBST(n.right);
    }

    private boolean isLeftSubTreeLesser(Node n) {
        return n.data > n.left.data && isLeftSubTreeLesser(n.left) && isLeftSubTreeLesser(n.right);
    }

    private boolean isRightSubTreeGreater(Node n) {
        return n.data < n.right.data && isLeftSubTreeLesser(n.left) && isLeftSubTreeLesser(n.right);
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
        if (lookup(n, a) && lookup(n, b)) return _LCABST(n, a, b);  // Pre-check : both node should exist in BST, else return null
        else return null;
    }

    private Node _LCABST(Node n, int a, int b) {
        if (n == null) return null;
        if (n.data == a || n.data == b) return n;    // one of the nodes is parent of other, but check if the other exists though
        if (a < n.data && b < n.data) return _LCABST(n.left, a, b);  // both a & b are smaller than n, LCA exists in left subtree
        if (a > n.data && b > n.data) return _LCABST(n.right, a, b); // both a & b are greater than n, LCA exists in right subtree
        if(a < n.data && b > n.data) return n;
        return null;
    }

    /**
     * LCA of any Binary Tree : Little tricky, but idea is the same.
     *
     * The idea is to traverse the tree starting from root.
     * # If any of the given keys (n1 and n2) matches with root, then root is LCA (assuming that both keys are present).
     * # If root doesn’t match with any of the keys, we recur for left and right subtree. The node which has one key present in its left subtree and the other key present in right subtree is the LCA.
     * # If both keys lie in left subtree, then left subtree has LCA also, otherwise LCA lies in right subtree.
     */
    public Node LCAAnyBTree(Node n, int a, int b) {
        if (lookupAnyBTree(n, a) && lookupAnyBTree(n, b)) return _LCAAnyBTree(n, a, b); // Pre-check : both node should exist in BST, else return null
        else return null;
    }

    private Node _LCAAnyBTree(Node n, int a, int b) {      // assuming both keys exists in the tree via checking lookup()

        if (n == null) return null;

        // If either n1 or n2 matches with root, LCA is root
        if (n.data == a || n.data == b) return n;

        // Look for keys in left and right subtrees, if a or b is present in them, then we wud get answer as non-null
        Node left = _LCAAnyBTree(n.left, a, b);
        Node right = _LCAAnyBTree(n.right, a, b);

        // If both of the above calls return non-null, then one node is present in left subtree and other is present in right,
        // so this node is the LCA
        if (left != null && right != null) return n;

        // Otherwise return the non-null node found, so that once we get the other node, we find the answer
        return (left != null) ? left : right;
    }

}
