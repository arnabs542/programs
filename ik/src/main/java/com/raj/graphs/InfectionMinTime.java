package com.raj.graphs;

import java.util.LinkedList;
import java.util.Queue;

public class InfectionMinTime {

    /**
     Given a matrix of dimension RxC where each cell in the matrix can have values 0, 1 or 2 which has the following meaning:
     0 : Empty cell
     1 : Healthy Patient
     2 : Covid Patient
     We have to determine what is the minimum time required to spread disease. A covid patient at index [i,j] can infect other at indexes [i-1,j], [i+1,j], [i,j-1], [i,j+1] (up, down, left and right) in unit time. 

     Testcase 1:
     2 | 1 | 0 | 2 | 1
     1 | 0 | 1 | 2 | 1
     1 | 0 | 0 | 2 | 1 
     Covid patients at positions {0,0}, {0, 3}, {1, 3} and {2, 3} will infect cells at {0, 1}, {1, 0}, {0, 4}, {1, 2}, {1, 4}, {2, 4} during 1st unit time. And, during 2nd unit time, covid patient at {1, 0} got infected and will infect cell at {2, 0}. Hence, total 2 unit of time is required to infect all cells. 

     Edge case: what if grid is mostly infected and few healthy patient exist - how does that affect minTime ? BFS wud terminate even before traversing whole grid
     */

    public static void main(String[] args) {
        int[][] grid = new int[][] {
                {2,1,0,2,1},
                {1,0,1,2,1},
                {1,0,0,2,1}
        };
        System.out.println(minTimeToInfect(grid));
    }

    public static int minTimeToInfect(int[][] grid) {
        if (grid.length == 0) {
            return 0;
        }

        int n =  grid.length;
        int m = grid[0].length;
        int healthyPatients = 0, minTime = 0;
        Queue<int[]> infectedPatients = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 1) {
                    healthyPatients++;
                }
                if(grid[i][j] == 2) {
                    int[] patientDim = new int[2];
                    patientDim[0] = i;
                    patientDim[1] = j;
                    infectedPatients.add(patientDim);
                }
            }
        }

        if (healthyPatients == 0 || infectedPatients.size() == 0) {
            return 0;
        }

        while (healthyPatients != 0 || !infectedPatients.isEmpty()) {
            Queue<int[]> currentQueue = new LinkedList<>();

            while (!infectedPatients.isEmpty()) {
                int[] currentPatient = infectedPatients.poll();
                int x = currentPatient[0];
                int y = currentPatient[1];
                if(x - 1 >= 0 && grid[x-1][y] == 1) {
                    currentQueue.add(new int[]{x-1,y});
                    grid[x-1][y] = 2;
                    healthyPatients--;
                }
                if (x + 1 < n && grid[x+1][y] == 1) {
                    currentQueue.add(new int[]{x+1,y});
                    grid[x+1][y] = 2;
                    healthyPatients--;
                }
                if (y - 1 >= 0 && grid[x][y-1] == 1) {
                    currentQueue.add(new int[]{x,y-1});
                    grid[x][y-1] = 2;
                    healthyPatients--;
                }
                if (y + 1 < m && grid[x][y+1] == 1) {
                    currentQueue.add(new int[]{x,y+1});
                    grid[x][y+1] = 2;
                    healthyPatients--;
                }
            }
            if (healthyPatients == 0) { // not reqd to track healthyPatients, if we check if no patient were infected in this iteration, then just exit
                minTime++;
                return minTime;
            } else {
                minTime++;
            }
            infectedPatients = currentQueue;
        }

        return minTime;
    }

}
