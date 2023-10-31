package edu.augustana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CardListDB {


    private  ArrayList<Card> allCards;
    private String dataCsvPath = "DEMO1Pack/DEMO1.csv";
    public CardListDB(){


        try {
            buildCardsObjectList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildCardsObjectList() throws IOException {

        //Reads the csv file
        FileReader csvFile = new FileReader(dataCsvPath);
        BufferedReader reader = new BufferedReader(csvFile);
        allCards = new ArrayList<>();
        String line = null;
        line = reader.readLine();

        //creates new cards for all csv files data
        while (line != null) {
            String[] splittedLine = line.split(",");
            Card newCard = new Card(splittedLine);
            allCards.add(newCard);
            line = reader.readLine();
        }
    }

    public  ArrayList<Card> getAllCards(){
        return allCards;
    }



}
