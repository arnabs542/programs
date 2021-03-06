package com.raj;

import com.raj.adhoc.stack.StockSpan_MostWaterContainer;
import com.raj.dp.WeightedJobSchedule;
import com.raj.graphs.topo.CourseSchedule;
import com.raj.slidingwindow.MinimumWindowSubstr;
import com.raj.slidingwindow.SubArraySum;
import com.raj.sorting.intervals.IntervalBuckets;

public class PROBLEM_PATTERNS {

    /**
     * Patterns by problem keywords:
     * # Frequency - HashMap
     * # Lookups - HashMap
     * # Sorted Array - Binary Search
     * # K Sorted Arrays - K-way Merge using Heap
     * # Top 'K' - Anti-Heap
     * # 'K' sized max subarray/Longest substring with 'K' - Sliding Window
     * # Combinations or Permutations of a given set - Subset Pattern
     * # Prefix Searches/Dictionary - Trie
     * # Substring Searches - KMP/RabinKarp/SuffixTrie
     * # DAG/Acyclic Graph Ordering - Topological sort
     *
     * [Good Links]
     * 14 Patterns -
     * https://hackernoon.com/14-patterns-to-ace-any-coding-interview-question-c5bb3357f6ed
     * Leetcode Problems by Patterns -
     * https://leetcode.com/discuss/career/448285/list-of-questions-sorted-by-common-patterns
     * https://seanprashad.com/leetcode-patterns/
     *
     * --- Legend ---
     * # Pattern: BruteForce >> SubOptimal >> Optimal Solution
     *
     * [SORTING]
     * @see com.raj.sorting.BalancedPartition - Equal Sum Partition / Sort / Left & Right Ptrs expand on some conditions
     * @see com.raj.binarysearch.KClosestElements - K Smallest/Nearest/Largest elements: Sort >> Anti-Heap >> Modified BSearch
     * @see com.raj.sorting.KNearestNeighbors - SelectionSort >> Anti-Heap >> QuickSelect(Modified BSearch)
     * @see com.raj.sorting.Sum3 - A+B+C=T >> HashMap >> Sort + 2-Pointer technique
     * @see com.raj.arrays.FindRepetitiveAndMissingElement - Cyclic sort / Sorted array w/ numbers in a given range
     *
     * [INTERVALS]
     * @see IntervalBuckets - Sort by start / Pre-compute sorted buckets map / 1st pass - mark +,- bucket at start,end+1 / 2nd pass - accumulate final buckets / Modified BSearch
     * @see com.raj.sorting.intervals.NonOverlapIntervals - Maximize selected tasks / Greedy Activity selection / Sort by end times
     *
     * [ARRAYS]
     * @see com.raj.adhoc.TrapRainWater - bars have width, precompute max left & right bounds, & add up water over each bar
     *
     * [SLIDING WINDOW]
     * @see SubArraySum - 2 loops >> Expand/Contract algo >> Expand/Map algo >> Map Prev seen sum & look for curSum-T in map
     * @see MinimumWindowSubstr - Expand/Contract Sliding Window pattern
     * @see com.raj.linkedlist.SlidingWindowMax - Use Deque(remove elems smaller on left) to track window's max in constant time
     *
     * [STACK]
     * Use Stack to keep elements of interest only / Pop backwards when cond met
     * @see com.raj.adhoc.stack.NextGreaterElement
     * @see StockSpan_MostWaterContainer - differs from TrapRainWater as the bar have 0 width, so area can be computed straightaway
     * @see com.raj.adhoc.stack.AreaHistogram
     *
     * [STRING]
     * @see com.raj.dp.WordBreakCount - Rec >> DP Memo >> Use Trie for Dict words always reduces runtime
     * @see com.raj.strings.PalindromePairs - Trie + Reverse lookup / Map + Reverse lookup
     * @see com.raj.strings.SubstringMatch - Naive N^2 >> RabinKarp/RollingHash algo >> SuffixTrie
     * @see com.raj.strings.SuffixTrie - Most Repeated substr / Longest Common substr / Longest Common Palin
     * @see com.raj.strings.RegExPatternMatch - Recur include or skip a char for special char >> DP
     * @see com.raj.strings.LongestPalinSubstr - Expand around center
     *
     * [TREES]
     * # Traversals:
     * DFS:
     * 1. Inorder - sorted order
     * 2. Preorder - process root first & then go down
     * 3. Postorder - process leaf first & then build upwards
     * BFS:
     * 4. Levelorder / BFS - radiate outwards 1 level at a time. left to right order on each level.
     * # Successor/Predecessor
     * # Subset pattern - dfs, at each node, include & exclude this value & recurse, build soln bottom up
     * @see com.raj.tree.binarytree.BST - Basics - Mirror tree / Path Sum
     * @see com.raj.trees.LeftSideView - Level order traversal
     * @see com.raj.trees.ZigZagTraverse - Level order / Track level change / use descendingIterator to print in reverse
     *
     * # Building trees from traversals
     * @see com.raj.trees.BuildBTreeFromTraversals - Constructing tree from traversals
     * @see com.raj.trees.SerializeDeserializeBTree
     * @see com.raj.trees.SubtreeOfAnother
     *
     * @see com.raj.trees.ClosestNode - Minimize diff as we traverse
     * @see com.raj.trees.KClosestNodes - K Closest Nodes in BST: Inorder K Smallest >> Inorder k-sized Heap >> Inorder Successor/Predecessor
     * @see com.raj.trees.DiameterTree - Maximize paths that originate & end in leaf
     * @see com.raj.trees.MaxSumNonAdjNodes - Similar pattern as diameter max paths above + DP Memo + n-ary trees dfs traverse
     *
     * [RECURSION]
     * @see com.raj.recursion.NQueens - Fundamentals
     * @see com.raj.recursion.combinations.SubsetSum - include/exclude i (using ptrs)
     * @see com.raj.recursion.combinations.SubsetChangeCase - similar to above
     * @see com.raj.recursion.permutations.EvalAllPossibleExpr - form incremental str & recurse on 2 options: '+' & '*'
     * @see com.raj.recursion.permutations.Permute - use slate w/ swaps >> use ptrs
     * @see com.raj.recursion.permutations.DecodeMessages - Permute pattern
     *
     * [GREEDY]
     * @see com.raj.dp.greedy.JobSequence
     * @see com.raj.dp.greedy.ActivitySelection
     * @see com.raj.dp.greedy.TaskSchedulerCoolDown - Greedily schedule most freq task for cooldown / Max Heap / Cooldown Tasks / Idle condition
     * @see com.raj.dp.greedy.MaxSeatingAwkwardness - Greedily pick max and slot it in a GG pattern
     *
     * [DP]
     * Types of problems:
     * 1> Fibonacci/Factorial/LIS - count ways, rod cut
     * 2> Subset/Knapsack - subset(include/exclude) sum
     * 3> Grid/Matrix - max sum path
     * 4> Strings
     * @see com.raj.dp.CountWays - Simple counting
     * @see com.raj.dp.DiceThrowSum - Complex counting - notice dice,target & dice face loops - follows rec formula exactly
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
     * @see CourseSchedule - Simple Topological Sort DFS
     * @see com.raj.graphs.topo.DependencyResolver - Topo Sort w/ (rev dep) Kahns works with BFS layerwise peeling
     *
     * @see com.raj.graphs.ShortestPathWeighted - Dijkstra's Shortest Path in Weighted graph w/ cycles
     * # When a problem refers a DAG, first thing to try is Topological Sort, then do what u need to find like - shortest path, schedule etc in second pass.
     * @see com.raj.graphs.LongestPathWeightedDAG - Topo Sort DFS + Longest Path + Track Parent
     * @see com.raj.graphs.InfectionMinTime - BFS w/ tracking healthy patient
     *
     */



}
