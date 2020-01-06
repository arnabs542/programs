package com.raj.trees;

import com.raj.binarysearch.KClosestElements;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static com.raj.trees.Util.Node;

public class KClosestNodes {
    /**
     * Given a BST and a target value T.
     * Find K nodes in the tree that are closest to the target value T.
     *
     *              12 (root)
     *          /              \
     *        10                17
     *      /    \            /    \
     *    9       11        13      20
     *           /
     *        10.7
     *
     *
     *               16
     *           14.5
     *        13                17
     *     12     14         13      20
     *         10.7
     */
    public static void main(String[] args) {
        Node root = new Node(12);
        root.left = new Node(10); root.left.left = new Node(9); root.left.right = new Node(11); root.left.right.left = new Node(10.7f);
        root.right = new Node(17); root.right.right = new Node(20); root.right.left = new Node(13);
        System.out.println(brute(root, 11.9d, 3));  // 11,12,13
        System.out.println(subOptimal(root, 11.9d, 3));
        System.out.println(optimal(root, 11.9d, 3));
    }

    /**
     * BruteForce:
     * Since it's a BST, inorder traversal will give a sorted list. Store it in a list ...O(n)
     * Two Approaches:
     * 1> Iterate the list & compute diffs from the target value. Find the min diff. Init L,R ptrs.
     *    Print the smaller of L/R ptr. Incr/Decr ptr until k==0
     *    Time = O(n + n + k), Space = O(n)
     * 2> Optimize 1> by using a modified Binary Search to find nearest element to T
     * @see com.raj.binarysearch.KClosestElements
     *    Time = O(n + logn + k), Space = O(n)   ...still not better than 1> from Big-O perspective
     * Big-O => Runtime = O(n), Space = O(n)
     */
    static List<Double> brute(Node n, double T, int K) {
        List<Double> nodeList = new ArrayList<>();
        inorder(n, nodeList);
        return KClosestElements.findKClosest(nodeList.stream().mapToDouble(x->x).toArray(), T, K);
    }

    static void inorder(Node n, List<Double> nodeList) {
        if (n == null) return;
        inorder(n.left, nodeList);
        nodeList.add(n.val);
        inorder(n.right, nodeList);
    }

    /**
     * Sub-Optimal: To reduce runtime, we have to somehow not look at all elements.
     * # Search BST for T. Since we are looking for K closest nodes, we need some priority based on closeness.
     * # Hence, fill up a k-sized max heap which stores Element{diff,Node}
     * # Once full, pop if the incoming node has lower diff.
     * # Stop when incoming node doesn't add to heap (means this & further nodes will be of greater diff)
     * Time = O(k + (n-k)log k) ...k to fill up heap. n-k nodes remain to traverse, each time we may add to heap at logk cost.
     * Space = O(k + log n) ...k for heap & log n for BST search rec stack
     */
    static List<Double> subOptimal(Node n, double T, int K) {
        List<Double> res = new ArrayList<>();
        // max heap
        PriorityQueue<E> pq = new PriorityQueue<>((a,b) -> Double.compare(b.diff,a.diff));
        inorderPQ(n, T, K, pq);
        while (!pq.isEmpty()) res.add(pq.poll().val);
        return res;
    }

    static void inorderPQ(Node n, double T, int K, PriorityQueue<E> pq) {
        if (n == null) return;
        inorderPQ(n.left, T, K, pq);
        double diff = Math.abs(T-n.val);
        if (pq.size() < K) pq.add(new E(n.val, diff));
        else if (diff < pq.peek().diff) {
            pq.poll();
            pq.add(new E(n.val, diff));
        } else {    // if not adding to heap, it means no need to traverse further as other nodes are definitely higher than this (inorder traverse does a increasing sequence)
            return;
        }
        inorderPQ(n.right, T, K, pq);
    }

    /**
     * Optimal: To reduce runtime further, we need to binary search while traversing itself to reach O(logn)
     * Also, we need to bbe able to get before & next node to get k closest nodes in logn time as well.
     * Basically, the crux of the problem now lies in getting predecessor & successor of a given node.
     * getPredecessor() & getSuccessor() should run in O(height of tree)
     * Time = O(klogn)   ... max k times getSuccessor() / getPredecessor()
     * Space = O(logn)   ... rec stack space
     */
    static List<Double> optimal(Node root, double T, int K) {
        List<Double> res = new ArrayList<>();
        double L = getPred(root, T);
        double R = getSucc(root, T);
        while (K > 0) {
            if (T-L < R-T) {
                res.add(L);
                L = getPred(root, L);
                K--;
            } else {
                res.add(R);
                R = getSucc(root, R);
                K--;
            }
        }
        return res;
    }

    static double getPred(Node root, double T) {
        SuccessorPredecessorBST.PredSucc predSucc = new SuccessorPredecessorBST.PredSucc();
        SuccessorPredecessorBST.findInorderSuccessorPredecessor(root, T, predSucc);
        return predSucc.pred.val;
    }

    static double getSucc(Node root, double T) {
        SuccessorPredecessorBST.PredSucc predSucc = new SuccessorPredecessorBST.PredSucc();
        SuccessorPredecessorBST.findInorderSuccessorPredecessor(root, T, predSucc);
        return predSucc.succ.val;
    }

    static class E {
        double val,diff;
        E(double v, double d) { val=v; diff=d; }
    }

}
