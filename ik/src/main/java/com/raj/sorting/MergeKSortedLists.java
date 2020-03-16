package com.raj.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author rshekh1
 */
public class MergeKSortedLists {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeArrays(new int[][] {
                {1,3,5,7},{2,4,6,8},{0,9,10,11}   // asc
        })));
        System.out.println(Arrays.toString(mergeArrays(new int[][] {
                {7,5,3,1},{8,6,4,2},{11,10,9,0}   // desc
        })));
        System.out.println(Arrays.toString(mergeArrays(new int[][] {
                {2,2,2,2},{8,8,8,8},{7,7,7,5}     // desc
        })));
    }

    /**
     * Input:
     * 3
     * 4
     * 1. 3. 5. 7.
     * 2. 4. 6. 8.
     * 0. 9. 10. 11.
     * => [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
     *
     * init k sized PQ put first elem from each array, track index also
     * pop top and move next elem from list it belongs
     *
     * Runtime = k sized minHeap O(k + nlogk) = O(nlogk)
     * Space = O(k for PQ + n for res arr)
     */
    static int[] mergeArrays(int[][] arr) {
        List<Integer> res = new ArrayList<>();

        final boolean[] isDesc = new boolean[1];

        // determine ordering
        if (arr[0].length > 1) {
            for (int i=0; i<arr.length; i++) {
                for (int j=1; j<arr[i].length; j++) {
                    if (arr[i][j-1] > arr[i][j]) {
                        isDesc[0] = true;
                        break;
                    }
                }
            }
        }

        PriorityQueue<Element> pq = new PriorityQueue<>((x,y) ->
                isDesc[0] ? y.val-x.val : x.val-y.val);

        // push k elems first
        for (int i=0; i<arr.length; i++) {
            pq.add(new Element(arr[i][0], i, 0));
        }

        while(!pq.isEmpty()) {
            Element e = pq.remove();
            res.add(e.val);
            if (++e.idx < arr[e.arr].length) {
                pq.add(new Element(arr[e.arr][e.idx], e.arr, e.idx));
            }
        }
        return res.stream().mapToInt(x -> x).toArray();
    }

    static class Element {
        int val;
        int arr;
        int idx;

        Element(int _val, int _arr, int _idx) {
            val = _val;
            arr = _arr;
            idx = _idx;
        }

        public String toString() {
            return val + "";
        }
    }

}
