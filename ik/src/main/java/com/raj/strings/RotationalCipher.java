package com.raj.strings;

public class RotationalCipher {
    /**
     * One simple way to encrypt a string is to "rotate" every alphanumeric character by a certain amount.
     * Rotating a character means replacing it with another character that is a certain number of steps away in normal
     * alphabetic or numerical order.
     * For example, if the string "Zebra-493?" is rotated 3 places, the resulting string is "Cheud-726?".
     * Every alphabetic character is replaced with the character 3 letters higher (wrapping around from Z to A),
     * and every numeric character replaced with the character 3 digits higher (wrapping around from 9 to 0).
     * Note that the non-alphanumeric characters remain unchanged.
     */
    public static void main(String[] args) {
        System.out.println(rotationalCipher("Zebra-493?", 3));
    }

    static String rotationalCipher(String input, int rotationFactor) {
        // Write your code here
        String res = "";
        for (char c : input.toCharArray()) {
            boolean isUpper = Character.isUpperCase(c);
            char ch = Character.toUpperCase(c);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) {
                // rotate rotationFactor number of times taking care of edges
                int i = 0;
                while (i++ < rotationFactor) {
                    if (ch == 'Z') ch = 'A';
                    else if (ch == '9') ch = '0';
                    else ch += 1;
                }
                res += isUpper ? ch : Character.toLowerCase(ch);
            } else {
                res += c;
            }
        }
        return res;
    }

}
