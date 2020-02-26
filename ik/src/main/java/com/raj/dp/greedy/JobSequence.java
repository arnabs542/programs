package com.raj.dp.greedy;

import java.util.Arrays;

public class JobSequence {

    /**
     * https://www.geeksforgeeks.org/job-sequencing-problem/
     * Given an array of jobs where every job has a deadline and associated profit if the job is finished before the
     * deadline. It is also given that every job takes single unit of time, so the minimum possible deadline for any job
     * is 1. How to maximize total profit if only one job can be scheduled at a time.
     * JobID   Deadline  Profit
     *   A       2        100
     *   B       1        19
     *   C       2        27
     *   D       1        25
     *   E       3        15
     * Output: C,A,E
     */
    public static void main(String[] args) {
        Job[] jobs = new Job[]{
                new Job("A",2,100),
                new Job("B",1,19),
                new Job("C",2,27),
                new Job("D",1,25),
                new Job("E",3,15)
        };
        System.out.println(Arrays.toString(schedule_greedy(jobs)));
    }

    /**
     * First we need some ordering of jobs. Let's sort it by profit as we'll try to solve it via Greedy approach.
     * Idea is to schedule most profitable job as late as possible so as to allow slot for the next profitable job.
     * Scheduling it earlier won't help as all jobs are eligible to be scheduled at time t1, per problem & it may block some other's jobs scheduling.
     *
     * Sorted by Profit:
     * A,2,100
     * C,2,27
     * D,1,25
     * B,1,19
     * E,3,15
     *
     * Time slot =>  1  2  3  4  5
     *                  A
     *               C
     *                     E
     *
     * Runtime = O(n*n)
     */
    static String[] schedule_greedy(Job[] jobs) {
        int n = Arrays.stream(jobs).max((a,b) -> a.deadline-b.deadline).get().deadline;
        String[] res = new String[n+1];
        Arrays.fill(res, "");
        int maxProfit = 0;
        // sort by profit
        Arrays.sort(jobs, (a,b) -> b.profit-a.profit);
        for (int i = 0; i < jobs.length; i++) {     // iter thru all jobs
            for (int j = jobs[i].deadline; j > 0; j--) {    // find a free slot for this job
                if (res[j].isEmpty()) {
                    res[j] = jobs[i].id;
                    maxProfit += jobs[i].profit;
                    break;
                }
            }
        }
        System.out.println("max profit = " + maxProfit);
        return res;
    }

    static class Job {
        String id;
        int deadline;
        int profit;

        public Job(String id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    /**
     * Optimal Solution in O(nlogn) : https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
     */

}
