package com.example.picapplication.board;

/**
 * A board message interface
 */
public interface BoardMessage {
    /**
     * @return true if the message is a main information
     *        false if the message is a sub information
     */
    public boolean isMainInformation();
    /**
     * @return the message
     */
    public String getMessage();
}
