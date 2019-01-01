package com.raj.hashing;

import com.google.common.collect.Maps;

import java.util.*;

/**
 * @author rshekh1
 */
public class Sum4 {

    /**
     * Find all unique quadruplets in the array which gives the sum of target.
     * Given array S = {1 0 -1 0 -2 2}, and target = 0
     * A solution set is:
     *     (-2, -1, 1, 2)
     *     (-2,  0, 0, 2)
     *     (-1,  0, 0, 1)
     *
     *     Also make sure that the solution set is lexicographically sorted.
     */
    public static void main(String[] args) {
        sum4(new int[]{1, 0, -1, 0, -2, 2}, 0);
        sum4(new int[]{0, 1, 2, 3, 4, 5, 6}, 6);
    }

    /**
     * Algo:
     * A+B+C+D = T
     * => A+B = T-(C+D)
     *
     * Map A+B -> (A,B)
     * Map.find T-(C+D) & check for distinctness
     */
    private static void sum4(int[] a, int target) {
        // store sum -> pairs
        Map<Integer,String> sumPairs = Maps.newHashMap();
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                sumPairs.put(a[i]+a[j], i+","+j);
            }
        }

        TreeSet<List<Integer>> res = new TreeSet<>((o1, o2) -> {        // for lexicographically sorting
            for (int i = 0; i < o1.size(); i++) {
                if (o1.get(i) < o2.get(i)) return -1;
                if (o1.get(i) > o2.get(i)) return 1;
            }
            return 0;
        });

        // find (target - newSum) in map
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                int newSum = a[i]+a[j];
                int remain = target-newSum;
                if (sumPairs.containsKey(remain)) {
                    String[] b = sumPairs.get(remain).split(",");
                    if (i != Integer.parseInt(b[0]) && i != Integer.parseInt(b[1])
                            && j != Integer.parseInt(b[0]) && j != Integer.parseInt(b[0])) {
                        List<Integer> quads = new ArrayList<>();
                        String s = a[i] + "," + a[j] + "," + a[Integer.parseInt(b[0])] + "," + a[Integer.parseInt(b[1])];
                        Arrays.stream(s.split(",")).forEach(x -> quads.add(Integer.valueOf(x)));
                        quads.sort(Comparator.naturalOrder());      // for lexicographically sorting
                        res.add(quads);
                    }
                }
            }
        }
        System.out.println(res);
    }
}
