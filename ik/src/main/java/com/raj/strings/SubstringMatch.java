package com.raj.strings;

public class SubstringMatch {

    /**
     * Find if a given substring is present in text
     * abcdbcam, bca => 4
     */
    public static void main(String[] args) {
        System.out.println(bruteForce("abcdbcam", "bca"));
        System.out.println(rabin_karp("abcdbcam", "bca"));
    }

    /**
     * O(nm), where n is the length of text & m is the len of substr
     */
    static int bruteForce(String text, String substr) {
        for (int i = 0; i < text.length(); i++) {
            int j = 0;
            while (j < substr.length() && text.charAt(i+j) == substr.charAt(j)) {
                j++;
            }
            if (j == substr.length()) return i;
        }
        return -1;
    }

    /**
     * Rolling Hash Sliding Window - Rabin Karp Algo
     * # Compute Hash for substr using prime num & digits place significance (i* prime^0 + i* prime^1 + i* prime^2 ...)
     * # Iterate & keep calculating hash for window of size substr
     * # Readjust hash as we go from left to right in text as follows -
     *   - deduct leading char from text
     *   - divide hash / prime
     *   - add the new char ascii num w/ prime^substr.length-1
     *   - why ? eg. say prime=3 & ascii char as (text:2345, substr:345)
     *          => substr = (3*3^0)+(4*3^1)+(5*3^2) = 60
     *          => text(0..2) = 2+3*3+4*9 = 47, text(1..3) = (47-2)/3 + 5*9 = 60 (which is a match, we double confirm by iterating substr w/ matched text portion)
     * Runtime = hash is O(1), avg case = O(n) + O(m) = O(n)
     *                         worst case = O(nm), if hash is poor & collisions cause each text's substr to match given substr
     */
    static int rabin_karp(String text, String substr) {
        int prime = 101;

        // compute substr hash
        int hashSubstr = 0, hashText = 0;
        for (int i = 0; i < substr.length(); i++) {
            hashSubstr += ((int)substr.charAt(i)) * Math.pow(prime,i);
            hashText += ((int)text.charAt(i)) * Math.pow(prime,i);
        }
        if (hashSubstr == hashText && text.substring(0, substr.length()).equals(substr)) return 0;

        // compute sliding window hash and check if it matches
        for (int i = substr.length(); i < text.length()-substr.length()+1; i++) {
            hashText = (int) ((hashText - (int)text.charAt(i-1)) / prime
                    + (int)text.charAt(i+substr.length()-1) * Math.pow(prime,substr.length()-1));
            if (hashSubstr == hashText && text.substring(i, i+substr.length()).equals(substr)) return i;
        }
        return -1;
    }
}
