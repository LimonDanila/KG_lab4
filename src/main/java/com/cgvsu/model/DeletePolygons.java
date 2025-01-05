package com.cgvsu.model;

import com.cgvsu.math.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DeletePolygons {

    public static void deletePolygons(Model model, boolean freeVertices, Integer[] numbers) {
        if (numbers.length > model.polygons.size()) {
            throw new RuntimeException("More polygons are being deleted than their number");
        }

        if (freeVertices) {
            deleteWith(numbers, model);
        } else {
            deleteWithout(numbers, model.polygons);
        }
    }

    private static void deleteWithout(Integer[] numbers, ArrayList<Polygon> polygons) {
        Arrays.sort(numbers, Collections.reverseOrder());
        for (int i: numbers) {
            polygons.remove(i);
        }
    }

    private static void deleteWith(Integer[] numbers, Model model) {
        deleteWithout(numbers, model.polygons);
        Integer[] freeVertices = findFreeVertices(model.polygons, model.vertices.size());
        Arrays.sort(freeVertices, Collections.reverseOrder());
        deleteFreeVertices(model.vertices, freeVertices);
        reindexing(model.polygons, freeVertices);
    }

    private static Integer[] findFreeVertices(ArrayList<Polygon> polygons, int countOfVertices) {
        Integer[] points = new Integer[countOfVertices];
        for (Polygon polygon: polygons) {
            for (Integer i: polygon.getVertexIndices()) {
                points[i] = 1;
            }
        }
        ArrayList<Integer> freeVerticesIndexes = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                freeVerticesIndexes.add(i);
            }
        }
        return freeVerticesIndexes.toArray(new Integer[0]);
    }

    private static void deleteFreeVertices(ArrayList<Vector3f> vertices, Integer[] freeVerticesIndexes) {
        for (int i: freeVerticesIndexes) {
            vertices.remove(i);
        }
    }

    private static void reindexing(ArrayList<Polygon> polygons, Integer[] freeVerticesIndexes) {
        for (Polygon polygon: polygons) {
            ArrayList<Integer> VertexIndices = polygon.getVertexIndices();
            for (int i = 0; i < VertexIndices.size(); i++) {
                for (Integer j: freeVerticesIndexes) {
                    if (VertexIndices.get(i) > j) {
                        VertexIndices.set(i, VertexIndices.get(i) - 1);
                    }
                }
            }
        }
    }
}
