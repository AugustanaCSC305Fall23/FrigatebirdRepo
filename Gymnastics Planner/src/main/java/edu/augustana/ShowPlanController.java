package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ShowPlanController {
    @FXML
    private Label Title;

    @FXML
    private Button exitButton;

    private String filePath;

    private String allPlansDir;
    @FXML
    void initialize() {
        }

        public void buildPlans(String planName, String segmentType){
        allPlansDir = "AllPlans";
        //write logic to read through the csv for the specific file name
            filePath = getFilePath(planName);
            try {
                FileReader fileReader = new FileReader(filePath);
            }catch(FileNotFoundException e){
                throw new RuntimeException(e);
                }


            //write the logic to segment the cards into the segment type
            //write the logic to show the cards
        }
        private String getFilePath(String planName){
            File[] files = new File(allPlansDir).listFiles();
            for(File file: files){
                String path = file.getAbsolutePath();
                if (path.contains(planName)){
                    return path;
                }
            }
            return "no File Found";
        }

    @FXML
    void exitShowPlanView() throws IOException {
        App.setRoot("Primary");
    }

    }

