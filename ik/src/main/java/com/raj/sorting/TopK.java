package com.raj.sorting;

import java.util.*;

/**
 * @author rshekh1
 */
public class TopK {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(topK(new int[] {
                4,
                1,
                8,
                1,
                9,
                10,
                3,
                9,
                4,
                4,
                2,
                5,
                7,
                1,
                3,
                5}, 8)));
    }

    /**
     * You are given an array of integers arr, of size n, which is analogous to a continuous stream of integers input.
     * Your task is to find K largest elements from a given stream of numbers.
     *
     * By definition, we don't know the size of the input stream. Hence, produce K largest elements seen so far,
     * at any given time. For repeated numbers, return them only once.
     *
     * If there are less than K distinct elements in arr, return all of them.
     * arr = [1, 5, 4, 4, 2]; K = 2
     * o/p [4, 5]
     *
     * arr = [1, 5, 1, 5, 1]; K = 3
     * o/p [5, 1]
     */

    static PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    static Set<Integer> set = new HashSet<>(); // keep track of dupes

    static int[] topK_(int[] arr, int k) {   // O(n log k)

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (minHeap.size() < k) {
                add(arr[i]);   // add elements until heap reaches size k
                continue;
            }
            if (arr[i] > minHeap.peek()) {  // element is of interest
                if (add(arr[i])) remove();  // try adding the larger element & then only remove
            }
        }

        // finally pop top k elements
        while(!minHeap.isEmpty()) {
            int n = minHeap.remove();
            res.add(n);
        }
        int[] resArr = new int[res.size()];
        for (int j=0; j<res.size(); j++) resArr[j] = res.get(j);
        return resArr;
    }

    static boolean add(int n) {
        if (set.contains(n)) return false;  // don't add dupes to the heap
        minHeap.add(n);
        set.add(n);
        return true;
    }

    static void remove() {
        int n = minHeap.remove();
        set.remove(n);
    }


    /*
     * A simpler solve with k sized TreeSet (eliminates dupes + tracks min + already sorted collection)
     * Time Complexity: O(N*log(K))
     * Space Complexity: O(K)
     */
    static int[] topK(int[] arr, int k) {
        // TreeSet will maintain set of elements in a sorted fashion
        TreeSet<Integer> tree = new TreeSet<>();
        /*
        We will add all elements to the sorted set and when size of the set increases over
        required size K, we will remove the smallest element
        */
        for (int x : arr) {
            tree.add(x);
            if (tree.size() > k) {
                tree.pollFirst();
            }
        }
        int ans[] = new int[tree.size()];
        int ptr = 0;
        for (int x : tree) {
            ans[ptr++] = x;
        }
        return ans;
    }

}
