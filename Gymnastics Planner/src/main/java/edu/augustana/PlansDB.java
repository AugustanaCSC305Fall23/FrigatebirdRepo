package edu.augustana;

import javafx.scene.control.CheckBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlansDB {

    String fileName;
    HashMap<CheckBox,Card> allSelectedCards;
    HashMap<CheckBox,Card> tempAllSelectedCards;



    public PlansDB() {

        allSelectedCards = new HashMap<>();
        tempAllSelectedCards = new HashMap<>();
    }

    public void addCardsToPlans(CheckBox cBox, Card card){
        tempAllSelectedCards.put(cBox, card);
    }

    public void addCardsToAllSelectedCards(CheckBox cBox, Card card){
        allSelectedCards.put(cBox, card);
    }


    //So in the selected cards view I created a new set of cards that the user can select and remove
    public void filterSelectedTempCards(){

        Iterator<CheckBox> iterator = tempAllSelectedCards.keySet().iterator();
        while (iterator.hasNext()) {
            CheckBox cBox = iterator.next();
            if (!cBox.isSelected()) {
                iterator.remove(); // Remove the current CheckBox from the map
            }
        }
    }

    public ArrayList<Card> getAllSelectedCards(){
        ArrayList<Card> selectedCards = new ArrayList<>();
        for (CheckBox cBox: allSelectedCards.keySet()){
            if(cBox.isSelected()){
                selectedCards.add(allSelectedCards.get(cBox));
                cBox.setSelected(false);
            }
        }
        return selectedCards;
    }

    public HashMap<CheckBox,Card> getFilterSelectedTempCards(){
        this.filterSelectedTempCards();
        return tempAllSelectedCards;
    }



    public void removeDublicates(HashMap<CheckBox,Card> selectedCards){

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


    public void overrideOrCreateNewPlan(String planTitle , Boolean isPlanTitle) throws IOException {
        String filePath;
        if(isPlanTitle) {
             filePath = "AllPlans/" + fileName;
        }else{
             filePath = "AllPlans/" + planTitle;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(planTitle);

            System.out.println("All Selected Cards in PlansDB: " + allSelectedCards.size());

            for (Card card : allSelectedCards.values()) {

                String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        card.getCode(), card.getEvent(), card.getCategory(), card.getTitle(),card.getPackFolder(),
                        card.getImageName(), card.getGender(), card.getSex(), card.getLevel(),
                        card.getEquipment(), card.getKeywords());
                System.out.println(csvLine);
                // Write the CSV line to the file
                writer.write("\n" + csvLine);
            }

            allSelectedCards.clear();
            writer.close();
        }

    }


    public int getCardsInViewSize(){
        return allSelectedCards.size();
    }


    //segement and return dictionary of code for events




}
