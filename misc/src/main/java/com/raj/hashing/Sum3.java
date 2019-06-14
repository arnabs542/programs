package com.raj.hashing;

import java.util.*;

/**
 * @author rshekh1
 */
public class Sum3 {

    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }

    static Set<List<Integer>> res = new HashSet<>();
    // O(n^2)
    public static List<List<Integer>> threeSum(int[] A) {
        for (int i=0;i<A.length;i++) {
            findABEqualsT(i, 0-A[i], A);
        }
        return new ArrayList<>(res);
    }

    public static void findABEqualsT(int i, int T, int[] A) {
        //System.out.println(i + "," + T);
        Set<Integer> targetSet = new HashSet<>();
        for (int j=0; j<A.length; j++) {
            if (j == i) continue;   // duplicates not allowed
            //System.out.println("Set => " + targetSet);
            if (targetSet.contains(A[j])) {
                List<Integer> list = new ArrayList<>();
                list.add(A[i]);list.add(A[j]);list.add(T-A[j]);
                Collections.sort(list);
                res.add(list);
            } else targetSet.add(T-A[j]);
        }
    }

}
