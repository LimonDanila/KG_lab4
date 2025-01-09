package com.cgvsu;

import com.cgvsu.math.Vector2f;
import com.cgvsu.model.Light;
import com.cgvsu.model.Texture;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.GraphicConveyor;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.model.DeletePolygons;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private ListView<String> listViewModel;
    @FXML
    private ListView<String> listViewCamera;

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
    @FXML
    private TextField deletePolygonIndex;
    @FXML
    private CheckBox checkSaveModel;
    @FXML
    private CheckBox checkTriangulated;
    @FXML
    private CheckBox checkLighting;
    @FXML
    private CheckBox checkActiveTexture;

    Alert messageWarning = new Alert(Alert.AlertType.WARNING);
    Alert messageError = new Alert(Alert.AlertType.ERROR);
    Alert messageInformation = new Alert(Alert.AlertType.INFORMATION);

    private ArrayList<Model> meshList = new ArrayList<>();
    private ArrayList<Model> originMeshList = new ArrayList<>();
    private ArrayList<Vector3f[]> TRSList = new ArrayList<>();
    private List<Integer> indexPolygonsDelete = new ArrayList<>();

    private ArrayList<Texture> textures = new ArrayList<>();
    private Light light = new Light(new Vector3f(10,10,0), Color.WHITE);

    private final float RADIUS = 100;

    private Camera curCamera = new Camera(
            new Vector3f(0, 0, RADIUS),
            new Vector3f(0, 0, 0),
            new Vector3f(100, 0, 0),
            1.0F, 1, 0.01F, 100);
    private ArrayList<Camera> camerasList = new ArrayList<>();
    private int camerasCounter = 1;

    private Timeline timeline;

