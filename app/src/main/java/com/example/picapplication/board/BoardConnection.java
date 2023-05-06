package com.example.picapplication.board;

import android.os.AsyncTask;

import com.example.picapplication.database.DatabaseHelper;
import com.example.picapplication.database.Game;
import com.example.picapplication.database.PicDatabase;
import com.example.picapplication.database.User;

import java.util.ArrayList;
import java.util.List;

public class BoardConnection implements PicBoardConnection{
    private PicDatabase database = new DatabaseHelper();
    private static int CONNECTION_SUCCESS = 0;
    private static int CONNECTION_FAILED = -1;
    private static List<BoardMessageReceiver> receivers = new ArrayList<>();
    private static boolean ruleMode = false;
    WiFiConnection connection;
    @Override
    public int connect() {
        connection = new WiFiConnection();
        int result = connection.connect();
        if(result == CONNECTION_SUCCESS){
            connection.send(database.getUserLogged().getUsername());
            connection.send(database.getUserLogged().getId()+"");
            return Integer.parseInt(connection.receive().get(0));
        }
        else{
            return CONNECTION_FAILED;
        }
    }

    @Override
    public void disconnect() {
        connection.disconnect();
    }

    @Override
    public boolean isConnected() {
        return connection.isConnected();
    }

    @Override
    public void resetBoard() {
    }

    @Override
    public void loadGame(Game game) {
        connection.sendGame(game);
    }

    @Override
    public void sendMessage(BoardMessage message) {
        connection.send(message.getMessage());
    }
    public void receiveMessage(String message){
        for(BoardMessageReceiver receiver : receivers){
            receiver.onReceive(new BoardMessage(message));
        }
    }

    @Override
    public void addReceiver(BoardMessageReceiver receiver) {
        receivers.add(receiver);
    }

    @Override
    public void removeReceiver(BoardMessageReceiver receiver) {
        receivers.remove(receiver);
    }

    @Override
    public List<User> getUsers() {
        connection.send("get_users");
        List<String> usersId = connection.receive();
        List<User> users = new ArrayList<>();
        for(String id : usersId){
            String username = database.getUsernameFromId(Integer.parseInt(id));
            users.add(User.createDisplayUser(username,null));
        }
        return users;
    }

    @Override
    public void setRuleMode(boolean ruleMode) {
        if(ruleMode){
            connection.send("rule_mode");
        }
        else{
            connection.send("game_mode");
        }
        this.ruleMode = ruleMode;
    }

    @Override
    public boolean getRuleMode() {
        return ruleMode;
    }
}
