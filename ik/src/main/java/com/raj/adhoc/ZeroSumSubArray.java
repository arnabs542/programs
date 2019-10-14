package com.raj.adhoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rshekh1
 */
public class ZeroSumSubArray {

    /**
     * Given an array of integers arr of size n, find a non-empty subarray resSubArray such that sum of elements in resSubArray is zero.
     * For input n = 6 and arr = [5, 1, 2, -3, 7, -4], output will be {1,3}
     * For input n = 5 and arr = [1, 2, 3, 5, -9], output will be {-1}
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(sumZero_brute(new int[]{5,1,2,-3,7,-4})));
        System.out.println(Arrays.toString(sumZero(new int[]{5,1,2,-3,7,-4})));
        System.out.println(Arrays.toString(sumZero(new int[]{2,-1,-1,5})));
    }

    /**
     * O(n^2) brute force, O(1) aux space
     * Iter with start i until n-1, check if sumSoFar becomes zero for j, then i,j is the answer
     */
    static int[] sumZero_brute(int[] A) {
        for (int i = 0; i < A.length; i++) {
            int sumSoFar = 0;
            for (int j = i; j <A.length; j++) {
                sumSoFar += A[j];
                if (sumSoFar == 0) return new int[]{i,j};
            }
        }
        return new int[]{-1};
    }

    /**
     * Time + Space = O(n)
     * Idea is to store sumSoFar for each index as we traverse
     *   # sumSoFar will increase, and then decrease for negative value.
     *   # At this point, if the sum again becomes something that we have seen before, it means all elems after that added upto zero
     *   # (0...i-1), (i...j), j+1...n
     *      sum(i-1)     sum(j)  => sum(j) - sum(i-1) = sum(i...j)  => general formula for sum until 'x' idx
     *   eg.   5,    1, 2, -3,  7, -4
     *  sum =  5,    6, 8,  5,  12,  8
     *         |            |
     *  => same sum means everything in b/w added upto 0 (had no effect to overall sum)
     *  => hence, answer is indices after 5 until this ith element
     *  => imagine this on a graph, it'll become very easy to grasp
     *  => edge cases ?
     *     - what if element itself is zero?
     *     - what if the sum became zero, but there wasn't 0 before?
     *       eg. 2, -1, -1, 5
     *           2   1   0  5
     *                   x --> need to store a sentinel 0,-1 before start in map so that answer can be {0,2}
     */
    static int[] sumZero(int[] A) {
        if (A == null || A.length == 0) return new int[] {-1};

        Map<Long,Integer> map = new HashMap<>();
        map.put(0L, -1);    // edge case when we add upto zero with non-zero elements
        Long sumSoFar = 0L;
        for (int i = 0; i < A.length; i++) {
            // edge case when A[i] itself is 0
            if (A[i] == 0) return new int[] {i,i};

            sumSoFar += A[i];
            // if we have seen this sum before, everything after it added upto zero
            if (map.containsKey(sumSoFar)) {
                return new int[] {map.get(sumSoFar) + 1, i};
            }
            map.put(sumSoFar, i);   // keep storing sumSoFar by index
        }
        return new int[] {-1}; // didn't find answer
    }

}
