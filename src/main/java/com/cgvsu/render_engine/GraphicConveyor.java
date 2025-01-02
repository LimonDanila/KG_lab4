package com.cgvsu.render_engine;
//import javax.vecmath.*;
import com.cgvsu.math.*;
import com.cgvsu.model.Model;

import java.util.ArrayList;

public class GraphicConveyor {

    public static Matrix4d translateRotateScale(Vector3f translate, Vector3f rotate, Vector3f scale) {
        Matrix4d translateMatrix = new Matrix4d(new float[][] {
                {1, 0, 0, translate.getX()},
                {0, 1, 0, translate.getY()},
                {0, 0, 1, translate.getZ()},
                {0, 0, 0, 1}
        });
        Matrix4d scaleMatrix = new Matrix4d(new float[][]  {
                {scale.getX(), 0, 0, 0},
                {0, scale.getY(), 0, 0},
                {0, 0, scale.getZ(), 0},
                {0, 0, 0, 1}
        });
        Matrix4d rotateMatrix = getRotateMatrix(rotate);
        return translateMatrix.multiply(rotateMatrix).multiply(scaleMatrix);
    }

    private static Matrix4d getRotateMatrix(Vector3f rotate) {
        float alpha = (float) ((rotate.getX() * Math.PI) / 180);
        float beta = (float) ((rotate.getY() * Math.PI) / 180);
        float gamma = (float) ((rotate.getZ() * Math.PI) / 180);
        Matrix4d rotateMatrixX = new Matrix4d(new float[][]{
                {1, 0, 0, 0},
                {0, (float) Math.cos(alpha), (float) Math.sin(alpha), 0},
                {0, (float) -Math.sin(alpha), (float) Math.cos(alpha), 0},
                {0, 0, 0, 1}});
        Matrix4d rotateMatrixY = new Matrix4d(new float[][]{
                {(float) Math.cos(beta), 0, (float) Math.sin(beta), 0},
                {0, 1, 0, 0},
                {(float) -Math.sin(beta), 0, (float) Math.cos(beta), 0},
                {0, 0, 0, 1}});
        Matrix4d rotateMatrixZ = new Matrix4d(new float[][]{
                {(float) Math.cos(gamma), (float) Math.sin(gamma), 0, 0},
                {(float) -Math.sin(gamma), (float) Math.cos(gamma), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}});
        return rotateMatrixX.multiply(rotateMatrixY).multiply(rotateMatrixZ);
    }

    public static Matrix4d lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4d lookAt(Vector3f eye, Vector3f target, Vector3f up) {

        Vector3f resultZ = target.subtract(eye);
        Vector3f resultX = up.cross(resultZ);
        Vector3f resultY = resultZ.cross(resultX);
        
        resultZ = resultZ.normalize();
        resultX = resultX.normalize();
        resultY = resultY.normalize();

        Matrix4d translateMatrix = new Matrix4d(new float[][] {
                {1, 0, 0, -eye.getX()},
                {0, 1, 0, -eye.getY()},
                {0, 0, 1, -eye.getZ()},
                {0, 0, 0, 1}
        });

        Matrix4d projectionMatrix = new Matrix4d(new float[][] {
                {resultX.getX(), resultX.getY(), resultX.getZ(), 0},
                {resultY.getX(), resultY.getY(), resultY.getZ(), 0},
                {resultZ.getX(), resultZ.getY(), resultZ.getZ(), 0},
                {0, 0, 0, 1}
        });
        return projectionMatrix.multiply(translateMatrix);
//        float[][] matrix = new float[][]{
//                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
//                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
//                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
//                {-resultX.dot(eye), -resultY.dot(eye), -resultZ.dot(eye), 1}};
//        return new Matrix4d(matrix);
    }

    public static Matrix4d perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float[][] matrix = new float[4][4];
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        matrix[0][0] = tangentMinusOnDegree / aspectRatio;
        matrix[1][1] = tangentMinusOnDegree;
        matrix[2][2] = (farPlane + nearPlane) / (farPlane - nearPlane);
        matrix[3][2] = 1.0F;
        matrix[2][3] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        return new Matrix4d(matrix);
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4d matrix4d, final Vector3f vertex) {
        float[][] matrix = matrix4d.getMatrix();
        final float x = (vertex.getX() * matrix[0][0]) + (vertex.getY() * matrix[0][1]) + (vertex.getZ() * matrix[0][2]) + matrix[0][3];
        final float y = (vertex.getX() * matrix[1][0]) + (vertex.getY() * matrix[1][1]) + (vertex.getZ() * matrix[1][2]) + matrix[1][3];
        final float z = (vertex.getX() * matrix[2][0]) + (vertex.getY() * matrix[2][1]) + (vertex.getZ() * matrix[2][2]) + matrix[2][3];
        final float w = (vertex.getX() * matrix[3][0]) + (vertex.getY() * matrix[3][1]) + (vertex.getZ() * matrix[3][2]) + matrix[3][3];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}
