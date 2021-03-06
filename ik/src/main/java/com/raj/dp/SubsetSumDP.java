package com.raj.dp;

import com.raj.Util;

import java.util.ArrayList;
import java.util.List;

public class SubsetSumDP {
    /**
     * Find is it's possible to Divide a group of people into 2 groups of equal weights.
     * Another variation is - Given an array of non negative numbers and a total, is there subset of numbers in this
     * array which adds up to given total?
     *
     * weights = {1,2,3,4,5,7} => true as we can have groups of 11 weights using {7,4} & {1,2,3,5}
     */
    public static void main(String[] args) {
        int[] A = {1,2,3,4,5,7};
        System.out.println(rec(A, 0, 11));  // total_weight/2
        System.out.println(dp(A));

        System.out.println(dp(new int[]{2,3,1}));
    }

    /**
     * Check if the total weight is even. If yes, divide it and find if can reach to it using subset solution.
     * weights = {1,2,3,4,5,7} = total 22
     * Target weight = 22/2 = 11
     * Now apply include/exclude subset pattern and see if we can add upto 11.
     */
    static boolean rec(int[] A, int i, int wt) {
        if (wt == 0) return true;
        if (wt < 0 || i >= A.length) return false;
        // include i
        boolean includeAns = rec(A, i+1, wt-A[i]);
        // exclude i
        boolean excludeAns = rec(A, i+1, wt);
        return includeAns || excludeAns;
    }

    /**
     * We have 2 changing params - i & it's sum & the answer is true/false
     * Hence we need a 2D DP table.
     *           0 1 2 3 4 5 6 7 8 9 10 11  --> Sum
     * Arr |  1  T T F F ....           F
     *     v  2  T
     *        3  T         x -> can we form 5 using arr elems {1,2,3} ? Lets try to use prev computed ans which used {1,2}
     *        4  T              We exclude 3 & include 3. Exclusion is re-using prev value. Inclusion means subtract 3
     *        5  T              from sum and check if the remaining sum is true which is:
     *        7  T              dp[i][sum] = dp[i-1][sum] || dp[i-1][sum-A[i-1]]
     *                           {1,2,3}   =    {1,2}     OR     can sum-3 be formed using {1,2}
     * Base cases:
     * # Col 0 means can we reach 0 using any element? Yes {} empty set, hence its True.
     *
     * Time / Space = O(n * sum)
     * Note - Recursive solve O(2^n) or rec solve w/ memo could be better if sum is very large (in millions)
     */
    static boolean dp(int[] A) {
        int total = 0;
        // what's the target sum?
        for (int i = 0; i < A.length; i++) total += A[i];
        // odd sum, return false as not possible to partition
        if (total % 2 != 0) return false;
        // target is half of the total
        int target = total / 2;

        boolean[][] dp = new boolean[A.length+1][target+1];
        // base case 1 - we can form sum=0 with empty set, hence set T irrespective of arr elements
        for (int i = 0; i <= A.length; i++) dp[i][0] = true;
        // base case 2 - we can only form 1 with arr element 1, every other col is false
        //dp[0][1] = true;

        for (int i = 1; i <= A.length; i++) {           // row 0 was taken care of in base case
            for (int sum = 1; sum <= target; sum++) {   // sum 0 was taken care of in base case
                // exclude
                boolean exclude = dp[i-1][sum];
                // include
                boolean include = false;
                if (sum >= A[i-1]) {    // sum needs to be greater than or equal to this arr element
                    include = dp[i-1][sum-A[i-1]];  // check if the remaining sum can be formed using {1,2} if 3 was being included
                }
                dp[i][sum] = include || exclude;
            }
        }

        Util.print2DBooleanArray(dp);

        // print partitions
        List<Integer> inclPart = new ArrayList<>();
        List<Integer> exclPart = new ArrayList<>();
        int i = A.length; int sum = target;
        while (i>0) {
            if (sum >= A[i-1] && dp[i][sum-A[i-1]]) {
                inclPart.add(A[i-1]);                  // we included subject to above condition
                sum-=A[i-1];
            } else {
                exclPart.add(A[i-1]);                  // else add to excluded partition
            }
            i--;
        }

        System.out.println("Partition 1 = " + inclPart + ", Partition 2 = " + exclPart);
        return dp[A.length][target];
    }

    /**
     * Subset Sum with negative values: s = [10, -3, 7, 2, 1, 3], ans =>  [7,2,1] & [10,-3,3]
     * https://oj.interviewkickstart.com/view_top_submission/6555/37/79438/
     *
     * Adjust the above code to handle:
     * # Compute sum_of_negatives & sum_of_positives separately.
     * # Loop will run from sum_of_negatives to sum_of_positives
     *   -> dp[][] won't be able to store -ve vals, so use a map instead - Map<Integer, Boolean> dp[]
     *   -> if condition changes to if(sum - A.get(i-1) - sum_of_negatives >= 0){ ..}
     */

}
