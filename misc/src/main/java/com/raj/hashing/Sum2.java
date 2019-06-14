package com.raj.hashing;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rshekh1
 */
public class Sum2 {

    /**
     * Given an array of integers, find two numbers such that they add up to a specific target number
     * O(n)
     */
    public static void main(String[] args) {
        int[] a = {6,4,2,8,5,7}; int target = 9;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (map.containsKey(a[i])) {
                System.out.println(a[i] + "," + map.get(a[i]));
            }
            map.put(target - a[i], a[i]);
        }
    }

}
