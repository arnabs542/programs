package com.raj.dp;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rshekh1
 */
public class MaxProductSubArray {

    public static void main(String[] args) {
        System.out.println(maxProduct(Lists.newArrayList(2,3,-2,4,-1,-3,2)));
    }

    /**
     * If there were no zeros or negative numbers, then the answer would definitely be the product of the whole array.
     * Now lets assume there were no negative numbers and just positive numbers and 0. In that case we could maintain
     * a current maximum product which would be reset to A[i] when 0s were encountered.
     * When the negative numbers are introduced, the situation changes ever so slightly. We need to now maintain
     * the maximum product in positive and maximum product in negative. On encountering a negative number, the
     * maximum product in negative can quickly come into picture.
     */
    public static int maxProduct(final List<Integer> A) {
        if (A.size() == 0) return 0;
        List<Integer> posProduct = new ArrayList<>();
        List<Integer> negProduct = new ArrayList<>();
        List<Integer> maxProduct = new ArrayList<>();
        posProduct.add(A.get(0)); negProduct.add(A.get(0)); maxProduct.add(A.get(0));
        for (int i = 1;i < A.size();i++) {
            int a = posProduct.get(i-1) * A.get(i);
            int b = negProduct.get(i-1) * A.get(i);
            posProduct.add(i, Math.max(Math.max(a, b), A.get(i)));
            negProduct.add(i, Math.min(Math.min(a, b), A.get(i)));
            maxProduct.add(i, Math.max(posProduct.get(i), maxProduct.get(i-1)));
        }
        System.out.println("Arr:" + A);
        System.out.println("+ : " + posProduct);
        System.out.println("- : " + negProduct);
        System.out.println("Max:" + maxProduct);
        return maxProduct.get(A.size()-1);
    }

}
