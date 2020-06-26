package com.raj.graphs;

import java.util.stream.IntStream;

public class PartitionArrayKSubset {
    /**
     * Partition to K Equal Sum Subsets
     * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.
     * Example 1:
     * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * Output: True
     * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
     */
    public static void main(String[] args) {

    }

    /**
     * Simple DFS + Visited + Backtrack
     * Assume sum is the sum of nums[] . The dfs process is to find a subset of nums[] which sum equals to sum/k.
     * We use an array visited[] to record which element in nums[] is used. Each time when we get a cur_sum = sum/k,
     * we will start from position 0 in nums[] to look up the elements that are not used yet and find another cur_sum = sum/k.
     * A corner case is when sum = 0, my method is to use cur_num to record the number of elements in the current subset.
     * Only if cur_sum = sum/k && cur_num >0, we can start another look up process.
     */
    public boolean canPartitionKSubsets(int[] a, int k) {
        int sum = IntStream.of(a).sum();
        return k != 0 && sum % k == 0 && canPartition(0, a, new boolean[a.length], k, 0, sum / k);
    }

    boolean canPartition(int start, int[] a, boolean[] visited, int k, int sum, int target) {
        if (k == 1) return true; // found all k subsets
        if (sum == target) return canPartition(0, a, visited, k - 1, 0, target);  // a subset found
        for (int i = start; i < a.length; i++)
            if (!visited[i]) {
                visited[i] = true;  // mark visited as we'll use it towards sum
                if (canPartition(i + 1, a, visited, k, sum + a[i], target)) return true;
                visited[i] = false; // backtrack as above didn't wrk, revert visited
            }
        return false;
    }

}
