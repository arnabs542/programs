package com.raj.recursion.permutations;

import java.util.*;

public class DecodeMessages {

    /**
     A top secret message containing letters from A-Z is being encoded to numbers using the following mapping:

     'A' -> 1
     'B' -> 2
     ...
     'Z' -> 26
     You are an FBI agent. You have to determine the total number of ways that message can be decoded.
     Note: An empty digit sequence is considered to have one decoding. It may be assumed that the input contains valid digits from 0 to 9 and If there are leading 0’s, extra trailing 0’s and two or more consecutive 0’s then it is an invalid string.

     Example :
     Given encoded message "123",  it could be decoded as "ABC" (1 2 3) or "LC" (12 3) or "AW"(1 23).
     So total ways are 3.
     */
    public static void main(String[] args) {
        List<String> res = new ArrayList<>();
        populate();
        System.out.println("Map: " + map);
        decode(new int[]{1,2,3}, 0, new Stack<>(), res);
        System.out.println("Decoded Messages = " + res);
        System.out.println("Total ways = " + res.size());
    }

    private static void decode(int[] A, int i, Stack<Integer> soFar, List<String> res) {
        if (i == A.length) {
            System.out.println("Found Combination : " + soFar);
            String msg = "";
            for (int j : soFar) {
                if (!map.containsKey(j)) return;
                msg += map.get(j);
            }
            if (!msg.isEmpty()) res.add(msg);
        }

        String s = "";
        for (int j = i; j < A.length; j++) {
            s += A[j];
            soFar.add(Integer.parseInt(s));
            decode(A, j+1, soFar, res);
            soFar.pop();
        }
    }

    private static Map<Integer,Character> map = new HashMap<>();

    private static void populate() {
        if (map.isEmpty()) {
            int k = 1;
            for (char j = 'A'; j <= 'Z'; j++) {
                map.put(k++, j);
            }
        }
    }

}
