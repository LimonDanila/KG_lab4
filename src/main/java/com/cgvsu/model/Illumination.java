package com.cgvsu.model;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;

import java.awt.*;
import java.util.List;

public class Illumination {

    public static void illumination(Vector3f[] normalVectors, Vector3f P, List<Light> lights,
                                    float weightA, float weightB, float weightC,
                                    int[] rgb) {
        if (normalVectors.length == 0) {
            return;
        }
        float k = 0.4f;
        float intensity = 1.0f / lights.size();
        rgb[0] *= (1 - k);
        rgb[1] *= (1 - k);
        rgb[2] *= (1 - k);

        for (Light light : lights) {
            float l = calculateParameterOfIllumination(normalVectors, light.getPosition(), P, weightA, weightB, weightC);
            rgb[0] += (int) (intensity * k * l * light.getColor().getRed());
            rgb[1] += (int) (intensity * k * l * light.getColor().getGreen());
            rgb[2] += (int) (intensity * k * l * light.getColor().getBlue());
        }
    }

    private static float calculateParameterOfIllumination(Vector3f[] normalVectors, Vector3f pos, Vector3f P,
                                                          float weightA, float weightB, float weightC) {
        Vector3f normalA = normalVectors[0];
        Vector3f normalB = normalVectors[1];
        Vector3f normalC = normalVectors[2];

        Vector3f vn = (normalA.multiply(weightA).add(normalB.multiply(weightB).add(normalC.multiply(weightC))));
        vn = vn.normalize();
        Vector3f ray = (new Vector3f(P.getX(), P.getY(), P.getZ()).subtract(pos)).normalize();
        float l = -(ray.dot(vn));
        return Math.max(l, 0.0f);
    }

    private static Vector3f toVector3f(Vector4f v){

        return new Vector3f(v.getX() / v.getW(), v.getY() / v.getW(), v.getZ() / v.getW());

    }

}
