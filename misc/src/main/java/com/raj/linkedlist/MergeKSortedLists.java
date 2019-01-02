package com.raj.linkedlist;


import com.raj.linkedlist.MergeTwoSortedLists.Node;

import static com.raj.linkedlist.MergeTwoSortedLists.sortedMerge;

/**
 * @author rshekh1
 */
public class MergeKSortedLists {

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
        int n = 4; // Number of elements in each list

        Node[] arr = new Node[k];

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
        Node head = mergeKLists(arr);
        printList(head);
    }

    /**
     * This approach doesn’t require extra space for heap and works in O(nk Log k)
     *
     * We already know that merging of two linked lists can be done in O(n) time and O(1) space
     * The idea is to pair up K lists and merge each pair in linear time using O(1) space.
     * After first cycle, K/2 lists are left each of size 2*N.
     * After second cycle, K/4 lists are left each of size 4*N and so on.
     * We repeat the procedure until we have only one list left.
     */
    private static Node mergeKLists(Node[] arr) {
        int remain = arr.length - 1;

        while (remain > 0) {     // repeat until only one list is left
            int i = 0, j = remain;

            // (i, j) forms a pair
            while (i < j) {
                // merge List i with List j and store merged list in List i
                arr[i] = sortedMerge(arr[i], arr[j]);

                // consider next pair
                i++; j--;

                // If all pairs are merged, update last
                if (i >= j) remain = j;
            }
        }

        return arr[0];
    }

    static void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

}
