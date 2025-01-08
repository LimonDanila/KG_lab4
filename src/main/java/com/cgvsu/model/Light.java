package com.cgvsu.model;

import com.cgvsu.math.Vector3f;
import javafx.scene.paint.Color;

public class Light {
    private Vector3f position;
//    private Vector3f color;
    private Color color;

    public Light(Vector3f position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    //    public Vector3f getColor() {
//        return color;
//    }
}
