package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CreatePlanController {


    @FXML
    TextField planTitle;
    @FXML
    private TilePane selectedCardsView;
    private AddPlanHandler plansDB;

    @FXML
    Button selectTheseCards;

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

    @FXML
    private ComboBox selectCourse;

    private String lastSearch = "";
    HashMap<CheckBox, Card> selectedCardsToCheckBox;
    private String dataCsvPath = "DEMO1Pack/DEMO1.csv";
    private CardListDB cardListDB;
    private  HandleSearch handleSearch;

    private String selectedCourse;


    public CreatePlanController(){
        plansDB = new AddPlanHandler();
    }

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
              //  try {
                    //createPlanController.buildCards(plansDB,this);
             //   } catch (IOException ex) {
                 //   throw new RuntimeException(ex);
             //   }
            });

            // Show the stage
            selectCardsStage.show();

        } catch (IOException e) {
            throw new RuntimeException("Error loading selectCards.fxml", e);
        }
    }




    @FXML
    public void createPlan() throws IOException {

        String planTitleName = planTitle.getText().strip();

        if (planTitleName.isEmpty() || plansDB.getAllCheckBox().size() == 0) {
            //Prompt user to write the name of the plan
            System.out.println(prompt("Please write the plan name and select atleast one card" ,false));
        } else {
            // see if file exits and if user wants to override
            Boolean fileThere = plansDB.createCsvandCheckFileExists(planTitleName , selectedCourse);
            System.out.println("Is file there? " + fileThere);
            Boolean override = false;
            if (fileThere) {
                System.out.println("Plan already created do you want to ovverride?");
                override = prompt(" Plan already created do you want to replace the existing plan?", true);
            }

            //if file is not there or override then write file
            if (!fileThere || override) {
                plansDB.overrideOrCreateNewPlan(planTitleName ,true , selectedCourse);
                prompt("Sucessfully created the plan!", false);
                planTitle.clear();
                selectedCardsView.getChildren().clear();
            }

        }
    }

