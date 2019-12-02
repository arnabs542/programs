package com.raj.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountingSort {

    public static void main(String[] args) {
        System.out.println(sort_array(new ArrayList<>(Arrays.asList('c','d','#','b','&','a','z'))));
    }

    public static List<Character> sort_array(List<Character> arr) {
        int[] countArr = new int[256];
        for (char c : arr) {
            countArr[(int) c]++;
        }
        arr.clear();
        for (int i=0;i<256;i++) {
            while (countArr[i]-- > 0) arr.add((char) i);
        }
        return arr;
    }

}
