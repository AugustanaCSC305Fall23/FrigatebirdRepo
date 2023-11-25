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

public class AddPlanHandler {


    String fileName;

    HashMap<CheckBox,Card> allSelectedCards;

    HashMap<CheckBox,Card> tempAllSelectedCards;

    ArrayList<Card> deletedCards;

    ArrayList<Card> allNoneSSelectedCards;
    public AddPlanHandler() {

        allSelectedCards = new HashMap<>();
        tempAllSelectedCards = new HashMap<>();
        deletedCards = new ArrayList<>();
        allNoneSSelectedCards = new ArrayList<>();

    }

    public void addCardsToPlans(CheckBox cBox, Card card){
        tempAllSelectedCards.put(cBox, card);
    }

    public void addCardsToAllSelectedCards(CheckBox cBox, Card card){
        allSelectedCards.put(cBox, card);
    }


    public ArrayList<Card> getAllSelectedCards(){
        ArrayList<Card> selectedCards = new ArrayList<>();
        for (CheckBox cBox: allSelectedCards.keySet()){
            if(cBox.isSelected()){
                selectedCards.add(allSelectedCards.get(cBox));
                tempAllSelectedCards.put(cBox,allSelectedCards.get(cBox));
            }
        }
        return selectedCards;
    }


    //So in the selected cards view I created a new set of cards that the user can select and remove

    public void removeFromSelectedCards(CheckBox cBox) {
        // Assuming allSelectedCards is an instance variable

        System.out.println("Before removing: " + allSelectedCards.size());
        if (allSelectedCards != null) {
            allSelectedCards.remove(cBox);
        }
        System.out.println("After removing: " + allSelectedCards.size());
    }



    public HashMap<CheckBox,Card> removeDublicates(HashMap<CheckBox,Card> selectedCards){

        System.out.println("Remove dublicates from where - " + allSelectedCards.size());
        System.out.println("Currently the cards that are in the view- " + getAllCheckBox().size());


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

        System.out.println("Remove post from where - " + selectedCards.size());
        System.out.println("Post  the cards that are in the view- " + allSelectedCards.size());
        System.out.println("");
        return selectedCards;

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

        deletedCards.clear();
        Iterator<CheckBox> iterator = allSelectedCards.keySet().iterator();
        while (iterator.hasNext()) {
            CheckBox cBox = iterator.next();
            if (cBox.isSelected()) {
                deletedCards.add(allSelectedCards.get(cBox));
                iterator.remove();
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

    public void createFileDifferentLocation(String planTitle , Boolean isPlanTitle , String locationPath) throws IOException {
        String filePath;
        if(isPlanTitle) {
            filePath = locationPath+ "/" + fileName;
        }else{
            filePath = locationPath+ "/" + planTitle;
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
            try (BufferedWriter planPathWriter = new BufferedWriter(new FileWriter("OutsidePlanLocation/AllPlansPathOutSideApp.csv"))){
                planPathWriter.write(filePath);
            }


        }

    }

    public int getCardsInViewSize(){
        return allSelectedCards.size();
    }




    public HashMap<CheckBox,Card> getAllSelectedCardsToAdd(){

        HashMap<CheckBox,Card> allCheckBoxCard = new HashMap<>();

        for(CheckBox checkBox : tempAllSelectedCards.keySet()){
            if(checkBox.isSelected()){

                allCheckBoxCard.put(checkBox,tempAllSelectedCards.get(checkBox));
                checkBox.setSelected(false);
            }
        }

        return allCheckBoxCard;
    }


}
