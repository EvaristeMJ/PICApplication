package com.example.picapplication.database;

import android.graphics.Bitmap;

/**
 * Created by Maxence Jauberty
 * A class to store the information of a game
 */
public class GameInfo {
    private String[] nameInformation;
    private String[] information;
    private Bitmap backgroundImage;
    public GameInfo(String nameFirstInformation, String nameSecondInformation,
                    String nameThirdInformation, String nameFourthInformation,
                    String nameFifthInformation,String firstInformation, String secondInformation,
                    String thirdInformation, String fourthInformation,
                    String fifthInformation, Bitmap backgroundImage) {
        this.nameInformation = new String[5];
        this.nameInformation[0] = nameFirstInformation;
        this.nameInformation[1] = nameSecondInformation;
        this.nameInformation[2] = nameThirdInformation;
        this.nameInformation[3] = nameFourthInformation;
        this.nameInformation[4] = nameFifthInformation;
        this.information = new String[5];
        this.information[0] = firstInformation;
        this.information[1] = secondInformation;
        this.information[2] = thirdInformation;
        this.information[3] = fourthInformation;
        this.information[4] = fifthInformation;
        this.backgroundImage = backgroundImage;
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