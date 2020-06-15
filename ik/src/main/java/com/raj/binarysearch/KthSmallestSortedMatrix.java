package com.raj.binarysearch;

import java.util.PriorityQueue;

public class KthSmallestSortedMatrix {
    /**
     * Find the Kth smallest element in a matrix with sorted rows and columns.
     */
    public static void main(String[] args) {
        System.out.println(kthSmallest_heap(new int[][]{
                {1,3,5},
                {2,4,6},
                {7,8,9}
        }, 7));
    }

    /**
     * [K-way merge routine using heap]
     * O(k log n) time by merging the rows incrementally, augmented with a heap to efficiently find the minimum element.
     * put the elements of the first column into a heap and track the row they came from. At each step, you remove the
     * minimum element from the heap and push the next element from the row it came from (if you reach the end of the row,
     * then you don't push anything).
     */
    static int kthSmallest_heap(int[][] A, int K) {
        int rows = A.length, cols = A[0].length;
        PriorityQueue<Tuple> pq = new PriorityQueue<>((a,b) -> a.val-b.val);
        // add first col of each row
        for (int i = 0; i < rows; i++) {
            pq.add(new Tuple(i, 0, A[i][0]));
        }
        // remove & add from the row it was removed. Count until k
        while (!pq.isEmpty() && K > 0) {
            Tuple tuple = pq.remove();
            K--;
            if (K == 0) return tuple.val;
            if (tuple.j+1 == cols) continue;
            pq.add(new Tuple(tuple.i, tuple.j+1, A[tuple.i][tuple.j+1]));
        }
        return -1;
    }

    /**
     * https://www.jianshu.com/p/f16928ea675b
     * Binary Search in matrix
     * # Pick A(0,0) as low & A(rows,cols) as high. Define mid. Will shrink search space according to the two nums.
     * # for each row, we r going to find count of nums <= mid in matrix
     * # go left or right if count < k
     *
     */
    static int kthSmallest(int[][] matrix, int k) {
        int lo = matrix[0][0], hi = matrix[matrix.length - 1][matrix[0].length - 1] + 1; // to include hi in search space, add 1
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;  // mid of lo,high
            int count = 0, j = matrix[0].length - 1;
            // num elements less than mid
            for (int i = 0; i < matrix.length; i++) {
                while (j >= 0 && matrix[i][j] > mid) j--;
                count += (j + 1);
            }
            // adjust search range
            if (count < k) lo = mid + 1;
            else hi = mid;
        }
        return lo;   // lo & high will converge at kth smallest num
    }

    static class Tuple {
        int i,j,val;
        Tuple(int _i, int _j, int v) {
            i = _i;
            j = _j;
            val = v;
        }
    }
}
