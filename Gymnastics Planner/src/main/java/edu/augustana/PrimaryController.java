package edu.augustana;

import java.io.*;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private TilePane selectedCardsView;

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
                allCardContent.setPrefColumns(5);

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



    /*

    This is for the work of the select card we need to move this to the createPlanController.java later somehow


    Anuthing bwlow this ----------------------------------



     */





    @FXML
    Button selectCards;
    HashMap<CheckBox,Card> allSelectedCards = new HashMap<>();

    @FXML
    TextField planTitle;



    @FXML
    public void selectCard() {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("selectCards.fxml"));
                Parent root = loader.load();

                // Create a new stage
                Stage selectCardsStage = new Stage();
                selectCardsStage.setTitle("Select Cards"); // Set the stage title

                // Set the scene
                Scene selectCardsScene = new Scene(root);
                selectCardsStage.setScene(selectCardsScene);

                // Set an event handler for the stage being shown
                selectCardsStage.setOnShown(e -> {
                    // This will be called after the stage is shown
                    SlectCardsController createPlanController = loader.getController();
                    try {
                        createPlanController.buildCards(this);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                // Show the stage
                selectCardsStage.show();

            } catch (IOException e) {
                throw new RuntimeException("Error loading selectCards.fxml", e);
            }
    }


    //This is the delete functionality for selected cards here

    @FXML
    private void deleteSelectedCards(){

        if ( allSelectedCards.size() == 0){

            //Prompt that the selected cards has nothing to delete
            prompt("No cards to delete!",false);
        }else{

            for(CheckBox cBox : allSelectedCards.keySet()){
                if(cBox.isSelected()){
                    allSelectedCards.remove(cBox);
                }
            }
            recieveArrayListCheckBox( allSelectedCards);

        }

    }


    //We reicieve the parameters from the selecrCards view here and display anything that is needed

    public void recieveArrayListCheckBox(HashMap<CheckBox,Card> selectedCards) {
        // Display all of the checkedBoxes
        // remove the dublicated from the all selectedCsrds

        for (Map.Entry<CheckBox, Card> availableCards : allSelectedCards.entrySet()) {
            Card availableCard = availableCards.getValue();

            Iterator<Map.Entry<CheckBox, Card>> iterator = selectedCards.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<CheckBox, Card> sentCardsMap = iterator.next();
                Card sentCard = sentCardsMap.getValue();
                CheckBox sentCheckBox = sentCardsMap.getKey();
                if (sentCard.getCode().equals(availableCard.getCode())) {
                    iterator.remove();  // Safe removal using iterator's remove()
                    break;
                }
            }
        }

        System.out.println("Available cards number: " + allSelectedCards.size());
        System.out.println("Sent cards number: " + selectedCards.size());


        allSelectedCards.putAll(selectedCards);

        selectedCardsView.getChildren().clear();
        selectedCardsView.setPrefColumns(4);

        for (CheckBox cBox : allSelectedCards.keySet()) {
            cBox.setSelected(false);
            selectedCardsView.getChildren().add(cBox);
        }

        System.out.println("These came from another page: " + selectedCards.size());
    }

@FXML
    public void createPlan() throws IOException {

    String fileName = planTitle.getText();


    if (fileName.isEmpty() || allSelectedCards.size() == 0) {

        //Prompt user to write the name of the plan
        System.out.println(prompt("Please write the plan name and select atleast one card" ,false));
    } else {
        //Create a CSV and then update it with the card Information here
        fileName = fileName + "_Plan.csv";
        //Plan name already exists overwrite or dont make any changes
        File allPlans = new File("AllPlans");
        File[] files = allPlans.listFiles();

        Boolean fileThere = false;
        for (File oneFile : files) {
            if (oneFile.getName().equals(fileName)) {
                fileThere = true;
                break;
            }
        }

        Boolean override = false;
        if(fileThere) {

            System.out.println("Plan already created do you want to ovverride?");
            override = prompt(" Plan already created do you want to replace the existing plan?", true);
        }

        System.out.println("File there or not: " + fileThere);

        if (!fileThere || override) {
            fileName = "AllPlans/Plan.csv" + fileName;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {


                writer.write(planTitle.getText()+"\n");

                for (Card card : allSelectedCards.values()) {

                    String csvLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                            card.getCode(), card.getEvent(), card.getCategory(), card.getTitle(),
                            card.getImage(), card.getGender(), card.getSex(), card.getLevel(),
                            card.getEquipment(), card.getKeywords());
                    // Write the CSV line to the file
                    writer.write("\n" + csvLine);

                }
                prompt("Sucessfully created the plan!" ,false);
                System.out.println("Sucessfully created the plan! ");
                writer.close();

                planTitle.clear();
                selectedCardsView.getChildren().clear();

            }
        }
    }

    }

//jsut for prompting different things based on the needs

    private boolean prompt(String value ,Boolean buttonsNeeded){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(value);

        AtomicBoolean choice = new AtomicBoolean(false);

        if(buttonsNeeded) {

            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            // Show the alert and handle the user's choice
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // User clicked "Yes", handle the positive action
                    choice.set(true);
                    System.out.println("User clicked Yes");
                } else if (response == ButtonType.NO) {
                    // User clicked "No", handle the negative action
                    System.out.println("User clicked No");

                }
            });

        }else{

            alert.getButtonTypes().setAll(ButtonType.OK);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                   alert.close();
                }
            });


        }

        return choice.get();
    }



}


