package com.cgvsu.render_engine;
//import javax.vecmath.*;
import com.cgvsu.math.*;

public class GraphicConveyor {

    public static Matrix4d rotateScaleTranslate() {
        float[][] matrix = new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
        return new Matrix4d(matrix);
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
        
        

        float[][] matrix = new float[][]{
                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
                {-resultX.dot(eye), -resultY.dot(eye), -resultZ.dot(eye), 1}};
        return new Matrix4d(matrix);
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
        matrix[2][3] = 1.0F;
        matrix[3][2] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        return new Matrix4d(matrix);
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4d matrix4d, final Vector3f vertex) {
        float[][] matrix = matrix4d.getMatrix();
        final float x = (vertex.getX() * matrix[0][0]) + (vertex.getY() * matrix[1][0]) + (vertex.getZ() * matrix[2][0]) + matrix[3][0];
        final float y = (vertex.getX() * matrix[0][1]) + (vertex.getY() * matrix[1][1]) + (vertex.getZ() * matrix[2][1]) + matrix[3][1];
        final float z = (vertex.getX() * matrix[0][2]) + (vertex.getY() * matrix[1][2]) + (vertex.getZ() * matrix[2][2]) + matrix[3][2];
        final float w = (vertex.getX() * matrix[0][3]) + (vertex.getY() * matrix[1][3]) + (vertex.getZ() * matrix[2][3]) + matrix[3][3];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}
