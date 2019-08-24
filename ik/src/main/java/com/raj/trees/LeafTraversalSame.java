package com.raj.trees;

import java.util.Stack;

/**
 * @author rshekh1
 */
public class LeafTraversalSame {

    public static void main(String[] args) {
        // TODO : build trees
        System.out.println(isEqual(new LeafIterator(new Tree.Node(5)), new LeafIterator(new Tree.Node(5))));
    }

    static boolean isEqual(Iterator i1, Iterator i2) {
        while (i1.hasNext() && i2.hasNext()) {
            if (i1.next().val != i2.next().val) return false;  // mismatch
        }
        return !i1.hasNext() && !i2.hasNext();  // both iters have to be completed
    }

    /**
     * Check if two trees have same nodes while leaf traversal.
     * Use a stack to traverse & keep track of nodes. If a leaf occurs, return.
     * Even better design a generic leaf iterator for comparison later.
     */
    interface Iterator {
        boolean hasNext();
        Tree.Node next();
    }

    static class LeafIterator implements Iterator {

        Stack<Tree.Node> stack = new Stack<>();
        Tree.Node curr = null;

        public LeafIterator(Tree.Node n) {
            stack.push(n);
        }

        @Override
        public boolean hasNext() {
            if (curr != null) return true;
            while (!stack.isEmpty()) {
                Tree.Node node = stack.pop();
                if (node.left == null && node.right == null) {
                    curr = node;
                    return true;
                }
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
            }
            return false;
        }

        @Override
        public Tree.Node next() {
            if (!hasNext()) throw new IllegalStateException("Nothing in stack !!");
            Tree.Node res = curr;
            curr = null;
            return res;
        }
    }
}
