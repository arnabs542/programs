package com.raj.adhoc;

public class JobScheduleMinTime {

    /**
     * Given an array of jobs with different time requirements. There are K identical workers available and we are
     * also given how much time an assignee takes to do one unit of the job. Find the minimum time to finish all jobs
     * with following constraints:
     * An assignee can be assigned only contiguous jobs. For example, an worker cannot be assigned jobs 1 and 3, but not 2.
     */
    public static void main(String arg[]) {
        System.out.println(findMinTime(4, 5, new int[]{10,7,8,12,6,8}));
        System.out.println(findMinTime(2, 5, new int[]{4,5,10}));
    }

    /**
     * K --> number of workers
     * T --> Time required by every assignee to finish 1 unit
     * jobs --> Number of jobs with time
     *
     * For {10, 7, 8, 12, 6, 8}, the answer will lie b/w 0.....51 (sum of all job times)
     * So, basically it's a sorted time order, we just need to binary search for our answer which can finish all jobs w/ constraints
     * 0.....51
     *   m=25 ---> can we finish in this time? If yes, try reducing the time window by searching answer in left or right
     * canFinish(time) => tells if we can finish all jobs
     */
    static int findMinTime(int K, int timePerJob, int[] job) {
        // add up all job times to get max bounds
        int end = 0;
        for (int j : job) end += j;

        // now binary search for answer
        int minTime = Integer.MAX_VALUE;
        int start = 0;
        int mid;
        while (start <= end) {
            mid = (start + end) / 2;
            if (canFinishInTime(mid, job, K)) {     // can we finish in this time?
                minTime = Math.min(minTime, mid);   // update if a shorter time possible
                end = mid - 1;      // try a shorter time frame
            } else {    // else try a bigger time
                start = mid + 1;
            }
        }
        return minTime * timePerJob;
    }

    static boolean canFinishInTime(int allowedTime, int[] job, int K) {
        int curr_time = 0;
        for (int j : job) {
            // if job itself is greater than allowed time, just exit as we can't slot it with any worker at all
            if (j > allowedTime) return false;

            // if time exceeds allowed time, use another worker
            if (curr_time + j > allowedTime) {
                curr_time = 0;
                K--;
                if (K < 1) return false;  // exhausted all workers, exit
            }
            // add time of job to current time, only if it fits within time
            curr_time += j;
        }
        return K >= 1; // should have used less than or equal to K workers
    }

}


