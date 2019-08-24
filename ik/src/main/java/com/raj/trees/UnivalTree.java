package com.raj.trees;

/**
 * @author rshekh1
 */
public class UnivalTree {

    /**
     * Given a binary tree, write a program to count the number of Single Valued Subtrees.
     * A Single Valued Subtree is one in which all the nodes have same value.
     * Note that a leaf qualifies as a unival tree
     *
     * A Simple Solution is to traverse the tree. For every traversed node, check if all values under this node are
     * same or not. If same, then increment count. Time complexity of this solution is O(n2).
     *
     * An Efficient Solution O(n) is to traverse the tree in bottom up manner. For every subtree visited, return true
     * if subtree rooted under it is single valued and increment count. So the idea is to use count as a reference
     * parameter in recursive calls and use returned values to find out if left and right subtrees are single valued
     * or not.
     */
    public static void main(String[] args) {
        /**
         *          5
         *        /   \
         *       4     5
         *     /  \     \
         *    4    4     5
         *    => has 5 subtrees with single values (three 4's, two 5's)
         */
        Tree tree = new Tree();
        tree.root = new Tree.Node(5);
        tree.root.left = new Tree.Node(4); tree.root.right = new Tree.Node(5);
        tree.root.left.left = new Tree.Node(4); tree.root.left.right = new Tree.Node(4); tree.root.right.right = new Tree.Node(5);
        Tree.printBreadthFirst(tree.root);
        Counter counter = new Counter();
        System.out.println(_countSame(tree.root, counter));
        System.out.println(counter.count);
    }

    static boolean countSame(Tree.Node node, Counter counter) {
        if (node == null) return true;  // no need to incr count

        // do post order traversal to figure out left & right subtrees, add up univals along the way
        boolean isLeftSame = countSame(node.left, counter);
        boolean isRightSame = countSame(node.right, counter);

        // find negates as it straightaway gives the answer at the current level, without evaluating subtrees fully
        if (!isLeftSame || !isRightSame) return false;

        // now root processing
        if (node.left != null && node.left.val != node.val) return false;
        if (node.right != null && node.right.val != node.val) return false;

        // everything good, incr count as it's a unival subtree node
        counter.count++;
        return true;
    }

    static boolean _countSame(Tree.Node node, Counter counter) {
        if(node == null) return true;

        // dfs left & right sub trees
        _countSame(node.left, counter);
        _countSame(node.right, counter);

        /**
         * At leaf levels or penultimate leaf levels, find answers as follows :-
         * leaf node => true +1
         * node with 1 child & values same => true +1
         * node with 2 child & values same => true +1
         */
        if ((node.left == null && node.right == null)
                || (node.left == null && node.right != null && node.right.val == node.val)
                || (node.left != null && node.right == null && node.left.val == node.val)
                || (node.left != null && node.right != null && node.left.val == node.val && node.right.val == node.val)) {
            counter.count ++;
            return true;
        } else {
            return false;
        }
    }

    static class Counter {
        public int count;
    }

}
