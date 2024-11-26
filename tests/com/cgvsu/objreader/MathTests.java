package com.cgvsu.objreader;

import com.cgvsu.math.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class MathTests {

    @Test
    public void testVector2fAdd() {
        Vector2f vector1 = new Vector2f(1, 2);
        Vector2f vector2 = new Vector2f(2, 3);
        Vector2f result = vector1.add(vector2);
        Vector2f expectedResult = new Vector2f(3, 5);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testVector3fSubtract() {
        Vector3f vector1 = new Vector3f(1, 2, 3);
        Vector3f vector2 = new Vector3f(2, 3, 0);
        Vector3f result = vector1.subtract(vector2);
        Vector3f expectedResult = new Vector3f(-1, -1, 3);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testVector4fMultiply() {
        Vector4f vector = new Vector4f(1, 2, 3, 4);
        float val = 1.1f;
        Vector4f result = vector.multiply(val);
        Vector4f expectedResult = new Vector4f(1.1f, 2.2f, 3.3f, 4.4f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testVector2fDiv() {
        Vector2f vector = new Vector2f(10, 5);
        float val = 2.5f;
        Vector2f result = vector.div(val);
        Vector2f expectedResult = new Vector2f(4, 2);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testVectorGetLength() {
        Vector2f vector = new Vector2f(3, 4);
        float result = vector.getLength();
        float expectedResult = 5;
        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    public void testVector3fNormalize() {
        Vector3f vector = new Vector3f(1, 2, 3);
        Vector3f result = vector.normalize();
        Vector3f expectedResult = new Vector3f(0.267261241f, 0.53452248382f, 0.80178372573f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testVector4fDot() {
        Vector4f vector1 = new Vector4f(1, 2, 3, 4);
        Vector4f vector2 = new Vector4f(1, 2, 3, 3);
        float result = vector1.dot(vector2);
        float expectedResult = 26;
        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    public void testMatrix3dAdd() {
        Matrix3d matrix1 = new Matrix3d(new float[][]
                {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        Matrix3d matrix2 = new Matrix3d(new float[][]
                {{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}});
        Matrix3d result = matrix1.add(matrix2);
        Matrix3d expectedResult = new Matrix3d(new float[][]
                {{2, 4, 6},
                        {8, 10, 12},
                        {14, 16, 18}});
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testMatrix4dSubtract() {
        Matrix4d matrix1 = new Matrix4d(new float[][]
                {{1, 2, 3, 0},
                        {4, 5, 6, 0},
                        {7, 8, 9, 0},
                        {10, 11, 12, 13}});
        Matrix4d matrix2 = new Matrix4d(new float[][]
                {{1, 2, 3, 0},
                        {4, 5, 6, 0},
                        {7, 8, 9, 0},
                        {10, 11, 12, 13}});
        Matrix4d result = matrix1.subtract(matrix2);
        Matrix4d expectedResult = Matrix4d.getZeroMatrix4d();
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testMatrix3dMultiply() {
        Matrix3d matrix1 = new Matrix3d(new float[][]
                {{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}});
        Matrix3d matrix2 = new Matrix3d(new float[][]
                {{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}});
        Matrix3d result = matrix1.multiply(matrix2);
        Matrix3d expectedResult = new Matrix3d(new float[][]
                {{30, 36, 42},
                        {66, 81, 96},
                        {102, 126, 150}});
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testMatrix4dTranspose() {
        Matrix4d matrix = new Matrix4d(new float[][]
                        {{1, 2, 3, 0},
                        {4, 5, 6, 0},
                        {7, 8, 9, 0},
                        {10, 11, 12, 13}});
       Matrix4d result = matrix.transpose();
        Matrix4d expectedResult = new Matrix4d(new float[][]
                        {{1, 4, 7, 10},
                        {2, 5, 8, 11},
                        {3, 6, 9, 12},
                        {0, 0, 0, 13}});
        Assertions.assertTrue(result.equals(expectedResult));
    }
}