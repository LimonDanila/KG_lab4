package com.cgvsu.math;

public class Vector2f extends Vector {
    public Vector2f(float x, float y) {
        vector = new float[]{x, y};
    }

    public Vector2f add(Vector2f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return new Vector2f(arr1[0] + arr2[0], arr1[1] + arr2[1]);
    }

    public Vector2f subtract(Vector2f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return new Vector2f(arr1[0] - arr2[0], arr1[1] - arr2[1]);
    }

    public Vector2f multiply(float val) {
        float[] arr = this.vector;
        return new Vector2f(arr[0] * val, arr[1] * val);
    }

    public Vector2f div(float val) {
        float[] arr = this.vector;
        return new Vector2f(arr[0] / val, arr[1] / val);
    }

    public Vector2f normalize() {
        float length = this.getLength();
        return this.div(length);
    }

    public float dot(Vector2f vector) {
        float[] arr1 = this.vector;
        float[] arr2 = vector.getVector();
        return arr1[0] * arr2[0] + arr1[1] * arr2[1];
    }
}
