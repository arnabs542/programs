package com.raj.adhoc;

/**
 * @author rshekh1
 */
public class RunLengthEncode {

    /**
     * ABaaaBCC => AB3aB2C
     *
     * Another variation of the problem called "Look & Say Sequence". Print nth sequence
     * 1,11,21,1211,111221,312211
     *                     3 ones 2 twos 1 ones
     */
    public static void main(String[] args) {
        System.out.println(RLE("AAAAA"));
        System.out.println(RLE("ABCD"));
        System.out.println(RLE("ABaaaBCC"));

        System.out.println(lookAndSaySeq("1",6));  //312211
    }

    public static String RLE(String s) {
        if (s == null || s.length() == 1) return s;
        int cnt = 1;
        int i;
        String res = "";
        for (i=1; i<s.length(); i++) {
            if (s.charAt(i) == s.charAt(i-1)) cnt++;
            else {
                if (cnt > 1) res += cnt;  // we don't print A1
                res += s.charAt(i-1);
                cnt = 1;
            }
        }
        // add leftover
        if (cnt > 1) res += cnt;
        res += s.charAt(i-1);
        return res;
    }

    /**
     * Generate nth Look & say sequence
     * n  i  cnt  cur  res   s
     * 2     1    1          1
     *    1            11    11
     *
     * 1     1    1
     *    1  2    1
     *    2            21    21
     *
     * 0     1    2          21
     *    1  1    1    12
     *    2            1211  1211
     *
     */
    static String lookAndSaySeq(String s, int n) {
        if (n == 1) return s;
        String res = "";
        while (--n > 0) {   // the term counter
            char cur = s.charAt(0);   // init dig & count
            int cnt = 1;
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) == cur) cnt++;
                else {
                    res += cnt + "" + cur;
                    cur = s.charAt(i);
                    cnt = 1;
                }
            }
            // add leftover
            res += cnt + "" + cur;
            s = res;
            res = "";
        }
        return s;
    }

}
