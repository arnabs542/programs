package com.raj;

import com.raj.dp.WeightedJobSchedule;

public class PROBLEM_PATTERNS {

    /**
     * --- Legend ---
     * # Pattern: BruteForce >> SubOptimal >> Optimal Solution
     *
     * [SORTING]
     * @see com.raj.binarysearch.KClosestElements - K Smallest/Nearest/Largest elements: Sort >> Anti-Heap >> QuickSelect
     * @see com.raj.sorting.KNearestNeighbors - SelectionSort >> Anti-Heap >> QuickSelect
     *
     * [ARRAYS]
     * @see com.raj.adhoc.SubArraySum - 2 loops >> Expand/Contract algo >> Expand/Map algo
     *
     * [STRING]
     * @see com.raj.strings.PalindromePairs - Trie + Reverse lookup / Map + Reverse lookup
     * @see com.raj.strings.SubstringMatch - Naive N^2 >> RabinKarp/RollingHash algo >> SuffixTrie
     * @see com.raj.strings.SuffixTrie - Most Repeated substr / Longest Common substr / Longest Common Palin
     *
     * [TREES]
     * @see com.raj.trees.KClosestNodes - K Closest Nodes in BST: Inorder K Smallest >> Inorder k-sized Heap >> Inorder Successor/Predecessor
     *
     * [RECURSION]
     * @see com.raj.recursion.NQueens - Fundamentals
     * @see com.raj.recursion.combinations.SubsetSum - include/exclude i (using ptrs)
     * @see com.raj.recursion.permutations.EvalAllPossibleExpr - form incremental str & recurse on 2 options: '+' & '*'
     * @see com.raj.recursion.permutations.Permute - use slate w/ swaps >> use ptrs
     *
     * [GREEDY]
     * @see com.raj.dp.greedy.JobSequence
     * @see com.raj.dp.greedy.ActivitySelection
     * @see com.raj.dp.greedy.TaskScheduler
     *
     * [DP]
     * Types of problems:
     * 1> Fibonacci/Factorial/LIS - count ways, rod cut
     * 2> Subset/Knapsack - subset(include/exclude) sum
     * 3> Grid/Matrix - max sum path
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
     * [GRAPH]
     * @see com.raj.graphs.GuardDistance - BFS Shortest Path
     * @see com.raj.graphs.FindIslands - Connected components DFS
     * @see com.raj.graphs.CountBasins - Connected components DFS Tricky
     * @see com.raj.graphs.BipartiteGraph - Divide into 2 partitions / Alternate Color Graph BFS or DFS
     * @see com.raj.graphs.CourseSchedule - Simple Topological Sort DFS
     *
     * # When a problem refers a DAG, first thing to try is Topological Sort, then do what u need to find like - shortest path, schedule etc in second pass.
     * @see com.raj.graphs.LongestPathWeightedDAG - Topo Sort DFS + Longest Path + Track Parent
     */
}