//    private Vector2f position;
//
//    private Vector3f positionC = new Vector3f(100, 0, 0);

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
    ObservableList<String> list = listViewCamera.getItems();
    list.add("Camera " + camerasCounter++);
    listViewCamera.setItems(list);
    camerasList.add(curCamera);

    timeline = new Timeline();
    timeline.setCycleCount(Animation.INDEFINITE);

    KeyFrame frame = new KeyFrame(Duration.millis(100), event -> {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
        curCamera.setAspectRatio((float) (width / height));

        if (!meshList.isEmpty()) {
            for (int i = 0; i < meshList.size(); i++) {
                if (meshList.get(i) != null) {
                    RenderEngine.initModel(meshList.get(i));
                    RenderEngine.render(canvas.getGraphicsContext2D(), curCamera, meshList.get(i), textures.get(i), light, (int) width, (int) height,
                            GraphicConveyor.translateRotateScale(TRSList.get(i)[0], TRSList.get(i)[1], TRSList.get(i)[2]), checkTriangulated.isSelected(), checkLighting.isSelected(), checkActiveTexture.isSelected());
                }
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
            originMeshList.add(ObjReader.read(fileContent));
            TRSList.add(new Vector3f[] {new Vector3f(-40 * listViewModel.getItems().size(), 0, 0),
                    new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)});
            textures.add(null);
            ObservableList<String> list = listViewModel.getItems();
            list.add(file.getName());
            listViewModel.setItems(list);
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
            int i = listViewModel.getFocusModel().getFocusedIndex();
            if (checkSaveModel.isSelected()) {
                ObjWriter.write(meshList.get(listViewModel.getFocusModel().getFocusedIndex()), file,
                        GraphicConveyor.translateRotateScale(TRSList.get(i)[0], TRSList.get(i)[1], TRSList.get(i)[2]));
                System.out.println("Yes");
            } else {
                ObjWriter.write(originMeshList.get(i), file);
                System.out.println("No");
            }
            showMessage("Информация", "Модель сохранена", messageInformation);
        } else {
            showMessage("Предупреждение", "Откройте модель", messageInformation);
        }
    }

//    @FXML
//    private void onOpenTextureMenuItemClick() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp"));
//        fileChooser.setTitle("Load Texture");
//
//        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
//        if (file == null) {
//            return;
//        }
//
//        int index = listViewModel.getFocusModel().getFocusedIndex();
//        if (index >= 0) {
//
//            texture = new Texture(file.getAbsolutePath());
//        }
//    }

    @FXML
    private void onOpenTextureMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp"));
        fileChooser.setTitle("Load Texture");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        int index = listViewModel.getFocusModel().getFocusedIndex();
        if (index >= 0) {
            Texture texture = new Texture(file.getAbsolutePath());
            textures.set(index, texture);
        }
    }


    @FXML
    public void acceptChanges(ActionEvent actionEvent) {
        int index = listViewModel.getFocusModel().getFocusedIndex();
        if (index >= 0) {
            TRSList.set(index, new Vector3f[] {new Vector3f(Float.parseFloat(translateX.getText()), Float.parseFloat(translateY.getText()), Float.parseFloat(translateZ.getText())),
                    new Vector3f(Float.parseFloat(rotatePhi.getText()), Float.parseFloat(rotateTetta.getText()), Float.parseFloat(rotateZetta.getText())),
                    new Vector3f(Float.parseFloat(scaleX.getText()), Float.parseFloat(scaleY.getText()), Float.parseFloat(scaleZ.getText()))});
        }
    }

    @FXML
    public void choiceModel(MouseEvent actionEvent) {
        int index = listViewModel.getFocusModel().getFocusedIndex();
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
        int index = listViewModel.getFocusModel().getFocusedIndex();
        if (index >= 0) {
            ObservableList<String> list = listViewModel.getItems();
            list.remove(index);
            listViewModel.setItems(list);
            meshList.remove(index);
            textures.remove(index);
            originMeshList.remove(index);
            TRSList.remove(index);
            for (int i = index; i < TRSList.size(); i++) {
                Vector3f[] vectors = TRSList.get(i);
                vectors[0] = vectors[0].add(new Vector3f(40, 0, 0));
                TRSList.set(i, vectors);
            }
        }
    }

    @FXML
    public void deletePolygons(ActionEvent actionEvent) {
        int index = listViewModel.getFocusModel().getFocusedIndex();
        if (index >= 0) {
            List<Integer> temp;
            Model currModel = meshList.get(listViewModel.getFocusModel().getFocusedIndex());
            if (deletePolygonIndex.getLength() != 0) {
                if (!currModel.polygons.isEmpty()) {
                    temp = extractIntegersFromString(deletePolygonIndex.getText());
                    indexPolygonsDelete.addAll(temp);
                    if (indexPolygonsDelete.get(indexPolygonsDelete.size() - 1) < currModel.polygons.size()) {
                        DeletePolygons.deletePolygons(currModel, true, indexPolygonsDelete.toArray(new Integer[0]));
                        indexPolygonsDelete.clear();
                    } else {
                        showMessage("Предупреждение", "Последний введенный индекс полигона больше, чем количество полигонов в модели!", messageWarning);
                    }
                } else {
                    showMessage("Предупреждение", "Все полигоны данной модели удалены!", messageInformation);
                }
            } else {
                showMessage("Предупреждение", "Введите индекс(-сы) полигона(-нов) для удаления", messageInformation);
            }
        } else {
            showMessage("Предупреждение", "Добавьте модель", messageInformation);
        }
    }

    @FXML
    public void handlerMouseClick(MouseEvent actionEvent) {
        if (actionEvent.isPrimaryButtonDown() || actionEvent.isSecondaryButtonDown()) {
            curCamera.setThisPosition(new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY()));
        }
    }

    @FXML
    public void handlerMouseMove(MouseEvent actionEvent) {
        if (actionEvent.isPrimaryButtonDown()) {
//            positionC = positionC.add(new Vector3f((float) 0, (float) (actionEvent.getX() - curCamera.getThisPosition().getX()), (float) (actionEvent.getY() - curCamera.getThisPosition().getY())));
            curCamera.addToPositionC(new Vector3f((float) 0, (float) (actionEvent.getX() - curCamera.getThisPosition().getX()), (float) (actionEvent.getY() - curCamera.getThisPosition().getY())));
            Vector3f newPos = getNewPos();
            curCamera.setPosition(newPos);
            curCamera.setThisPosition(new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY()));
        } else if (actionEvent.isSecondaryButtonDown()){
            float cos = getCos();
            curCamera.movePosition(new Vector3f((float) (actionEvent.getX() - curCamera.getThisPosition().getX()) * cos, (float) actionEvent.getY() - curCamera.getThisPosition().getY(), (float) ((curCamera.getThisPosition().getX() - actionEvent.getX()) * (1-cos))));
            curCamera.moveTarget(new Vector3f((float) (actionEvent.getX() - curCamera.getThisPosition().getX()) * cos, (float) actionEvent.getY() - curCamera.getThisPosition().getY(), (float) ((curCamera.getThisPosition().getX() - actionEvent.getX()) * (1-cos))));
            curCamera.setThisPosition(new Vector2f((float) actionEvent.getX(), (float) actionEvent.getY()));
        }
