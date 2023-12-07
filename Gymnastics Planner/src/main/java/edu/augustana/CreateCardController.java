package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class CreateCardController {
    private String dataCsvPath = "AllPacks/";

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
    private TextField thumbsName;

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
    private TextField packFolder;
    @FXML
    Button done_Button;

    @FXML
    Button addCardButton;

    @FXML
    Button selectThumbsButton;

    AddCard addCard = new AddCard(imageName);





    @FXML
    private void selectThumbsButtonAction(){
        String name =  addCard.selectImage("thumbs");
        thumbsName.setText(name);

    }





    @FXML
    void Select_Image_Action(){
        String name =  addCard.selectImage("Images");
        imageName.setText(name);

    }



    @FXML
    void doneOnAction(){
        String ID = codeInput.getText();
        String Event = eventInput.getText();
        String Category = categoryInput.getText();
        String Name = titleInput.getText();
        String Equipment = equipmentInput.getText();
        String Level = levelInput.getText();
        String Gender = Male.isSelected() ? "M" : Female.isSelected() ? "F" : Neutral.isSelected() ? "N" : "";
        String Sex = sexMale.isSelected() ? "M" : sexFemale.isSelected() ? "F" : "";
        String Keyword = keyword.getText();
        String Data = packFolder.getText();



        addCard.addCard(ID, Event, Category, Name, Equipment, Level, Gender, Sex, Keyword, Data);
        clear();
    }
    void clear(){
        codeInput.clear();
        eventInput.clear();
        categoryInput.clear();
        titleInput.clear();
        equipmentInput.clear();
        levelInput.clear();
        Male.setSelected(false);
        Female.setSelected(false);
        Neutral.setSelected(false);
        sexMale.setSelected(false);
        sexFemale.setSelected(false);
        keyword.clear();
        packFolder.clear();
        imageName.clear();
        thumbsName.clear();

    }


    @FXML
    private void addCardButtonAction() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a Directory");

        // Show the directory chooser dialog
        Stage stage = (Stage) addCardButton.getScene().getWindow(); // Replace yourButton with the actual reference to your button
        File selectedDirectory = chooser.showDialog(stage);

        boolean imagesDirFound = false;
        boolean thumbsDirFound = false;
        boolean csvFileFound = false;

        File[] allFiles = selectedDirectory.listFiles();

        int count = 0;

        // Iterate through allFiles
        for (File file : allFiles) {
            if (count < 3) {
                if (file.isDirectory() && file.getName().equals("Images")) {
                    imagesDirFound = true;
                } else if (file.isDirectory() && file.getName().equals("thumbs")) {
                    thumbsDirFound = true;
                } else if (file.isFile() && file.getName().endsWith(".csv")) {
                    csvFileFound = true;
                }
            } else {
                break;
            }
        }

        String destinationPath = "AllPacks/"+selectedDirectory.getName();
        String fileFromWhere = selectedDirectory.getAbsolutePath();

        if (imagesDirFound && thumbsDirFound && csvFileFound && count == 2) {
            Path sourcePath = Paths.get(fileFromWhere);
            Path targetPath = Paths.get(destinationPath);

            try {
                copyDirectory(sourcePath, targetPath);
                System.out.println("Directory copied successfully!");
            } catch (IOException e) {
                System.err.println("Error copying directory: " + e.getMessage());
            }

        }else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Directory type not matched.");
            alert.show();
        }
    }

    private static void copyDirectory(Path source, Path destination) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = destination.resolve(source.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targetFile = destination.resolve(source.relativize(file));
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                // Handle file visit failure if necessary
                System.err.println("Failed to copy file: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                // Handle directory visit completion if necessary
                return FileVisitResult.CONTINUE;
            }
        });
    }



}