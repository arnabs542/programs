package com.raj.companies;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rshekh1 on 8/4/16.
 */
public class PermuteStr {

    /**
     encode(str) -> str
     "az" -> "126"

     encoding - FIXED
     a -> 1
     b -> 2
     ...
     z -> 26


     input "126"
     az
     abf
     lf
     output :3 total ways


     126 -> length of string 1 to input.length()
     str += input.substring[i,i+length] if i+length < input.length else w/e is left     => (1,2,6), (12,6), (1,26), (126-not valid)




     Map to store numbers to chars

     soFar, rest
     "",    126
     /                  \
     (1),26               (12),6
     /       \              \
     (1,2),6   (1,26),""     (12,6),""
     /
     (1,2,6),""
     */

    private Set<String> set = new HashSet<>();

    private void permute(String str) {
        set.clear();
        permute("", str);
        System.out.println(str + " --->" + set);
    }

    private void permute(String soFar, String rest) {
        System.out.println("soFar=" + soFar + "  rest=" + rest);
        if (rest.isEmpty()) {
            System.out.println(soFar);
            validateAndPrint(soFar);
            return;
        }

        soFar += ",";   // add separator for each new computation to separate the subresults

        for (int i = 0; i < rest.length(); i++) {
            soFar += rest.charAt(i);                // keep adding char i.e. 126 -> 1(i=0), 12(i=1), 126(i=2)
            permute(soFar, rest.substring(i+1));    // recurse on leftover
        }
    }

    private void validateAndPrint(String soFar) {
        String str = "";
        String[] sArr = soFar.split(",");
        for (String s : sArr) {
            if (s == null || s.isEmpty()) continue;
            int digit = Integer.parseInt(s) + 64;
            if (digit < 65 || digit > 92) continue;
            str += (char) digit;
        }
        set.add(str);
    }

    public static void main(String[] args) {
        PermuteStr p = new PermuteStr();
        p.permute("1234");
        //p.permute("011");
        //p.permute("246013");
    }

}