//        System.out.println(camera.getPosition().getX() + " " + camera.getPosition().getY() + " " + camera.getPosition().getZ());
    }

    @FXML
    public void handlerScroll(ScrollEvent actionEvent) {
        if (actionEvent.getDeltaY() < 0){
            curCamera.addToPositionC(new Vector3f(TRANSLATION, 0, 0));
        } else {
            curCamera.addToPositionC(new Vector3f(-TRANSLATION, 0, 0));
        }
        curCamera.setPosition(getNewPos());
    }

    private float getCos() {
        Vector3f vectorZ = new Vector3f(0, 0, 1);
        Vector3f targetVector = new Vector3f(curCamera.getPosition().getX() - curCamera.getTarget().getX(), curCamera.getPosition().getY() - curCamera.getTarget().getY(), curCamera.getPosition().getZ() - curCamera.getTarget().getZ());
        float cos = (float) ((vectorZ.getX() * targetVector.getX() + vectorZ.getY() * targetVector.getY() + vectorZ.getZ() * targetVector.getZ()) /
                (Math.sqrt(vectorZ.getX() * vectorZ.getX() + vectorZ.getY() * vectorZ.getY() + vectorZ.getZ() * vectorZ.getZ()) *
                        Math.sqrt(targetVector.getX() * targetVector.getX() + targetVector.getY() * targetVector.getY() + targetVector.getZ() * targetVector.getZ())));
        return cos;
    }

    private Vector3f getNewPos() {
        float x = (float) (curCamera.getPositionC().getX() * Math.sin((curCamera.getPositionC().getY() * Math.PI) / 180) * Math.cos((curCamera.getPositionC().getZ() * Math.PI) / 180));
        float y = (float) (curCamera.getPositionC().getX() * 1 * Math.sin((curCamera.getPositionC().getZ() * Math.PI) / 180));
        float z = (float) (curCamera.getPositionC().getX() * Math.cos((curCamera.getPositionC().getY() * Math.PI) / 180));
        return new Vector3f(x, y, z);
    }

    public static List<Integer> extractIntegersFromString(String input) {
        List<Integer> numbers = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            return numbers;
        }

        // Регулярное выражение для поиска одного или более последовательных символов цифр
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            try {
                int number = Integer.parseInt(matcher.group());
                numbers.add(number);
            } catch (NumberFormatException e) {
                // Если не смогли распарсить, просто пропускаем это число.
                System.err.println("Failed to parse integer: " + matcher.group() + ". Skipping it. " + e.getMessage());
            }

        }
        return numbers;
    }

    @FXML
    public void addCamera(ActionEvent actionEvent) {
        float x = (float) (RADIUS * Math.cos((Math.PI / 2) * (camerasList.size() + 1)));
        float y = 0;
        float z = (float) (RADIUS * Math.sin((Math.PI / 2) * (camerasList.size() + 1)));
        float phi = (float) ((Math.PI / 2) * -camerasList.size() * (180 / Math.PI));
        camerasList.add(new Camera(
                new Vector3f(x, y, z),
                new Vector3f(0, 0, 0),
                new Vector3f(RADIUS, phi, 0 ),
                1.0F, 1, 0.01F, 100));
        ObservableList<String> list = listViewCamera.getItems();
        list.add("Camera " + camerasCounter++);
        listViewCamera.setItems(list);
    }

    @FXML
    public void deleteCamera(ActionEvent actionEvent) {
        if (listViewCamera.getItems().size() != 1) {
            int index = listViewCamera.getFocusModel().getFocusedIndex();
            ObservableList<String> list = listViewCamera.getItems();
            list.remove(index);
            listViewCamera.setItems(list);
            camerasList.remove(index);
        }
    }

    @FXML
    public void choiceCamera(ActionEvent actionEvent) {
        curCamera = camerasList.get(listViewCamera.getFocusModel().getFocusedIndex());
    }
}