//just for prompting different things based on the needs

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


    // The new view that was an add on as split view for cards


    //This is the delete functionality for selected cards here

    @FXML
    private void deleteSelectedCards(){

            for (Iterator<Node> iterator = selectedCardsView.getChildren().iterator(); iterator.hasNext();) {
                Node node = iterator.next();

                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;

                    if (checkBox.isSelected()) {
                        // Remove the selected CheckBox from the selectedCardsView
                        iterator.remove();
                        // Perform additional actions if needed
                        plansDB.removeFromSelectedCards(checkBox);

                    }
                }
            }


    }


    //We reicieve the parameters from the selecrCards view here and display anything that is needed

    //currently not working might fix later not sure if this is necessary
    public void recieveArrayListCheckBox(HashMap<CheckBox,Card> selectedCards, Boolean recieve) {
        // Display all of the checkedBoxes
        // remove the dublicated from the all selectedCsrds

        System.out.println("Incomming cards from another view --" + selectedCards.size());


       selectedCards =  plansDB.removeDublicates(selectedCards);
       List<Card> cardsToAddCheckboxesFor = new ArrayList<>(selectedCards.values());


        for (Card card : cardsToAddCheckboxesFor) {
            CheckBox checkbox = new CheckBox();

            // Set the text and style for the checkbox
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
                checkbox.setGraphic(cardContentBox);

                selectedCardsView.getChildren().add(checkbox);
                plansDB.addCardsToAllSelectedCards(checkbox, card);

            } catch (Exception e) {

            }
        }
    }


    @FXML
    private void addCardsToPlan() {

        HashMap<CheckBox, Card> allCheckBoxCard = plansDB.getAllSelectedCardsToAdd();
        allCheckBoxCard = plansDB.removeDublicates(allCheckBoxCard);

        List<Card> cardsToAddCheckboxesFor = new ArrayList<>(allCheckBoxCard.values());

    for (Card card : cardsToAddCheckboxesFor) {
            CheckBox checkbox = new CheckBox();

            // Set the text and style for the checkbox
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
                checkbox.setGraphic(cardContentBox);
                selectedCardsView.getChildren().add(checkbox);

                plansDB.addCardsToAllSelectedCards(checkbox,card);

            }catch (Exception e){

            }
        }

    }



    private void dynamicCardAddingToView(ArrayList<Card> filteredCards , Boolean firstTimeCreating) {

        allCardContent.getChildren().clear();

        for (Card card : filteredCards) {

            CheckBox checkbox = new CheckBox();

            // Set the text and style for the checkbox
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
                checkbox.setGraphic(cardContentBox);
                allCardContent.setPrefColumns(2);
                allCardContent.getChildren().add(checkbox);


                plansDB.addCardsToPlans(checkbox, card);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Set gaps for the TilePane
        allCardContent.setHgap(10);
        allCardContent.setVgap(20);

    }

    @FXML
    private void searchButtonAction() {
        MaleCheckBox.setSelected(false);
        FemaleCheckBox.setSelected(false);

        String searchText = searchedWord.getText().strip();

        if (!searchText.equals(lastSearch) && !(searchText.equals("")) ){
            lastSearch = searchText;
            handleSearch.clearSearchBoxFilter();
            handleSearch.clearFavoriteCards();
            allCardContent.getChildren().clear();

            searchList(searchText);
        }else{
            if (searchText.equals("")) {
                allCardContent.getChildren().clear();
                dynamicCardAddingToView(handleSearch.getFavoriteCards() , false);
                dynamicCardAddingToView(handleSearch.getAllCardsExceptFavorites(), false);

            } else {
                allCardContent.getChildren().clear();
                dynamicCardAddingToView(handleSearch.getFilteredSearchBoxCards(), false);
            }
        }
    }

    @FXML
    void showFemaleAction() {

        allCardContent.getChildren().clear();
        MaleCheckBox.setSelected(false);

        if (FemaleCheckBox.isSelected()) {
            // when female box is selected
            handleSearch.clearCheckBoxFilter();
            handleSearch.clearFavoriteCards();
            ArrayList<Card> tempList = handleSearch.checkBoxSearch("F" , "N");
            dynamicCardAddingToView(handleSearch.getFavoriteCards(), false);
            dynamicCardAddingToView(tempList, false);
        } else{

            //if not selected un checked
            dynamicCardAddingToView(handleSearch.queryIfTextInBoxSearch(), false);

        }
    }

    @FXML
    void showMaleAction() {

        allCardContent.getChildren().clear();
        FemaleCheckBox.setSelected(false);
        if (MaleCheckBox.isSelected()) {
            // when female box is selected
            handleSearch.clearCheckBoxFilter();
            // add the cards
            handleSearch.clearFavoriteCards();
            ArrayList<Card> tempList = handleSearch.checkBoxSearch("M" , "N");
            dynamicCardAddingToView(handleSearch.getFavoriteCards(), false);
            dynamicCardAddingToView(tempList, false);
        } else{

            //if not selected un checked
            dynamicCardAddingToView(handleSearch.queryIfTextInBoxSearch(), false);

        }
    }



    public void buildCards() throws IOException {

        //map for the cards
        cardListDB = new CardListDB(false);
        handleSearch = new HandleSearch(cardListDB);
        dynamicCardAddingToView(handleSearch.getFavoriteCards(), true);
        dynamicCardAddingToView(handleSearch.getAllCardsExceptFavorites(), true);
        initializeComboBox();
    }



    private void searchList(String inputWord) {
        allCardContent.getChildren().clear();
        dynamicCardAddingToView(handleSearch.textBoxSearch(inputWord), false);

    }


    @FXML
    public void savePlanAtAlternateLocation() throws IOException {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Location To Save Your File");

        // Show directory chooser dialog
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            // Now you have the selected directory, and you can save your plan there
            String selectedPath = selectedDirectory.getAbsolutePath();

            System.out.println(selectedPath);


            String planTitleName = planTitle.getText().strip();

            if (planTitleName.isEmpty() || plansDB.getAllCheckBox().size() == 0) {
                //Prompt user to write the name of the plan
                System.out.println(prompt("Please write the plan name and select atleast one card" ,false));
            } else {
                // see if file exits and if user wants to override
                Boolean fileThere = plansDB.createCsvandCheckFileExists(planTitleName , selectedCourse);
                System.out.println("Is file there? " + fileThere);
                Boolean override = false;

                if (fileThere) {
                    System.out.println("Plan already created do you want to ovverride?");
                    override = prompt(" Plan already created do you want to replace the existing plan?", true);
                }

                //if file is not there or override then write file
                if (!fileThere || override) {
                    plansDB.createFileDifferentLocation(planTitleName ,true,selectedPath );

                    prompt("Sucessfully created the plan!", false);
                    planTitle.clear();
                    selectedCardsView.getChildren().clear();
                }

            }




          //  createPlan();

            // Continue with your code to save the plan to the selected location


        } else {
            System.out.println("No directory selected");
        }
    }


    public void initializeComboBox(){
        selectedCourse = "Unassigned Plans";
        File[] files = new File("AllPlans").listFiles();
        selectCourse.getItems().addAll("Create New Course");

        for(File file : files){
            if(file.isDirectory()){
                selectCourse.getItems().add(file.getName());
            }
        }

    }



    @FXML
    public void onCourseSelected() {
        selectedCourse = selectCourse.getSelectionModel().getSelectedItem().toString();

        if (selectedCourse.equals("Create New Course")) {
            // Prompt user with a text input dialog
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create New Course");
            dialog.setHeaderText("Enter the name of the new course:");
            dialog.setContentText("Course Name:");

            // Show the dialog and wait for the user's response
            Optional<String> result = dialog.showAndWait();

            // If the user entered a course name, update the selectedCourse
            result.ifPresent(courseName -> {
                selectedCourse = courseName;
                selectCourse.getItems().add(courseName);
                selectCourse.setValue(courseName);
            });
        }



        //Create a new dir called that in AllPlans

        try {
            plansDB.createNewDir(selectedCourse);
        } catch (FileAlreadyExistsException e){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Directory Exists");
            alert.setHeaderText("The directory already exists.");

        }



        System.out.println("Selected course: " + selectedCourse);
    }
}



