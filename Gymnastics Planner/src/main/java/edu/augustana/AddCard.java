package edu.augustana;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddCard {
    private ArrayList<Card> allCards;
    private TextField imageName;
    private String dataCsvPath;

    public AddCard(TextField imageName,String dataCsvPath) {
        this.imageName = imageName;
        this.allCards = new CardListDB(true).getAllCards();
        this.dataCsvPath = dataCsvPath;
    }

    public void selectImage() {
        Stage primaryStage = new Stage();
            primaryStage.setTitle("File Chooser Example");
            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show open file dialog
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                imageName.setText(file.getName());
                // Copy the selected file to a specified location
                Path sourcePath = file.toPath();
                Path destinationPath = Paths.get("C:\\git\\FrigatebirdRepo\\DEMO1Pack\\Images", file.getName()); // Specify the destination directory
                try {
                    Files.copy(sourcePath, destinationPath);
                    System.out.println("File copied successfully to: " + destinationPath);

                    String newFileName = allCards.size() + 1 + ".png"; // Specify the new file name
                    Path newFilePath = Paths.get("C:\\git\\FrigatebirdRepo\\DEMO1Pack\\Images", newFileName);
                    Files.move(destinationPath, newFilePath);
                    System.out.println("File renamed successfully to: " + newFilePath);


                } catch (Exception ex) {
                    System.out.println("Error occurred while copying or renaming the file: " + ex.getMessage());
                }
            }
    }

    public void addCard(String ID, String Event, String Category, String Name, String Equipment, String Level,
                        String Gender, String Sex, String Keyword, String Data) {
        String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\"",
                ID, Event, Category, Name, "Demo1", (allCards.size() + 1) + ".png", Gender, Sex, Level, Keyword, Equipment, Data);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataCsvPath, true))) {
            writer.write(csvLine + "\n");

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Card Added!");
            alert.showAndWait();


        } catch (IOException ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Unable to Add Card.");
            alert.showAndWait();
        }
    }
}
