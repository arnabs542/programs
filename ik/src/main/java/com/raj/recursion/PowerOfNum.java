package com.raj.recursion;

public class PowerOfNum {

    public static void main(String[] args) {
        System.out.println(bruteForce(2, 16));
        System.out.println(logarithmic(2, 16));
        System.out.println(optimal_log(3, 5));
    }

    /**
     * Time = O(b)  ... we recursively compute for b times
     * Space = O(b) ... could be optimized to O(1) by using iterative approach
     */
    static int bruteForce(long a, long b) {
        if (b == 1) return (int) a % MOD;
        return (int) (a * bruteForce(a, b-1)) % MOD;
    }

    /**
     * We are iterating b times, we could optimize to log b if we reduce task by 2, in every recursive call
     * Runtime = O(log b), where b is the exponent as we are halving problem space every recursive call
     * Space = O(log b), depth of recursive call
     */
    static int MOD = 1000000007;
    static int logarithmic(long a, long b) {
        // base case
        if (b == 0) return 1;
        a = a % MOD;
        // recursively calculate a^(b/2)
        long tmp = logarithmic(a , b/2);        // reducing problem by half every recursive call
        // doubling the power : a^(b/2) * a^(b/2) = a^b , if b is even
        tmp = tmp * tmp % MOD;
        // if power is odd
        if (b % 2 == 1) {
            // multiply with extra a
            tmp = tmp * a % MOD;
        }
        return(int)tmp;
    }

    /**
     * Further optimization could be achieved by doing a bottom up iterative approach rather than top down recursive (recursive stack overhead will be eliminated)
     * 3^5 = 3 ^ (101) binary = 3*2^2 * nothing for zero * 3*2^0
     * Keep raising power of 2 in every iteration of bit shift to right
     */
    static int optimal_log(long a, long b) {
        // To store resultant value
        long answer = 1;

        // To store a^(incremental power of 2). Initial value a^(2^0) = a
        long powerOfTwo = a % MOD;

        while (b > 0) {
            // For set bit in b
            if ((b & 1l) > 0) {
                answer = (answer * powerOfTwo) % MOD;
            }

            // To increase value of powerOfTwo by powerOfTwo
            // For example to make a^(2^2 + 2^2) from a^(2^2) as a^(2^2 + 2^2) = a^(2^2) * a(2^2).
            powerOfTwo = (powerOfTwo * powerOfTwo) % MOD;

            // Divide b by 2
            b = b >> 1;
        }
        return (int)answer;
    }

}
