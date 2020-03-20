package com.raj.recursion;

import java.util.Arrays;

/**
 * @author rshekh1
 */
public class ReverseLL {

    static Node head;

    public static void main(String[] args) {
        head = new Node(1);
        final Node[] n = {head};
        Arrays.stream(new int[]{1,2,3,4,5}).forEach(x -> {
            n[0].next = new Node(x);
            n[0] = n[0].next;
        });
        print();
        Node p = head;
        reverse_rec(p);
        print();

        head = reverse_iter(head);
        print();
    }

    static Node reverse_iter(Node head) {
        Node prev = null, curr = head, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;  // remember curr has moved to next which is null, hence prev is our head now
        return head;
    }

    /**
     * head
     *    a  -> b -> c -> d -> e -> null
     */
    static void reverse_rec(Node p) {

        // Base case
        if (p.next == null) {
            head = p;
            return;
        }

        // dfs - reverse will keep going in until base case is met
        reverse_rec(p.next);

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
        int val;
        Node next;

        Node (int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val + " -> ";
        }
    }
}
