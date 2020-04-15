package com.raj.strings;

public class PrintSinusoidal {

    /**
     * Also called "SnakeString". For example, the phrase "Google Worked" should be printed as follows
     * (where ~ is the word separator):
     *     o     ~      k
     *   o  g  e  W   r   e
     * G     l      o       d
     */
    public static void main(String[] args) {
        String s = "GOOGLE WORKED!!";
        int len = s.length();
        for (int i=0;i<len;i++) {
            if ((i+2)%4 == 0) print(s.charAt(i));
            else System.out.print(" ");
        }
        System.out.println();
        for (int i=0;i<len;i++) {
            if ((i+1)%2 == 0) print(s.charAt(i));
            else System.out.print(" ");
        }
        System.out.println();
        for (int i=0;i<len;i++) {
            if ((i)%4 == 0) print(s.charAt(i));
            else System.out.print(" ");
        }
    }

    static void print(char c) {
        if (c == ' ') System.out.print("~");
        else System.out.print(c);
    }


}
