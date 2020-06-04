package com.raj.strings;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class LetterCombinationPhoneNumber {

    public static void main(String[] args) {
        LetterCombinationPhoneNumber l = new LetterCombinationPhoneNumber();
        System.out.println(l.letterCombinations("234"));
    }

    /**
     *              " ", 234
     *           /     |    \
     *         a,34   b,34   c,34    (decompose 2 and permute on each letter of 2)
     *         / | \
     *      ad,4 ae,4 af,4           (decompose 3 and permute on each letter of 3)
     *       /
     *  (adg,adh,adi)
     *
     * O(bf = 3 ^ max_depth = 3) = O(3^3)
     */
    private HashMap<String, String> map = new HashMap<>();
    private List<String> result = Lists.newArrayList();

    public LetterCombinationPhoneNumber() {
        map.put("0", "0");
        map.put("1", "1");
        map.put("2", "abc");
        map.put("3", "def");
        map.put("4", "ghi");
        map.put("5", "jkl");
        map.put("6", "mno");
        map.put("7", "pqrs");
        map.put("8", "tuv");
        map.put("9", "wxyz");
    }

    public List<String> letterCombinations(String digits) {
        if(digits == null || digits.length() == 0) {
            return result;
        }
        permuteLetterCombinations(digits.toCharArray(), 0, new Stack<>());
        return result;
    }

    public void permuteLetterCombinations(char[] digits, int i, Stack<Character> stack) {
        if(i == digits.length) {       // Base case - when no digits are left to permute
            String s = "";
            for (char c : stack) s += c;
            result.add(s);
            return;
        }

        char c = digits[i];   // take the first digit
        char[] letters = map.get(c+"").toCharArray();      // decompose digit into letters
        for(char option : letters) {        // iterate on each letter
            stack.push(option);
            permuteLetterCombinations(digits, i+1, stack);  // keep appending letter to soFar and permute on leftover digits
            stack.pop();
        }
    }

}
