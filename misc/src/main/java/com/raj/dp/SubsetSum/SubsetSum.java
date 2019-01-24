package com.raj.dp.SubsetSum;

/**
 * @author rshekh1
 */
public class SubsetSum {

    /**
     * Given {5,2,1,3}
     * {5,1,3} sums up to 9
     * Return true and print the subset
     */
    public static void main(String[] args) {
        System.out.println(rec("", new int[]{5,2,1,3}, 9, 0));
        //System.out.println(cnt);
        System.out.println(dp(new int[]{0,5,2,1,3}, 9));
    }

    /**
     * Recursive solve:
     * Include A[i] & exclude & recurse
     * O(n2^n) --> n for iterating to add to see the new sum ideally, but the following is just 2^n as the sum is computed greedily
     */
    static int cnt = 0;

    private static boolean rec(String soFar, int[] A, int targetSum, int idx) {
        if (targetSum == 0) {
            System.out.println(soFar);
            return true;
        }
        if (idx >= A.length || targetSum < 0) return false;
        cnt++;
        // include & exclude A[i]
        return rec(soFar+A[idx]+",", A, targetSum-A[idx], idx+1) ||
                rec(soFar, A, targetSum, idx+1);
    }

    /**
     * DP = O(n x length of target sum)
     *
     *          0   1   2   3   4   5   6   7   8   9   (sum)
     *      0   T   F   F   F   F   F   F   F   F   F
     *      5   T   F   F   F   F   T   F   F   F   F
     *      2   T   F   T   F   F   T   F   T   F   F
     *      1   T   T   T   ......
     *      3   T
     *
     *      Formula = true if DP[i-1][j] || DP[i-1][j-A[i]] is true
     */
    private static boolean dp(int[] A, int sum) {
        boolean[][] dpTable = new boolean[A.length][sum + 1];
        
        // fill up trivial cases - we can always make sum=0 with empty set
        for (int i = 0; i < A.length; i++) dpTable[i][0] = true;

        for (int i = 1; i < A.length; i++) {
            for (int j = 1; j <= sum; j++) {
                boolean include = dpTable[i-1][j];     // if this sum was achieved for previous row, then we can achieve the same for this row without including it
                boolean exclude = (j >= A[i]) ? dpTable[i-1][j-A[i]] : false;    // if we include this number, determine if the leftover sum is achievable
                dpTable[i][j] = include || exclude;
            }
        }

        // print the subset
        int i = A.length-1, j = sum;
        while (i > 0) {
            if (dpTable[i][j] && !dpTable[i-1][j]) {
                System.out.print(A[i] + ",");
                j -= A[i];
            }
            i--;
        }

        return dpTable[A.length-1][sum];
    }

}
