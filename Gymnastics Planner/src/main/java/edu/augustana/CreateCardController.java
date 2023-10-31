package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CreateCardController {
    private String dataCsvPath = "DEMO1Pack/DEMO1.csv";

    @FXML
    private TextField codeInput;

    @FXML
    private TextField eventInput;

    @FXML
    private TextField categoryInput;
    @FXML
    private TextField titleInput;
    @FXML
    private TextField equipmentInput;

    @FXML
    private TextField imageName;

    @FXML
    private TextField levelInput;

    @FXML
    Button Select_Image_Button;
    @FXML
    private CheckBox Male;
    @FXML
    private CheckBox Female;
    @FXML
    private CheckBox Neutral;
    @FXML
    private CheckBox sexMale;
    @FXML
    private CheckBox sexFemale;
    @FXML
    private TextField keyword;
    @FXML
    private TextField data;
    @FXML
    Button done_Button;

    ArrayList<Card> allCards;


    @FXML
    void Select_Image_Action(){
        Select_Image_Button.setOnAction(e -> {
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
        });
    }
    @FXML
    void doneOnAction(){
        String ID = codeInput.getText();
        String Name = titleInput.getText();
        String Event = eventInput.getText();
        String Category = categoryInput.getText();
        String Equipment = equipmentInput.getText();
        String Level = levelInput.getText();
        String Gender = "";
        String Sex ="";
        String Keyword = keyword.getText();
        String Data = data.getText();
        if(Male.isSelected()){
            Gender="M";
        }
        else if(Female.isSelected()){
            Gender="F";
        }
        else if(Neutral.isSelected()){
            Gender ="N";
        }

        System.out.println(Gender);

        if(sexMale.isSelected()){
            Sex = "M";
        }
        else if (sexFemale.isSelected()){
            Sex = "F";
        }
        System.out.println(Sex);
        String finalGender = Gender;
        String finalSex = Sex;
        done_Button.setOnAction(e -> {
            String csvFile = dataCsvPath; // Specify the path to the CSV file
            String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\"",
                    ID, Event, Category, Name, "Demo1", (allCards.size() + 1) + ".png", finalGender, finalSex, Level, Keyword, Equipment, Data);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))) {
                writer.write(csvLine+"\n");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Card Added!");
                alert.showAndWait();

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Failed");
                alert.setHeaderText(null);
                alert.setContentText("Unable to Add Card.");
                alert.showAndWait();

            }


        });
    }



}
