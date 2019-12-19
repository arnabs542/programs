package com.raj.math;

/**
 * Created by rshekh1 on 6/8/16.
 */
public class Power {
    /**
     * Given a positive integer which fits in a 32 bit signed integer, find if it can be expressed as A^P
     * where P > 1 and A > 0.
     * A and P both should be integers.
     Example:
     Input = 4
     Output = True
     as 2^2 = 4.
     */

    public boolean isPower(int a) {
        if (a == 1) return true;
        for (int i = 2; i <= Math.sqrt(a); i++) {
            if (a%i != 0) continue;
            int tmp = a;
            while(true) {
                if (tmp == 1) return true;
                if (tmp%i == 0) tmp /= i;
                else break;
            }
        }
        return false;
    }

    static int MOD = 1000000007;
    // Runtime = O(log b), where b is the exponent as we are halving problem space every recursive call
    // Space = O(log b), depth of recursive call
    public static int calculate_power(long a, long b) {
        // base case
        if (b == 0) return 1;
        a = a % MOD;
        // recursively calculate a^(b/2)
        long tmp = calculate_power(a , b/2);
        // doubling the power : a^(b/2) * a^(b/2) = a^b , if b is even
        tmp = tmp * tmp % MOD;
        // if power is odd
        if (b % 2 == 1) {
            // multiply with extra a
            tmp = tmp * a % MOD;
        }
        return(int)tmp;
    }

    public static void main(String[] args) {
        Power p = new Power();
        System.out.println(p.isPower(1));
        System.out.println(p.isPower(2));
        System.out.println(p.isPower(3));
        System.out.println(p.isPower(4));
        System.out.println(p.isPower(5));
        System.out.println(p.isPower(6));
        System.out.println(p.isPower(7));
        System.out.println(p.isPower(9));
        System.out.println(p.isPower(16));
        System.out.println(p.isPower(81));
        System.out.println(p.isPower(1000));

        System.out.println(calculate_power(2, 32));
    }

}
