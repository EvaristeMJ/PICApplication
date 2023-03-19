package com.example.picapplication.database;

import com.example.picapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String gameName,gamePitch,gameDescription,gameTime,gameRules,gameFile,author;
    private int id,image,gamePopularity;

    public Game(int id, String gameName, String gamePitch, String gameDescription, int image, String gameTime, String gameRules, String gameFile, int gamePopularity, String author) {
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
        gameDescription = null;
        gameFile = null;
        gameName = null;
        gamePitch = null;
        gameRules = null;
        gameTime = null;
        author = null;
        image = 0;
        gamePopularity = 0;
        id = 0;
    }
    public static List<Game> initGameList(){
        ArrayList games = new ArrayList<Game>();
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", R.drawable.ic_menu_camera,"Game 1 time","Game 1 rules","Game 1 file",0,"Game 1 author"));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", R.drawable.ic_menu_camera,"Game 1 time","Game 1 rules","Game 1 file",0,"Game 1 author"));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", R.drawable.ic_menu_camera,"Game 1 time","Game 1 rules","Game 1 file",0,"Game 1 author"));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", R.drawable.ic_menu_camera,"Game 1 time","Game 1 rules","Game 1 file",0,"Game 1 author"));
        games.add(new Game(1,"Game 1","Game 1 pitch","Game 1 description", R.drawable.ic_menu_camera,"Game 1 time","Game 1 rules","Game 1 file",0,"Game 1 author"));
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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

