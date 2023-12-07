package edu.augustana;

import javafx.scene.control.CheckBox;

import java.io.*;
import java.util.*;

public class CardListDB {


    private  ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<CheckBox> allCheckBoxes;
    private String dataCsvPath = "AllPacks";
    private ArrayList<Card> allCardsExceptfavorites  = new ArrayList<>();
    private ArrayList<Card> favoriteCards = new ArrayList<>();
    String demoPackCsvFile = "";
    public CardListDB(Boolean forPlans){
        try {
            buildCardsObjectList(forPlans);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CardListDB(String filePath , Boolean forPlans){
        demoPackCsvFile = filePath;
        try {
            buildCardsObjectListForDirectPathsGiven(forPlans);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeFavorite(ArrayList<Card> cardList) throws  IOException {
        for (Card card : cardList) {
            Scanner reader = new Scanner(new File(new FileTool().getFavoriteCSVPath()));
            Boolean exists = false;
            if (reader.hasNextLine()) {
                reader.nextLine();
            }
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                String[] data = line.split(",");
                if(card.getCode().equals(data[0])){
                    exists = true;
                    break;
                }
            }
            if (!exists){
                card.makeFavorite(card);
                favoriteCards.add(card);
            }
            reader.close();
        }
    }


    private void buildCardsObjectList(Boolean forPlans) throws IOException {

        File dirAllPacks = new File(dataCsvPath);
        File[] demoPacks = dirAllPacks.listFiles();

        for (File singleDemoPack : demoPacks) {

            System.out.println(singleDemoPack.getName());

            //get csv from demopack
            for (File filesinDemoPack : singleDemoPack.listFiles()) {
                if (filesinDemoPack.getName().endsWith(".csv"))
                demoPackCsvFile = filesinDemoPack.getAbsolutePath();
            }

            buildCardsObjectListForDirectPathsGiven(forPlans);

        }
    }

    private void buildCardsObjectListForDirectPathsGiven(Boolean forPlans) throws IOException {

        //Reads the csv file
            FileReader csvFile = new FileReader(demoPackCsvFile);
            BufferedReader reader = new BufferedReader(csvFile);
            String line = null;
            line = reader.readLine();
            if (forPlans) {
                line = reader.readLine();
            }

            //creates new cards for all csv files data
            while (line != null) {
                String[] splittedLine = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (splittedLine.length >= 11) {
                    Card newCard = new Card(splittedLine);
                    allCards.add(newCard);
                    if (!newCard.getFavoriteStatus()) {
                        allCardsExceptfavorites.add(newCard);
                    }
                    if (newCard.getFavoriteStatus()) {
                        favoriteCards.add(newCard);
                    }
                }
                line = reader.readLine();

            }
        }


    public  ArrayList<Card> getAllCards(){
        return allCards;
    }

    public ArrayList<Card> getAllCardsExceptfavorites(){
        return allCardsExceptfavorites;
    }

    public ArrayList<Card> getFavoriteCards(){
        return favoriteCards;
    }


    public HashSet<String> getAllEquipmentName(){

        HashSet<String> allEquipmentNames = new HashSet<>();

        for(Card cards: allCards){
            String[] listOfEquipments = cards.getEquipment().split(",");
            allEquipmentNames.addAll(List.of(listOfEquipments));
        }

        return allEquipmentNames;

    }


}
