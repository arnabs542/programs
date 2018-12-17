package com.raj.backtracking;

/**
 * @author rshekh1
 */
public class GenerateParenthesis {
    /**
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses of length 2*n.
     * For example, given n = 3, a solution set is:
     * "((()))", "(()())", "(())()", "()(())", "()()()"
     */

    /**
     ALGO:
     Let A = [()()()]
            /  \   \
     (,)()()  )X  (,())()  )X  (,()())
     / \ \        / \ \
     ...
     function rec (soFar, remain)
     if (remain.isEmpty()) {
        isValid(soFar)
        print(soFar)
     }
     for (i=0...n) {
        recurse(soFar+s[i], s[0,i] + s[i+1])
     }

     function isValid(s) {
        for (i=0...n)
            a closing brace can only happen if an open brace exists
     }
     */
}
