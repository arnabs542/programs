package com.raj.sorting;

import java.util.*;

public class KNearestNeighbors {

    /**
     * Given a point p, & other n points in 2D space, find k points which are nearest to p.
     */
    static Random random = new Random();

    public static void main(String[] args) {
        Point[] points = new Point[] {
                new Point(7,9), new Point(5,4),
                new Point(1,2), new Point(3,5),
                new Point(1,7), new Point(3,9),
                new Point(6,2), new Point(8,6)
        };

        System.out.println("K nearest points to origin = " + nearest_neighbours(1,1, 3, points));
        System.out.println("K nearest points to origin = " + Arrays.toString(findKNearestPoints(points, new Point(1,1), 3)));
    }

    /**
     * Brute Force:
     * 1> Selection Sort k times => O(nk)
     * 2> Sort and find the first k points => O(nlogn)
     * 3> n-sized Min Heap => O(n) to build heap + O(klogn) to extract k min elems.
     *
     * Better - Using k-sized max heap (Anti-Heap as we just need to store k elems that are of interest):
     * Runtime = O(k + (n-k)logk) ~= O(nlogk)
     */
    public static List<List<Integer>> nearest_neighbours(int x, int y, int k, Point[] pts) {
        if (pts.length == 0 || k < 1) return Collections.emptyList();
        PriorityQueue<Point> pq = new PriorityQueue<>((a, b) -> Double.compare(b.dist,a.dist)); // max heap
        for (Point p : pts) {   // O(n)
            double dist = getDist(x,y,p.x, p.y);
            Point newPoint = new Point(p.x, p.y);
            newPoint.dist = dist;

            if (pq.size() < k) pq.add(newPoint); // fill up heap first ... O(k)
            else if (dist < pq.peek().dist) {    // check if new dist is of interest  ... n-k remaining elements
                pq.remove();        // O(logk)
                pq.add(newPoint);
            }
        }
        List<List<Integer>> res = new ArrayList<>();
        int i=0;
        while (++i<=k) res.add(Collections.emptyList());
        while(!pq.isEmpty()) {
            Point p = pq.remove();
            res.set(--k, Arrays.asList(p.x, p.y));
        }
        return res;
    }

    /**
     * QuickSelect k smallest, return all elements before kth pivot
     * Runtime = O(n)
     *
     * The algorithm is similar to QuickSort. The difference is, instead of recurring for both sides (after finding pivot),
     * it recurs only for the part that contains the k-th smallest element. The logic is simple, if index of partitioned
     * element is more than k, then we recur for left part. If index is same as k, we have found the k-th smallest
     * element and we return. If index is less than k, then we recur for right part. This reduces the expected
     * complexity from O(n log n) to O(n), with a worst case of O(n^2).
     */
    static Point[] findKNearestPoints(Point[] points, Point origin, int k) {
        // compute dist with origin
        for (Point p : points) {
            p.dist = getDist(origin.x, origin.y, p.x, p.y);
        }
        System.out.println("Distance array : " + Arrays.toString(points) + ", k = " + k);
        // use quick select to get nearest k neighbors in O(n) time
        quickSelect(points, k, 0, points.length-1);
        return Arrays.copyOfRange(points, 0, k);
    }

    static void quickSelect(Point[] A, int k, int start, int end) {
        // base case
        if (start >= end) return;

        // pick pivot
        int pivotIdx = start + random.nextInt(end - start + 1);
        swap(A, start, pivotIdx);

        // partition around fixed pivot. j moves swapping smaller elements with i
        int smaller = start;
        Point pivot = A[start];
        for (int bigger = smaller+1; bigger <= end; bigger++) {
            if (A[bigger].dist < pivot.dist) swap(A, ++smaller, bigger);  // don't use A[pivotIdx] as it's swapped elem
        }
        swap(A, smaller, start);  // smaller is at boundary of lesser elems than pivot, hence swap pivot to here (not smaller+1 as it will bring a bigger elem to lesser partition)
        System.out.println("After partitioning array " + Arrays.toString(Arrays.copyOfRange(A, start, smaller)) + " < " + A[smaller] + " > " + Arrays.toString(Arrays.copyOfRange(A, smaller+1, end+1)) + " , smallerIdx=" + smaller + ", smaller=" + A[smaller]);

        // if pivot is kth, we found the answer as it is at it's exact sorted place in array & elems to left are smaller (but not sorted)
        if (smaller == k) return;
        else if (k < smaller) quickSelect(A, k, start, smaller-1); // else recurse on the partition that is of interest (dropping the other half that's of no use)
        else quickSelect(A, k, smaller+1, end);
    }

    static void swap(Point[] points, int i, int j) {
        if (i == j) return;
        Point tmp = points[i];
        points[i] = points[j];
        points[j] = tmp;
    }

    static double getDist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((y2-y1),2) + Math.pow((x2-x1),2));
    }

    static class Point {
        int x,y;
        double dist;
        Point(int _x, int _y) { x = _x; y = _y; }

        public String toString() {
            return (int)dist + "" ; //Arrays.toString(new int[]{(int)dist});
        }
    }

    /**
     * Another Variant of this problem:
     * K smallest / largest elements in an array. Also called Order Statistics.
     * https://www.geeksforgeeks.org/k-largestor-smallest-elements-in-an-array/
     */

}
