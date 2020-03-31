package com.raj.adhoc;

public class VersionCompare {
    /**
     * Compare version numbers
     * eg.
     * 1.0 < 1.1
     * 1.0.1 < 1.1
     * 1.9 < 1.10
     * 1.1.2 < 8.0
     * 12BD23 < 12BF45
     * 09 < 10
     * 000 < 1
     * 00 == 000
     */
    public static void main(String[] args) {
        // equal
        System.out.println(compare("1", "1"));
        System.out.println(compare("1.0.2", "1.0.2"));
        System.out.println(compare("00", "000"));

        // lesser
        System.out.println(compare("0.0.0", "0.0.0.0"));
        System.out.println(compare("0001", "00002"));
        System.out.println(compare("11", "12"));
        System.out.println("1A2".compareTo("1B0"));
        System.out.println(compare("1A2", "1B0"));
        System.out.println(compare("1AB3", "1BAC24"));
        System.out.println(compare("1.0.1", "1.0.2"));
        System.out.println(compare("1.0.0", "1.0.0.1"));
        System.out.println(compare("1.1.2", "8.0"));
    }

    /**
     * # create arrays, split by "."
     * # iter for min length of either arr
     *   - for each sub-part a,b => remove leading zeros
     *   - if length greater, return answer
     *   - if val greater, return answer
     *   - otherwise continue
     * # if arrays length different, return answer
     * # return equal
     */
    static int compare(String s1, String s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null || s2 == null) return s1 != null ? 1 : -1;

        String[] A = s1.contains(".") ? s1.split("\\.") : new String[]{s1};
        String[] B = s2.contains(".") ? s2.split("\\.") : new String[]{s2};

        // pick min length for iter to avoid arr index out of bounds
        int len = A.length < B.length ? A.length : B.length;

        for (int i = 0; i < len; i++) {
            String a = A[i];
            String b = B[i];

            // remove leading zeros  001 -> 1
            a = removeLeadingZeros(a);
            b = removeLeadingZeros(b);

            // length differs 99 < 123 - we have an answer
            if (a.length() != b.length()) return a.length() - b.length();

            // length is same, but value differs - we have an answer
            if (a.compareTo(b) != 0) return a.compareTo(b);
        }
        if (A.length != B.length) return A.length - B.length;
        return 0;
    }

    // return a string with left padded zeros removed
    static String removeLeadingZeros(String s) {
        int i = 0;
        for (; i < s.length(); i++) {
            if (s.charAt(i) != '0') break;
        }
        return s.substring(i);
    }

}
