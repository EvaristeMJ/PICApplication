package com.example.picapplication.database;

import android.graphics.Bitmap;

/**
 * Created by Maxence Jauberty
 * A class to store the information of a game
 */
public class GameInfo {
    private String[] nameInformation;
    private Bitmap backgroundImage;
    public GameInfo(String nameFirstInformation, String nameSecondInformation,
                    String nameThirdInformation, String nameFourthInformation,
                    String nameFifthInformation, Bitmap backgroundImage) {
        this.nameInformation = new String[5];
        this.nameInformation[0] = nameFirstInformation;
        this.nameInformation[1] = nameSecondInformation;
        this.nameInformation[2] = nameThirdInformation;
        this.nameInformation[3] = nameFourthInformation;
        this.nameInformation[4] = nameFifthInformation;
        this.backgroundImage = backgroundImage;
    }
    public String[] getNameInformation() {
        return nameInformation;
    }
    public Bitmap getBackgroundImage() {
        return backgroundImage;
    }
}