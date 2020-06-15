package com.raj.binarysearch;

public class FindPeakElement {
    /**
     * A peak element is an element that is greater than its neighbors.
     * Given an input array nums, where nums[i] ≠ nums[i+1], find a peak element and return its index.
     * The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.
     * Input: nums = [1,2,1,3,5,6,4]
     * Output: 1 or 5
     */
    public static void main(String[] args) {
        int[] A = new int[]{1,2,1,3,5,6,4};
        System.out.println(findPeak(A, 0, A.length));
    }

    static int findPeak(int[] A, int l, int r) {
        if (l == r) return A[l];
        int mid = l + (r-l)/2;
        if (A[mid] > A[mid+1]) return findPeak(A, l, mid);
        else return findPeak(A, mid+1, r);
    }

}
