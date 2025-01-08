//package com.cgvsu.model;
//
//import com.cgvsu.math.Vector2f;
//import com.cgvsu.math.Vector3f;
//import com.cgvsu.render_engine.RenderEngine;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class Rasterizer {
//    private final int width;
//    private final int height;
//    private final float[] zBuffer;
//    private Texture texture;
//    private Light light;
//
//    public Rasterizer(int width, int height) {
//        this.width = width;
//        this.height = height;
//        this.zBuffer = new float[width * height];
//        Arrays.fill(zBuffer, Float.MAX_VALUE);
//    }
//
//    public void setTexture(Texture texture) {
//        this.texture = texture;
//    }
//
//    public void setLight(Light light) {
//        this.light = light;
//    }
//
//    public void rasterizePolygon(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints, ArrayList<Vector2f> textureCoords, ArrayList<Vector3f> normals, Color baseColor) {
//
//        int minY = (int) screenPoints.stream().mapToDouble(Vector2f::getY).min().getAsDouble();
//        int maxY = (int) screenPoints.stream().mapToDouble(Vector2f::getY).max().getAsDouble();
//
//        minY = Math.max(minY, 0);
//        maxY = Math.min(maxY, height - 1);
//
//        for (int y = minY; y <= maxY; y++) {
//
//            ArrayList<Edge> edges = new ArrayList<>();
//            for (int i = 0; i < screenPoints.size(); i++) {
//                Vector2f p1 = screenPoints.get(i);
//                Vector2f p2 = screenPoints.get((i + 1) % screenPoints.size());
//                if ((p1.getY() <= y && p2.getY() > y) || (p2.getY() <= y && p1.getY() > y)) {
//                    edges.add(new Edge(p1, p2, y));
//                }
//            }
//
//            edges.sort((e1, e2) -> Float.compare(e1.x, e2.x));
//
//
//            for (int i = 0; i < edges.size(); i += 2) {
//                Edge left = edges.get(i);
//                Edge right = edges.get(i + 1);
//                for (int x = (int) left.x; x <= (int) right.x; x++) {
//                    if (x >= 0 && x < width) {
//                        float z = interpolateZ(new Vector2f(x, y), screenPoints, worldPoints);
//                        int index = y * width + x;
//                        if (z < zBuffer[index]) {
//                            zBuffer[index] = z;
//                            Vector2f textureCoord = interpolateTextureCoord(new Vector2f(x, y), screenPoints, textureCoords);
//                            Vector3f normal = interpolateNormal(new Vector2f(x, y), screenPoints, normals);
//                            Color pixelColor = calculateLighting(normal, textureCoord, baseColor);
//                            graphicsContext.getPixelWriter().setColor(x, y, pixelColor);
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//
//
//    public void rasterizePolygon(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints, Color baseColor) {
////        int nVertices = screenPoints.size();
////        for (int i = 0; i < nVertices; i++) {
////            Vector2f start = screenPoints.get(i);
////            Vector2f end = screenPoints.get((i + 1) % nVertices);
////            graphicsContext.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
////        }
//        if (screenPoints.size() < 3)
//            return;
//
//        Vector2f p1 = screenPoints.get(0);
//        Vector2f p2 = screenPoints.get(1);
//        Vector2f p3 = screenPoints.get(2);
//
//
//        // Calculate the edge function for the whole triangle (ABC)
//        float edged = RenderEngine.edgeFunction(p1, p2, p3);
//
//        // Our nifty trick: Don't bother drawing the triangle if it's back facing
//        if (edged < 0) {
//            return;
//        }
//
//        // Initialise our point
////        Vector2f pt = new Vector2f(0, 0);
//
//        // Get the bounding box of the triangle
//        float minX = Math.min(p1.getX(), Math.min(p2.getX(), p3.getX()));
//        float minY = Math.min(p1.getY(), Math.min(p2.getY(), p3.getY()));
//        float maxX = Math.max(p1.getX(), Math.max(p2.getX(), p3.getX()));
//        float maxY = Math.max(p1.getY(), Math.max(p2.getY(), p3.getY()));
//
//        // Loop through all the pixels of the bounding box
//        for (int y = (int) minY; y < maxY; y++) {
//            for (int x = (int) minX; x < maxX; x++) {
//                Vector2f pt = new Vector2f(x, y);
//                // Calculate our edge functions
//                float p1p2P = RenderEngine.edgeFunction(p1, p2, pt);
//                float p2p3P = RenderEngine.edgeFunction(p2, p3, pt);
//                float p3p1P = RenderEngine.edgeFunction(p3, p1, pt);
//
//                // If all the edge functions are positive, the point is inside the triangle
//                if (p1p2P >= 0 && p2p3P >= 0 && p3p1P >= 0) {
//                    // Draw the pixel
//                    graphicsContext.getPixelWriter().setColor(x, y, baseColor);
//                }
//            }
//        }
//
//
//    }
//    private float interpolateZ(Vector2f point, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints) {
//        float totalWeight = 0;
//        float totalZ = 0;
//        int n = screenPoints.size();
//        for (int i = 0; i < n; i++) {
//            Vector2f p1 = screenPoints.get(i);
//            Vector2f p2 = screenPoints.get((i + 1) % n);
//            Vector2f p3 = screenPoints.get((i + 2) % n);
//
//            float area = Math.abs((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p2.getY() - p1.getY()) * (p3.getX() - p1.getX()));
//            float weight = Math.abs((p2.getX() - point.getX()) * (p3.getY() - point.getY()) - (p2.getY() - point.getY()) * (p3.getX() - point.getX())) / area;
//
//            totalWeight += weight;
//            totalZ += weight * worldPoints.get(i).getZ();
//        }
//        return totalZ / totalWeight;
//    }
//
//    private Vector2f interpolateTextureCoord(Vector2f point, ArrayList<Vector2f> screenPoints, ArrayList<Vector2f> textureCoords) {
//        float totalWeight = 0;
//        float totalU = 0;
//        float totalV = 0;
//        int n = screenPoints.size();
//        for (int i = 0; i < n; i++) {
//            Vector2f p1 = screenPoints.get(i);
//            Vector2f p2 = screenPoints.get((i + 1) % n);
//            Vector2f p3 = screenPoints.get((i + 2) % n);
//
//            float area = Math.abs((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p2.getY() - p1.getY()) * (p3.getX() - p1.getX()));
//            float weight = Math.abs((p2.getX() - point.getX()) * (p3.getY() - point.getY()) - (p2.getY() - point.getY()) * (p3.getX() - point.getX())) / area;
//
//            totalWeight += weight;
//            totalU += weight * textureCoords.get(i).getX();
//            totalV += weight * textureCoords.get(i).getY();
//        }
//        return new Vector2f(totalU / totalWeight, totalV / totalWeight);
//    }
//
//    private Vector3f interpolateNormal(Vector2f point, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> normals) {
//        float totalWeight = 0;
//        float totalX = 0;
//        float totalY = 0;
//        float totalZ = 0;
//        int n = screenPoints.size();
//        for (int i = 0; i < n; i++) {
//            Vector2f p1 = screenPoints.get(i);
//            Vector2f p2 = screenPoints.get((i + 1) % n);
//            Vector2f p3 = screenPoints.get((i + 2) % n);
//
//            float area = Math.abs((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p2.getY() - p1.getY()) * (p3.getX() - p1.getX()));
//            float weight = Math.abs((p2.getX() - point.getX()) * (p3.getY() - point.getY()) - (p2.getY() - point.getY()) * (p3.getX() - point.getX())) / area;
//
//            totalWeight += weight;
//            totalX += weight * normals.get(i).getX();
//            totalY += weight * normals.get(i).getY();
//            totalZ += weight * normals.get(i).getZ();
//        }
//        return new Vector3f(totalX / totalWeight, totalY / totalWeight, totalZ / totalWeight).normalize();
//    }
//
//    private Color calculateLighting(Vector3f normal, Vector2f textureCoord, Color baseColor) {
//        if (texture == null) {
//            return baseColor;
//        }
//
//        Vector3f lightDir = light.getPosition().subtract(new Vector3f(0, 0, 0)).normalize();
//        float diffuse = Math.max(0, normal.dot(lightDir));
//        Color textureColor = texture.getColor((int) (textureCoord.getX() * texture.getWidth()), (int) (textureCoord.getY() * texture.getHeight()));
//        return baseColor.interpolate(textureColor, diffuse);
//    }
//
//    public void clearZBuffer() {
//        Arrays.fill(zBuffer, Float.MAX_VALUE);
//    }
//
//    private static class Edge {
//        float x;
//        float dx;
//
//        Edge(Vector2f p1, Vector2f p2, int y) {
//            if (p1.getY() < p2.getY()) {
//                this.x = p1.getX() + (y - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
//                this.dx = (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
//            } else {
//                this.x = p2.getX() + (y - p2.getY()) * (p1.getX() - p2.getX()) / (p1.getY() - p2.getY());
//                this.dx = (p1.getX() - p2.getX()) / (p1.getY() - p2.getY());
//            }
//        }
//    }
//}

