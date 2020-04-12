package com.raj.sorting;

public class StreamingMedian {

    /**
     * Find the Median of a Number Stream.
     *
     * Use 2 heaps:
     *
     *   max    min heap
     * 5 3 7    9 13 11
     *     |    |
     *    top  top
     *      \  /
     *     median
     *
     * add() - add number by comparing new number to tops of both heaps
     * rebalance() - rebalance once size diff of heaps grows by 2
     * median() - depending on size return median using the tops
     */
    public static void main(String[] args) {

    }

}
