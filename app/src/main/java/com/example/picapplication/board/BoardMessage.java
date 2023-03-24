package com.example.picapplication.board;

/**
 * A board message interface
 */
public interface BoardMessage {
    public static final int CARD_INFORMATION = 0;
    public static final int MAIN_INFORMATION = 1;
    public static final int SECOND_INFORMATION = 2;
    public static final int THIRD_INFORMATION = 3;
    public static final int FOURTH_INFORMATION = 4;
    public static final int FIFTH_INFORMATION = 5;
    public static final int RESET_BOARD = 6;
    public static final int GAME_OVER = 7;
    public static final int GAME_START = 8;
    /**
     * @return the message
     */
    public String getMessage();
    /**
     * @return the type of the message
     */
    public int getType();
}
