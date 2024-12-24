package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.render_engine.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
//первая версия
//public class Rasterizer {
//    private final int width;
//    private final int height;
//    private final float[] zBuffer;
//
//    public Rasterizer(int width, int height) {
//        this.width = width;
//        this.height = height;
//        this.zBuffer = new float[width * height];
//        Arrays.fill(zBuffer, Float.MAX_VALUE);
//    }
//
//    public void rasterizePolygon(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints, Color color) {
//        int minX = (int) screenPoints.stream().mapToDouble(Vector2f::getX).min().getAsDouble();
//        int maxX = (int) screenPoints.stream().mapToDouble(Vector2f::getX).max().getAsDouble();
//        int minY = (int) screenPoints.stream().mapToDouble(Vector2f::getY).min().getAsDouble();
//        int maxY = (int) screenPoints.stream().mapToDouble(Vector2f::getY).max().getAsDouble();
//
//        minX = Math.max(minX, 0);
//        maxX = Math.min(maxX, width - 1);
//        minY = Math.max(minY, 0);
//        maxY = Math.min(maxY, height - 1);
//
//        for (int y = minY; y <= maxY; y++) {
//            for (int x = minX; x <= maxX; x++) {
//                if (isPointInsidePolygon(new Vector2f(x, y), screenPoints)) {
//                    float z = interpolateZ(new Vector2f(x, y), screenPoints, worldPoints);
//                    int index = y * width + x;
//                    if (z < zBuffer[index]) {
//                        zBuffer[index] = z;
//                        graphicsContext.getPixelWriter().setColor(x, y, color);
//                    }
//                }
//            }
//        }
//    }
//
//    private boolean isPointInsidePolygon(Vector2f point, ArrayList<Vector2f> polygon) {
//        boolean inside = false;
//        int n = polygon.size();
//        for (int i = 0, j = n - 1; i < n; j = i++) {
//            if ((polygon.get(i).getY() > point.getY()) != (polygon.get(j).getY() > point.getY()) &&
//                    point.getX() < (polygon.get(j).getX() - polygon.get(i).getX()) * (point.getY() - polygon.get(i).getY()) / (polygon.get(j).getY() - polygon.get(i).getY()) + polygon.get(i).getX()) {
//                inside = !inside;
//            }
//        }
//        return inside;
//    }
//
//    private float interpolateZ(Vector2f point, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints) {
//        float totalWeight = 0;
//        float totalZ = 0;
//        for (int i = 0; i < screenPoints.size(); i++) {
//            Vector2f p1 = screenPoints.get(i);
//            Vector2f p2 = screenPoints.get((i + 1) % screenPoints.size());
//            Vector2f p3 = screenPoints.get((i + 2) % screenPoints.size());
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
//    private float[] GaussMethod(Vector2f pointA, Vector2f pointB, Vector2f pointC, Vector2f desiredPoint) {
//        float[] result = new float[3];
//        float[][] matrix = new float[][] {
//                {pointA.getX(), pointB.getX(), pointC.getX(), desiredPoint.getX()},
//                {pointA.getY(), pointB.getY(), pointC.getY(), desiredPoint.getY()},
//                {1, 1, 1, 1}
//        };
//        float coefOne = matrix[0][0];
//        float coefTwo = matrix[1][0];
//        for (int i = 0; i < matrix[0].length; i++) {
//            matrix[0][i] -= matrix[2][i] * coefOne;
//            matrix[1][i] -= matrix[2][i] * coefTwo;
//        }
//        if (matrix[1][1] == 0) {
//            float[] mas = matrix[1];
//            matrix[1] = matrix[0];
//            matrix[0] = mas;
//        }
//        coefOne = matrix[0][1] / matrix[1][1];
//        for (int i = 1; i < matrix[0].length; i++) {
//            matrix[0][i] -= matrix[1][i] * coefOne;
//        }
//        result[2] = matrix[0][3] / matrix[0][2];
//        result[1] = (matrix[1][3] - matrix[1][2] * result[2]) / matrix[1][1];
//        result[0] = matrix[2][3] - result[2] - result[1];
//        return result;
//    }
//}



                                    //вторая версия
