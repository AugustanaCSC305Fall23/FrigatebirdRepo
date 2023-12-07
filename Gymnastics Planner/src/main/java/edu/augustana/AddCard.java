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
    private String dataCsvPath;

    public AddCard(TextField imageName) {
        this.allCards = new CardListDB(true).getAllCards();
    }

    private File imageFile;
    private File thumbsFile;


    public String selectImage(String imageName) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("File Chooser Example");
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg)", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            if(imageName.equals("Images") && file.getName().endsWith(".png")){

                System.out.println("This is running images" + file.getName().endsWith(".png")+ " " + imageName);
                imageFile = file;
                return file.getName().toString();

            }else if (imageName.equals("thumbs") && file.getName().endsWith(".jpg")){
                System.out.println("This is running thumbs"+ file.getName().endsWith(".jpg") + " " + imageName);
                thumbsFile = file;
                return file.getName().toString();

            }else{
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setContentText("Wrong file type choosen. Choose 1 png file and one jpg file");
                alert.show();
                return null;
            }
        }else{
            return null;
        }

    }

    public void copyImage(String demoPack , File file , String folder ){

        // Copy the selected file to a specified location
        Path sourcePath = file.toPath();
        Path destinationPath = Paths.get("AllPacks/" , demoPack , "/" , folder, "/" , file.getName()); // Specify the destination directory

        try {
            Files.copy(sourcePath, destinationPath);
            System.out.println("File copied successfully to: " + destinationPath);

            // String newFileName = allCards.size() + 2 + ".png"; // Specify the new file name
            // Path newFilePath = Paths.get("AllPacks/" , demoPack , "/" , folder , "/", newFileName);
            // Files.move(destinationPath, newFilePath);
            // System.out.println("File renamed successfully to: " + newFilePath);


        } catch (Exception ex) {
            System.out.println("Error occurred while copying or renaming the file: " + ex.getMessage());
        }


    }

    public void addCard(String ID, String Event, String Category, String Name, String Equipment, String Level,
                        String Gender, String Sex, String Keyword, String packFolder) {


        if (!ID.isEmpty() && !Event.isEmpty() && !Category.isEmpty() && !Name.isEmpty() && !Equipment.isEmpty() && !Level.isEmpty() && !Gender.isEmpty() && !Sex.isEmpty() && !Keyword.isEmpty() && !packFolder.isEmpty()) {
            String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\"",
                    ID, Event, Category, Name, packFolder, imageFile.getName(), Gender, Sex, Level, Equipment, Keyword);


            copyImage(packFolder, imageFile, "Images");
            copyImage(packFolder, thumbsFile, "thumbs");

            String path1 = "AllPacks/" + packFolder + "/" +packFolder+".csv";
            writeFile(path1,csvLine);



        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Fill out all the fields!");
            alert.showAndWait();
        }
    }


    private void writeFile(String pathToWrite , String csvLine){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToWrite, true))) {
            writer.write("\n"+ csvLine + "\n");

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
