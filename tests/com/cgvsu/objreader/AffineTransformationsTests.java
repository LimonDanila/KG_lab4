package com.cgvsu.objreader;

import com.cgvsu.math.*;
import com.cgvsu.model.Model;
import com.cgvsu.render_engine.GraphicConveyor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class AffineTransformationsTests {

    @Test
    public void testWithoutTransformations() {
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 1)
        ));
        Matrix4d result = GraphicConveyor.translateRotateScale(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1)
        );
        final ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 1))
        );
        for (int i = 0; i < expectedResult.size(); i++) {
            Vector4f vector4f = new Vector4f(model.vertices.get(i).getX(), model.vertices.get(i).getY(), model.vertices.get(i).getZ(), 1);
            vector4f = result.multiply(vector4f);
            model.vertices.set(i, new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ()));
            if (!model.vertices.get(i).equals(expectedResult.get(i))) {
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedResult.size(), model.vertices.size());
    }

    @Test
    public void testTranslateOneAxis() {
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 1)
        ));
        Matrix4d result = GraphicConveyor.translateRotateScale(
                new Vector3f(0, 0, 10),
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1)
        );
        final ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 10),
                new Vector3f(1, 0, 10),
                new Vector3f(1, 1, 11))
        );
        for (int i = 0; i < expectedResult.size(); i++) {
            Vector4f vector4f = new Vector4f(model.vertices.get(i).getX(), model.vertices.get(i).getY(), model.vertices.get(i).getZ(), 1);
            vector4f = result.multiply(vector4f);
            model.vertices.set(i, new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ()));
            if (!model.vertices.get(i).equals(expectedResult.get(i))) {
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedResult.size(), model.vertices.size());
    }

    @Test
    public void testTranslateTwoAxis() {
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 1)
        ));
        Matrix4d result = GraphicConveyor.translateRotateScale(
                new Vector3f(11, 0, 10),
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1)
        );
        final ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(
                new Vector3f(11, 0, 10),
                new Vector3f(12, 0, 10),
                new Vector3f(12, 1, 11))
        );
        for (int i = 0; i < expectedResult.size(); i++) {
            Vector4f vector4f = new Vector4f(model.vertices.get(i).getX(), model.vertices.get(i).getY(), model.vertices.get(i).getZ(), 1);
            vector4f = result.multiply(vector4f);
            model.vertices.set(i, new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ()));
            if (!model.vertices.get(i).equals(expectedResult.get(i))) {
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedResult.size(), model.vertices.size());
    }

    @Test
    public void testTranslateThreeAxis() {
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 1)
        ));
        Matrix4d result = GraphicConveyor.translateRotateScale(
                new Vector3f(11, 20, 10),
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1)
        );
        final ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(
                new Vector3f(11, 20, 10),
                new Vector3f(12, 20, 10),
                new Vector3f(12, 21, 11))
        );
        for (int i = 0; i < expectedResult.size(); i++) {
            Vector4f vector4f = new Vector4f(model.vertices.get(i).getX(), model.vertices.get(i).getY(), model.vertices.get(i).getZ(), 1);
            vector4f = result.multiply(vector4f);
            model.vertices.set(i, new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ()));
            if (!model.vertices.get(i).equals(expectedResult.get(i))) {
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedResult.size(), model.vertices.size());
    }

    @Test
    public void testScale() {
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 1)
        ));
        Matrix4d result = GraphicConveyor.translateRotateScale(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 0),
                new Vector3f(5, 1, 2)
        );
        final ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(5, 0, 0),
                new Vector3f(5, 1, 2))
        );
        for (int i = 0; i < expectedResult.size(); i++) {
            Vector4f vector4f = new Vector4f(model.vertices.get(i).getX(), model.vertices.get(i).getY(), model.vertices.get(i).getZ(), 1);
            vector4f = result.multiply(vector4f);
            model.vertices.set(i, new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ()));
            if (!model.vertices.get(i).equals(expectedResult.get(i))) {
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedResult.size(), model.vertices.size());
    }

    @Test
    public void testRotateAroundOneAxis() {
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 1)
        ));
        Matrix4d result = GraphicConveyor.translateRotateScale(
                new Vector3f(0, 0, 0),
                new Vector3f(90, 0, 0),
                new Vector3f(1, 1, 1)
        );
        final ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, -1))
        );
        for (int i = 0; i < expectedResult.size(); i++) {
            Vector4f vector4f = new Vector4f(model.vertices.get(i).getX(), model.vertices.get(i).getY(), model.vertices.get(i).getZ(), 1);
            vector4f = result.multiply(vector4f);
            model.vertices.set(i, new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ()));
            if (!model.vertices.get(i).equals(expectedResult.get(i))) {
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedResult.size(), model.vertices.size());
    }

    @Test
    public void testRotateAroundTwoAxis() {
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 2, 3)
        ));
        Matrix4d result = GraphicConveyor.translateRotateScale(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 90, 90),
                new Vector3f(1, 1, 1)
        );
        final ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(0, -1, 0),
                new Vector3f(3, -1, -2))
        );
        for (int i = 0; i < expectedResult.size(); i++) {
            Vector4f vector4f = new Vector4f(model.vertices.get(i).getX(), model.vertices.get(i).getY(), model.vertices.get(i).getZ(), 1);
            vector4f = result.multiply(vector4f);
            model.vertices.set(i, new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ()));
            if (!model.vertices.get(i).equals(expectedResult.get(i))) {
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedResult.size(), model.vertices.size());
    }

    @Test
    public void testWithFullAffineTransformation() {
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 2, 3)
        ));
        Matrix4d result = GraphicConveyor.translateRotateScale(
                new Vector3f(9, 10, 0),
                new Vector3f(0, 90, 0),
                new Vector3f(4, 1, 1)
        );
        final ArrayList<Vector3f> expectedResult = new ArrayList<>(Arrays.asList(
                new Vector3f(9, 10, 0),
                new Vector3f(9, 10, -4),
                new Vector3f(12, 12, -4))
        );
        for (int i = 0; i < expectedResult.size(); i++) {
            Vector4f vector4f = new Vector4f(model.vertices.get(i).getX(), model.vertices.get(i).getY(), model.vertices.get(i).getZ(), 1);
            vector4f = result.multiply(vector4f);
            model.vertices.set(i, new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ()));
            if (!model.vertices.get(i).equals(expectedResult.get(i))) {
                Assertions.fail();
            }
        }
        Assertions.assertEquals(expectedResult.size(), model.vertices.size());
    }
}
