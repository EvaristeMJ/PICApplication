package com.example.picapplication.database;

import android.graphics.Bitmap;

import com.example.picapplication.utilities.BitmapMethod;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int id;
    private int idBoard;
    private Bitmap profilePicture;
    private boolean wantsAssistance;
    private ArrayList<Integer> gamesPlayed;

    public User(String username,String password,int id,Bitmap profilePicture){
        this.password = password;
        this.username = username;
        this.id = id;
        this.idBoard = 0;
        gamesPlayed = new ArrayList<>();
        wantsAssistance = false;
        this.profilePicture = profilePicture;
    }

    /**
     * Creates a user with only the username and the profile picture
     * Only used for displaying the user
     * @param username the username of the user
     * @param profilePicture the profile picture of the user
     * @return the user created with only the username and the profile picture
     */
    public static User createDisplayUser(String username,Bitmap profilePicture){
        return new User(username,"",0, BitmapMethod.getDefaultProfilePicture());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setWantsAssistance(boolean wantsAssistance) {
        this.wantsAssistance = wantsAssistance;
    }
    public boolean wantsAssistance() {
        return wantsAssistance;
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
    public void setIdBoard(int idBoard) {
        this.idBoard = idBoard;
    }
    public int getIdBoard() {
        return idBoard;
    }
    public int getId() {
        return id;
    }
    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }
    public Bitmap getProfilePicture(){
        return profilePicture;
    }
    public void playGame(int gameId){
        gamesPlayed.add(gameId);
    }
    public void setUsername(String newUsername){
        username = newUsername;
    }
    public void setPassword(String newPassword){
        password = newPassword;
    }
}
