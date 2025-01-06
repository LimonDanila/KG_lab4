package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import javafx.scene.paint.Color;


import java.util.List;

public class Material {
    private boolean showMesh;
    private boolean showIllumination;
    private boolean showTexture;
    private Texture texture;
    private Color mainColor;
    private Color highlightColor;
    private Color basicColor;
    private List<Light> lights;

    public Material(boolean showMesh, boolean showIllumination, boolean showTexture) {
        this.showMesh = showMesh;
        this.showIllumination = showIllumination;
        this.showTexture = showTexture;
    }

    public Material() {
        this.showMesh = false;
        this.showIllumination = false;
        this.showTexture = false;
    }

    public Color useMaterial(float wA, float wB, float wC, Vector2f[] textureVectors, Vector3f[] normalVectors, Vector3f P) {
        //int[] rgb = new int[]{mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue()};
        Color cl = mainColor;
        if (showTexture) {
           cl = Texturing.texturing(textureVectors, texture, wA, wB, wC, cl);
        }
        if (showIllumination) {
            cl= Illumination.illumination(normalVectors, P, lights, wA, wB, wC, rgb);
        }
        if (showMesh) {
            cl= Mech.showMesh(wA, wB, wC, rgb, background);
        }

        return cl;//new Color(rgb[0], rgb[1], rgb[2]);
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
