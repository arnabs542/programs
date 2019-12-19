package com.raj.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TowerOfHanoi {

    /**
     * https://www.geeksforgeeks.org/c-program-for-tower-of-hanoi/
     * https://www.youtube.com/watch?time_continue=227&v=YstLjLCGmgg&feature=emb_logo
     *
     * Take an example for 2 disks :
     * Let rod 1 = 'A', rod 2 = 'B', rod 3 = 'C'.
     *
     * Step 1 : Shift first disk from 'A' to 'B'.
     * Step 2 : Shift second disk from 'A' to 'C'.
     * Step 3 : Shift first disk from 'B' to 'C'.
     *
     * The pattern here is :
     * Shift 'n-1' disks from 'A' to 'B'.
     * Shift last disk from 'A' to 'C'.
     * Shift 'n-1' disks from 'B' to 'C'.
     *
     * Input : 3
     * Output : Disk 1 moved from A to C
     *          Disk 2 moved from A to B
     *          Disk 1 moved from C to B
     *          Disk 3 moved from A to C
     *          Disk 1 moved from B to A
     *          Disk 2 moved from B to C
     *          Disk 1 moved from A to C
     */
    public static void main(String[] args) {
        move(3, "A", "C", "B");
        System.out.println(tower_of_hanoi(4));
    }

    // Branching factor = 2 for n-1, T(n) = 2 * T(n - 1) + O(1)
    // Hence, Runtime = O(2^n)
    static void move(int n, String from, String to, String using) {
        if (n == 1) {
            System.out.println("Move " + n + " from rod " + from + " to rod " + to);
            return;
        }
        // solve n-1 smaller problems
        move(n-1, from, using, to);
        System.out.println("Move " + n + " from rod " + from + " to rod " + to);
        move(n-1, using, to, from);
    }

    public static List<List<Integer>> tower_of_hanoi(int n) {
        List<List<Integer>> res = new ArrayList<>();
        move(n, 1, 3, 2, res);
        return res;
    }

    static void move(int n, int from, int to, int using, List<List<Integer>> res) {
        if (n == 1) {
            res.add(Arrays.asList(from, to));
            return;
        }
        move(n-1, from, using, to, res);
        res.add(Arrays.asList(from, to));
        move(n-1, using, to, from, res);
    }

}
