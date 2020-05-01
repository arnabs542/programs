package com.raj.strings;

public class KPalindrome {
    /**
     * Given a string, find out if the string is K-Palindrome or not.
     * A k-palindrome string transforms into a palindrome on removing at most k characters from it.
     *
     * Examples :
     * Input : String - abcdecba, k = 1
     * Output : Yes
     * String can become palindrome by removing 1 character i.e. either d or e)
     *
     * Input  : String - abcdeca, K = 2
     * Output : Yes
     * Can become palindrome by removing 2 characters b and e.
     *
     * Input : String - acdcb, K = 1
     * Output : No
     */
    public static void main(String[] args) {
        char[] A = "abcdecba".toCharArray(); int k = 1;
        int[][][] memo = new int[A.length][A.length][k+1];
        System.out.println(isKPalin_rec(A, 0, A.length-1, k, memo) == 1);

        A = "abcbd".toCharArray(); k = 1;
        memo = new int[A.length][A.length][k+1];
        System.out.println(isKPalin_rec(A, 0, A.length-1, k, memo) == 1);

        A = "abcbd".toCharArray(); k = 2;
        memo = new int[A.length][A.length][k+1];
        System.out.println(isKPalin_rec(A, 0, A.length-1, k, memo) == 1);
    }

    /**
     * Edit Distance pattern
     * Recursive Soln:
     * O(n + 2^k)  => n for all chars match & if char dn't match, branching factor = 2 & depth is at max k
     *
     * memo results for i,j,k
     * 0 = uninitialized
     * 1 = true
     * 2 = false
     *
     * Time = O(n.k)  w/ memo
     * Space = O(n.n.k)
     */
    static int isKPalin_rec(char[] A, int i, int j, int k, int[][][] memo) {
        if (k < 0) return 2;
        if (i == j || (A[i] == A[j] && Math.abs(i-j) == 1)) return  1;

        if (memo[i][j][k] != 0) {
            System.out.println("returning " + i + j + k + memo[i][j][k]);
            return memo[i][j][k];
        }

        int result;
        if (A[i] == A[j]) {
            result = isKPalin_rec(A, i+1, j-1, k, memo);
        } else {
            if (isKPalin_rec(A, i+1, j, k-1, memo) == 1 || isKPalin_rec(A, i, j-1, k-1, memo) == 1) {
                result = 1;   // k-palin
            } else {
                result = 2;   // not k-palin
            }
        }

        memo[i][j][k] = result;
        return result;
    }

}
