package com.raj.sorting;

import java.util.*;

/**
 * @author rshekh1
 */
public class Sum3 {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(findZeroSum(new int[]{0,0,0})));
        System.out.println(Arrays.toString(findZeroSum(new int[]{-2, 2, 0, -2, 2})));
        System.out.println(Arrays.toString(findZeroSum(new int[]{10, 3, -4, 1, -6, 9})));
    }
    /*
     * Complete the function below.
     */
    static String[] findZeroSum(int[] arr) {
        // Write your code here.
        if (arr == null || arr.length < 3) return null;

        Set<String> res = new HashSet<>(); // eliminates dupes in res
        List<Integer> resTmp = new ArrayList<>();
        // Iterate and for each i, check if a pair adds upto -i in O(n) time using HashMap
        // O(n^2) runtime, O(n^2) space
        for (int i=0; i<arr.length; i++) {  // O(n)
            Map<Integer,Integer> map = new HashMap<>();
            int targetSum = -1*arr[i];
            for (int j=0; j<arr.length; j++) {  // O(n)
                if (j == i) continue; // skip same pos
                if (map.containsKey(arr[j])) {  // found pair
                    resTmp.clear();
                    resTmp.add(-1*targetSum);
                    resTmp.add(arr[j]);
                    resTmp.add(map.get(arr[j]));
                    Collections.sort(resTmp);
                    res.add(resTmp.get(0) + "," + resTmp.get(1) + "," + resTmp.get(2));
                } else {
                    map.put(targetSum - arr[j], arr[j]);
                }
            }
        }
        String[] resStr = new String[res.size()];
        int k = 0; for (String t:res) resStr[k++] = t;
        return resStr;
    }

}
