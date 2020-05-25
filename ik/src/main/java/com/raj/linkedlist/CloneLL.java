package com.raj.linkedlist;

import java.util.HashMap;
import java.util.Map;

public class CloneLL {
    /**
     * Given a LL which may have a secondary ptr, clone it.
     *
     *      |---------|
     * 1 -> 2 -> 3 -> 3 -> 3 -> 6
     *           |---------|
     *
     * # Since, a secondary ptr exists and nodes can be duplicate, we'll need to resolve it using address.
     * # Create clone of each node, don't link them yet
     * # Store ref of original node -> clone in map
     * # Traverse original list, grab the corresponding clone from map and link.
     * # For secondary ptrs, find it's clone as well and link it.
     */
    static Node clone(Node origPtr) {
        Node origHead = origPtr;
        Map<Node,Node> cloneMap = new HashMap<>();
        Node cloneHead = new Node(origHead.val);
        cloneMap.put(origHead,cloneHead);
        origPtr = origPtr.next;

        // create orig->clone map first
        while (origPtr != null) {
            cloneMap.put(origPtr,new Node(origPtr.val));
            origPtr = origPtr.next;
        }

        // link next & sec_next
        origPtr = origHead;
        Node clonePtr = cloneHead;
        while (origPtr.next != null) {
            clonePtr.next = cloneMap.get(origPtr.next);
            if (origPtr.sec_next != null) clonePtr.sec_next = cloneMap.get(origPtr.sec_next);
            origPtr = origPtr.next;
            clonePtr = clonePtr.next;
        }
        return cloneHead;
    }

    static class Node {
        int val;
        Node next,sec_next;
        Node(int v) {
            val = v;
        }
    }
}
