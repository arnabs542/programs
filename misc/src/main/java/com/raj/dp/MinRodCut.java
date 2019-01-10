package com.raj.dp;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rshekh1
 */
public class MinRodCut {

    public static void main(String[] args) {
        System.out.println(rodCut(6, Lists.newArrayList(1,2,5)));
    }

    /**
     * https://www.youtube.com/watch?v=zFe-SX7jzDs
     *
     * There is a rod of length N lying on x-axis with its left end at x = 0 and right end at x = N.
     * Now, there are M weak points on this rod denoted by positive integer values(all less than N) A1, A2, …, AM.
     * You have to cut rod at all these weak points. You can perform these cuts in any order.
     * After a cut, rod gets divided into two smaller sub-rods. Cost of making a cut is the length of the sub-rod in which you are making a cut.
     *
     * Your aim is to minimise this cost. Return an array denoting the sequence in which you will make cuts.
     * If two different sequences of cuts give same cost, return the lexicographically smallest.
     *
     * Notes:
     * Sequence a1, a2 ,…, an is lexicographically smaller than b1, b2 ,…, bm, if and only if at the first i
     * where ai and bi differ, ai < bi, or if no such i found, then n < m.
     * N can be upto 109.
     * For example,
     * N = 6
     * A = [1, 2, 5]
     *
     * If we make cuts in order [1, 2, 5], let us see what total cost would be.
     * For first cut, the length of rod is 6.
     * For second cut, the length of sub-rod in which we are making cut is 5(since we already have made a cut at 1).
     * For third cut, the length of sub-rod in which we are making cut is 4(since we already have made a cut at 2).
     * So, total cost is 6 + 5 + 4.
     *
     * Cut order          | Sum of cost
     * (lexicographically | of each cut
     *  sorted)           |
     * ___________________|_______________
     * [1, 2, 5]          | 6 + 5 + 4 = 15
     * [1, 5, 2]          | 6 + 5 + 4 = 15
     * [2, 1, 5]          | 6 + 2 + 4 = 12
     * [2, 5, 1]          | 6 + 4 + 2 = 12
     * [5, 1, 2]          | 6 + 5 + 4 = 15
     * [5, 2, 1]          | 6 + 5 + 2 = 13
     *
     *
     * So, we return [2, 1, 5].
     */

    /**
     * Solution:
     * We rewrite our problem as given N cut points(and you cannot make first and last cut), decide order of these cuts to minimise the cost.
     * So, we insert 0 and N at beginning and end of vector B. Now, we have solve our new problem with respect to this new array(say A).
     *
     * We define dp(i, j) as minimum cost for making cuts Ai, Ai+1, …, Aj. Note that you are not making cuts Ai and Aj, but they decide the cost for us.
     *
     * For solving dp(i, j), we iterate k from i+1 to j-1, assuming that the first cut we make in this interval is Ak.
     * The total cost required(if we make first cut at Ak) is Aj - Ai + dp(i, k) + dp(k, j).
     *
     * This is our solution. We can implement this DP recursively with memoisation. Total complexity will be O(N3).
     * For actually building the solution, after calculating dp(i, j), we can store the index k which gave the minimum
     * cost and then we can build the solution backwards.
     *
     * Let’s say We want to find find point from we can cut rod optimally from array index L to R.
     * We need to check each points L<i<R and find costs by cutting rod from i.
     * At any index i cost of cutting rod is,
     * cost[L][R] = A[R]-A[L] + cost[L][i] + cost[i][R]
     *
     */
    static List<Integer> result;
    static int[] cuts;
    static int[][] parent;

    public static List<Integer> rodCut(int rodSize, List<Integer> cutPoints) {
        int numCuts = cutPoints.size() + 2;   // add 0 & N to the size
        cuts = new int[numCuts];
        cuts[0] = 0;
        for (int i = 0; i < cutPoints.size(); i++) {
            cuts[i + 1] = cutPoints.get(i);
        }
        cuts[numCuts - 1] = rodSize;      // 0,1,2,5,6

        long[][] dp = new long[numCuts][numCuts];
        parent = new int[numCuts][numCuts];

        for (int len = 1; len <= numCuts; len++) {
            for (int start = 0; start < numCuts - len; start++) {
                int end = start + len;
                for (int k = start + 1; k < end; k++) {
                    long sum = cuts[end] - cuts[start] + dp[start][k] + dp[k][end];
                    if (dp[start][end] == 0 || sum < dp[start][end]) {
                        dp[start][end] = sum;
                        parent[start][end] = k;
                    }
                }

            }
        }
        result = new ArrayList<>();
        backTrack(0, numCuts - 1);
        return result;
    }

    private static void backTrack(int start, int end) {
        if (start + 1 >= end) {
            return;
        }

        result.add(cuts[parent[start][end]]);
        backTrack(start, parent[start][end]);
        backTrack(parent[start][end], end);
    }

    /**
     * 1 -> 6 + f(1,6,[2,5])
     *              /2 select  \5 select
     *     6 + 5 + f(1,2,[5]) + f(2,6,[5])
     *     ....
     * 2 -> 6 + f(0,2,[1]) + f(2,6,[5])
     *             /           |
     *      6 +  2+f(0)       4+f(0) = 12
     * 5 -> 6 + f(0,5,[1,2]) + f(5,6,[])
     */
    /*static int recSolve(int n, int start, int end, int[] A, int cost) {
        if (n <= 0) return 0;
        if (start >= end) return 0;
        int minVal = Integer.MAX_VALUE;
        for (int i = start; i < end; i++) {
            minVal = Math.min(minVal, recSolve(n - A[i], start, A[i], A, cost+A[i]));
        }

    }
*/
}
