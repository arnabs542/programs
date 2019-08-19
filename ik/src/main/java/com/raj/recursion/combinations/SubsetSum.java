package com.raj.recursion.combinations;

import java.util.Stack;

/**
 * @author rshekh1
 */
public class SubsetSum {

    public static void main(String[] args) {
        System.out.println(subsetSum(new int[] {1,2,3,4,5}, 0, 5, new Stack()));
    }

    static int subsetSum(int[] A, int i, int sumLeft, Stack slate) {
        // sum is already met
        if (sumLeft == 0) {
            System.out.println(slate);
            return 1;
        }

        // our sum is more than target sum
        if (sumLeft < 0) return 0;

        // exhausted search
        if (i == A.length) return 0;

        // we can also add another optimization where we keep track of sum of all number in array[i,n] and check if the max available sum is less than sumLeft, then it's not possible to go further

        // exclude
        int excludeCount = subsetSum(A, i+1, sumLeft, slate);

        // include
        slate.push(A[i]);
        int includeCount = subsetSum(A, i+1, sumLeft - A[i], slate);
        slate.pop();

        return excludeCount + includeCount;
    }

}
