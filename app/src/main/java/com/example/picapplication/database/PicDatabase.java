package com.example.picapplication.database;

import android.graphics.Matrix;

import com.example.picapplication.database.Game;
import com.example.picapplication.database.GameInfo;
import com.example.picapplication.database.User;

import java.util.List;

public interface PicDatabase {

    /**
     * Adds a new user to the database
     * @param username the username of the user
     * @param password the password of the user
     */
    public void addUser(String username,String password);

    /**
     * Get the username from the id
     * @param id the id of the user
     * @return the username of the user
     */
    public String getUsernameFromId(int id);

    /**
     * Logs a user in the database
     * @param username the username of the user
     * @param password the password of the user
     *                 @return the id of the user logged
     */
    public int logUser(String username, String password);

    /**
     * Updates a game in the database
     * @param gameId
     * @param gameName
     * @param gamePitch
     * @param gameTime
     * @param gameRules
     * @param GameFile
     * @param editor the user that edits the game
     */
    public void updateGame(int gameId,String gameName,String gamePitch,String gameTime,String gameRules,String GameFile,User editor);

    /**
     * Change the profile picture of the user logged
     * @param profilePicture
     */
    public void changeProfilePicture(String profilePicture);
    /**
     * Changes the username of the user logged
     * @param newUsername
     */
    void changeUsername(String newUsername, String password);

    /**
     * Checks if the username is already in the database
     * @param username
     * @return true if the username is already in the database, false otherwise
     */
    public boolean checkUsername(String username);

    /**
     * Gets the user logged
     * @return User the user logged
     */
    public User getUserLogged();

    /**
     * Changes the password of the user logged
     * @param newPassword
     */
    public void changePassword(String newPassword,String oldPassword);

    /**
     * Deletes a user from the database
     * @param user
     */
    public void deleteUser(User user, String password);

    /**
     * Adds a new game to the database
     * @param gameName
     * @param gamePitch
     * @param gameTime
     * @param gameRules
     * @param gameDescription
     * @param gameFile
     * @param gameImage
     * @param author the user that creates the game
     */
    void addGame(String gameName, String gamePitch, String gameTime, String gameRules, String gameDescription, String gameFile, String gameImage, User author);

    /**
     * Gets the last games created in the database
     * @return List<Game> the list of games
     */
    public List<Game> getLastGamesCreated();

    /**
     * @param gameId
     * @return Game the game with the id gameId
     */
    public Game getGameFromId(int gameId);

    /**
     * Gets the games that are popular in the database
     * @return List<Game> the list of games
     */
    public List<Game> getPopularGames();

    /**
     * Gets the games that have been played by the user
     * @param user
     * @return List<Game> the list of games
     */
    public List<Game> getGamesPlayed(User user);

    /**
     * Gets the games that have been created by the user
     * @param user
     * @return List<Game> the list of games
     */
    public List<Game> getGamesCreated(User user);

    /**
     * Deletes a game from the database
     * @param game to delete
     */
    public void deleteGame(Game game);

    /**
     * Gets the game selected by the user
     * @return Game the game selected
     */
    public Game getGameSelected();
    /**
     * Launches a game
     * @param user the user that plays the game
     * @param game the game to play
     */
    public void startPlayingGame(User user, Game game);

    public void setGameSelected(Game gameSelected);

}
