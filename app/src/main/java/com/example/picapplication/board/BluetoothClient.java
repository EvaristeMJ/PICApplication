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

import com.example.picapplication.database.Game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
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
        sendStringToBoard("Hello, I'm a new Player");
        System.out.println("OK for sending");
        closeSocket();
        String message = receiveStringFromBoard();
        result = Integer.parseInt(message);
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
    public void sendStringToBoard(String message){
        Method m = null;
        try {
            m = device.getClass().getMethod("createInsecureRfcommSocket", new Class[] {int.class});
            socket = (BluetoothSocket) m.invoke(device, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                socket.connect();
                break;
            } catch (IOException e) {
                continue;
            }
        }
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            outputStream.write("Hello, I'm a new Player".getBytes());
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
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("MissingPermission")
    /**
     * Receives a message from the board
     * open a server socket
     * and wait for a message
     * @return message
     */
    public String receiveStringFromBoard(){
        try {
            @SuppressLint("MissingPermission")
            BluetoothServerSocket serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(device.getName(), UUID.fromString(MY_UUID));
            socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytes;
            String message = "0";
            while((bytes = inputStream.read(buffer)) != -1){
                message = new String(buffer, 0, bytes);
            }
            serverSocket.close();
            closeSocket();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
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
