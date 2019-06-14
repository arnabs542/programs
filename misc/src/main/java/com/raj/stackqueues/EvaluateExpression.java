package com.raj.stackqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author rshekh1
 */
public class EvaluateExpression {

    /**
     * (2 + ( (1+2)*(3+1) ) + 5) = 19
     * (1+((2*3))) = 7
     */
    static boolean isDebug = false;
    public static void main(String[] args) {
        System.out.println(evalExpr("(2 + ( (1+2)*(3+1) ) + 5)"));
        System.out.println(evalExpr("(1+((2*3)))"));
    }

    private static int evalExpr(String e) {
        int res = 0;
        if (e == null || e.isEmpty()) return res;
        String expr = e.replace(" ", "");
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            if (isDebug) System.out.println("Stack => " + stack);

            // push to stack until we hit )
            if (expr.charAt(i) == ')') {
                // pop until we hit (, form a sub expr and eval
                List<String> subExpr = new ArrayList<>();   // List helps with more than single digit numbers
                while (true) {
                    if (isDebug) System.out.println("Stack pop => " + stack);

                    subExpr.add(stack.pop());
                    if (stack.peek().equals("(")) {
                        stack.pop();    // done with (a+b), pop off last (
                        break;
                    }
                }
                res = evalSubExpr(subExpr);
                stack.push(res+"");
                if (isDebug) System.out.println("Stack after sub expr eval => " + stack);
            } else {
                // keep pushing into stack
                stack.push(expr.charAt(i)+"");
            }
        }
        return res;
    }

    private static int evalSubExpr(List<String> subExpr) {
        if (isDebug) System.out.println("subExpr = " + subExpr);
        if (subExpr.isEmpty()) return 0;
        if (subExpr.size() == 1) return Integer.parseInt(subExpr.get(0));
        // can even evaluate a + b + c
        int res = Integer.parseInt(subExpr.get(0));
        for (int i = 1; i < subExpr.size(); i++) {
            if (subExpr.get(i).equals("+")) {
                res += Integer.parseInt(subExpr.get(i+1));
                i++;
            } else {
                res *= Integer.parseInt(subExpr.get(i+1));
                i++;
            }
        }
        if (isDebug) System.out.println(res);
        return res;
    }

}
