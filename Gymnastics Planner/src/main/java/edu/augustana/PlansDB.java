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
    HashMap<CheckBox,Card> tempAllSelectedCards;



    public PlansDB() {

        allSelectedCards = new HashMap<>();
        tempAllSelectedCards = new HashMap<>();
    }

    public void addToDict(CheckBox cBox,Card card){
        tempAllSelectedCards.put(cBox, card);
    }


    public void filterSelectedTempCards(){

        Iterator<CheckBox> iterator = tempAllSelectedCards.keySet().iterator();
        while (iterator.hasNext()) {
            CheckBox cBox = iterator.next();
            if (!cBox.isSelected()) {
                iterator.remove(); // Remove the current CheckBox from the map
            }
        }
    }

    public HashMap<CheckBox,Card> getFilterSelectedTempCards(){

        this.filterSelectedTempCards();
        return tempAllSelectedCards;
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

    public HashMap<CheckBox,Card> deleteCheckBox(){


        Iterator<CheckBox> iterator = allSelectedCards.keySet().iterator();
        while (iterator.hasNext()) {
            CheckBox cBox = iterator.next();
            if (cBox.isSelected()) {
                iterator.remove(); // Remove the current CheckBox from the map
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


}
