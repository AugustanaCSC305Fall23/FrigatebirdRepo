package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class CreatePlanController {


    @FXML
    TextField planTitle;
    @FXML
    private TilePane selectedCardsView;

    private PlansDB plansDB;


    public CreatePlanController(){

        plansDB = new PlansDB();

    }

    @FXML
    public void selectCard() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("selectCards.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage selectCardsStage = new Stage();
            selectCardsStage.setTitle("Select Cards"); // Set the stage title

            // Set the scene
            Scene selectCardsScene = new Scene(root);
            selectCardsStage.setScene(selectCardsScene);

            // Set an event handler for the stage being shown
            selectCardsStage.setOnShown(e -> {
                // This will be called after the stage is shown
                SlectCardsController createPlanController = loader.getController();
                try {
                    createPlanController.buildCards(this);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Show the stage
            selectCardsStage.show();

        } catch (IOException e) {
            throw new RuntimeException("Error loading selectCards.fxml", e);
        }
    }


    //This is the delete functionality for selected cards here

    @FXML
    private void deleteSelectedCards(){

        if (plansDB.getAllCheckBox().size() == 0){

            //Prompt that the selected cards has nothing to delete
            prompt("No cards to delete!",false);
        }else{

            recieveArrayListCheckBox(plansDB.deleteCheckBox());

        }

    }


    //We reicieve the parameters from the selecrCards view here and display anything that is needed

    public void recieveArrayListCheckBox(HashMap<CheckBox,Card> selectedCards) {
        // Display all of the checkedBoxes
        // remove the dublicated from the all selectedCsrds

        plansDB.recieveCheckBox(selectedCards);

        selectedCardsView.getChildren().clear();
        selectedCardsView.setPrefColumns(4);

        for (CheckBox cBox : plansDB.getAllCheckBox().keySet()) {
            cBox.setSelected(false);
            selectedCardsView.getChildren().add(cBox);
        }

    }

    @FXML
    public void createPlan() throws IOException {

        String planTitleName = planTitle.getText().strip();

        if (planTitleName.isEmpty() || plansDB.getAllCheckBox().size() == 0) {
            //Prompt user to write the name of the plan
            System.out.println(prompt("Please write the plan name and select atleast one card" ,false));
        } else {
            // see if file exits and if user wants to override
            Boolean fileThere = plansDB.createCsvandCheckFileExists(planTitleName);
            System.out.println("Is file there? " + fileThere);
            Boolean override = false;
            if (fileThere) {
                System.out.println("Plan already created do you want to ovverride?");
                override = prompt(" Plan already created do you want to replace the existing plan?", true);
            }

            //if file is not there or override then write file
            if (!fileThere || override) {
                plansDB.overrideOrCreateNewPlan(planTitleName);
                prompt("Sucessfully created the plan!", false);
                planTitle.clear();
                selectedCardsView.getChildren().clear();
            }


        }
    }

//jsut for prompting different things based on the needs


    private boolean prompt(String value ,Boolean buttonsNeeded){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(value);

        AtomicBoolean choice = new AtomicBoolean(false);

        if(buttonsNeeded) {

            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            // Show the alert and handle the user's choice
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // User clicked "Yes", handle the positive action
                    choice.set(true);
                    System.out.println("User clicked Yes");
                } else if (response == ButtonType.NO) {
                    // User clicked "No", handle the negative action
                    System.out.println("User clicked No");

                }
            });

        }else{

            alert.getButtonTypes().setAll(ButtonType.OK);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    alert.close();
                }
            });

        }

        return choice.get();
    }

}
