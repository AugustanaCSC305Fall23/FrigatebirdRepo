package edu.augustana;

public class Plan {
    String title;
    String imageLink;

    public Plan(String title, String[] data){
        this.title = title;
        this.imageLink = data[4];
        System.out.println(title + imageLink);
    }
}
