package com.raj.dp.greedy;

import java.util.Arrays;

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
        /*Job jobs2[] = new Job[]{
                new Job(1, 3, 50),
                new Job(2, 4, 10),
                new Job(3, 5, 40),
                new Job(3, 6, 70)
        };*/
        System.out.println(rec(jobs1));
        System.out.println(dp(jobs1));
    }

    static int rec(Job[] jobs) {
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
     * {4, 6, 10}
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
     * {4, 6, 10}
     * {6, 19, 100}
     * {2, 100, 200}
     *          0  1  2  3  4  5  6  .. 19  .. 100  => End Time
     *          0  0  50 50 50 70 70    170    x -> 200 + T(2) = 200+50 = 250
     *
     * # Starting job 1 onwards, find profit by including / excluding this job.
     * # Since, at each ith job, we look for a job which occurs before, we wud have pre-computed lower values of i.
     *
     * DP Formula =>
     * base case:  T[0] = jobs[0].profit
     * for i <- 1 to n,
     *     T[i] = max(jobs[i].profit + binarySearchNextJob(i), T[i-1])
     *
     * Time = O(n.logn)
     */
    static int dp(Job[] jobs) {
        Arrays.sort(jobs, (a,b) -> (a.end-b.end));
        int[] T = new int[jobs.length];
        T[0] = jobs[0].profit;

        for (int i = 1; i < jobs.length; i++) { // for each ith job, try to find the profit going backwards
            // include
            int inclP = jobs[i].profit;
            int j = bSearchNextJob(jobs, i);
            if (j != -1) inclP += T[j];
            // exclude
            int exclP = T[i-1];
            T[i] = Math.max(inclP, exclP);
        }
        System.out.println(Arrays.toString(T));
        return T[jobs.length-1];
    }

    /**
     * As jobs array is sorted by end times, Binary Search for the job whose end time <= passed job[i].start time.
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

}
