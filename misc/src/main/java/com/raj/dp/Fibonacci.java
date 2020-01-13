package com.raj.dp;

/**
 * Created by rshekh1 on 6/13/16.
 */
public class Fibonacci {

    /**
     *
     * Dynamic Programming is an algorithmic paradigm that solves a given complex problem by breaking it into subproblems
     * and stores the results of subproblems to avoid computing the same results again.
     * Following are the two main properties of a problem that suggest that the given problem can be solved using Dynamic programming.

     1) Overlapping Subproblems
     2) Optimal Substructure

     1) Overlapping Subproblems:
     Like Divide and Conquer, Dynamic Programming combines solutions to sub-problems. Dynamic Programming is mainly used
     when solutions of same subproblems are needed again and again. In dynamic programming, computed solutions to subproblems
     are stored in a table so that these don’t have to recomputed. So Dynamic Programming is not useful when there are
     no common (overlapping) subproblems because there is no point storing the solutions if they are not needed again.
     For example, Binary Search doesn’t have common subproblems. If we take example of following recursive program for Fibonacci Numbers,
     there are many subproblems which are solved again and again.

     Recursion tree for execution of fib(5):

     fib(5)
     /             \
     fib(4)                fib(3)
     /      \                /     \
     fib(3)      fib(2)         fib(2)    fib(1)
     /     \        /    \       /    \
     fib(2)   fib(1)  fib(1) fib(0) fib(1) fib(0)
     /    \
     fib(1) fib(0)
     We can see that the function f(3) is being called 2 times. If we would have stored the value of f(3), then instead
     of computing it again, we would have reused the old stored value. There are following two different ways to store
     the values so that these values can be reused.

     a) Memoization (Top Down)
     b) Tabulation (Bottom Up)
     */

    private static Integer[] dp = new Integer[100];

    // top down(memoization) - Whenever we need the solution to a subproblem, we first look into the lookup table. If the precomputed value is there then we return that value, otherwise, we calculate the value and put the result in the lookup table so that it can be reused later..
    public static int fib0(int n) {     // runtime & space is O(n) while recursive solve is exponential O(2^n)
        if (n == 0) dp[n] = 0;
        if (n == 1) dp[n] = 1;
        if (n > 1 && dp[n] == null)  dp[n] = fib0(n-1) + fib0(n-2);
        return dp[n];
    }

    // bottom up(tabulation) - solve obvious ones like f(0), f(1) and then use them to solve others - O(n) time
    public static int fib1(int n) {
        /**
         * Note - To optimize on space, since f depends on prev 2 values, we can just store 3 values
         * & do i%3 to get index thereby achieving O(1) space
         * f[i%3] = f[(i-1)%3] + f[(i-2)%3]
         * return f[n%3]
         */
        int f[] = new int[n+1];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i <= n; i++)  f[i] = f[i - 1] + f[i - 2];
        return f[n];
    }

    public static void main(String[] args) {
        System.out.println(Fibonacci.fib0(7));
        System.out.println(Fibonacci.fib1(7));
    }
}
