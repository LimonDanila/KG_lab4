//package com.cgvsu.math;
//
//import java.util.Arrays;
//
//public class Matrix4d extends Matrix{
//    public Matrix4d(float[][] matrix) {
//        if (matrix.length != 4 || matrix[0].length != 4) {
//            throw new RuntimeException("the transmitted array differs from the dimension of the matrix4d");
//        }
//        this.matrix = matrix;
//    }
//
//    public Matrix4d add(Matrix4d matrix) {
//        float[][] arr = this.matrix;
//        for (int x = 0; x < arr.length; x++) {
//            for (int y = 0; y < arr[0].length; y++) {
//                arr[x][y] += matrix.getMatrix()[x][y];
//            }
//        }
//        return new Matrix4d(arr);
//    }
//
//    public Matrix4d subtract(Matrix4d matrix) {
//        float[][] arr = this.matrix;
//        for (int x = 0; x < arr.length; x++) {
//            for (int y = 0; y < arr[0].length; y++) {
//                arr[x][y] -= matrix.getMatrix()[x][y];
//            }
//        }
//        return new Matrix4d(arr);
//    }
//
//    public Matrix4d multiply(Matrix4d matrix1) {
//        float[][] arr = new float[matrix1.getMatrix().length][matrix1.getMatrix()[0].length];
//        float sum;
//        for (int x = 0; x < arr.length; x++) {
//            for (int y = 0; y < arr[0].length; y++) {
//                sum = 0;
//                for (int i = 0; i < arr.length; i++) {
//                    sum += matrix1.getMatrix()[i][y] * this.matrix[x][i];
//                }
//                arr[x][y] = sum;
//            }
//        }
//        return new Matrix4d(arr);
//    }
//
//    public Vector4f multiply(Vector4f vector) {
//        float sum;
//        float[] arr = new float[vector.getVector().length];
//        for (int i = 0; i < arr.length; i++) {
//            sum = 0;
//            for (int j = 0; j < arr.length; j++) {
//                sum += vector.getVector()[j] * this.matrix[i][j];
//            }
//            arr[i] = sum;
//        }
//        return new Vector4f(arr[0], arr[1], arr[2], arr[3]);
//    }
//
//    public Matrix4d transpose() {
//        float[][] arr = this.matrix;
//        float[][] finalArr = new float[arr.length][arr[0].length];
//        for (int x = 0; x < arr.length; x++) {
//            for (int y = 0; y < arr[0].length; y++) {
//                finalArr[x][y] = arr[y][x];
//            }
//        }
//        return new Matrix4d(finalArr);
//    }
//
//    public static Matrix4d getZeroMatrix4d() {
//        return new Matrix4d(new float[4][4]);
//    }
//
//    public static Matrix4d getUnitMatrix4d() {
//        float[][] matrix = new float[4][4];
//        for (int i = 0; i < matrix.length; i++) {
//            Arrays.fill(matrix[i], 1);
//        }
//        return new Matrix4d(matrix);
//    }
//}

package com.cgvsu.math;

import java.util.Arrays;

public class Matrix4d extends Matrix {
    public Matrix4d(float[][] matrix) {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new RuntimeException("the transmitted array differs from the dimension of the matrix4d");
        }
        this.matrix = matrix;
    }

    public Matrix4d add(Matrix4d matrix) {
        float[][] arr = this.matrix;
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                arr[x][y] += matrix.getMatrix()[x][y];
            }
        }
        return new Matrix4d(arr);
    }

    public Matrix4d subtract(Matrix4d matrix) {
        float[][] arr = this.matrix;
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                arr[x][y] -= matrix.getMatrix()[x][y];
            }
        }
        return new Matrix4d(arr);
    }

    public Matrix4d multiply(Matrix4d matrix1) {
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
        return new Matrix4d(arr);
    }

    public Vector4f multiply(Vector4f vector) {
        float sum;
        float[] arr = new float[vector.getVector().length];
        for (int i = 0; i < arr.length; i++) {
            sum = 0;
            for (int j = 0; j < arr.length; j++) {
                sum += vector.getVector()[j] * this.matrix[i][j];
            }
            arr[i] = sum;
        }
        return new Vector4f(arr[0], arr[1], arr[2], arr[3]);
    }

    public Matrix4d transpose() {
        float[][] arr = this.matrix;
        float[][] finalArr = new float[arr.length][arr[0].length];
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[0].length; y++) {
                finalArr[x][y] = arr[y][x];
            }
        }
        return new Matrix4d(finalArr);
    }

    public static Matrix4d getZeroMatrix4d() {
        return new Matrix4d(new float[4][4]);
    }

    public static Matrix4d getUnitMatrix4d() {
        float[][] matrix = new float[4][4];
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(matrix[i], 1);
        }
        return new Matrix4d(matrix);
    }

    public void set(int row, int col, float value) {
        if (row < 0 || row >= 4 || col < 0 || col >= 4) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        this.matrix[row][col] = value;
    }
}

