package com.cgvsu.model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Texture {
    private Image texture;
    private int width;
    private int height;

    public Texture(String filePath) {
        this.texture = new Image(filePath);
        this.width = (int) texture.getWidth();
        this.height = (int) texture.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isNull() {
        return texture == null;
    }

    public Color getColor(int x, int y) {
        PixelReader pixelReader = texture.getPixelReader();
        return pixelReader.getColor(x, y);
    }
}


