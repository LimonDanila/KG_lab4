package com.cgvsu.render_engine;
//import javax.vecmath.Vector3f;
//import javax.vecmath.Matrix4f;
import com.cgvsu.math.*;

public class Camera {

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final Vector3f positionC,
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
        this.positionC = positionC;
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position = this.position.add(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target = this.target.add(translation);
    }

    Matrix4d getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4d getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public Vector3f getPositionC() {
        return positionC;
    }

    public Vector2f getThisPosition() {
        return thisPosition;
    }

    public void addToPositionC(Vector3f vector) {
        positionC = positionC.add(vector);
    }

    public void setPositionC(Vector3f positionC) {
        this.positionC = positionC;
    }

    public void setThisPosition(Vector2f thisPosition) {
        this.thisPosition = thisPosition;
    }

    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
    private Vector3f positionC;
    private Vector2f thisPosition;
}