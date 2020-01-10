package com.raj.binarysearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PopularNumbers {

    /**
     * A popular number is a number that appears more than 25% in an array.
     * Find all popular numbers in a SORTED array.
     *
     * A = [1,1,2,5,5,5,7,7,7,7,7]  => {5,7} as they occur more than 25% of time
     */
    public static void main(String[] args) {
        System.out.println(findPopularNumbers(new int[]{1,1,2,5,5,5,7,7,7,7}, 25));
    }

    /**
     * Brute: Count freq using a map or count sort array. Print ones that have freq more than 25% of arr len.
     * Optimal: Since array is sorted, we need to make use of it by applying binary search.
     * We can pick arr elements at distance of 25% of arr length as candidate from where we launch modified binary search.
     * Modified bSearch -
     * find_left_idx will search until leftmost index is found when it finds the target number.
     * find_right_idx will search until rightmost index is found when it finds the target number.
     * Time = (n/p) * log n, where p = occurrences reqd eg. 25% => n/4
     */
    static List<Integer> findPopularNumbers(int[] A, int pct_reqd) {
        Set<Integer> res = new HashSet<>();
        int p = 100 / pct_reqd;  // 4 for 25%
        int n = A.length;
        for (int i = n/4; i < n; i+=n/4) {  // candidates are n/4, n/2, 3n/4
            int l = find_left_idx(A, 0, n-1, A[i]);   // modified binary to find leftmost idx for candidate
            int r = find_right_idx(A, 0, n-1, A[i]);
            if ((r-l+1) > n/4) res.add(A[i]);
        }
        return new ArrayList<>(res);
    }

    static int find_left_idx(int[] A, int s, int e, int T) {
        if (s > e) return -1;
        int m = s + (e-s)/2;
        if (T == A[m]) {
            int idx = find_left_idx(A, s, m-1, T);
            if (idx != -1) return idx;  // found a more distant left idx
            else return m;  // this was the leftmost
        }
        else if (T < A[m]) return find_left_idx(A, s, m-1, T);
        else return find_left_idx(A, m+1, e, T);
    }

    static int find_right_idx(int[] A, int s, int e, int T) {
        if (s > e) return -1;
        int m = s + (e-s)/2;
        if (T == A[m]) {
            int idx = find_right_idx(A, m+1, e, T);
            if (idx != -1) return idx;  // found a more distant right idx
            else return m;  // this was the rightmost
        }
        else if (T < A[m]) return find_right_idx(A, s, m-1, T);
        else return find_right_idx(A, m+1, e, T);
    }

}
