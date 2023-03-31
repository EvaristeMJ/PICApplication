package com.example.picapplication.board;

import java.util.ArrayList;
import java.util.List;

public class TestThread extends Thread{
    private PicBoardConnection picBoardConnection = new BoardConnection();
    private List<BoardMessage> boardMessages = new ArrayList<>();
    public TestThread(){
        System.out.println("Creating test thread");
        start();
    }
    private void initBoardMessages(){
        boardMessages.add(new BoardMessage("27.0",BoardMessage.MAIN_INFORMATION));
        boardMessages.add(new BoardMessage("24.0",BoardMessage.MAIN_INFORMATION));
        boardMessages.add(new BoardMessage("Ace of clubs",BoardMessage.CARD_INFORMATION));
        boardMessages.add(new BoardMessage("You can play either an ace or a clubs",BoardMessage.RULE_INFORMATION));
        boardMessages.add(new BoardMessage("7.0",BoardMessage.SECOND_INFORMATION));
        boardMessages.add(new BoardMessage("10.0",BoardMessage.SECOND_INFORMATION));
        boardMessages.add(new BoardMessage("20.0",BoardMessage.MAIN_INFORMATION));
    }
    @Override
    public void run() {
        initBoardMessages();
        System.out.println("Starting test thread");
        for(int i = 0; i < boardMessages.size(); i++){
            System.out.println("Sending message: " + boardMessages.get(i).getMessage());
            picBoardConnection.sendMessage(boardMessages.get(i));
            try {
                Thread.sleep((long)(Math.random()*5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
