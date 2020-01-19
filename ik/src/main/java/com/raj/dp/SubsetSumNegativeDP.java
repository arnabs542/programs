package com.raj.dp;

import java.util.*;

public class SubsetSumNegativeDP {

    public static void main(String[] args) {
        System.out.println(equalSubSetSumPartition(Arrays.asList(1,3,2)));
    }

    public static List<Boolean> equalSubSetSumPartition(List<Integer> s) {
        List<Boolean> solution = new ArrayList<>();
        int positivesSum = 0, negativesSum = 0;
        int n = s.size();

        for (int i : s) {
            if (i >= 0) positivesSum += i;
            else negativesSum += i;
        }

        // Could be something like 30 + -14 == 16, so then each half would need 8
        if ( (positivesSum + negativesSum) % 2 > 0) return solution;

        // Index of the array is for the index in the list. The key in HashMap is the sum of group1 --> true or false
        Map<Integer, Boolean>[] dpTable = new DefaultHashMap[n];
        for(int i = 0; i < n; i++) {
            dpTable[i] = new DefaultHashMap<>(false);
        }

        // is it possible to have any of the sums in this smallest to biggest sum range, as of index 0.
        // base state
        // For index 0, only one sum (s.get(0)) is possible
        dpTable[0].put(s.get(0), true);

        // no additional subproblems at index 0 to solve
        for(int i = 1; i < n; i++) {
            // Every possible value that the nums in group 1 could sum up to
            for (int val = negativesSum; val <= positivesSum; val++) {
                /*
                 1. if it was possible to get this sum at the previous index, then we know it's definitely
                 possible to get it now. So we'll add that in now if it's true. If it's
                 false we have a chance to check to see if "hey at this index now we can get this sum in group1!"
                 */
                dpTable[i].put(val, dpTable[i - 1].get(val));

                /*
                 2. If the possible sum is equal to the element at this index, then we can for sure
                 achieve this sum for group 1
                 */
                if (val == s.get(i)) {
                    dpTable[i].put(val, true);
                } else if (val - s.get(i) - negativesSum >= 0) {
                    /*
                    elements: [3, 1, 2, 6, 1]
                    Most negative: 0
                    Most positive sum: 13
                    Range of values: 0 - 13
                    index 3
                     a. was this group1 sum attainable at the previous index (see line before the if statements)
                     b. was it possible to get this sum by a previous sum found at the last index + taking the current value

                     So let's say we're at index 3, and we look at each value from 0 to 13 each time...
                     index 3, val 8.

                     1st thing:
                     Could we reach a sum of 8 from the previous index? (only looking at indices 0 thru 2). No.
                     2nd thing:
                     Is element 3 equal to 8? Because then we for sure could get a group1Sum of 8. (if group1Sum was 0
                     from never taking any elements + 8 for the current one)
                     3rd thing:
                     if the 1st thing wasn't true (first part of the or), then could we reach this sum of 8 by choosing to take the current element,
                     plus seeing if a group1Sum of 2 was achievable?..
                     Because if a sum of 2 was achievable previously (which you could have had a 2 all the way back at index 0 and
                     that would have persisted forward up to the dpTable entry at index 2 (dpTable[2]) or even two 1's previously),
                     and if we have a "6" currently that we're sitting on, then we for sure could achieve this group1Sum of 8.
                     */
                    dpTable[i].put(val, dpTable[i].get(val) || dpTable[i - 1].get(val - s.get(i)));
                }
            }
        }

        /*
        Now we have a fully created dpTable, and we just need to determine how to trace our path back to our starting
        index to have equal groups.
        We have equal groups if we could trace a valid path that
        gives group1 a sum of half the combined sum...
        Which would mean that in turn group2 is holding an equal amount of sum as well.
         */

        /*
        4 6 -3 3 8 2
        Range: -3 to 23
        Total: 20
        Equal partition puts 10 in each group
         */
        int requiredSum = (positivesSum + negativesSum) / 2;
        int i = n - 1;

        System.out.println(Arrays.toString(dpTable));
        /*
        If we could never reach a group1Sum of half the whole sum (required sum) by the end,
        then we know that no path existed to split the 2 groups into equal sum sets of elements
         */
        if(!dpTable[i].get(requiredSum)) {
            return solution;
        }

        // To make sure the list is the exact size as N, and we can set values
        // true or false when desired for the solution list
        solution = new ArrayList<>(Arrays.asList(new Boolean[n]));
        Collections.fill(solution, false);
        int count = 0;
        while(i >= 0) {
            if(i != 0) {

                if(dpTable[i].get(requiredSum) && dpTable[i - 1].get(requiredSum) == Boolean.FALSE) {
                    solution.set(i, Boolean.TRUE);
                    count++;
                    requiredSum -= s.get(i);
                    if(requiredSum == 0)
                        break;
                }

            } else {
                solution.set(i, Boolean.TRUE);
                count++;
            }
            i--;
        }

        /*
        We want to make sure that not all elements are included in group1
        Ex: if total sum == 0, and our list is [-2, 2],
        Then the partition wouldn't be possible in that case.
         */
        return count == n || count == 0 ? new ArrayList<>() : solution;
    }

    static class DefaultHashMap<K, V> extends HashMap<K, V> {
        protected V defaultValue;
        public DefaultHashMap(V defaultValue) {
            this.defaultValue = defaultValue;
        }

        @Override
        public V get(Object k) {
            return containsKey(k) ? super.get(k) : defaultValue;
        }
    }

}

