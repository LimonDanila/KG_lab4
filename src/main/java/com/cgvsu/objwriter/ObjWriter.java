package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ObjWriter{
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";
    private static final String FILE_NAME = "model.obj";

    public static void write(Model model, String filename) {
        File file = new File(filename);
        if (!createDir(file.getParentFile()))
            return;
        if (!createFile(file))
            return;
        try (PrintWriter writer = new PrintWriter(file)) {
            model.vertices.forEach(v -> writer.println(vertexToString(v)));
            model.textureVertices.forEach(v -> writer.println(textureVertexToString(v)));
            model.normals.forEach(v -> writer.println(normalToString(v)));
            model.polygons.forEach(v -> writer.println(polygonToString(v)));
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла");
        }
    }

    public static void write(Model model) {
        write(model, FILE_NAME);
    }

    private static boolean createDir(File directory) {
        if (directory != null && !directory.exists() && !directory.mkdirs()) {
            System.out.println("Не удалось создать директорию: " + directory);
            return false;
        }
        return true;
    }

    private static boolean createFile(File file) {
        try {
            if (!file.createNewFile())
                System.out.println("Предупреждение: " + file.getName() + " уже существует");
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            return false;
        }
        return true;
    }

    public static String vertexToString(Vector3f vector) {
        return OBJ_VERTEX_TOKEN + " " + vector.getX() + " " + vector.getY() + " " + vector.getZ();
    }

    public static String textureVertexToString(Vector2f vector) {
        return OBJ_TEXTURE_TOKEN + " " + vector.getX() + " " + vector.getY();
    }

    public static String normalToString(Vector3f vector) {
        return OBJ_NORMAL_TOKEN + " " + vector.getX() + " " + vector.getY() + " " + vector.getZ();
    }

    public static String polygonToString(Polygon polygon) {
        StringBuilder stringBuilder = new StringBuilder(OBJ_FACE_TOKEN);
        List<Integer> vertexIndices = polygon.getVertexIndices();
        List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
        List<Integer> normalIndices = polygon.getNormalIndices();
        boolean hasTextures = textureVertexIndices.size() == vertexIndices.size();
        boolean hasNormals = normalIndices.size() == vertexIndices.size();
        for (int i = 0; i < vertexIndices.size(); i++) {
            stringBuilder.append(" ")
                    .append(getFormattedIndex(vertexIndices, i));
            if (hasNormals) {
                stringBuilder.append("/");
                if (hasTextures) {
                    stringBuilder.append(getFormattedIndex(textureVertexIndices, i))
                            .append("/")
                            .append(getFormattedIndex(normalIndices, i));
                } else {
                    stringBuilder.append("/")
                            .append(getFormattedIndex(normalIndices, i));
                }
            } else {
                if (hasTextures) {
                    stringBuilder.append("/")
                            .append(getFormattedIndex(textureVertexIndices, i));
                }
            }
        }
        return stringBuilder.toString();
    }

    private static int getFormattedIndex(List<Integer> indices, int index) {
        return indices.get(index) + 1;
    }

}
