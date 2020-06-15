package com.raj.adhoc;

public class GuessPassword {
    /**
     * Please use this Google doc during your interview (your interviewer will see what you write here). To free your hands for typing, we recommend using a headset or speakerphone.
     *
     * Q. [Coding] Write a function that computes the number of letters in common between two strings.
     *
     * s1 = xyz
     * s2 = abxvz
     * => 2
     *
     *
     * xxyz, xyz => 3
     *
     * abc, xyz => 0
     * Abc, abc => 3
     *
     * aaabb, bbbaa = 4
     * a=3, b=2, => [3,2,000000]
     * b=3,a=2
     * a 2+2
     *
     * // aaabb, bbbaa
     * s1Cnt = [a=3,b=2]
     * s2Cnt = [a=2,b=3]
     * 2+2 = 4
     *
     * public int findCommonLetters(String s1, String s2) {
     *    int[] s1Count = new int[26];
     *    int[] s2Count = new int[26];
     *
     *    // iter s1
     *    for (char ch : s1.toCharArray()) {
     *      s1Count[(int)ch - ‘a’]++;
     *    }
     *
     *    // iter s2
     *    for (char ch : s2.toCharArray()) {
     *      s2Count[(int)ch - ‘a’]++;
     *    }
     *
     *    // find intersection
     *    int matchCount = 0;
     *    for (int i=0; i<26; i++) {
     *      matchCount += Math.min(s1Count[i], s2Count[i]);
     *    }
     *
     *    return matchCount;
     * }
     *
     * [Variation of https://leetcode.com/problems/guess-the-word/ where it's exact position match]
     * Q. [Coding] In the game "Fallout 3," a hacking mini-game exists where you are given a set of possible passwords.
     * You guess a password, and the system responds with the number of letters matching the actual password irrespective
     * of position. If you select the correct password, the system unlocks.
     * Suppose that you have five possible passwords: robot, tiger, motor, meter, and metro. Let "meter" be the correct
     * password. If you guessed "metro", the computer would respond with the number 4 because the letters 'm', 'e', 't',
     * and 'r' are common between the two passwords.
     *
     * ACTUAL PASSWORD: METER
     *
     * METRO = 4, x   => M=1,E=1,T=1,R=1,O=1
     * ROBOT = 2, x   => common = R=1, O=2, T=1 => {R=1,T=1} {O=2}
     * TIGER = 3, x
     * MOTOR = 3, x
     * METER = 5, c
     *
     * 4 guesses for a 10 words
     *
     * Write a function that accepts the list of possible passwords and returns the correct password. You may assume a
     * Guess(guessed_password) function exists that returns the number of letters matching the correct password and
     * whether the password was correct or not.
     *
     * C++ examples:
     * std::pair<bool, int> Guess(const string& guessed_password);
     * bool Guess(const string& guessed_password, int* in_common);
     * ... or define your own!  :)
     *
     *
     * metro = 4 in common with actual password  m=1,e=1,t=1,r=1,o=1
     *   robot = 3 in common with metro
     *   tiger = 3
     *   motor = 4  m=1,0=2,t=1,r=1
     *   meter = 4  m=1,e=2,t=1,r=1
     *
     * motor = 3 in common with actual password
     *   meter = 3 in common with motor
     *
     *
     * Pair<Boolean,Integer> Guess(String guessed_password){
     *   String pwd = “METER”;
     *  int match = findCommonLetters(pwd, guessed_password) ;
     *   return new Pair(pwd.length == match, match);
     * }
     */
    // O(string_max_len)
    public static int findCommonLetters(String s1, String s2) {
        int[] s1Count = new int[26];
        int[] s2Count = new int[26];
        // iter s1
        for (char ch : s1.toCharArray()) {
            s1Count[(int)Character.toLowerCase(ch) - 'a']++;
        }
        // iter s2
        for (char ch : s2.toCharArray()) {
            s2Count[(int)Character.toLowerCase(ch) - 'a']++;
        }
        // find intersection
        int matchCount = 0;
        for (int i=0; i<26; i++) {
            matchCount += Math.min(s1Count[i], s2Count[i]);
        }
        return matchCount;
    }

    static Pair Guess(String guessed_password){
        String pwd = "METER".toLowerCase();
        int match = findCommonLetters(pwd, guessed_password) ;
        return new Pair(pwd.length() == match, match);
    }

    // O(n_guesses * guess_max_len)
    static boolean crackPassword_brute(String[] guessPwd) {
        for (String p : guessPwd) {
            if (Guess(p).isMatch) return true;
        }
        return false;
    }

    /**
     * 4 guesses for a 10 words
     * Optimize for min number of attempts to unlock
     * metro
     * robot
     * tiger
     * motor
     * ...
     * meter
     *
     * 1. metro tried w/ meter => 4 (reqd 5)
     *    since we were just short by 1 char, we try to find similar char freq words
     *    - metro vs robot => t1,r1,o1 = 3
     *    - metro vs tiger => e1,t1,r1 = 3
     *    - metro vs motor => m1,t1,r1,o1 = 4   ... try this
     *    - metro vs meter => m1,e1,t1,r1 = 4   ... try this
     * 2. motor tried w/ meter => 3
     *    metro,motor = m,t,r,o
     *
     * Start w/ robot:
     * 1. robot tried w/ meter => 2
     *    we are short by quite big margin, 3 chars, so we need to try different charset
     *             similar, not similar
     *    - vs metro => 3,  2
     *    - vs tiger => 2,  3
     *    - vs motor => 4,  1
     *    - vs meter => 2,  3
     *
     * # We can use process of elimination where we try a guess, if char matches > 0, discard all options which aren't at
     * least 1 char match with the guess we just tried.
     * # Can't guarantee exact num tries to crack -
     */
    static boolean crackPassword(String[] guessPwd) {
        for (String p : guessPwd) {
            System.out.println(p + " -> " + Guess(p).matchedChars);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(findCommonLetters("aaabb", "bbbaa"));

        System.out.println(crackPassword_brute(new String[] {
                "metro",
                "robot",
                "tiger",
                "motor",
                "meter"
        }));

        System.out.println(crackPassword(new String[] {
                "metro",
                "robot",
                "tiger",
                "motor",
                "meter"
        }));
    }

    static class Pair {
        boolean isMatch;
        int matchedChars;

        Pair(boolean isMatch, int matchedChars) {
            this.isMatch = isMatch;
            this.matchedChars = matchedChars;
        }
    }
}
