package com.raj.recursion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class GenParenthesis {

    /**
     * Optimal solution:
     * @see com.raj.recursion.permutations.WellFormedBraces
     */
    public static void main(String[] args) {
        System.out.println(Arrays.toString(find_all_well_formed_brackets(4)));
    }

    static String[] find_all_well_formed_brackets(int n) {
        String inp = "";
        while (n > 0) {
            inp += "()";
            n--;
        }

        p(inp, "");
        return res.toArray(new String[res.size()]);
    }

    static Set<String> res = new HashSet<>();

    static void p(String s, String soFar) {
        if (s.isEmpty() && isValid(soFar)) {
            res.add(soFar);
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            p(s.substring(0,i) + s.substring(i+1), soFar+s.charAt(i));
        }

    }

    static boolean isValid(String e) {
        Stack<Character> s = new Stack();
        for (char c : e.toCharArray()) {
            if (c == '(') s.push(c);
            else {
                // c == ')'
                if (!s.isEmpty() && s.peek() == '(') s.pop();
                else return false;
            }
        }
        return s.isEmpty();
    }
}
