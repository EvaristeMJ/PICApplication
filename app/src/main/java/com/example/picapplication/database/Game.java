package com.example.picapplication.database;

import static com.makeramen.roundedimageview.RoundedDrawable.drawableToBitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.picapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Game {
    private String gameName,gamePitch,gameDescription,gameTime,gameRules,gameFile,author;
    private int id,gamePopularity;
    private Bitmap image;

    public Game(int id, String gameName, String gamePitch, String gameDescription, Bitmap image, String gameTime, String gameRules, String gameFile, int gamePopularity, String author) {
        this.gameName = gameName;
        this.gamePitch = gamePitch;
        this.image = image;
        this.gameDescription = gameDescription;
        this.gameTime = gameTime;
        this.gameRules = gameRules;
        this.gameFile = gameFile;
        this.author = author;
        this.gamePopularity = gamePopularity;
        this.id = id;
    }
    public Game(){
        gameDescription = "";
        gameFile = "";
        gameName = "";
        gamePitch = "";
        gameRules = "";
        gameTime = "";
        author = "";
        image = null;
        gamePopularity = 0;
        id = 0;
    }
    public static List<Game> initGameList(Resources resources) {
        ArrayList games = new ArrayList<Game>();
        Bitmap bitmap = drawableToBitmap(resources.getDrawable(R.drawable.ic_menu_camera));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", bitmap,"Game 1 time","Game 1 rules","Game 1 file",0,"4"));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", bitmap,"Game 1 time","Game 1 rules","Game 1 file",0,"4"));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", bitmap,"Game 1 time","Game 1 rules","Game 1 file",0,"4"));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", bitmap,"Game 1 time","Game 1 rules","Game 1 file",0,"4"));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", bitmap,"Game 1 time","Game 1 rules","Game 1 file",0,"4"));
        return games;
    }
    // Getters and setters

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGamePitch() {
        return gamePitch;
    }

    public void setGamePitch(String gamePitch) {
        this.gamePitch = gamePitch;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getGameRules() {
        return gameRules;
    }

    public void setGameRules(String gameRules) {
        this.gameRules = gameRules;
    }

    public String getGameFile() {
        return gameFile;
    }

    public void setGameFile(String gameFile) {
        this.gameFile = gameFile;
    }

    public Bitmap getImage() {
        return image;
    }

    /**
     * This method is used to get the game information from the game file
     * Read the file line by line and add the information to the GameInfo object
     * @see GameInfo
     * @return GameInfo object with all the information about the game
     */
    public GameInfo getGameInfo(){
        GameInfo gameInfo = new GameInfo(gameFile);
        return gameInfo;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getGamePopularity() {
        return gamePopularity;
    }
    public int getId() {
        return id;
    }
}

