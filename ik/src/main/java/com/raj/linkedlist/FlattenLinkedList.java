package com.raj.linkedlist;

public class FlattenLinkedList {
    /**
     * Given a linked list with some nodes having child pointers which in turn is a list, flatten it into a single level-wise LL.
     */
    public static void main(String[] args) {

    }

    /**
     * 1-2-3-4-5
     *     |    \
     *     6-7   8-9
     *     |        \
     *     10-11     12-13-14
     *
     * o/p => 1-2-3-4-5-6-7-8-9-10-11-12-13-14-15
     *
     * Approach 1:
     * # Since it's a level order traversal, just use a Queue to add childs when we see one.
     * # Traverse the LL, until end of first list. Now poll from Queue and append to end of first list.
     * # Repeat process.
     * Time/Space = O(n)
     *
     * Approach 2:
     * # Prefetch the tail of first LL. Traverse LL until a child found.
     * # Append the tail to child. Prefetch tail again. Repeat.
     * Time = O(n), no aux space.
     */
    static void flatten(Node head) {
        Node curr = head;
        Node tail = getTail(curr);
        while (curr != null) { // traverse until end, appending tail to next child
            if (curr.child != null) {
                tail.next = curr.child;
                tail = getTail(curr.child);
                curr.child = null;
            }
            curr = curr.next;
        }
    }

    static Node getTail(Node n) {
        while (n.next != null) n = n.next;
        return n;
    }

    Node head = null;
    static class Node {
        int val;
        Node next, child;
    }

}
