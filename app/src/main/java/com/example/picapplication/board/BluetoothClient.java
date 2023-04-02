package com.example.picapplication.board;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import androidx.core.app.ActivityCompat;

import com.example.picapplication.database.DatabaseHelper;
import com.example.picapplication.database.Game;
import com.example.picapplication.database.PicDatabase;
import com.example.picapplication.database.User;
import com.example.picapplication.utilities.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.List;
import java.util.UUID;

public class BluetoothClient extends AsyncTask<Void, Void, Void> {
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private BluetoothAdapter adapter;
    private String message;
    private String userid = "1";
    private PicDatabase database = new DatabaseHelper();
    private BoardConnection boardConnection = new BoardConnection();
    private static final String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public BluetoothClient(BluetoothDevice device) {
        this.device = device;
        this.adapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    /**
     * Sends a message to connect with device
     * Accordingly to board response
     * Returns a number
     * @return result (0 if it fails, 1 to 4 if it is success)
     */
    @SuppressLint("MissingPermission")
    public int connection(){
        int result = 0;
        sendStringToBoard(userid,"send_a_string_to_board");
        System.out.println("sent username");
        String message = receiveStringFromBoard();
        System.out.println("received message");
        result = Integer.parseInt(message);
        // test sending
        Game game = new Game();
        game.setGameFile(Constants.TEST_GAME_FILE);
        sendGame(game);
        System.out.println("sent game");
        // get the id from the message
        return result;
    }
    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("MissingPermission")
    public void sendStringToBoard(String message,String serviceName){
        try {
            BluetoothServerSocket serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(serviceName, UUID.fromString(MY_UUID));
            socket = serverSocket.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("socket created");
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeSocket();
    }
    @SuppressLint("MissingPermission")
    public void sendGame(Game game){
        sendStringToBoard(game.getGameFile(),"send_the_game_to_board");
    }
    @SuppressLint("MissingPermission")
    /**
     * Receives a message from the board
     * open a server socket
     * and wait for a message
     * @return message
     */
    public String receiveStringFromBoard(){
        String message = "0";
        try {
            @SuppressLint("MissingPermission")
            BluetoothServerSocket serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord("receive_from_board", UUID.fromString(MY_UUID));
            socket = serverSocket.accept();
            Thread.sleep(1000);
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytes;
            while((bytes = inputStream.read(buffer)) != -1){
                message = new String(buffer, 0, bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeSocket();
        return message;
    }

    /**
     * Sends a message to get the players connected to the board
     * and returns a list of users
     * @return
     */
    public List<User> getPlayers(){
        List<User> players = null;
        sendStringToBoard("get_players","send_a_string_to_board");
        String message = receiveStringFromBoard();
        if(message.equals("0")){
            return null;
        }
        String[] playersId = message.split(",");
        for(int i = 0; i < playersId.length; i++){
            int id = Integer.parseInt(playersId[i]);
            User user = User.createDisplayUser(database.getUsernameFromId(id),null); // TODO put a real image instead of null
        }
        return players;
    }

    @SuppressLint("MissingPermission")
    @Override
    protected Void doInBackground(Void... voids) {
        String message = receiveStringFromBoard();
        System.out.println(message); // debug TODO remove
        this.message = message;
        publishProgress();
        return null;
    }

    /**
     * Updates the message on the board
     * @param values
     */
    public void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        System.out.println(message); // debug TODO remove
        boardConnection.sendMessage(message);
    }
}
