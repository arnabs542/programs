package com.raj.sorting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rshekh1
 */
public class DutchFlag {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(dutch_flag_sort(new char[] { 'G', 'B', 'G', 'G', 'R', 'B', 'R', 'G'})));
    }


    static Map<Character,Integer> map = new HashMap<>();

    public static char[] _dutch_flag_sort(char[] balls) {

        if (balls == null || balls.length == 0) return balls;

        for (char c : balls) {
            if (!map.containsKey(c)) map.put(c, 1);
            else {
                map.put(c, map.get(c) + 1);
            }
        }

        // arrange as RGB
        add(balls, 'R');
        add(balls, 'G');
        add(balls, 'B');
        return balls;
    }

    static int k = 0;   // ptr for result array

    static void add(char[] balls, char c) {
        if (!map.containsKey(c)) return;
        int color = map.get(c);
        //System.out.println(c + "->" + color);
        while(color > 0) {
            balls[k++] = c;
            color --;
        }
    }

    public static char[] dutch_flag_sort(char[] A) {

        if (A == null || A.length == 0) return A;

        // create 0...R.....G.......B...n partitions by having r & g ptrs at start while b at the end
        int r = -1, b = A.length;
        for (int g = 0; g < b; g++) {
            if (A[g] == 'R') {  // swap with R ptr
                swap(A, ++r, g);
            } else if (A[g] == 'B') {   // swap with B ptr
                swap(A, --b, g);

                // IMPORTANT - edge case where G ptr finds Red ball & has to be swapped again with R ptr
                if (A[g] == 'R') swap(A, ++r, g);
            }
        }
        return A;
    }

    static void swap(char[] A, int i, int j) {
        if (i == j) return;
        char t = A[i];
        A[i] = A[j];
        A[j] = t;
    }

}
