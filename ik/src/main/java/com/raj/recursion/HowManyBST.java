package com.raj.recursion;

/**
 * @author rshekh1
 */
public class HowManyBST {
    /**
     * How Many Binary Search Trees With n Nodes?
     * 1 => 1
     * 2 => 2
     * 3 =>        Root   +  Root     +        2      = 2 + 2 + 1 = 5
     *             /           \             /  \
     *      {2 left}          {2 right}     1    3
     *       subtree           subtree
     *
     * 4 =>  {3}-root + root-{3} + {2}-root-{1} + {1}-root-{2}  =  5 + 5 + 2 + 2 = 14
     *
     * 5 =>  {4}-root + root-{4} + {3}-root-{1} + {1}-root-{3} + {2}-root-{2}  =  14 + 14 + 5 + 5 + 2*2 = 42
     *
     * ....
     *                           f(5)
     *       will need     f(4) f(3) f(2) f(1)
     *          f(3) f(2) f(1)  f(2) f(1) ...   overlapping sub-problem
     * Repeated pattern, we we save it we can re-use earlier computed values.
     * 1> Recursive
     * 2> Recursive with memoization
     * 3> DP
     *
     */
    public static void main(String[] args) {
        System.out.println(numBST(5));
    }

    static int[] dpTable = new int[20];  // init with max n possible as per problem

    static int numBST(int n) {
        System.out.println(">>> recursion for " + n);
        if (n == 0 || n == 1) return 1;
        if (dpTable[n] != 0) {
            System.out.println("--- dpTable used for "+n);
            return dpTable[n];
        }

        int sum = 0;
        for (int leftSubtreeSize = 0; leftSubtreeSize < n; leftSubtreeSize++) {
            int rightSubtreeSize = n - leftSubtreeSize - 1; // -1 for root as {left subtree} <-- root --> {right subtree}
            sum += numBST(leftSubtreeSize) * numBST(rightSubtreeSize);
        }
        System.out.println("+++ dpTable set for "+n);
        dpTable[n] = sum;
        return dpTable[n];
    }

}
