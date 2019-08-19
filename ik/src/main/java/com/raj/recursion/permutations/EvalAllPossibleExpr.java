package com.raj.recursion.permutations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rshekh1
 */
public class EvalAllPossibleExpr {

    public static void main(String[] args) {
        //generate_all_expressions("1234", 24);
        _permute("123", "", 0, 6, 0, 0);
        System.out.println(res);
        res.clear();

        //_permute("1234", "", 0, 24, 0);
        //System.out.println(res);

        //genExpr("123", 0, "", 0L, 6L, 0);
    }

    /*
     * get all exprs
     * 1234, 24 => [1*2*3*4, 12+3*4]
     *
     *                      123
     *          /1dig       |2dig    \3dig
     *       1,23           12,3    123,""
     *     /1dig  |2dig     |       |
     *    1+2,3  1+23       12+3    123
     *    |
     *    1+2+3
     *
     */
    static String[] generate_all_expressions(String s, long target) {
        permute(s, 0, "", 0, target, 0);
        String[] resArr = new String[res.size()]; int k = 0;
        for (String r : res) resArr[k++] = r;
        System.out.println(res);
        return resArr;
    }

    static List<String> res = new ArrayList<>();

    // dfs - fix bug
    static void permute(String s, int i, String soFar, long valueSoFar, long target, long prev) {
        if (i == s.length() && valueSoFar == target) {
            res.add(soFar);
            return;
        }

        for (int j = i; j < s.length(); j++) {
            // Get the string s[pos, i] (both inclusive).
            String numStr = s.substring(i, j+1);
            if (numStr.isEmpty()) continue;
            // Convert it to number.
            long num = Long.parseLong(numStr);
            System.out.println(num + " -> " + soFar);
            // If we have just started then first we will add number without operator.
            if (i == 0) {
                permute(s, i + 1, soFar + numStr, num, target, num);
            } else {
                permute(s, i + 1, soFar + "+" + numStr, valueSoFar + num, target, num);
                /**
                 * Give precedence to multiplication - eg if we have a + b * c, we really want
                 * (a+b) * c has to be a + (b*c)
                 * => ((a+b) - b) + b*c
                 * => (valueSoFar - prev) + prev*num
                 **/
                permute(s, i + 1, soFar + "*" + numStr, (valueSoFar - prev) + (prev * num), target, (prev * num));
            }
        }
    }

    /**
     *                         rest, (soFar)
     *                          123 ('')
     *          1(23)               12(3)           123()
     *    1,2(3)    1,23()          12,3()           123()
     *   1,2,3()
     */
    // dfs - working
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
