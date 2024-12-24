//package com.cgvsu.render_engine;
////import javax.vecmath.Vector3f;
////import javax.vecmath.Matrix4f;
//import com.cgvsu.math.*;
//
//public class Camera {
//
//    public Camera(
//            final Vector3f position,
//            final Vector3f target,
//            final float fov,
//            final float aspectRatio,
//            final float nearPlane,
//            final float farPlane) {
//        this.position = position;
//        this.target = target;
//        this.fov = fov;
//        this.aspectRatio = aspectRatio;
//        this.nearPlane = nearPlane;
//        this.farPlane = farPlane;
//    }
//
//    public void setPosition(final Vector3f position) {
//        this.position = position;
//    }
//
//    public void setTarget(final Vector3f target) {
//        this.target = target;
//    }
//
//    public void setAspectRatio(final float aspectRatio) {
//        this.aspectRatio = aspectRatio;
//    }
//
//    public Vector3f getPosition() {
//        return position;
//    }
//
//    public Vector3f getTarget() {
//        return target;
//    }
//
//    public void movePosition(final Vector3f translation) {
//        this.position = this.position.add(translation);
//    }
//
//    public void moveTarget(final Vector3f translation) {
//        this.target = this.target.add(target);
//    }
//
//    com.cgvsu.math.Matrix4d getViewMatrix() {
//        return GraphicConveyor.lookAt(position, target);
//    }
//
//    com.cgvsu.math.Matrix4d getProjectionMatrix() {
//        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
//    }
//
//    private Vector3f position;
//    private Vector3f target;
//    private float fov;
//    private float aspectRatio;
//    private float nearPlane;
//    private float farPlane;
//}

package com.cgvsu.render_engine;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Matrix4d;
import com.cgvsu.math.Vector4f;

public class Camera {

    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
    private Matrix4d viewMatrix;
    private Matrix4d projectionMatrix;

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        updateViewMatrix();
        updateProjectionMatrix();
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
        updateViewMatrix();
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
        updateViewMatrix();
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
        updateProjectionMatrix();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position = this.position.add(translation);
        updateViewMatrix();
    }

    public void moveTarget(final Vector3f translation) {
        this.target = this.target.add(translation);
        updateViewMatrix();
    }

    public Matrix4d getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4d getProjectionMatrix() {
        return projectionMatrix;
    }

    public void rotateAroundTarget(float angleX, float angleY) {
        Vector3f direction = target.subtract(position).normalize();
        Vector3f right = direction.cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = right.cross(direction).normalize();

        Matrix4d rotationX = Matrix4d.getUnitMatrix4d();
        rotationX.set(1, 1, (float) Math.cos(angleX));
        rotationX.set(1, 2, (float) -Math.sin(angleX));
        rotationX.set(2, 1, (float) Math.sin(angleX));
        rotationX.set(2, 2, (float) Math.cos(angleX));

        Matrix4d rotationY = Matrix4d.getUnitMatrix4d();
        rotationY.set(0, 0, (float) Math.cos(angleY));
        rotationY.set(0, 2, (float) Math.sin(angleY));
        rotationY.set(2, 0, (float) -Math.sin(angleY));
        rotationY.set(2, 2, (float) Math.cos(angleY));

        Matrix4d rotation = rotationX.multiply(rotationY);

        Vector4f direction4 = direction.toVector4(1.0f);
        Vector4f newDirection4 = rotation.multiply(direction4);
        Vector3f newDirection = new Vector3f(newDirection4.getX(), newDirection4.getY(), newDirection4.getZ());

        position = target.subtract(newDirection);
        updateViewMatrix();
    }

    private void updateViewMatrix() {
        viewMatrix = GraphicConveyor.lookAt(position, target);
    }

    private void updateProjectionMatrix() {
        projectionMatrix = GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }
}



