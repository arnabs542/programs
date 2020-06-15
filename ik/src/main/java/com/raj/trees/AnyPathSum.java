package com.raj.trees;

import java.util.HashMap;

public class AnyPathSum {
    /**
     * Find the number of paths that sum to a given value.
     * The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).
     *
     * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
     *
     *       10
     *      /  \
     *     5   -3
     *    / \    \
     *   3   2   11
     *  / \   \
     * 3  -2   1
     *
     * Return 3. The paths that sum to 8 are:
     *
     * 1.  5 -> 3
     * 2.  5 -> 2 -> 1
     * 3. -3 -> 11
     */
    public static void main(String[] args) {
        Node n = new Node(10);
        n.left = new Node(5); n.left.left = new Node(3); n.left.left.left = new Node(3);
        n.left.right = new Node(2); n.left.right.right = new Node(1);
        n.right = new Node(-3); n.right.right = new Node(11);
        System.out.println(pathSum(n, 8));
    }

    static int pathSum(Node root, int sum) {
        HashMap<Integer, Integer> preSum = new HashMap();
        preSum.put(0,1);
        helper(root, 0, sum, preSum);
        return count;
    }

    static int count = 0;
    static void helper(Node root, int currSum, int target, HashMap<Integer, Integer> preSum) {
        if (root == null) {
            return;
        }

        currSum += root.val;

        // did we see this sum already? If yes, add up num ways
        if (preSum.containsKey(currSum - target)) {
            count += preSum.get(currSum - target);
        }

        // update new sum ways into map for later reuse
        if (!preSum.containsKey(currSum)) {
            preSum.put(currSum, 1);
        } else {
            preSum.put(currSum, preSum.get(currSum)+1);
        }

        helper(root.left, currSum, target, preSum);
        helper(root.right, currSum, target, preSum);

        // revert back this sum way by -1 as we backtrack
        preSum.put(currSum, preSum.get(currSum) - 1);
    }

    static class Node {
        Node left,right;
        int val;

        public Node(int val) {
            this.val = val;
        }

        static Node of(int val) {
            return new Node(val);
        }
    }
}
