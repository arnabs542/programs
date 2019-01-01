package com.raj.hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author rshekh1
 */
public class Diffk {
    /**
     * Given an array A of integers and another non negative integer k,
     * find if there exists 2 indices i and j such that A[i] - A[j] = k, i != j.
     *
     * Input :
     * A : [1 5 3], k : 2
     * Output : 1, as 3 - 1 = 2
     */
    public static void main(String[] args) {
        System.out.println(diffk_n2(new int[]{6,2,8,11,9,1}, 3));
        System.out.println(diffk_n(new int[]{6,2,8,11,9,1}, 3));
        System.out.println(diffPossible(new int[]{6,2,8,11,9,1}, 3));

        System.out.println();
        System.out.println(diffk_n2(new int[]{1,3,2}, 0));
        System.out.println(diffk_n(new int[]{1,3,2}, 0));   // doesn't work
        System.out.println(diffPossible(new int[]{1,3,2}, 0));  // works always

        System.out.println();
        System.out.println(diffk_n2(new int[]{16, 36, 29, 4, 45, 80, 86, 53, 37, 39, 78, 40, 80, 64, 44, 35, 73, 48, 64, 82, 46, 97, 75, 26, 83, 20, 9, 23, 2, 20, 74, 96, 78, 27, 28, 68, 99, 5, 24, 98, 26, 56, 40, 26, 93, 97, 93, 82, 40, 46, 13, 11, 25, 9, 20, 39, 79, 45, 65, 76, 31}, 67));
        System.out.println(diffk_n(new int[]{16, 36, 29, 4, 45, 80, 86, 53, 37, 39, 78, 40, 80, 64, 44, 35, 73, 48, 64, 82, 46, 97, 75, 26, 83, 20, 9, 23, 2, 20, 74, 96, 78, 27, 28, 68, 99, 5, 24, 98, 26, 56, 40, 26, 93, 97, 93, 82, 40, 46, 13, 11, 25, 9, 20, 39, 79, 45, 65, 76, 31}, 67));
    }

    private static boolean diffk_n2(int[] a, int k) {
        if (a == null || a.length == 0) return false;
        Arrays.sort(a); // sort
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[j] - a[i] == k) {
                    System.out.println(a[j] + "-" + a[i]);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Algo:
     * B - A = k
     * => B = k + A
     *
     * iterate array, map (k+A) -> B
     * iterate array, find if k+A is present and get A,B
     *
     */
    private static boolean diffk_n(int[] A, int k) {
        if (A == null || A.length == 0) return false;
        Arrays.sort(A); // sort
        Map<Integer,Integer> map = new HashMap<>();
        for (int a : A) {
            if (!map.containsKey(k+a)) map.put(k+a, a);
        }
        for (int a : A) {
            if (map.containsKey(a)) {
                System.out.println(a + "-" + map.get(a));
                return true;
            }
        }
        return false;
    }

    // iterate once and find B-k if seen previously
    public static boolean diffPossible(final int[] a, int b) {
        HashSet<Integer> visited = new HashSet<>();
        for (Integer number : a) {
            /*if (visited.contains(number + b)) { // do we need this?
                System.out.println(number + "," + number+b);
                return true;
            }*/
            if (visited.contains(number - b)) {
                System.out.println(number + "," + (number-b));
                return true;
            }
            visited.add(number);
        }
        return false;
    }

}
