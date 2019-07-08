package com.raj.stackqueues;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class BalancedParenthesis {

    public static void main(String[] args) {
        System.out.println(isValid("{4*(2+3)}]"));
        System.out.println(isValid("[{((4+2)*(2+3))-5}]"));
    }

    static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character,Character> map = new HashMap<>();
        map.put(']','[');
        map.put('}','{');
        map.put(')','(');
        for (char ch : s.toCharArray()) {
            // if digit or number, skip
            if (Character.isDigit(ch) || Character.isSpaceChar(ch) || Character.isAlphabetic(ch)) continue;
            // if opening braces, push
            if (map.values().contains(ch)) stack.push(ch);
            // if 'matching' closing braces, pop
            if (map.keySet().contains(ch)) {
                if (stack.isEmpty() || map.get(ch) != stack.peek()) return false; // not matching closing braces
                else stack.pop();   // pop otherwise
            }
        }
        return stack.isEmpty();
    }

}
