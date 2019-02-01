package com.raj.dp;

/**
 * @author rshekh1
 */
public class EditDistance {

    /**
     * Given two words A and B, find the minimum number of steps required to convert A to B.
     * Each operation is counted as 1 step. You have the following 3 operations permitted on a word:
     *
     * Insert a character
     * Delete a character
     * Replace a character
     *
     * Example :
     * edit distance between
     * "Anshuman" and "Antihuman" is 2.
     *
     * Operation 1: Replace s with t.
     * Operation 2: Insert i.
     */
    public static void main(String[] args) {
        System.out.println(minDistance("ANSHUMAN", "ANTIHUMAN"));
    }

    /**
     * This is a very standard Dynamic programming problem.
     *
     * Lets look at the brute-force approach for finding edit distance of S1 and S2.
     * We are trying to modify S1 to become S2.
     *
     * We look at the first character of both the strings.
     * If they match, we can look at the answer from remaining part of S1 and S2.
     * If they don’t, we have 3 options.
     * 1) Insert S2’s first character and then solve the problem for remaining part of S2, and S1.
     * 2) Delete S1’s first character and trying to match S1’s remaining string with S2.
     * 3) Replace S1’s first character with S2’s first character in which case we solve the problem for remaining part of S1 and S2.
     *
     * The code would probably look something like this :
     * <Recursive O(3^n) exponential solution>
     *
     * int editDistance(string &S1, int index1, string &S2, int index2) {
     * // BASE CASES
     *
     * if (S1[index1] == S2[index2]) {
     *      return editDistance(S1, index1 + 1, S2, index2 + 1);
     * } else {
     *      return min(
     *     1 + editDistance(S1, index1 + 1, S2, index2), // Delete S1 char
     *             1 + editDistance(S1, index1, S2, index2 + 1), // Insert S2 char
     *             1 + editDistance(S1, index1 + 1, S2, index2 + 1) // Replace S1 first char with S2 first char
     *      );
     * } }
     *
     * Now DP O(n^2) solve:
     */
    public static int minDistance(String A, String B) {
        int la=A.length();
        int lb=B.length();
        int[][] T;
        T= new int[lb+1][la+1];

        for(int i=0; i<=lb; i++){
            T[i][0]=i;
        }
        for(int j=0; j<=la; j++){
            T[0][j]=j;
        }

        for(int i=1; i<=lb; i++){
            for(int j=1; j<=la; j++){
                if(B.charAt(i-1)==A.charAt(j-1)){
                    T[i][j]=T[i-1][j-1];
                }
                else{
                    T[i][j]=1+minimum(T[i-1][j-1],T[i][j-1],T[i-1][j]);
                }
            }
        }
        return T[lb][la];
    }

    public static int minimum(int A, int B, int C){
        if(A<B){
            return A<C?A:C;
        }
        else{
            return B<C?B:C;
        }
    }

}
