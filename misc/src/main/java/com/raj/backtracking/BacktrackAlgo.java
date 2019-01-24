package com.raj.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rshekh1
 */
public class BacktrackAlgo {

    /**
     * Backtracking ALGO:
     * DFS(state)
     *  did we reach goal already? return true
     *  try all combinations possible from here
     *      if isFeasible(newStateCombination) && DFS(newStateCombination + 1) return true
     *      else backtrack and revert choice made
     *  return false
     */
    static  int states = 5;
    static List<String> solution = new ArrayList<>();

    public static void main(String[] args) {
        // Define initial board, maze etc
        solve("Start state");
        System.out.println(solution);
    }

    static boolean solve(String state) {
        if (state.equals("Finished")) return true;  // reached final desired state

        for (int i = 0; i < states; i++) {      // try all state changes from here
            String aNewState = state + i;
            if (isFeasible(aNewState)) {    // before trying out, verify if it's possible?
                solution.add(aNewState);    // add it to solution
                if (solve(aNewState + 1)) return true;  // if we solve maze one step ahead from here, we are done
                else solution.remove(aNewState);    // otherwise backtrack
            }
        }
        return false;   // no solution
    }

    static boolean isFeasible(String state) {
        if (state.equals("Possible")) return true;
        else return false;
    }

}
