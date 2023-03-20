package com.example.picapplication.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.picapplication.sql.PicDatabase;
import com.example.chat.utilities.Constants;
import com.example.picapplication.sql.ConnectionHelper;
import com.example.picapplication.database.User;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DatabaseHelper implements PicDatabase {
    private User userLogged;
    private Game gameSelected;
    public static int USER_NOT_FOUND = -1;
    public static int WRONG_PASSWORD = -2;

    @Override
    public void addUser(String username, String password) {
        String sqlStatement = "INSERT INTO " + Constants.KEY_COLLECTION_USERS+
                "("+Constants.KEY_NAME+","+Constants.KEY_PASSWORD+","+Constants.KEY_GAMES_PLAYED+") " + "VALUES(?,?,?)";
        int id = 0;
        try {
            Connection conn = ConnectionHelper.connection();
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            pstmt.setString(3,"");

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs a user in the database
     * checks if the username and password are correct
     * if they are, it returns the id of the user logged
     * userLogged is set to the user logged
     * if the username is not found, it returns USER_NOT_FOUND
     * if the password is wrong, it returns WRONG_PASSWORD
     * @param username the username of the user
     * @param password the password of the user
     * @return
     */
    @Override
    public int logUser(String username, String password) {
        try {
            Connection conn = ConnectionHelper.connection();
            String sqlStatement = "SELECT * FROM " + Constants.KEY_COLLECTION_USERS + " WHERE " + Constants.KEY_NAME + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                if(rs.getString(Constants.KEY_PASSWORD).equals(password)){
                    int id = rs.getInt(Constants.KEY_USER_ID);
                    userLogged = new User(rs.getString(Constants.KEY_NAME),rs.getString(Constants.KEY_PASSWORD),id);
                    userLogged.setGamesPlayed(rs.getString(Constants.KEY_GAMES_PLAYED));
                    return id;
                }
                else{
                    return WRONG_PASSWORD;
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return USER_NOT_FOUND;
    }

    @Override
    public void updateGame(int gameId, String gameName, String gamePitch, String gameTime, String gameRules, String GameFile, User editor) {
        try {
            Connection conn = ConnectionHelper.connection();
            String sqlStatement = "UPDATE " + Constants.KEY_COLLECTION_GAMES + " SET " + Constants.KEY_GAME_NAME + " = ?," +
                    Constants.KEY_GAME_PITCH + " = ?," + Constants.KEY_GAME_DESCRIPTION + " = ?," + Constants.KEY_GAME_TIME + " = ?," +
                    Constants.KEY_GAME_RULES + " = ?," + Constants.KEY_GAME_FILE + " = ? WHERE " + Constants.KEY_GAME_ID + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1,gameName);
            pstmt.setString(2,gamePitch);
            pstmt.setString(3,gameTime);
            pstmt.setString(4,gameRules);
            pstmt.setString(5,GameFile);
            pstmt.setInt(6,gameId);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void changeUsername(String newUsername){
        try {
            Connection conn = ConnectionHelper.connection();
            String sqlStatement = "UPDATE " + Constants.KEY_COLLECTION_USERS + " SET " + Constants.KEY_NAME + " = ? WHERE " + Constants.KEY_USER_ID + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1,newUsername);
            pstmt.setInt(2,userLogged.getId());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkUsername(String username) {
        try {
            Connection conn = ConnectionHelper.connection();
            String sqlStatement = "SELECT * FROM " + Constants.KEY_COLLECTION_USERS + " WHERE " + Constants.KEY_NAME + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserLogged() {
        return userLogged;
    }

    @Override
    public void changePassword(String newPassword) {
        try {
            Connection conn = ConnectionHelper.connection();
            String sqlStatement = "UPDATE " + Constants.KEY_COLLECTION_USERS + " SET " + Constants.KEY_PASSWORD + " = ? WHERE " + Constants.KEY_USER_ID + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setString(1,newPassword);
            pstmt.setInt(2,userLogged.getId());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(User user) {
        try {
            Connection conn = ConnectionHelper.connection();
            String sqlStatement = "DELETE FROM " + Constants.KEY_COLLECTION_USERS + " WHERE " + Constants.KEY_USER_ID + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.setInt(1,user.getId());
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addGame(String gameName, String gamePitch, String gameTime, String gameRules, String gameDescription, String gameFile, String gameImage, User author) {
        String sqlStatement = "INSERT INTO " + Constants.KEY_COLLECTION_GAMES +
                "(" + Constants.KEY_GAME_NAME+ "," +Constants.KEY_GAME_PITCH+ "," +Constants.KEY_GAME_DESCRIPTION +","+
                Constants.KEY_GAME_TIME+"," + Constants.KEY_GAME_RULES + "," + Constants.KEY_GAME_FILE +","+Constants.KEY_GAME_IMAGE+","+Constants.KEY_GAME_POPULARITY +","
                +Constants.KEY_GAME_AUTHOR + ") "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        int id = 0;
        try {
            Connection conn = ConnectionHelper.connection();
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,gameName);
            pstmt.setString(2,gamePitch);
            pstmt.setString(3,gameDescription);
            pstmt.setString(4,gameTime);
            pstmt.setString(5,gameRules);
            pstmt.setString(6,gameFile);
            pstmt.setString(8, gameImage);
            pstmt.setString(9,author.getUsername());

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows > 0){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    id = rs.getInt(1);
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Game> getGamesFromQuery(String sqlStatement) {
        ArrayList <Game> games = new ArrayList<>();
        try{
            Connection conn = ConnectionHelper.connection();
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Game game = new Game(rs.getInt(Constants.KEY_GAME_ID),
                        rs.getString(Constants.KEY_GAME_NAME),
                        rs.getString(Constants.KEY_GAME_PITCH),
                        rs.getString(Constants.KEY_GAME_DESCRIPTION),
                        BitmapFactory.decodeFile(rs.getString(Constants.KEY_GAME_IMAGE)),
                        rs.getString(Constants.KEY_GAME_TIME),
                        rs.getString(Constants.KEY_GAME_RULES),
                        rs.getString(Constants.KEY_GAME_FILE),
                        rs.getInt(Constants.KEY_GAME_POPULARITY),
                        rs.getString(Constants.KEY_GAME_AUTHOR)
                );
                games.add(game);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public List<Game> getLastGamesCreated() {
        int numberOfGames = 100;
        String sqlStatement = "SELECT * FROM " + Constants.KEY_COLLECTION_GAMES + " ORDER BY " + Constants.KEY_GAME_ID + " DESC LIMIT " + numberOfGames;
        return getGamesFromQuery(sqlStatement);
    }

    @Override
    public Game getGameFromId(int gameId) {
        String sqlStatement = "SELECT * FROM " + Constants.KEY_COLLECTION_GAMES + " WHERE " + Constants.KEY_GAME_ID + " = " + gameId;
        List<Game> games = getGamesFromQuery(sqlStatement);
        if(games.size() > 0){
            return games.get(0);
        }
        return null;
    }

    @Override
    public List<Game> getPopularGames() {
        int numberOfGames = 100;
        String sqlStatement = "SELECT * FROM " + Constants.KEY_COLLECTION_GAMES + " ORDER BY " + Constants.KEY_GAME_POPULARITY + " DESC" + " LIMIT " + numberOfGames;
        return getGamesFromQuery(sqlStatement);
    }

    @Override
    public List<Game> getGamesPlayed(User user) {
        String sqlStatement = "SELECT * FROM " + Constants.KEY_COLLECTION_GAMES + " WHERE " + Constants.KEY_GAME_ID + " IN " + user.getGamesPlayed();
        return getGamesFromQuery(sqlStatement);
    }

    @Override
    public List<Game> getGamesCreated(User user) {
        String sqlStatement = "SELECT * FROM " + Constants.KEY_COLLECTION_GAMES + " WHERE " + Constants.KEY_GAME_AUTHOR + " = " + user.getUsername();
        return getGamesFromQuery(sqlStatement);
    }

    @Override
    public void deleteGame(Game game) {
        try {
            Connection conn = ConnectionHelper.connection();
            String sqlStatement = "DELETE FROM " + Constants.KEY_COLLECTION_GAMES + " WHERE " + Constants.KEY_GAME_ID + " = " + game.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increase the popularity of a game (+1)
     * Add the game to the list of games played by the user
     * @param user the user that plays the game
     * @param game the game to play
     */
    @Override
    public void startPlayingGame(User user, Game game) {
        try {
            Connection conn = ConnectionHelper.connection();
            String sqlStatement = "UPDATE " + Constants.KEY_COLLECTION_GAMES + " SET " + Constants.KEY_GAME_POPULARITY + " = " + Constants.KEY_GAME_POPULARITY + " + 1 WHERE " + Constants.KEY_GAME_ID + " = " + game.getId();
            PreparedStatement pstmt = conn.prepareStatement(sqlStatement);
            pstmt.executeUpdate();
            String sqlStatement2 = "UPDATE " + Constants.KEY_COLLECTION_USERS + " SET " + Constants.KEY_GAMES_PLAYED + " = "
                    + Constants.KEY_GAMES_PLAYED + " + " + ","+game.getId() + " WHERE " + Constants.KEY_NAME + " = " + user.getUsername();
            PreparedStatement pstmt2 = conn.prepareStatement(sqlStatement2);
            pstmt2.executeUpdate();
            gameSelected = game;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Game getGameSelected() {
        return gameSelected;
    }

}
