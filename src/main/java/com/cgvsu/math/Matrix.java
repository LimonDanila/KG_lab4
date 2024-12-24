//package com.cgvsu.math;
//
//public abstract class Matrix {
//    protected float[][] matrix;
//
//    public float[][] getMatrix() {
//        return matrix;
//    }
//
//    public void setMatrix(float[][] matrix) {
//        if (this.matrix.length != matrix.length || this.matrix[0].length != matrix[0].length) {
//            throw new RuntimeException("the matrix differs in size from the original one");
//        }
//        this.matrix = matrix;
//    }
//
//    public boolean equals(Matrix matrix) {
//        if (this.matrix.length != matrix.getMatrix().length || this.matrix[0].length != matrix.getMatrix()[0].length) {
//            return false;
//        }
//        final float eps = 1e-7f;
//        for (int x = 0; x < this.matrix.length; x++) {
//            for (int y = 0; y < this.matrix[0].length; y++) {
//                if (Math.abs(this.matrix[x][y] - matrix.getMatrix()[x][y]) >= eps) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//}

package com.cgvsu.math;

public abstract class Matrix {
    protected float[][] matrix;

    public float[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(float[][] matrix) {
        if (this.matrix.length != matrix.length || this.matrix[0].length != matrix[0].length) {
            throw new RuntimeException("the matrix differs in size from the original one");
        }
        this.matrix = matrix;
    }

    public boolean equals(Matrix matrix) {
        if (this.matrix.length != matrix.getMatrix().length || this.matrix[0].length != matrix.getMatrix()[0].length) {
            return false;
        }
        final float eps = 1e-7f;
        for (int x = 0; x < this.matrix.length; x++) {
            for (int y = 0; y < this.matrix[0].length; y++) {
                if (Math.abs(this.matrix[x][y] - matrix.getMatrix()[x][y]) >= eps) {
                    return false;
                }
            }
        }
        return true;
    }
}

