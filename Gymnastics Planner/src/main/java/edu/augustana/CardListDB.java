package edu.augustana;

import javafx.scene.control.CheckBox;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CardListDB {


    private  ArrayList<Card> allCards;
    private ArrayList<CheckBox> allCheckBoxes;
    private String dataCsvPath = "AllPacks";
    private ArrayList<Card> allCardsExceptfavorites;
    private ArrayList<Card> favoriteCards;
    public CardListDB(Boolean forPlans){
        try {
            buildCardsObjectList(forPlans);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CardListDB(String filePath , Boolean forPlans){
        dataCsvPath = filePath;
        try {
            buildCardsObjectList(forPlans);
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

        //Reads the csv file

        File dirAllPacks = new File(dataCsvPath);
        File[] demoPacks = dirAllPacks.listFiles();

        for(File singleDemoPack : demoPacks) {

            //get csv from demopack
            String demoPackCsvFile = "";

            for(File filesinDemoPack : singleDemoPack.listFiles()){
                if(filesinDemoPack.getName().endsWith(".csv"));
                demoPackCsvFile = filesinDemoPack.getAbsolutePath();
            }

            FileReader csvFile = new FileReader(demoPackCsvFile);
            BufferedReader reader = new BufferedReader(csvFile);
            allCards = new ArrayList<>();
            favoriteCards = new ArrayList<>();
            allCardsExceptfavorites = new ArrayList<>();
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
                        System.out.println("NOOO");
                        favoriteCards.add(newCard);
                    }
                }
                line = reader.readLine();

            }
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



}
