package edu.augustana;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.TilePane;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ShowPlanController {
    @FXML
    private Label Title;

    @FXML
    private Button exitButton;

    @FXML
    private TilePane cardPane;

    private String filePath;

    private String allPlansDir;

    private ArrayList<String> shortCodes;

    private ArrayList<Card> cardsList;

    private ArrayList<Card> allCards;
    @FXML
    void initialize() {
        }

        public void buildPlans(String planName, String segmentType, ArrayList<Card> cards) throws IOException{
        shortCodes = new ArrayList<>();
        cardsList = new ArrayList<>();
        allCards = cards;
        allPlansDir = "AllPlans";
        Title.setText(planName);
        //write logic to read through the csv file and collect the short codes
            filePath = getFilePath(planName);
            try {
                Scanner reader = new Scanner(new File(filePath));
                System.out.println(filePath);
                String line = reader.nextLine();
                while (reader.hasNextLine()){
                    line = reader.nextLine();
                    String[] data = line.split(",");
                    shortCodes.add(data[0]);
                }

            }catch(FileNotFoundException e){
                throw new RuntimeException(e);
                }
            System.out.println(shortCodes.get(2));
            //write the logic to gather the list of cards used in the plan
            for (String code: shortCodes){
                cardsList.add(getCard(code));
            }
            System.out.println(cardsList.size());
            //write the logic to segment the cards into the segment type
            //write the logic to show the cards
            dynamicCarAddingToView(cardsList);
        }
        private String getFilePath(String planName){
            File[] files = new File(allPlansDir).listFiles();
            for(File file: files){
                String path = file.getPath();
                if (path.contains(planName)){
                    return path;
                }
            }
            return "no File Found";
        }

        private Card getCard(String code){
        for (Card card: allCards){
            if (card.getCode().equals(code)){
                return card;
            }
        }
        return null;
        }

    private void dynamicCarAddingToView(ArrayList<Card> filteredCards) {
        for (Card card : filteredCards) {
            Button button = new Button();

            // Set the text and style for the button
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
                cardPane.setPrefColumns(5);

                cardPane.getChildren().add(button);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Set gaps for the TilePane
        cardPane.setHgap(10);
        cardPane.setVgap(20);

    }

    @FXML
    void exitShowPlanView() throws IOException {
        App.setRoot("Primary");
    }

    }

