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

    AddCard addCard = new AddCard(imageName, dataCsvPath);

    @FXML
    void Select_Image_Action(){
        Select_Image_Button.setOnAction(e -> {
            addCard =  new AddCard(imageName, dataCsvPath);
            addCard.selectImage();
        });
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
        String Data = data.getText();
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
        data.clear();
        imageName.clear();
    }



}
