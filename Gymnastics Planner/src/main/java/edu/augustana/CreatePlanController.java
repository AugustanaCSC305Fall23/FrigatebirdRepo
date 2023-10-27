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
    private HashMap<CheckBox,Card> allSelectedCards = new HashMap<>();
    @FXML
    TextField planTitle;
    @FXML
    private TilePane selectedCardsView;

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

        if ( allSelectedCards.size() == 0){

            //Prompt that the selected cards has nothing to delete
            prompt("No cards to delete!",false);
        }else{

            for(CheckBox cBox : allSelectedCards.keySet()){
                if(cBox.isSelected()){
                    allSelectedCards.remove(cBox);
                }
            }
            recieveArrayListCheckBox( allSelectedCards);

        }

    }


    //We reicieve the parameters from the selecrCards view here and display anything that is needed

    public void recieveArrayListCheckBox(HashMap<CheckBox,Card> selectedCards) {
        // Display all of the checkedBoxes
        // remove the dublicated from the all selectedCsrds

        for (Map.Entry<CheckBox, Card> availableCards : allSelectedCards.entrySet()) {
            Card availableCard = availableCards.getValue();

            Iterator<Map.Entry<CheckBox, Card>> iterator = selectedCards.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<CheckBox, Card> sentCardsMap = iterator.next();
                Card sentCard = sentCardsMap.getValue();
                CheckBox sentCheckBox = sentCardsMap.getKey();
                if (sentCard.getCode().equals(availableCard.getCode())) {
                    iterator.remove();  // Safe removal using iterator's remove()
                    break;
                }
            }
        }

        System.out.println("Available cards number: " + allSelectedCards.size());
        System.out.println("Sent cards number: " + selectedCards.size());


        allSelectedCards.putAll(selectedCards);

        selectedCardsView.getChildren().clear();
        selectedCardsView.setPrefColumns(4);

        for (CheckBox cBox : allSelectedCards.keySet()) {
            cBox.setSelected(false);
            selectedCardsView.getChildren().add(cBox);
        }

        System.out.println("These came from another page: " + selectedCards.size());
    }

    @FXML
    public void createPlan() throws IOException {

        String fileName = planTitle.getText();


        if (fileName.isEmpty() || allSelectedCards.size() == 0) {

            //Prompt user to write the name of the plan
            System.out.println(prompt("Please write the plan name and select atleast one card" ,false));
        } else {
            //Create a CSV and then update it with the card Information here
            fileName = fileName + "_Plan.csv";
            //Plan name already exists overwrite or dont make any changes
            File allPlans = new File("AllPlans");
            File[] files = allPlans.listFiles();

            Boolean fileThere = false;
            for (File oneFile : files) {
                if (oneFile.getName().equals(fileName)) {
                    fileThere = true;
                    break;
                }
            }

            Boolean override = false;
            if(fileThere) {

                System.out.println("Plan already created do you want to ovverride?");
                override = prompt(" Plan already created do you want to replace the existing plan?", true);
            }

            System.out.println("File there or not: " + fileThere);

            if (!fileThere || override) {
                fileName = "AllPlans/Plan.csv" + fileName;

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {


                    writer.write(planTitle.getText()+"\n");

                    for (Card card : allSelectedCards.values()) {

                        String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                                card.getCode(), card.getEvent(), card.getCategory(), card.getTitle(),
                                card.getImage(), card.getGender(), card.getSex(), card.getLevel(),
                                card.getEquipment(), card.getKeywords());
                        // Write the CSV line to the file
                        writer.write("\n" + csvLine);

                    }
                    prompt("Sucessfully created the plan!" ,false);
                    System.out.println("Sucessfully created the plan! ");
                    writer.close();

                    planTitle.clear();
                    selectedCardsView.getChildren().clear();

                }
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
