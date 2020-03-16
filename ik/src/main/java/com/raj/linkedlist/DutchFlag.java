package com.raj.linkedlist;

public class DutchFlag {
    /**
     * Given a linked list & a node x, partition it into such a way that:
     * <x | =x | >x
     */
    public static void main(String[] args) {

    }

    /**
     * # Init 3 ptrs: less, equal, greater as null
     * # Traverse LL, and append it to corresponding ptr if less, equal or greater than x, respectively.
     *   - Edge case: initially those ptrs may be null, hence take care of that
     * # Join the ptrs less.next = equal; equal.next = greater;
     */
}
