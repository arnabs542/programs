package com.raj;

import com.raj.dp.WeightedJobSchedule;

public class Problem_PATTERNS {

    /**
     * --- Legend ---
     * # Pattern: BruteForce >> SubOptimal >> Optimal Solution
     *
     * [Sorting]
     * @see com.raj.binarysearch.KClosestElements - K Smallest/Nearest/Largest elements: Sort >> Anti-Heap >> QuickSelect
     * @see com.raj.sorting.KNearestNeighbors
     *
     * [BST]
     * @see com.raj.trees.KClosestNodes - K Closest Nodes in BST: Inorder K Smallest >> Inorder k-sized Heap >> Inorder Successor/Predecessor
     *
     * [DP]
     * Types of problems:
     * 1> Fibonacci/Factorial/LIS - count ways, rod cut
     * 2> Subset / Knapsack - subset sum
     * 3> Grid / Matrix - max sum path
     * 4> Strings
     * @see com.raj.dp.CountWays - Simple counting
     * @see com.raj.dp.MinCoinChange - Counting + Minimizing change
     * @see com.raj.dp.RodCutMaxProfit - Rod cut
     * @see com.raj.dp.LongestSubsequence - 2 loops / Update counts trying each elemnt from 0...i in inner loop
     *
     * @see com.raj.dp.SubsetSumDP - Subset Sum DP reduces recursive exponential time to polynomial time
     * @see com.raj.dp.LongestCommonSubsequence - Subset pattern strings
     * @see com.raj.dp.MinEggDrops - Subset Pattern Hard - Don't know where to start, try all possibilities
     *
     * # Some problems may require pre-processing the input set like sorting etc to formulate a correct recursion > DP formula.
     * @see WeightedJobSchedule - Subset Pattern Hard - Preprocess input - Sort End times / Reverse iterate / Modified Binary Search
     *
     * @see com.raj.dp.LevenshteinDistance - String Edit Distance
     * @see com.raj.dp.MaxSumPath - Grid / Matrix path traversal + Maximizing sum
     *
     */
}
