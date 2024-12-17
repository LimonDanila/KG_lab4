package com.cgvsu.math;

public class Vector3f extends Vector {
    public Vector3f(float x, float y, float z) {
        vector = new float[]{x, y, z};
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

    public Vector3f add(Vector3f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return new Vector3f(arr1[0] + arr2[0], arr1[1] + arr2[1], arr1[2] + arr2[2]);
    }

    public Vector3f subtract(Vector3f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return new Vector3f(arr1[0] - arr2[0], arr1[1] - arr2[1], arr1[2] - arr2[2]);
    }

    public Vector3f multiply(float val) {
        float[] arr = this.vector;
        return new Vector3f(arr[0] * val, arr[1] * val, arr[2] * val);
    }

    public Vector3f div(float val) {
        float[] arr = this.vector;
        return new Vector3f(arr[0] / val, arr[1] / val, arr[2] / val);
    }

    public Vector3f normalize() {
        float length = this.getLength();
        return this.div(length);
    }

    public float dot(Vector3f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return arr1[0] * arr2[0] + arr1[1] * arr2[1] + arr1[2] * arr2[2];
    }

    public Vector3f cross(Vector3f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return new Vector3f(
                arr1[1] * arr2[2] - arr1[2] * arr2[1],
                - arr1[0] * arr2[2] + arr1[2] * arr2[0],
                arr1[0] * arr2[1] - arr1[1] * arr2[0]
        );
    }
}
