package com.raj.adhoc;

import java.util.HashMap;
import java.util.Map;

public class Integer2Roman {
    /**
     * 3649 => MMMDCXLIX
     * Map:
     * 1    -> I
     * 5    -> V
     * 10   -> X
     * 50   -> L
     * 100  -> C
     * 500  -> D
     * 1000 -> M
     * 5000 -> G
     *
     * # iter from left to right, maintain the digitPlace
     *
     * # 3649  G,M? 3000,1000 = 3000/1000 becomes MMM
     * #  649  D,C?  600,100  = 600/500 + (100/100) => DC
     * #   49  L,X?   40,10   = 40/10 = XXXX => XL  ... replace XXX by L
     * #    9  V,I?    9,1    = 9/5 = V + 4/1 = VIIII = IX (special case)
     */
    static String convertToRoman(int n) {
        String res = "";
        Map<Integer,String> map = populateMap();
        String s = n + "";
        int len = s.length();

        int digitPlace = (int) Math.pow(10,len-1);  //1000
        while (n>0) {

            // special case as 9 isn't VIV by normal logic but IX
            if (n == 9) {
                res += "IX";
                n -= 9;
                continue;
            }

            // try 5x
            String x5 = map.get(digitPlace*5); // 5x = 5000
            if (n/(digitPlace*5) > 0) {
                res += x5;
                n -= digitPlace*5;
            }

            // try 1x
            String x1 = map.get(digitPlace);   // 1x = 1000
            String tmp = "";
            while (n/digitPlace > 0) {
                tmp += x1;
                n -= digitPlace;  // 3000-1000
            }

            // readjust
            if (tmp.length() == 4) res += x1 + "" + x5;  // XXXX => XL
            else res += tmp;

            digitPlace /= 10;  // 1000 -> 100
        }
        return res;
    }

    static Map<Integer,String> populateMap() {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"I");
        map.put(5,"V");
        map.put(10,"X");
        map.put(50,"L");
        map.put(100,"C");
        map.put(500,"D");
        map.put(1000,"M");
        map.put(5000,"G");
        return map;
    }

    /**
     * MMMDCXLIX => 3649
     * # start from right to left
     * # X = 10
     * # if next is lesser, then subtract it ie. IX = 10-1 = 9
     * # if next if greater or equal, then add it ie. 9+50 = 59
     *   ... C => greater +100, D => +500, M => +1000, M => +1000
     */
    static int convertToInteger(String roman) {
        Map<String,Integer> map = new HashMap<>();
        populateMap().forEach((k,v) -> map.put(v,k));
        int n = 0;
        int prev = 0;
        for (int i=roman.length()-1; i>=0; i--) {
            int curVal = map.get(roman.charAt(i) + "");
            if (curVal >= prev) n += curVal;
            else n -= curVal;
            prev = curVal;
        }
        return n;
    }

    public static void main(String[] args) {
        System.out.println(convertToRoman(3649) + " = " + convertToInteger("MMMDCXLIX"));
        System.out.println(convertToRoman(78) + " = " + convertToInteger("LXXVIII"));
    }
}
