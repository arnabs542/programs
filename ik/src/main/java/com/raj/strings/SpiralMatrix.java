package com.raj.strings;

public class SpiralMatrix {

    public static void main(String[] args) {
        System.out.println(printSpirally(new char[][]{
                {'A','B','C'},
                {'D','E','F'},
                {'G','H','I'},
                {'J','K','L'}
        }));
    }

    /**
     * Set preference as ->, DN, <-, UP
     * Mark visited
     * Change dir once exhausted
     */
    static String printSpirally(char[][] g) {

        if (g == null || g.length == 0) return "";

        int i=0, r=0, c=0;
        int ROW = g.length, COL = g[0].length;
        int size = ROW * COL;

        // init state
        String res = g[0][0] + ""; g[0][0] = '$'; char dir = 'R'; i++;
        while (i < size) {
            switch(dir) {
                // -->
                case 'R':
                    if (c+1<COL && g[r][c+1] != '$') { res+=g[r][++c]; g[r][c]='$'; i++; }
                    else dir = 'D';
                    break;

                // DN
                case 'D':
                    if (r+1<ROW && g[r+1][c] != '$') { res+=g[++r][c]; g[r][c]='$'; i++; }
                    else dir = 'L';
                    break;

                // <--
                case 'L':
                    if (c-1>=0 && g[r][c-1] != '$') { res+=g[r][--c]; g[r][c]='$'; i++; }
                    else dir = 'U';
                    break;

                // UP
                case 'U':
                    if (r-1>=0 && g[r-1][c] != '$') { res+=g[--r][c]; g[r][c]='$'; i++; }
                    else dir = 'R';
                    break;
            }
            System.out.println(i + " :" + r + "," + c + "-> " + res);
            g[r][c] = '$'; // visited
        }
        return res;
    }

}
