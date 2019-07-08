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
        // better impl - takes care of operator precedence and double digits results
        System.out.println(_evalExpr("(2 + ( (1+2)*(3+1) ) + 5)"));
        System.out.println(_evalExpr("(4*(2+3))"));
        System.out.println(_evalExpr("4+2*3"));

        //System.out.println(evalExpr("(2 + ( (1+2)*(3+1) ) + 5)"));
        //System.out.println(evalExpr("(1+((2*3)))"));
    }

    private static int _evalExpr(String e) {
        operatorStack.clear();

        // convert to postfix for easier evaluation later
        String postFixExp = infixToPostfix(e);
        System.out.println("PostFix Exp => " + postFixExp);

        // finally compute postFix
        Stack<Integer> opStack = new Stack<>();
        for (char ch : postFixExp.toCharArray()) {
            if (Character.isDigit(ch)) opStack.push(Integer.parseInt(ch+""));
            else {  // get 2 operand out and push result back
                int a = opStack.pop();
                int b = opStack.pop();
                switch (ch) {
                    case '+': opStack.push(a+b); break;
                    case '-': opStack.push(a-b); break;
                    case '/': opStack.push(a/b); break;
                    case '*': opStack.push(a*b); break;
                }
            }
        }
        return opStack.pop();
    }

    static Stack<Character> operatorStack = new Stack<>();

    /**
     * (1+(2*3)) => 123*+
     * Makes it very easy to compute with postFix notation
     */
    private static String infixToPostfix(String e) {
        String postfixExp = "";
        for (char ch : e.toCharArray()) {
            if (Character.isSpaceChar(ch)) continue;

            // operand 1,2,3 etc goes into exp directly
            if (Character.isDigit(ch)) postfixExp += ch;

            // operator +,-,* etc can be PUSHED if equal or higher precedence
            else if (ch == '(' || !isLowerPrecedenceOperator(ch)) operatorStack.push(ch);

            // pop & add to exp if ) or a lower precedence than top come
                // as the before exp has to be evaluated before pushing lower operator
            else if (ch == ')' || isLowerPrecedenceOperator(ch)) {    // pop until '('
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') postfixExp += operatorStack.pop();
                if (!operatorStack.isEmpty() && operatorStack.peek() == '(') operatorStack.pop(); // pop corresponding '(', if any
            }
        }
        // pop off remaining operators and add to exp as we are done traversing
        while (!operatorStack.isEmpty()) postfixExp += operatorStack.pop();
        return postfixExp;
    }

    private static boolean isLowerPrecedenceOperator(char a) {
        if (operatorStack.isEmpty()) return false;
        return getScore(a) < getScore(operatorStack.peek());
    }

    private static int getScore(char c) {
        switch (c) {
            case '+':
            case '-': return 1;
            case '/':
            case '*': return 2;
            default: return 0;
        }
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
