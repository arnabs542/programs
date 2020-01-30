package com.raj.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WeightedJobSchedule {
    /**
     * Given N jobs where every job is represented by following three elements of it:
     * a) Start Time
     * b) Finish Time
     * c) Profit or Value Associated (>= 0)
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
     *
     * Hence, we'll need to try all possible options - subset pattern.
     */
    public static void main(String[] args) {
        Job jobs1[] = new Job[]{
                new Job(3, 5, 20),
                new Job(1, 2, 50),
                new Job(6, 19, 100),
                new Job(2, 100, 200)
        };
        Job jobs2[] = new Job[]{
                new Job(3, 5, 20),
                new Job(1, 2, 50),
                new Job(6, 19, 300),
                new Job(2, 100, 200)
        };
        System.out.println("==== Jobs 1 ====");
        System.out.println("Max Profit rec = " + rec(jobs1) + "\n");
        System.out.println("Max Profit DP = " + dp(jobs1) + "\n");
        System.out.println("Max Profit DP sub-optimal = " + dp_suboptimal(jobs1) + "\n");  // easy solution but time limit exceeded on leetcode

        System.out.println("==== Jobs 2 ====");
        System.out.println("Max Profit rec = " + rec(jobs2) + "\n");
        System.out.println("Max Profit DP = " + dp(jobs2) + "\n");
        System.out.println("Max Profit DP sub-optimal = " + dp_suboptimal(jobs2));
    }

    static int rec(Job[] jobs) {
        // sort jobs by end times
        Arrays.sort(jobs, (a,b) -> (a.end-b.end));
        return findMaxProfit(jobs, jobs.length-1);
    }

    /**
     * The idea is to find the latest job before the current job (in sorted array) that doesn't conflict with current job arr[n]
     * Note: Going fwd runs into problems with excluding especially as it adds up jobs that shudn't be & bug prone.
     * Going reverse is much simpler.
     *
     * Sort jobs by increasing finish times.
     * {1, 2, 50}
     * {3, 5, 20}
     * {6, 19, 100}
     * {2, 100, 200}
     *
     * Rec Tree =>
     *                        f(A,i)
     *            incl i/               \excl i
     *                 /                 \
     *     A[i].profit +                f(A,i-1)
     *     f(A,getNextJob(A,i))
     *
     *                        f(3)          ... max(250,170)
     *                   200/        \0
     *             200 + f(0)         f(2)   ... max(100+20+50,70)
     *                50/        100/    \0
     *                 x        f(1)    f(1)   ... max(20+50,50)
     *                        20/      20/   \0
     *                       f(0)     f(0)    f(0)
     *                        |        |      |
     *                       50       50      50
     *
     *
     * Time = O(n.2^n)    ... n to get next non-conflicting job & 2^n for num subsets
     * Space = O(depth of rec stack) = O(n)
     * Optimization -> reduce to O(logn.2^n) by using binary search to find next job
     */
    static int findMaxProfit(Job[] jobs, int i) {
        if (i < 0) return 0;
        if (i == 0) return jobs[i].profit;

        // include ith job - also find the next job that can be scheduled
        int inclP = jobs[i].profit + findMaxProfit(jobs, nextNonConflict(jobs, i));

        // Find profit when current job is excluded
        int exclP = findMaxProfit(jobs, i-1);

        return Math.max(inclP, exclP);
    }

    /**
     * Find the latest job before the current job (in sorted array) that doesn't conflict with current job
     */
    static int nextNonConflict(Job[] jobs, int i) {
        for (int j=i-1; j>=0; j--) {
            if (jobs[j].end <= jobs[i].start) return j;
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

    /**
     * Sort jobs by increasing finish times. We can optimize next job to schedule in logn time.
     * {1, 2, 50}
     * {3, 5, 20}
     * {6, 19, 100}
     * {2, 100, 200}
     *          0   1   2   3    => Job i
     *          50  70  170 x -> max(200 + 50, 170) = 250
     *
     * # Starting job 1 onwards, find profit by including / excluding this job.
     * # Since, we go from i=0->n, we wud have pre-computed values for lower i that is reqd for next ith job computation
     *
     * DP Formula =>
     * base case:  T[0] = jobs[0].profit
     * for i <- 1 to n,
     *     T[i] = max(jobs[i].profit + binarySearchNextJob(i), T[i-1])
     *
     * Time = O(n.logn)
     */
    static int dp(Job[] jobs) {
        // sort jobs by end times
        Arrays.sort(jobs, (a,b) -> (a.end-b.end));
        int[] T = new int[jobs.length];
        T[0] = jobs[0].profit;  // start with selecting the first job

        for (int i = 1; i < jobs.length; i++) { // for each ith job, try to find the profit going backwards
            // include
            int inclP = jobs[i].profit;
            int j = bSearchNextJob(jobs, i);
            if (j != -1) inclP += T[j];
            // exclude
            int exclP = T[i-1];
            T[i] = Math.max(inclP, exclP);
        }
        System.out.println("DP Table O(nlogn): " + Arrays.toString(T));

        Set<Job> res = new HashSet<>();
        int i = jobs.length-1;
        int P = T[i];
        while (i >= 0) {
            int j = nextNonConflict(jobs, i);
            if (j != -1 && (P-jobs[i].profit) == T[j]) {
                res.add(jobs[i]); res.add(jobs[j]);
                P -= jobs[i].profit;
                i = j;
            } else i--;
        }
        System.out.println("Jobs selected : " + res);
        return T[jobs.length-1];
    }

    /**
     * Find a job which could be scheduled before this job i.
     * As jobs array is sorted by end times, Binary Search for the job whose end time <= input job[i].start time
     */
    static int bSearchNextJob(Job[] jobs, int i) {
        int s = 0;
        int e = i-1;
        while (s <= e) {
            int m = s + (e-s)/2;
            if (jobs[m].end <= jobs[i].start) {
                // what if m+1 element is also meeting the above condition?
                if (jobs[m+1].end <= jobs[i].start) s = m+1;    // we need to reduce search space to m+1...i...e
                else return m;
            } else {      // jobs[m].end > jobs[i].start ie. s...i...m...e
                e = m-1;
            }
        }
        return -1;
    }

    /**
     * This uses LIS pattern - tries each job from 0..i in inner loop to maximize profit.
     *
     *    0   1   2    3   => job end times
     *   50  70  170  250  => profit
     *
     * T[i] = max(T[i], jobs[i].profit+T[j]), for j=0..i AND jobs[j].end <= jobs[i].start
     * Time = O(n^2)
     *
     * Can we find the job to schedule next in a more optimized way?
     * That's what optimal solution above - dp(jobs) is about where it uses b-search to find the prev job.
     */
    static int dp_suboptimal(Job[] jobs) {
        // sort by end times
        Arrays.sort(jobs, (a,b) -> (a.end-b.end));
        int[] T = new int[jobs.length];
        T[0] = jobs[0].profit;  // start with selecting the first job

        int maxProfit = 0;
        for (int i = 1; i < jobs.length; i++) { // for each ith job, try to find the profit using 0...i jobs
            T[i] = jobs[i].profit;
            for (int j = 0; j < i; j++) {
                // include ith job's profit & find max profit using jobs that end before ith job
                if (jobs[j].end <= jobs[i].start) {
                    T[i] = Math.max(T[i], jobs[i].profit + T[j]);
                }
            }
            maxProfit = Math.max(maxProfit, T[i]);
        }
        System.out.println("DP Table sub-optimal : " + Arrays.toString(T));
        return maxProfit;
    }

}
