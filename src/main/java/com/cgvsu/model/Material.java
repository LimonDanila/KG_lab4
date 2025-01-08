package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Material {
    private boolean showMesh;
    private boolean showIllumination;
    private boolean showTexture;
    private Texture texture;
    private Color basicColor = Color.BLUE;
    private Color mainColor = basicColor;
    private Color background = Color.WHITE;
    private Color highlightColor = Color.rgb(255, 215, 50);
    private List<Light> lights;

    public Material(boolean showMesh, boolean showIllumination, boolean showTexture) {
        this.showMesh = showMesh;
        this.showIllumination = showIllumination;
        this.showTexture = showTexture;
    }

    public Material() {
        this.showMesh = false;
        this.showIllumination = true;
        this.showTexture = false;
    }

    public Color useMaterial(float wA, float wB, float wC, ArrayList<Vector2f> textureVectors, ArrayList<Vector3f> normalVectors, Vector3f P,Texture texture) {
        int[] rgb = new int[]{
                (int) mainColor.getRed() * 255,
                (int) mainColor.getGreen() * 255,
                (int) mainColor.getBlue() * 255
        };
        if (texture != null){
        setTexture(texture);}
//        Color cl = mainColor;
        if (showTexture) {
            Texturing.texturing(textureVectors, this.texture, wA, wB, wC, rgb);
        }
        if (showIllumination) {
            Illumination.illumination(normalVectors, P, lights, wA, wB, wC, rgb);
        }
        if (showMesh) {
            Mech.showMesh(wA, wB, wC, rgb, background);
        }

        return Color.rgb(rgb[0], rgb[1], rgb[2]);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.showTexture = !texture.isNull();
    }

    public boolean isShowMesh() {
        return showMesh;
    }

    public boolean isShowTexture() {
        return showTexture;
    }

    public boolean isShowIllumination() {
        return showIllumination;
    }

    public void setShowMesh(boolean showMesh) {
        this.showMesh = showMesh;
    }

    public void setShowTexture(boolean showTexture) {
        this.showTexture = showTexture;
    }

    public void setShowIllumination(boolean showIllumination) {
        this.showIllumination = showIllumination;
    }

    public void selectHighlightColor() {
        this.mainColor = highlightColor;
    }

    public void selectBasicColor() {
        this.mainColor = basicColor;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public void setBasicColor(Color basicColor) {
        this.basicColor = basicColor;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }
}
