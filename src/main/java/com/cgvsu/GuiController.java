package com.cgvsu;

import com.cgvsu.math.Vector2f;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.GraphicConveyor;
import com.cgvsu.render_engine.RenderEngine;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;

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

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField translateX;
    @FXML
    private TextField translateY;
    @FXML
    private TextField translateZ;
    @FXML
    private TextField rotatePhi;
    @FXML
    private TextField rotateTetta;
    @FXML
    private TextField rotateZetta;
    @FXML
    private TextField scaleX;
    @FXML
    private TextField scaleY;
    @FXML
    private TextField scaleZ;

    Alert messageWarning = new Alert(Alert.AlertType.WARNING);
    Alert messageError = new Alert(Alert.AlertType.ERROR);
    Alert messageInformation = new Alert(Alert.AlertType.INFORMATION);

    private ArrayList<Model> meshList = new ArrayList<>();
    private ArrayList<Vector3f[]> TRSList = new ArrayList<>();

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

    private void showMessage(String headText, String messageText, Alert alert) {
        alert.setHeaderText(headText);
        alert.setContentText(messageText);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(canvas.getWidth()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(canvas.getHeight()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (!meshList.isEmpty()) {
                for (int i = 0; i < meshList.size(); i++) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), camera, meshList.get(i), (int) width, (int) height,
                            GraphicConveyor.translateRotateScale(TRSList.get(i)[0], TRSList.get(i)[1], TRSList.get(i)[2]));
                }
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
            meshList.add(ObjReader.read(fileContent));
            TRSList.add(new Vector3f[] {new Vector3f(-40 * listView.getItems().size(), 0, 0),
            new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)});
            ObservableList<String> list = listView.getItems();
            list.add(file.getName());
            listView.setItems(list);
            // todo: обработка ошибок
        } catch (IOException exception) {
            showMessage("Ошибка", "Не удалось найти файл", messageError);
        }
    }

    @FXML
    private void onSaveModelMenuItemClick() {
        if (!meshList.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
            fileChooser.setTitle("Сохранить модель");

            File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
            if (file == null) {
                return;
            }
            ObjWriter.write(meshList.get(listView.getFocusModel().getFocusedIndex()), file);
            showMessage("Информация", "Модель сохранена", messageError);
        } else {
            showMessage("Предупреждение", "Откройте модель", messageError);
        }
    }

    @FXML
    public void acceptChanges(ActionEvent actionEvent) {
        int index = listView.getFocusModel().getFocusedIndex();
        if (index >= 0) {
            TRSList.set(index, new Vector3f[] {new Vector3f(Float.parseFloat(translateX.getText()), Float.parseFloat(translateY.getText()), Float.parseFloat(translateZ.getText())),
                    new Vector3f(Float.parseFloat(rotatePhi.getText()), Float.parseFloat(rotateTetta.getText()), Float.parseFloat(rotateZetta.getText())),
                    new Vector3f(Float.parseFloat(scaleX.getText()), Float.parseFloat(scaleY.getText()), Float.parseFloat(scaleZ.getText()))});
        }
    }

    @FXML
    public void choiceModel(MouseEvent actionEvent) {
        int index = listView.getFocusModel().getFocusedIndex();
        if (index >= 0) {
            translateX.setText(Float.toString(TRSList.get(index)[0].getX()));
            translateY.setText(Float.toString(TRSList.get(index)[0].getY()));
            translateZ.setText(Float.toString(TRSList.get(index)[0].getZ()));
            rotatePhi.setText(Float.toString(TRSList.get(index)[1].getX()));
            rotateTetta.setText(Float.toString(TRSList.get(index)[1].getY()));
            rotateZetta.setText(Float.toString(TRSList.get(index)[1].getZ()));
            scaleX.setText(Float.toString(TRSList.get(index)[2].getX()));
            scaleY.setText(Float.toString(TRSList.get(index)[2].getY()));
            scaleZ.setText(Float.toString(TRSList.get(index)[2].getZ()));
        }
    }

    @FXML
    public void deleteModel(ActionEvent actionEvent) {
        int index = listView.getFocusModel().getFocusedIndex();
        if (index >= 0) {
            ObservableList<String> list = listView.getItems();
            list.remove(index);
            listView.setItems(list);
            meshList.remove(index);
            TRSList.remove(index);
        }
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
            positionC = positionC.add(new Vector3f((float) 0, (float) (actionEvent.getX() - position.getX()), (float) (actionEvent.getY() - position.getY())));
            float x = (float) (positionC.getX() * Math.sin((positionC.getY() * Math.PI) / 180) * Math.cos((positionC.getZ() * Math.PI) / 180));
            float y = (float) (positionC.getX() * 1 * Math.sin((positionC.getZ() * Math.PI) / 180));
            float z = (float) (positionC.getX() * Math.cos((positionC.getY() * Math.PI) / 180));
            Vector3f newPos = new Vector3f(x, y, z);
            camera.setPosition(newPos);
            position = new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY());
        } else if (actionEvent.isSecondaryButtonDown()){
            camera.movePosition(new Vector3f((float) actionEvent.getX() - position.getX(), (float) actionEvent.getY() - position.getY(), 0));
            camera.moveTarget(new Vector3f((float) actionEvent.getX() - position.getX(), (float) actionEvent.getY() - position.getY(), 0));
            position = new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY());
        }
//        System.out.println(camera.getPosition().getX() + " " + camera.getPosition().getY() + " " + camera.getPosition().getZ());
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