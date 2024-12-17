package com.cgvsu.math;

import java.util.Arrays;

public class Matrix3d extends Matrix{

    public Matrix3d(float[][] matrix) {
        if (matrix.length != 3 || matrix[0].length != 3) {
            throw new RuntimeException("the transmitted array differs from the dimension of the matrix3d");
        }
        this.matrix = matrix;
    }

    public Matrix3d add(Matrix3d matrix) {
        float[][] arr = this.matrix;
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                arr[x][y] += matrix.getMatrix()[x][y];
            }
        }
        return new Matrix3d(arr);
    }

    public Matrix3d subtract(Matrix3d matrix) {
        float[][] arr = this.matrix;
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                arr[x][y] -= matrix.getMatrix()[x][y];
            }
        }
        return new Matrix3d(arr);
    }

    public Matrix3d multiply(Matrix3d matrix1) {
        float[][] arr = new float[matrix1.getMatrix().length][matrix1.getMatrix()[0].length];
        float sum;
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                sum = 0;
                for (int i = 0; i < arr.length; i++) {
                    sum += matrix1.getMatrix()[i][y] * this.matrix[x][i];
                }
                arr[x][y] = sum;
            }
        }
        return new Matrix3d(arr);
    }

    public Vector3f multiply(Vector3f vector) {
        float sum;
        float[] arr = new float[vector.getVector().length];
        for (int i = 0; i < arr.length; i++) {
            sum = 0;
            for (int j = 0; j < arr.length; j++) {
                sum += vector.getVector()[j] * this.matrix[i][j];
            }
            arr[i] = sum;
        }
        return new Vector3f(arr[0], arr[1], arr[2]);
    }

    public Matrix3d transpose() {
        float[][] arr = this.matrix;
        float[][] finalArr = new float[arr.length][arr[0].length];
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                finalArr[x][y] = arr[y][x];
            }
        }
        return new Matrix3d(finalArr);
    }

    public static Matrix3d getZeroMatrix3d() {
        return new Matrix3d(new float[3][3]);
    }

    public static Matrix3d getUnitMatrix3d() {
        float[][] matrix = new float[3][3];
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], 1);
        }
        return new Matrix3d(matrix);
    }
}
