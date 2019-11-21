package com.raj.strings;

public class ReverseWordStr {

    public static void main(String[] args) {
        System.out.println(reverse_ordering_of_words(" I WILL DO IT.   "));
    }

    /**
     * Algo:
     * Reverse whole text
     * Find a word (i...j) by skipping spaces
     * Reverse each found Word
     * Runtime = O(n), Aux Space = O(1)
     */
    static String reverse_ordering_of_words(String s) {
        if (s == null || s.length() < 2) return s;
        char[] A = s.toCharArray();
        revStr(A);
        int i=0, j, len=A.length;
        System.out.println(len);

        while(i<len) {

            // skip spaces
            i = skipSpace(A, i);

            // find word i...j
            j = i;
            while(j+1<len && A[j+1] != ' ') j++;

            // reverse word
            i = revWord(A, i, j);
        }
        return new String(A);
    }

    static int skipSpace(char[] A, int i) {
        while(i<A.length && A[i]==' ') i++;
        return i;
    }

    static void revStr(char[] A) {
        int i = -1, j = A.length;
        while(++i < --j) {
            char t = A[i];
            A[i] = A[j];
            A[j] = t;
        }
    }

    static int revWord(char[] A, int i, int j) {
        int _j = j;
        i--;j++;
        while(++i < --j) {
            char t = A[i];
            A[i] = A[j];
            A[j] = t;
        }
        return _j+1;
    }

}
