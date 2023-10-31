package edu.augustana;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class AllCardsController {



    @FXML
    private TextField searchedWord;

    @FXML
    private CheckBox femaleCheckBox;

    @FXML
    private CheckBox maleCheckBox;

    @FXML
    private TilePane allCardContent;

    private String lastSearch = "";




    /*

    This is how I did everything using the handle cards previously i did everything like above!

     */
    private HandleSearch handleSearch;

    public AllCardsController(){
        CardListDB cardListDB = new CardListDB();
        handleSearch = new HandleSearch(cardListDB);
    }

    @FXML
   private void searchButtonAction() {
            maleCheckBox.setSelected(false);
            femaleCheckBox.setSelected(false);

            String searchText = searchedWord.getText().strip();

            if (!searchText.equals(lastSearch) && !(searchText.equals("")) ){
                lastSearch = searchText;
                handleSearch.clearSearchBoxFilter();
                allCardContent.getChildren().clear();

                searchList(searchText);
            }else{
                if (searchText.equals("")) {
                    allCardContent.getChildren().clear();
                    dynamicCardAddingToView(handleSearch.getAllCards());

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
                dynamicCardAddingToView(handleSearch.checkBoxSearch("F" , "N"));
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
               // handleCards.clearCheckBoxFilter();
                    // add the cards
                    dynamicCardAddingToView(handleSearch.checkBoxSearch("M" , "N"));
            } else{

                //if not selected un checked
                    dynamicCardAddingToView(handleSearch.queryIfTextInBoxSearch());

            }
    }




    public void buildCards() {

        dynamicCardAddingToView(handleSearch.getAllCards());
    }



    private void dynamicCardAddingToView(ArrayList<Card> filteredCards) {
        for (Card card : filteredCards) {
            Button button = new Button();

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

                allCardContent.getChildren();
                allCardContent.setPrefColumns(5);

                allCardContent.getChildren().add(button);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Set gaps for the TilePane
        allCardContent.setHgap(10);
        allCardContent.setVgap(20);

    }



    private void searchList(String inputWord) {

        allCardContent.getChildren().clear();
        dynamicCardAddingToView(handleSearch.textBoxSearch(inputWord));

    }

}
