package com.example.picapplication.board;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.picapplication.database.Game;

import java.util.ArrayList;
import java.util.List;

public class BoardConnection implements PicBoardConnection {
    public static final int CONNECTION_FAILED = 0;
    private static List<BoardMessageReceiver> receivers = new ArrayList<>();
    private static BluetoothClient client;
    private static BluetoothDevice device;
    private static final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private static boolean connected = false;
    private static final String KEY_NAME_BOARD = "PicBoard";
    private static String stringMessage;

    /**
     * Connects to the board with bluetooth
     * returns the player id on the board (1 to 4)
     * returns 0 if the connection failed
     * @return
     */
    @Override
    public int connect() {
        setDevice();
        if (adapter.isEnabled() && device != null) {
            client = new BluetoothClient(device);
            return client.connection();
        }
        return CONNECTION_FAILED;
    }

    @Override
    public void disconnect() {
        if (client != null) {
            client.cancel(true);
            connected = false;
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void resetBoard() {
    }

    /**
     * Send the game to the board
     * @param game
     */
    @Override
    public void loadGame(Game game) {
        if(client != null){
            client.sendGame(game);
        }
    }

    @Override
    public void sendMessage(BoardMessage message) {
        for (BoardMessageReceiver receiver : receivers) {
            receiver.onReceive(message);
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

    public static void setDevice(BluetoothDevice device) {
        BoardConnection.device = device;
    }

    public void sendMessage(String message) {
        sendMessage(new BoardMessage(message));
    }


    @SuppressLint("MissingPermission")
    /**
     * Search in the paired devices one with KEY_NAME_BOARD in it
     * Then sets the device
     */
    private static void setDevice() {
        //TODO check permissions
        for (BluetoothDevice device : adapter.getBondedDevices()) {
            if (device.getName()!= null && device.getName().contains(KEY_NAME_BOARD)) {
                BoardConnection.setDevice(device);
            }
        }
    }
}
