package com.example.picapplication.database;

import android.graphics.Bitmap;

import com.example.picapplication.utilities.BitmapMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DatabaseHelper implements PicDatabase {

    private static User userLogged;
    private static Game gameSelected;
    private static Bitmap defaultProfilePicture; //TODO: set default profile picture
    public static int USER_NOT_FOUND = -1;
    public static int WRONG_PASSWORD = -2;
    private String url = "http://137.194.210.224/";
    private static String serverSideUser = "user.php";
    private static String serverSideGame = "game.php";
    private static String serverSideUserInteraction = "user_play_games.php";

    public void defaultLogin(){
        userLogged = new User("anon","pic",0,defaultProfilePicture);

    }
    @Override
    public void addUser(String username, String password) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", username);
        httpConnection.addParam("password", password);
        httpConnection.addParam("action", "add");
        httpConnection.addParam("profilePicture", BitmapMethod.encodeBitmap(defaultProfilePicture));
        httpConnection.send();
        JSONObject response = httpConnection.getResponse();
        try {
            if(response.getString("status").equals("success")){
                userLogged = new User(username, password, response.getInt("id"), defaultProfilePicture);
            }
            if(response.getString("status").equals("error")){
                //TODO inform user that username already exists
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int logUser(String username, String password) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", username);
        httpConnection.addParam("password", password);
        httpConnection.addParam("action", "login");
        httpConnection.send();
        JSONObject response = httpConnection.getResponse();
        try {
            if(response.getString("status").equals("success")){
                userLogged = new User(username, password, response.getInt("id"), BitmapMethod.decodeBitmap(response.getString("profile_picture")));
                return 0;
            }else{
                return response.getInt("error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateGame(int gameId, String gameName, String gamePitch, String gameTime, String gameRules, String GameFile, User editor) {

    }

    @Override
    public void changeProfilePicture(String profilePicture) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", userLogged.getUsername());
        httpConnection.addParam("password", userLogged.getPassword());
        httpConnection.addParam("action", "change_profile_picture");
        httpConnection.addParam("profilePicture", profilePicture);
        httpConnection.addParam("userId", String.valueOf(userLogged.getId()));
        httpConnection.send();
        userLogged.setProfilePicture(BitmapMethod.decodeBitmap(profilePicture));

    }

    @Override
    public void changeUsername(String newUsername) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", userLogged.getUsername());
        httpConnection.addParam("password", userLogged.getPassword());
        httpConnection.addParam("action", "change_username");
        httpConnection.addParam("newUsername", newUsername);
        httpConnection.addParam("userId", String.valueOf(userLogged.getId()));
        httpConnection.send();
        userLogged.setUsername(newUsername);
    }

    @Override
    public boolean checkUsername(String username) {
        return false;
    }

    @Override
    public User getUserLogged() {
        return userLogged;
    }

    @Override
    public void changePassword(String newPassword) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", userLogged.getUsername());
        httpConnection.addParam("password", userLogged.getPassword());
        httpConnection.addParam("action", "change_password");
        httpConnection.addParam("id", String.valueOf(userLogged.getId()));
        httpConnection.addParam("profile_picture", BitmapMethod.encodeBitmap(userLogged.getProfilePicture()));
        userLogged.setPassword(newPassword);
    }

    @Override
    public void deleteUser(User user) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", userLogged.getUsername());
        httpConnection.addParam("password", userLogged.getPassword());
        httpConnection.addParam("action", "delete");
        httpConnection.addParam("profile_picture", BitmapMethod.encodeBitmap(userLogged.getProfilePicture()));
        httpConnection.addParam("id", String.valueOf(userLogged.getId()));
        httpConnection.send();
        userLogged = null;
    }

    @Override
    public void addGame(String gameName, String gamePitch, String gameTime, String gameRules, String gameDescription, String gameFile, String gameImage, User author) {

    }

    @Override
    public List<Game> getLastGamesCreated() {
        return null;
    }

    @Override
    public Game getGameFromId(int gameId) {
        return null;
    }

    @Override
    public List<Game> getPopularGames() {
        return null;
    }

    @Override
    public List<Game> getGamesPlayed(User user) {
        return null;
    }

    @Override
    public List<Game> getGamesCreated(User user) {
        return null;
    }

    @Override
    public void deleteGame(Game game) {

    }

    @Override
    public Game getGameSelected() {
        return null;
    }

    @Override
    public void startPlayingGame(User user, Game game) {

    }

    @Override
    public GameInfo getGameInfo(int gameId) {
        return null;
    }

    @Override
    public void addGameInfo(int gameId) {

    }

    @Override
    public void updateGameInfo(int gameID, String firstInfoName, String secondInfoName, String thirdInfoName, String fourthInfoName, String fifthInfoName) {

    }

    @Override
    public void deleteGameInfo(int gameId) {

    }
    public void setGameSelected(Game gameSelected) {
        this.gameSelected = gameSelected;
    }
    public void setDefaultUser(String username) {
        userLogged = new User(username, "pic", 0, defaultProfilePicture);
    }
}
