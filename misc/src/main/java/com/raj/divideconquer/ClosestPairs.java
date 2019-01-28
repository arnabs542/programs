package com.raj.divideconquer;

/**
 * @author rshekh1
 */
public class ClosestPairs {

    /**
     * https://www.geeksforgeeks.org/closest-pair-of-points-using-divide-and-conquer-algorithm/
     *
     * Sort by points X co-ordinates - O(nlogn)
     * Recurse:
     *  - if points size <= 3, use brute force to find minDist (since n=3, is still less than O(n^2))
     *  - divide the list of points into 2 sublists by finding the mid of points list -> n/2
     *  - again sort the sublists by X co-ordinates
     *  - Recurse which returns minDist of left sublist
     *  - Recurse which returns minDist of right sublist
     *  - find min of both dist above, lets call it sigma
     *  - find points within +- sigma(either side) of mid
     *  - apply brute force to find minDist for this sigmaStrip (only for points whose y dist is less than sigma)
     *  - minDist is now the min of minSigmaStrip and minDist of left&right sublist computed above
     */
}
