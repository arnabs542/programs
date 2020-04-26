package com.raj.sorting;

import java.util.*;

public class DistinctTriangle {

    /**
     * Given a list of N triangles with integer side lengths, determine how many different triangles there are.
     * Two triangles are considered to be the same if they can both be placed on the plane such that their vertices
     * occupy exactly the same three points.
     * triangles = [[2, 2, 3], [3, 2, 2], [2, 5, 6]]
     * output = 2
     *
     * triangles = [[5, 8, 9], [5, 9, 8], [9, 5, 8], [9, 8, 5], [8, 9, 5], [8, 5, 9]]
     * output = 1
     */
    public static void main(String[] args) {
        ArrayList<Sides> arr_1 = new ArrayList<>();
        arr_1.add(new Sides(7, 6, 5));
        arr_1.add(new Sides(5, 7, 6));
        arr_1.add(new Sides(8, 2, 9));
        arr_1.add(new Sides(2, 3, 4));
        arr_1.add(new Sides(2, 4, 3));
        System.out.println(countDistinctTriangles(arr_1));  // 3

        ArrayList<Sides> arr_2 = new ArrayList<>();
        arr_2.add(new Sides(3, 4, 5));
        arr_2.add(new Sides(8, 8, 9));
        arr_2.add(new Sides(7, 7, 7));
        System.out.println(countDistinctTriangles(arr_2));  // 3
    }

    static class Sides {   // given & can't modify
        int a;
        int b;
        int c;
        Sides(int a,int b,int c){
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    // Create SidesWrapper for equals comparison & hashset addition later
    static class SidesWrapper extends Sides {
        public SidesWrapper(int a, int b, int c) {
            super(a,b,c);
        }

        @Override
        public boolean equals(Object o) {
            SidesWrapper other = (SidesWrapper) o;
            return (this.a == other.a && this.b == other.b && this.c == other.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.a, this.b, this.c);
        }
    }

    static int countDistinctTriangles(ArrayList<Sides> arr) {

        // sort each list entry containing sides in asc order
        List<SidesWrapper> list = new ArrayList<>();
        arr.forEach(x -> {
            int[] A = new int[]{x.a,x.b,x.c};
            Arrays.sort(A);
            list.add(new SidesWrapper(A[0], A[1], A[2]));
        });

        // push them into set
        Set<SidesWrapper> set = new HashSet<>();
        list.forEach(x -> set.add(x));

        // return size as distinct
        return set.size();
    }

}
