package com.cgvsu.model;

import javafx.scene.paint.Color;

public class Mech {

    public static void showMesh(float weight_a, float weight_b, float weight_c, int[]rgb, Color background) {

        if (weight_a > 0.02f && weight_b > 0.02f && weight_c > 0.02f) {
            rgb[0] = (int) (background.getRed() * 255);
            rgb[1] = (int) (background.getGreen() * 255);
            rgb[2] = (int) (background.getBlue() * 255);
        }
    }
}
