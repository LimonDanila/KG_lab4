package com.cgvsu.math;

public class Vector4f extends Vector {
    public Vector4f(float x, float y, float z, float w) {
        vector = new float[]{x, y, z, w};
    }

    public float getX() {
        return vector[0];
    }

    public float getY() {
        return vector[1];
    }

    public float getZ() {
        return vector[2];
    }

    public float getW() {
        return vector[3];
    }

    public Vector4f add(Vector4f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return new Vector4f(arr1[0] + arr2[0], arr1[1] + arr2[1], arr1[2] + arr2[2], arr1[3] + arr2[3]);
    }

    public Vector4f subtract(Vector4f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return new Vector4f(arr1[0] - arr2[0], arr1[1] - arr2[1], arr1[2] - arr2[2], arr1[3] - arr2[3]);
    }

    public Vector4f multiply(float val) {
        float[] arr = this.vector;
        return new Vector4f(arr[0] * val, arr[1] * val, arr[2] * val, arr[3] * val);
    }

    public Vector4f div(float val) {
        float[] arr = this.vector;
        return new Vector4f(arr[0] / val, arr[1] / val, arr[2] / val, arr[3] / val);
    }

    public Vector4f normalize() {
        float length = this.getLength();
        return this.div(length);
    }

    public float dot(Vector4f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return arr1[0] * arr2[0] + arr1[1] * arr2[1] + arr1[2] * arr2[2] + arr1[3] * arr2[3];
    }
}
