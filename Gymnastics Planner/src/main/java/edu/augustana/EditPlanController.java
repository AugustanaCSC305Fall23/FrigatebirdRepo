package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditPlanController {

    @FXML
    private TilePane selectedCardsView;

    @FXML
    private TextField planTitle;
    private PlansDB plansDB;
    private CardListDB cardListDB;

    private String officialPlanTitle;


    public void buildLayout(String filePath , String planName){
        planTitle.setText(planName);
        plansDB = new PlansDB();
        cardListDB = new CardListDB(filePath,false);
        this.officialPlanTitle = planName;


        //create cBox and then create Cards and add it to plansDb
        for(Card card : cardListDB.getAllCards()){
            System.out.println("Whats  in the care: " + card.toString());
            CheckBox checkBox = new CheckBox();
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
                checkBox.setGraphic(cardContentBox);

                selectedCardsView.getChildren();
                selectedCardsView.setPrefColumns(5);

                selectedCardsView.getChildren().add(checkBox);
                plansDB.addCardsToAllSelectedCards(checkBox, card);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // Set gaps for the TilePane
        selectedCardsView.setHgap(10);
        selectedCardsView.setVgap(20);


        System.out.println("The num of the cards in the tile pane after buildin the cards: " + plansDB.getCardsInViewSize());

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
                try {
                    createPlanController.buildCards(plansDB,this);
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


    public void recieveArrayListCheckBox(HashMap<CheckBox,Card> selectedCards, Boolean recieve) {
        // Display all of the checkedBoxes
        // remove the dublicated from the all selectedCsrds

        if(recieve) {
            plansDB.recieveCheckBox(selectedCards);
        }

        selectedCardsView.getChildren().clear();
        selectedCardsView.setPrefColumns(4);

        for (CheckBox cBox : plansDB.getAllCheckBox().keySet()) {
            cBox.setSelected(false);
            selectedCardsView.getChildren().add(cBox);
        }

    }


    @FXML
    private void deleteSelectedCards(){

        if (plansDB.getAllCheckBox().size() == 0){

            //Prompt that the selected cards has nothing to delete
            prompt("No cards to delete!",false);
        }else{

            recieveArrayListCheckBox(plansDB.deleteCheckBox(),false);

        }

    }



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

    @FXML
    public void makeChanges() throws IOException {

        System.out.println("Plan title in make changes for edit plans for final csv edit: " + officialPlanTitle+"_Plan.csv");
        plansDB.overrideOrCreateNewPlan(officialPlanTitle+"_Plan.csv" ,false);

        Boolean wantToChange =  prompt("Sucessfully Made the change! \n Do you want to make more changes? ",true);



        if(!wantToChange){
            Stage stage = (Stage) planTitle.getScene().getWindow();
            stage.close();
        }

    }


}
