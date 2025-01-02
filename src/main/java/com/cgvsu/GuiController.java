package com.cgvsu;

import com.cgvsu.math.Vector2f;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import com.cgvsu.math.Vector3f;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 2F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private float radius = 100;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    private Vector2f position;

    private Vector3f positionC = new Vector3f(radius, 0, 0);

    private float phiX = 0;
    private float phiZ = 0;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }

    @FXML
    public void handlerMouseClick(MouseEvent actionEvent) {
        if (actionEvent.isPrimaryButtonDown() || actionEvent.isSecondaryButtonDown()) {
            position = new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY());
        }
    }

    @FXML
    public void handlerMouseMove(MouseEvent actionEvent) {
        if (actionEvent.isPrimaryButtonDown()) {
//            float x;
//            float y;
//            float z;
            positionC = positionC.add(new Vector3f((float) 0, (float) (actionEvent.getX() - position.getX()), (float) (actionEvent.getY() - position.getY())));
//            positionC = positionC.add(new Vector3f(0, 0, 1));
//            if ((positionC.getY() * Math.PI) / 180 < 1e-6f || 180 - (positionC.getY() * Math.PI) / 180 < 1e-6f) {
//                x = (float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.cos((positionC.getZ() * Math.PI) / 180));
//                y = (float) (positionC.getX() * 1 * Math.sin((positionC.getZ() * Math.PI) / 180));
//                z = (float) (positionC.getX() * Math.cos((positionC.getY() * Math.PI) / 180));
//            } else {
//                x = (float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.cos((positionC.getZ() * Math.PI) / 180));
//                y = (float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.sin((positionC.getZ() * Math.PI) / 180));
//                z = (float) (positionC.getX() * Math.cos((positionC.getY() * Math.PI) / 180));
//            }
//            Vector3f newPos = new Vector3f((float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.cos((positionC.getZ() * Math.PI) / 180)),
//                    (float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.sin((positionC.getZ() * Math.PI) / 180)),
//                    (float) (positionC.getX() * Math.cos((positionC.getY() * Math.PI) / 180)));
            float x = (float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.cos((positionC.getZ() * Math.PI) / 180));
            float y = (float) (positionC.getX() * 1 * Math.sin((positionC.getZ() * Math.PI) / 180));
            float z = (float) (positionC.getX() * Math.cos((positionC.getY() * Math.PI) / 180));
            Vector3f newPos = new Vector3f(x, y, z);
            camera.setPosition(newPos);
            position = new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY());
            position = new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY());
        } else if (actionEvent.isSecondaryButtonDown()){
            camera.movePosition(new Vector3f((float) actionEvent.getX() - position.getX(), (float) actionEvent.getY() - position.getY(), 0));
            camera.moveTarget(new Vector3f((float) actionEvent.getX() - position.getX(), (float) actionEvent.getY() - position.getY(), 0));
            position = new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY());
        }
        System.out.println(camera.getPosition().getX() + " " + camera.getPosition().getY() + " " + camera.getPosition().getZ());
    }

    @FXML
    public void handlerScroll(ScrollEvent actionEvent) {
        if (actionEvent.getDeltaY() < 0){
            positionC = positionC.add(new Vector3f(TRANSLATION, 0, 0));
        } else {
            positionC = positionC.add(new Vector3f(-TRANSLATION, 0, 0));
        }
        Vector3f newPos = new Vector3f((float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.cos((positionC.getZ() * Math.PI) / 180)),
                (float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.sin((positionC.getZ() * Math.PI) / 180)),
                (float) (positionC.getX() * Math.cos((positionC.getY() * Math.PI) / 180)));
        camera.setPosition(newPos);
    }
}