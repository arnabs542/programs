package com.raj.adhoc;

import java.util.Arrays;
import java.util.List;

/**
 * @author rshekh1
 */
public class MinElemInSortedRotatedArr {

    /**
     * You are given a sorted array arr which is rotated by unknown pivot k. You need to find minimum element from
     * given array using fastest possible way which uses only constant space.
     *
     * arr = [4, 5, 6, 7, 8, 1, 2, 3] => output = 1
     */
    public static void main(String[] args) {
        System.out.println(find_minimum(Arrays.asList(4, 5, 6, 7, 8, 1, 2, 3)));
        System.out.println(find_minimum(Arrays.asList(18,0,-1,-11,-13,45,35,23)));
        System.out.println(find_minimum(Arrays.asList(6,2,3,4,5)));
    }

    /**
     * Best runtime is with binary search
     * # find s,m,e of array
     * # Array already sorted?
     *   - Find if the arr is increasing or decreasing order
     *   - s < m < e => increasing, min is first
     *   - s > m > e => decreasing, min is last
     * # Array sorted & rotated?
     *   - Find if the arr was increasing or decreasing order
     *   - s > e => was increasing before
     *   - s < m => go right as min lies in right
     *   - & vice versa for decreasing seq
     *   - min is start if arr already sorted (s < e)
     */
    public static int find_minimum(List<Integer> A) {
        if (A == null || A.size() < 1) return -1;
        if (A.size() == 1) return A.get(0);
        if (A.size() == 2) return Math.min(A.get(0), A.get(1));

        int s = 0;
        int e = A.size() - 1;
        int m = (s+e) / 2;

        // Is the arr already sorted?
        // 1,2,3,4,5
        if (A.get(s) < A.get(m) && A.get(m) < A.get(e)) return A.get(s); // increasing seq, min is first
        // 5,4,3,2,1
        if (A.get(s) > A.get(m) && A.get(m) > A.get(e)) return A.get(e); // decreasing seq, min is last

        // If not above, then is it Rotated? Increasing or Decreasing? What are the cases?
        // 4,5,1,2,3
        if (A.get(s) > A.get(e)) return bSearchMinIncreasingSeq(A, s, e);
        // 3,2,1,5,4
        else return bSearchMinDecreasingSeq(A, s, e);
    }

    // Recursive bSearch uses log n extra stack space, use iterative for optimization
    static int bSearchMinIncreasingSeq(List<Integer> A, int s, int e) {
        while (s <= e) {
            if (A.get(s) <= A.get(e)) return A.get(s); // already sorted in increasing order, return s
            int m = (s+e)/2;
            if (A.get(m) >= A.get(s)) s = m + 1;  // go right
            else e = m;
        }
        return -1;
    }

    static int bSearchMinDecreasingSeq(List<Integer> A, int s, int e) {
        while (s <= e) {
            if (A.get(s) >= A.get(e)) return A.get(e); // already sorted in decreasing order, return e
            int m = (s+e)/2;
            if (A.get(m) < A.get(s)) s = m;  // go right
            else e = m;
        }
        return -1;
    }

}
