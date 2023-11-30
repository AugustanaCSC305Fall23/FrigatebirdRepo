package edu.augustana;


import java.io.*;
import java.util.ArrayList; // import the ArrayList class
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Card {
        private String dataCsvPath = "DEMO1Pack/DEMO1.csv";
        private String favoriteCSVPath = "FavoriteCards.csv";
        private String code;
        private String event;
        private String category;
        private String title;
        private String image;
        private String gender;
        private String sex;
        private String level;
        private String equipment;
        private String keywords;

        private String packFolder;
        private String[] allData;
        private String imageName;
        private boolean isFavorite;


    public Card(String[] data) throws  IOException{


        for(String s : data){

            System.out.println(s);
        }

        if (data.length > 10) {

            //all data array
            allData = new String[data.length];
            for (int i = 0 ; i < data.length ; i++){
                allData[i] = data[i];
            }

            this.code = data[0].strip();
            this.event = data[1].strip();
            this.category = data[2].strip();
            this.title = data[3].strip();
            this.packFolder = data[4].strip();
            this.image = "file:DEMO1Pack/Images/" + data[5].strip();
            this.imageName = data[5].strip();


            //special handeling needed
            this.gender = data[6].strip();
            this.sex = data[7].strip();


            this.level = data[8].strip();
            this.equipment = data[9].strip();
            this.keywords = data[10].strip();

            setFavoriteStatus(this.code);
        } else {
            System.out.println("Insufficient data in the provided array.");
        }
    }

    public String[] getDirectStringMatchingData() {
        if (allData.length >= 11) {
            // Return elements 0 to 5, 9, and 10 from allData
            return new String[]{allData[0], allData[1], allData[2], allData[3], allData[4], allData[5]};

        } else {
            System.out.println("Insufficient data in the provided array.");
            return new String[0];
        }

    }

    public String[] getListDataForSearching(){
        return new String[] {allData[8], allData[9], allData[10]};
    }


    public String[] getData(){

        return allData;

    }



    public void makeFavorite(Card card) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new FileTool().getFavoriteCSVPath(), true));
        String csvLine = String.format("%s,%s", card.getCode(), "Y");
        System.out.println(csvLine);
        writer.write("\n" + csvLine);
        writer.close();
        card.isFavorite = true;
    }

    private void setFavoriteStatus(String code) throws IOException{
        Scanner reader = new Scanner(new File(new FileTool().getFavoriteCSVPath()));
        if (reader.hasNextLine()) {
            reader.nextLine();
        }
        while (reader.hasNextLine()){
            String input = reader.nextLine();
            String[] data = input.split(",");
            if (data[0].equals(code)){
                if(data[1].equals("Y")){
                    this.isFavorite = true;
                    break;
                }
                if (data[1].equals("N")){
                    this.isFavorite = false;
                    break;
                }
            }
            this.isFavorite = false;
        }
        reader.close();
    }

    public String getImageName(){return imageName; }


    public String getCode() {
        return code;
    }

    public String getEvent() {
        return event;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getGender() {
        return gender;
    }

    public String getSex() {
        return sex;
    }

    public String getLevel() {
        return level;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getKeywords() {
        return keywords;
    }

    public boolean getFavoriteStatus(){
        return isFavorite;}
    public  String getPackFolder(){return packFolder;}

    @Override
    public String toString() {
        return "Card{" +
                "code='" + code + '\'' +
                ", event='" + event + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", gender='" + gender + '\'' +
                ", sex='" + sex + '\'' +
                ", level='" + level + '\'' +
                ", equipment='" + equipment + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }

}