package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Rasterizer {

    public class  ComparatorVecY implements Comparator<Vector2f> {
        @Override
        public int compare(Vector2f v1, Vector2f v2) {
            if (v1.getY() < v2.getY()) return -1;
            if (v1.getY() > v2.getY())  return 1;
            return 0;
        }
    }


    private final int width;
    private final int height;
    private final float[] zBuffer;
    private Texture texture;
    private Camera camera;
    private Light light;
    private Material mt;

    public Rasterizer(int width, int height, Camera camera) {
        this.width = width;
        this.height = height;
        this.camera = camera;
        this.light = new Light(camera.getPosition(),Color.WHITE);
        this.zBuffer = new float[width * height];
        Arrays.fill(zBuffer, Float.MAX_VALUE);
        mt = new Material();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public void rasterizePolygon(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints, Color baseColor, ArrayList<Vector2f> textureCoords, ArrayList<Vector3f> normalVectors) {

        if (screenPoints.size() < 3)
            return;

        Vector2f p1 = screenPoints.get(0);
        Vector2f p2 = screenPoints.get(1);
        Vector2f p3 = screenPoints.get(2);

        // Calculate the edge function for the whole triangle (ABC)
        float edged = RenderEngine.edgeFunction(p1, p2, p3);

        // Our nifty trick: Don't bother drawing the triangle if it's back facing
        if (edged < 0) {
            return;
        }

        // Get the bounding box of the triangle
        float minX = Math.min(p1.getX(), Math.min(p2.getX(), p3.getX()));
        float minY = Math.min(p1.getY(), Math.min(p2.getY(), p3.getY()));
        float maxX = Math.max(p1.getX(), Math.max(p2.getX(), p3.getX()));
        float maxY = Math.max(p1.getY(), Math.max(p2.getY(), p3.getY()));

        // Loop through all the pixels of the bounding box
        for (int y = (int) minY; y < maxY; y++) {
            for (int x = (int) minX; x < maxX; x++) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    Vector2f pt = new Vector2f(x, y);
                    // Calculate our edge functions
                    float p1p2P = RenderEngine.edgeFunction(p1, p2, pt);
                    float p2p3P = RenderEngine.edgeFunction(p2, p3, pt);
                    float p3p1P = RenderEngine.edgeFunction(p3, p1, pt);

                    float[] weight = calculate_baricentric_coeficients(p1, p2, p3, pt);


                    Vector3f P_world_coord = new Vector3f(worldPoints.get(0).getX() * weight[0] + weight[1] * worldPoints.get(1).getX() + weight[2] * worldPoints.get(2).getX(),
                            worldPoints.get(0).getY() * weight[0] + weight[1] * worldPoints.get(1).getY() + weight[2] * worldPoints.get(2).getY(),
                            worldPoints.get(0).getZ() * weight[0] + weight[1] * worldPoints.get(1).getZ() + weight[2] * worldPoints.get(2).getZ());

                    // If all the edge functions are positive, the point is inside the triangle
                    if (p1p2P >= 0 && p2p3P >= 0 && p3p1P >= 0)
                    {

                        float z = interpolateZ(pt, p1, p2, p3, worldPoints.get(0), worldPoints.get(1), worldPoints.get(2));
                        int index = y * width + x;
                        if (z < zBuffer[index]) {
                            zBuffer[index] = z;
                            ArrayList<Light> lights = new ArrayList<>();
                            lights.add(light);
                            // Draw the pixel
                            mt.setLights(lights);
                            Color clNew = mt.useMaterial(weight[0], weight[1], weight[2], textureCoords, normalVectors, P_world_coord);
                            graphicsContext.getPixelWriter().setColor(x, y, Color.BLUE);
                        }
                    }
                }
            }
        }
    }

    private float interpolateZ(Vector2f point, Vector2f p1, Vector2f p2, Vector2f p3, Vector3f w1, Vector3f w2, Vector3f w3) {
        float det = (p2.getY() - p3.getY()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getY() - p3.getY());
        float l1 = ((p2.getY() - p3.getY()) * (point.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (point.getY() - p3.getY())) / det;
        float l2 = ((p3.getY() - p1.getY()) * (point.getX() - p3.getX()) + (p1.getX() - p3.getX()) * (point.getY() - p3.getY())) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * w1.getZ() + l2 * w2.getZ() + l3 * w3.getZ();
    }

    public void clearZBuffer() {
        Arrays.fill(zBuffer, Float.MAX_VALUE);
    }

    private float[] calculate_baricentric_coeficients(Vector2f A, Vector2f B,
                                                      Vector2f C,
                                                      Vector2f P) {
        float ABC = RenderEngine.edgeFunction(A, B, C);
        float ABP = RenderEngine.edgeFunction(A, B, P);
        float BCP = RenderEngine.edgeFunction(B, C, P);
        float CAP = RenderEngine.edgeFunction(C, A, P);

        float weight_a = BCP / ABC;
        float weight_b = CAP / ABC;
        float weight_c = ABP / ABC;

        return new float[]{weight_a, weight_b, weight_c};
    }

    public void rasterizePolygonv2(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints, Color baseColor, ArrayList<Vector2f> textureCoords, ArrayList<Vector3f> normalVectors) {

        if (screenPoints.size() < 3)
            return;

        ComparatorVecY comparatorVecY = new ComparatorVecY();
        Collections.sort(screenPoints, comparatorVecY);

        Vector2f p1 = screenPoints.get(0);
        Vector2f p2 = screenPoints.get(1);
        Vector2f p3 = screenPoints.get(2);

        float total_height = p3.getY() - p1.getY();

        for (int i = 0; i < total_height; i++) {
            boolean second_half = (i > p2.getY() - p1.getY()) || (p2.getY() == p1.getY());
            float segment_height = second_half ? p3.getY() - p2.getY() : p2.getY() - p1.getY();
            float alpha = (float) i / total_height;
            float beta = (float) (i - (second_half ? p2.getY() - p1.getY() : 0)) / segment_height; // be careful: with above conditions no division by zero here
            Vector2f A = p1.add(p3.subtract(p1).multiply(alpha));

            Vector2f B = second_half ? p2.add(p3.subtract(p2).multiply(beta)) :
                    p1.add(p2.subtract(p1).multiply(beta));

            if (A.getX() > B.getX()) {
                Vector2f tmp = A;
                A = B;
                B = tmp;
            }
            for (int j = (int) A.getX(); j <= (int) B.getX(); j++) {

                Vector2f pt = new Vector2f(j, (int) (p1.getY() + i));
                float[] weight = calculate_baricentric_coeficients(p1, p2, p3, pt);


                Vector3f P_world_coord = new Vector3f(worldPoints.get(0).getX() * weight[0] + weight[1] * worldPoints.get(1).getX() + weight[2] * worldPoints.get(2).getX(),
                        worldPoints.get(0).getY() * weight[0] + weight[1] * worldPoints.get(1).getY() + weight[2] * worldPoints.get(2).getY(),
                        worldPoints.get(0).getZ() * weight[0] + weight[1] * worldPoints.get(1).getZ() + weight[2] * worldPoints.get(2).getZ());


                float z = interpolateZ(pt, p1, p2, p3, worldPoints.get(0), worldPoints.get(1), worldPoints.get(2));
                int index = (int) (p1.getY() + i) * width + j;
                try {
                    if (z < zBuffer[index]) {
                        zBuffer[index] = z;
                        ArrayList<Light> lights = new ArrayList<>();
                        lights.add(light);
                        // Draw the pixel
                        mt.setLights(lights);
                        Color clNew = mt.useMaterial(weight[0], weight[1], weight[2], textureCoords, normalVectors, P_world_coord);
                        graphicsContext.getPixelWriter().setColor(j, (int) (p1.getY() + i), clNew);

                    }
                } catch (Exception e) {
                    //throw new RuntimeException(e);
                }


            }
        }


    }
}



















