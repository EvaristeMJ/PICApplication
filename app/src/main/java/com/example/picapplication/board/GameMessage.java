package com.example.picapplication.board;

import com.example.picapplication.database.Game;

public class GameMessage extends BoardMessage{
    private Game game;
    /**
     * Creates a new board message
     *
     * @param game
     */
    public GameMessage(Game game) {
        super("", GAME_START);
        this.game = game;
    }
    public Game getGame(){
        return game;
    }
}
