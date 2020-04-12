package com.raj.sorting;

import java.util.*;

public class KPairsLargestSumArray {
    /**
     * https://www.geeksforgeeks.org/k-maximum-sum-combinations-two-arrays/
     * Find K pairs which have the largest sum using elements from two arrays. The combination sum should be unique.
     *
     */
    public static void main(String[] args) {
        Assert(getMaxSumCombinations(new Integer[]{4, 2, 5, 1}, new Integer[]{8, 0, 3, 5}, 3),
                Arrays.asList(13, 12, 10));

        Assert(getMaxSumCombinations(new Integer[]{4}, new Integer[]{8}, 3),
                Arrays.asList());

        Assert(getMaxSumCombinations(new Integer[]{3,3}, new Integer[]{3,3}, 3),
                Arrays.asList(6));

        Assert(getMaxSumCombinations(new Integer[]{4, 2}, new Integer[]{4, 2}, 3),
                Arrays.asList(8, 6, 4));

        Assert(getMaxSumCombinations(new Integer[]{-4, 2}, new Integer[]{8, -1}, 3),
                Arrays.asList(10, 4, 1));
    }

    // assert tests
    static void Assert(List<Integer> A, List<Integer> B) {
        String res = " FAIL!!";
        if (A.equals(B)) res = " OK";
        System.out.println(A + res);
    }

    /**
     * Input :  A[] : {4, 2, 5, 1}
     *          B[] : {8, 0, 3, 5}
     *          K : 3
     * Output : 13 (5+8)
     *          12 (4+8)
     *          10 (2+8)
     *
     * Algo:
     * 1. Brute - 2 loops, push sum combination to k sized max heap, use set for removing dupes, O(n^2)
     * 2. Sort + Merge Pattern using Max Heap
     * # Sort both arrays
     * # Use a max heap to track max sum. Insert (sum,0,0) pair into heap.
     * # Pop & insert the next elements - (i+1,j) & (i,j+1)
     * # Use a set to drop dupes.
     *
     * Time = O(nlogn)
     * Dry run:
     * A = {5,4,2,1}
     * B = {8,5,3,0}
     * pq = remove 13,00 & add 12,10  10,01
     * set = 13 12 ...
     */
    private static List<Integer> getMaxSumCombinations(Integer[] A, Integer[] B, int K) {
        if (K > A.length*B.length) return new ArrayList<>();

        // sort in desc order
        Arrays.sort(A, (x,y) -> y - x);
        Arrays.sort(B, (x,y) -> y - x);

        // use a desc sorted set for tracking as well as results
        Set<Integer> set = new TreeSet<>((x,y) -> y-x);
        // max heap w/ sum as priority
        PriorityQueue<Pair> pq = new PriorityQueue<>((x,y) -> y.sum - x.sum);
        pq.add(new Pair(0, 0, A[0]+B[0]));

        while (!pq.isEmpty()) {
            Pair pr = pq.remove();
            if (!set.contains(pr.sum) && K>0) {     // add first K max elements
                set.add(pr.sum);
                K--;
            }
            // add adjacent numbers
            if (pr.i+1 < A.length) pq.add(new Pair(pr.i+1, pr.j, A[pr.i+1] + B[pr.j]));
            if (pr.j+1 < B.length) pq.add(new Pair(pr.i, pr.j+1, A[pr.i] + B[pr.j+1]));
        }
        return new ArrayList<>(set);
    }

    private static class Pair {
        int i,j;
        int sum;
        Pair(int i, int j, int sum) {
            this.i = i;
            this.j = j;
            this.sum = sum;
        }
        public String toString() {
            return sum + " " + i + "," + j;
        }
    }
}
