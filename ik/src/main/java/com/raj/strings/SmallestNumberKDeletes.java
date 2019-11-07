package com.raj.strings;

public class SmallestNumberKDeletes {

    /**
     * Find the smallest number with k digit deletes
     * 23194, k=2
     *   194
     *
     * 3194, k=2
     *  1 4
     */
    public static void main(String[] args) {
        System.out.println(findSmallestNum("23194", 2));
        System.out.println(findSmallestNum("523194", 2));
        System.out.println(findSmallestNum("3194", 2));
    }

    /**
     * Algo: Sliding Window - Pick Min
     * # Create a sliding window of length k+1 & keep picking min & move forward.
     * # Decrement k when leading chars not considered
     * # Stop when k = 0.
     * Runtime = O(n)
     */
    static int findSmallestNum(String s, int k) {
        StringBuilder res = new StringBuilder();
        int i = 0, del = 0;
        while (del<k && i<s.length()) {
            int min = Integer.MAX_VALUE, minIdx = -1;
            int window = k+1; // window length
            int start = i;
            while (window > 0 && i<s.length()) {
                int digit = Integer.parseInt(s.charAt(i)+"");
                if (digit < min) {
                    min = digit;  // new min digit in this window
                    minIdx = i;
                }
                window--; i++;
            }
            del += minIdx - start; // we skipped this digit
            res.append(min);  // append min in this window
            i = minIdx + 1;   // slide i to min + 1 & start of a new window
        }

        // append left over digits if del = k caused loop to exit
        while (i<s.length()) res.append(s.charAt(i++));

        return Integer.valueOf(res.toString());
    }

}
