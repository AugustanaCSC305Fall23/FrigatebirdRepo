package edu.augustana;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlansDB {


    String fileName;

    HashMap<CheckBox,Card> allSelectedCards;

    public PlansDB() {

        allSelectedCards = new HashMap<>();
    }

    public void recieveCheckBox(HashMap<CheckBox,Card> selectedCards){


        for (Map.Entry<CheckBox, Card> availableCards : allSelectedCards.entrySet()) {
            Card availableCard = availableCards.getValue();

            Iterator<Map.Entry<CheckBox, Card>> iterator = selectedCards.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<CheckBox, Card> sentCardsMap = iterator.next();
                Card sentCard = sentCardsMap.getValue();
                if (sentCard.getCode().equals(availableCard.getCode())) {
                    iterator.remove();
                    break;
                }
            }
        }

        allSelectedCards.putAll(selectedCards);

    }

    public HashMap<CheckBox,Card> getAllCheckBox(){

        return allSelectedCards;
    }



    public Boolean createCsvandCheckFileExists(String fileName){

        //Create a CSV and then update it with the card Information here
        this.fileName = fileName + "_Plan.csv";
        //Plan name already exists overwrite or dont make any changes
        File allPlans = new File("AllPlans");
        File[] files = allPlans.listFiles();

        Boolean fileThere = false;
        for (File oneFile : files) {
            if (oneFile.getName().equals(this.fileName)) {
                fileThere = true;
                return fileThere;
            }
        }

        return fileThere;
    }

    public HashMap<CheckBox,Card>  deleteCheckBox(){

        for(CheckBox cBox : allSelectedCards.keySet()){
            if(cBox.isSelected()){
                allSelectedCards.remove(cBox);
            }
        }
        return getAllCheckBox();
    }


    public void overrideOrCreateNewPlan( String planTitle) throws IOException {

        String filePath = "AllPlans/"+fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(planTitle);

            for (Card card : allSelectedCards.values()) {

                String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        card.getCode(), card.getEvent(), card.getCategory(), card.getTitle(),
                        card.getImage(), card.getGender(), card.getSex(), card.getLevel(),
                        card.getEquipment(), card.getKeywords());
                // Write the CSV line to the file
                writer.write("\n" + csvLine);

            }
            allSelectedCards.clear();
            writer.close();
        }

    }





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
