package com.raj.sorting.intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class MergeOverlappingIntervals {

    /**
     * https://www.geeksforgeeks.org/merging-intervals/
     * Given a set of time intervals in any order, merge all overlapping intervals into one and output the result which
     * should have only mutually exclusive intervals.
     * Input : (1,3), (5,7), (2,4), (6,8)
     * Output: (1,4), (5,8)
     */
    public static void main(String[] args) {
        int[][] A = new int[][]{
                {1,3},
                {5,7},
                {2,4},
                {6,8}
        };
        System.out.println(Arrays.deepToString(getMergedIntervals_stack(A)));
        System.out.println(Arrays.deepToString(getMergedIntervals(A)));
        intersectRangeIntervals(new int[][]{{0,2},{5,10},{13,23},{24,25}}, new int[][]{{1,5},{8,12},{15,24},{25,26}}).forEach(x -> System.out.println(Arrays.toString(x)));
    }

    /**
     * [Sort + Using Stacks]
     * Intuition to use stack is based on the fact that we only operate on the top of the stack interval by comparing against incoming interval
     * 1. Sort the intervals based on increasing order of starting time.
     * 2. Push the first interval on to a stack.
     * 3. For each interval do the following
     *    a. If the current interval does not overlap with the stack top, push it.
     *    b. If the current interval overlaps with stack top and ending time of current interval is more than that of stack top, update stack top with the ending time of current interval.
     * 4. At the end stack contains the merged intervals.
     * Time/Space = O(n)
     */
    static int[][] getMergedIntervals_stack(int[][] A) {
        if (A == null || A.length <= 1) return A;

        // sort by start idx
        Arrays.sort(A, (a,b) -> a[0]-b[0]);
        Stack<int[]> stack = new Stack<>();
        stack.push(A[0]); // push first interval
        for (int i = 1; i < A.length; i++) {
            int[] top = stack.peek();
            if (A[i][0] <= top[1]) top[1] = Math.max(top[1], A[i][1]);
            else stack.push(A[i]);
        }
        return stack.toArray(new int[][]{});
    }

    /**
     * [Sort + In-place adjust]
     * # Maintain 2 ptrs i & j
     * # i is the merged interval
     * # j is the candidate interval that is compared against i
     * # 2 conditions:
     *   j.start <= i.end merge by extending end for i by max(i.end,j.end)  ... takes care of partial + full overlap
     *   j.start > i.end, can't merge so just swap it as i+1 th pos as j might have moved fwd by more than 1 place after merges before
     * # Return A[0...i]
     * Time = O(n), Aux Space = O(1)
     */
    static int[][] getMergedIntervals(int[][] A) {
        if (A == null || A.length <= 1) return A;

        // sort by start idx
        Arrays.sort(A, (a,b) -> Integer.compare(a[0],b[0]));

        int i = 0; // use the array itself for result, i represents bounds of the res array
        for (int j=1; j<A.length; j++) {
            if (A[j][0] <= A[i][1]) A[i][1] = Math.max(A[i][1],A[j][1]);  // partial or full overlap. just extend i's end
            else A[++i] = A[j];  // no overlap, so shuffle j to it's correct place
        }
        return Arrays.copyOfRange(A, 0, i+1);  // no arr copy
    }

    /**
     * Another simpler variant of this problem -
     * Given N intervals of the form of [l, r], the task is to find the intersection of all the intervals.
     * Input: arr[] = {{1, 6}, {2, 8}, {3, 10}, {5, 8}}
     * Output: [5, 6]
     * # res = A[0]
     * # for i = 1 to n:
     *   if (A[i].start > res.end) ret -1
     *   else {
     *      res.start = max(res.start, A[i].start)
     *      res.end = min(res.end, A[i].end)
     *   }
     */

    /**
     * Another variant of above:
     * Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.
     * Return the intersection of these two interval lists.
     * Input: A = {{0,2},{5,10},{13,23},{24,25}}, B = {{1,5},{8,12},{15,24},{25,26}}
     * Output: {{1,2},{5,5},{8,10},{15,23},{24,24},{25,25}}
     *
     * [2-Ptr Merge Sort]
     * # i,j ptrs for respective lists
     * # max of starts, min of ends, if valid print. Move ptr whose end is lesser
     */
    static List<Integer[]> intersectRangeIntervals(int[][] A, int[][] B) {
        List<Integer[]> res = new ArrayList<>();
        int i = 0, j = 0;
        while (i < A.length && j < B.length) {
            int startMax = Math.max(A[i][0], B[j][0]);
            int endMin = Math.min(A[i][1], B[j][1]);
            if (startMax <= endMin) res.add(new Integer[]{startMax,endMin});
            if (A[i][1] < B[j][1]) i++;
            else if (B[j][1] < A[i][1]) j++;
            else {i++; j++;}   // if both range end is same
        }
        return res;
    }

    /**
     * [Find Missing ranges]
     * Given a sorted integer array where the range of elements are [0, 99] inclusive, return its missing ranges.
     * For example, given [0, 1, 3, 50, 75], return [“2”, “4->49”, “51->74”, “76->99”]
     *
     * # Iter and compare i-1 with i, if they aren't 1 number apart, we have a missing range. Print prev+1 -> cur-1
     * # Add -1 to start & 100 to end to avoid dealing with edge cases like arr empty etc.
     */

}
