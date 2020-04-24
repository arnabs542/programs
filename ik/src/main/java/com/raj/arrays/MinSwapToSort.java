package com.raj.arrays;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MinSwapToSort {
    /**
     * https://www.geeksforgeeks.org/minimum-number-of-swaps-required-to-sort-an-array-set-2/
     * Minimum number of swaps required to sort an array
     * Given an array of N distinct elements, find the minimum number of swaps required to sort the array.
     *
     * Input : arr[] = {4, 3, 2, 1}
     * Output : 2
     * Explanation : Swap index 0 with 3 and 1 with 2 to get the sorted array {1, 2, 3, 4}.
     *
     * Input : arr[] = {3, 5, 2, 4, 6, 8}
     * Output : 3
     */
    public static void main(String[] args) {
        System.out.println(minSwaps(new int[]{2,1,3,0}));
        System.out.println(minSwaps(new int[]{3,5,2,4,6,8}));
        System.out.println(minPermutation(new int[]{3,1,2}));
        System.out.println(minPermutation(new int[]{1,2,5,4,3}));  // answer = 1
    }

    /**
     * [Cyclic Sort Pattern] Compare the sorted state with original and compute swaps needed
     * The idea is to create a array of pair with first element as array values and second element as array indices.
     * The next step is to sort the array of pair according to the first element of the pair.
     * Traverse the pair array and check if the index mapped with the value is correct or not (for i=0, pair's 2nd val shud match)
     * If not then keep swapping, until the element is placed correctly and keep counting the number of swaps.
     *
     * Example 1:
     * idx = 0 1 2 3
     * arr = 2 1 3 0
     *
     * idx = 3 1 0 2
     * sor = 0 1 2 3
     *
     *       2     3
     *       3 ... 0   swap 0th with 3rd as sor[0].idx != 0
     *
     *       0 1 2 3
     *       2 1 3 0  swap 0th with 2nd. Now we have array in original state. 2 swaps were needed.
     *
     * Example 2:
     * idx = 0 1 2 3 4 5
     * arr = 3 5 2 4 6 8
     *
     * idx = 2 0 3 1 4 5    ... sorted pairs
     * sor = 2 3 4 5 6 8
     *       x -> idx at i=0 is 2, so swap it with 3,4 pair which is at idx=2. Keep doing until idx here becomes 0
     *
     * O(n+nlon)
     */
    static int minSwaps(int arr[]) {
        Pair[] pairs = new Pair[arr.length];
        for (int i=0; i<arr.length; i++) {
            pairs[i] = new Pair(arr[i],i);  // elem -> index
        }

        // sort pairs
        Arrays.sort(pairs, (a,b) -> a.num - b.num);

        // compute swaps by bringing the sorted pairs to original state
        int swaps = 0;
        for (int i = 0; i < arr.length; i++) {
            while (pairs[i].idx != i) {
                int dest = pairs[i].idx;
                Pair tmp = pairs[i];
                pairs[i] = pairs[dest];
                pairs[dest] = tmp;
                swaps++;
            }
        }
        return swaps;
    }

    static class Pair {
        int idx, num;
        Pair(int _n, int _i) {
            idx = _i; num = _n;
        }
    }

    /**
     * Minimizing Permutations
     * In this problem, you are given an integer N, and a permutation, P of the integers from 1 to N, denoted as (a_1, a_2, ..., a_N).
     * You want to rearrange the elements of the permutation into increasing order, repeatedly making the following operation:
     * Select a sub-portion of the permutation, (a_i, ..., a_j), and reverse its order.
     * Your goal is to compute the minimum number of such operations required to return the permutation to increasing order.
     *
     * If N = 3, and P = (3, 1, 2), we can do the following operations:
     * Select (1, 2) and reverse it: P = (3, 2, 1).
     * Select (3, 2, 1) and reverse it: P = (1, 2, 3).
     * output = 2
     *
     * Permute + Minimize? BFS
     *         3 1 2   target = 123
     *         i   j
     *        /   |    \
     *   2 1 3  1 3 2  3 2 1
     *                     \
     *                    1 2 3
     * Time = branching_factor ^ N
     */
    static int minPermutation(int[] A) {
        // permute options, create a target string to reach & compare. BFS wud give min permutations.
        int len = A.length;
        String start = "";
        for (int i:A) start += i + "";
        Arrays.sort(A);
        String target = "";
        for (int i:A) target += i + "";

        Queue<PairStr> queue = new LinkedList<>();
        queue.add(new PairStr(start, 0));
        while (!queue.isEmpty()) {
            System.out.println(queue);
            PairStr pairStr = queue.remove();
            if (pairStr.str.equals(target)) return pairStr.cnt;

            // add 1 level permutes
            int i = 0, j = pairStr.str.length()-1;
            while (i<j) {
                // incorrect for 1,2,5,4,3 - Fix to do reverse(5,4,3) to arrive at 1,2,3,4,5
                queue.add(new PairStr(reverse(pairStr.str, i, j), pairStr.cnt+1));
                if (i+1 < target.length()) queue.add(new PairStr(reverse(pairStr.str, i+1, j), pairStr.cnt+1));
                if (j-1 >= 0)queue.add(new PairStr(reverse(pairStr.str, i, j-1), pairStr.cnt+1));
                i++;
                j--;
            }
        }
        return -1;
    }

    static String reverse(String s, int i, int j) {
        String revStr = "";
        for (int k = j; k >=i; k--) {
            revStr += s.charAt(k);
        }
        return s.substring(0,i) + revStr + s.substring(j+1);
    }

    static class PairStr {
        String str;
        int cnt;
        PairStr(String _s, int _i) {
            str = _s;
            cnt = _i;
        }

        @Override
        public String toString() {
            return str + ":" + cnt;
        }
    }

}
