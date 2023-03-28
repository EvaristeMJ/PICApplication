package com.example.picapplication.board;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import androidx.core.app.ActivityCompat;

import com.example.picapplication.database.Game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothClient extends AsyncTask<Void, Void, Void> {
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private BluetoothAdapter adapter;
    private String message;
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
        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
            socket.connect();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("Hello, I'm a new Player".getBytes());
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytes;
            String message = "0";
            while((bytes = inputStream.read(buffer)) != -1){
                message = new String(buffer, 0, bytes);
            }
            // get the id from the message
            result = Integer.parseInt(message.substring(0,1));
            return result;


        } catch (IOException e) {
            return 0;
        }
    }
    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("MissingPermission")
    public void sendGame(Game game){
        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
            socket.connect();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(game.getGameFile().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected Void doInBackground(Void... voids) {
        try {

            socket = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
            socket.connect();

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("Hello, PicBoard".getBytes());

            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytes;
            while((bytes = inputStream.read(buffer)) != -1){
                String message = new String(buffer, 0, bytes);
                this.message = message;
                publishProgress();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the message on the board
     * @param values
     */
    public void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        boardConnection.sendMessage(message);
    }
}
