package com.raj.adhoc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rshekh1
 */
public class PascalsTriangle {

    public static void main(String[] args) {
        findPascalTriangle(6).forEach(x -> System.out.println(x));
    }

    public static List<List<Integer>> findPascalTriangle(int n) {
        List<List<Integer>> res = new ArrayList<>();
        int mod = 1000000007;
        if (n<=0) return res;
        for (int i=0; i<n; i++) {
            List<Integer> l = new ArrayList<>();
            res.add(l);
            for (int j=0; j<=i; j++) {
                if (j == 0 || i == j) l.add(1); // first & the last index is 1
                else l.add((res.get(i-1).get(j-1) + res.get(i-1).get(j)) % mod);
            }
        }
        return res;
    }

}
