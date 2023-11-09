package edu.augustana;

import javafx.scene.control.CheckBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CardListDB {


    private  ArrayList<Card> allCards;
    private ArrayList<CheckBox> allCheckBoxes;
    private String dataCsvPath = "DEMO1Pack/DEMO1.csv";
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


    public void makeFavorite(HashMap<CheckBox, Card> cardMap) throws  IOException{
        ArrayList<Card> selectedCards = new ArrayList<>();
        System.out.println(cardMap.size());
        for(CheckBox checkBox: cardMap.keySet()){
            selectedCards.add(cardMap.get(checkBox));
            System.out.println(cardMap.get(checkBox).getCode());;
        }
        for (Card card: selectedCards){
            card.makeFavorite(card);
        }

    }

    private void buildCardsObjectList(Boolean forPlans) throws IOException {

        //Reads the csv file
        FileReader csvFile = new FileReader(dataCsvPath);
        BufferedReader reader = new BufferedReader(csvFile);
        allCards = new ArrayList<>();
        String line = null;
        line = reader.readLine();
        if (forPlans){
            line = reader.readLine();
        }


        //creates new cards for all csv files data
        while (line != null) {
            String[] splittedLine = line.split(",");

            System.out.println(line);
            if(splittedLine.length >= 11) {
                Card newCard = new Card(splittedLine);
                allCards.add(newCard);
            }
            line = reader.readLine();

        }
    }

    public  ArrayList<Card> getAllCards(){
        return allCards;
    }



}
