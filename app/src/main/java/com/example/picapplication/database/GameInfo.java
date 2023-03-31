package com.example.picapplication.database;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Maxence Jauberty
 * A class to store the information of a game
 */
public class GameInfo {
    private String[] nameInformation = new String[4];
    private String[] information = new String[4];
    private Bitmap backgroundImage;
    private int numberOfInformation = 0;
    public GameInfo(){
        backgroundImage = null;
        information = new String[5];
        nameInformation = new String[5];
    }
    public GameInfo(String picFile){
        BufferedReader reader = new BufferedReader(new StringReader(picFile));
        int indexInformation = 0;
        try {
            String line = reader.readLine();
            while(line != null && !line.contains("EndGameSettings")){
                if(line.contains("AddPlayerVariable")){
                    line = reader.readLine();
                    setNameInformation(indexInformation,line);
                    line = reader.readLine();
                    setInformation(indexInformation,line);
                    indexInformation++;
                    numberOfInformation++;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public GameInfo(String nameFirstInformation, String nameSecondInformation,
                    String nameThirdInformation, String nameFourthInformation,
                    String nameFifthInformation,String firstInformation, String secondInformation,
                    String thirdInformation, String fourthInformation,
                    String fifthInformation, Bitmap backgroundImage) {
        this.nameInformation = new String[4];
        this.nameInformation[0] = nameFirstInformation;
        this.nameInformation[1] = nameSecondInformation;
        this.nameInformation[2] = nameThirdInformation;
        this.nameInformation[3] = nameFourthInformation;
        this.information = new String[4];
        this.information[0] = firstInformation;
        this.information[1] = secondInformation;
        this.information[2] = thirdInformation;
        this.information[3] = fourthInformation;
        this.backgroundImage = backgroundImage;
    }

    /**
     * Return the game file as a string
     * @param path
     * @return
     */
    public static String gameFileToString(String path){
        String gameFile = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while(line != null) {
                gameFile += line + "\n";
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameFile;
    }
    public void setNameInformation(int i, String nameInformation) {
        this.nameInformation[i] = nameInformation;
    }
    public void setInformation(int i, String information) {
        this.information[i] = information;
    }
    public String[] getNameInformation() {
        return nameInformation;
    }
    public String[] getInformation() {
        return information;
    }
    public Bitmap getBackgroundImage() {
        return backgroundImage;
    }
}