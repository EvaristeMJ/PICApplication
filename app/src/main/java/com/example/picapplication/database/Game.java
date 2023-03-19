package com.example.picapplication.database;

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
    /*
    public static List<Game> initGameList(){
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game("Game1","This the first game, this is a very interesting pitch\n You can play with your friends, that is really funny",
                "Game description",com.google.android.material.R.drawable.test_level_drawable));
        games.add(new Game("Game2","This the second game, this is a very interesting pitch\n You can play with your friends, that is really funny",
                "Game description",com.google.android.material.R.drawable.test_level_drawable));
        games.add(new Game("Game3","This the third game, this is a very interesting pitch\n You can play with your friends, that is really funny",
                "Game description",com.google.android.material.R.drawable.test_level_drawable));
        games.add(new Game("Game4","This the fourth game, this is a very interesting pitch\n You can play with your friends, that is really funny",
                "Game description",com.google.android.material.R.drawable.test_level_drawable));
        games.add(new Game("Game5","This the fifth game, this is a very interesting pitch\n You can play with your friends, that is really funny",
                "Game description",com.google.android.material.R.drawable.test_level_drawable));
        return games;
    }

     */
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

