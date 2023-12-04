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
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = loader.load();

        // Assuming Primary is the controller class for your FXML
        Primary primary = loader.getController();
        primary.setTabControllers();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(root, 1298, bounds.getHeight()-50);
        stage.setScene(scene);
        stage.show();

        // Optional: Icon addition and title addition
        stage.setTitle("TumbleTech");
        //primary.setLogo(stage);

    }


}
