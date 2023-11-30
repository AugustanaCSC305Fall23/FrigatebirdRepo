package edu.augustana;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AllPlansController {


    private String allPlansDir;


    @FXML
    private Label planListLabel;


    @FXML
    private ListView<Button> plansView;


    private String selectedCourse = "";

    @FXML
    void initialize() {
        filterSelect.getItems().addAll("none", "event", "category", "gender", "sex", "level"

        );
    }

    @FXML
    private ComboBox<String> filterSelect;


    @FXML
    private ListView<String> showPlans;

    @FXML
    void showPlan() throws IOException {
        if (showPlans.getSelectionModel().getSelectedItem() == null) {
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
            controller.buildPlans(showPlans.getSelectionModel().getSelectedItem(), segmentType ,false ,"");
        }
    }

    @FXML
    public void addPlanToListView(String Title) {



        Button btn = new Button();
        btn.setText(Title);
        plansView.getItems().add(btn);

    }
        public void clearAllContent () {
            plansView.getItems().clear();
        }

        public void buildPlans () {
            allPlansDir = new FileTool().getPlansDirectory();
            File[] planFiles = new File(allPlansDir).listFiles();
            if (planFiles.length != 0) {
                for (File file : planFiles) {
                  //  if (file.isDirectory())

                    Scanner fileReader = new Scanner(file.getPath());
                    String input = fileReader.nextLine();
                    String[] titleData = input.split("_");
                    String title = titleData[0];
                    title = title.substring(9);

                    if (file.isDirectory()){
                        //We need to perhaps so something

                        Button btn = new Button();
                        btn.setText(title);
                        btn.setOnAction(e-> courseAction(btn.getText()));
                        plansView.getItems().add(btn);

                        System.out.println("Thisihsisfbskadfkjasdflkjasd ");

                    }else{

                        Button btn = new Button();
                        btn.setText(title);
                        plansView.getItems().add(btn);

                    }
                }
            }

        }

    public void buildPlansForCourse (String course) {
        System.out.println(course);
        File[] planFiles = new File("AllPlans/" + course).listFiles();

        if (planFiles.length != 0) {
            for (File file : planFiles) {
                Scanner fileReader = new Scanner(file.getPath());
                String input = fileReader.nextLine();
                String[] titleData = input.split("_");
                String title = titleData[0];
                title = title.substring(9);
                showPlans.getItems().add(title);

            }
        }

    }


        private void courseAction(String course){

            planListLabel.setText("Thise are all the plans in course: " + course);
            showPlans.getItems().clear();
            selectedCourse = course;
            buildPlansForCourse(course);
        System.out.println("HR22342342938492340923498230480823");

        }


        @FXML
        private void editPlan () throws IOException {

            if (showPlans.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Select a plan first");
                alert.show();
            } else {
                String planName = showPlans.getSelectionModel().getSelectedItem();
                String[] planNameList = showPlans.getSelectionModel().getSelectedItem().split("/");

                FileTool fileTool = new FileTool();
                String filePath = fileTool.getPlanFilePath(planName);
                buildEditPlanStage(filePath, planNameList[1] , false , ""  );
            }
        }

        @FXML
     void editPlanDifferentLocation () throws IOException {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");

            // Show file chooser dialog
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                // Now you have the selected directory, and you can save your plan there
                String selectedPath = selectedFile.getAbsolutePath();
                String planName = selectedFile.getName();

                System.out.println(selectedPath);

                if (selectedFile == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Select a plan first");
                    alert.show();
                } else {

                    try{
                        buildEditPlanStage(selectedPath, planName ,true , selectedPath );
                    }catch (Exception e){

                    }

                }

            }
        }


        private void buildEditPlanStage (String filePath, String planTitle , Boolean isOutside , String selectedPath) throws IOException {

            FXMLLoader loader = new FXMLLoader(AllCardsController.class.getResource("Edit Plans.fxml"));
            Parent root = loader.load();
            EditPlanController controller = loader.getController();  // Initialize the controller
            Scene scene = new Scene(root, 1500, 1500);
            Stage showPlanStage = new Stage();
            showPlanStage.setTitle("Edit Plan");
            showPlanStage.setScene(scene);
            showPlanStage.show();

            System.out.println("File path outside: " + filePath);

            controller.buildLayout(filePath, planTitle , isOutside , selectedPath , selectedCourse) ;


        }



        @FXML
        private void viewPlanDifferentLocation() throws IOException {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");

            // Show file chooser dialog
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                // Now you have the selected directory, and you can save your plan there
                String selectedPath = selectedFile.getAbsolutePath();

                System.out.println(selectedPath);

                if (selectedFile == null) {
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
                    controller.buildPlans("", segmentType , true , selectedPath);
                }

            }else{

                //Error
            }


        }


        @FXML
        private void deletePlan(){

        //bad coding hard coded it in here coz everything is messy now
        String plantoDelete = "AllPlans/" + showPlans.getSelectionModel().getSelectedItem() +"_Plan.csv" ;
            Path filePath = Paths.get(plantoDelete);

            // Delete the file
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            courseAction(selectedCourse);

        }

    }

