package com.raj.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMP {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(KMP("DAABCAACAAABD", "AAB")));
    }

    // https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/
    static int[] KMP(String inputS, String patternS) {
        if(inputS == null || inputS.length() == 0 || patternS == null || patternS.length() == 0) {
            return new int[]{-1};
        }
        char[] input = inputS.toCharArray();
        char[] pattern = patternS.toCharArray();
        int[] lps = buildLps(pattern);
        List<Integer> matchIndexes = new ArrayList();

        int i =0, j =0;
        while (i < input.length && j < pattern.length) {
            if(input[i] == pattern[j]) {
                i++;
                j++;
                if(j == pattern.length) {
                    matchIndexes.add(i - j);
                    j = lps[j-1];
                }
            } else {
                if(j != 0) {
                    j = lps[j-1];
                } else {
                    i++;
                }
            }
        }
        if(matchIndexes.isEmpty()) {
            return new int[]{-1};
        } else {
            int[] output = new int[matchIndexes.size()];
            for(int count=0; count<matchIndexes.size();count++) {
                output[count]= matchIndexes.get(count);
            }
            return output;
        }

    }

    static int[] buildLps(char[] pattern) {
        int len = 0;
        //AAABAAA - 0,1,2,0,1,2,3
        //abcde
        int[] lps= new int[pattern.length];
        for(int i=1; i < pattern.length; ){
            if(pattern[len] == pattern[i]) {
                lps[i] = lps[len] + 1;
                len ++;
                i++;
            } else {
                if(len != 0) {
                    len = lps[len - 1];
                }
                if(len == 0) {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

}
