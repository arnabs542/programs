package com.raj.graphs;

import java.util.Arrays;

public class MSPaintFloodFill {
    /**
     * Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill, and a pixel value
     * newColor, "flood fill" the image.
     *
     * To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to the starting
     * pixel of the same color as the starting pixel. Replace the color of all of the aforementioned pixels with the newColor.
     *
     * Input:
     * image = [[1,1,1],[1,1,0],[1,0,1]]
     * sr = 1, sc = 1, newColor = 2
     * Output: [[2,2,2],[2,2,0],[2,0,1]]
     */
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(
                floodFill(new int[][] {{1,1,1},{1,1,0},{1,0,1}}, 1, 1, 2)));
    }

    static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        // dfs starting the starting pixel
        // grab the current pixel's color & color adjoining cells with new color which are of the same color as start pixel
        // visited isn't needed as color check acts as the base case for stopping recursion
        int currentColor = image[sr][sc];
        dfs(image,sr,sc,currentColor,newColor);
        return image;
    }

    static int[] rowOffset = new int[]{0,-1,0,1};
    static int[] colOffset = new int[]{-1,0,1,0};

    static void dfs(int[][] img, int r, int c, int currentColor, int newColor) {
        img[r][c] = newColor;
        //dfs neighbors
        for (int i = 0; i < rowOffset.length; i++) {
            int r1 = r + rowOffset[i];
            int c1 = c + colOffset[i];
            if (r1 >= 0 && r1 < img.length && c1 >= 0 && c1 < img[0].length && img[r1][c1] == currentColor) {
                dfs(img, r1, c1, currentColor, newColor);
            }
        }
    }

}
