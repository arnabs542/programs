package com.raj.trees;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MaxSumNonAdjNodes {
    /**
     * Maximum sum of nodes in Binary tree such that no two are adjacent.
     * [IK Mock] Thief gets caught if he does burglary from adjacent houses, but is safe if not adjacent.
     * Find the max loot without getting caught.
     *
     *        1
     *     3     3
     *  1      4   5
     *  MaxSum = 3 + 4 + 5 = 12
     */
    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(3);
        root.left.left = new Node(1);
        root.right = new Node(3);
        root.right.right = new Node(5);
        root.right.left = new Node(4);
        System.out.println(maxSum(root, null));  // 12
        System.out.println("cmp = " + cmp); cmp = 0;
        System.out.println(maxSum_memo(root, null));  // 12
        System.out.println("cmp = " + cmp); cmp = 0;

        root = new Node(3);
        root.left = new Node(70);
        root.right = new Node(10);
        root.right.right = new Node(4);
        root.right.right.left = new Node(100);
        root.right.left = new Node(300);
        System.out.println(maxSum(root, null));  // 470 = 70+100+300
    }

    /**
     * Brute force:
     * include/exclude this node & recurse on left/right
     * return max of both options
     *
     * Time = O(2^height of tree)  ...branching factor is 2 for incl/excl options
     */
    static int cmp = 0;
    static int maxSum(Node n, Node prev) {
        if (n == null) return 0;
        cmp++;
        // excl
        int excl = maxSum(n.left, null) + maxSum(n.right, null);

        // incl
        int incl = Integer.MIN_VALUE;
        if (prev == null || (prev.left != n && prev.right != n)) { // not adjacent check
            incl = n.val + maxSum(n.left, n) + maxSum(n.right, n);
        }

        return Math.max(incl, excl);
    }

    /**
     * Optimize BF by memoizing results to sub-problems, thereby reducing the runtime to O(n)
     */
    static Map<Node,Integer> memo = new HashMap<>();
    static int maxSum_memo(Node n, Node prev) {
        if (n == null) return 0;
        if (memo.get(n) != null) {
            System.out.println("memo.get " + n + " = " + memo.get(n));
            return memo.get(n);
        }

        cmp++;
        // excl
        int excl = maxSum_memo(n.left, null) + maxSum_memo(n.right, null);

        // incl
        int incl = Integer.MIN_VALUE;
        if (prev == null || (prev.left != n && prev.right != n)) { // not adjacent check
            incl = n.val + maxSum_memo(n.left, n) + maxSum_memo(n.right, n);
        }

        memo.put(n, Math.max(incl, excl));
        return memo.get(n);
    }

    static class Node {
        int val;
        Node left;
        Node right;
        Node(int v) { val = v; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return val == node.val &&
                    Objects.equals(left, node.left) &&
                    Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, left, right);
        }

        @Override
        public String toString() {
            return val + "";
        }
    }

}