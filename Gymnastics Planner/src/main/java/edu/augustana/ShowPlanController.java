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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    private String planNote;


@FXML
private Label planNoteSection;

    @FXML
    void initialize() {


    }

    @FXML
    void  printPlan(){

        Print print = new Print();
        print.printAnchorPane(cardBox);

    }
    public void buildPlans(String planName, String segmentType , Boolean diffLocation , String path,String text) throws IOException {

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
            Scanner reader;
            if (diffLocation) {
                reader = new Scanner(new File(path));
            } else {
                reader = new Scanner(new File(filePath));
            }
            System.out.println(filePath);
            String line = reader.nextLine();
            planNote = line;

            System.out.println("Plan note is this: " + planNote);
            planNoteSection.setText(planNote);

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
            dynamicTextAddingToView(cardsList, planName);
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
                        dynamicTextAddingToView(segmentedCards, planName);
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
                        dynamicTextAddingToView(segmentedCards, planName);
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
                        dynamicTextAddingToView(segmentedCards, planName);

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
                        dynamicTextAddingToView(segmentedCards, planName);
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
                        dynamicTextAddingToView(segmentedCards, planName);
                    }
                    break;
            }
        }
    }

    public void buildPlans(String planName, String segmentType , Boolean diffLocation , String path) throws IOException {

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
            Scanner reader;
            if(diffLocation){
                reader = new Scanner(new File(path));
            }else {
                reader = new Scanner(new File(filePath));
            }
            System.out.println(filePath);
            String line = reader.nextLine();
            String planNote = line;

            System.out.println("Plan note is this: " + planNote);
            planNoteSection.setText(planNote);

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
                        cardBox.setSpacing(30);
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

    private void dynamicTextAddingToView(ArrayList<Card> filteredCards, String planName) {
        if (cardBox == null) {
            System.out.println("Error: cardBox is null. Make sure it is initialized.");
            return;
        }

        if (filteredCards == null || filteredCards.isEmpty()) {
            System.out.println("Error: filteredCards is null or empty.");
            return;
        }

        // Clear existing content in cardBox
        cardBox.getChildren().clear();

        // Extract the part of planName after the last backslash
        int lastBackslashIndex = planName.lastIndexOf("\\");
        String titleAfterBackslash = lastBackslashIndex >= 0 ? planName.substring(lastBackslashIndex + 1) : planName;

        // Add titleAfterBackslash as the title of the document
        Label planTitleLabel = new Label(titleAfterBackslash);
        planTitleLabel.setFont(Font.font(28)); // Set the font size for the plan title
        planTitleLabel.setStyle("-fx-font-weight: bold;"); // Make the title bold
        cardBox.getChildren().add(planTitleLabel);

        Label planNoteLabel = new Label("Plan Note: " + planNote);
        planNoteLabel.setFont(Font.font(16)); // Set the font size for the plan title
        planNoteLabel.setStyle("-fx-font-weight: Normal;"); // Make the title bold
        cardBox.getChildren().add(planNoteLabel);

        // Group cards by event
        Map<String, List<Card>> cardsByEvent = filteredCards.stream()
                .collect(Collectors.groupingBy(Card::getEvent));

        // Set numbering counter
        int eventNumber = 1;

        for (Map.Entry<String, List<Card>> entry : cardsByEvent.entrySet()) {
            String eventTitle = entry.getKey();
            List<Card> cardsForEvent = entry.getValue();

            // Add event title to the VBox
            Label titleLabel = new Label(String.format("%d. Event: %s", eventNumber, eventTitle));
            titleLabel.setFont(Font.font(24)); // Set the font size for event title
            titleLabel.setStyle("-fx-font-weight: bold;"); // Make the title bold
            cardBox.getChildren().add(titleLabel);

            // Set numbering counter for cards within the event
            int cardNumber = 1;

            for (Card card : cardsForEvent) {
                // Construct a string containing card information
                String cardInfo = String.format("    %d.%d Event: %s, Gender: %s, Category: %s, Sex: %s, Level: %s",
                        eventNumber, cardNumber,
                        card.getEvent(),
                        card.getGender(),
                        card.getCategory(),
                        card.getSex(),
                        card.getLevel());

                // Create a Text node with the card information
                Text cardText = new Text(cardInfo);
                cardText.setFont(Font.font(20));  // Set the font size for card information

                // Add spacing between each Text node
                cardText.setStyle("-fx-margin: 10;"); // You can adjust the spacing as needed

                // Add the Text node to the VBox
                cardBox.getChildren().add(cardText);

                // Increment card number within the event
                cardNumber++;
            }

            // Increment event number
            eventNumber++;
        }
    }



    private void dynamicCarAddingToView(ArrayList<Card> filteredCards) {
        TilePane cardPane = new TilePane();
        cardBox.getChildren().add(cardPane);
        for (Card card : filteredCards) {
            Button button = new Button();

            // Set the text and style for the button
            String imageLink = card.getPrintingImage();
            try {
                // Load the image and create an ImageView

                Image img = new Image(imageLink);
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(300);
                imgView.setFitWidth(300);

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

