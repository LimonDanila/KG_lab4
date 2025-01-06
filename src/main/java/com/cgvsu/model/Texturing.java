package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import javafx.scene.paint.Color;

import static java.lang.Math.clamp;

public class Texturing {

    public static Color texturing(Vector2f[] texture_vectors, Texture texture, float weight_a,
                          float weight_b, float weight_c, Color color/*int r, int g, Integer b*/)
    {
        if (texture_vectors.length == 0 || texture == null) {
            return null;
        }
        Color texColor = getSuitablePixel(texture_vectors, texture, weight_a, weight_b, weight_c);
//        r = (int) texColor.getRed();
//        g = (int) texColor.getGreen();
//        b = (int) texColor.getBlue();
        return new Color(texColor.getRed(),texColor.getGreen(),texColor.getBlue());
    }

    public static Color getSuitablePixel(Vector2f[] texture_vectors, Texture texture,
                                         float weight_a, float weight_b, float weight_c)
    {
        float u = weight_a * texture_vectors[0].getX() + weight_b * texture_vectors[1].getX() + weight_c * texture_vectors[2].
                getX();
        float v = weight_a * texture_vectors[0].getY() + weight_b * texture_vectors[1].getY() + weight_c * texture_vectors[2].
                getY();
        int texX = (int)((float)(texture.getWidth() - 1) - u * (float)(texture.getWidth() - 1));
        int texY = (int)((float)(texture.getHeight() - 1) - v * (float)(texture.getHeight() - 1));
        texX = clamp(texX, 0, texture.getWidth() - 1);
        texY = clamp(texY, 0, texture.getHeight() - 1);

        return texture.getColor(texX, texY);
    }

    private static int clamp (int v, int lo, int hi){
        if (lo<=hi){
            if (v<=hi && v>=lo)
                return v;
            else if (v<lo)
                    return lo;
            else if (v>hi)
                return hi;
        }
    }
}
