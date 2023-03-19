package com.example.picapplication.database;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int id;
    private ArrayList<Integer> gamesPlayed;

    public User(String username,String password,int id){
        this.password = password;
        this.username = username;
        this.id = id;
        gamesPlayed = new ArrayList<>();
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
    public void playGame(int gameId){
        gamesPlayed.add(gameId);
    }
}
