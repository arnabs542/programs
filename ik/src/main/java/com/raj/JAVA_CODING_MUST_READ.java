package com.raj;

import java.util.Arrays;
import java.util.List;

/**
 * @author rshekh1
 */
public class JAVA_CODING_MUST_READ {

    public static void main(String[] args) {
        /* Array Gotchas */
        int[] A = new int[] {1, 2, 3};  // Init array
        print(Arrays.toString(A));  // print an array

        int [][] B = new int[][] { {1,2}, {3,4} };  // Init 2D array
        print(Arrays.deepToString(B)); // print a 2D array

        // Create any type list with no fuss
        List<String> strList = Arrays.asList("aStr","anotherStr");
        print(strList);

        List<Integer> intList = Arrays.asList(1,2,3,4);
        print(intList);

        print(Arrays.binarySearch(A, 2)); // binary search an array

        // Use while loop when u want full control over incrementing/decrementing index

        // why mod? just to keep values not going negative, at least will be +ve
        int i = 2_147_483_646;       // int max limit is 2_147_483_647
        print(i+2);                  // goes negative
        print((i % 1000000007) + 9); // still +ve w/ some lossy operation
    }

    static void print(Object o) {
        System.out.println(o);
    }

}
