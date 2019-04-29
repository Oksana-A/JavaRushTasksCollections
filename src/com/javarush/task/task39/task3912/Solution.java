package com.javarush.task.task39.task3912;

/* 
Максимальная площадь
*/

public class Solution {
    public static void main(String[] args) {
        int [][] matrix = new int[][] {
            {1, 1, 1, 1},
            {1, 0, 0, 1},
            {1, 0, 1, 1},
            {1, 1, 1, 1}
        };

        System.out.println(maxSquare(matrix));
    }

    public static int maxSquare(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length+1][matrix[0].length+1];
        int maxSide = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {
                if (matrix[i][k] == 0) {
                    newMatrix[i+1][k+1] = 0;
                }
                if (matrix[i][k] == 1) {
                    newMatrix[i+1][k+1] = 1 + Math.min(newMatrix[i][k], Math.min(newMatrix[i+1][k], newMatrix[i][k+1]));
                    if (newMatrix[i+1][k+1] > maxSide)
                        maxSide = newMatrix[i+1][k+1];
                }
            }
        }
        return maxSide*maxSide;
    }
}
