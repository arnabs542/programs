package com.raj.slidingwindow;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SubArraySum {

    /**
     * Given an unsorted array A of size N of non-negative integers, find a continuous sub-array which adds to a
     * given number S.
     *
     * A = 1 2 3 7 5 , Sum = 12
     *       1...3   => arr idx 1 to 3 adds upto 12
     */
    public static void main(String[] args) {
        System.out.println(brute(new int[]{1,2,3,7,5}, 12));

        System.out.println(expand_contract(new int[]{1,2,3,7,5}, 12));
        System.out.println(expand_contract(new int[]{1,2,3,-7,5}, 1)); // WRONG, doesn't wrk with -ve nos

        System.out.println(expand_map(new int[]{1,2,3,7,5}, 12));
        System.out.println(expand_map(new int[]{1,2,3,-7,5}, 1));   // works with -ve nos
        System.out.println(expand_map(new int[]{1,2,3,-7,5}, -2));
    }

    /**
     * Brute Force:
     * Fix i in outer loop
     *   inner loop i...j, keep adding until it equals T, print
     * Runtime = O(n^2)
     */
    static Pair brute(int[] A, int T) {
        for (int i = 0; i < A.length; i++) {
            int sum = 0;
            for (int j = i; j < A.length; j++) {
                sum += A[j];
                if (sum == T) return new Pair<>(i, j);
            }
        }
        return null;
    }

    /**
     * Expand/Contract algo:
     *
     * 1 2 3 7 5
     * i ... j   => 13 > T, end expansion
     *   i...j   => 12 = T, end contraction
     *
     * # Expand until sum < T
     *   - if sum == T, print result
     *   - contract until sum > T
     *
     * Note : This only works with +ve numbers as the condition is expand until sum < T (if T is -ve, no expansion occurs)
     * Runtime = O(2*n) as max we expand once & contract once on all elements (eg, last elem is the sum)
     */
    static Pair expand_contract(int[] A, int T) {
        if (T == 0) return null;

        int sum = 0, j = -1;
        for (int i = 0; i < A.length; i++) {
            while (sum < T) {   // expand i...j until sum < T
                sum += A[++j];
                if (sum == T) return new Pair(i,j);
            }
            // now contract as sum has exceeded T
            sum -= A[i];
            if (sum == T) return new Pair(i+1,j);
        }
        return null;
    }

    /**
     * The reason above solution doesn't work for negative numbers is because we are looking for sum less than T
     * condition which can go awry with -ve nos, eg. 1 2 3 -7 5
     * So, we use a map to store the sum and the corresponding index as we expand.
     * The moment sum goes over T, we check if the diff sum-T was already seen before in map.
     * If yes, we print that index+1 & the current index.
     *
     *       i (sum=13)
     * 1 2 3 7 5
     *       sum-T = 1 is there in map, hence (map.get(1)+1,i) is the answer
     * map =>
     * 1 -> 0
     * 3 -> 1
     * 6 -> 2
     *
     * Now try it for -ve values: 1 2 3 -7 5
     *
     * Runtime/Space = O(n)
     */
    static Pair expand_map(int[] A, int T) {
        if (T == 0) return null;
        Map<Integer,Integer> map = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            int diff = sum - T;
            if (map.containsKey(diff)) {
                return new Pair(map.get(diff)+1, i);  // for min window length, use min ptr to minimize
            }
            map.put(sum, i);
        }
        return null;
    }

    /**
     * Follow up:
     * O(nlogn) soln without using extra space? https://leetcode.com/problems/minimum-size-subarray-sum/discuss/59103/Two-AC-solutions-in-Java-with-time-complexity-of-N-and-NLogN-with-explanation
     * If arr has +ve elems only, the cumulative sum is strictly increasing. Update the original arr with cumulative sum.
     * We can exploit the above sorted cumulative sum property to do binarySearch for each curSum + T in arr.
     */

}
