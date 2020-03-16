package com.raj.linkedlist;

import java.util.Arrays;

public class DetectRemoveCycle {
    /**
     * Given a linked list, find & remove cycle
     */
    public static void main(String[] args) {
        LinkedList list = new LinkedList(1);  // init LL with head
        Arrays.asList(2,3,4,5,6,7).forEach(x -> list.add(x));  // add nodes
        list.tail.next = list.head.next.next.next;
        list.print();
        list.detectRemoveLoop();
        list.print();
    }

    /**
     * Floyd Loop detection algo:
     * # Fast & Slow ptrs method - detect loop
     * # How do we remove hte loop?
     *
     * ----- m ------ x
     * 1 -> 2 -> 3 -> 4 -> 5
     *                ^    |
     *                |    v
     *                7 <- 6
     * # https://www.youtube.com/watch?v=Cs3KwAsqqn4
     * # Say m is the dist to loop start x. n is the length of loop & k is the dist till slow & fast ptrs meet.
     * # Fast ptr dist = 2 x Slow ptr dist
     *   => m + c1n + k = 2(m + c2n + k)
     *   => m = cn - k
     *   => m = (c-1)n+n - k   ...cn can be written as (c-1)n+n
     *        = c1n + (n-k)
     *        = c1n + d    ... hence if we init a ptr from start of LL & another from meeting point, they'll meet at x.
     *
     * Runtime = O(n)
     */

    static class LinkedList {
        Node head, tail;

        LinkedList(int v) {
            head = new Node(v);
            tail = head;
        }

        Node add(int v) {
            tail.next = new Node(v);
            tail = tail.next;
            return tail;
        }

        void detectRemoveLoop() {
            Node slow = head, fast = head;

            // detect loop & find meeting point node
            while (fast != null) {
                slow = slow.next;
                if (fast.next == null) {
                    System.out.println("No loop detected...");
                    break;
                }
                fast = fast.next.next;
                if (slow == fast) {
                    System.out.println("Loop detected ! Meeting point node = " + slow);
                    break;
                }
            }

            // find the cycle start node, keep track of prev so that we could unlink the loop
            slow = head;
            Node fast_prev = null;
            while (slow != fast) {
                slow = slow.next;
                fast_prev = fast;
                fast = fast.next;
            }
            System.out.println("Loop start is at node = " + slow + ", unlinking node = " + fast_prev);
            fast_prev.next = null;
        }

        void print() {
            System.out.println("Printing nodes => ");
            int cnt = 15; // to avoid infinite loop
            Node tmp = head;
            while (tmp != null && --cnt > 0) {
                System.out.print(tmp + " ");
                tmp = tmp.next;
            }
            System.out.println();
        }

    }

    static class Node {
        int val;
        Node next;
        Node(int v) {
            val = v;
        }
        public String toString() {
            return val + "";
        }
    }
}
