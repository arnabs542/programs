package com.raj.sorting;

import com.raj.Util;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class PartitionArray {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(partitionEvenOdd(new int[] {2,4,5,3,6,7,8})));
    }

    /**
     * Partition a given array with even elems at start followed by odd elems - no need to sort or sorted array not given
     * Use Lomuto's partitioning from QuickSort - in place, single pass & O(n) algo
     */
    private static int[] partitionEvenOdd(int[] A) {
        int even = -1, odd = 0; // we'll traverse with odd ptr
        for (; odd < A.length; odd++) {
            // if even, then move it to even block
            if (A[odd] % 2 == 0) {
                Util.swap(A, ++even, odd);
            }
        }
        return A;
    }

    /**
     * Dutch national flag - 0,1,2 denotes different colors of the flag. Arrange them such that all similar colors are together
     */
}
