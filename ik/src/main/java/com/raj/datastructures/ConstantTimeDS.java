package com.raj.datastructures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConstantTimeDS {

    /**
     * Design a DS which supports following operations in O(1) time & linear space:
     * void add(int x)
     * void remove(int x)
     * boolean contains(int x)
     * int getRandom()
     */
    public static void main(String[] args) {
        HybridArray hybridArray = new HybridArray();
        hybridArray.add(4);hybridArray.add(6);hybridArray.add(3);hybridArray.add(5);
        System.out.println(hybridArray.contains(3));
        System.out.println(hybridArray);
        System.out.println(hybridArray.getRandom());
        System.out.println(hybridArray.getRandom());
        hybridArray.remove(6);
        System.out.println(hybridArray.contains(6));
        System.out.println(hybridArray.getRandom());
        System.out.println(hybridArray);
    }

    /**
     * Array operations support O(1) time for add, remove (if we track size like a stack) & getRandom()
     * But contains takes linear time. What if we combine it with a hashMap?
     * DS => int[] A, Map<Integer,Integer> map   (Map's value tracks index in array for deletion later)
     *
     * After adding 4,6,3,5 =>
     * A = {4,6,3,5}
     * Map(Num->ArrIdx) = { 4->0, 6->1, 3->2, 5->3 }
     * contains is map.containsKey()
     * getRandom is A[random idx]
     *
     * remove(6) => remove from map, take it's arr idx and swap with A's last element. Update idx for swapped element in map.
     * A = {4,5,3}
     * Map(Num->ArrIdx) = { 4->0, 3->2, 5->1 }
     */
    static class HybridArray {
        int[] A = new int[100];
        int len = -1;
        Map<Integer,Integer> map = new HashMap<>();

        void add(int x) {
            A[++len] = x;
            map.put(x,len);
        }

        void remove(int x) {
            if (!map.containsKey(x)) return;
            int idx = map.get(x);
            map.remove(x);
            A[idx] = A[len]; len--;
            map.put(A[idx],idx);
        }

        boolean contains(int x) {
            return map.containsKey(x);
        }

        int getRandom() {
            int idx = (int) Math.ceil(Math.random()*len);
            System.out.println("idx="+idx);
            return A[idx];
        }

        public String toString() {
            return "Arr: " + Arrays.toString(Arrays.copyOfRange(A, 0, len+1)) + ", map: " + map;
        }

    }

}
