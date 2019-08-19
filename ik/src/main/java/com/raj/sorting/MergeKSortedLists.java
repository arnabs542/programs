package com.raj.sorting;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author rshekh1
 */
public class MergeKSortedLists {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeArrays(new int[][] {
                {1,3,5,7},{2,4,6,8},{0,9,10,11}
        })));
        System.out.println(Arrays.toString(mergeArrays(new int[][] {
                {7,5,3,1},{8,6,4,2},{11,10,9,0}
        })));
        System.out.println(Arrays.toString(mergeArrays(new int[][] {
                {2,2,2,2},{8,8,8,8},{7,7,7,5}
        })));
    }
    /*
    * Complete the mergeArrays function below.
Input:
3
4
1. 3. 5. 7.
2. 4. 6. 8.
0. 9. 10. 11.
=> [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]

k sized minHeap O(k + nlogk) = O(nlogk)
 */
    static int[] mergeArrays(int[][] arr) {

        if (arr == null || arr.length == 0) return null;

        PriorityQueue<E> heap = new PriorityQueue<>();

        // find ordering of k input lists
        boolean isNaturalOrder = findOrdering(arr);

        int[] res = new int[arr.length * arr[0].length]; // result arr
        int k = 0;

        // fill up heap first
        for (int i=0; i<arr.length; i++) {
            add(heap, arr, i, 0, isNaturalOrder);
        }

        // iterate - insert, pop
        while(!heap.isEmpty()) {
            // pop an element and insert next from that list, if that list not empty
            E e = heap.remove();
            res[k++] = isNaturalOrder ? e.n : -1 * e.n;
            if (++e.j < arr[e.i].length) {
                add(heap, arr, e.i, e.j, isNaturalOrder);
            }
        }
        return res;
    }

    static void add(PriorityQueue<E> heap, int[][] arr, int i, int j, boolean isNaturalOrder) {
        int n = arr[i][j];
        if (!isNaturalOrder) n *= -1;
        heap.add(new E(n, i, j));
    }

    // array can be 2,2,2,3 or 3,3,3,3   4,4,2,1 etc
    static boolean findOrdering(int[][] arr) {
        for (int i = 0; i < arr.length; i++)
        for (int j = 1; j < arr[0].length; j++) {
            if (arr[i][j-1] > arr[i][j]) return false;
            if (arr[i][j-1] < arr[i][j]) return true;
        }
        return true;
    }

    static class E implements Comparable<E> {
        public int n, i, j;
        public E(int a, int b, int c) {
            n = a;
            i = b;
            j = c;
        }
        @Override
        public int hashCode() {
            return n;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            E e = (E) o;
            return n == e.n &&
                    i == e.i &&
                    j == e.j;
        }

        @Override
        public String toString() {
            return n + "[" + i + "," + j + "]";
        }

        @Override
        public int compareTo(E o) {
            if (n == o.n) return 0;
            if (n < o.n) return -1;
            else return 1;
        }
    }

}
