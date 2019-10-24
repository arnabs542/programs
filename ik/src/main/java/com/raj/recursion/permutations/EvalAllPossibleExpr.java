package com.raj.recursion.permutations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rshekh1
 */
public class EvalAllPossibleExpr {

    /**
     * Generate All Possible Expressions That Evaluate To The Given Target Value
     * using input array elements & + , * only
     *
     * Input1:
     * s = "222", target = 24
     * ["22+2", "2+22"]
     *
     * Input2:
     * s = "123", target = 6
     * [1+2+3, 1*2*3]
     */
    public static void main(String[] args) {
        _permute("123", "", 0, 6, 0, 0);
        System.out.println(res + "\n\n");
        res.clear();

        //_permute("1234", "", 0, 24, 0, 0);
        System.out.println(res);

    }

    static List<String> res = new ArrayList<>();

    /**
     *                         rest, (soFar)
     *                          123 ('')
     *          1(23)               12(3)           123()
     *    1,2(3)    1,23()          12,3()           123()
     *   1,2,3()
     */
    // dfs
    static void _permute(String rest, String soFar, long valueSoFar, long target, long prev, int level) { // level is for debug print
        System.out.println(level + " -> " + soFar + " = " + valueSoFar);

        if (rest.isEmpty() && valueSoFar == target) {
            res.add(soFar);
            return;
        }

        String numStr = "";
        for (int i = 0; i < rest.length(); i++) {  // append ith char to form 1, 12, 123 number ....
            numStr += rest.charAt(i);
            long num = Long.parseLong(numStr);  // parse as num for maths operations later

            if (soFar.isEmpty()) {
                // no need to do '+' or '*' if soFar (which is left operand) is empty
                _permute(rest.substring(i+1), soFar + numStr, num, target, num, level+1);
            } else {
                // do maths operations if soFar is minimally formed
                _permute(rest.substring(i+1), soFar + "+" + numStr, valueSoFar + num, target, num, level+1);
                /**
                 * Give precedence to multiplication - eg if we have a + b * c, we really want
                 * (a+b) * c has to be a + (b*c)
                 * => ((a+b) - b) + b*c
                 * => (valueSoFar - prev) + prev*num  (basically we undo last math operation using prev)
                 **/
                _permute(rest.substring(i+1), soFar + "*" + numStr, (valueSoFar - prev) + (prev * num), target, (prev * num), level+1);
            }
        }
    }

}
