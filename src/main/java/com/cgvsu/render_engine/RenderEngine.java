//package com.cgvsu.render_engine;
//
//import java.util.ArrayList;
//import com.cgvsu.model.Rasterizer;
//import com.cgvsu.model.TriangulatedModel;
//import com.cgvsu.model.VertexNormalsCalculator;
//import javafx.scene.canvas.GraphicsContext;
//
//import com.cgvsu.math.*;
//import com.cgvsu.model.Model;
//import javafx.scene.paint.Color;
//
//import static com.cgvsu.render_engine.GraphicConveyor.*;
//
//
//
//public class RenderEngine {
//
//    public static void render(
//            final GraphicsContext graphicsContext,
//            final Camera camera,
//            final Model mesh,
//            final int width,
//            final int height)
//    {
//        TriangulatedModel.triangulateModel(mesh);
//        VertexNormalsCalculator.calculateNormals(mesh);
//        Matrix4d modelMatrix = rotateScaleTranslate();
//        Matrix4d viewMatrix = camera.getViewMatrix();
//        Matrix4d projectionMatrix = camera.getProjectionMatrix();
//
//        Matrix4d modelViewProjectionMatrix = modelMatrix.multiply(viewMatrix).multiply(projectionMatrix);
//
//        Rasterizer rasterizer = new Rasterizer(width, height);
//
//        final int nPolygons = mesh.polygons.size();
//        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
//            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();
//
//            ArrayList<Vector2f> screenPoints = new ArrayList<>();
//            ArrayList<Vector3f> worldPoints = new ArrayList<>();
//            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
//                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
//
//                Vector3f transformedVertex = multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex);
//                Vector2f screenPoint = vertexToPoint(transformedVertex, width, height);
//
//                screenPoints.add(screenPoint);
//                worldPoints.add(transformedVertex);
//            }
//
//            rasterizer.rasterizePolygon(graphicsContext, screenPoints, worldPoints, Color.BLUE,camera);
//            drawPolygonOutline(graphicsContext,screenPoints);
//        }
//
//    }
//
//    private static void drawPolygonOutline(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints) {
//        int nVertices = screenPoints.size();
//        for (int i = 0; i < nVertices; i++) {
//            Vector2f start = screenPoints.get(i);
//            Vector2f end = screenPoints.get((i + 1) % nVertices);
//            graphicsContext.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
//        }
//    }
//}


                //вторая версия(работает вместе со второй версией расторизации)
//package com.cgvsu.render_engine;
//
//import java.util.ArrayList;
//import com.cgvsu.model.Rasterizer;
//import com.cgvsu.model.TriangulatedModel;
//import com.cgvsu.model.VertexNormalsCalculator;
//import javafx.scene.canvas.GraphicsContext;
//
//import com.cgvsu.math.*;
//import com.cgvsu.model.Model;
//import javafx.scene.paint.Color;
//
//import static com.cgvsu.render_engine.GraphicConveyor.*;
//
//public class RenderEngine {
//
//    public static void render(
//            final GraphicsContext graphicsContext,
//            final Camera camera,
//            final Model mesh,
//            final int width,
//            final int height)
//    {
//        TriangulatedModel.triangulateModel(mesh);
//        VertexNormalsCalculator.calculateNormals(mesh);
//        Matrix4d modelMatrix = rotateScaleTranslate();
//        Matrix4d viewMatrix = camera.getViewMatrix();
//        Matrix4d projectionMatrix = camera.getProjectionMatrix();
//
//        Matrix4d modelViewProjectionMatrix = modelMatrix.multiply(viewMatrix).multiply(projectionMatrix);
//
//        Rasterizer rasterizer = new Rasterizer(width, height);
//        rasterizer.clearZBuffer(); // Очистка Z-буфера перед рендерингом
//
//        final int nPolygons = mesh.polygons.size();
//        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
//            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();
//
//            ArrayList<Vector2f> screenPoints = new ArrayList<>();
//            ArrayList<Vector3f> worldPoints = new ArrayList<>();
//            Vector3f polygonNormal = new Vector3f(0, 0, 0);
//
//            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
//                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
//
//                Vector3f transformedVertex = multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex);
//                Vector2f screenPoint = vertexToPoint(transformedVertex, width, height);
//
//                screenPoints.add(screenPoint);
//                worldPoints.add(transformedVertex);
//
//                // Accumulate the normal for the polygon
//                polygonNormal = polygonNormal.add(mesh.normals.get(mesh.polygons.get(polygonInd).getNormalIndices().get(vertexInPolygonInd)));
//            }
//
//            // Normalize the polygon normal
//            polygonNormal = polygonNormal.normalize();
//
//            // Get the camera direction
//            Vector3f cameraDirection = camera.getTarget().subtract(camera.getPosition()).normalize();
//
//            // Check if the polygon is facing the camera
//            if (polygonNormal.dot(cameraDirection) >= 0) {
//                rasterizer.rasterizePolygon(graphicsContext, screenPoints, worldPoints, Color.BLUE);
//                drawPolygonOutline(graphicsContext, screenPoints);
//            }
//        }
//    }
//
//    private static void drawPolygonOutline(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints) {
//        int nVertices = screenPoints.size();
//        for (int i = 0; i < nVertices; i++) {
//            Vector2f start = screenPoints.get(i);
//            Vector2f end = screenPoints.get((i + 1) % nVertices);
//            graphicsContext.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
//        }
//    }
//}



