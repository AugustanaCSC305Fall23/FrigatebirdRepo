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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AllPlansController {


    private String allPlansDir;


    @FXML
    private ListView<String> plansView;

    @FXML
    void initialize() {
        filterSelect.getItems().addAll("none", "event", "category", "gender", "sex", "level");
    }

    @FXML
    private ComboBox<String> filterSelect;

    @FXML
    void showPlan() throws IOException {
        if (plansView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a plan first");
            alert.show();
        } else {
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

    @FXML
    public void addPlanToListView(String Title) {
        plansView.getItems().add(Title);
    }
        public void clearAllContent () {

            plansView.getItems().clear();
        }

        public void buildPlans () {
            allPlansDir = new FileTool().getPlansDirectory();
            File[] planFiles = new File(allPlansDir).listFiles();
            if (planFiles.length != 0) {
                for (File file : planFiles) {
                    Scanner fileReader = new Scanner(file.getPath());
                    String input = fileReader.nextLine();
                    String[] titleData = input.split("_");
                    String title = titleData[0];
                    title = title.substring(9);
                    plansView.getItems().add(title);
                }
            }
        }

        @FXML
        private void editPlan () throws IOException {

            if (plansView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Select a plan first");
                alert.show();
            } else {
                String planName = plansView.getSelectionModel().getSelectedItem();
                FileTool fileTool = new FileTool();
                String filePath = fileTool.getPlanFilePath(planName);
                buildEditPlanStage(filePath, planName);
            }
        }

        private void buildEditPlanStage (String filePath, String planTitle) throws IOException {

            FXMLLoader loader = new FXMLLoader(AllCardsController.class.getResource("Edit Plans.fxml"));
            Parent root = loader.load();
            EditPlanController controller = loader.getController();  // Initialize the controller
            Scene scene = new Scene(root, 1500, 1500);
            Stage showPlanStage = new Stage();
            showPlanStage.setTitle("Edit Plan");
            showPlanStage.setScene(scene);
            showPlanStage.show();

            controller.buildLayout(filePath, planTitle);


        }

    }

