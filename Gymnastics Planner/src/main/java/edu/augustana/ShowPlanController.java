package edu.augustana;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ShowPlanController {

    @FXML
    private Label Title;

    @FXML
    private Button exitButton;

    @FXML
    private VBox cardBox;

    private String filePath;

    private String allPlansDir;

    private ArrayList<String> shortCodes;

    private ArrayList<Card> cardsList;

    private ArrayList<Card> allCards;

    private ArrayList<Card> segmentedCards;
    @FXML
    void initialize() {


    }

        public void buildPlans(String planName, String segmentType) throws IOException {
            shortCodes = new ArrayList<>();
            cardsList = new ArrayList<>();
            FileTool fileTool = new FileTool();
            System.out.println(segmentType);
            CardListDB dataBase = new CardListDB(false);
            allCards = dataBase.getAllCards();
            allPlansDir = fileTool.getPlansDirectory();
            Title.setText(planName);
            //write logic to read through the csv file and collect the short codes
            filePath = fileTool.getPlanFilePath(planName);
            try {
                Scanner reader = new Scanner(new File(filePath));
                System.out.println(filePath);
                String line = reader.nextLine();
                while (reader.hasNextLine()) {
                    line = reader.nextLine();
                    String[] data = line.split(",");
                    shortCodes.add(data[0]);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            //write the logic to gather the list of cards used in the plan
            for (String code : shortCodes) {
                cardsList.add(getCard(code));
            }
            //write the logic to segment the cards into the segment type
            HandleSearch searchTool = new HandleSearch(dataBase);
            if (segmentType == null || segmentType.equals("none")) {
                dynamicCarAddingToView(cardsList);
            } else {
                switch (segmentType) {

                    case "event":
                        while (cardsList.size() != 0) {
                            Label eventLabel = new Label();
                            eventLabel.setFont(new Font("Arial", 20));
                            eventLabel.setAlignment(Pos.TOP_CENTER);
                            eventLabel.setPrefWidth(500);
                            eventLabel.setPrefHeight(60);
                            eventLabel.setText(cardsList.get(0).getEvent());
                            cardBox.getChildren().add(eventLabel);
                            cardBox.setSpacing(60);
                            segmentedCards = new ArrayList<>();
                            String segment = cardsList.get(0).getEvent();
                            for (int i = cardsList.size() - 1; i >= 0; i--) {
                                if (cardsList.get(i).getEvent().equals(segment)) {
                                    segmentedCards.add(cardsList.get(i));
                                    cardsList.remove(i);
                                }
                            }
                            System.out.println(segmentedCards.size());
                            dynamicCarAddingToView(segmentedCards);
                        }
                        break;

                    case "category":
                        while (cardsList.size() != 0) {
                            Label eventLabel = new Label();
                            eventLabel.setText(cardsList.get(0).getCategory());
                            eventLabel.setFont(new Font("Ariel", 20));
                            eventLabel.setAlignment(Pos.TOP_CENTER);
                            eventLabel.setPrefWidth(500);
//                            cardPane.getChildren().add(eventLabel);
                            segmentedCards = new ArrayList<>();
                            String segment = cardsList.get(0).getCategory();
                            for (int i = cardsList.size() - 1; i >= 0; i--) {
                                if (cardsList.get(i).getCategory().equals(segment)) {
                                    segmentedCards.add(cardsList.get(i));
                                    cardsList.remove(i);
                                }
                            }
                        System.out.println(segmentedCards.size());
                        dynamicCarAddingToView(segmentedCards);
                    }
                    break;

                    case "gender":
                    while (cardsList.size() != 0) {
                        Label eventLabel = new Label();
                        eventLabel = new Label();
                        eventLabel.setText(cardsList.get(0).getGender());
                        eventLabel.setFont(new Font("Ariel", 20));
                        eventLabel.setAlignment(Pos.TOP_CENTER);
                        eventLabel.setPrefWidth(500);
//                        cardPane.getChildren().add(eventLabel);
                        segmentedCards = new ArrayList<>();
                        String segment = cardsList.get(0).getGender();
                        for (int i = cardsList.size() - 1; i >= 0; i--) {
                            if (cardsList.get(i).getGender().equals(segment)) {
                                segmentedCards.add(cardsList.get(i));
                                cardsList.remove(i);
                            }
                        }
                            System.out.println(segmentedCards.size());
                            dynamicCarAddingToView(segmentedCards);

                    }
                        break;

                    case "sex":
                        while (cardsList.size() != 0) {
                            Label eventLabel = new Label();
                            eventLabel = new Label();
                            eventLabel.setText(cardsList.get(0).getSex());
                            eventLabel.setFont(new Font("Ariel", 20));
                            eventLabel.setAlignment(Pos.TOP_CENTER);
                            eventLabel.setPrefWidth(500);
//                            cardPane.getChildren().add(eventLabel);
                            segmentedCards = new ArrayList<>();
                            String segment = cardsList.get(0).getSex();
                            for (int i = cardsList.size() - 1; i >= 0; i--) {
                                if (cardsList.get(i).getSex().equals(segment)) {
                                    segmentedCards.add(cardsList.get(i));
                                    cardsList.remove(i);
                                }
                            }
                            System.out.println(segmentedCards.size());
                            dynamicCarAddingToView(segmentedCards);
                        }
                        break;

                    case "level":
                        while (cardsList.size() != 0) {
                            Label eventLabel = new Label();
                            eventLabel = new Label();
                            eventLabel.setText(cardsList.get(0).getLevel());
                            eventLabel.setFont(new Font("Ariel", 20));
                            eventLabel.setAlignment(Pos.TOP_CENTER);
                            eventLabel.setPrefWidth(500);
//                            cardPane.getChildren().add(eventLabel);
                            segmentedCards = new ArrayList<>();
                            String segment = cardsList.get(0).getLevel();
                            for (int i = cardsList.size() - 1; i >= 0; i--) {
                                if (cardsList.get(i).getLevel().equals(segment)) {
                                    segmentedCards.add(cardsList.get(i));
                                    cardsList.remove(i);
                                }
                            }
                            System.out.println(segmentedCards.size());
                            dynamicCarAddingToView(segmentedCards);
                        }
                        break;
                }
                //write the logic to show the cards
            }
        }


    /**
     * gets the card from the short codes collected from the csv file
     * @param code - the code to be used to find a card
     * @return - the card that goes with the code passed in.
     */
    private Card getCard(String code){
        for (Card card: allCards){
            if (card.getCode().equals(code)){
                return card;
            }
        }
        return null;
        }

    private void addSegmentedCardsToView(){

    }
    private void dynamicCarAddingToView(ArrayList<Card> filteredCards) {
        TilePane cardPane = new TilePane();
        cardBox.getChildren().add(cardPane);
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
                cardPane.setPrefColumns(5);

                cardPane.getChildren().add(button);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Set gaps for the TilePane
        cardPane.setHgap(10);
        cardPane.setVgap(20);

    }

    @FXML
    void exitShowPlanView() throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    }

