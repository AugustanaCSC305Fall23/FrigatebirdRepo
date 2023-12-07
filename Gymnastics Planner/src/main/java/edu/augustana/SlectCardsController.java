package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
    private ComboBox genderFilter;
    @FXML
    private ComboBox modelFilter;
    @FXML
    private ComboBox levelFilter;
    @FXML
    private ComboBox equipmentFilter;
    public void populateFiltersComboBox(){

        genderFilter.getItems().addAll( "All", "M", "F" , "N");
        modelFilter.getItems().addAll("All" , "M", "F" , "N");
        levelFilter.getItems().addAll( "All", "AB" , "B" , "I" );
        equipmentFilter.getItems().add("All");
        equipmentFilter.getItems().addAll(cardListDB.getAllEquipmentName());

    }

    public void setActionForFilters(){

        genderFilter.setOnAction(e->dynamicCardAddingToView(handleSearch.filterGender(genderFilter.getSelectionModel().getSelectedItem().toString())));
        modelFilter.setOnAction(e->dynamicCardAddingToView(handleSearch.filterModel(modelFilter.getSelectionModel().getSelectedItem().toString())));
        levelFilter.setOnAction(e->dynamicCardAddingToView(handleSearch.filteredLevel(levelFilter.getSelectionModel().getSelectedItem().toString())));
        equipmentFilter.setOnAction(e->dynamicCardAddingToView(handleSearch.filterByEquipment(equipmentFilter.getSelectionModel().getSelectedItem().toString())));


    }
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


    public void buildCards(PlansDB plansDB , EditPlanController editPlanController) throws IOException {

        this.editPlanController = editPlanController;
        //map for the cards
        this.planDB = plansDB;
        cardListDB = new CardListDB(false);
        handleSearch = new HandleSearch(cardListDB);
        dynamicCardAddingToView(handleSearch.getFavoriteCards());
        dynamicCardAddingToView(handleSearch.getAllCardsExceptFavorites());
        populateFiltersComboBox();
        setActionForFilters();
    }



    private void searchList(String inputWord) {
        allCardContent.getChildren().clear();
        dynamicCardAddingToView(handleSearch.textBoxSearch(inputWord));

    }


}