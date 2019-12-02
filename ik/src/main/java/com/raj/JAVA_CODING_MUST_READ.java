package com.raj;

import java.util.*;

/**
 * @author rshekh1
 */
public class JAVA_CODING_MUST_READ {

    public static void main(String[] args) {

        // =========== Array Gotchas =========
        int[] A = new int[] {1, 2, 3, 4, 5};  // Init array
        print(Arrays.toString(A));  // print an array

        int [][] B = new int[][] { {1,2}, {3,4} };  // Init 2D array
        print(Arrays.deepToString(B)); // print a 2D array

        // When dealing w/ Arrays, never forget to handle boundary checks, especially in recursion / while loops

        // Create any type list with no fuss
        List<String> strList = Arrays.asList("aStr","anotherStr");
        print(strList);

        // Convert to Array from List
        String[] arr = strList.toArray(new String[strList.size()]);

        List<Integer> intList = Arrays.asList(1,2,3,4);
        print(intList);
        List<String> strList1 = Arrays.asList("abc","def","ghf");

        print(Arrays.binarySearch(A, 2)); // binary search an array

        System.out.println("0123456789".substring(2, 5)); // 234 & not 2345 ... add +1 to endIdx

        // Pick a random pivot
        int start = 0, end = 5;
        Random random = new Random();
        int pivotIdx = start + random.nextInt(end - start + 1); // add +1 to endIdx

        // Array copy - get an in place O(1) time & space array view of elems A[1,2,3]
        Arrays.copyOfRange(A, 1, 3+1);  // add +1 to endIdx

        // ============ LOOP GOTCHAS ============
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


        // ============= OVERFLOW GOTACHAS ===========
        // why modulo w/ prime number? just to keep values not going negative, at least will be +ve
        int i = 2_147_483_646;       // int max limit is 2_147_483_647
        print(i+2);                  // goes negative
        print((i % 1000000007) + 9); // still +ve w/ some lossy operation
        // https://www.geeksforgeeks.org/modulo-1097-1000000007/


        // ============= SORT / COMPARE / HASHCODE GOTCHAS ===========
        // Using COMPARATORS - Sort an array or list using lambda comparators
        List<List<Integer>> list = new ArrayList<>();
        Collections.sort(list, (a,b) -> {
            if (a.get(0) == b.get(0)) return a.get(2).compareTo(b.get(2)); // or return a.get(2) - b.get(2)
            else return a.get(0).compareTo(b.get(0));
        });

        // Init a MAX HEAP using lambda comparator
        PriorityQueue<Integer> heightPQ = new PriorityQueue<>((a,b) -> b-a);

        // Define max heap comparator in more detail eg. for decimals '-' won't work as it expects int only
        PriorityQueue<Point> pq = new PriorityQueue<>((a,b) -> {
            if (b.dist < a.dist) return -1;
            if (b.dist == a.dist) return 0;
            else return 1;  // or just use Double.compare(b.dist,a.dist);
        });
        // class Point implements Comparable<Point> { public int compareTo(Point p){...} } --> same effect

    }

    /**
     * https://www.geeksforgeeks.org/comparable-vs-comparator-in-java/
     * If sorting of objects needs to be based on one generic attribute then use Comparable whereas
     * If you sorting needs to be done on multiple attributes of different objects, then use Comparator
     * e.g. sort on attribute name vs rating
     *
     */
    static class Point implements Comparable<Point> {      // Define a Comparable at Object level itself. Comparators give more flexibility
        List<Integer> pt;
        double dist;
        public Point(List<Integer> _pt, long _dist) {
            pt = _pt; dist = _dist;
        }

        @Override
        public int compareTo(Point o) {
            /**
             * Notice the comparison using Double.compare
             * Use aString.compareTo for lexicographical order (takes care of varying length)
             */
            return Double.compare(this.dist, o.dist); // compares decimals effectively. Use String.compare
        }

        /**
         * ALWAYS OVERRIDE custom objects when using it with HASHMAP / HASHSET etc. They use hashes on key for :
         * First, find out the right bucket using hashCode()
         * Secondly, search the bucket for the right element using equals(), if multiple hashes go to same bucket
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return Double.compare(point.dist, dist) == 0;
        }

        /**
         * It takes O(L) time to calculate hashcode of key or to compare two strings up to L characters long.
         * Thus, populating/retrieving n words will take O(n*L) runtime.
         */
        @Override
        public int hashCode() {
            return Objects.hash(dist);
        }

        @Override
        public String toString() {
            return pt.toString();
        }
    }


    static void print(Object o) {
        System.out.println(o);
    }

}
