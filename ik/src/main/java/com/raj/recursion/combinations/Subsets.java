package com.raj.recursion.combinations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class Subsets {

    // Print ALL possible subsets = Print all 1 char, 2 char, 3 char.... combinations

    public static void main(String[] args) {
        //printSubsets_ExtraMem(Arrays.asList(1,2,3,4), new ArrayList());
        printSubsets(Arrays.asList(1,2,3,4), new ArrayList());

        List result = new ArrayList<>();
        getSubsets(Arrays.asList(1,2,3,4), 0, new Stack(), result);
        System.out.println(result);
    }

    /**
     * Print all subsets of a set S (with n distinct integers)
     *
     *                      1234
     *                      /    \
     *                  1,234      x,234       ...2 leaf  (2x branching factor)
     *               /    \         /   \
     *            12,34  1,34     2,34  x,34   ...4 leaf
     *
     * => 2^n leaf nodes, which will print the nodes, hence that's our time complexity = O(n.2^n), n to print slate if printing does iterations
     * Time complexity of the above solution is exponential. The problem is in-fact NP-Complete (There is no known polynomial time solution for this problem)
     */
    // creates copies of the List everytime O(2^n) extra space
    static void printSubsets_ExtraMem(List rem, List soFar) {
        if (rem.isEmpty()) {
            System.out.println(soFar);
            return;
        }

        // exclude i
        printSubsets_ExtraMem(new ArrayList<>(rem.subList(1, rem.size())), soFar);

        // include i
        List _soFar = new ArrayList<>(soFar);_soFar.add(rem.get(0));
        printSubsets_ExtraMem(new ArrayList<>(rem.subList(1, rem.size())), _soFar);
    }

    // O(n) extra space for the slate, we use the same slate O(n) (add/erase elems) for the DFS call stack till printing
    static void printSubsets(List rem, List slate) {
        if (rem.isEmpty()) {
            System.out.println(slate);
            return;
        }

        // exclude i
        printSubsets(new ArrayList<>(rem.subList(1, rem.size())), slate);  // Extra space for arrayList? subList doesn't create array copy but just creates a view

        // include i
        slate.add(rem.get(0));  // mutate slate by adding i
        printSubsets(new ArrayList<>(rem.subList(1, rem.size())), slate);
        slate.remove(0);  // reset back to original slate, remember each frame wud have reverted state as well
    }

    // Same as above, except that it uses ptr to track array index. It stores all results, hence incurs O(2^n) space
    static void getSubsets(List rem, int i, Stack slate, List result) {   // O(n) slate extra space as we don't create copies, instead use the same slate (add/erase elems)
        if (i >= rem.size()) {
            result.add(new ArrayList<>(slate)); // adds extra space
            return;
        }

        // exclude i
        getSubsets(rem, i+1, slate, result);  // subList doesn't create array copy but just creates a view

        // include i
        slate.push(rem.get(i));  // mutate slate by adding i
        getSubsets(rem, i+1, slate, result);
        slate.pop();  // reset back to original slate, remember each frame wud have reverted state as well
    }

}
