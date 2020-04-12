package com.raj.linkedlist;

public class ReverseLLInGroups {

    /**
     * Reverse the first sub-list of size k.
     * https://www.geeksforgeeks.org/reverse-a-list-in-groups-of-given-size/
     *
     *  head = 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null , k = 3
     *         1 <- 2 <- 3    reverse(4 -> 5 -> 6)
     *  head = 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> null    ... notice how sublists are reversed & linked
     *
     * Algo:
     * # Reverse the first sub-list of size k. While reversing keep track of the next node and previous node.
     * # Recursively call for rest of the list and link the two sub-lists
     *   head->next = reverse(next, k)
     * # Return prev as it becomes the new head of the list (see the diagrams)
     */
    public static void main(String[] args) {
        addNode(1);
        addNode(2);
        addNode(3);
        addNode(4);
        addNode(5);
        addNode(6);
        addNode(7);
        addNode(8);

        print(head);
        Node newHead = reverse(head, 3);
        System.out.println("\nReversed list => ");
        print(newHead);
    }

    static Node reverse(Node head, int k) {
        if (head == null) return head;
        Node prev = null, cur = head, nxt = null;

        /**
         * Reverse first k nodes using following ptrs:
         * prev -> cur -> nxt
         * prev <- cur    ... point cur to prev
         * node <- prev   ... move forward prev & cur
         */
        int i = 0;
        while (i<k && cur != null) {
            nxt = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nxt;
            i++;
        }

        /**
         * == New State ==
         * head       prev   cur
         *   1 <- 2 <- 3      4 -> 5 -> 6 -> null
         *
         * prev      head             prev  cur
         *   3 -> 2 -> 1      4 <- 5 <- 6   ...... reverse next k nodes. Prev now becomes head of reversed sublist.
         *
         * prev      head    prev
         *   3 -> 2 -> 1  ->  6 -> 5 -> 4   ...... link head.next to reverse(cur,k) which will return head of the next sublist
         *
         * return prev (as its always the head of the reversed sublist)
         */
        head.next = reverse(cur, k);
        return prev;
    }

    static Node head, tail;
    static void addNode(int v) {
        Node newNode = new Node(v);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    static class Node {
        int val;
        Node next;
        Node(int v) {
            val = v;
        }
        public String toString() {
            return val + " ->";
        }
    }

    static void print(Node n) {
        Node tmp = n;
        while (tmp != null) {
            System.out.print(tmp + " ");
            tmp = tmp.next;
        }
        System.out.print("null");
    }
}
