package com.raj.datastructure;

import com.raj.linkedlist.MergeTwoSortedLists.Node;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * @author rshekh1
 */
public class MergeKSortedList {

    /**
     * Given K sorted linked lists of size N each, merge them and print the sorted output.
     *
     * Example:
     *
     * Input: k = 3, n =  4
     * list1 = 1->3->5->7->NULL
     * list2 = 2->4->6->8->NULL
     * list3 = 0->9->10->11
     *
     * Output:
     * 0->1->2->3->4->5->6->7->8->9->10->11
     *
     * https://www.geeksforgeeks.org/merge-k-sorted-linked-lists/
     */
    public static void main(String[] args) {
        int k = 3; // Number of linked lists

        Node arr[]=new Node[k];

        arr[0] = new Node(1);
        arr[0].next = new Node(3);
        arr[0].next.next = new Node(5);
        arr[0].next.next.next = new Node(7);

        arr[1] = new Node(2);
        arr[1].next = new Node(4);
        arr[1].next.next = new Node(6);
        arr[1].next.next.next = new Node(8);

        arr[2] = new Node(0);
        arr[2].next = new Node(9);
        arr[2].next.next = new Node(10);
        arr[2].next.next.next = new Node(11);

        // Merge all lists
        Node start = mergeKLists(arr);
        printList(start);
    }

    /**
     * Following is detailed algorithm. Time complexity: O(nk log k)
     * 1. Create an output array of size n*k.
     * 2. Create a min heap of size k and insert 1st element in all the arrays into the heap
     * 3. Repeat following steps n*k times.
     *      a) Get minimum element from heap (minimum is always at root) and store it in output array.
     *      b) Replace heap root with next element from the array from which the element is extracted.
     *      If the array doesn’t have any more elements, then replace root with infinite.
     *      After replacing the root, heapify the tree.
     */
    public static Node mergeKLists(Node[] arr) {
        int k = arr.length;
        PriorityQueue<HeapNode> minHeap = new PriorityQueue<>(k, new Comparator<HeapNode>() {
            @Override
            public int compare(HeapNode o1, HeapNode o2) {
                if (o1 == null) return 1;
                if (o2 == null) return -1;
                if (o1.node.data == o2.node.data) return 0;
                else if (o1.node.data < o2.node.data) return -1;
                else return 1;
            }
        });
        Node result = new Node(0);
        Node start = result;

        for (int i = 0; i < arr.length; i++) {  // add first element from each list
            minHeap.add(new HeapNode(arr[i], i));
        }

        while (true) {
            HeapNode heapNode = minHeap.poll();
            if (heapNode.node == null) break;
            result.next = heapNode.node;
            minHeap.add(new HeapNode(arr[heapNode.i].next, heapNode.i));
            result = result.next;
        }

        return start;
    }

    private static class HeapNode {
        Node node; // The element to be stored
        int i; // index of the array from which the element is taken

        HeapNode(Node n, int ii) {
            node = n;
            i = ii;
        }

        @Override
        public String toString() {
            return "HeapNode{" +
                    "node=" + node +
                    ", i=" + i +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HeapNode heapNode = (HeapNode) o;
            return i == heapNode.i &&
                    Objects.equals(node, heapNode.node);
        }

        @Override
        public int hashCode() {
            return Objects.hash(node, i);
        }
    }

    public static void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}
