package com.example.picapplication.database;

import android.graphics.Bitmap;
import android.os.StrictMode;

import com.example.picapplication.utilities.BitmapMethod;
import com.example.picapplication.utilities.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper implements PicDatabase {

    private static User userLogged;
    private static Game gameSelected;
    private static Bitmap defaultProfilePicture = BitmapMethod.standardBitmap(BitmapMethod.getDefaultProfilePicture()); //TODO: set default profile picture
    public static int USER_NOT_FOUND = -1;
    public static int WRONG_PASSWORD = -2;
    private String url = "http://137.194.210.224/";
    private static String serverSideUser = "user.php";
    private static String serverSideGame = "game.php";
    private static String serverSideUserInteraction = "user_play_games.php";
    public DatabaseHelper(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void defaultLogin(){
        userLogged = new User("anon","pic",0,defaultProfilePicture);

    }
    @Override
    public void addUser(String username, String password) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", username);
        httpConnection.addParam("password", password);
        httpConnection.addParam("action", "add");
        httpConnection.addParam("profilePicture", Constants.DEFAULT_ENCODED_PICTURE);
        httpConnection.post();

    }

    @Override
    public int logUser(String username, String password) {

        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("action", "login");
        httpConnection.addParam("username", username);
        httpConnection.addParam("password", password);
        httpConnection.post();

        JSONObject response = httpConnection.getResponse();
        try {
            if(response.getString("status").equals("success")){
                userLogged = new User(username, password, response.getInt("userid"), defaultProfilePicture);
                System.out.println("User logged: " + userLogged.getUsername());
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
    public String getUsernameFromId(int id){
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("action", "get_username");
        httpConnection.addParam("id", id+"");
        httpConnection.post();
        JSONObject response = httpConnection.getResponse();
        String username = "";
        try {
            if(response.getString("status").equals("success")){
                username = response.getString("username");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }

    @Override
    public void updateGame(int gameId, String gameName, String gamePitch, String gameTime, String gameRules, String GameFile, User editor) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideGame);
        httpConnection.addParam("author", ""+editor.getId());
        httpConnection.addParam("password", editor.getPassword());
        httpConnection.addParam("action", "update");
        httpConnection.addParam("id", String.valueOf(gameId));
        httpConnection.addParam("name", gameName);
        httpConnection.addParam("game_pitch", gamePitch);
        httpConnection.addParam("game_time", gameTime);
        httpConnection.addParam("game_rules", gameRules);
        httpConnection.addParam("game_file", GameFile);
        httpConnection.post();
    }

    @Override
    public void changeProfilePicture(String profilePicture) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("action", "change_profile_picture");
        httpConnection.addParam("profilePicture", profilePicture);
        httpConnection.addParam("id", String.valueOf(userLogged.getId()));
        httpConnection.post();
        userLogged.setProfilePicture(BitmapMethod.decodeBitmap(profilePicture));

    }

    @Override
    public void changeUsername(String newUsername, String password) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("password", password);
        httpConnection.addParam("action", "change_username");
        httpConnection.addParam("username", newUsername);
        httpConnection.addParam("userId", String.valueOf(userLogged.getId()));
        httpConnection.post();
        if(httpConnection.isSuccessful()){
            userLogged.setUsername(newUsername);
        }
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
    public void changePassword(String newPassword, String password) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", userLogged.getUsername());
        httpConnection.addParam("password", userLogged.getPassword());
        httpConnection.addParam("action", "change_password");
        httpConnection.addParam("id", String.valueOf(userLogged.getId()));
        httpConnection.addParam("profile_picture", BitmapMethod.encodeBitmap(userLogged.getProfilePicture()));
        userLogged.setPassword(newPassword);
    }

    @Override
    public void deleteUser(User user, String password) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideUser);
        httpConnection.addParam("username", userLogged.getUsername());
        httpConnection.addParam("password", userLogged.getPassword());
        httpConnection.addParam("action", "delete");
        httpConnection.addParam("id", String.valueOf(userLogged.getId()));
        httpConnection.post();
        JSONObject response = httpConnection.getResponse();
        try {
            if(response.getString("status").equals("success")){
                userLogged = null;
                System.out.println("User deleted");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addGame(String gameName, String gamePitch, String gameTime, String gameRules, String gameDescription, String gameFile, String gameImage, User author) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideGame);
        httpConnection.addParam("author", ""+author.getId());
        httpConnection.addParam("password", author.getPassword());
        httpConnection.addParam("action", "add");
        httpConnection.addParam("name", gameName);
        httpConnection.addParam("game_pitch", gamePitch);
        httpConnection.addParam("game_time", gameTime);
        httpConnection.addParam("game_rules", gameRules);
        httpConnection.addParam("game_description", gameDescription);
        httpConnection.addParam("game_file", gameFile);
        httpConnection.addParam("game_image", gameImage);
        httpConnection.post();
    }

    @Override
    public List<Game> getLastGamesCreated() {
        HttpConnection httpConnection = new HttpConnection(url + serverSideGame);
        httpConnection.addParam("action", "get_last");
        httpConnection.post();
        JSONObject response = httpConnection.getResponse();
        List<Game> games = new ArrayList<>();
        for(int i = 0; i < response.length(); i++){
            try {
                JSONObject game = response.getJSONObject(String.valueOf(i));
                games.add(new Game(game.getInt("id"), game.getString("name"),
                        game.getString("game_pitch"), game.getString("game_description"),
                        BitmapMethod.decodeBitmap(game.getString("game_image")), game.getString("game_time"),
                        game.getString("game_rules"),game.getString("game_file"),game.getInt("popularity"),game.getString("author")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return games;
    }

    @Override
    public Game getGameFromId(int gameId) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideGame);
        httpConnection.addParam("action", "get_game");
        httpConnection.addParam("id", String.valueOf(gameId));
        httpConnection.post();
        JSONObject response = httpConnection.getResponse();
        Game game = null;
        try {
            game = new Game(response.getInt("id"), response.getString("name"),
                    response.getString("game_pitch"), response.getString("game_description"),
                    BitmapMethod.decodeBitmap(response.getString("game_image")), response.getString("game_time"),
                    response.getString("game_rules"),response.getString("game_file"),response.getInt("popularity"),response.getString("author"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return game;
    }

    @Override
    public List<Game> getPopularGames() {
        HttpConnection httpConnection = new HttpConnection(url + serverSideGame);
        httpConnection.addParam("action", "get_popular");
        httpConnection.post();
        JSONObject response = httpConnection.getResponse();
        List<Game> games = new ArrayList<>();
        for(int i = 0; i < response.length(); i++){
            try {
                JSONObject game = response.getJSONObject(String.valueOf(i));
                games.add(new Game(game.getInt("id"), game.getString("name"),
                        game.getString("game_pitch"), game.getString("game_description"),
                        BitmapMethod.decodeBitmap(game.getString("game_image")), game.getString("game_time"),
                        game.getString("game_rules"),game.getString("game_file"),game.getInt("popularity"),game.getString("author")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return games;
    }

    @Override
    public List<Game> getGamesPlayed(User user) {
        return null;
    }

    @Override
    public List<Game> getGamesCreated(User user) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideGame);
        httpConnection.addParam("action", "get_created");
        httpConnection.addParam("author", ""+user.getId());
        httpConnection.post();
        JSONObject response = httpConnection.getResponse();
        List<Game> games = new ArrayList<>();
        for(int i = 0; i < response.length(); i++){
            try {
                JSONObject game = response.getJSONObject(String.valueOf(i));
                games.add(new Game(game.getInt("id"), game.getString("name"),
                        game.getString("game_pitch"), game.getString("game_description"),
                        BitmapMethod.decodeBitmap(game.getString("game_image")), game.getString("game_time"),
                        game.getString("game_rules"),game.getString("game_file"),game.getInt("popularity"),game.getString("author")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return games;
    }

    @Override
    public void deleteGame(Game game) {
        HttpConnection httpConnection = new HttpConnection(url + serverSideGame);
        httpConnection.addParam("action", "delete");
        httpConnection.addParam("id", String.valueOf(game.getId()));
        httpConnection.post();
    }

    @Override
    public Game getGameSelected() {
        return gameSelected;
    }

    @Override
    public void startPlayingGame(User user, Game game) {
        /*
        TODO : remove this comment
        HttpConnection httpConnection = new HttpConnection(url + serverSideUserInteraction);
        httpConnection.addParam("action", "start_playing");
        httpConnection.addParam("gameid", String.valueOf(game.getId()));
        httpConnection.addParam("userid", String.valueOf(user.getId()));
        httpConnection.post();
         */
        gameSelected = game;
    }
    public void setGameSelected(Game gameSelected) {
        this.gameSelected = gameSelected;
    }
    public void setDefaultUser(String username) {
        userLogged = new User(username, "pic", 0, defaultProfilePicture);
    }
}
