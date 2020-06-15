package com.raj.sorting.intervals;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MeetingRooms {
    /**
     * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
     * determine if a person could attend all meetings.
     * For example:
     * Given [[0, 30],[5, 10],[15, 20]],
     * return false.
     *
     * # Sort the intervals in the ascending order of start; then we check for the overlapping of each pair of neighboring intervals
     */
    static boolean canAttendAllMeetings(Interval[] intervals) {
        Arrays.sort(intervals, (a,b) -> a.start-b.start);
        Interval cur_interval = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start < cur_interval.end) return false;
        }
        return true;
    }

    /**
     * Maximize number of meetings one can attend. Same as -
     * @see NonOverlapIntervals
     * [[0, 30],[5, 10],[15, 20]] => 2
     * # [Activity selection greedy strategy]
     * # Sort by end times, pick eligible meetings otherwise a single meeting could hog all time slots.
     */

    /**
     * Meeting Rooms II:
     * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
     * find the minimum number of conference rooms required.
     * For example,
     * Given [[0, 30],[5, 10],[15, 20]],
     * return 2.
     *
     * Approach 1: [Sort_by_start_time + MinHeap_end_time]
     * # Sort the meetings based on start at first. Use a minHeap by end time, to find the least end times of current meeting rooms in heap.
     * # Iter & compare incoming's start is after top's end time, if yes update the endtime (same room is used)
     *   - if not then push into heap with end time
     * O(nlogn)
     */
    static int minMeetingRooms_heap(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        // sort by start times
        Arrays.sort(intervals, (a,b) -> a.start - b.start);
        // keep the most earliest end meeting time at top
        PriorityQueue<Interval> minPQ = new PriorityQueue<>((a,b) -> a.end - b.end);
        minPQ.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            Interval cur = intervals[i];
            Interval top = minPQ.peek();
            if (cur.start >= top.end) {  // same room can be used
                top.end = cur.end;
            } else minPQ.add(cur);
        }
        return minPQ.size();
    }

    /**
     * Approach 2: [Sort+Pre-compute start/end ranges as +1/-1, iter and find max count]
     * # Similar idea to -
     * @see IntervalBuckets
     * # Sort by start. Add start as +1 & end as -1 for each interval to TreeMap (sorted by time slot)
     * # Iterate treemap & compute max room needed
     * O(nlogn)
     */
    static int minMeetingRooms(Interval[] intervals) {
        // sort by start times
        Arrays.sort(intervals, (a,b) -> a.start - b.start);
        TreeMap<Integer,Integer> map = new TreeMap<>();
        // add start/end markers
        for (Interval interval : intervals) {
            map.put(interval.start, map.getOrDefault(interval.start,0) + 1);
            map.put(interval.end, map.getOrDefault(interval.end,0) - 1);
        }
        // scan and compute max rooms
        int cnt = 0, max = 0;
        for (int i : map.values()) {
            cnt += i;
            max = Math.max(max, cnt);
        }
        return max;
    }

    static class Interval {
        int start;
        int end;
        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static void main(String[] args) {
        System.out.println(canAttendAllMeetings(new Interval[]{
                new Interval(0,30),
                new Interval(5,10),
                new Interval(15,20)
        }));

        System.out.println(minMeetingRooms_heap(new Interval[]{
                new Interval(0,30),
                new Interval(5,10),
                new Interval(15,20)
        }));

        System.out.println(minMeetingRooms(new Interval[]{
                new Interval(0,30),
                new Interval(5,10),
                new Interval(15,20)
        }));
    }
}
