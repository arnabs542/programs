package com.raj.scheduling;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * @author rshekh1
 */
public class TaskScheduleCoolDown {

    public static void main(String[] args) {
        System.out.println(schedule(Lists.newArrayList("t3", "t3", "t3", "t2", "t2", "t1"), 2));
    }

    /**
     * Schedule optimally with 2 units cool down for each task t3 t3 t3 t2 t2 t1
     * Output: t3 t2 t1 t3 t2 _ t3  (_ denotes, nothing can be scheduled)
     */
    static List<String> schedule(List<String> tasks, int cooldown) {
        List<String> res = new ArrayList<>();
        /**
         * [t3, 3] -> t3 _ _
         * [t2 t3 t1 t2 t3, 2] -> t2 t3 t2 t3 t1
         * Node {String task, cooldown task set}
         *
         * Algo:
         * Sort List of tasks by desc # of occurrences
         * while(!tasks.isEmpty) iterate
         * attach a task which is not in cooldown list, else _
         * for each task added to head
         *  - create 'x'(cooldown) empty nodes and set it's taskid in cooldown list
         *  - delete it from the list
         *
         */
        sortByOccurenceDesc(tasks);
        head = new Node();
        Node node = head;

        while (!tasks.isEmpty()) {

            // find a task not in cooldown list
            String nextTask = "_";
            for (String t : tasks) {
                if (!node.cooldownTasks.contains(t)) {
                    nextTask = t;
                    break;
                }
            }

            // assign to this node, delete from task list, attach cooldowns to the linkedlist
            node.task = nextTask;
            tasks.remove(nextTask);

            Node tmp = node;
            for (int i = 0; i < cooldown; i++) {
                if (tmp.next == null) tmp.next = new Node();
                tmp.next.cooldownTasks.add(nextTask);
                tmp = node.next;
            }
            node = node.next;
        }

        while (head != null) {
            res.add(head.task);
            head = head.next;
        }

        return res;
    }

    static List<String> sortByOccurenceDesc(List<String> tasks) {
        HashMap<String,Integer> map = new HashMap<>();
        for (String t : tasks) {
            if (map.get(t) == null) map.put(t, 0);
            map.put(t, map.get(t)+1);
        }
        List<Map.Entry<String,Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        List<String> res = new ArrayList<>();
        for (Map.Entry<String,Integer> t : list) {
            res.add(t.getKey());
        }
        return res;
    }

    private static class Node {
        String task;
        Set<String> cooldownTasks = new HashSet<>();
        Node next;

        @Override
        public String toString() {
            return task + "!" + cooldownTasks.toString() + "->" + next;
        }
    }
    static Node head;


}
