package com.raj.dp.greedy;

import java.util.Arrays;

public class WeightedJobSchedule {
    /**
     * Given N jobs where every job is represented by following three elements of it.
     *
     * Start Time
     * Finish Time
     * Profit or Value Associated (>= 0)
     * Find the maximum profit subset of jobs such that no two jobs in the subset overlap.
     *
     * Number of Jobs n = 4
     *        Job Details {Start Time, Finish Time, Profit}
     *        Job 1:  {1, 2, 50}
     *        Job 2:  {3, 5, 20}
     *        Job 3:  {6, 19, 100}
     *        Job 4:  {2, 100, 200}
     * Output: The maximum profit is 250. We can get the maximum profit by scheduling jobs 1 and 4.
     *
     * Note that there is longer schedules possible Jobs 1, 2 and 3 but the profit with this schedule is 20+50+100 which is less than 250.
     * Hence, Greedy Activity Selection Strategy to maximize Number of tasks won’t work here as a schedule with more jobs
     * may have smaller profit or value.
     */
    public static void main(String[] args) {
        Job jobs[] = new Job[]{
                new Job(3, 10, 20),
                new Job(1, 2, 50),
                new Job(6, 19, 100),
                new Job(2, 100, 200)
        };
        System.out.println(rec(jobs));
    }

    static int rec(Job[] jobs) {
        Arrays.sort(jobs, (a,b) -> (a.end-b.end));
        return findMaxProfit(jobs, 0);
    }

    static int findMaxProfit(Job[] jobs, int i) {
        if (i == jobs.length-1) return jobs[i].profit;

        // include
        int inclP = jobs[i].profit;
        int j = nextNonConflict(jobs, i);
        if (i != -1) inclP += findMaxProfit(jobs, i+1);

        // Find profit when current job is excluded
        int exclP = findMaxProfit(jobs, i+1);

        return Math.max(inclP, exclP);
    }

    static int nextNonConflict(Job jobs[], int i) {
        for (int j=i+1; j<jobs.length; j++) {
            if (jobs[j].start >= jobs[i].start) return j;
        }
        return -1;
    }

    static class Job {
        int start, end, profit;
        Job(int _s, int _e, int _p) {
            start = _s;
            end = _e;
            profit = _p;
        }
        public String toString() {
            return "(" + start + "," + end + "," + profit + ")";
        }
    }



}
