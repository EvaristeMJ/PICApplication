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
     * The type of the message is in the first characters before the first ','
     * The message is after ','
     * then the message
     * @param message the message
     */
    public BoardMessage(String message){
        for(int i = 0; i < message.length(); i++){
            if(message.charAt(i) == ','){
                this.type = Integer.parseInt(message.substring(0, i));
                this.message = message.substring(i + 1);
                return;
            }
        }
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
