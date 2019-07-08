package com.raj.recursion;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class ReverseLL {

    static Node head;

    public static void main(String[] args) {
        head = new Node("1");
        final Node[] n = {head};
        Arrays.stream("2 3 4 5".split(" ")).forEach(x -> {
            n[0].next = new Node(x);
            n[0] = n[0].next;
        });
        print();
        Node p = head;
        reverse(p);
        print();
    }

    /**
     * head
     *    a  -> b -> c -> d -> e -> null
     */
    static void reverse(Node p) {

        // Base case
        if (p.next == null) {
            head = p;
            return;
        }

        // dfs - reverse will keep going in until base case is met
        reverse(p.next);

        // then re-link         null <- d <- e <- head
        Node q = p.next;
        q.next = p;
        p.next = null;
    }

    private static void print() {
        Node n = head;
        while (n != null) {
            System.out.print(n);
            n = n.next;
        }
        System.out.println();
    }

    private static final class Node {
        String val;
        Node next;

        Node (String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val + " -> ";
        }
    }
}
