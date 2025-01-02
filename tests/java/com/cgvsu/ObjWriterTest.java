package com.cgvsu;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.objwriter.ObjWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ObjWriterTest {

    @Test
    @DisplayName("Тест на запись вершин")
    public void testVertexToString() {
        Vector3f vector3f = new Vector3f(1.01f, 1.02f, 1.03f);
        final String result = ObjWriter.vertexToString(vector3f);
        final String expectedResult = "v 1.01 1.02 1.03";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест на запись текстур")
    public void testTextureVertexToString() {
        Vector2f vector2f = new Vector2f(1, 1);
        final String result = ObjWriter.textureVertexToString(vector2f);
        final String expectedResult = "vt 1.0 1.0";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест на запись нормалей")
    public void testNormalToString() {
        Vector3f vector3f = new Vector3f(-1, 1, -1);
        final String result = ObjWriter.normalToString(vector3f);
        final String expectedResult = "vn -1.0 1.0 -1.0";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест на запись полигонов с вершинами и текстурами")
    public void testWritePolygonVT() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2, 5)));
        polygon.setTextureVertexIndices(new ArrayList<>(Arrays.asList(3, 5, 4, 2)));
        final String result = ObjWriter.polygonToString(polygon);
        final String expectedResult = "f 1/4 2/6 3/5 6/3";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест на запись полигонов с вершинами и нормалями")
    public void testWritePolygonVN() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(1, 2, 3)));
        polygon.setNormalIndices(new ArrayList<>(Arrays.asList(1, 2, 3)));
        final String result = ObjWriter.polygonToString(polygon);
        final String expectedResult = "f 2//2 3//3 4//4";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест на запись полигонов с вершинами, текстурами и нормалями")
    public void testWritePolygonVTN() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2, 5)));
        polygon.setTextureVertexIndices(new ArrayList<>(Arrays.asList(6, 4, 3, 1)));
        polygon.setNormalIndices(new ArrayList<>(Arrays.asList(3, 5, 4, 2)));
        final String result = ObjWriter.polygonToString(polygon);
        final String expectedResult = "f 1/7/4 2/5/6 3/4/5 6/2/3";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест на запись полигонов с вершинами")
    public void testWritePolygonV() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(1, 2, 3)));
        final String result = ObjWriter.polygonToString(polygon);
        final String expectedResult = "f 2 3 4";
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Тест на запись координат в файл")
    public void testWrite() throws IOException {
        Model model = new Model();
        model.vertices = new ArrayList<>(List.of(
                new Vector3f(1.0f, 2.0f, 3.0f),
                new Vector3f(4.0f, 5.0f, 6.0f),
                new Vector3f(7.0f, 8.0f, 9.0f)
        ));
        model.textureVertices = new ArrayList<>(List.of(
                new Vector2f(0.1f, 0.2f),
                new Vector2f(0.3f, 0.4f),
                new Vector2f(0.5f, 0.6f)
        ));
        model.normals = new ArrayList<>(List.of(
                new Vector3f(-1.0f, -2.0f, -3.0f),
                new Vector3f(-4.0f, -5.0f, -6.0f),
                new Vector3f(-7.0f, -8.0f, -9.0f)
        ));
        model.polygons = new ArrayList<>(List.of(
                new Polygon() {{
                    setVertexIndices(new ArrayList<>(List.of(0, 1, 2)));
                    setTextureVertexIndices(new ArrayList<>(List.of(0, 1, 2)));
                    setNormalIndices(new ArrayList<>(List.of(0, 1, 2)));
                }}
        ));

        String filename = "test.obj";
        ObjWriter.write(model, filename);

        List<String> fileLines = Files.readAllLines(Paths.get(filename));
        List<String> expectedLines = List.of(
                "v 1.0 2.0 3.0",
                "v 4.0 5.0 6.0",
                "v 7.0 8.0 9.0",
                "vt 0.1 0.2",
                "vt 0.3 0.4",
                "vt 0.5 0.6",
                "vn -1.0 -2.0 -3.0",
                "vn -4.0 -5.0 -6.0",
                "vn -7.0 -8.0 -9.0",
                "f 1/1/1 2/2/2 3/3/3"
        );

        Assertions.assertEquals(expectedLines, fileLines);
        File file = new File(filename);
        file.delete();
    }
}