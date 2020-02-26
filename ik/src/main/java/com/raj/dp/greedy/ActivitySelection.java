package com.raj.dp.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivitySelection {
    /**
     * You are given n activities with their start and finish times. Select the maximum number of activities that can be
     * performed by a single person, assuming that a person can only work on a single activity at a time.
     *
     * Example:
     * start time = {0, 3, 1, 5, 5, 8};
     * end time   = {6, 4, 2, 9, 7, 9};
     * o/p - maximum set of activities that can be executed is (1,2) (3,4) (5,7) (8,9)
     *
     * https://en.wikipedia.org/wiki/Interval_scheduling
     * "Selecting the intervals that start earliest is not an optimal solution, because if the earliest interval happens
     * to be very long, accepting it would make us reject many other shorter requests.
     * Selecting the shortest intervals or selecting intervals with the fewest conflicts is also not optimal."
     */
    public static void main(String[] args) {
        System.out.println(greedy(new int[]{0, 3, 1, 5, 5, 8}, new int[]{6, 4, 2, 9, 7, 9}));
        System.out.println(greedy(new int[]{0, 2, 4, 3}, new int[]{2, 4, 6, 5}));
    }

    /**
     * https://www.geeksforgeeks.org/activity-selection-problem-greedy-algo-1/
     * The greedy choice is to always pick the next activity whose finish time is least among the remaining activities
     * and the start time is more than or equal to the finish time of previously selected activity. We can sort the
     * activities according to their finishing time so that we always consider the next activity which will finish
     * faster as time elapses.
     * 1) Sort the activities according to their finishing time
     * 2) Select the first activity from the sorted array and print it.
     * 3) Do following for remaining activities in the sorted array.
     *    a) If the start time of this activity is greater than or equal to the finish time of previously selected
     *       activity then select this activity and print it.
     * TIme = O(nlogn)
     */
    static List<Job> greedy(int[] start, int[] end) {
        List<Job> res = new ArrayList<>();

        // sort by finish times so as to maximize num tasks otherwise a long running task can hog b/w
        int n = start.length;
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(start[i], end[i]);
        }
        Arrays.sort(jobs, (a,b) -> a.e - b.e);

        // start with first job in sorted array
        int i = 0;
        res.add(jobs[i]);
        // i -> last selected job, j -> new job under consideration
        for (int j = 1; j < n; j++) {
            if (jobs[j].s >= jobs[i].e) {
                res.add(jobs[j]);
                i = j;
            }
        }
        return res;
    }

    static class Job {
        int s,e;
        Job(int _s, int _e) {
            s = _s;
            e = _e;
        }
        public String toString() {
            return "(" + s + "," + e + ")";
        }
    }

}
