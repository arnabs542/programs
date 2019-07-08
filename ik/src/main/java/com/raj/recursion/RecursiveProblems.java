package com.raj.recursion;

/**
 * @author <a href="mailto:rshekhar@walmartlabs.com">Shekhar Raj</a>
 */
public class RecursiveProblems {

    /**
     * changePi("xpix") → "x3.14x"	"x3.14x"	OK
     * changePi("pipi") → "3.143.14"	"3.143.14"	OK
     * changePi("pip") → "3.14p"	"3.14p"	OK
     * changePi("pi") → "3.14"	"3.14"	OK
     * changePi("hip") → "hip"	"hip"	OK
     * changePi("p") → "p"	"p"	OK
     * changePi("x") → "x"	"x"	OK
     * changePi("") → ""	""	OK
     * changePi("pixx") → "3.14xx"	"3.14xx"	OK
     * changePi("xyzzy") → "xyzzy"	"xyzzy"	OK
    */

    public static void main(String[] args) {
        System.out.println(changePi(""));// → ""
        System.out.println(changePi("xpix"));// → "x3.14x"
        System.out.println(changePi("pipi"));// → "3.143.14"
        System.out.println(changePi("pip"));// → "3.14p"
        System.out.println(changePi("xyzzy"));// → "xyzzy"

        System.out.println(strCopies("catcowcat", "cat", 2));// → "xyzzy"
        System.out.println(strCopies("catcowcat", "cow", 1));

        System.out.println(groupSum(0, new int[] {2, 4, 8}, 10)); // true
        System.out.println(groupSum(0, new int[] {2, 4, 8}, 14)); // true
        System.out.println(groupSum(0, new int[] {2, 4, 8}, 9)); // false

        System.out.println(groupSum6(0, new int[] {5, 2, 4, 6}, 9));  // false
        System.out.println(groupSum6(0, new int[] {5, 6, 2}, 8));  // true

        System.out.println(groupNoAdj(0, new int[] {2, 5, 10, 4}, 12));  // true

        System.out.println(groupSum5(0, new int[] {2, 5, 10, 4}, 19));  // true
        System.out.println(groupSum5(0, new int[] {2, 5, 4, 10}, 12));  // true
    }

    public static String changePi(String str) {
        if (str.length() <= 1) return str;
        if (str.substring(0,2).equals("pi")) return "3.14" + changePi(str.substring(2));
        else return str.charAt(0) + changePi(str.substring(1));
    }

    /**
     * strCopies("catcowcat", "cat", 2) → true
     * strCopies("catcowcat", "cow", 2) → false
     * strCopies("catcowcat", "cow", 1) → true
     * strCopies("catcowcat", "cat", 2) → true	true	OK
     * strCopies("catcowcat", "cow", 2) → false	false	OK
     * strCopies("catcowcat", "cow", 1) → true	true	OK
     * strCopies("dogcatdogcat", "dog", 2) → true	true	OK
     */
    public static boolean strCopies(String str, String sub, int n) {
        if (str.length() == 0) return false;  // reached end
        if (n == 0) return true;
        if (str.length() == sub.length()) return n == 1 && str.equals(sub);
        System.out.println(str);
        return str.substring(0,sub.length()).equals(sub) ?
                strCopies(str.substring(sub.length()), sub, n-1) :
                strCopies(str.substring(1), sub, n);
    }

    /**
     groupSum(0, [2, 4, 8], 10) → true
     groupSum(0, [2, 4, 8], 14) → true
     groupSum(0, [2, 4, 8], 9) → false
     */
    public static boolean groupSum(int start, int[] nums, int target) {
        if (target == 0) return true;   // reached target, we are good
        if (start >= nums.length) return false;     // reached dead end
        return groupSum(start+1, nums, target-nums[start]) // include elem
                || groupSum(start+1, nums, target);   // don't include elem
    }

    /**
     Given an array of ints, is it possible to choose a group of some of the ints,
     beginning at the start index, such that the group sums to the given target?
     However, with the additional constraint that all 6's must be chosen. (No loops needed.)
     groupSum6(0, [5, 6, 2], 8) → true
     groupSum6(0, [5, 6, 2], 9) → false
     groupSum6(0, [5, 6, 2], 7) → false
     */
    public static boolean groupSum6(int start, int[] nums, int target) {
        //if (target == 0) return true;  --> commented as we want 6 to be included and we can't know until we process all elems
        if (start >= nums.length) return target == 0;
        if (nums[start] == 6) return groupSum6(start+1, nums, target-6);
        return groupSum6(start+1, nums, target-nums[start]) ||
                groupSum6(start+1, nums, target);
    }

    /**
     * Given an array of ints, is it possible to choose a group of some of the ints,
     * such that the group sums to the given target with this additional constraint:
     * If a value in the array is chosen to be in the group, the value immediately following
     * it in the array must not be chosen. (No loops needed.)
     *
     * groupNoAdj(0, [2, 5, 10, 4], 12) → true
     * groupNoAdj(0, [2, 5, 10, 4], 14) → false
     * groupNoAdj(0, [2, 5, 10, 4], 7) → false
     */
    public static boolean groupNoAdj(int start, int[] nums, int target) {
        if (start >= nums.length) return target == 0;
        return groupNoAdj(start+2, nums, target-nums[start]) ||
                groupNoAdj(start+1, nums, target);
    }

    /**
     * Given an array of ints, is it possible to choose a group of some of the ints,
     * such that the group sums to the given target with these additional constraints:
     * all multiples of 5 in the array must be included in the group.
     * If the value immediately following a multiple of 5 is 1, it must not be chosen. (No loops needed.)
     *
     * groupSum5(0, [2, 5, 10, 4], 19) → true
     * groupSum5(0, [2, 5, 10, 4], 17) → true
     * groupSum5(0, [2, 5, 10, 4], 12) → false
     */
    public static boolean groupSum5(int start, int[] nums, int target) {
        if (start >= nums.length) return target == 0;
        if (nums[start] % 5 == 0) {
            if (start+1 < nums.length && nums[start+1] == 1) return groupSum5(start+2, nums, target-nums[start]);
            else return groupSum5(start+1, nums, target-nums[start]);
        }
        return groupSum5(start+1, nums, target-nums[start]) ||
                groupSum5(start+1, nums, target);
    }

}
