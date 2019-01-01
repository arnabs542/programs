package com.raj.hashing;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * @author rshekh1
 */
public class PointsOnStraightLine {

    /**
     * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line
     */
    public static void main(String[] args) {
        System.out.println(maxPoints(Lists.newArrayList(0,1,2,2,2), Lists.newArrayList(0,1,2,2,2)));
        System.out.println(maxPoints(Lists.newArrayList(0,1,2,4,6), Lists.newArrayList(0,1,2,5,9)));
    }

    /**
     * For each point find the slope with another point
     * Keep adding it to the map of slope -> List of points
     * Count the points for slope and keep extending max. Clear map for every new i iteration
     */
    public static int maxPoints(ArrayList<Integer> a, ArrayList<Integer> b) {
        if (a.size() <= 2) return a.size();
        Map<Double, Set<String>> map = new HashMap<>();
        int max = 0;
        for (int i=0; i<a.size(); i++) {
            map.clear();
            for (int j = i + 1; j < a.size(); j++) {
                int x1 = a.get(i);
                int y1 = b.get(i);
                int x2 = a.get(j);
                int y2 = b.get(j);
                double n = (y2 / 1d) - (y1 / 1d);
                double d = (x2 / 1d) - (x1 / 1d);
                Double slope = (d == 0) ? 99999999d : n / d;
                Set<String> points = map.get(slope);
                if (points == null) points = new HashSet<>();
                points.add(x1 + "," + y1);
                points.add(x2 + "," + y2);
                map.put(slope, points);
                if (points.size() > max) max = points.size();
            }
        }
        return max;
    }

}
