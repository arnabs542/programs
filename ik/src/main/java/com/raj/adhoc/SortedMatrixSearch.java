package com.raj.adhoc;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class SortedMatrixSearch {

    /**
     * Given a 2D matrix where elements are in increasing order going left to right or top to down, find an element
     * 1 2 3 12
     * 4 5 6 45
     * 7 8 9 78
     * 3  -> "present"
     * 6  -> "present"
     * 7  -> "present"
     * 23 -> "not present"
     */
    public static void main(String[] args) {
        Arrays.stream(new int[]{3,6,7,23}).forEach(x -> System.out.println(isPresent(new int[][]{
                {1,2,3,12},
                {4,5,6,45},
                {7,8,9,78},
        }, x)));
    }

    /**
     * Idea is to Binary Search the 2D grid
     * start from rightmost element in first row
     * x == A[row][col] return true
     * x < A[row][col] go left where elems are lesser, ie. col--
     * x > A[row][col] go down where elems are greater, ie. row++
     */
    static String isPresent(int[][] A, int x) {
        int R = A.length; int C = A[0].length;
        int row = 0, col = C-1;

        while(row < R && col >= 0) {
            if (x == A[row][col]) return "present";
            else if (x < A[row][col]) col--;
            else if (x > A[row][col]) row++;
        }
        return "not present";
    }

}
