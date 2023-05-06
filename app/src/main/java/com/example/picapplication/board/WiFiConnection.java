package com.example.picapplication.board;

import android.os.AsyncTask;

import com.example.picapplication.database.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WiFiConnection extends AsyncTask<Void, Void, Void>{
    private static final String SSID = "SSID";
    private static final String PASS = "PASSWORD";
    private static final String IP = "192.168.43.155";
    private static final int PORT = 20100;
    private static Socket socket;
    private static List<String> messages = new ArrayList<>();
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static PicBoardConnection boardConnection = new BoardConnection();

    public List<String> receive(){
        List<String> messages = new ArrayList<>();
        BufferedReader in = new BufferedReader(new java.io.InputStreamReader(inputStream));
        try {
            String message = in.readLine();
            while(message != null){
                messages.add(message);
                message = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * Sends a message to the server
     * @param message
     */
    public void send(String message) {
        PrintWriter out = new PrintWriter(outputStream);
        out.println(message);
        out.flush();
    }
    /**
     * Connects to the server
     */
    public int connect(){
        try {
            System.out.println("Connecting");
            socket = new Socket(IP, PORT);
            System.out.println("Connected");
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Disconnects from the server
     */
    public void disconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendGame(Game game){
        send(game.getGameFile());
    }
    @Override
    protected Void doInBackground(Void... voids) {
        while(true){
            if(socket.isConnected()){
                ArrayList<String> newMessages = (ArrayList<String>) receive();
                for(String message : newMessages){
                    messages.add(message);
                }
                publishProgress();
            }
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        for(String message : messages){
            boardConnection.receiveMessage(message);
        }
    }
    public boolean isConnected(){
        return socket.isConnected();
    }
}
