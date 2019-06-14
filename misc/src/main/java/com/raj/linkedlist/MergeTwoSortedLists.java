package com.raj.linkedlist;

import java.util.Objects;

/**
 * @author rshekh1
 */
public class MergeTwoSortedLists {
    /**
     * Merge two sorted linked lists
     * https://www.geeksforgeeks.org/merge-two-sorted-linked-lists/
     * <p>
     * For example if the first linked list a is 5->10->15 and the other linked list b is 2->3->20,
     * then SortedMerge() should return a pointer to the head node of the merged list 2->3->5->10->15->20.
     */

    public static void main(String[] args) {
        Linkedlist llist1 = new Linkedlist();
        Linkedlist llist2 = new Linkedlist();

        llist1.addToTheLast(new Node(5));
        llist1.addToTheLast(new Node(10));
        llist1.addToTheLast(new Node(15));

        llist2.addToTheLast(new Node(2));
        llist2.addToTheLast(new Node(3));
        llist2.addToTheLast(new Node(20));

        //llist1.head = sortedMerge(llist1.head, llist2.head);
        //llist1.printList();

        llist1.head = sortedMergeRecursive(llist1.head, llist2.head);
        llist1.printList();
    }

    // O(2n)
    public static Node sortedMerge(Node A, Node B) {
        Node mergedList = new Node(0);      // sentinel node to hang the merged list
        Node start = mergedList;               // save start node for return
        while (A != null || B != null) {
            // append the remaining list, if the other list ends and finish processing
            if (A == null) { mergedList.next = B; break; }
            if (B == null) { mergedList.next = A; break; }
            // attach the next lowest node to mergedList
            if (A.data < B.data) {
                mergedList.next = A;
                A = A.next;
            } else {
                mergedList.next = B;
                B = B.next;
            }
            mergedList = mergedList.next;   // move the current ptr of mergedList as it's processed already
        }
        return start.next;
    }

    /**
     * Merge is one of those nice recursive problems where the recursive solution code is much cleaner than the
     * iterative code.
     * NOTE: You probably wouldn’t want to use the recursive version for production code
     * because it will use recursionStack space which is proportional to the length of the lists.
     */
    public static Node sortedMergeRecursive(Node A, Node B) {
        // BASE Case
        if (A == null) return B;    // the other list is empty
        if (B == null) return A;

        // Recursive case
        Node result;    // result of combined lists
        if (A.data <= B.data) {
            result = A;
            result.next = sortedMergeRecursive(A.next, B);  // move A as it was processed
        } else {
            result = B;
            result.next = sortedMergeRecursive(A, B.next);
        }
        return result;
    }

    public static class Node {
        public int data;
        public Node next;

        public Node(int d) {
            data = d;
            next = null;
        }

        @Override
        public String toString() {
            return data+"";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return data == node.data;
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

    public static class Linkedlist {

        Node head;

        public void addToTheLast(Node node) {
            if (head == null) head = node;
            else {
                Node temp = head;
                while (temp.next != null) temp = temp.next;
                temp.next = node;
            }
        }

        void printList() {
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.data + " ");
                temp = temp.next;
            }
            System.out.println();
        }

    }
}
