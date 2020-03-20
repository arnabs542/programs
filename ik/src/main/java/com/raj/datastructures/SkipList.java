package com.raj.datastructures;

public class SkipList {
    /**
     * Search in a sorted Linked List, can we do better than O(n)?
     * # Binary Search won't work as we'll need to traverse nodes to get to mid element etc - as LL doesn't support random access on index.
     * # We can use "Skip List" for getting O(logn) performance:
     *   - The idea is simple, we create multiple layers on top of original list so that we can skip nodes.
     *   - Build n/2, n/4....2 nodes as skip list:
     *
     *   k =>  1-------------------------------------------->100   whether element exists at all?
     *         |                                              |
     *   .     1---------->25--------->50--------->75------->100   minimize the search range as we go down
     *         |           |                                  |
     *   1 =>  1--->12---->25  ....    ....             ---->100
     *         ...
     *   0 =>  1->2->3->4   ...                  ... 98->99->100
     *
     *   0..k List Index contains heads of each Skip List. 0th LL is the original list containing all numbers.
     *   Now, searching 98 wud mean traversing thru skip list path = 1,1,25,50,75..98  (close to 10 or log 100)
     *
     * # DS:
     * ArrayList<SkipNode> heads
     *
     * SkipNode {
     *     int data;
     *     SkipNode next, child;
     * }
     *
     * insert(data,level=0):
     *      add(data,level)
     *      if(Math.random() > 0.5):        ... 50% probability of adding a node to each of the above level, hence each level is 50% less dense.
     *         insert(data,l+1)
     *
     * # Since, only one node out of so many nodes will be written at a time, it provides for building lock-less DS & high concurrent reads/writes.
     */
}
