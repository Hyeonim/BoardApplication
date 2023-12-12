package kr.or.yi.board.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtil {

    private static SceneUtil instance;

    Stage stage;
    Scene scene;
    FXMLLoader loader;
    Parent root;

    private SceneUtil() {

    }

    public static SceneUtil getInstance() {
        if (instance == null) {
            instance = new SceneUtil();
        }
        return instance;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void switchScene(MouseEvent event, String fxml) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchScene(MouseEvent event, String fxml, Parent root) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchScene(ActionEvent event, String fxml) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchScene(ActionEvent event, String fxml, Parent root) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public Object getController(String fxml) throws IOException {
        loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();
        return loader.getController();
    }

    public void close(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("exit?");
        alert.setHeaderText("exit?");
//        alert.setContentText("exit");

//        System.out.println("프로그램 종료");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }

    }

    public void close(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("exit?");
        alert.setHeaderText("exit?");
//        alert.setContentText("exit");

//        System.out.println("프로그램 종료");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }

    }

    public void close(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("exit?");
        alert.setHeaderText("exit?");
//        alert.setContentText("exit");
//        System.out.println("프로그램 종료");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }

    }

}
