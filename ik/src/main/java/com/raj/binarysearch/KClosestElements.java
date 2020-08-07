package com.raj.binarysearch;

import java.util.ArrayList;
import java.util.List;

public class KClosestElements {

    /**
     * Find K closest elements to a given target value. Target may or may not be present in A[].
     * Given a SORTED array A[] and a value T, find the K closest elements to T in arr[].
     */
    public static void main(String[] args) {
        double A[] = {12, 16, 22, 30, 32, 39, 42, 45, 48, 50, 53, 55, 56};
        int T = 35, K = 5;
        System.out.println(findKClosest(A, T, K));
    }

    /**
     * Brute: Iterate and find the closest element first & then go left/right until k is exhausted.
     * Or use a K sized max heap with diff as priority & insert into heap if A[i] < top. Finally we have K min diff elems.
     * But we need to make use of sorted array.
     * Optimal: Since A[] is sorted, use a modified binarySearch for the closest element & then go left/right.
     */
    public static List<Double> findKClosest(double A[], double T, int K) {
        int closestIdx = findClosestBSearch(A, T, 0, A.length-1);
        return getClosestK(A, T, closestIdx, K);
    }

    /**
     * Modified bSearch will entail modifying base cases mostly
     * # What happens when T lies beyond array range?
     * # When we assert for mid element being answer, how do we say it can be the closest?
     */
    static int findClosestBSearch(double[] A, double T, int s, int e) {
        // Base cases: What happens when T lies beyond array range?
        if (T <= A[s]) return s;
        if (T >= A[e]) return e;

        // Is mid our answer?
        int m = s + (e-s)/2;
        if (A[m] == T) return m;   // T is equal to mid
        if (A[m] < T && T < A[m+1]) {  // if  T lies b/w mid & mid+1 i.e. mid ... T .. mid+1
            // we have 2 contenders - mid elem or mid+1 elem. Return the one closest
            return (T-A[m] < A[m+1]-T) ? m : m+1;
        }

        // Else discard one part & recurse on other
        if (T < A[m]) return findClosestBSearch(A, T, s, m-1); // left half
        else return findClosestBSearch(A, T, m+1, e); // right half
    }

    // Find K elements closest to pivot
    static List<Double> getClosestK(double[] A, double T, int idx, int K) {
        List<Double> res = new ArrayList<>();
        res.add(A[idx]); K--; // add the closest element first
        int left = idx-1, right = idx+1;
        while(K > 0 && idx-1 >= 0 && idx+1 < A.length) {
            if (Math.abs(T-A[left]) <= Math.abs(T-A[right])) res.add(A[left--]);
            else res.add(A[right++]);
            K--;
        }
        return res;
    }

}
