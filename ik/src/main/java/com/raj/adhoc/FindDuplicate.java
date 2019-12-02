package com.raj.adhoc;

import java.util.Arrays;

public class FindDuplicate {

    /**
     * Find all duplicates (preferably w/ their counts) in array which contains numbers in the range 1...n
     */
    public static void main(String[] args) {
        printDuplicates(new int[]{2,4,3,7,3,1,6}); // arr size = 7
        printDuplicates(new int[]{2,1,3,7,3,3,7}); // arr size = 7
    }

    /**
     * Brute: 2 loops - i looking for dupe in i+1...len - O(n^2)
     * Set of numbers: Add to set, return moment we find the element is contained within set - O(n) runtime, O(n) space
     * Use Array elements to represent numbers seen: (if we aren't allowed extra space)
     *  => If -ve nos aren't allowed, just set negate num at corresponding index which is equal to number i.e. -A[num]
     *     -> While setting, if we find -ve num set already, it means it's a dupe.
     *     -> Limitation is it won't find freq of occurrences
     *  => Otherwise, incr the A[num] by k=length of array. Finding original number is A[num] % k.
     *     -> We do % to curtail A[num] to size as it could have already been incremented earlier & we don't want indexOutOfBounds error
     *     -> It won't work for given problem, it'll need some adjusting, why?
     *     Problem 1 - what shud be correct k? eg. [2,4,3,7,3,1,6], size of arr = 7, num are b/w 1...7
     *      - If we incr by 7 => [9,11,10,21,10,1,13]. Reducing it to original state by doing % 7 = [2,4,3,0,3,1,6]
     *      - We lost original state because incr 7 on elem 7 yielded mod as 0.
     *      - !! IMP !! Hence, we should always take k as highest number in array(which is size) + 1 to avoid such cases.
     *     Problem 2 - But now we have idxToSet going from 0...k-1 or n & n can cause OutOfBounds as arr is 0....n-1
     *      - We can say idxToSet to be num-1, as problem states that elems are in 1...n range, so we can use 0 idx as well for elem 1 (earlier it wud have gone waste)
     *      - Essentially, the problem is all about +1 forward shifting. Had the problem been abt elems b/w 0...n-1, no adjustments were reqd.
     * [Mock Interview Question]
     */
    static void printDuplicates(int[] A) {
        System.out.println("Orig Arr : " + Arrays.toString(A));

        int k = A.length+1; // we take k as +1 as we need to accommodate value n in arr
        for (int i = 0; i < A.length; i++) {
            // % : to keep index within range, in case it was incr earlier by another num.
            // -1 : idxToSet will lie b/w 0...k-1, & k-1 is n. To avoid OOB.
            int idxToSet = (A[i]-1) % k;
            if (A[idxToSet] > k) System.out.println("Found Dupe = " + A[i]%k);
            A[idxToSet] += k;
        }
        System.out.println("Finding frequency of dupes from set Array: " + Arrays.toString(A));
        for (int i = 0; i < A.length; i++) {
            if (A[i]-k > k) System.out.println(i+1 + " -> " + A[i]/k);
        }
    }

}
