package edu.augustana;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FileTool {
    private final String allPlansDir = "AllPlans";

    private final String favoriteCSVPath = "FavoriteCards.csv";

    public FileTool(){

    }

    public String getPlansDirectory(){
        return allPlansDir;
    }

    public String getFavoriteCSVPath(){return  favoriteCSVPath;}

    public String getPlanFilePath(String planName){
        File[] files = new File(allPlansDir).listFiles();

        for(File file: files){
            if(file.isDirectory()){
                File[] course = new File(file.getPath()).listFiles();
                for(File moreFile: course){
                    String path = moreFile.getPath();
                    if (path.contains(planName)){
                        return path;
                    }
                }
            }
            String path = file.getPath();
            if (path.contains(planName)){
                return path;
            }
        }
        return "no File Found";
    }



    public ArrayList<String[]> getFileInfo(String filePath) throws FileNotFoundException {

        ArrayList<String[]> fileInfo = new ArrayList<>();
        Scanner reader = new Scanner(filePath);
        String line = "";
        while (reader.hasNextLine()) {
                line = reader.nextLine();
                String[] data = line.split(",");
                fileInfo.add(data);
        }

        return fileInfo;
    }

}
