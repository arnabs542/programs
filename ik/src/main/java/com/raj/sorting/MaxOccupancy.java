package com.raj.sorting;

public class MaxOccupancy {
    /**
     * Given a list of incoming & outgoing times of cars in a parking lot, find the max occupancy for a day.
     */
    public static void main(String[] args) {
        System.out.println(findMaxOccupancy(new int[]{1,3,5,6,8}, new int[]{2,5,7,9,10}));
        System.out.println(findMaxOccupancy(new int[]{1,3,5,6,8}, new int[]{2,5,9,10,12}));

        System.out.println(findMaxOccupancy_linear(new int[]{1,3,5,6,8}, new int[]{2,5,7,9,10}));
        System.out.println(findMaxOccupancy_linear(new int[]{1,3,5,6,8}, new int[]{2,5,9,10,12}));
    }

    /**
     * Sort lists, 2-Ptr approach
     * O(nlogn), no aux space apart from sorting space overhead for eg. QuickSort has logn space overhead.
     */
    static int findMaxOccupancy(int[] in, int[] out) {
        if (in == null || out == null || in.length != out.length) return 0;
        int max=0, cnt=0;
        int i=0, j=0;
        while(i < in.length && j < out.length) {
            if (in[i] < out[j]) {   // incoming hence, incr count
                cnt++;
                i++;
            } else if (in[i] == out[j]) {   // incr & decr - just move ptrs
                i++;
                j++;
            } else {        // outgoing hence decr
                cnt--;
                j++;
            }
            max = Math.max(max,cnt);
        }
        return max;
    }

    /**
     * To achieve linear soln, we need to define the time slot array beforehand. In a day even if we take secs, we have 100K slots.
     * Idea is to iterate incoming car arr & +1 for each incoming car directly at time arr index
     * For each outgoing car we then decrement.
     * Now we iter time arr and update maxOcc ptr.
     *
     * O(n) & constant aux space
     */
    static int findMaxOccupancy_linear(int[] in, int[] out) {
        if (in == null || out == null || in.length != out.length) return 0;
        short[] timeArr = new short[24*60*60];  // ~100K secs max

        for (int i : in) timeArr[i]++;
        for (int o : out) timeArr[o]--;

        int runningSum = 0, max = 0;
        for (short cnt : timeArr) {
            runningSum += cnt;
            max = Math.max(max, runningSum);
        }
        return max;
    }

}
