package com.raj.recursion.permutations;

import com.raj.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class Permute {

    public static void main(String[] args) {
        permute(new int[]{1,2,3}, 0, new Stack<>());  // no repetitions of elements in input array
        permute(new int[]{1,2,2}, 0, new Stack<>());  // repetitions yields dupe results

        System.out.println("\nWith repetitions....");
        permuteRep(new int[]{1,2,1}, 0, new Stack<>());  // input array has repeating elems, but algo takes care of not printing dupe results

        System.out.println("\nWith repetitions & optimized solution....");
        permuteRepOptimized(new int[]{1,2,3,2}, 0);
    }

    /**
     *                123
     *            /    /     |
     *        1,23   2,13   3,12     n leaf  = branching factor
     *      /   |     |  \
     *  12,3  13,2  21,3  23,1  ...  n x n-1 (for each of prev n leaf, there are n-1 more leaf)
     *            => complexity = n!
     *
     */
    // O(n!) time complexity, O(n) extra space
    static void permute(int[] arr, int i, Stack<Integer> slate) {
        if (i == arr.length) {
            System.out.println(slate);
            return;
        }

        // doesn't create copies while recursion
        for (int j = i; j < arr.length; j++) {
            // arr - > 0...i-1 | i....n  , ignore until i-1 as it's done
            Util.swap(arr, i, j);   // swap & bring j to front
            slate.push(arr[i]);      // add to slate
            permute(arr, i+1, slate);    // now recurse on remaining which is i+1 th

            // revert
            slate.pop();
            Util.swap(arr, i, j);
        }
    }

    /**
     *                121
     *            /    /     |
     *        1,21   2,11   1,12 (same as 1,21) --> prune it here so as to not recurse unnecessarily
     *      /   |     |  ......
     */
    static void permuteRep(int[] arr, int i, Stack<Integer> slate) {
        if (i == arr.length) {
            System.out.println(slate);
            return;
        }

        Set<Integer> set = new HashSet<>(); // maintain a set for ith elem fan-out & to avoid repetitive branching (linear space)

        // doesn't create copies while recursion w/ ptr
        for (int j = i; j < arr.length; j++) {

            if (set.contains(arr[j])) continue;     // we have already done work for this

            // arr - > 0...i-1 | i....n  , ignore until i-1 as it's done
            Util.swap(arr, i, j);   // swap & bring j to front
            slate.push(arr[i]);      // add to slate
            permuteRep(arr, i+1, slate);    // now recurse on remaining which is i+1 th

            // revert
            slate.pop();
            Util.swap(arr, i, j);

            set.add(arr[j]);    // add this as done ...
        }
    }

    /**
     * ==== MOST OPTIMIZED SOLUTION ====
     * Same as above but with a optimization of removing O(n) space for slate.
     * We don't need it, just print the damn arr before swap revert
     * https://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
     */
    static void permuteRepOptimized(int[] arr, int i) {
        if (i == arr.length) {
            System.out.println(Arrays.toString(arr));   // the arr is already what we want to print
            return;
        }

        Set<Integer> set = new HashSet<>(); // maintain a set for ith elem fan-out & to avoid repetitive branching (linear space)

        // doesn't create copies while recursion w/ ptr
        for (int j = i; j < arr.length; j++) {

            if (set.contains(arr[j])) continue;     // we have already done work for this

            // Permute with ith char FIXED
            Util.swap(arr, i, j);   // swap & bring j to front
            permuteRepOptimized(arr, i+1);    // now recurse on remaining which is i+1 th
            Util.swap(arr, i, j);   // revert swap

            set.add(arr[j]);    // add this as done ...
        }
    }

}
