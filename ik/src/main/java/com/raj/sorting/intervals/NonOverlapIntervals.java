package com.raj.sorting.intervals;

import java.util.Arrays;

public class NonOverlapIntervals {
    /**
     * Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the
     * intervals non-overlapping. (Same as, find the max number of non-overlapping ranges)
     * Input: [[1,2],[2,3],[3,4],[1,3]]
     * Output: 1
     * Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
     */
    public static void main(String[] args) {
        System.out.println(eraseOverlapIntervals(new Interval[]{
                new Interval(1,2),
                new Interval(2,3),
                new Interval(3,4),
                new Interval(1,3),
        }));
    }

    /**
     * Sort by start?
     * # May hog a lot of subsequent ranges if it has a huge end range. And we have to optimize for max non-overlap ranges.
     * # To maximize on ranges, we need to finish tasks which have quicker end times (idea is same as Greedy Activity Selection)
     * https://leetcode.com/problems/non-overlapping-intervals/discuss/91713/Java%3A-Least-is-Most
     * # Sort by end times, pick next eligible interval whose start is after this end.
     * Dry run:
     * 1...2       -> selected
     *     2...3   -> selected next
     * 1.......3   -> not eligible
     *         3...4 -> selected next
     */
    static int eraseOverlapIntervals(Interval[] intervals) {
        // sort by end to greedily optimize on max number of tasks
        Arrays.sort(intervals, (a,b) -> a.end-b.end);
        int selectedCount = 1;
        Interval cur_interval = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start >= cur_interval.end) {
                selectedCount++;
                cur_interval = intervals[i];
            }
        }
        return intervals.length - selectedCount;
    }

    static class Interval {
        int start;
        int end;
        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }
}
