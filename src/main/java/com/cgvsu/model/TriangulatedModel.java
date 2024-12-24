package com.cgvsu.model;

import com.cgvsu.math.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;

public class TriangulatedModel extends Model {

    public TriangulatedModel() {

    }

    public static void triangulateModel(Model model) {
        ArrayList<Polygon> poligons = new ArrayList<>();
        poligons.addAll(model.polygons);
        ArrayList<Polygon> triangulatedPoligon = new ArrayList<>();

        for (Polygon polygon : poligons) {
            ArrayList<Integer> vertex = polygon.getVertexIndices();
            ArrayList<Integer> normals = polygon.getNormalIndices();
            ArrayList<Integer> textureVertex = polygon.getTextureVertexIndices();

            for (int i = 2; i < polygon.getVertexIndices().size(); i++) {
                Polygon triangle = new Polygon();
                triangle.setVertexIndices(new ArrayList<>(Arrays.asList(vertex.get(0), vertex.get(i - 1), vertex.get(i))));

                if (!normals.isEmpty()){
                    triangle.setNormalIndices(new ArrayList<>(Arrays.asList(normals.get(0), normals.get(i - 1), normals.get(i))));
                }

                if (!textureVertex.isEmpty()){
                    triangle.setTextureVertexIndices(new ArrayList<>(Arrays.asList(textureVertex.get(0), textureVertex.get(i - 1), textureVertex.get(i))));
                }

                triangulatedPoligon.add(triangle);
            }
            model.polygons = triangulatedPoligon;
        }
    }

    public static void calculateNormals(Model model) {
        for (Polygon polygon : model.polygons) {
            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
            Vector3f v0 = model.vertices.get(vertexIndices.get(0));
            Vector3f v1 = model.vertices.get(vertexIndices.get(1));
            Vector3f v2 = model.vertices.get(vertexIndices.get(2));

            Vector3f edge1 = v1.subtract(v0);
            Vector3f edge2 = v2.subtract(v0);
            Vector3f normal = edge1.cross(edge2).normalize();

            for (int i = 0; i < vertexIndices.size(); i++) {
                int index = vertexIndices.get(i);
                if (index >= model.normals.size()) {
                    model.normals.add(normal);
                } else {
                    model.normals.set(index, model.normals.get(index).add(normal).normalize());
                }
            }
        }
    }
}

