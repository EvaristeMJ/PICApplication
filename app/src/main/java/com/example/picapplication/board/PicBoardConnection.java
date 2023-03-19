package com.example.chat.board;

import com.example.chat.database.Game;

/**
 * A pic board interface
 * Mainly used for handling the communication between the application and the board
 */
public interface PicBoardConnection {
    /**
     * Connect the application to the board
     */
    public void connect();
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

}
