package com.raj.linkedlist;

public class SwapKthNode {
    /**
     * Swap kth Nodes In Given Linked List
     * Given an integer singly linked list L of size n, and an integer k, you have to swap kth (1-indexed) node from
     * the beginning, with kth node from the end. Note that you have to swap the nodes themselves, not just the contents.
     * <p>
     * Example:
     * List: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> NULL, k = 2
     * Output: 1 -> 5 -> 3 -> 4 -> 2 -> 6 -> NULL
     */
    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            add(i);
        }
        print();
        swap_nodes(head, 2);
        print();
    }

    /**
     * # Move ptr1 to kth node
     * # Move ptr2 to kth node from end by using a ptr2=head & tmp=ptr1
     *   - when tmp goes null, ptr2 will be at desired pos
     * # To swap we just to rearrange prev & next ptrs of node
     * Edge case: what if ptr1 is head with null prev? Similarly, what if ptr2 is head as well?
     */
    static Node swap_nodes(Node head, int k) {

        // bring ptr1 to desired place - kth node from start
        Node prev1 = null, ptr1 = head;
        while (k > 1) {
            prev1 = ptr1;
            ptr1 = ptr1.next;
            k--;
        }

        // bring ptr2 to kth node from end - tmp goes null if it's k dist apart
        Node prev2 = null, ptr2 = head;
        Node tmp = ptr1;
        while (tmp.next != null) {
            tmp = tmp.next;
            prev2 = ptr2;
            ptr2 = ptr2.next;
        }

        // 1. Swap prev ptrs

        // if prev1 is null, after swap ptr2 will become head
        if (prev1 == null) head = ptr2;  // edge case
        else prev1.next = ptr2;

        // if prev1 is null, after swap ptr2 will become head
        if (prev2 == null) head = ptr1;  // edge case
        else prev2.next = ptr1;

        // 2. Swap ptr's next - exactly follows the swap variable A & B logic
        tmp = ptr1.next;
        ptr1.next = ptr2.next;
        ptr2.next = tmp;
        return head;
    }

    private static Node head, tail;

    static Node add(int v) {
        Node node = new Node(v);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        return head;
    }

    static void print() {
        Node tmp = head;
        while (tmp != null) {
            System.out.print(tmp.val + " ");
            tmp = tmp.next;
        }
        System.out.println();
    }

    static class Node {
        int val;
        Node next;
        Node(int v) {
            val = v;
        }
    }

}
