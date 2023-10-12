package edu.augustana;

import java.io.*;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private CheckBox FemaleCheckBox;

    @FXML
    private CheckBox MaleCheckBox;

    @FXML
    private TilePane allCardContent;
    private ArrayList<Card> searchFilteredCards = new ArrayList<>();

    private ArrayList<Card> checkBoxFilteredCards = new ArrayList<>();

    private String lastSearch = "";
    private HashMap<String, HashMap> cardsDictionary;

    @FXML
    void searchButtonAction() {
        searchButton.setOnAction(event -> {
            MaleCheckBox.setSelected(false);
            FemaleCheckBox.setSelected(false);


                    String searchText = searchedWord.getText().strip();

                    if (!searchText.equals(lastSearch) && !(searchText.equals("")) ){
                        lastSearch = searchText;
                        searchFilteredCards.clear();

                        allCardContent.getChildren().clear();

                        searchList(searchText);
                    }else{
                        if (searchText.equals("")) {
                            allCardContent.getChildren().clear();
                            dynamicCarAddingToView(allCards);

                        } else {
                            allCardContent.getChildren().clear();
                            dynamicCarAddingToView(searchFilteredCards);
                        }
                    }
        });
    }

    @FXML
    void showFemaleAction() {
        FemaleCheckBox.setOnAction(event -> {

            allCardContent.getChildren().clear();

            if (FemaleCheckBox.isSelected()) {
                // when female box is selected
                checkBoxFilteredCards.clear();

                if (searchFilteredCards.isEmpty()) {
                    //get cards from fresh set

                for (Card card : allCards) {
                    if (card.getGender().equals("N") || card.getGender().equals("F")) {
                        checkBoxFilteredCards.add(card);
                    }
                }

                //add the fresh set of cards to view
                dynamicCarAddingToView(checkBoxFilteredCards);
            } else {

                    //get cards from the search view
                    for (Card card : searchFilteredCards) {
                        if (card.getGender().equals("N") || card.getGender().equals("F")) {
                            checkBoxFilteredCards.add(card);
                        }
                    }

                    // add the cards

                    dynamicCarAddingToView(checkBoxFilteredCards);
                }
            } else{

                //if not selected un checked

                if(searchFilteredCards.isEmpty()){
                    dynamicCarAddingToView(allCards);
                }else{
                    dynamicCarAddingToView(searchFilteredCards);
                }
            }

        });
    }

    @FXML
    void showMaleAction() {
            MaleCheckBox.setOnAction(event -> {
                allCardContent.getChildren().clear();

                if (MaleCheckBox.isSelected()) {
                    // when female box is selected
                    checkBoxFilteredCards.clear();

                    if (searchFilteredCards.isEmpty()) {
                        //get cards from fresh set

                        for (Card card : allCards) {
                            if (card.getGender().equals("N") || card.getGender().equals("M")) {
                                checkBoxFilteredCards.add(card);
                            }
                        }

                        //add the fresh set of cards to view
                        dynamicCarAddingToView(checkBoxFilteredCards);
                    } else {

                        //get cards from the search view
                        for (Card card : searchFilteredCards) {
                            if (card.getGender().equals("N") || card.getGender().equals("M")) {
                                checkBoxFilteredCards.add(card);
                            }
                        }

                        // add the cards
                        dynamicCarAddingToView(checkBoxFilteredCards);
                    }
                } else{

                    //if not selected un checked
                    if(searchFilteredCards.isEmpty()){
                        dynamicCarAddingToView(allCards);
                    }else{
                        dynamicCarAddingToView(searchFilteredCards);
                    }
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


            for (Map.Entry<String, Card> genderEntry : genderMap.entrySet()) {
                String gender = genderEntry.getKey();
                Card card = genderEntry.getValue();

            }
        }
    }



    private void searchList(String inputWord) {
        List<String> searchWordArray = Arrays.asList(inputWord.trim().split(" "));



        for (Card card : allCards) {
            for (String word : searchWordArray) {
                for (String data : card.getData()) {
                    if (data.toLowerCase().equals(word.toLowerCase())) {
                        searchFilteredCards.add(card);
                        break;
                    }
                }
            }
        }


        allCardContent.getChildren().clear();
        dynamicCarAddingToView(searchFilteredCards);


    }

    private void dynamicCarAddingToView(ArrayList<Card> filteredCards) {
        allCardContent.setPrefColumns(5);
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

                Text event = new Text(card.getEvent() + " " + card.getGender());
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


        System.out.println("All filter: " + allCards.size());
        System.out.println("Search filter: " + searchFilteredCards.size());
        System.out.println("Check box filter: " + checkBoxFilteredCards.size());
    }
}


