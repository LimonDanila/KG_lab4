package com.cgvsu.model;

import javafx.scene.paint.Color;

public class Mech {

    public void showMesh(float weight_a, float weight_b, float weight_c, int r, int g, int b, Color background) {

        if (weight_a > 0.02f && weight_b > 0.02f && weight_c > 0.02f) {
            r = background.red(), g = background.green(), b = background.getBlue();
        }
    }
}
