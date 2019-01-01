package com.raj.combination;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rshekh1
 */
public class ColorfulNum {

    /**
     * N = 23
     * 2 3 23
     * 2 -> 2
     * 3 -> 3
     * 23 -> 6
     * this number is a COLORFUL number since product of every digit of a sub-sequence are different.
     *
     * Output : true
     *
     * N = 123
     * [1->1, 2->2, 3->3, 12->2, 23->6, 123->6] => false
     */

    public static void main(String[] args) {
        System.out.println(isColorful("", "1"));map.clear();System.out.println();
        System.out.println(isColorful("", "23"));map.clear();System.out.println();
        System.out.println(isColorful("", "123"));map.clear();System.out.println();
        System.out.println(isColorfulContiguousOnly_Rec("263"));map.clear();System.out.println();
        System.out.println(isColorfulContiguousOnly_Brute("263"));map.clear();System.out.println();
    }

    static Set<Integer> map = new HashSet<>();

    static boolean isColorful(String soFar, String remain) {
        if (soFar.isEmpty() && remain.isEmpty()) return true;
        if (remain.isEmpty()) {
            System.out.println(soFar);
            int prod = 1;
            for (char ch : soFar.toCharArray()) {
                prod *= Integer.parseInt(ch + "");
            }
            if (map.contains(prod)) return false;
            else {
                map.add(prod);
                return true;
            }
        }
        return isColorful(soFar+remain.charAt(0), remain.substring(1)) && isColorful(soFar, remain.substring(1));
    }

    /**
     * N = 263
     * [2->2, 6->6, 3->3, 26->12, 63->18, 263->36] => true
     */
    static boolean isColorfulContiguousOnly_Rec(String remain) {
        Set<String> combinations = Sets.newHashSet();
        makeCombinations(remain, combinations);
        return isColorfulContiguousOnly(combinations);
    }

    /**
     *           f(263) -> 2,26,263
     *           |
     *          f(63) -> 6,63
     *          |
     *         f(3) -> 3
     */
    static void makeCombinations(String remain, Set<String> combinations) {
        if (remain.isEmpty()) return;
        String soFar = "";
        for (int i = 0; i < remain.length(); i++) {
            soFar += remain.charAt(i);
            System.out.println(soFar);
            combinations.add(soFar);
        }
        makeCombinations(remain.substring(1), combinations);
    }

    // Find if the combination's product is duplicated
    static boolean isColorfulContiguousOnly(Set<String> combinations) {
        System.out.println(combinations);
        for (String s : combinations) {
            int product = 1;
            for (char c : s.toCharArray()) {
                int i = Integer.parseInt(c+"");
                product *= i;
            }
            if (map.contains(product)) return false;
            map.add(product);
        }
        return true;
    }

    static boolean isColorfulContiguousOnly_Brute(String s) {
        Set<String> combinations = Sets.newHashSet();
        for (int i = 0; i < s.length(); i++) {
            String soFar = "";
            for (int j = i; j < s.length(); j++) {
                soFar += s.charAt(j);
                System.out.println(soFar);
                combinations.add(soFar);
            }
        }
        return isColorfulContiguousOnly(combinations);
    }

}