//public class Rasterizer {
//    private final int width;
//    private final int height;
//    private final float[] zBuffer;
//
//    public Rasterizer(int width, int height) {
//        this.width = width;
//        this.height = height;
//        this.zBuffer = new float[width * height];
//        Arrays.fill(zBuffer, Float.MAX_VALUE);
//    }
//
//    public void rasterizePolygon(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints, Color color) {
//        int minX = (int) screenPoints.stream().mapToDouble(Vector2f::getX).min().getAsDouble();
//        int maxX = (int) screenPoints.stream().mapToDouble(Vector2f::getX).max().getAsDouble();
//        int minY = (int) screenPoints.stream().mapToDouble(Vector2f::getY).min().getAsDouble();
//        int maxY = (int) screenPoints.stream().mapToDouble(Vector2f::getY).max().getAsDouble();
//
//        minX = Math.max(minX, 0);
//        maxX = Math.min(maxX, width - 1);
//        minY = Math.max(minY, 0);
//        maxY = Math.min(maxY, height - 1);
//
//        for (int y = minY; y <= maxY; y++) {
//            for (int x = minX; x <= maxX; x++) {
//                if (isPointInsidePolygon(new Vector2f(x, y), screenPoints)) {
//                    float z = interpolateZ(new Vector2f(x, y), screenPoints, worldPoints);
//                    int index = y * width + x;
//                    if (z < zBuffer[index]) {
//                        zBuffer[index] = z;
//                        graphicsContext.getPixelWriter().setColor(x, y, color);
//                    }
//                }
//            }
//        }
//    }
//
//    private boolean isPointInsidePolygon(Vector2f point, ArrayList<Vector2f> polygon) {
//        boolean inside = false;
//        int n = polygon.size();
//        for (int i = 0, j = n - 1; i < n; j = i++) {
//            if ((polygon.get(i).getY() > point.getY()) != (polygon.get(j).getY() > point.getY()) &&
//                    point.getX() < (polygon.get(j).getX() - polygon.get(i).getX()) * (point.getY() - polygon.get(i).getY()) / (polygon.get(j).getY() - polygon.get(i).getY()) + polygon.get(i).getX()) {
//                inside = !inside;
//            }
//        }
//        return inside;
//    }
//
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
//    public void clearZBuffer() {
//        Arrays.fill(zBuffer, Float.MAX_VALUE);
//    }
//}

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Rasterizer {
    private final int width;
    private final int height;
    private final float[] zBuffer;
    private Texture texture;
    private Light light;

    public Rasterizer(int width, int height) {
        this.width = width;
        this.height = height;
        this.zBuffer = new float[width * height];
        Arrays.fill(zBuffer, Float.MAX_VALUE);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public void rasterizePolygon(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints, ArrayList<Vector2f> textureCoords, ArrayList<Vector3f> normals, Color baseColor) {
        int minY = (int) screenPoints.stream().mapToDouble(Vector2f::getY).min().getAsDouble();
        int maxY = (int) screenPoints.stream().mapToDouble(Vector2f::getY).max().getAsDouble();

        minY = Math.max(minY, 0);
        maxY = Math.min(maxY, height - 1);

        for (int y = minY; y <= maxY; y++) {
            ArrayList<Edge> edges = new ArrayList<>();
            for (int i = 0; i < screenPoints.size(); i++) {
                Vector2f p1 = screenPoints.get(i);
                Vector2f p2 = screenPoints.get((i + 1) % screenPoints.size());
                if ((p1.getY() <= y && p2.getY() > y) || (p2.getY() <= y && p1.getY() > y)) {
                    edges.add(new Edge(p1, p2, y));
                }
            }
            edges.sort((e1, e2) -> Float.compare(e1.x, e2.x));

            for (int i = 0; i < edges.size(); i += 2) {
                Edge left = edges.get(i);
                Edge right = edges.get(i + 1);
                for (int x = (int) left.x; x <= (int) right.x; x++) {
                    if (x >= 0 && x < width) {
                        float z = interpolateZ(new Vector2f(x, y), screenPoints, worldPoints);
                        int index = y * width + x;
                        if (z < zBuffer[index]) {
                            zBuffer[index] = z;
                            Vector2f textureCoord = interpolateTextureCoord(new Vector2f(x, y), screenPoints, textureCoords);
                            Vector3f normal = interpolateNormal(new Vector2f(x, y), screenPoints, normals);
                            Color pixelColor = calculateLighting(normal, textureCoord, baseColor);
                            graphicsContext.getPixelWriter().setColor(x, y, pixelColor);
                        }
                    }
                }
            }
        }
    }

    private float interpolateZ(Vector2f point, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> worldPoints) {
        float totalWeight = 0;
        float totalZ = 0;
        int n = screenPoints.size();
        for (int i = 0; i < n; i++) {
            Vector2f p1 = screenPoints.get(i);
            Vector2f p2 = screenPoints.get((i + 1) % n);
            Vector2f p3 = screenPoints.get((i + 2) % n);

            float area = Math.abs((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p2.getY() - p1.getY()) * (p3.getX() - p1.getX()));
            float weight = Math.abs((p2.getX() - point.getX()) * (p3.getY() - point.getY()) - (p2.getY() - point.getY()) * (p3.getX() - point.getX())) / area;

            totalWeight += weight;
            totalZ += weight * worldPoints.get(i).getZ();
        }
        return totalZ / totalWeight;
    }

    private Vector2f interpolateTextureCoord(Vector2f point, ArrayList<Vector2f> screenPoints, ArrayList<Vector2f> textureCoords) {
        float totalWeight = 0;
        float totalU = 0;
        float totalV = 0;
        int n = screenPoints.size();
        for (int i = 0; i < n; i++) {
            Vector2f p1 = screenPoints.get(i);
            Vector2f p2 = screenPoints.get((i + 1) % n);
            Vector2f p3 = screenPoints.get((i + 2) % n);

            float area = Math.abs((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p2.getY() - p1.getY()) * (p3.getX() - p1.getX()));
            float weight = Math.abs((p2.getX() - point.getX()) * (p3.getY() - point.getY()) - (p2.getY() - point.getY()) * (p3.getX() - point.getX())) / area;

            totalWeight += weight;
            totalU += weight * textureCoords.get(i).getX();
            totalV += weight * textureCoords.get(i).getY();
        }
        return new Vector2f(totalU / totalWeight, totalV / totalWeight);
    }

    private Vector3f interpolateNormal(Vector2f point, ArrayList<Vector2f> screenPoints, ArrayList<Vector3f> normals) {
        float totalWeight = 0;
        float totalX = 0;
        float totalY = 0;
        float totalZ = 0;
        int n = screenPoints.size();
        for (int i = 0; i < n; i++) {
            Vector2f p1 = screenPoints.get(i);
            Vector2f p2 = screenPoints.get((i + 1) % n);
            Vector2f p3 = screenPoints.get((i + 2) % n);

            float area = Math.abs((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p2.getY() - p1.getY()) * (p3.getX() - p1.getX()));
            float weight = Math.abs((p2.getX() - point.getX()) * (p3.getY() - point.getY()) - (p2.getY() - point.getY()) * (p3.getX() - point.getX())) / area;

            totalWeight += weight;
            totalX += weight * normals.get(i).getX();
            totalY += weight * normals.get(i).getY();
            totalZ += weight * normals.get(i).getZ();
        }
        return new Vector3f(totalX / totalWeight, totalY / totalWeight, totalZ / totalWeight).normalize();
    }

    private Color calculateLighting(Vector3f normal, Vector2f textureCoord, Color baseColor) {
        if (texture == null) {
            return baseColor;
        }

        Vector3f lightDir = light.getPosition().subtract(new Vector3f(0, 0, 0)).normalize();
        float diffuse = Math.max(0, normal.dot(lightDir));
        Color textureColor = texture.getColor((int) (textureCoord.getX() * texture.getWidth()), (int) (textureCoord.getY() * texture.getHeight()));
        return baseColor.interpolate(textureColor, diffuse);
    }

    public void clearZBuffer() {
        Arrays.fill(zBuffer, Float.MAX_VALUE);
    }

    private static class Edge {
        float x;
        float dx;

        Edge(Vector2f p1, Vector2f p2, int y) {
            if (p1.getY() < p2.getY()) {
                this.x = p1.getX() + (y - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
                this.dx = (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
            } else {
                this.x = p2.getX() + (y - p2.getY()) * (p1.getX() - p2.getX()) / (p1.getY() - p2.getY());
                this.dx = (p1.getX() - p2.getX()) / (p1.getY() - p2.getY());
            }
        }
    }
}

















