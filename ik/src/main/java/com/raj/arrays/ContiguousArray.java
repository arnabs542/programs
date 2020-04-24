package com.raj.arrays;

import java.util.Arrays;
import java.util.Stack;

public class ContiguousArray {
    /**
     * You are given an array a of N integers. For each index i, you are required to determine the number of contiguous
     * subarrays that fulfills the following conditions:
     * The value at index i must be the maximum element in the contiguous subarrays, and
     * These contiguous subarrays must either start from or end with i.
     * a = [3, 4, 1, 6, 2]
     * output = [1, 3, 1, 5, 1]
     * For index 0 - [3] is the only contiguous subarray that starts (or ends) with 3, and the maximum value in this subarray is 3.
     * For index 1 - [4], [3, 4], [4, 1]
     * For index 2 -[1]
     * For index 3 - [6], [6, 2], [1, 6], [4, 1, 6], [3, 4, 1, 6]
     * For index 4 - [2]
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(countSubarrays(new int[]{3, 4, 1, 6, 2})));
        System.out.println(Arrays.toString(getCount(new int[]{3, 4, 1, 6, 2})));
    }

    // O(n^2)
    static int[] countSubarrays(int[] arr) {
        // Write your code here
        // fix i, expand around it count where it is greater than prev or after
        int[] res = new int[arr.length];
        for (int i=0; i<arr.length; i++) {
            int cnt = 1;  // for itself

            // go left
            int k = i;
            while (--k>=0 && arr[i] > arr[k]) cnt++;
            // go right
            k = i;
            while (++k<arr.length && arr[i] > arr[k]) cnt++;
            res[i] = cnt;
        }
        return res;
    }

    /**
     * ?????
     * # O(N)
     * # this solution uses Stacks. Every index starts with n possibilities.
     * # Using stack, going from left to right, we remove the sub-arrays that
     * # doesn't satisfy the problem condition at this line:
     * # 'result[st.pop()] -= n-i'
     * # Then we do it again from right to left.
     */
    static int[] getCount(int[] nums) {
        int l = nums.length;
        Stack<Integer> stackLR = new Stack<>();
        Stack<Integer> stackRL = new Stack<>();
        int[] res = new int[l];
        int[] cntLR = new int[l];
        Arrays.fill(cntLR, -1);
        int[] cntRL = new int[l];
        Arrays.fill(cntRL, -1);
        for(int i=0;i<l;i++) {
            while(!stackLR.isEmpty() && nums[stackLR.peek()] < nums[i]) {
                cntLR[stackLR.pop()] = i;
            }
            stackLR.push(i);
        }
        for(int i=l-1;i>=0;i--) {
            while(!stackRL.isEmpty() && nums[stackRL.peek()] < nums[i]) {
                cntRL[stackRL.pop()] = i;
            }
            stackRL.push(i);
        }
        for(int i=0;i<l;i++) {
            res[i] = (cntLR[i] == -1 ? l - i : cntLR[i] - i) + (cntRL[i] == -1 ? i + 1 : i - cntRL[i]) - 1;
        }
        return res;
    }

}
