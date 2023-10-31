package edu.augustana;

import java.io.File;

public class FileTool {
    private final String allPlansDir = "AllPlans";

    public FileTool(){

    }

    public String getPlansDirectory(){
        return allPlansDir;
    }

    public String getPlanFilePath(String planName){
        File[] files = new File(allPlansDir).listFiles();
        for(File file: files){
            String path = file.getPath();
            if (path.contains(planName)){
                return path;
            }
        }
        return "no File Found";
    }

}
