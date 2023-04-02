package com.example.picapplication.board;

import com.example.picapplication.database.Game;

/**
 * A board message interface
 */
public class BoardMessage {
    public static final int CARD_INFORMATION = 0; // message when a card is played
    public static final int MAIN_INFORMATION = 1;
    public static final int SECOND_INFORMATION = 2;
    public static final int THIRD_INFORMATION = 3;
    public static final int FOURTH_INFORMATION = 4;
    public static final int RESET_BOARD = 6;
    public static final int GAME_OVER = 7;
    public static final int GAME_START = 8;
    public static final int RULE_INFORMATION = 9;
    private String message;
    /**
     * Creates a new board message
     * @param message the message
     * @param type the type of the message
     */
    public BoardMessage(String message, int type){
        this.message = message;
        this.type = type;
    }
    /**
     * Creates a new board message
     * the first character of the message is the type of the message
     * then the message
     * @param message the message
     */
    public BoardMessage(String message){
        this.message = message.substring(1);
        this.type = Integer.parseInt(message.substring(0,1));
    }
    private int type;
    /**
     * @return the message
     */
    public String getMessage(){
        return message;
    }
    /**
     * @return the type of the message
     */
    public int getType(){
        return type;
    }
}
