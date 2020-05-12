package com.raj.sorting;

import java.util.*;

/**
 * @author rshekh1
 */
public class Sum3 {

    /**
     * Find triplets whose sum is zero. Return unique triplets only.
     * ie. if A+B=C, then print A,B,C
     *
     * Also asked as find triplets that add upto a target sum ie. A+B+C=T
     */
    public static void main(String[] args) {
        System.out.println(findTripletsZeroSum(new int[]{0,-1,2,-3,1}));
        System.out.println(findTripletsZeroSum(new int[]{-2,2,0,-2,2}));
        System.out.println(findTripletsZeroSum(new int[]{10,3,-4,1,-6,9}));

        System.out.println(findTripletsTargetSum(new int[]{4,2,3,5,1}, 10));
        System.out.println(findTripletsTargetSum_2ptr(new int[]{4,2,3,5,1}, 10));
        System.out.println(findTripletsTargetSum_2ptr(new int[]{0,-1,2,-3,1}, 0));
    }

    /**
     * # Brute:
     * Fix i, find pair j,k which add upto 0
     * Time = O(n^3), Space = O(1)
     *
     * # HashMap:
     * Similar to 2 Sum problem where we find A + B = T (we see A, put T-A in set, find a B which is in set).
     * Here, we have to find A + B + C = 0 which also means A + B = -C
     * So, we find a pair which adds upto -C (now it becomes similar to 2 sum problem).
     * For each element i in arr, set target sum T as -(arr[i])
     * Using map, find it's pair which adds upto target sum above ie. start with an A, push T-A into set, move forward & check if its in set.
     *
     * Time = O(n^2), Space = O(n) for map
     */
    static Set<String> findTripletsZeroSum(int[] arr) {
        if (arr == null || arr.length < 3) return null;

        Set<String> res = new HashSet<>(); // eliminates dupes in res
        List<Integer> resTmp = new ArrayList<>();

        // Iterate and for each i, check if a pair adds upto -i in O(n) time using HashMap
        // O(n^2) runtime, O(n) space
        for (int i=0; i<arr.length; i++) {  // O(n)

            Map<Integer,Integer> map = new HashMap<>();
            int targetSum = -1*arr[i];  // T

            for (int j=0; j<arr.length; j++) {  // our A
                if (j == i) continue; // skip same pos
                if (map.containsKey(arr[j])) {  // T-A exists, print
                    resTmp.clear();
                    resTmp.add(-1*targetSum);
                    resTmp.add(arr[j]);
                    resTmp.add(map.get(arr[j]));
                    Collections.sort(resTmp);
                    res.add(resTmp.get(0) + "," + resTmp.get(1) + "," + resTmp.get(2));
                } else {  // otherwise push T-A
                    map.put(targetSum - arr[j], arr[j]);
                }
            }
        }
        return res;
    }

    /**
     * Generalizing above, A+B+C = T     ... 3 vars
     * we see an A, define T' as T-A     ... reduced to 2 vars
     *   create set
     *   we see B, add to set T'-B       ... reduced to 1 var
     *   print if B is already in set
     *
     * Time = O(n^2), Space = O(n)
     */
    static Set<List<Integer>> findTripletsTargetSum(int[] A, int K) {
        if (A == null || A.length < 3) return null;
        Set<List<Integer>> res = new HashSet<>();

        for (int i = 0; i < A.length; i++) {
            // we see A, def T' as T-A
            int T = K-A[i];
            Map<Integer,Integer> map = new HashMap<>();
            for (int j = 0; j < A.length; j++) {
                if (j == i) continue;
                // we see B, add to set T'-B & check it later for answer
                if (!map.containsKey(A[j])) map.put(T-A[j], A[j]);
                else {
                    Integer[] tmp = new Integer[3];
                    tmp[0] = A[i]; tmp[1] = A[j]; tmp[2] = map.get(A[j]);
                    Arrays.sort(tmp);
                    res.add(Arrays.asList(tmp));
                }
            }
        }
        return res;
    }

    /**
     * Optimal: Sort + 2 Ptr iter
     * # Sort the arr
     * # we see A, T' = T-A
     * # l = i+1, r = len-1, find left+right = T' ie. B+C=T'
     *   - if sum is less, incr l
     *   - if sum is greater decr r
     *   - if equal then print
     *
     * Time = O(nlogn + n^2), Space = O(1)
     */
    static Set<List<Integer>> findTripletsTargetSum_2ptr(int[] A, int K) {
        if (A == null || A.length < 3) return null;
        Set<List<Integer>> res = new HashSet<>();

        Arrays.sort(A);

        for (int i = 0; i < A.length; i++) {
            int T = K-A[i];  // we see A, def T' as T-A
            int left = i+1;  // seek after A
            int right = A.length-1;
            while (left < right) {  // adjust left,right until left+right=T'
                int sum = A[left]+A[right];
                if (sum < T) left++;
                else if (sum > T) right--;
                else {
                    res.add(Arrays.asList(A[i], A[left], A[right]));
                    left++; right--;  // don't break as there may be other pairs
                }
            }
        }
        return res;
    }

}
