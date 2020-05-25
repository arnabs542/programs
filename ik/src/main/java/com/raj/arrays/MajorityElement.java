package com.raj.arrays;

public class MajorityElement {
    /**
     * Given an array, find the majority element in linear time.
     * Majority element occurs more than n/2 times.
     * Assume that a majority element always exists.
     *
     *             7 7 5 7 5 1 5 7 5 5 5
     * maj_elem =  7           5   5
     * count    =  1 2 1 2 1 0 1 0 1 2 3
     *
     * # if count=0, curr elem becomes maj_elem
     * O(n)
     */
    public static void main(String[] args) {
        System.out.println(majorityElement(new int[]{7,7,5,7,5,1,5,7,5,5,5}));
    }

    static int majorityElement(int[] A) {
        int maj_elem = -1, count = 0;
        for (int i : A) {
            if (count == 0) {
                maj_elem = i;
                count++;
            }
            else { // count>0
                if (i == maj_elem) count++;
                else count--;
            }
        }
        return maj_elem;
    }
}
