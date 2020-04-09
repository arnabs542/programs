package com.raj.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rshekh1 on 4/23/16.
 */
public class FindRepetitiveAndMissingElement {

    /**
     * You are given a read only array of n integers from 1 to n.
     Each integer appears exactly once except A which appears twice and B which is missing. Return A and B.
     Note: Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

     Example:
     Input:[3 1 2 5 3]
     Output:[dupe: 3, missing:4]
     */

    // Uses extra memory
    public ArrayList<Integer> repeatedNumber(int[] A) {
        ArrayList<Integer> res = new ArrayList<>(2);
        boolean[] bitArr = new boolean[A.length+1];

        for (int i=0; i<A.length; i++) {  // dupe
            if (bitArr[A[i]] == true) res.add(0, A[i]);
            else bitArr[A[i]] = true;
        }
        for (int i=1; i<bitArr.length; i++) {  // missing
            if (bitArr[i] == false) res.add(1, i);
        }
        return res;
    }

    /**
     * Idea is to use cyclic sort technique.
     * Given we have nums from 1..n, we can leverage that info to iterate once ans swap numbers to their respective pos
     * Input:[3 1 2 5 3]
     *
     * after linear pass =>
     * idx  0 1 2 3 4
     * num  1 2 3 3 5
     *            x - A[idx-1] == A[idx] -> dupe
     *            x - idx+1 != A[idx] -> missing
     */
    public List<Integer> cyclic_sort(int[] A) {
        Integer[] res = new Integer[2];
        for (int i = 0; i < A.length; i++) {
            if (i+1 != A[i]) {  // swap
                int t1 = A[i];
                int t2 = A[i]-1;
                A[i] = A[t2];
                A[t2] = t1;
            }
        }
        for (int i = 0; i < A.length; i++) {
            if (i>0 && A[i-1] == A[i]) res[0] = A[i];  // dupe
            if (A[i] != (i+1)) res[1] = i+1;  // missing
        }
        return Arrays.asList(res);
    }

    public static void main(String[] args) {
        FindRepetitiveAndMissingElement f = new FindRepetitiveAndMissingElement();
        System.out.println(f.repeatedNumber(new int[]{3,1,2,5,3}));
        System.out.println(f.cyclic_sort(new int[]{3,1,2,5,3}));
    }
}
