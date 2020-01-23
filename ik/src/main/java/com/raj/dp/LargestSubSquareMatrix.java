package com.raj.dp;

public class LargestSubSquareMatrix {
    /**
     * Given a 2D matrix mat of integers with n rows and m columns. All the elements in the matrix mat are either 0 or 1.
     * Your task is to determine the largest sub-square size of the matrix that contains only 1s.
     *
     * 1 1 0 0 0 0
     * 1 1 0 1 1 1
     * 0 1 0 1 1 1
     * 0 0 1 1 1 1
     * => 3 as 3x3 sub matrix is the largest
     */
    public static void main(String[] args) {
        System.out.println(dp(new int[][] {
                {1,1,0,0,0,0},
                {1,1,0,1,1,1},
                {0,1,0,1,1,1},
                {0,0,1,1,1,1}
        }));

    }

    /**
     * When we find a 1, it may be part of a sub-matrix square. The size at that col will be incr by 1
     * if neighboring 3 cells have 1 as well.
     *
     *      2 2
     *      1 x -> 1+2
     *
     * DP Formula:
     * T[i][j] = max(max, 1 + min(T[i-1][j-1], T[i-1][j], T[i][j-1]) , if (T[i][j] == 1)
     */
    static int dp(int[][] A) {
        int n = A.length, m = A[0].length;
        int[][] T = new int[n+1][m+1];

        // pad resultant matrix with zeros so that A(i-1,j-1) won't fail
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                T[i+1][j+1] = A[i][j];
            }
        }

        int maxlen = 0;     // store the max sub matrix length

        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < m+1; j++) {
                if (T[i][j] == 1) {
                    T[i][j] = 1 + Math.min(T[i-1][j-1], Math.min(T[i-1][j], T[i][j-1]));
                    maxlen = Math.max(maxlen, T[i][j]);
                }
            }
        }
        return maxlen;
    }
}
