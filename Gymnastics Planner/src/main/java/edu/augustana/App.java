package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {


    private static Scene scene;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = loader.load();

        // Assuming Primary is the controller class for your FXML
        Primary primary = loader.getController();
        primary.setTabControllers();

        Screen screen = Screen.getPrimary();
        // getting the width of the screen being used
//        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(root, 1298, 868);
        stage.setScene(scene);

        // Optional: Icon addition and title addition
        stage.setTitle("TumbleTech");
        primary.setLogo(stage);



        stage.show();
    }



}
