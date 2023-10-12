package edu.augustana;

import java.io.*;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PrimaryController{

    private String dataCsvPath = "DEMO1Pack/DEMO1.csv";
    private ArrayList<Card> allCards;

    @FXML
    private TextField searchedWord;

    @FXML
    Button searchButton;

    @FXML
    private TilePane allCardContent;
    ArrayList<Card> filteredCards = new ArrayList<>();


    private HashMap<String, HashMap> cardsDictionary;

    @FXML
    void searchButtonAction() {
        searchButton.setOnAction(event -> {

            String searchText = searchedWord.getText();
                 if(searchText.equals("")) {
                     dynamicCarAddingToView(allCards);
                 }else {
                     searchList(searchText);
                 }
        });
    }

    void buildCards() throws IOException {

        //map for the cards
        cardsDictionary = new HashMap<>();

        //Reads the csv file
        FileReader csvFile = new FileReader(dataCsvPath);
        BufferedReader reader = new BufferedReader(csvFile);
        allCards = new ArrayList<>();
        String line = reader.readLine();

        //creates new cards for all csv files data
        while (line != null) {
            String[] splittedLine = line.split(",");
            Card newCard = new Card(splittedLine);
            allCards.add(newCard);

            //map Created for searching
            HashMap<String, Card> genderMap = new HashMap<>();
            genderMap.put(newCard.getGender(), newCard);
            cardsDictionary.put(newCard.getEvent(), genderMap);


            line = reader.readLine();
        }

        printCardsDictionary(cardsDictionary);
        dynamicCarAddingToView(allCards);
    }

    public static void printCardsDictionary(HashMap<String, HashMap> cardsDictionary) {
        for (Map.Entry<String, HashMap> entry : cardsDictionary.entrySet()) {
            String event = entry.getKey();
            Map<String, Card> genderMap = entry.getValue();

            System.out.println("Event: " + event);

            for (Map.Entry<String, Card> genderEntry : genderMap.entrySet()) {
                String gender = genderEntry.getKey();
                Card card = genderEntry.getValue();

                System.out.println("  Gender: " + gender);
                System.out.println("  Card Details: " + card.toString());
            }
        }
    }



    private void searchList(String inputWord) {
        List<String> searchWordArray = Arrays.asList(inputWord.trim().split(" "));

        for (Card card : allCards) {
            for (String word : searchWordArray) {
                for(String data: card.getData()){
                    if(data.toLowerCase().equals(word.toLowerCase())){
                        filteredCards.add(card);
                        System.out.println("lopping search");
                        break;
                    }
                }
            }
        }
        System.out.println(filteredCards);
        allCardContent.getChildren().clear();
        dynamicCarAddingToView(filteredCards);
        filteredCards.clear();
    }
    private void dynamicCarAddingToView(ArrayList<Card> filteredCards) {
        allCardContent.setPrefColumns(3);
        for (Card card : filteredCards) {
            Button button = new Button();

            // Set the text and style for the button
            String imageLink = card.getImage();

            try {
                // Load the image and create an ImageView

                Image img = new Image(imageLink);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(250);
                imgView.setFitWidth(250);

                Text event = new Text(card.getEvent());
                event.setFont(Font.font(20));

                VBox cardContentBox = new VBox(imgView, event);
                button.setGraphic(cardContentBox);
                allCardContent.getChildren().add(button);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Set gaps for the TilePane
        allCardContent.setHgap(10);
        allCardContent.setVgap(20);
    }
}

