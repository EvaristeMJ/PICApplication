package com.example.picapplication.database;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int id;
    private Bitmap profilePicture;
    private ArrayList<Integer> gamesPlayed;

    public User(String username,String password,int id,Bitmap profilePicture){
        this.password = password;
        this.username = username;
        this.id = id;
        gamesPlayed = new ArrayList<>();
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setGamesPlayed(String gamesPlayed) {
        String[] games = gamesPlayed.split(",");
        for(int i = 0; i < games.length; i++){
            this.gamesPlayed.add(Integer.parseInt(games[i]));
        }
    }
    public String getGamesPlayed(){
        String games = "";
        for(int i = 0; i < gamesPlayed.size(); i++){
            games += gamesPlayed.get(i);
            if(i != gamesPlayed.size()-1){
                games += ",";
            }
        }
        return games;
    }
    public int getId() {
        return id;
    }
    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void playGame(int gameId){
        gamesPlayed.add(gameId);
    }
}
