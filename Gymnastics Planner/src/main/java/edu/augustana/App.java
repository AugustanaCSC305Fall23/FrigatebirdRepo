package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {


    private static Scene scene;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(Primary.class.getResource("primary.fxml"));
        Parent root = loader.load();
        Primary primary = loader.getController();
        primary.setTabControllers();
        scene = new Scene(root, 1500, 1500);
        stage.setScene(scene);
        stage.show();

    }



}
