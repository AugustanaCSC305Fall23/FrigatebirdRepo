package edu.augustana;

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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditPlanController {

    @FXML
    private TilePane selectedCardsView;

    @FXML
    private Label planTitle;

    @FXML
    private ComboBox selectCourse;
    private PlansDB plansDB;
    private CardListDB cardListDB;

    private String officialPlanTitle;

    private Boolean outsideLocation;

    private String pathOutsideLocation;

    private String selectedCourse;

    @FXML
    private TextField programNoteArea;

    public void initializeComboBox(){
        selectedCourse = "Unassigned Plans";
        File[] files = new File("AllPlans").listFiles();
        for(File file : files){
            if(file.isDirectory()){
                selectCourse.getItems().add(file.getName());
            }
        }

    }

    @FXML
    public void onCourseSelected() {
        selectedCourse = selectCourse.getSelectionModel().getSelectedItem().toString();

        selectCourse.setValue(selectedCourse);

        //Create a new dir called that in AllPlans

        System.out.println("Selected course: " + selectedCourse);
    }





    public void buildLayout(String filePath , String planName , Boolean isOutside , String pathOutsideLocation , String selectedCourse){
        if (isOutside) {
            selectCourse.setVisible(false);
        }

        initializeComboBox();
        this.selectedCourse = selectedCourse;
        outsideLocation = isOutside;
        this.pathOutsideLocation = pathOutsideLocation;

        planTitle.setText(planName);


        plansDB = new PlansDB();

        System.out.println("File path being printed: " + filePath);
        cardListDB = new CardListDB(filePath,true);
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
            plansDB.removeDublicates(selectedCards);
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
        String note = programNoteArea.getText();

        if(outsideLocation){

            System.out.println("Pathoutsidelocation: " + pathOutsideLocation);

            plansDB.createFileDifferentLocation(officialPlanTitle , false , pathOutsideLocation , note);

        }else {


            System.out.println("Plan title in make changes for edit plans for final csv edit: " + officialPlanTitle + "_Plan.csv");
            System.out.println("The selecred course is this : " + selectedCourse);
            plansDB.overrideOrCreateNewPlan(selectedCourse + "/" + officialPlanTitle + "_Plan.csv", false , note) ;

        }
        Boolean wantToChange = prompt("Sucessfully Made the change! ", false);


        Stage stage = (Stage) planTitle.getScene().getWindow();
        stage.close();


    }


}
