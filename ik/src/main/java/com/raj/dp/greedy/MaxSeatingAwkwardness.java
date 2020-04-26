package com.raj.dp.greedy;

import com.raj.Util;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MaxSeatingAwkwardness {
    /**
     * There are n guests attending a dinner party, numbered from 1 to n. The ith guest has a height of arr[i] inches.
     * The awkwardness between a pair of guests sitting in adjacent seats is defined as the absolute difference between their two heights.
     * Note that, because the table is circular, seats 1 and n are considered to be adjacent to one another.
     * The overall awkwardness of the seating arrangement is then defined as the maximum awkwardness of any pair of
     * adjacent guests. Determine the minimum possible overall awkwardness of any seating arrangement.
     *
     * n = 4
     * arr = [5, 10, 6, 8]
     * output = 4
     * If the guests sit down in the permutation having heights [6, 5, 8, 10], in that order, then the awkwardness
     * between pairs of adjacent guests will be |6-5| = 1, |5-8| = 3, |8-10| = 2, and |10-6| = 4,
     * yielding an overall awkwardness of 4. It's impossible to achieve a smaller overall awkwardness.
     */
    public static void main(String[] args) {
        System.out.println("brute = " + minOverallAwkwardness_brute(new int[]{5,10,6,8}, 0));
        System.out.println("brute = " + minOverallAwkwardness_brute(new int[]{1,5,9,15,16}, 0));
        System.out.println("brute = " + minOverallAwkwardness_brute(new int[]{1,2,5,3,7}, 0));

        System.out.println(minOverallAwkwardness_optimal(new int[]{5,10,6,8}));
        System.out.println(minOverallAwkwardness_optimal(new int[]{1,5,9,15,16}));
        System.out.println(minOverallAwkwardness_optimal(new int[]{1,2,5,3,7}));
        System.out.println(minOverallAwkwardness_optimal(new int[]{1,2,5}));
    }

    /**
     *  0  1   2  3   = idx
     * [5, 10, 6, 8]  = arr
     *  3  5   4  2   = awkwardness
     *
     *  5 10 8 6
     *  rec try perm w/ 5 then 10, then 8 ...
     *
     * Brute:
     * # Permute & try every combination, check its awkwardness
     * # O(n for checking awkwardness x n! for permutations)
     */
    static int minOverallAwkwardness_brute(int[] A, int i) {
        if (i == A.length) {   // permutation found
            int max_awk = Math.abs(A[0] - A[A.length-1]);  // circular hence start w/ 0 & last awk
            for (int j=1;j<A.length;j++) {
                max_awk = Math.max(max_awk, Math.abs(A[j-1] - A[j]));
            }
            return max_awk;
        }

        int min_awk = Integer.MAX_VALUE;
        for (int j = i; j < A.length; j++) {
            Util.swap(A, i, j);
            int awk = minOverallAwkwardness_brute(A, j+1);  // recursively find awk with this permutation
            min_awk = Math.min(min_awk, awk);
            Util.swap(A, i, j);
        }

        return min_awk;
    }

    /**
     * Optimal: [GREEDY Substitution, expand around candidates >> Sort & Slot]
     * # Idea is to greedily find the max awk pairs & try to substitute one of the place from adj numbers so as to bring it down
     * # find i,j pair which has max awk
     *     check adj i+1,j & i-1,j & i,j+1 & i,j-1 awk & substitute only if it minimizes overall awk
     *     we don't need to check for overall awk as we just need to find the awks of elem which are getting swapped w/ their adj
     *
     *               max awk overall
     * 1 5 9 15 16     1-16 = 15       try 5-16 (after that 1-9 is less than max awk still), 1-15 (9-16<)
     * 5 1 9 15 16     5-16 = 11       try 5-15 (1-16 was already tried & is higher)
     * 5 1 9 16 15     5-15 = 10       all options done break out
     *
     * Ex2:
     * 1 2 5 3 7      1-7 = 6          try 2-7, 1-3 & eval how awk is affected for neighbors after swap
     * 1 2 5 7 3      7-3 = 4          no other option to minimize
     *
     * But what if array is huge & optimizations are localized  ........(action here)..................... how are others affected?
     * We'll end up doing O(n^2) operations in worst case.
     *
     * # If the table wasn't circular, the best way to minimize the awkwardness is to sort them.
     * # As it's circular, can we still use sorted sequence to slot them such that 0........n  A[0]-A[n] is also minimum?
     * # Idea is to slot them in Golden Gate Bridge pattern | . |
     *      high mid low mid high   (Decrease Seq -> reach global min -> Increase Seq)
     *
     * Algo:
     * # Sort or put them into max heap
     * # Greedily Pick the next highest put it at 0, next highest at n, next highest at 1, next highest at n-1
     *
     * Time = O(nlogn), Space = O(n) for arranging in desired sequence
     */
    static int minOverallAwkwardness_optimal(int[] A) {
        // trivial boundary cases
        if (A == null || A.length == 0) return 0;
        if (A.length == 1) return A[0];
        if (A.length == 2) return Math.abs(A[0] - A[1]);

        // use max heap to get highest elems
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b-a);
        for (int i : A) maxHeap.add(i);

        // place high low high order using the original array
        int i = 0, j = A.length-1;
        A[i++] = maxHeap.remove();   // init to compute max_awk effectively
        A[j--] = maxHeap.remove();
        int max_awk = Math.abs(A[0] - A[A.length-1]);

        while (i<=j) {
            if (i+1 < A.length && !maxHeap.isEmpty()) {
                A[i++] = maxHeap.remove();
                max_awk = Math.max(max_awk, Math.abs(A[i-1] - A[i-2]));
            }
            if (j-1 >= 0 && !maxHeap.isEmpty()) {
                A[j--] = maxHeap.remove();
                max_awk = Math.max(max_awk, Math.abs(A[j+1] - A[j+2]));
            }
        }
        System.out.print(Arrays.toString(A) + " => ");  // print arrangement
        return max_awk;
    }

}
