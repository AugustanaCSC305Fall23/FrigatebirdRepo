package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AllPlansController {


    private String dataCsvPath = "DEMO1Pack/DEMO1.csv";

    private String allPlansDir;

    @FXML
    private ListView<String> plansView;

    @FXML
    private Button showPlanButton;

    @FXML
    void initialize() {
        filterSelect.getItems().addAll("none", "event", "category", "gender", "sex", "level");
    }

    @FXML
    private ComboBox<String> filterSelect;

    @FXML
    void showPlan() throws IOException {
        if (plansView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a plan first");
            alert.show();
        }
        else{
        FXMLLoader loader = new FXMLLoader(AllCardsController.class.getResource("ShowPlan.fxml"));
        Parent root = loader.load();
        ShowPlanController controller = loader.getController();  // Initialize the controller
        Scene scene = new Scene(root, 1500, 1500);
        Stage showPlanStage = new Stage();
        showPlanStage.setTitle("Show Plan");
        showPlanStage.setScene(scene);
        showPlanStage.show();
        String segmentType = filterSelect.getSelectionModel().getSelectedItem();

        controller.buildPlans(plansView.getSelectionModel().getSelectedItem(), segmentType);
        }
    }

    void buildPlans() throws  IOException{
        allPlansDir = new FileTool().getPlansDirectory();
        File[] planFiles = new File(allPlansDir).listFiles();
        if (planFiles.length != 0){
            for (File file: planFiles){
                Scanner fileReader = new Scanner(file.getPath());
                String input = fileReader.nextLine();
                String[] titleData = input.split("_");
                String title = titleData[0];
                title = title.substring(9);
                plansView.getItems().add(title);
                //make button show full plan on click
//                button.setOnMouseClicked(evt -> {
//                    try {
//                        showPlan(button);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//                showPlanList.getChildren().add(button);
            }
        }
    }

}
