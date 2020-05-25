package com.raj.adhoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class KProjectsMaxProfit {
    /**
     * Given C or Capital reqd for a project & P or Profit made from completing the project,
     * Find max profit if we have K max projects to choose.
     *
     * Input: K=2, W=0, Profits=[1,2,3], Capital=[0,1,1].
     * Output: 4  (0 + 1 + 3 = 4)
     */
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1,2,3}, new int[]{0,1,1}, 0, 2));
    }

    /**
     * Brute:
     * # Until K>0, Choose max profit from projects that are eligible to be picked from ie. C<=W
     * # Add max profit to res
     * O(K.C.P) = O(K.n^2), where n = size of array
     *
     * Using Max Heap:
     * # Sort C so that we could find C<=W faster.
     * # To eliminate picking max profit faster use a maxHeap.
     * O(nlogn + n + K.logn)
     */
    static int maxProfit(int[] P, int[] C, int W, int K) {
        int n = P.length;

        // arrange input
        List<Pair> input = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            input.add(new Pair(P[i],C[i]));
        }

        // sort by asc capital
        Collections.sort(input, (a,b) -> a.capital-b.capital);

        // max pq to get max profit project
        PriorityQueue<Pair> maxPQ = new PriorityQueue<>((a,b) -> b.profit-a.profit);

        // pick k times
        int i = 0; // projects already pushed, we iterate only once
        while (K-- > 0) {
            // push eligible projects onto heap to select later
            for (; i < n && C[i] <= W; i++) {
                maxPQ.add(new Pair(P[i],C[i]));
            }
            System.out.println(maxPQ);
            // pop max profit project fulfilling the eligibility
            Pair pr = maxPQ.remove();
            W += pr.profit;
        }
        return W;
    }

    static class Pair {
        int profit;
        int capital;
        Pair(int p, int c) {
            profit = p;
            capital = c;
        }
        public String toString() {
            return profit + ":" + capital;
        }
    }
}
