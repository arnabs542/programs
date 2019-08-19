package com.raj.recursion;

/**
 * @author rshekh1
 */
public class Fibonacci {

    // Fib seq => 1,1,2,3,5,8,13,21,34
    public static void main(String[] args) {
        //System.out.println(fib_exp(7));
        System.out.println(fib_linear(7, 1, 1));
    }

    // O(2^n)
    static int fib_exp(int n) {
        if (n == 0) return 0;
        if (n < 2) return 1;
        else return fib_exp(n-1) + fib_exp(n-2);
    }

    /**
     * O(n) - without memoization
     * f(n,1,1)
     * 1,1,2,3,5,8,13,21 (keep removing the term from first, thereby changing base cases and reducing size by 1)
     * _,1,2,3,5,8,13,21 => f(n-1,1,2) base case becomes - b2, b1+b2
     * _,_,2,3,5,8,13,21 => f(n-2,2,3)
     * _,_,_,3,5,8,13,21 => f(n-3,3,5)
     * _,_,_,_,5,8,13,21 => f(n-4,5,8)
     */
    static int fib_linear(int n, int base1, int base2) {
        if (n == 1) return base1;
        System.out.println(n + "," + base1 + "," + base2);
        return fib_linear(n-1, base2, base1 + base2);   // decrease & conquer recursion
    }

}
