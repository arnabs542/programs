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
        Node root = new Node(1,1);   // id,val to simplify hashcode/equals
        root.left = new Node(2,3);
        root.left.left = new Node(4,1);
        root.right = new Node(3,3);
        root.right.right = new Node(6,5);
        root.right.left = new Node(5,4);

        System.out.println(maxSum(root, null));  // 12
        System.out.println("BF cmp = " + cmp); cmp = 0; memo.clear();
        System.out.println();

        Pair maxSum = maxSum_memo(root);
        System.out.println(Math.max(maxSum.incl, maxSum.excl));  // 12
        System.out.println(memo_pr + ", memo_pr cmp = " + cmp); cmp = 0; memo_pr.clear();
        System.out.println();

        System.out.println(_maxSum(root));  // 12
        System.out.println(memo + ", memo cmp = " + cmp); cmp = 0; memo.clear();
        System.out.println();

        /**
         *          3
         *     70        10
         *           300     4
         *                100
         * maxsum => 470 = 70+100+300
         */
        root = new Node(1,3);
        root.left = new Node(2,70);
        root.right = new Node(3,10);
        root.right.right = new Node(5,4);
        root.right.right.left = new Node(6,100);
        root.right.left = new Node(4,300);

        System.out.println(maxSum(root, null));
        System.out.println("BF cmp = " + cmp); cmp = 0; memo.clear();
        System.out.println();

        maxSum = maxSum_memo(root);
        System.out.println(Math.max(maxSum.incl, maxSum.excl));
        System.out.println(memo_pr + ", memo_pr cmp = " + cmp); cmp = 0; memo_pr.clear();
        System.out.println();

        System.out.println(_maxSum(root));
        System.out.println(memo + ", memo cmp = " + cmp); cmp = 0; memo.clear();
        System.out.println();
    }

    /**
     * Brute force:
     * # subset pattern
     * # include/exclude this node & recurse on left/right
     *   - when including a node, make sure it wasn't included previously by using a prev ptr (if null means, it wasn't)
     * # return max of both options
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

        // return max of both options
        return Math.max(incl, excl);
    }

    /**
     * Optimize BF by memoizing results to sub-problems
     *  - But can't just memo as we need to consider the effects of including/excluding nodes
     *  - what if we return both incl/excl answers for each node & memoize it?
     * Time = O(n)
     */
    static Map<Node,Pair> memo_pr = new HashMap<>();
    static Pair maxSum_memo(Node n) {
        if (n == null) return new Pair(0,0);

        if (memo_pr.containsKey(n)) {
            return memo_pr.get(n);
        }

        cmp++;
        // get left & right subtree incl/excl answers
        Pair left = maxSum_memo(n.left);
        Pair right = maxSum_memo(n.right);

        // exclude this node - just add the max from left & right
        int excl =  Math.max(left.incl, left.excl) + Math.max(right.incl, right.excl);

        // include this node - use the excl answers from left & right subtrees (for skip adj condition)
        int incl = n.val + left.excl + right.excl;

        memo_pr.put(n, new Pair(incl, excl));
        return memo_pr.get(n);
    }

    /**
     * Eagerly compute skip levels when including instead of tracking prev
     * memoize the max value at that node
     * Time = O(n)
     */
    static Map<Node,Integer> memo = new HashMap<>();
    static int _maxSum(Node n) {
        if (n == null) return 0;

        if (memo.containsKey(n)) return memo.get(n);

        cmp++;
        // if excluded, include the childs
        int excl = _maxSum(n.left) + _maxSum(n.right);

        // if included, skip the childs
        int incl = n.val;
        if (n.left != null) incl += _maxSum(n.left.left) + _maxSum(n.left.right);
        if (n.right != null) incl += _maxSum(n.right.left) + _maxSum(n.right.right);

        memo.put(n, Math.max(incl, excl));
        return memo.get(n);
    }

    static class Node {
        int id;
        int val;
        Node left;
        Node right;
        Node(int id, int v) { this.id = id; this.val = v; }

        @Override
        public boolean equals(Object o) {
            if (this == o || this.id == ((Node)o).id) return true;
            else return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return val + "";
        }
    }

    static class Pair {
        int incl, excl;

        public Pair(int incl, int excl) {
            this.incl = incl;
            this.excl = excl;
        }

        @Override
        public String toString() {
            return incl + ":" + excl;
        }
    }
}