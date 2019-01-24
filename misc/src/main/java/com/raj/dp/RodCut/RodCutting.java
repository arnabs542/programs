package com.raj.dp.RodCut;

/**
 * @author rshekh1
 */
public class RodCutting {

    private int[][] profit;
    private int[] cutPrices;
    private int rodLength;

    RodCutting(int rodLength, int[] cutPrices) {
        this.cutPrices = cutPrices;
        this.rodLength = rodLength;
        this.profit = new int[cutPrices.length][rodLength+1];
    }

    public void showResults() {
        System.out.println("Profit DP Table: Rod Length(Left to right), Cut Length(Top to Down), Matrix value=$ profit");
        for (int i = 0; i < profit.length; i++) {
            for (int j = 0; j < profit[0].length; j++) {
                System.out.print(profit[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\nMax Profit = $" + profit[cutPrices.length - 1][rodLength]);

        int cutLen = cutPrices.length - 1;
        while (rodLength > 0) {     // until we exhaust length of the rod
            if (profit[cutLen][rodLength] != 0 && profit[cutLen][rodLength] != profit[cutLen-1][rodLength]) {
                System.out.println("Cut Length# " + cutLen + "m for $" + cutPrices[cutLen]);
                rodLength -= cutLen;        // profit was a combination of this cut length's price plus saved (rodLen-cutLength)'s dp value
            } else cutLen --;
        }
    }

    public void solve() {

        for (int cutLen = 1; cutLen < cutPrices.length; cutLen++) {
            for (int rodLen = 1; rodLen <= rodLength; rodLen++) {
                if (cutLen <= rodLen) {
                    profit[cutLen][rodLen] =
                            Math.max(profit[cutLen-1][rodLen], cutPrices[cutLen] + profit[cutLen][rodLen-cutLen]);
                } else {
                    profit[cutLen][rodLen] = profit[cutLen-1][rodLen];
                }
            }
        }
    }

    public static void main(String[] args) {
        RodCutting rc = new RodCutting(5, new int[]{0,2,5,7,3});
        rc.solve();
        rc.showResults();
    }

}
