package edu.augustana;


import java.util.ArrayList; // import the ArrayList class
import java.util.Arrays;

public class Card {
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

        private String[] allData;

    public Card(String[] data) {
        if (data.length >= 10) {

            //all data array

             allData = new String[data.length];
            for (int i = 0 ; i < data.length ; i++){

                allData[i] = data[i];
            }

            this.code = data[0].strip();
            this.event = data[1].strip();
            this.category = data[2].strip();
            this.title = data[4].strip();
            this.image = "file:DEMO1Pack/Images/" + data[5].strip();
            this.gender = data[6].strip();
            this.sex = data[7].strip();
            this.level = data[8].strip();
            this.equipment = data[9].strip();
            this.keywords = data[10].strip();
        } else {
            throw new IllegalArgumentException("Insufficient data in the provided array.");
        }
    }


    public String[] getData(){

        return allData;

    }


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
