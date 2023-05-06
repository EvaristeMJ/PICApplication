package com.example.picapplication.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.picapplication.MainActivity;
import com.example.picapplication.R;
import com.example.picapplication.board.BluetoothBoardConnection;
import com.example.picapplication.board.BoardConnection;
import com.example.picapplication.board.PicBoardConnection;
import com.example.picapplication.database.DatabaseHelper;
import com.example.picapplication.database.PicDatabase;
import com.example.picapplication.databinding.FragmentConnectBinding;
import com.google.android.material.button.MaterialButton;

/**
 * ConnectFragment is a fragment that allows the user to connect to a board.
 */
public class ConnectFragment extends Fragment {
    private PicBoardConnection boardConnection = new BoardConnection();
    private PicDatabase database = new DatabaseHelper();
    private TextView connectStatus;
    private MaterialButton connectButton;
    private FragmentConnectBinding binding;
    private static final int REQUEST_ENABLE_BT = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("searching"); //TODO: remove this line
        int idBoard = boardConnection.connect();
        if(idBoard != 0){
            database.getUserLogged().setIdBoard(idBoard);
            MainActivity mainActivity = (MainActivity) getActivity();
            boardConnection.addReceiver(mainActivity);
            mainActivity.setNavUserPlayer("Connected to PIC board : player " + idBoard);
            System.out.println("board found"); //TODO: remove this line
        }
        else {
            System.out.println("board not found"); //TODO: remove this line
        }
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
