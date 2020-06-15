package com.raj.sorting.intervals;

public class FindNumIntervals {

    /**
     * Given a list of interval n & stream of numbers m, find how many intervals each number is in:
     * (1,5), (12,15), (2,4)
     * Num 1 occurs in 1 interval
     * Num 2 & 3 occurs in 2 interval
     * Num 4 occurs in 2 interval
     * Num 5 occurs in 1 interval
     * Num 6 & 17 occurs in 0 interval
     */
    public static void main(String[] args) {
        Interval[] intervals = new Interval[] {
                new Interval(1,5),
                new Interval(12,15),
                new Interval(2,4)
        };
        System.out.println(findNumIntervals(intervals, 1));
        System.out.println(findNumIntervals(intervals, 2));
        System.out.println(findNumIntervals(intervals, 4));
        System.out.println(findNumIntervals(intervals, 5));
        System.out.println(findNumIntervals(intervals, 6));
    }

    /**
     * Approach 1: https://www.geeksforgeeks.org/count-the-number-of-intervals-in-which-a-given-value-lies/
     * [Runtime efficient at cost of space, think abt ranges going into billions, we'll need billion array size]
     * Use a frequency array that keeps track of how many of the given intervals an element lies in.
     *
     * For every interval [L, R], put freq[L] as freq[L] + 1 and freq[R+1] as freq[R + 1] – 1 indicating start and end of the interval.
     * Keep track of the overall minimum and maximum of the intervals.
     * Starting from minimum to maximum construct the frequency array from its previous element i.e.,
     * freq[i] = freq[i] + freq[i – 1].
     * Required count of intervals is then given by freq[V].
     *
     * Approach 2: IK Glenn Stroz
     * [Space efficient]
     * First we'll pre-process the intervals.
     * # Sort the intervals by start => (1,5), (2,4), (12,15)
     * # Compute num interval at each start / end points.
     * # Structure is - IntervalCount(start,num_intervals)
     *                  1.................5     12......15
     *                 +1  +1        -1  -1    +1       -1
     *                      2.........4
     * # After processing => (1,1), (2,2), (4,1), (5,0), (12,1), (15,0)
     * # Here we are excluding the endIdx eg. (4,1) but it lies in 2 intervals, for convenience.
     * # We can consider the prev IntervalCount and see if the count is decreasing, then we just add 1 to count.
     * # Now for a given number, binary search and find the closest IntervalCount and determine count
     *
     * @see IntervalBuckets - for space efficient soln based on Approach 2 IK Glenn Stroz
     */
    static int findNumIntervals(Interval[] intervals, int num) {
        //Arrays.sort(intervals, (a,b) -> a.x-b.x);
        // Variables to store overall minimum and maximum of the intervals
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        // Variables to store start and end of an interval
        int li, ri;

        // Frequency array to keep track of how many of the given intervals an element lies in
        int[] freq = new int[100000];

        for (int i = 0; i < intervals.length; i++) {
            li = intervals[i].x;
            freq[li] = freq[li] + 1;
            ri = intervals[i].y;
            freq[ri + 1] = freq[ri + 1] - 1;

            if (li < min) min = li;
            if (ri > max) max = ri;
        }

        // Constructing the frequency array
        for (int i = min; i <= max; i++) {
            freq[i] = freq[i] + freq[i - 1];
        }

        return freq[num];
    }

    static class Interval {
        int x,y;
        Interval(int _x, int _y) {
            x = _x; y = _y;
        }
    }

}
