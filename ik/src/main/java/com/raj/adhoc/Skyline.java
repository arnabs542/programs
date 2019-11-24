package com.raj.adhoc;

import java.util.*;

/**
 * @author rshekh1
 */
public class Skyline {

    /**
     * Given n buildings on a 2D plane, find the skyline of these buildings. Each building on the 2D plane has a
     * start coordinate, end coordinate, and height. The skyline is defined as a unique representation of rectangular
     * strips of different heights which are created after the overlap of multiple buildings in the 2D plane.
     * <p>
     * 2,9 -> 10
     * 3,7 -> 15
     * 5,12 -> 12
     * 15,20 -> 10
     * 19,24 -> 8
     * <p>
     * Sample Output:
     * 2 10
     * 3 15
     * 7 12
     * 12 0
     * 15 10
     * 20 8
     * 24 0
     */
    public static void main(String[] args) {
        System.out.println(findSkyline(Arrays.asList(
                Arrays.asList(1,2,3),
                Arrays.asList(2,3,3),
                Arrays.asList(3,4,3),
                Arrays.asList(4,5,3)
        )));
        System.out.println(findSkyline(Arrays.asList(
                Arrays.asList(2,9,10),
                Arrays.asList(3,7,15),
                Arrays.asList(5,12,12),
                Arrays.asList(15,20,10),
                Arrays.asList(19,24,8)
        )));
    }

    /**
     * Approach 1:
     * Sort the input by start index
     * For each (x,y,h) keep updating end idx & keep track of height
     * Iterate left -> right looking for points where increase in height occurs (ignore drops for now)
     * <p>
     * For each (x,y,h) keep updating start idx & keep track of height
     * Iterate right -> left looking for points where decrease in height occurs (ignore increase in this case)
     * <p>
     * Sort the final list of points where height changes occurred from prev 2 iterations
     * <p>
     * O(nlogn)
     */
    public static List<List<Integer>> _findSkyline(List<List<Integer>> A) {
        if (A == null || A.size() == 0) return A;
        List<List<Integer>> res = new ArrayList<>();

        Collections.sort(A, (a, b) -> {
            if (a.get(0) == b.get(0)) return b.get(2).compareTo(a.get(2));
            else return a.get(0).compareTo(b.get(0));
        });

        // go left to right noting the points of ascent
        int h = 0, e = 0;
        for (List<Integer> l : A) {
            int _s = l.get(0), _e = l.get(1), _h = l.get(2);
            if (_s > e) h = 0; // height dropped to zero, we need to note it to detect increase in height

            // height increased, update new max height
            if (_h > h) {
                h = _h;
                res.add(Arrays.asList(_s, h)); // add point of ascent
            }
            e = Math.max(e, _e);  // update end idx
        }

        Collections.sort(A, (a, b) -> {
            if (b.get(0) == a.get(0)) return a.get(2).compareTo(b.get(2));
            else return b.get(0).compareTo(a.get(0));
        });

        // go right to left noting points of drops
        h = 0; int s = Integer.MAX_VALUE;
        for (int i=A.size()-1; i>=0; i--) {
            List<Integer> l = A.get(i);
            int _s = l.get(0), _e = l.get(1), _h = l.get(2);
            if (s > _e) h = 0; // height dropped to zero, we need to note it to detect decrease in height

            // height dropped at this point
            if (_h > h) {
                res.add(Arrays.asList(_e, h)); // add point of descent
                h = _h;
            }
            s = Math.min(s, _s);  // update end idx
        }

        Collections.sort(res, (a,b) -> a.get(0).compareTo(b.get(0)));
        return res;
    }

    /**
     * Approach 2:
     * Intuition is index & height is only printed when there is an increase or decrease (change) in height.
     * Also, engulfed bldg heights are not points of interest as we are looking at edges.
     * Which means we need some kind of DS w/ some ordering & priority => PriorityQueue
     *
     * # Break down list into singular index -> height pairs. Also track whether it's start or end.
     * # Sort this list by start index (edge cases?)
     *   - if index same, then give pref to greater height
     *   - startIdx takes pref over endIdx
     * # Use a Max Heap to track heights. Use another ptr to keep track of current height (this is to detect height changes)
     * # Iterate & push height into PQ when startIdx & pop when endIdx (bldg end has elapsed, so remove)
     * # If height changes (current height vs PQ.peek()), print the index -> height pair
     */
    public static List<List<Integer>> findSkyline(List<List<Integer>> buildings) {
        List<List<Integer>> res = new ArrayList<>();
        List<Building> inputs = new ArrayList<>();
        // pre-process input
        buildings.forEach(x -> {    // build a building list
            int _s = x.get(0), _e = x.get(1), _h = x.get(2);
            inputs.add(new Building(_s, _h, true));
            inputs.add(new Building(_e, _h, false));
        });
        Collections.sort(inputs, (a,b) -> {    // sort by index, giving pref to height & startIdx, if index same
            if (a.index == b.index) {
                if (a.isStart == b.isStart) return b.height - a.height;  // height tie-breaker
                else return a.isStart ? -1 : 1; // give pref to startIdx if an endIdx is being compared
            }
            else return a.index - b.index;  // lesser index gets pref, in general
        });
        PriorityQueue<Integer> heightPQ = new PriorityQueue<>((a,b) -> b-a);
        heightPQ.add(0);
        int currentHeight = 0;
        for (Building x : inputs) {
            if (x.isStart) heightPQ.add(x.height);
            else heightPQ.remove(x.height);
            if (heightPQ.peek() != currentHeight) {
                res.add(Arrays.asList(x.index, heightPQ.peek()));
                currentHeight = heightPQ.peek();
            }
        }
        return res;
    }

    public static List<List<Integer>> __findSkyline(List<List<Integer>> buildings) {

        List<List<Integer>> result = new ArrayList<> ();
        if (buildings == null || buildings.isEmpty()) {
            return result;
        }

        List<Building> allBuildings = new ArrayList<> ();
        for (List<Integer> building : buildings) {
            allBuildings.add(new Building(building.get(0), building.get(2), true));
            allBuildings.add(new Building(building.get(1), building.get(2), false));
        }

        // sort the data by the indexes. if two buildings have the same index, return the order with a higher preference to height.
        Collections.sort(allBuildings, (b1, b2) -> {
            if (b1.index == b2.index) {
                return (b1.isStart ? -b1.height : b1.height) - (b2.isStart ? -b2.height : b2.height);
            } else {
                return b1.index - b2.index;
            }
        });

        Queue<Integer> heightQ = new PriorityQueue<> ((f, s) -> s - f);
        heightQ.add(0);
        int currentMax = 0;
        for (Building building : allBuildings) {
            if (building.isStart) {
                heightQ.add(building.height);
            } else {
                heightQ.remove(building.height);
            }
            int newMax = heightQ.peek();
            if (newMax != currentMax) {
                result.add(Arrays.asList(building.index, newMax));
                currentMax = newMax;
            }
        }
        return result;
    }

    static class Building {
        int height;
        int index;
        boolean isStart;

        public Building(int index, int height, boolean isStart) {
            this.index = index;
            this.height = height;
            this.isStart = isStart;
        }

        @Override
        public String toString() {
            return index + "," + height + "," + isStart;
        }
    }

}
