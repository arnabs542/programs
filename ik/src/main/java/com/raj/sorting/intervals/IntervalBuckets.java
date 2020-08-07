package com.raj.sorting.intervals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class IntervalBuckets {
    /**
     * Given a stream of numbers, find the recipient bucket which holds a interval range of numbers.
     * Interval   Bucket
     * (1,10)  -> 1
     * (5,10)  -> 2
     * (15,25) -> 4
     * (7,12)  -> 3
     * (20,25) -> 5
     *
     * O/P:
     * Number  Buckets
     * 2       1
     * 5       1,2
     * 6       1,2
     * 8       1,2,3
     * 10      1,2,3
     * 13      0
     * 16      4
     * 25      4,5
     * 30      0
     */
    public static void main(String[] args) {
        findRecipientBucket(new int[]{2,5,6,8,10,11,13,16,24,25,30}, new Interval[] {
                new Interval(1,10,1),
                new Interval(5,10,2),
                new Interval(15,25,4),
                new Interval(7,12,3),
                new Interval(20,25,5),
        });
    }

    /**
     * # Brute: Given a number, go through all M intervals it lies within and print the corresponding bucket.
     * O(n.m)
     *
     * # SubOptimal: Pre-compute buckets for each number
     * 1->1, 2->1 ... 25->4,5
     * Pre-compute Time/Space = O(min_max_range_of_intervals)
     * Streaming Lookup = O(n.1)
     *
     * # Optimal_Complex: The above soln might be too time & space intensive when the range goes into millions, but the num of intervals are lesser (eg. 1,10->1 & 5,10M->2)
     * - To optimize, we need to save on pre-computation & only store info about the start/end range and buckets
     * - Sort the intervals by start
     * - For 1,10 we can just store 10 -> 1 in map? Then do BSearch, say, if N=2, then bucket is 1.
     * - Iter & push into map the range_end -> buckets + plus any fragmented intervals that occur due to collapse
     * - 3 cases when collapsing intervals:
     *   -------         |  -------       |   ---------
     *            -----  |       ------   |      ----
     * - Edge case: How do we handle inclusive & exclusive range? Have exclusive,inclusive
     *
     * End   Ex  | Inc
     * 1  -> -1  | 1
     * 5  -> 1   | 1,2
     * 7  -> 2   | 2,3
     * 10 -> 1,2 | 1,2
     * 12 -> 3   | 3
     * 15 -> -1  | 4
     * 20 -> 4   | 4,5
     * 25 -> 4   | 4
     *
     * # Optimal_Elegant: The above approach is quite complex
     * - Simpler way is the intuition of adding a bucket to start & removing it at the end of a range
     * - For (1,10)->1, Just +1 at 1 & -1 at 11. Do it for all ranges
     *   1   5  7  11     13  15  20  26
     *   +1 +2 +3  -1,-2  -3  +4  +5  -4,-5
     * - Now go left to right, adding / subtracting buckets to get cumulative buckets:
     *   1   5   7      11   13  15  20  26
     *   1  1,2  1,2,3  3    0   4   5   0
     * - Now given a number, do BSearch:
     *   mid == num ? ret mid.bucket
     *   mid-1 < num < mid ? ret mid-1.bucket
     *   num < mid ? bSearch left
     *   else bSearch right
     */
    static void findRecipientBucket(int[] A, Interval[] intervals) {
        // sort intervals
        Arrays.sort(intervals, (a,b) -> a.start-b.start);

        /**
         * Pre-compute phase
         */
        // first scan add start,end individual +/- bucket
        TreeMap<Integer,RangeBucket> rangeBuckets = new TreeMap<>((a,b) -> a-b);
        for (Interval interval : intervals) {
            // + at start
            rangeBuckets.putIfAbsent(interval.start, new RangeBucket(interval.start));
            rangeBuckets.get(interval.start).buckets.add(interval.bucket);
            // - at end
            rangeBuckets.putIfAbsent(interval.end+1, new RangeBucket(interval.end+1));
            rangeBuckets.get(interval.end+1).buckets.add(-1 * interval.bucket);
        }

        // second scan - accumulate buckets into an array for easier BSearch
        RangeBucket[] rangeBucketsArr = rangeBuckets.values().toArray(new RangeBucket[0]);
        for (int i = 0; i < rangeBucketsArr.length; i++) {
            Set<Integer> buckets = rangeBucketsArr[i].buckets;
            Set<Integer> accumulatedBuckets = new HashSet<>();
            // get prev bucket state copy
            if (i > 0) accumulatedBuckets = new HashSet<>(rangeBucketsArr[i-1].buckets);
            for (int bucket : buckets) {
                if (bucket > 0) { // add to buckets
                    accumulatedBuckets.add(bucket);
                } else accumulatedBuckets.remove(-1 * bucket);  // remove from bucket
            }
            rangeBucketsArr[i].buckets = accumulatedBuckets;
        }

        /**
         * Use pre-computed sorted rangeBuckets to Binary Search for numbers
         */
        for (int n : A) {
            System.out.println(n + " -> " + bSearch(rangeBucketsArr, n, 0, rangeBucketsArr.length-1));
        }
    }

    static Set<Integer> bSearch(RangeBucket[] arr, int num, int start, int end) {
        if (start > end) return null;
        int mid = start + (end - start)/2;
        if (arr[mid].end == num) return arr[mid].buckets; // mid == num ? ret mid.bucket
        else if (mid-1 >= 0 && arr[mid-1].end < num && num < arr[mid].end) return arr[mid-1].buckets; // mid-1 < num < mid ? ret mid-1.bucket
        else if (num < arr[mid].end) return bSearch(arr, num, start, mid-1); // rec search left
        else return bSearch(arr, num, mid+1, end);  // rec search right
    }

    static class Interval {
        int start;
        int end;
        int bucket;
        Interval(int s, int e, int b) {
            start = s;
            end = e;
            bucket = b;
        }
    }

    static class RangeBucket {
        int end;
        Set<Integer> buckets = new HashSet<>();
        RangeBucket(int e) {
            end = e;
        }
    }
}
