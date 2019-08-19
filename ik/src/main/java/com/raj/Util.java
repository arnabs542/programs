package com.raj;

import java.util.StringJoiner;

/**
 * @author rshekh1
 */
public class Util {

    public static void swap(int[] A, int i, int j) {
        if (i == j) return;
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    public static class Point {
        public int x,y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", "[", "]")
                    .add("x=" + x)
                    .add("y=" + y)
                    .toString();
        }
    }


}
