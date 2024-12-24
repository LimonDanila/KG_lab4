package com.cgvsu.model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Texture {
    private Image image;
    private int width;
    private int height;

    public Texture(String filePath) {
        this.image = new Image(filePath);
        this.width = (int) image.getWidth();
        this.height = (int) image.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor(int x, int y) {
        PixelReader pixelReader = image.getPixelReader();
        return pixelReader.getColor(x, y);
    }
}


