package com.example.picapplication.board;

import com.example.picapplication.database.Game;

import java.util.List;

/**
 * A pic board interface
 * Mainly used for handling the communication between the application and the board
 */
public interface PicBoardConnection {
    /**
     * Connect the application to the board
     * @return the id of the user for the board (from 1 to 4)
     */
    public int connect();
    /**
     * Disconnect the application from the board
     */
    public void disconnect();
    /**
     * @return true if the application is connected to the board
     *         false otherwise
     */
    public boolean isConnected();
    /**
     * Send a message
     * to reset the board
     */
    public void resetBoard();
    /**
     * Send the game choose by the user to the board
     */
    public void loadGame(Game game);
    /**
     * Send a message to the receivers
     */
    public void sendMessage(BoardMessage message);

    /**
     * Add a receiver to the list of receivers
     * @param receiver
     */
    public void addReceiver(BoardMessageReceiver receiver);
    /**
     * Remove a receiver from the list of receivers
     * @param receiver
     */
    public void removeReceiver(BoardMessageReceiver receiver);
}
