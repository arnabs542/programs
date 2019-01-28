package com.raj.scheduling.binpacking;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rshekh1
 */
public class BinPacking {

    /**
     * Pack items in minimum bins
     * Each Bin size = 10
     * Items = {10,5,5}
     * Number of Bin = 2
     */
    public static void main(String[] args) {
        System.out.println(binpack(10, Lists.newArrayList(5, 5, 10, 4, 3)));
    }

    /**
     * First fit decreasing algo
     * - sort by decreasing items size
     * - find / add bin while iterating items
     */
    private static int binpack(int binSize, List<Integer> items) {
        List<Bin> bins = new ArrayList<>();

        Collections.sort(items, Collections.reverseOrder());   // sort by decreasing order of item sizes

        bins.add(new Bin(binSize, null));    // add first bin to start
        for (int item : items) {
            if (item > binSize) {
                System.out.println("Item " + item + " is skipped as it exceeds max bin size !!");
                continue;
            }
            Bin bin = findBin(item, bins, binSize);
            if (bin != null) bin.put(item);
        }

        // show items by bin
        int i = 1;
        for (Bin bin : bins) System.out.println("Bin " + (i++) + " has items = " + bin.items);

        return bins.size();
    }

    private static Bin findBin(int item, List<Bin> bins, int binSize) {
        Bin foundBin = null;   // indicates when to stop internal loop for finding bin
        int i = 0;

        // iterate and find existing bins to stash this item in
        while (foundBin == null && i < bins.size()) {
            if (bins.get(i).capacityLeft >= item) {
                foundBin = bins.get(i);
                break;
            } else i++;
        }

        // otherwise add a new bin
        if (foundBin == null) {
            Bin newBin = new Bin(binSize, null);
            bins.add(newBin);
            foundBin = newBin;
        }

        return foundBin;
    }

    private static class Bin {
        public int capacityLeft;
        public List<Integer> items = new ArrayList<>();

        public Bin(int initialCapacity, Integer item) {
            capacityLeft = initialCapacity;
            if (item != null) {
                items.add(item);
                capacityLeft -= item;
            }
        }

        public void put(int item) {
            items.add(item);
            capacityLeft -= item;
        }
    }

}
