package com.raj.adhoc;

public class SumOfCubes {
    /**
     * For numbers ranging from 1 to 1000, print a,b,c,d where a^3 + b^3 = c^3 + d^3
     *
     * # Brute: O(n^4)
     * for a = 1 to n
     *  for b = 1 to n
     *   for c = 1 to n
     *    for d = 1 to n
     *     if (cond) print a,b,c,d
     *
     * # Some Maths: How can we reduce loops?
     * a^3 + b^3 - c^3 = d^3
     * i.e. d = 1/3 root over a^3 + b^3 - c^3
     * 3 loops for a,b,c
     *   d = Math.pow(a^3 + b^3 - c^3, 1/3)
     *   print a,b,c,d
     * Time = O(n^3)
     *
     * # Pre-compute for 1 to n: map of c^3 + d^3 -> List[(c,d), ...]
     * Now, for a,b cube sum, lookup map
     * for c = 1 to n
     *  for d = 1 to n
     *   map.put(a^3 + b^3, (c,d) pair)
     *
     * for a = 1 to n
     *  for b = 1 to n
     *   map.get(a^3 + b^3).forEach(pr -> print a,b,c,d)
     * Time = O(n^2) w/ O(n^2) space
     *
     * Could also just iter map & for each key, print pair list combinations
     * i.e. map.forEach((k,v) -> v has pair1, pair2, pair3, then print pair1,pair2 then pair1,pair3 then pair2,pair3 ...)
     * same runtime though
     *
     */

}
