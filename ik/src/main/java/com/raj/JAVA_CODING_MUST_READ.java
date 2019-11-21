package com.raj;

import java.util.*;

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

        // When dealing w/ Arrays, never forget to handle boundary checks, especially in recursion / while loops

        // Create any type list with no fuss
        List<String> strList = Arrays.asList("aStr","anotherStr");
        print(strList);

        // Convert to Array from List
        strList.toArray(new String[strList.size()]);

        List<Integer> intList = Arrays.asList(1,2,3,4);
        print(intList);

        print(Arrays.binarySearch(A, 2)); // binary search an array

        // Use while loop when u want full control over incrementing/decrementing index
        // VERY IMPORTANT - ALWAYS INCR WHILE LOOP PTRS BEFORE USING IT, OTHERWISE YOU'LL GET INTO EXTREMELY COMPLEX DEBUGGING MESS
        // ESPECIALLY w/ SLIDING WINDOW problems
        int left = -1, right = -1; // INIT before first char
        String s = "ab";
        while (right < s.length()) {
            // expand until some condition
            while (++right < s.length()) { // && some condition until when to stop expansion
                // now use right ptr
            }
            // update maxLen, on some base condition
            // contract until some condition
            while (++left < right) { // && some condition until when to stop contraction
                // now use left ptr
            }
        }

        // why modulo w/ prime number? just to keep values not going negative, at least will be +ve
        int i = 2_147_483_646;       // int max limit is 2_147_483_647
        print(i+2);                  // goes negative
        print((i % 1000000007) + 9); // still +ve w/ some lossy operation

        // Sort an array or list using lambda comparators
        List<List<Integer>> list = new ArrayList<>();
        Collections.sort(list, (a, b) -> {
            if (a.get(0) == b.get(0)) return a.get(2).compareTo(b.get(2)); // or return a.get(2) - b.get(2)
            else return a.get(0).compareTo(b.get(0));
        });

        // Init a MAX HEAP using comparator
        PriorityQueue<Integer> heightPQ = new PriorityQueue<>((a, b) -> b-a);

        // class Point implements Comparable<Point>{ public int compareTo(Point p){...}  --> same effect

        // Ordered Sets  -> TreeSet maintains sorted collection,  LinkedHashSet maintains insertion order

        System.out.println("0123456789".substring(2, 5)); // 234 & not 2345 ... add +1 to endIdx

        // It takes O(L) time to calculate hashcode or to compare two strings up to L characters long. Thus populating the hashmap with n words will take O(n*L), making w searches in that hashmap will take O(w*L)
    }

    static void print(Object o) {
        System.out.println(o);
    }

}
