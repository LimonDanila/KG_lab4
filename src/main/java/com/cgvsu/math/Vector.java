package com.cgvsu.math;

public abstract class Vector {
    protected float[] vector;

    public float[] getVector() {
        return vector;
    }

    public boolean setCoordinates(float[] vector) {
        if (vector.length != this.vector.length) {
            throw new RuntimeException("The specified vector differs in size from the original one");
        }
        this.vector = vector;
        return true;
    }

    public boolean equals(Vector other) {
        if (other.getVector().length != vector.length) {
            return false;
        }
        final float eps = 1e-6f;
        for (int i = 0; i < vector.length; i++) {
            if (Math.abs(vector[i] - other.getVector()[i]) >= eps)
                return false;
        }
        return true;
    }

    public float getLength() {
        float[] arr = this.vector;
        double sum = 0;
        for (float v : arr) {
            sum += Math.pow(v, 2);
        }
        return (float) Math.sqrt(sum);
    }
}
