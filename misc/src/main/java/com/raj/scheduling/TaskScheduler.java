package com.raj.scheduling;

import com.google.common.collect.Lists;

import java.util.*;

public class TaskScheduler {
    /**
     * Say you are building an application to assemble a robot. A robot consists of list of pieces that you
     * need to assemble in order. Each piece has zero or more dependencies on another piece.
     * For example, if piece B has a dependency on piece A, then A must be assembled before B.
     * In this problem, you are given a list of pieces along with their list of dependencies,
     * and your goal should be to print a valid order to assemble the pieces.

     For example, given:
     A -> []
     B -> [A]
     C -> [B]
     => [A,B,C]

     A -> [B]
     B -> []
     C -> [A]
     D -> [B,C]
     => B,A,C,D

     */

    public static void main(String args[] ) {
        Map<String, List<String>> tasksMap = new HashMap<>();
        tasksMap.put("A", Lists.newArrayList("B"));
        tasksMap.put("B", Lists.newArrayList(""));
        tasksMap.put("C", Lists.newArrayList("A", "B"));
        tasksMap.put("D", Lists.newArrayList("B", "C"));
        System.out.print("\n" + tasksMap + " => "); assemble(tasksMap);

        tasksMap.clear(); completed.clear();
        tasksMap.put("A", Lists.newArrayList("B"));
        tasksMap.put("B", Lists.newArrayList("C"));
        tasksMap.put("C", Lists.newArrayList("A", "B"));
        tasksMap.put("D", Lists.newArrayList("B", "C"));
        System.out.print("\n" + tasksMap + " => "); assemble(tasksMap);
    }

    private static void assemble(Map<String, List<String>> tasksMap) {
        Stack<String> depStack = new Stack<>();     // recursionStack keeps track of dependencies as we dfs
        for (String key : tasksMap.keySet()) {      // iterate over all parts
            depStack.add(key);
            assembleRecursively(depStack, tasksMap);
        }
    }

    private static Set<String> completed = new HashSet<>(); // keeps track of completed tasks

    // Greedily DFS until we hit end for this task & all it's dependency
    private static void assembleRecursively(Stack<String> depStack, Map<String, List<String>> tasksMap) {
        if (depStack.isEmpty()) return;         // base case

        String t = depStack.peek();             // take the next task
        // if we hit end or is already completed, just print recursionStack contents
        if (tasksMap.get(t).isEmpty() || tasksMap.get(t).get(0).isEmpty() || completed.contains(t)) {
            printStack(depStack);
        } else {
            tasksMap.get(t).forEach(x -> {      // else add all dependencies for task t
                addDependencies(x, depStack);
            });
        }
        assembleRecursively(depStack, tasksMap);    // now recurse until depTask is emptied
    }

    private static void printStack(Stack<String> depStack) {
        while (!depStack.isEmpty()) {       // print recursionStack as this dfs is done
            String done = depStack.pop();
            if (completed.add(done)) System.out.print(done + ", ");  // only print tasks that weren't already completed
        }
    }

    private static void addDependencies(String dep, Stack<String> depStack) {
        if (depStack.contains(dep)) {     // a cycle is detected if the original task is dependent on itself
            System.out.print("Cycle detected !!");
            System.exit(0);      // just exit, as we can't solve
        }
        depStack.add(dep);
    }




}
