package com.raj.dp;

/**
 * @author rshekh1
 */
public class LevenshteinDistance {

    /**
     * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2.
     * (each operation is counted as 1 step.)
     * You have the following 3 operations permitted on a word:
     * a) Insert a character
     * b) Delete a character
     * c) Replace a character
     *
     * The minimum no of steps required to convert word1 to word2 with the given set of allowed operations
     * is called edit distance.
     *
     * e.g. Minimum edit distance between the words 'kitten' and 'sitting', is 3.
     * kitten → sitten (substitution of "s" for "k")
     * sitten → sittin (substitution of "i" for "e")
     * sittin → sitting (insertion of "g" at the end)
     */
    public static void main(String[] args) {
        // Recursive
        System.out.println(levenshteinDistance_recur("abc".toCharArray(), 0, "abc".toCharArray(), 0));  // 0
        System.out.println(levenshteinDistance_recur("a".toCharArray(), 0, "abc".toCharArray(), 0));  // 2
        System.out.println(levenshteinDistance_recur("kitten".toCharArray(), 0, "sitting".toCharArray(), 0));  // 3

        // iterating reverse eases base cases
        System.out.println(editDist("kitten", "sitting", "kitten".length(), "sitting".length()));    // 3

        // DP
        System.out.println(levenshteinDistance_dp("ab".toCharArray(), "abc".toCharArray()));  // 1
        System.out.println(levenshteinDistance_dp("kitten".toCharArray(), "sitting".toCharArray()));  // 3
    }

    /**
     * Note - we can't just find diff chars & length dist for answer as this will fail:
     * aaaa
     * bbbaaaa
     * Answer = 3, but by earlier logic it will add up 3 for diff chars + 3 extra chars = 6
     *
     * Rec tree:
     *           s1 = a
     *         +/ -|  \replace  ... bf = 3
     *        aa   .   b
     * Runtime => if depth of rec = n which is length of string, then its O(3^n)
     */
    static int levenshteinDistance_recur(char[] s1, int i, char[] s2, int j) {
        // reached end of comparison
        if (i == s1.length && j == s2.length) return 0;

        // If first string is empty, the only option is to, insert all characters of second string into first
        if (i == s1.length) return s2.length-i;

        // If second string is empty, the only option is to, remove all characters of first string
        if (j == s2.length) return s1.length-j;

        // first char matches, recur on remaining
        if (s1[i] == s2[j]) return levenshteinDistance_recur(s1, i+1, s2, j+1);

        // first char differs, then try all options for corrections i.e. add, delete, replace
        int delete = levenshteinDistance_recur(s1, i+1, s2, j); // just shift s1's ptr
        int add = levenshteinDistance_recur(s1, i, s2, j+1);    // n, wn => (w)n, wn => we just move j's ptr by 1 as we assume after adding char, it matches
        int replace = levenshteinDistance_recur(s1, i+1, s2, j+1);  // assume they are equal, move on
        return 1 + min(add, delete, replace);
    }

    static int min(int a, int b, int c) {
        int m1 = Math.min(a,b);
        return Math.min(m1,c);
    }

    /**
     * dp("ab", "abc") table:
     *
     *               (a) (ab) (abc)   <--- B (end state)
     * A|        ""   a   b   c
     *  v   ""   0    1   2   3  --> going right is + as "" -> a = 1, similarly going down is - as a -> "" = 1
     *  (a) a    1    0   1   2
     * (ab) b    2    1   0   1
     *                    |
     *                    v  A=ab, B=ab
     *                  b==b, we used diagonal 0 which is char equal case, plus it is also the case for replace (and u add 1)
     */
    static int levenshteinDistance_dp(char[] A, char[] B) {
        int[][] dp = new int[A.length+1][B.length+1];

        for (int i = 0; i <= A.length; i++) {
            for (int j = 0; j <= B.length; j++) {
                // base cases
                if (i == 0) dp[i][j] = j; // If 1st string is empty, the only option is to, insert all characters of 2nd string into 1st
                else if (j == 0) dp[i][j] = i; // If 2nd string is empty, the only option is to, insert all characters of 1st string into 2nd

                // char equal (NOTE - it's i-1, j-1 indices from char array)
                else if (A[i-1] == B[j-1]) dp[i][j] = dp[i-1][j-1];

                // If the last character is different, consider all possibilities and find the minimum
                else {
                    dp[i][j] = 1 + min(
                            dp[i][j-1],  // add
                            dp[i-1][j],  // delete
                            dp[i-1][j-1] // replace
                    );
                }
            }
        }
        return dp[A.length][B.length];
    }

    static int editDist(String str1, String str2, int m, int n)
    {
        // If first string is empty, the only option is to
        // insert all characters of second string into first
        if (m == 0) return n;

        // If second string is empty, the only option is to
        // remove all characters of first string
        if (n == 0) return m;

        // If last characters of two strings are same, nothing
        // much to do. Ignore last characters and get count for
        // remaining strings.
        if (str1.charAt(m-1) == str2.charAt(n-1))
            return editDist(str1, str2, m-1, n-1);

        // If last characters are not same, consider all three
        // operations on last character of first string, recursively
        // compute minimum cost for all three operations and take
        // minimum of three values.
        return 1 + min ( editDist(str1,  str2, m, n-1), // Insert
                editDist(str1,  str2, m-1, n),         // Remove
                editDist(str1,  str2, m-1, n-1)    // Replace
        );
    }

}