// рабочий варик без освещения
package com.cgvsu.render_engine;

import java.util.ArrayList;
import com.cgvsu.model.Rasterizer;
import com.cgvsu.model.TriangulatedModel;
import com.cgvsu.model.VertexNormalsCalculator;
import javafx.scene.canvas.GraphicsContext;

import com.cgvsu.math.*;
import com.cgvsu.model.Model;
import com.cgvsu.model.Texture;
import com.cgvsu.model.Light;
import javafx.scene.paint.Color;

import static com.cgvsu.render_engine.GraphicConveyor.*;
//import static com.cgvsu.render_engine.GraphicConveyor.rotateScaleTranslate;

public class RenderEngine {
    public RenderEngine() {

    }

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final Texture texture,
            final Light light,
            final int width,
            final int height,
            Matrix4d modelMatrix4d)
    {

//        Matrix4d modelMatrix = modelMatrix4d;
        Matrix4d modelMatrix = new Matrix4d(new float[][]
                {{1,0,0,0},
                {0,1,0,0},
                {0,0,1,0},
                {0,0,0,1}}
                        );
        Matrix4d viewMatrix = camera.getViewMatrix();
        Matrix4d projectionMatrix = camera.getProjectionMatrix();

        Matrix4d modelViewProjectionMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);
//        Matrix4d modelMatrix = rotateScaleTranslate();
//        Matrix4d viewMatrix = camera.getViewMatrix();
//        Matrix4d projectionMatrix = camera.getProjectionMatrix();

//        Matrix4d modelViewProjectionMatrix = modelMatrix.multiply(viewMatrix).multiply(projectionMatrix);
//        Matrix4d modelViewProjectionMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);

        Rasterizer rasterizer = new Rasterizer(width, height,camera);
        rasterizer.setTexture(texture);
        rasterizer.setLight(light);
        rasterizer.clearZBuffer(); // Очистка Z-буфера перед рендерингом

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Vector2f> screenPoints = new ArrayList<>();
            ArrayList<Vector3f> worldPoints = new ArrayList<>();
            ArrayList<Vector2f> textureCoords = new ArrayList<>();
            ArrayList<Vector3f> normals = new ArrayList<>();
            Vector3f polygonNormal = new Vector3f(0, 0, 0);

            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                Vector2f textureCoord = mesh.textureVertices.get(mesh.polygons.get(polygonInd).getTextureVertexIndices().get(vertexInPolygonInd));
                Vector3f normal = mesh.normals.get(mesh.polygons.get(polygonInd).getNormalIndices().get(vertexInPolygonInd));


                Vector3f transformedVertex = multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex);
                Vector2f screenPoint = vertexToPoint(transformedVertex, width, height);

                screenPoints.add(screenPoint);
                worldPoints.add(transformedVertex);
                textureCoords.add(textureCoord);
                normals.add(normal);

                // Accumulate the normal for the polygon
                polygonNormal = polygonNormal.add(normal);
            }

            float edgeABC = edgeFunction(screenPoints.get(2), screenPoints.get(1), screenPoints.get(0));
            if (edgeABC<0)
                continue;

            // Normalize the polygon normal
            polygonNormal = polygonNormal.normalize();

            // Get the camera direction
            Vector3f cameraDirection = camera.getTarget().subtract(camera.getPosition()).normalize();

            // Check if the polygon is facing the camera
//            if (polygonNormal.dot(cameraDirection) >= 0) {
//                rasterizer.rasterizePolygon(graphicsContext, screenPoints, worldPoints, textureCoords, normals, Color.BLUE);
//            rasterizer.rasterizePolygon(graphicsContext, screenPoints, Color.BLUE);
            rasterizer.rasterizePolygonv2(graphicsContext, screenPoints,worldPoints, Color.BLUE,textureCoords,normals);

            drawPolygonOutline(graphicsContext, screenPoints);
//            }
        }
    }

    public static float edgeFunction (Vector2f a, Vector2f b, Vector2f c){
        return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
    };

    private static void drawPolygonOutline(GraphicsContext graphicsContext, ArrayList<Vector2f> screenPoints) {
        int nVertices = screenPoints.size();
        for (int i = 0; i < nVertices; i++) {
            Vector2f start = screenPoints.get(i);
            Vector2f end = screenPoints.get((i + 1) % nVertices);
            graphicsContext.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
    }

    public static void initModel(Model mesh) {
        TriangulatedModel.triangulateModel(mesh);
        VertexNormalsCalculator.calculateNormals(mesh);
    }
}












