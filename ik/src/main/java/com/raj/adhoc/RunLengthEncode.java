package com.raj.adhoc;

/**
 * @author rshekh1
 */
public class RunLengthEncode {

    /**
     * ABaaaBCC => AB3aB2C
     */
    public static void main(String[] args) {
        System.out.println(RLE("AAAAA"));
        System.out.println(RLE("ABCD"));
        System.out.println(RLE("ABaaaBCC"));
    }

    public static String RLE(String s) {
        if (s == null || s.length() == 1) return s;
        int cnt = 1;
        int i = 1;
        String res = "";
        for (i=1; i<s.length(); i++) {
            if (s.charAt(i) == s.charAt(i-1)) cnt++;
            else {
                if (cnt > 1) res += cnt;
                res += s.charAt(i-1);
                cnt = 1;
            }
        }
        if (cnt > 1) res += cnt;
        res += s.charAt(i-1);
        return res;
    }

}
