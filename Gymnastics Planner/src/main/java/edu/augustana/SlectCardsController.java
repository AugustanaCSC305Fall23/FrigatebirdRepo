package edu.augustana;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;



public class SlectCardsController {


    @FXML
    Button selectTheseCards;

    @FXML
    private TextField searchedWord;

    @FXML
    Button searchButton;

    @FXML
    private CheckBox femaleCheckBox;

    @FXML
    private CheckBox maleCheckBox;

    @FXML
    private TilePane allCardContent;

    private String lastSearch = "";
    HashMap<CheckBox, Card> selectedCardsToCheckBox;
    CreatePlanController createPlanController;

    EditPlanController editPlanController;

    private String dataCsvPath = "DEMO1Pack/DEMO1.csv";
    private PlansDB planDB;
    private CardListDB cardListDB;
    private  HandleSearch handleSearch;


    @FXML
    private void addCardsToPlan() {

        //add all the checked boxed cards into this
        //either I can go through all of the cards that are there and then see things that are checked
        //or I can create a array that is updated the when a checkbox is clicked or not
        //i think the second onw will require more array access

        selectedCardsToCheckBox = planDB.getFilterSelectedTempCards();
        try {
            createPlanController.recieveArrayListCheckBox(selectedCardsToCheckBox, true);
        }catch (Exception e){

        }

        try {
            editPlanController.recieveArrayListCheckBox(selectedCardsToCheckBox, true);

        }catch (Exception e){

        }

        //Close the this particular tab
        Stage stage = (Stage) selectTheseCards.getScene().getWindow();
        stage.close();


    }

    private void dynamicCardAddingToView(ArrayList<Card> filteredCards) {

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
                allCardContent.setPrefColumns(5);
                allCardContent.getChildren().add(checkbox);

                planDB.addCardsToPlans(checkbox,card);

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
        maleCheckBox.setSelected(false);
        femaleCheckBox.setSelected(false);

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
                dynamicCardAddingToView(handleSearch.getFavoriteCards());
                dynamicCardAddingToView(handleSearch.getAllCardsExceptFavorites());

            } else {
                allCardContent.getChildren().clear();
                dynamicCardAddingToView(handleSearch.getFilteredSearchBoxCards());
            }
        }
    }

    @FXML
    void showFemaleAction() {

        allCardContent.getChildren().clear();
        maleCheckBox.setSelected(false);

        if (femaleCheckBox.isSelected()) {
            // when female box is selected
            handleSearch.clearCheckBoxFilter();
            handleSearch.clearFavoriteCards();
            ArrayList<Card> tempList = handleSearch.checkBoxSearch("F" , "N");
            dynamicCardAddingToView(handleSearch.getFavoriteCards());
            dynamicCardAddingToView(tempList);
        } else{

            //if not selected un checked
            dynamicCardAddingToView(handleSearch.queryIfTextInBoxSearch());

        }
    }

    @FXML
    void showMaleAction() {

        allCardContent.getChildren().clear();
        femaleCheckBox.setSelected(false);
        if (maleCheckBox.isSelected()) {
            // when female box is selected
            handleSearch.clearCheckBoxFilter();
            // add the cards
            handleSearch.clearFavoriteCards();
            ArrayList<Card> tempList = handleSearch.checkBoxSearch("M" , "N");
            dynamicCardAddingToView(handleSearch.getFavoriteCards());
            dynamicCardAddingToView(tempList);
        } else{

            //if not selected un checked
            dynamicCardAddingToView(handleSearch.queryIfTextInBoxSearch());

        }
    }


    public void buildCards(PlansDB plansDB ,CreatePlanController createPlanController) throws IOException {

        this.createPlanController = createPlanController;
        //map for the cards
        this.planDB = plansDB;
        cardListDB = new CardListDB(false);
        handleSearch = new HandleSearch(cardListDB);
        dynamicCardAddingToView(handleSearch.getFavoriteCards());
        dynamicCardAddingToView(handleSearch.getAllCardsExceptFavorites());
    }

    public void buildCards(PlansDB plansDB , EditPlanController editPlanController) throws IOException {

        this.editPlanController = editPlanController;
        //map for the cards
        this.planDB = plansDB;
        cardListDB = new CardListDB(false);
        handleSearch = new HandleSearch(cardListDB);
        dynamicCardAddingToView(handleSearch.getFavoriteCards());
        dynamicCardAddingToView(handleSearch.getAllCardsExceptFavorites());
    }



    private void searchList(String inputWord) {
        allCardContent.getChildren().clear();
        dynamicCardAddingToView(handleSearch.textBoxSearch(inputWord));

    }


}