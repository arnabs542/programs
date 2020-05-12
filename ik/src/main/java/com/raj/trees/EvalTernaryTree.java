package com.raj.trees;

import java.util.Stack;

public class EvalTernaryTree {
    /**
     * Given a string that contains ternary expressions. The expressions may be nested, task is convert the given
     * ternary expression to a binary Tree.
     *
     * Input : expression =  a?b?c:d:e
     * Output :     a
     *            /  \
     *           b    e
     *         /  \
     *        c    d
     *
     * # start with building a root node as first char
     * # if ? then build left
     * # if : then build right
     * # Recursive solution doesn't work or gets too complex
     * # Use Stack based approach to have more control over previously seen nodes
     */
    static Node evalTernaryExpAsBTree(char[] exp) {
        Stack<Node> stack = new Stack<>();

        // build root
        Node root = new Node(exp[0]);
        stack.push(root);

        for (int i = 1; i < exp.length; i+=2) {  // we process ? + node each iter
            Node node = new Node(exp[i+1]);

            // build left, if next char is ?
            if (exp[i] == '?') stack.peek().left = node;

            // build right, if next char is :
            if (exp[i] == ':') {
                stack.pop();                // pop last node 'c' out as we need it's parent
                Node parent = stack.pop();  // attach new node to parent's right
                parent.right = node;
            }

            stack.push(node);  // add new node to stack for populating left/right
        }

        return root;
    }

    public static void main(String[] args) {
        printTree(evalTernaryExpAsBTree("a?b?c:d:e".toCharArray()));
        System.out.println();
        /**
         *            a
         *      b            i
         *   c      f      j    k
         * d   e  g   h
         */
        printTree(evalTernaryExpAsBTree("a?b?c?d:e:f?g:h:i?j:k".toCharArray()));
    }

    static void printTree(Node n) {
        if (n == null) return;
        System.out.print(n.ch + "");
        if (n.left != null) {
            System.out.print("?");
            printTree(n.left);
        }
        if (n.right != null) {
            System.out.print(":");
            printTree(n.right);
        }
    }

    static class Node {
        char ch;
        Node left,right;
        Node(char c) {
            ch = c;
        }
    }
}
