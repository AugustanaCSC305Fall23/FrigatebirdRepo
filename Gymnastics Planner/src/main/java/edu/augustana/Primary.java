package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Primary  {


    @FXML
    private Tab allCardTab;

    @FXML
    private Tab createPlan;

    @FXML
    private Tab allPlans;

    @FXML
    private Tab addCards;

    @FXML
    private Tab exit;




    /*

    In this class we want to be able to set the layout for each of the tab.
    We also want to be able to create the data that we will be using throughout the entire application.
    So this particular class will also feed an object that all of the controller classes will have access to.
    This object will be used to interact wil the data and get desired data from our csv for the UI to work with.

    The developer is expected to use this handle like handleSearch object that is passed to all of the controller class for retreving data from the csv file.


    Make the adjustment for your controller as such.


     */


    public Primary(){

    }

    public void setTabControllers() throws IOException {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("AllCards.fxml"));
            AnchorPane pane = loader.load();
            AllCardsController ac = loader.getController();
            ac.buildCards();
            allCardTab.setContent(pane);


            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("CreatePlan.fxml"));
            AnchorPane pane1 = loader1.load();
            createPlan.setContent(pane1);


            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("AllPlans.fxml"));
            AnchorPane pane2 = loader2.load();
            AllPlansController pc = loader2.getController();
            pc.buildPlans();
            allPlans.setContent(pane2);


            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("AddCards.fxml"));
            AnchorPane pane3 = loader3.load();
            addCards.setContent(pane3);


            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("Exit.fxml"));
            AnchorPane pane4 = loader4.load();
            exit.setContent(pane4);

         clickedOnTabs(ac,pc);

    }


    public void clickedOnTabs(AllCardsController ac , AllPlansController pc){

        allCardTab.setOnSelectionChanged(event -> {
            ac.clearAllContent();
            ac.buildCards();
        });

        allPlans.setOnSelectionChanged(event -> {
            try {
                pc.clearAllContent();
                pc.buildPlans();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }




}
