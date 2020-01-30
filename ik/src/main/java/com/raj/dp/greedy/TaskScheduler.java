package com.raj.dp.greedy;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class TaskScheduler {
    /**
     * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters
     * represent different tasks. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.
     * However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n
     * intervals that CPU are doing different tasks or just be idle.
     * You need to return the least number of intervals the CPU will take to finish all the given tasks.
     *
     * Example:
     * Input: tasks = ['A','A','A','B','B','B'], n = 2
     * Output: 8
     * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
     */
    public static void main(String[] args) {
        System.out.println(greedy(new char[]{65,65,66,66,67,67,68,68}, 2));
        System.out.println(greedy(new char[]{'A','A','A','B','B','B'}, 2));
    }

    /**
     * THIS DOESN'T YIELD OPTIMIZED SCHEDULE !
     * check greedy()
     *
     * Let's do Greedy approach - start with the task with max number of occurrences & slot it out in res.
     * Create a tuple of (task,count)
     * Insert into MaxHeap.
     * Pop next max and schedule it in empty slot after cooldown intervals starting from left.
     */
    static int _greedy_not_optimal(char[] tasks, int cd) {
        // get task freq
        int[] taskCounts = new int[256];
        for (char t : tasks) taskCounts[t]++;

        // insert into max heap with priority as counts
        PriorityQueue<Pair<Character,Integer>> pq = new PriorityQueue<>((a,b) -> b.getValue()-a.getValue());
        for (int i=0; i<256; i++)
            if (taskCounts[i] > 0) pq.add(new Pair<>((char)i, taskCounts[i]));

        // slot into empty slot starting with max freq task
        char[] res = new char[100];
        int totalIntervals = 0;

        while (!pq.isEmpty()) {
            Pair<Character,Integer> task = pq.poll();
            char taskId = task.getKey();
            int cnt = task.getValue();
            int i = 0;
            while (cnt > 0) {
                if (res[i] == '\u0000') {   // find empty slot
                    totalIntervals = Math.max(totalIntervals, i+1); // arr[0] being set means totIntervals is 1
                    res[i++] = taskId;
                    cnt--;
                    i+=cd;   // the minimum next slot we can choose for this task
                } else {    // otherwise keep looking for next empty slot
                    i++;
                }
            }
        }
        System.out.println(Arrays.toString(res));
        return totalIntervals;
    }

    /**
     * Slotting tasks doesn't yield optimized results if the interval is smaller than tasks for eg. A,A,B,B,C,C,D,D cd=2
     * Hence, we need to schedule each interval greedily with highest freq task. Do this until all tasks exhaust.
     * # Create tuples of task,counts
     * # Put them into maxHeap to be able to extract highest priority tasks(more freq).
     * # loop until heap has tasks
     *   -> loop until cooldown interval
     *      - remove top task from heap, add it to result & also to cooldown list (to put them back in heap later)
     *      - add a '.' for idle, if heap becomes empty
     *   -> add back tasks from cooldown list with one less freq
     * # print tasks sequence, return size of res
     *
     * Time = O(n + nlogn)  ... freq compute + pq runtime, but since num tasks are max 26 we can say it's O(n)
     * Space = O(n) ... an be said as O(1) as freq arr / pq / cooldownTasks will at max have 26 elements
     */
    static int greedy(char[] tasks, int cd) {
        List<Character> res = new ArrayList<>();
        int[] taskFreq = new int[25]; // max 26 capital letters for tasks
        for (char t : tasks) {  // compute task freq
            taskFreq[t-'A']++;
        }

        // build max heap to track most freq task
        PriorityQueue<Task> pq = new PriorityQueue<>((a,b) -> {
            int cmp = b.cnt-a.cnt;
            return cmp == 0 ? a.id-b.id : cmp;  // if same freq, use natural ordering of task ids (default wud have been a.equals(b))
        });
        for (int t = 0; t < 25; t++) {
            if (taskFreq[t] > 0) pq.add(new Task((char)(t+65), taskFreq[t]));
        }
        while (!pq.isEmpty()) {     // run until we got tasks left
            List<Task> cooldownTasks = new ArrayList<>();

            // schedule tasks for each cooldown interval
            for (int i = 0; i <= cd; i++) {
                if (!pq.isEmpty()) {    // if we have a task to schedule for this cooldown interval
                    Task task = pq.poll();
                    res.add(task.id);
                    task.cnt--;               // decr freq
                    if (task.cnt > 0) cooldownTasks.add(task);  // add newly scheduled task to cooldown
                } else {    // otherwise idle time
                    res.add('_');
                }
            }

            // add back cooldown tasks to heap for next scheduling iter
            pq.addAll(cooldownTasks);
        }
        // boundary case - the last scheduled interval may have '_' which can be removed eg. AB_ => AB
        int i = res.size();
        while (res.get(--i) == '_') res.remove(i);

        // print final schedule
        System.out.println(res);
        return res.size();
    }

    static class Task {
        char id;
        int cnt;

        public Task(char id, int cnt) {
            this.id = id;
            this.cnt = cnt;
        }
    }

}
