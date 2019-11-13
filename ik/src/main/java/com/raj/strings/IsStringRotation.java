package com.raj.strings;

public class IsStringRotation {

    /**
     * Check if a string is a rotation of another str:
     * abcde , deabc  => yes, rotated 2 times to right
     */
    public static void main(String[] args) {
        System.out.println(isRotation("abcde", "deabc"));
    }

    static boolean isRotation(String s1,String s2) {
        return (s1.length() == s2.length()) && ((s1+s1).indexOf(s2) != -1);
    }
}
