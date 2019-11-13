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

    public static void swap(char[] A, int i, int j) {
        if (i == j) return;
        char tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    public static String reverseStr(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length()-1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public static boolean isPalin(String s) {
        if (s == null || s.isEmpty()) return true;
        if (s.length() == 1) return true;
        for (int i = 0, j=s.length()-1; i < j; i++,j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
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
